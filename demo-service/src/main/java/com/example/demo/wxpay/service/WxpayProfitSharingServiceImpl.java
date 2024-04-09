package com.example.demo.wxpay.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.wxpay.api.ProfitsharingService;
import com.example.demo.wxpay.api.WxpayProfitSharingService;
import com.example.demo.wxpay.api.bo.WxpayProfitSharing;
import com.example.demo.wxpay.dao.dataobject.WxpayProfitSharingDO;
import com.example.demo.wxpay.dao.mapper.WxpayProfitSharingMapper;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.profitsharing.model.OrdersEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WxpayProfitSharingServiceImpl implements WxpayProfitSharingService {

    private static final Logger      logger = LoggerFactory
        .getLogger(WxpayProfitSharingServiceImpl.class);

    @Autowired(required = false)
    private RSAAutoCertificateConfig partnerConfig;

    @Autowired
    private ProfitsharingService     profitsharingService;

    @Autowired
    private WxpayProfitSharingMapper wxpayProfitSharingMapper;

    @Override
    public WxpayProfitSharing getWxpayProfitSharing(String transactionId, String outOrderNo) {
        if (StringUtils.isAnyBlank(transactionId, outOrderNo)) {
            return null;
        }

        OrdersEntity orders = profitsharingService.queryOrder(transactionId, outOrderNo);

        WxpayProfitSharing wxpayProfitSharing = new WxpayProfitSharing();
        wxpayProfitSharing.setOrderId(orders.getOrderId());
        wxpayProfitSharing.setTransactionId(orders.getTransactionId());
        wxpayProfitSharing.setOutOrderNo(orders.getOutOrderNo());
        wxpayProfitSharing.setState(orders.getState().name());
        wxpayProfitSharing.setReceivers(JSON.toJSONString(orders.getReceivers()));

        return wxpayProfitSharing;
    }

    @Override
    public WxpayProfitSharing getWxpayProfitSharing(String subMchid, String transactionId,
                                                    String outOrderNo) {
        if (StringUtils.isAnyBlank(subMchid, transactionId, outOrderNo)) {
            return null;
        }

        OrdersEntity orders = profitsharingService.queryOrder(subMchid, transactionId, outOrderNo);

        WxpayProfitSharing wxpayProfitSharing = new WxpayProfitSharing();
        wxpayProfitSharing.setOrderId(orders.getOrderId());
        wxpayProfitSharing.setSubMchid(orders.getSubMchid());
        wxpayProfitSharing.setTransactionId(orders.getTransactionId());
        wxpayProfitSharing.setOutOrderNo(orders.getOutOrderNo());
        wxpayProfitSharing.setState(orders.getState().name());
        wxpayProfitSharing.setReceivers(JSON.toJSONString(orders.getReceivers()));

        return wxpayProfitSharing;
    }

    @Override
    public WxpayProfitSharing getWxpayProfitSharing(String serialNumber, String nonce,
                                                    String timestamp, String signature,
                                                    String body) {
        RequestParam requestParam = new RequestParam.Builder().serialNumber(serialNumber)
            .nonce(nonce).signature(signature).timestamp(timestamp).body(body).build();

        Map<?, ?> map = new NotificationParser(partnerConfig).parse(requestParam, Map.class);

        WxpayProfitSharing wxpayProfitSharing = new WxpayProfitSharing();
        wxpayProfitSharing.setOrderId(map.get("order_id").toString());
        wxpayProfitSharing.setMchid(map.containsKey("mchid") ? map.get("mchid").toString() : null);
        wxpayProfitSharing
            .setSpMchid(map.containsKey("sp_mchid") ? map.get("sp_mchid").toString() : null);
        wxpayProfitSharing
            .setSubMchid(map.containsKey("sub_mchid") ? map.get("sub_mchid").toString() : null);
        wxpayProfitSharing.setTransactionId(map.get("transaction_id").toString());
        wxpayProfitSharing.setOutOrderNo(map.get("out_order_no").toString());
        wxpayProfitSharing.setState(WxpayProfitSharing.State.FINISHED.value);
        wxpayProfitSharing.setReceivers(map.get("receiver").toString());
        wxpayProfitSharing.setSuccessTime(map.get("success_time").toString());

        return wxpayProfitSharing;
    }

    @Override
    public WxpayProfitSharing insertWxpayProfitSharing(WxpayProfitSharing wxpayProfitSharing) {
        if (wxpayProfitSharing == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        WxpayProfitSharingDO wxpayProfitSharingDO = BeanUtil.copy(wxpayProfitSharing,
            WxpayProfitSharingDO.class);

        try {
            wxpayProfitSharingMapper.insert(wxpayProfitSharingDO);
        } catch (Exception e) {
            logger.error(wxpayProfitSharingDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return wxpayProfitSharing;
    }

    @Override
    public WxpayProfitSharing updateWxpayProfitSharing(String orderId,
                                                       WxpayProfitSharing wxpayProfitSharing) {
        if (StringUtils.isBlank(orderId) || wxpayProfitSharing == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        wxpayProfitSharing.setOrderId(orderId);

        WxpayProfitSharingDO wxpayProfitSharingDO = BeanUtil.copy(wxpayProfitSharing,
            WxpayProfitSharingDO.class);

        try {
            if (wxpayProfitSharingMapper.update(wxpayProfitSharingDO) == 0) {
                wxpayProfitSharingMapper.insert(wxpayProfitSharingDO);
            }
        } catch (Exception e) {
            logger.error(wxpayProfitSharingDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return wxpayProfitSharing;
    }

}
