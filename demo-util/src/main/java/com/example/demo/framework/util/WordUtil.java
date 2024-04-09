package com.example.demo.framework.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class WordUtil {

    public static void write(InputStream in, OutputStream out, Object data,
                             List<String> props) throws IOException, InvocationTargetException,
                                                 IllegalAccessException {
        if (props == null) {
            return;
        }

        XWPFDocument document = null;

        try {
            document = new XWPFDocument(in);

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setText(replace(run.text(), data, props), 0);
                }
            }

            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow tableRow : table.getRows()) {
                    for (XWPFTableCell tableCell : tableRow.getTableCells()) {
                        for (XWPFParagraph paragraph : tableCell.getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                run.setText(replace(run.text(), data, props), 0);
                            }
                        }
                    }
                }
            }

            document.write(out);
        } finally {
            IOUtils.closeQuietly(document);
        }
    }

    private static String replace(String text, Object data,
                                  List<String> props) throws InvocationTargetException,
                                                      IllegalAccessException {
        for (String prop : props) {
            if (!text.contains("{{" + prop + "}}")) {
                continue;
            }

            if (data == null) {
                text = text.replace("{{" + prop + "}}", "");
                continue;
            }

            Object obj = data;

            for (String p : prop.split("[.]")) {
                Method getMethod = findGetMethod(obj, p);
                if (getMethod == null) {
                    obj = null;
                    break;
                }
                obj = getMethod.invoke(obj);
                if (obj == null) {
                    break;
                }
            }

            if (obj == null) {
                text = text.replace("{{" + prop + "}}", "");
            } else {
                text = text.replace("{{" + prop + "}}", obj.toString());
            }
        }

        return text;
    }

    private static Method findGetMethod(Object object, String name) {
        return BeanUtils.findMethod(object.getClass(), "get" + StringUtils.capitalize(name));
    }

}
