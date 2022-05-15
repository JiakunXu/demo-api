package com.example.demo.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Visitor;

/**
 * @author JiakunXu
 */
public final class XmlUtil {

    private XmlUtil() {
    }

    public static <T extends Visitor> T parse(String str, T object) {
        if (StringUtils.isBlank(str) || object == null) {
            return null;
        }

        Document document;

        try {
            document = DocumentHelper.parseText(str);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        document.accept(object);

        return object;
    }

}
