package com.example.demo.wxpay.api.ao;

import org.dom4j.Element;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务结果.
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Result extends Return {

    /**
     * 业务结果 SUCCESS/FAIL.
     */
    private String resultCode;

    /**
     * 错误代码.
     */
    private String errCode;

    /**
     * 错误代码描述.
     */
    private String errCodeDes;

    @Override
    public void visit(Element node) {
        super.visit(node);

        String name = node.getName();
        String text = node.getText();

        if ("result_code".equals(name)) {
            resultCode = text;
        } else if ("err_code".equals(name)) {
            errCode = text;
        } else if ("err_code_des".equals(name)) {
            errCodeDes = text;
        }
    }

}
