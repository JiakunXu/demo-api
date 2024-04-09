package com.example.demo.wxpay.manager;

import com.example.demo.wxpay.api.ProfitsharingService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.profitsharing.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfitsharingServiceImpl implements ProfitsharingService {

    private static final Logger logger = LoggerFactory.getLogger(ProfitsharingServiceImpl.class);

    @Autowired(required = false)
    private Config              merchantConfig;

    @Autowired(required = false)
    private Config              partnerConfig;

    @Override
    public OrdersEntity createOrder(String appid, String transactionId, String outOrderNo,
                                    List<CreateOrderReceiver> receivers) {
        com.wechat.pay.java.service.profitsharing.ProfitsharingService service = new com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder()
            .config(merchantConfig).build();

        CreateOrderRequest request = new CreateOrderRequest();
        request.setAppid(appid);
        request.setTransactionId(transactionId);
        request.setOutOrderNo(outOrderNo);
        request.setReceivers(receivers);
        request.setUnfreezeUnsplit(Boolean.TRUE);

        try {
            return service.createOrder(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public OrdersEntity createOrder(String subMchid, String appid, String transactionId,
                                    String outOrderNo, List<CreateOrderReceiver> receivers) {
        com.wechat.pay.java.service.profitsharing.ProfitsharingService service = new com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder()
            .config(partnerConfig).build();

        CreateOrderRequest request = new CreateOrderRequest();
        request.setSubMchid(subMchid);
        request.setAppid(appid);
        request.setTransactionId(transactionId);
        request.setOutOrderNo(outOrderNo);
        request.setReceivers(receivers);
        request.setUnfreezeUnsplit(Boolean.TRUE);

        try {
            return service.createOrder(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public OrdersEntity queryOrder(String transactionId, String outOrderNo) {
        com.wechat.pay.java.service.profitsharing.ProfitsharingService service = new com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder()
            .config(merchantConfig).build();

        QueryOrderRequest request = new QueryOrderRequest();
        request.setTransactionId(transactionId);
        request.setOutOrderNo(outOrderNo);

        try {
            return service.queryOrder(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public OrdersEntity queryOrder(String subMchid, String transactionId, String outOrderNo) {
        com.wechat.pay.java.service.profitsharing.ProfitsharingService service = new com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder()
            .config(partnerConfig).build();

        QueryOrderRequest request = new QueryOrderRequest();
        request.setSubMchid(subMchid);
        request.setTransactionId(transactionId);
        request.setOutOrderNo(outOrderNo);

        try {
            return service.queryOrder(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public OrdersEntity unfreezeOrder(String transactionId, String outOrderNo, String description) {
        com.wechat.pay.java.service.profitsharing.ProfitsharingService service = new com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder()
            .config(merchantConfig).build();

        UnfreezeOrderRequest request = new UnfreezeOrderRequest();
        request.setTransactionId(transactionId);
        request.setOutOrderNo(outOrderNo);
        request.setDescription(description);

        try {
            return service.unfreezeOrder(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public OrdersEntity unfreezeOrder(String subMchid, String transactionId, String outOrderNo,
                                      String description) {
        com.wechat.pay.java.service.profitsharing.ProfitsharingService service = new com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder()
            .config(partnerConfig).build();

        UnfreezeOrderRequest request = new UnfreezeOrderRequest();
        request.setSubMchid(subMchid);
        request.setTransactionId(transactionId);
        request.setOutOrderNo(outOrderNo);
        request.setDescription(description);

        try {
            return service.unfreezeOrder(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AddReceiverResponse addReceiver(String appid, ReceiverType type, String account,
                                           String name, ReceiverRelationType relationType) {
        com.wechat.pay.java.service.profitsharing.ProfitsharingService service = new com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder()
            .config(merchantConfig).build();

        AddReceiverRequest request = new AddReceiverRequest();
        request.setAppid(appid);
        request.setType(type);
        request.setAccount(account);
        request.setName(name);
        request.setRelationType(relationType);

        try {
            return service.addReceiver(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public AddReceiverResponse addReceiver(String subMchid, String appid, ReceiverType type,
                                           String account, String name,
                                           ReceiverRelationType relationType) {
        com.wechat.pay.java.service.profitsharing.ProfitsharingService service = new com.wechat.pay.java.service.profitsharing.ProfitsharingService.Builder()
            .config(partnerConfig).build();

        AddReceiverRequest request = new AddReceiverRequest();
        request.setSubMchid(subMchid);
        request.setAppid(appid);
        request.setType(type);
        request.setAccount(account);
        request.setName(name);
        request.setRelationType(relationType);

        try {
            return service.addReceiver(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
