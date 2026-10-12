package com.example.demo.framework.util;

import com.example.demo.framework.util.UtilFixtures.Person;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WordUtilTest {
    private byte[] template() throws IOException {
        try (XWPFDocument document = new XWPFDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            var run = document.createParagraph().createRun();
            run.setBold(true);
            run.setText("姓名：{{name}}，城市：{{address.city}}，未知：{{unknown}}，保留：{{unlisted}}");
            document.createTable(1, 1).getRow(0).getCell(0).setText("{{name}} / {{age}}");
            document.write(out);
            return out.toByteArray();
        }
    }

    @Test
    void replacesParagraphAndTablePropertiesWhilePreservingFormatting() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WordUtil.write(new ByteArrayInputStream(template()), out, UtilFixtures.person("张三"),
            List.of("name", "address.city", "unknown", "age"));
        try (XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(out.toByteArray()))) {
            assertThat(document.getParagraphs().getFirst().getText()).isEqualTo("姓名：张三，城市：杭州，未知：，保留：{{unlisted}}");
            assertThat(document.getParagraphs().getFirst().getRuns().getFirst().isBold()).isTrue();
            assertThat(document.getTables().getFirst().getRow(0).getCell(0).getText()).isEqualTo("张三 / 18");
        }
    }

    @Test
    void nullDataAndNestedNullValuesProduceEmptyReplacement() throws Exception {
        for (Person data : new Person[] {null, new Person()}) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            WordUtil.write(new ByteArrayInputStream(template()), out, data, List.of("name", "address.city", "age", "unknown"));
            try (XWPFDocument document = new XWPFDocument(new ByteArrayInputStream(out.toByteArray()))) {
                assertThat(document.getParagraphs().getFirst().getText()).isEqualTo("姓名：，城市：，未知：，保留：{{unlisted}}");
                assertThat(document.getTables().getFirst().getRow(0).getCell(0).getText()).isEqualTo(" / ");
            }
        }
    }

    @Test
    void nullPropertyListDoesNotReadInputOrWriteOutput() throws Exception {
        InputStream unreadable = new InputStream() {
            public int read() { throw new AssertionError("must not read"); }
        };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WordUtil.write(unreadable, out, null, null);
        assertThat(out.size()).isZero();
    }

    @Test
    void outputFailureIsPropagated() throws Exception {
        OutputStream broken = new OutputStream() {
            public void write(int value) throws IOException { throw new IOException("disk full"); }
        };
        byte[] input = template();
        assertThatThrownBy(() -> WordUtil.write(new ByteArrayInputStream(input), broken, UtilFixtures.person("name"), List.of("name")))
            .isInstanceOf(org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException.class)
            .hasMessageContaining("Failed to save");
    }
}
