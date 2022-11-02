package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.example.demo.alipay.api.AlipayNotifyService;
import com.example.demo.alipay.api.AlipayTradeService;
import com.example.demo.alipay.api.bo.AlipayNotify;
import com.example.demo.alipay.dao.dataobject.AlipayNotifyDO;
import com.example.demo.alipay.dao.mapper.AlipayNotifyMapper;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.framework.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class AlipayNotifyServiceImpl implements AlipayNotifyService {

    private static final Logger logger = LoggerFactory.getLogger(AlipayNotifyServiceImpl.class);

    @Autowired
    private AlipayTradeService  alipayTradeService;

    @Autowired
    private AlipayNotifyMapper  alipayNotifyMapper;

    @Override
    public AlipayNotify getAlipayNotify(Map<String, String> parameters) {
        AlipayNotify alipayNotify = new AlipayNotify();
        alipayNotify.setNotifyTime(getDate(parameters.get("notify_time")));
        alipayNotify.setNotifyType(parameters.get("notify_type"));
        alipayNotify.setNotifyId(parameters.get("notify_id"));
        alipayNotify.setAppId(parameters.get("app_id"));
        alipayNotify.setCharset(parameters.get("charset"));
        alipayNotify.setVersion(parameters.get("version"));
        alipayNotify.setSignType(parameters.get("sign_type"));
        alipayNotify.setSign(parameters.get("sign"));
        alipayNotify.setTradeNo(parameters.get("trade_no"));
        alipayNotify.setOutTradeNo(parameters.get("out_trade_no"));
        alipayNotify.setOutBizNo(parameters.get("out_biz_no"));
        alipayNotify.setBuyerId(parameters.get("buyer_id"));
        alipayNotify.setBuyerLogonId(parameters.get("buyer_logon_id"));
        alipayNotify.setSellerId(parameters.get("seller_id"));
        alipayNotify.setSellerEmail(parameters.get("seller_email"));
        alipayNotify.setTradeStatus(parameters.get("trade_status"));
        alipayNotify.setTotalAmount(getBigDecimal(parameters.get("total_amount")));
        alipayNotify.setReceiptAmount(getBigDecimal(parameters.get("receipt_amount")));
        alipayNotify.setInvoiceAmount(getBigDecimal(parameters.get("invoice_amount")));
        alipayNotify.setBuyerPayAmount(getBigDecimal(parameters.get("buyer_pay_amount")));
        alipayNotify.setPointAmount(getBigDecimal(parameters.get("point_amount")));
        alipayNotify.setRefundFee(getBigDecimal(parameters.get("refund_fee")));
        alipayNotify.setSubject(parameters.get("subject"));
        alipayNotify.setBody(parameters.get("body"));
        alipayNotify.setGmtCreate(getDate(parameters.get("gmt_create")));
        alipayNotify.setGmtPayment(getDate(parameters.get("gmt_payment")));
        alipayNotify.setGmtRefund(getDate(parameters.get("gmt_refund")));
        alipayNotify.setGmtClose(getDate(parameters.get("gmt_close")));
        alipayNotify.setFundBillList(parameters.get("fund_bill_list"));
        alipayNotify.setPassbackParams(parameters.get("passback_params"));
        alipayNotify.setVoucherDetailList(parameters.get("voucher_detail_list"));

        try {
            if (!Factory.Payment.Common().verifyNotify(parameters)) {
                throw new RuntimeException("验签失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return alipayNotify;
    }

    @Override
    public AlipayNotify getAlipayNotify(String appAuthToken, String outTradeNo) {
        JSONObject data = JSON.parseObject(alipayTradeService.query(appAuthToken, outTradeNo),
            JSONObject.class);

        AlipayNotify alipayNotify = new AlipayNotify();
        alipayNotify.setNotifyId(UUID.randomUUID().toString());
        alipayNotify.setSign(data.getString("sign"));
        alipayNotify
            .setTradeNo(data.getJSONObject("alipay_trade_query_response").getString("trade_no"));
        alipayNotify.setOutTradeNo(
            data.getJSONObject("alipay_trade_query_response").getString("out_trade_no"));
        alipayNotify.setBuyerId(
            data.getJSONObject("alipay_trade_query_response").getString("buyer_user_id"));
        alipayNotify.setBuyerLogonId(
            data.getJSONObject("alipay_trade_query_response").getString("buyer_logon_id"));
        alipayNotify.setTradeStatus(
            data.getJSONObject("alipay_trade_query_response").getString("trade_status"));
        alipayNotify.setTotalAmount(getBigDecimal(
            data.getJSONObject("alipay_trade_query_response").getString("total_amount")));
        alipayNotify.setGmtPayment(
            getDate(data.getJSONObject("alipay_trade_query_response").getString("send_pay_date"))); // TODO

        return alipayNotify;
    }

    @Override
    public AlipayNotify insertAlipayNotify(AlipayNotify alipayNotify) {
        if (alipayNotify == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        AlipayNotifyDO alipayNotifyDO = BeanUtil.copy(alipayNotify, AlipayNotifyDO.class);

        try {
            alipayNotifyMapper.insert(alipayNotifyDO);
        } catch (Exception e) {
            logger.error(alipayNotifyDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return alipayNotify;
    }

    private Date getDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }

        return DateUtil.getDateTime(date);
    }

    private BigDecimal getBigDecimal(String val) {
        if (StringUtils.isBlank(val)) {
            return null;
        }

        return new BigDecimal(val);
    }

}
