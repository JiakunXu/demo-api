package com.example.demo.wxpay.service.impl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.wxpay.api.UnifiedOrderService;
import com.example.demo.wxpay.api.ao.UnifiedOrder;
import com.example.demo.wxpay.api.ao.UnifiedOrderReturn;
import com.example.demo.framework.util.EncryptUtil;
import com.example.demo.framework.util.HttpUtil;

/**
 * @author JiakunXu
 */
@Service
public class UnifiedOrderServiceImpl implements UnifiedOrderService {

    private static final Logger logger = LoggerFactory.getLogger(UnifiedOrderServiceImpl.class);

    @Override
    public String getPrepayId(UnifiedOrder unifiedOrder, String key) throws RuntimeException {
        if (unifiedOrder == null) {
            throw new RuntimeException("unifiedOrder 统一下单 不能为空.");
        }

        StringBuilder sign = new StringBuilder();

        sign.append("appid=").append(unifiedOrder.getAppId());
        if (StringUtils.isNotBlank(unifiedOrder.getAttach())) {
            sign.append("&attach=").append(unifiedOrder.getAttach());
        }
        sign.append("&body=").append(unifiedOrder.getBody());
        if (StringUtils.isNotBlank(unifiedOrder.getDetail())) {
            sign.append("&detail=").append(unifiedOrder.getDetail());
        }
        if (StringUtils.isNotBlank(unifiedOrder.getDeviceInfo())) {
            sign.append("&device_info=").append(unifiedOrder.getDeviceInfo());
        }
        if (StringUtils.isNotBlank(unifiedOrder.getFeeType())) {
            sign.append("&fee_type=").append(unifiedOrder.getFeeType());
        }
        if (StringUtils.isNotBlank(unifiedOrder.getGoodsTag())) {
            sign.append("&goods_tag=").append(unifiedOrder.getGoodsTag());
        }
        if (StringUtils.isNotBlank(unifiedOrder.getLimitPay())) {
            sign.append("&limit_pay=").append(unifiedOrder.getLimitPay());
        }
        sign.append("&mch_id=").append(unifiedOrder.getMchId());
        sign.append("&nonce_str=").append(unifiedOrder.getNonceStr());
        sign.append("&notify_url=").append(unifiedOrder.getNotifyUrl());
        if (StringUtils.isNotBlank(unifiedOrder.getOpenId())) {
            sign.append("&openid=").append(unifiedOrder.getOpenId());
        }
        sign.append("&out_trade_no=").append(unifiedOrder.getOutTradeNo());
        if (StringUtils.isNotBlank(unifiedOrder.getProductId())) {
            sign.append("&product_id=").append(unifiedOrder.getProductId());
        }
        if (StringUtils.isNotBlank(unifiedOrder.getReceipt())) {
            sign.append("&receipt=").append(unifiedOrder.getReceipt());
        }
        sign.append("&spbill_create_ip=").append(unifiedOrder.getSpbillCreateIp());
        if (StringUtils.isNotBlank(unifiedOrder.getTimeExpire())) {
            sign.append("&time_expire=").append(unifiedOrder.getTimeExpire());
        }
        if (StringUtils.isNotBlank(unifiedOrder.getTimeStart())) {
            sign.append("&time_start=").append(unifiedOrder.getTimeStart());
        }
        sign.append("&total_fee=").append(unifiedOrder.getTotalFee());
        sign.append("&trade_type=").append(unifiedOrder.getTradeType());

        sign.append("&key=").append(key);

        try {
            unifiedOrder.setSign(EncryptUtil.encryptMD5(sign.toString()).toUpperCase());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        StringBuilder sb = new StringBuilder();

        sb.append("<xml>");
        sb.append("<appid>").append(unifiedOrder.getAppId()).append("</appid>");
        if (StringUtils.isNotBlank(unifiedOrder.getAttach())) {
            sb.append("<attach>").append(unifiedOrder.getAttach()).append("</attach>");
        }
        sb.append("<body>").append(unifiedOrder.getBody()).append("</body>");
        if (StringUtils.isNotBlank(unifiedOrder.getDetail())) {
            sb.append("<detail>").append(unifiedOrder.getDetail()).append("</detail>");
        }
        if (StringUtils.isNotBlank(unifiedOrder.getDeviceInfo())) {
            sb.append("<device_info>").append(unifiedOrder.getDeviceInfo())
                .append("</device_info>");
        }
        if (StringUtils.isNotBlank(unifiedOrder.getFeeType())) {
            sb.append("<fee_type>").append(unifiedOrder.getFeeType()).append("</fee_type>");
        }
        if (StringUtils.isNotBlank(unifiedOrder.getGoodsTag())) {
            sb.append("<goods_tag>").append(unifiedOrder.getGoodsTag()).append("</goods_tag>");
        }
        if (StringUtils.isNotBlank(unifiedOrder.getLimitPay())) {
            sb.append("<limit_pay>").append(unifiedOrder.getLimitPay()).append("</limit_pay>");
        }
        sb.append("<mch_id>").append(unifiedOrder.getMchId()).append("</mch_id>");
        sb.append("<nonce_str>").append(unifiedOrder.getNonceStr()).append("</nonce_str>");
        sb.append("<notify_url>").append(unifiedOrder.getNotifyUrl()).append("</notify_url>");
        if (StringUtils.isNotBlank(unifiedOrder.getOpenId())) {
            sb.append("<openid>").append(unifiedOrder.getOpenId()).append("</openid>");
        }
        sb.append("<out_trade_no>").append(unifiedOrder.getOutTradeNo()).append("</out_trade_no>");
        if (StringUtils.isNotBlank(unifiedOrder.getProductId())) {
            sb.append("<product_id>").append(unifiedOrder.getProductId()).append("</product_id>");
        }
        sb.append("<spbill_create_ip>").append(unifiedOrder.getSpbillCreateIp())
            .append("</spbill_create_ip>");
        if (StringUtils.isNotBlank(unifiedOrder.getTimeExpire())) {
            sb.append("<time_expire>").append(unifiedOrder.getTimeExpire())
                .append("</time_expire>");
        }
        if (StringUtils.isNotBlank(unifiedOrder.getTimeStart())) {
            sb.append("<time_start>").append(unifiedOrder.getTimeStart()).append("</time_start>");
        }
        sb.append("<total_fee>").append(unifiedOrder.getTotalFee()).append("</total_fee>");
        sb.append("<trade_type>").append(unifiedOrder.getTradeType()).append("</trade_type>");
        sb.append("<sign>").append(unifiedOrder.getSign()).append("</sign>");
        sb.append("</xml>");

        String result = null;

        try {
            result = HttpUtil.post(UnifiedOrderService.HTTPS_UNIFIED_ORDER_URL, sb.toString());
        } catch (Exception e) {
            logger.error(sb.toString(), e);
        }

        if (StringUtils.isEmpty(result)) {
            throw new RuntimeException("统一下单失败.");
        }

        UnifiedOrderReturn ret = XmlUtil.parse(result, new UnifiedOrderReturn());

        if (ret == null) {
            throw new RuntimeException("统一下单失败.");
        }

        // 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        if ("FAIL".equals(ret.getReturnCode())) {
            throw new RuntimeException(ret.getReturnMsg());
        }

        // 验证返回签名
        if (!validate(ret, key)) {
            throw new RuntimeException("接口返回签名验证失败.");
        }

        if ("FAIL".equals(ret.getResultCode())) {
            throw new RuntimeException(ret.getErrCodeDes());
        }

        return ret.getPrepayId();
    }

