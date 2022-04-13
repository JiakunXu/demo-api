package com.example.demo.wxpay.manager;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author JiakunXu
 */
public class XmlUtil {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    public static <T extends Visitor> T parse(String str, T object) {
        if (StringUtils.isBlank(str) || object == null) {
            return null;
        }

        Document document = null;

        try {
            document = DocumentHelper.parseText(str.trim());
        } catch (DocumentException e) {
            logger.error("parseText", e);

            return object;
        }

        document.accept(object);

        return object;
    }

}
