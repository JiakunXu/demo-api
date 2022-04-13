package com.example.demo.wxpay.api.ao;

import org.dom4j.Element;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class TransfersReturn extends Result {

    private Transfers transfers = new Transfers();

    @Override
    public void visit(Element node) {
        super.visit(node);

        String name = node.getName();
        String text = node.getText();

        if ("mch_appid".equals(name)) {
            transfers.setAppid(text);
        } else if ("mchid".equals(name)) {
            transfers.setMchid(text);
        } else if ("device_info".equals(name)) {
            transfers.setDeviceInfo(text);
        } else if ("nonce_str".equals(name)) {
            transfers.setNonceStr(text);
        } else if ("partner_trade_no".equals(name)) {
            transfers.setPartnerTradeNo(text);
        } else if ("payment_no".equals(name)) {
            transfers.setPaymentNo(text);
        } else if ("payment_time".equals(name)) {
            transfers.setPaymentTime(text);
        }
    }

}
