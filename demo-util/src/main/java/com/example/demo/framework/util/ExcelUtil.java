package com.example.demo.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson2.JSON;

public class ExcelUtil {

    public static void download(InputStream in, OutputStream out) throws IOException {
        Workbook workbook = null;

        try {
            workbook = WorkbookFactory.create(in);

            workbook.write(out);
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    public static <T> List<T> read(InputStream in, Class<T> clazz,
                                   List<String> props) throws IOException, InstantiationException,
            IllegalAccessException,
            InvocationTargetException {
        if (props == null) {
            return null;
        }

        Workbook workbook = null;

        try {
            workbook = WorkbookFactory.create(in);
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet.getLastRowNum() == 0) {
                return null;
            }

            List<T> list = new ArrayList<T>();

            for (int rownum = 2; rownum <= sheet.getLastRowNum(); rownum++) {
                Row row = sheet.getRow(rownum);
                int column = 0;

                if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) {
                    continue;
                }

                T object = clazz.newInstance();

                for (String prop : props) {
                    Object obj = object;

                    int length = prop.split("[.]").length;

                    for (int i = 0; i < length; i++) {
                        String p = prop.split("[.]")[i];

                        if (i == length - 1) {
                            Method setMethod = findSetMethod(obj, p);
                            if (setMethod != null) {
                                setMethod.invoke(obj,
                                        getCell(row, column++, setMethod.getParameterTypes()[0]));
                            }
                            break;
                        }

                        Method getMethod = findGetMethod(obj, p);
                        if (getMethod == null) {
                            break;
                        }
                        Object sub = getMethod.invoke(obj);
                        if (sub != null) {
                            obj = sub;
                            continue;
                        }
                        Method setMethod = findSetMethod(obj, p);
                        if (setMethod == null) {
                            break;
                        }
                        sub = setMethod.getParameterTypes()[0].newInstance();
                        setMethod.invoke(obj, sub);
                        obj = sub;
                    }
                }

                list.add(object);
            }

            return list;
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    public static void write(InputStream in, OutputStream out, List<?> dataList,
                             List<String> props) throws IOException, InvocationTargetException,
            IllegalAccessException {
        if (props == null) {
            return;
        }

        Workbook workbook = null;

        try {
            workbook = WorkbookFactory.create(in);

            Sheet sheet = workbook.getSheetAt(0);
            int rownum = 2;

            if (dataList != null && dataList.size() > 0) {
                for (Object object : dataList) {
                    setRow(sheet, rownum++, object, props);
                }
            }

            workbook.write(out);
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    public static void write(InputStream in, OutputStream out, Object data,
                             Map<String, List<String>> props) throws IOException,
            InvocationTargetException,
            IllegalAccessException {
        if (props == null) {
            return;
        }

        Workbook workbook = null;

        try {
            workbook = WorkbookFactory.create(in);

            int index = 0;

            if (data != null) {
                for (Map.Entry<String, List<String>> map : props.entrySet()) {
                    Sheet sheet = workbook.getSheetAt(index++);
                    int rownum = 2;

                    Method getMethod = findGetMethod(data, map.getKey());
                    if (getMethod == null) {
                        continue;
                    }
                    Object obj = getMethod.invoke(data);
                    if (obj == null) {
                        continue;
                    }

                    if (obj instanceof List) {
                        for (Object object : (List) obj) {
                            setRow(sheet, rownum++, object, map.getValue());
                        }
                    } else {
                        setRow(sheet, rownum, obj, map.getValue());
                    }
                }
            }

            workbook.write(out);
        } finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    private static Method findGetMethod(Object object, String name) {
        return BeanUtils.findMethod(object.getClass(), "get" + StringUtils.capitalize(name));
    }

    private static Method findSetMethod(Object object, String name) {
        Method[] methods = object.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.getName().equals("set" + StringUtils.capitalize(name))
                    && method.getParameterTypes().length == 1) {
                return method;
            }
        }

        return null;
    }

    private static void setRow(Sheet sheet, int rownum, Object object,
                               List<String> props) throws InvocationTargetException,
            IllegalAccessException {
        Row row = sheet.getRow(rownum);

        if (row == null) {
            row = sheet.createRow(rownum);
            row.setRowStyle(sheet.getRow(rownum - 1).getRowStyle());
        }

        int column = 0;

        for (String prop : props) {
            Object obj = object;

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

            setCell(sheet, rownum, column++, obj);
        }
    }

    private static <T> T getCell(Row row, int column, Class<T> clazz) {
        Cell cell = row.getCell(column);

        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
            if (DateUtil.isCellDateFormatted(cell)) {
                if (clazz == Date.class) {
                    return JSON.parseObject(
                            JSON.toJSONString(DateUtil.getJavaDate(cell.getNumericCellValue())), clazz);
                }
            } else {
                return JSON.parseObject(
                        JSON.toJSONString(new BigDecimal(cell.getNumericCellValue())), clazz);
            }
        }

        if (cell.getCellType() == CellType.STRING) {
            return JSON.parseObject(JSON.toJSONString(cell.getStringCellValue()), clazz);
        }

        if (cell.getCellType() == CellType.BOOLEAN) {
            return JSON.parseObject(JSON.toJSONString(cell.getBooleanCellValue()), clazz);
        }

        if (cell.getCellType() == CellType.ERROR) {
            return JSON.parseObject(JSON.toJSONString(cell.getErrorCellValue()), clazz);
        }

        return null;
    }

    private static void setCell(Sheet sheet, int rownum, int column, Object value) {
        Cell cell = sheet.getRow(rownum).getCell(column);

        if (cell == null) {
            cell = sheet.getRow(rownum).createCell(column,
                    sheet.getRow(rownum - 1).getCell(column).getCellType());
            cell.setCellStyle(sheet.getRow(rownum - 1).getCell(column).getCellStyle());
        }

        if (value == null) {
            return;
        }

        if (value instanceof Double) {
            cell.setCellValue((Double) value);
            return;
        }

        if (value instanceof Date) {
            cell.setCellValue((Date) value);
            return;
        }

        if (value instanceof String) {
            cell.setCellValue((String) value);
            return;
        }

        if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
            return;
        }

        cell.setCellValue(JSON.toJSONString(value));
    }

}
