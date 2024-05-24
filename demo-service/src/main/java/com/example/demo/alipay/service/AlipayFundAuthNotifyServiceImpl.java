package com.example.demo.alipay.service;

import com.alipay.easysdk.factory.Factory;
import com.example.demo.alipay.api.AlipayFundAuthNotifyService;
import com.example.demo.alipay.api.bo.fund.AlipayFundAuthNotify;
import com.example.demo.alipay.dao.dataobject.AlipayFundAuthNotifyDO;
import com.example.demo.alipay.dao.mapper.AlipayFundAuthNotifyMapper;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.framework.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class AlipayFundAuthNotifyServiceImpl implements AlipayFundAuthNotifyService {

    private static final Logger        logger = LoggerFactory
        .getLogger(AlipayFundAuthNotifyServiceImpl.class);

    @Autowired
    private AlipayFundAuthNotifyMapper alipayFundAuthNotifyMapper;

    @Override
    public AlipayFundAuthNotify getAlipayFundAuthNotify(Map<String, String> parameters) {
        AlipayFundAuthNotify alipayFundAuthNotify = new AlipayFundAuthNotify();
        alipayFundAuthNotify.setAuthNo(parameters.get("auth_no"));
        alipayFundAuthNotify.setNotifyType(parameters.get("notify_type"));
        alipayFundAuthNotify.setOutOrderNo(parameters.get("out_order_no"));
        alipayFundAuthNotify.setOperationId(parameters.get("operation_id"));
        alipayFundAuthNotify.setOutRequestNo(parameters.get("out_request_no"));
        alipayFundAuthNotify.setOperationType(parameters.get("operation_type"));
        alipayFundAuthNotify.setAmount(parameters.get("amount"));
        alipayFundAuthNotify.setStatus(parameters.get("status"));
        alipayFundAuthNotify.setGmtCreate(parameters.get("gmt_create"));
        alipayFundAuthNotify.setGmtTrans(parameters.get("gmt_trans"));
        alipayFundAuthNotify.setPayeeLogonId(parameters.get("payee_logon_id"));
        alipayFundAuthNotify.setPayeeUserId(parameters.get("payee_user_id"));
        alipayFundAuthNotify.setTotalFreezeAmount(parameters.get("total_freeze_amount"));
        alipayFundAuthNotify.setTotalUnfreezeAmount(parameters.get("total_unfreeze_amount"));
        alipayFundAuthNotify.setTotalPayAmount(parameters.get("total_pay_amount"));
        alipayFundAuthNotify.setRestAmount(parameters.get("rest_amount"));
        alipayFundAuthNotify.setCreditAmount(parameters.get("credit_amount"));
        alipayFundAuthNotify.setFundAmount(parameters.get("fund_amount"));
        alipayFundAuthNotify
            .setTotalFreezeCreditAmount(parameters.get("total_freeze_credit_amount"));
        alipayFundAuthNotify.setTotalFreezeFundAmount(parameters.get("total_freeze_fund_amount"));
        alipayFundAuthNotify
            .setTotalUnfreezeCreditAmount(parameters.get("total_unfreeze_credit_amount"));
        alipayFundAuthNotify
            .setTotalUnfreezeFundAmount(parameters.get("total_unfreeze_fund_amount"));
        alipayFundAuthNotify.setTotalPayCreditAmount(parameters.get("total_pay_credit_amount"));
        alipayFundAuthNotify.setTotalPayFundAmount(parameters.get("total_pay_fund_amount"));
        alipayFundAuthNotify.setRestCreditAmount(parameters.get("rest_credit_amount"));
        alipayFundAuthNotify.setRestFundAmount(parameters.get("rest_fund_amount"));
        alipayFundAuthNotify.setPreAuthType(parameters.get("pre_auth_type"));
        alipayFundAuthNotify.setCreditMerchantExt(parameters.get("credit_merchant_ext"));

        alipayFundAuthNotify.setNotifyId(parameters.get("notify_id"));
        alipayFundAuthNotify.setNotifyTime(getDate(parameters.get("notify_time")));
        alipayFundAuthNotify.setCharset(parameters.get("charset"));
        alipayFundAuthNotify.setSign(parameters.get("sign"));
        alipayFundAuthNotify.setSignType(parameters.get("sign_type"));
        alipayFundAuthNotify.setVersion(parameters.get("version"));

        try {
            if (!Factory.Payment.Common().verifyNotify(parameters)) {
                throw new RuntimeException("验签失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return alipayFundAuthNotify;
    }

    @Override
    public AlipayFundAuthNotify insertAlipayFundAuthNotify(@NotNull AlipayFundAuthNotify alipayFundAuthNotify) {
        AlipayFundAuthNotifyDO alipayFundAuthNotifyDO = BeanUtil.copy(alipayFundAuthNotify,
            AlipayFundAuthNotifyDO.class);

        try {
            alipayFundAuthNotifyMapper.insert(alipayFundAuthNotifyDO);
        } catch (Exception e) {
            logger.error(alipayFundAuthNotifyDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return alipayFundAuthNotify;
    }

    private Date getDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }

        return DateUtil.getDateTime(date);
    }

}
