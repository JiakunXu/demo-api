package com.example.demo.alipay.service;

import com.alibaba.fastjson2.JSON;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.example.demo.alipay.api.AlipayRefundService;
import com.example.demo.alipay.api.AlipayService;
import com.example.demo.alipay.api.bo.AlipayRefund;
import com.example.demo.alipay.dao.dataobject.AlipayRefundDO;
import com.example.demo.alipay.dao.mapper.AlipayRefundMapper;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class AlipayRefundServiceImpl extends ServiceImpl<AlipayRefundMapper, AlipayRefundDO>
                                     implements AlipayRefundService {

    @Autowired
    private AlipayService alipayService;

    @Override
    public AlipayRefund getRefund(AlipayTradeRefundResponse response) {
        if (response == null) {
            return null;
        }

        AlipayRefund refund = new AlipayRefund();
        refund.setTradeNo(response.getTradeNo());
        refund.setOutTradeNo(response.getOutTradeNo());
        refund.setFundChange(response.getFundChange());
        refund.setGmtRefundPay(response.getGmtRefundPay());

        return refund;
    }

    @Override
    public AlipayRefund getRefund(AlipayTradeFastpayRefundQueryResponse response) {
        if (response == null) {
            return null;
        }

        AlipayRefund refund = new AlipayRefund();
        refund.setTradeNo(response.getTradeNo());
        refund.setOutTradeNo(response.getOutTradeNo());
        refund.setOutRequestNo(response.getOutRequestNo());
        refund.setTotalAmount(response.getTotalAmount());
        refund.setRefundAmount(response.getRefundAmount());
        refund.setRefundStatus(response.getRefundStatus());
        refund.setGmtRefundPay(response.getGmtRefundPay());
        refund.setSendBackFee(response.getSendBackFee());

        return refund;
    }

    @Override
    public AlipayRefund getRefund(String outTradeNo, String outRequestNo) {
        return getRefund(alipayService.queryRefund(outTradeNo, outRequestNo));
    }

    @Override
    public AlipayRefund getRefund(Map<String, String> parameters) {
        try {
            if (!Factory.Payment.Common().verifyNotify(parameters)) {
                throw new RuntimeException("验签失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        AlipayRefund refund = new AlipayRefund();

        return refund;
    }

    @Override
    public AlipayRefund insertRefund(@NotNull AlipayRefund refund) {
        AlipayRefundDO refundDO = new AlipayRefundDO();
        refundDO.setOutTradeNo(refund.getOutTradeNo());
        refundDO.setOutRequestNo(refund.getOutRequestNo());
        refundDO.setRefund(JSON.toJSONString(refund));
        refundDO.setCreator("sys");

        try {
            this.insert(refundDO);
        } catch (Exception e) {
            log.error("{}", refundDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return refund;
    }

}
