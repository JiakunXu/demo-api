package com.example.demo.weixin.api.ao;

import lombok.Getter;
import lombok.Setter;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class BaseReturn extends VisitorSupport {

    /**
     * SUCCESS/FAIL
     * 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断.
     */
    private String returnCode;

    /**
     * 返回信息，如非空，为错误原因
     * 签名失败
     * 参数格式校验错误.
     */
    private String returnMsg;

    @Override
    public void visit(Element node) {
        String name = node.getName();
        String text = node.getText();

        if ("return_code".equals(name)) {
            returnCode = text;
        } else if ("return_msg".equals(name)) {
            returnMsg = text;
        }
    }

}
