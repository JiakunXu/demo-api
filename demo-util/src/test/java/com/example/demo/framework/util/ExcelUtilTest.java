package com.example.demo.framework.util;

import com.example.demo.framework.util.UtilFixtures.Person;
import com.example.demo.framework.util.UtilFixtures.Report;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ExcelUtilTest {
    private static final List<String> PROPERTIES = List.of("name", "age", "amount", "active", "date", "address.city");

    private byte[] template(int sheets) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            for (int index = 0; index < sheets; index++) {
                Sheet sheet = workbook.createSheet("sheet" + index);
                sheet.createRow(0).createCell(0).setCellValue("title");
                sheet.createRow(1).createCell(0).setCellValue("header");
                Row row = sheet.createRow(2);
                for (int column = 0; column < PROPERTIES.size(); column++) row.createCell(column, CellType.BLANK);
                CellStyle dateStyle = workbook.createCellStyle();
                dateStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd"));
                row.getCell(4).setCellStyle(dateStyle);
            }
            workbook.write(out);
            return out.toByteArray();
        }
    }

    @Test
    void readsTypedCellsFormulaAndNestedBeanSkippingBlankKeys() throws Exception {
        byte[] input;
        Date expectedDate = DateUtil.getDate("2024-02-29");
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(template(1)))) {
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(2);
            row.getCell(0).setCellValue("张三");
            row.getCell(1).setCellValue(18);
            row.getCell(2).setCellFormula("10+2.5");
            workbook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(row.getCell(2));
            row.getCell(3).setCellValue(true);
            row.getCell(4).setCellValue(expectedDate);
            row.getCell(5).setCellValue("杭州");
            sheet.createRow(3).createCell(0, CellType.BLANK);
            sheet.createRow(4).createCell(1).setCellValue("ignored without key");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            input = out.toByteArray();
        }
        List<Person> people = ExcelUtil.read(new ByteArrayInputStream(input), Person.class, PROPERTIES);
        assertThat(people).hasSize(1);
        Person person = people.getFirst();
        assertThat(person.getName()).isEqualTo("张三");
        assertThat(person.getAge()).isEqualTo(18);
        assertThat(person.getAmount()).isEqualTo(12.5);
        assertThat(person.getActive()).isTrue();
        assertThat(person.getDate()).isEqualTo(expectedDate);
        assertThat(person.getAddress().getCity()).isEqualTo("杭州");
    }

    @Test
    void listExportPreservesHeadersAndDateStyleAcrossRows() throws Exception {
        Person first = UtilFixtures.person("first");
        first.setDate(DateUtil.getDate("2024-02-29"));
        Person second = UtilFixtures.person("second");
        second.setDate(first.getDate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelUtil.write(new ByteArrayInputStream(template(1)), out, List.of(first, second), PROPERTIES);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(out.toByteArray()))) {
            Sheet sheet = workbook.getSheetAt(0);
            assertThat(sheet.getRow(0).getCell(0).getStringCellValue()).isEqualTo("title");
            assertThat(sheet.getRow(1).getCell(0).getStringCellValue()).isEqualTo("header");
            for (int index = 2; index <= 3; index++) {
                assertThat(sheet.getRow(index).getCell(0).getStringCellValue()).isEqualTo(index == 2 ? "first" : "second");
                assertThat(sheet.getRow(index).getCell(2).getNumericCellValue()).isEqualTo(12.5);
                assertThat(sheet.getRow(index).getCell(3).getBooleanCellValue()).isTrue();
                assertThat(sheet.getRow(index).getCell(4).getCellStyle().getDataFormatString()).isEqualTo("yyyy-mm-dd");
                assertThat(sheet.getRow(index).getCell(5).getStringCellValue()).isEqualTo("杭州");
            }
        }
    }

    @Test
    void objectExportMapsListAndSingleBeanToDifferentSheets() throws Exception {
        Report report = new Report();
        report.setPeople(List.of(UtilFixtures.person("first"), UtilFixtures.person("second")));
        report.setSummary(UtilFixtures.person("summary"));
        Map<String, List<String>> props = new LinkedHashMap<>();
        props.put("people", List.of("name", "address.city"));
        props.put("summary", List.of("name"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelUtil.write(new ByteArrayInputStream(template(2)), out, report, props);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(out.toByteArray()))) {
            assertThat(workbook.getSheetAt(0).getRow(2).getCell(0).getStringCellValue()).isEqualTo("first");
            assertThat(workbook.getSheetAt(0).getRow(3).getCell(0).getStringCellValue()).isEqualTo("second");
            assertThat(workbook.getSheetAt(0).getRow(3).getCell(1).getStringCellValue()).isEqualTo("杭州");
            assertThat(workbook.getSheetAt(1).getRow(2).getCell(0).getStringCellValue()).isEqualTo("summary");
        }
    }

    @Test
    void downloadProducesReadableWorkbook() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelUtil.download(new ByteArrayInputStream(template(1)), out);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(out.toByteArray()))) {
            assertThat(workbook.getNumberOfSheets()).isEqualTo(1);
            assertThat(workbook.getSheetAt(0).getRow(0).getCell(0).getStringCellValue()).isEqualTo("title");
        }
    }

    @Test
    void nullPropertyMappingsLeaveStreamsUntouched() throws Exception {
        InputStream unreadable = new InputStream() {
            public int read() { throw new AssertionError("must not read"); }
        };
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        assertThat(ExcelUtil.read(unreadable, Person.class, null)).isNull();
        ExcelUtil.write(unreadable, out, List.of(), (List<String>) null);
        ExcelUtil.write(unreadable, out, new Report(), (Map<String, List<String>>) null);
        assertThat(out.size()).isZero();
    }

    @Test
    void emptyDataStillProducesWorkbook() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelUtil.write(new ByteArrayInputStream(template(1)), out, List.of(), PROPERTIES);
        try (Workbook workbook = WorkbookFactory.create(new ByteArrayInputStream(out.toByteArray()))) {
            assertThat(workbook.getSheetAt(0).getRow(1).getCell(0).getStringCellValue()).isEqualTo("header");
        }
    }

    @Test
    void outputFailureIsPropagated() throws Exception {
        OutputStream broken = new OutputStream() {
            public void write(int value) throws IOException { throw new IOException("disk full"); }
        };
        byte[] input = template(1);
        assertThatThrownBy(() -> ExcelUtil.download(new ByteArrayInputStream(input), broken)).isInstanceOf(org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException.class)
            .hasMessageContaining("Failed to save");
    }
}
