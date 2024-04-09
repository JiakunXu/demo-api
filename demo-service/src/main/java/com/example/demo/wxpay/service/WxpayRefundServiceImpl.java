package com.example.demo.wxpay.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.wxpay.api.RefundService;
import com.example.demo.wxpay.api.WxpayRefundService;
import com.example.demo.wxpay.api.bo.WxpayRefund;
import com.example.demo.wxpay.dao.dataobject.WxpayRefundDO;
import com.example.demo.wxpay.dao.mapper.WxpayRefundMapper;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WxpayRefundServiceImpl implements WxpayRefundService {

    private static final Logger      logger = LoggerFactory.getLogger(WxpayRefundServiceImpl.class);

    @Autowired(required = false)
    private RSAAutoCertificateConfig merchantConfig;

    @Autowired(required = false)
    private RSAAutoCertificateConfig partnerConfig;

    @Autowired
    private RefundService            refundService;

    @Autowired
    private WxpayRefundMapper        wxpayRefundMapper;

    @Value("${wxpay.merchant.serialNumber}")
    private String                   serialNumber;

    @Value("${wxpay.partner.merchant.serialNumber}")
    private String                   partnerSerialNumber;

    @Override
    public WxpayRefund getWxpayRefund(String outRefundNo) {
        if (StringUtils.isBlank(outRefundNo)) {
            return null;
        }

        Refund refund = refundService.queryByOutRefundNo(outRefundNo);

        WxpayRefund wxpayRefund = new WxpayRefund();
        wxpayRefund.setRefundId(refund.getRefundId());
        wxpayRefund.setOutRefundNo(refund.getOutRefundNo());
        wxpayRefund.setTransactionId(refund.getTransactionId());
        wxpayRefund.setOutTradeNo(refund.getOutTradeNo());
        wxpayRefund.setChannel(refund.getChannel() == null ? null : refund.getChannel().name());
        wxpayRefund.setUserReceivedAccount(refund.getUserReceivedAccount());
        wxpayRefund.setSuccessTime(refund.getSuccessTime());
        wxpayRefund.setCreateTime(refund.getCreateTime());
        wxpayRefund.setStatus(refund.getStatus().name());
        wxpayRefund.setFundsAccount(
            refund.getFundsAccount() == null ? null : refund.getFundsAccount().name());
        wxpayRefund.setAmount(JSON.toJSONString(refund.getAmount()));
        wxpayRefund.setPromotionDetail(JSON.toJSONString(refund.getPromotionDetail()));

        return wxpayRefund;
    }

    @Override
    public WxpayRefund getWxpayRefund(String subMchid, String outRefundNo) {
        if (StringUtils.isAnyBlank(subMchid, outRefundNo)) {
            return null;
        }

        Refund refund = refundService.queryByOutRefundNo(subMchid, outRefundNo);

        WxpayRefund wxpayRefund = new WxpayRefund();
        wxpayRefund.setRefundId(refund.getRefundId());
        wxpayRefund.setOutRefundNo(refund.getOutRefundNo());
        wxpayRefund.setTransactionId(refund.getTransactionId());
        wxpayRefund.setOutTradeNo(refund.getOutTradeNo());
        wxpayRefund.setChannel(refund.getChannel() == null ? null : refund.getChannel().name());
        wxpayRefund.setUserReceivedAccount(refund.getUserReceivedAccount());
        wxpayRefund.setSuccessTime(refund.getSuccessTime());
        wxpayRefund.setCreateTime(refund.getCreateTime());
        wxpayRefund.setStatus(refund.getStatus().name());
        wxpayRefund.setFundsAccount(
            refund.getFundsAccount() == null ? null : refund.getFundsAccount().name());
        wxpayRefund.setAmount(JSON.toJSONString(refund.getAmount()));
        wxpayRefund.setPromotionDetail(JSON.toJSONString(refund.getPromotionDetail()));

        return wxpayRefund;
    }

    @Override
    public WxpayRefund getWxpayRefundV1(String serialNumber, String nonce, String timestamp,
                                        String signature, String body) {
        RequestParam requestParam = new RequestParam.Builder().serialNumber(serialNumber)
            .nonce(nonce).signature(signature).timestamp(timestamp).body(body).build();

        RefundNotification refund = new NotificationParser(merchantConfig).parse(requestParam,
            RefundNotification.class);

        WxpayRefund wxpayRefund = new WxpayRefund();
        wxpayRefund.setRefundId(refund.getRefundId());
        wxpayRefund.setOutRefundNo(refund.getOutRefundNo());
        wxpayRefund.setTransactionId(refund.getTransactionId());
        wxpayRefund.setOutTradeNo(refund.getOutTradeNo());
        wxpayRefund.setChannel(refund.getChannel() == null ? null : refund.getChannel().name());
        wxpayRefund.setUserReceivedAccount(refund.getUserReceivedAccount());
        wxpayRefund.setSuccessTime(refund.getSuccessTime());
        wxpayRefund.setCreateTime(refund.getCreateTime());
        wxpayRefund.setStatus(refund.getRefundStatus().name());
        wxpayRefund.setFundsAccount(
            refund.getFundsAccount() == null ? null : refund.getFundsAccount().name());
        wxpayRefund.setAmount(JSON.toJSONString(refund.getAmount()));
        wxpayRefund.setPromotionDetail(JSON.toJSONString(refund.getPromotionDetail()));

        return wxpayRefund;
    }

    @Override
    public WxpayRefund getWxpayRefundV2(String serialNumber, String nonce, String timestamp,
                                        String signature, String body) {
        RequestParam requestParam = new RequestParam.Builder().serialNumber(serialNumber)
            .nonce(nonce).signature(signature).timestamp(timestamp).body(body).build();

        RefundNotification refund = new NotificationParser(partnerConfig).parse(requestParam,
            RefundNotification.class);

        WxpayRefund wxpayRefund = new WxpayRefund();
        wxpayRefund.setRefundId(refund.getRefundId());
        wxpayRefund.setOutRefundNo(refund.getOutRefundNo());
        wxpayRefund.setTransactionId(refund.getTransactionId());
        wxpayRefund.setOutTradeNo(refund.getOutTradeNo());
        wxpayRefund.setChannel(refund.getChannel() == null ? null : refund.getChannel().name());
        wxpayRefund.setUserReceivedAccount(refund.getUserReceivedAccount());
        wxpayRefund.setSuccessTime(refund.getSuccessTime());
        wxpayRefund.setCreateTime(refund.getCreateTime());
        wxpayRefund.setStatus(refund.getRefundStatus().name());
        wxpayRefund.setFundsAccount(
            refund.getFundsAccount() == null ? null : refund.getFundsAccount().name());
        wxpayRefund.setAmount(JSON.toJSONString(refund.getAmount()));
        wxpayRefund.setPromotionDetail(JSON.toJSONString(refund.getPromotionDetail()));

        return wxpayRefund;
    }

    @Override
    public WxpayRefund insertWxpayRefund(WxpayRefund wxpayRefund) {
        if (wxpayRefund == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        WxpayRefundDO wxpayRefundDO = BeanUtil.copy(wxpayRefund, WxpayRefundDO.class);

        try {
            wxpayRefundMapper.insert(wxpayRefundDO);
        } catch (Exception e) {
            logger.error(wxpayRefundDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return wxpayRefund;
    }

    @Override
    public WxpayRefund updateWxpayRefund(String refundId, WxpayRefund wxpayRefund) {
        if (StringUtils.isBlank(refundId) || wxpayRefund == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        wxpayRefund.setRefundId(refundId);

        WxpayRefundDO wxpayRefundDO = BeanUtil.copy(wxpayRefund, WxpayRefundDO.class);

        try {
            if (wxpayRefundMapper.update(wxpayRefundDO) == 0) {
                wxpayRefundMapper.insert(wxpayRefundDO);
            }
        } catch (Exception e) {
            logger.error(wxpayRefundDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return wxpayRefund;
    }

}
