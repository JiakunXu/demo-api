package com.example.demo.api.weixin.ao;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class UnifiedOrder implements Serializable {

    private static final long serialVersionUID = -3810572764622456769L;

    /**
     * 微信分配的小程序ID.
     * 必填.
     */
    private String            appId;

    /**
     * 微信支付分配的商户号.
     * 必填.
     */
    private String            mchId;

    /**
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB".
     */
    private String            deviceInfo;

    /**
     * 随机字符串，长度要求在32位以内.
     * 必填.
     */
    private String            nonceStr;

    /**
     * 必填.
     */
    private String            sign;

    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5.
     */
    private String            signType;

    /**
     * 商品描述.
     * 必填.
     */
    private String            body;

    /**
     * 商品详情.
     */
    private String            detail;

    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用.
     */
    private String            attach;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一.
     * 必填.
     */
    private String            outTradeNo;

    /**
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY.
     */
    private String            feeType;

    /**
     * 订单总金额，单位为分.
     * 必填.
     */
    private int               totalFee;

    /**
     * 支持IPV4和IPV6两种格式的IP地址。调用微信支付API的机器IP.
     * 必填.
     */
    private String            spbillCreateIp;

    /**
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010.
     */
    private String            timeStart;

    /**
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id.
     */
    private String            timeExpire;

    /**
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数.
     */
    private String            goodsTag;

    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数.
     * 必填.
     */
    private String            notifyUrl;

    /**
     * 小程序取值如下：JSAPI.
     * 必填.
     */
    private String            tradeType;

    /**
     * trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义.
     */
    private String            productId;

    /**
     * 上传此参数no_credit--可限制用户不能使用信用卡支付.
     */
    private String            limitPay;

    /**
     * trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识.
     */
    private String            openId;

    /**
     * Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效.
     */
    private String            receipt;

}
