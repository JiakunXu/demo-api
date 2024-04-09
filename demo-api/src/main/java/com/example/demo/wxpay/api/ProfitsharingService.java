package com.example.demo.wxpay.api;

import com.wechat.pay.java.service.profitsharing.model.*;

import java.util.List;

public interface ProfitsharingService {

    OrdersEntity createOrder(String appid, String transactionId, String outOrderNo,
                             List<CreateOrderReceiver> receivers);

    OrdersEntity createOrder(String subMchid, String appid, String transactionId, String outOrderNo,
                             List<CreateOrderReceiver> receivers);

    OrdersEntity queryOrder(String transactionId, String outOrderNo);

    OrdersEntity queryOrder(String subMchid, String transactionId, String outOrderNo);

    OrdersEntity unfreezeOrder(String transactionId, String outOrderNo, String description);

    OrdersEntity unfreezeOrder(String subMchid, String transactionId, String outOrderNo,
                               String description);

    AddReceiverResponse addReceiver(String appid, ReceiverType type, String account, String name,
                                    ReceiverRelationType relationType);

    AddReceiverResponse addReceiver(String subMchid, String appid, ReceiverType type,
                                    String account, String name, ReceiverRelationType relationType);

}
