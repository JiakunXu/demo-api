package com.example.demo.api.wxpay.ao;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Transfers {

    /**
     * 商户账号appid.
     */
    private String  appid;

    /**
     * 商户号.
     */
    private String  mchid;

    /**
     * 设备号.
     */
    private String  deviceInfo;

    /**
     * 随机字符串.
     */
    private String  nonceStr;

    /**
     * 签名.
     */
    private String  sign;

    /**
     * 商户订单号.
     */
    private String  partnerTradeNo;

    /**
     * 用户openid.
     */
    private String  openid;

    /**
     * 校验用户姓名选项
     * NO_CHECK：不校验真实姓名
     * FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账）
     * OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）.
     */
    private String  checkName;

    /**
     * 收款用户姓名
     * 收款用户真实姓名。
     * 如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名.
     */
    private String  reUserName;

    /**
     * 金额
     * 企业付款金额，单位为分.
     */
    private Integer amount;

    /**
     * 企业付款描述信息
     * 必填.
     */
    private String  desc;

    /**
     * Ip地址
     * 调用接口的机器Ip地址.
     */
    private String  spbillCreateIp;

    // >>>>>>>>>>以下是增强属性<<<<<<<<<<

    /**
     * 微信订单号
     * 企业付款成功，返回的微信订单号.
     */
    private String  paymentNo;

    /**
     * 微信支付成功时间
     * 企业付款成功时间.
     */
    private String  paymentTime;

}