    /**
     * 
     * @param ret
     * @param key
     * @return
     */
    private boolean validate(UnifiedOrderReturn ret, String key) {
        StringBuilder sign = new StringBuilder();

        if (StringUtils.isNotBlank(ret.getAppId())) {
            sign.append("&appid=").append(ret.getAppId());
        }
        if (StringUtils.isNotBlank(ret.getCodeUrl())) {
            sign.append("&code_url=").append(ret.getCodeUrl());
        }
        if (StringUtils.isNotBlank(ret.getDeviceInfo())) {
            sign.append("&device_info=").append(ret.getDeviceInfo());
        }
        if (StringUtils.isNotBlank(ret.getErrCode())) {
            sign.append("&err_code=").append(ret.getErrCode());
        }
        if (StringUtils.isNotBlank(ret.getErrCodeDes())) {
            sign.append("&err_code_des=").append(ret.getErrCodeDes());
        }
        if (StringUtils.isNotBlank(ret.getMchId())) {
            sign.append("&mch_id=").append(ret.getMchId());
        }
        if (StringUtils.isNotBlank(ret.getNonceStr())) {
            sign.append("&nonce_str=").append(ret.getNonceStr());
        }
        if (StringUtils.isNotBlank(ret.getPrepayId())) {
            sign.append("&prepay_id=").append(ret.getPrepayId());
        }
        if (StringUtils.isNotBlank(ret.getResultCode())) {
            sign.append("&result_code=").append(ret.getResultCode());
        }
        if (StringUtils.isNotBlank(ret.getReturnCode())) {
            sign.append("&return_code=").append(ret.getReturnCode());
        }
        if (StringUtils.isNotBlank(ret.getReturnMsg())) {
            sign.append("&return_msg=").append(ret.getReturnMsg());
        }
        if (StringUtils.isNotBlank(ret.getTradeType())) {
            sign.append("&trade_type=").append(ret.getTradeType());
        }

        sign.append("&key=").append(key);

        try {
            if (ret.getSign().equals(EncryptUtil.encryptMD5(sign.substring(1)).toUpperCase())) {
                return true;
            }
        } catch (IOException e) {
            logger.error("encryptMD5", e);
        }

        return false;
    }

}
