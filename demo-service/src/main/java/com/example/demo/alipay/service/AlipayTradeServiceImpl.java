package com.example.demo.alipay.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.example.demo.alipay.api.AlipayTradeService;
import com.example.demo.alipay.api.TradeService;
import com.example.demo.alipay.api.bo.AlipayTrade;
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
public class AlipayTradeServiceImpl implements AlipayTradeService {

    private static final Logger logger = LoggerFactory.getLogger(AlipayTradeServiceImpl.class);

    @Autowired
    private TradeService        tradeService;

    @Autowired
    private AlipayNotifyMapper  alipayNotifyMapper;

    @Override
    public AlipayTrade getTrade(Map<String, String> parameters) {
        AlipayTrade alipayTrade = new AlipayTrade();
        alipayTrade.setNotifyTime(getDate(parameters.get("notify_time")));
        alipayTrade.setNotifyType(parameters.get("notify_type"));
        alipayTrade.setNotifyId(parameters.get("notify_id"));
        alipayTrade.setAppId(parameters.get("app_id"));
        alipayTrade.setCharset(parameters.get("charset"));
        alipayTrade.setVersion(parameters.get("version"));
        alipayTrade.setSignType(parameters.get("sign_type"));
        alipayTrade.setSign(parameters.get("sign"));
        alipayTrade.setTradeNo(parameters.get("trade_no"));
        alipayTrade.setOutTradeNo(parameters.get("out_trade_no"));
        alipayTrade.setOutBizNo(parameters.get("out_biz_no"));
        alipayTrade.setBuyerId(parameters.get("buyer_id"));
        alipayTrade.setBuyerLogonId(parameters.get("buyer_logon_id"));
        alipayTrade.setSellerId(parameters.get("seller_id"));
        alipayTrade.setSellerEmail(parameters.get("seller_email"));
        alipayTrade.setTradeStatus(parameters.get("trade_status"));
        alipayTrade.setTotalAmount(getBigDecimal(parameters.get("total_amount")));
        alipayTrade.setReceiptAmount(getBigDecimal(parameters.get("receipt_amount")));
        alipayTrade.setInvoiceAmount(getBigDecimal(parameters.get("invoice_amount")));
        alipayTrade.setBuyerPayAmount(getBigDecimal(parameters.get("buyer_pay_amount")));
        alipayTrade.setPointAmount(getBigDecimal(parameters.get("point_amount")));
        alipayTrade.setRefundFee(getBigDecimal(parameters.get("refund_fee")));
        alipayTrade.setSubject(parameters.get("subject"));
        alipayTrade.setBody(parameters.get("body"));
        alipayTrade.setGmtCreate(getDate(parameters.get("gmt_create")));
        alipayTrade.setGmtPayment(getDate(parameters.get("gmt_payment")));
        alipayTrade.setGmtRefund(getDate(parameters.get("gmt_refund")));
        alipayTrade.setGmtClose(getDate(parameters.get("gmt_close")));
        alipayTrade.setFundBillList(parameters.get("fund_bill_list"));
        alipayTrade.setPassbackParams(parameters.get("passback_params"));
        alipayTrade.setVoucherDetailList(parameters.get("voucher_detail_list"));

        try {
            if (!Factory.Payment.Common().verifyNotify(parameters)) {
                throw new RuntimeException("验签失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return alipayTrade;
    }

    @Override
    public AlipayTrade getTrade(String appAuthToken, String outTradeNo) {
        JSONObject data = JSON.parseObject(tradeService.query(appAuthToken, outTradeNo));

        AlipayTrade alipayTrade = new AlipayTrade();
        alipayTrade.setNotifyId(UUID.randomUUID().toString());
        alipayTrade.setSign(data.getString("sign"));
        alipayTrade
            .setTradeNo(data.getJSONObject("alipay_trade_query_response").getString("trade_no"));
        alipayTrade.setOutTradeNo(
            data.getJSONObject("alipay_trade_query_response").getString("out_trade_no"));
        alipayTrade.setBuyerId(
            data.getJSONObject("alipay_trade_query_response").getString("buyer_user_id"));
        alipayTrade.setBuyerLogonId(
            data.getJSONObject("alipay_trade_query_response").getString("buyer_logon_id"));
        alipayTrade.setTradeStatus(
            data.getJSONObject("alipay_trade_query_response").getString("trade_status"));
        alipayTrade.setTotalAmount(getBigDecimal(
            data.getJSONObject("alipay_trade_query_response").getString("total_amount")));
        alipayTrade.setGmtPayment(
            getDate(data.getJSONObject("alipay_trade_query_response").getString("send_pay_date"))); // TODO

        return alipayTrade;
    }

    @Override
    public AlipayTrade insertTrade(AlipayTrade trade) {
        if (trade == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        AlipayNotifyDO alipayNotifyDO = BeanUtil.copy(trade, AlipayNotifyDO.class);

        try {
            alipayNotifyMapper.insert(alipayNotifyDO);
        } catch (Exception e) {
            logger.error(alipayNotifyDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return trade;
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
