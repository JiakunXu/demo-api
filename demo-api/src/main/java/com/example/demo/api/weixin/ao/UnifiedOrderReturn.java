package com.example.demo.api.weixin.ao;

import lombok.Getter;
import lombok.Setter;
import org.dom4j.Element;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class UnifiedOrderReturn extends BaseReturn {

    /**
     * 调用接口提交的小程序ID.
     */
    private String appId;

    /**
     * 调用接口提交的商户号.
     */
    private String mchId;

    /**
     * 自定义参数，可以为请求支付的终端设备号等.
     */
    private String deviceInfo;

    /**
     * 微信返回的随机字符串.
     */
    private String nonceStr;

    /**
     * 微信返回的签名值.
     */
    private String sign;

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

    /**
     * 交易类型，取值为：JSAPI，NATIVE，APP等.
     */
    private String tradeType;

    /**
     * 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时.
     */
    private String prepayId;

    /**
     * trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付.
     */
    private String codeUrl;

    @Override
    public void visit(Element node) {
        super.visit(node);

        String name = node.getName();
        String text = node.getText();

        if ("appid".equals(name)) {
            appId = text;
        } else if ("mch_id".equals(name)) {
            mchId = text;
        } else if ("device_info".equals(name)) {
            deviceInfo = text;
        } else if ("nonce_str".equals(name)) {
            nonceStr = text;
        } else if ("sign".equals(name)) {
            sign = text;
        } else if ("result_code".equals(name)) {
            resultCode = text;
        } else if ("err_code".equals(name)) {
            errCode = text;
        } else if ("err_code_des".equals(name)) {
            errCodeDes = text;
        } else if ("trade_type".equals(name)) {
            tradeType = text;
        } else if ("prepay_id".equals(name)) {
            prepayId = text;
        } else if ("code_url".equals(name)) {
            codeUrl = text;
        }
    }

}
