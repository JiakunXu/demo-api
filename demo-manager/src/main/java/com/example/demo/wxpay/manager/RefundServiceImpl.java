package com.example.demo.wxpay.manager;

import com.example.demo.wxpay.api.RefundService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;
import com.wechat.pay.java.service.refund.model.QueryByOutRefundNoRequest;
import com.wechat.pay.java.service.refund.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefundServiceImpl implements RefundService {

    @Autowired(required = false)
    private Config merchantConfig;

    @Autowired(required = false)
    private Config partnerConfig;

    @Override
    public Refund create(String outTradeNo, String outRefundNo, String reason, String notifyUrl,
                         Long refund, Long total) {
        com.wechat.pay.java.service.refund.RefundService service = new com.wechat.pay.java.service.refund.RefundService.Builder()
            .config(merchantConfig).build();

        CreateRequest request = new CreateRequest();
        request.setOutTradeNo(outTradeNo);
        request.setOutRefundNo(outRefundNo);
        request.setReason(reason);
        request.setNotifyUrl(notifyUrl);

        AmountReq amount = new AmountReq();
        amount.setRefund(refund);
        amount.setTotal(total);
        amount.setCurrency("CNY");

        request.setAmount(amount);

        try {
            return service.create(request);
        } catch (HttpException e) {
            log.error("{}", request, e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Refund create(String subMchid, String outTradeNo, String outRefundNo, String reason,
                         String notifyUrl, Long refund, Long total) {
        com.wechat.pay.java.service.refund.RefundService service = new com.wechat.pay.java.service.refund.RefundService.Builder()
            .config(partnerConfig).build();

        CreateRequest request = new CreateRequest();
        request.setSubMchid(subMchid);
        request.setOutTradeNo(outTradeNo);
        request.setOutRefundNo(outRefundNo);
        request.setReason(reason);
        request.setNotifyUrl(notifyUrl);

        AmountReq amount = new AmountReq();
        amount.setRefund(refund);
        amount.setTotal(total);
        amount.setCurrency("CNY");

        request.setAmount(amount);

        try {
            return service.create(request);
        } catch (HttpException e) {
            log.error("{}", request, e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Refund queryByOutRefundNo(String outRefundNo) {
        com.wechat.pay.java.service.refund.RefundService service = new com.wechat.pay.java.service.refund.RefundService.Builder()
            .config(merchantConfig).build();

        QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
        request.setOutRefundNo(outRefundNo);

        try {
            return service.queryByOutRefundNo(request);
        } catch (HttpException e) {
            log.error("{}", request, e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Refund queryByOutRefundNo(String subMchid, String outRefundNo) {
        com.wechat.pay.java.service.refund.RefundService service = new com.wechat.pay.java.service.refund.RefundService.Builder()
            .config(partnerConfig).build();

        QueryByOutRefundNoRequest request = new QueryByOutRefundNoRequest();
        request.setSubMchid(subMchid);
        request.setOutRefundNo(outRefundNo);

        try {
            return service.queryByOutRefundNo(request);
        } catch (HttpException e) {
            log.error("{}", request, e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
