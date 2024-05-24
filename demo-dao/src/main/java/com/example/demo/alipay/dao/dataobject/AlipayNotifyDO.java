package com.example.demo.alipay.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class AlipayNotifyDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1715441966296002388L;

    /**
     * 通知校验 id
     */
    private String            notifyId;

    /**
     * 通知的发送时间。格式为 yyyy-mm-dd hh:mm:ss
     */
    private Date              notifyTime;

    /**
     * 通知的类型
     */
    private String            notifyType;

    /**
     * 支付宝分配给开发者的应用 id
     */
    private String            appId;

    /**
     * 编码格式，如 utf-8、gbk、gb2312 等
     */
    private String            charset;

    /**
     * 调用的接口版本，固定为：1.0
     */
    private String            version;

    /**
     * 商家生成签名字符串所使用的签名算法类型，目前支持 rsa2 和 rsa，推荐使用 rsa2
     */
    private String            signType;

    /**
     * 签名。详情可查看 异步返回结果的验签
     */
    private String            sign;

    /**
     * 支付宝交易凭证号
     */
    private String            tradeNo;

    /**
     * 原支付请求的商户订单号
     */
    private String            outTradeNo;

    /**
     * 商户业务 id，主要是退款通知中返回退款申请的流水号
     */
    private String            outBizNo;

    /**
     * 买家支付宝账号对应的支付宝唯一用户号。以 2088 开头的纯 16 位数字
     */
    private String            buyerId;

    /**
     * 买家支付宝账号
     */
    private String            buyerLogonId;

    /**
     * 卖家支付宝用户号
     */
    private String            sellerId;

    /**
     * 卖家支付宝账号
     */
    private String            sellerEmail;

    /**
     * 交易目前所处的状态。详情可查看 交易状态说明
     */
    private String            tradeStatus;

    /**
     * 本次交易支付的订单金额，单位为人民币（元）
     */
    private BigDecimal        totalAmount;

    /**
     * 商家在收益中实际收到的款项，单位人民币（元）
     */
    private BigDecimal        receiptAmount;

    /**
     * 用户在交易中支付的可开发票的金额
     */
    private BigDecimal        invoiceAmount;

    /**
     * 用户在交易中支付的金额
     */
    private BigDecimal        buyerPayAmount;

    /**
     * 使用集分宝支付的金额
     */
    private BigDecimal        pointAmount;

    /**
     * 退款通知中，返回总退款金额，单位为人民币（元），支持两位小数
     */
    private BigDecimal        refundFee;

    /**
     * n商品的标题/交易标题/订单标题/订单关键字等，是请求时对应的参数，原样通知回来
     */
    private String            subject;

    /**
     * 订单的备注、描述、明细等。对应请求时的 body 参数，原样通知回来
     */
    private String            body;

    /**
     * 该笔交易创建的时间。格式为 yyyy-mm-dd hh:mm:ss
     */
    private Date              gmtCreate;

    /**
     * 该笔交易 的买家付款时间。格式为 yyyy-mm-dd hh:mm:ss
     */
    private Date              gmtPayment;

    /**
     * 该笔交易的退款时间。格式 yyyy-mm-dd hh:mm:ss.s
     */
    private Date              gmtRefund;

    /**
     * 该笔交易结束时间。格式为 yyyy-mm-dd hh:mm:ss
     */
    private Date              gmtClose;

    /**
     * 支付成功的各个渠道金额信息。详情可查看 资金明细信息说明
     */
    private String            fundBillList;

    /**
     * 公共回传参数，如果请求时传递了该参数，则返回给商家时会在异步通知时将该参数原样返回。本参数必须进行 urlencode 之后才可以发送给支付宝
     */
    private String            passbackParams;

    /**
     * 本交易支付时所有优惠券信息，详情可查看 优惠券信息说明
     */
    private String            voucherDetailList;

}
