package com.example.demo.alipay.service;

import com.alipay.easysdk.factory.Factory;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.example.demo.alipay.api.AlipayFundAuthNotifyService;
import com.example.demo.alipay.api.bo.fund.AlipayFundAuthNotify;
import com.example.demo.alipay.dao.dataobject.AlipayFundAuthNotifyDO;
import com.example.demo.alipay.dao.mapper.AlipayFundAuthNotifyMapper;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.framework.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class AlipayFundAuthNotifyServiceImpl extends
                                             ServiceImpl<AlipayFundAuthNotifyMapper, AlipayFundAuthNotifyDO>
                                             implements AlipayFundAuthNotifyService {

    @Override
    public AlipayFundAuthNotify getFundAuthNotify(Map<String, String> parameters) {
        AlipayFundAuthNotify notify = new AlipayFundAuthNotify();
        notify.setAuthNo(parameters.get("auth_no"));
        notify.setNotifyType(parameters.get("notify_type"));
        notify.setOutOrderNo(parameters.get("out_order_no"));
        notify.setOperationId(parameters.get("operation_id"));
        notify.setOutRequestNo(parameters.get("out_request_no"));
        notify.setOperationType(parameters.get("operation_type"));
        notify.setAmount(parameters.get("amount"));
        notify.setStatus(parameters.get("status"));
        notify.setGmtCreate(parameters.get("gmt_create"));
        notify.setGmtTrans(parameters.get("gmt_trans"));
        notify.setPayeeLogonId(parameters.get("payee_logon_id"));
        notify.setPayeeUserId(parameters.get("payee_user_id"));
        notify.setTotalFreezeAmount(parameters.get("total_freeze_amount"));
        notify.setTotalUnfreezeAmount(parameters.get("total_unfreeze_amount"));
        notify.setTotalPayAmount(parameters.get("total_pay_amount"));
        notify.setRestAmount(parameters.get("rest_amount"));
        notify.setCreditAmount(parameters.get("credit_amount"));
        notify.setFundAmount(parameters.get("fund_amount"));
        notify.setTotalFreezeCreditAmount(parameters.get("total_freeze_credit_amount"));
        notify.setTotalFreezeFundAmount(parameters.get("total_freeze_fund_amount"));
        notify.setTotalUnfreezeCreditAmount(parameters.get("total_unfreeze_credit_amount"));
        notify.setTotalUnfreezeFundAmount(parameters.get("total_unfreeze_fund_amount"));
        notify.setTotalPayCreditAmount(parameters.get("total_pay_credit_amount"));
        notify.setTotalPayFundAmount(parameters.get("total_pay_fund_amount"));
        notify.setRestCreditAmount(parameters.get("rest_credit_amount"));
        notify.setRestFundAmount(parameters.get("rest_fund_amount"));
        notify.setPreAuthType(parameters.get("pre_auth_type"));
        notify.setCreditMerchantExt(parameters.get("credit_merchant_ext"));

        notify.setNotifyId(parameters.get("notify_id"));
        notify.setNotifyTime(getDate(parameters.get("notify_time")));
        notify.setCharset(parameters.get("charset"));
        notify.setSign(parameters.get("sign"));
        notify.setSignType(parameters.get("sign_type"));
        notify.setVersion(parameters.get("version"));

        try {
            if (!Factory.Payment.Common().verifyNotify(parameters)) {
                throw new RuntimeException("验签失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return notify;
    }

    @Override
    public AlipayFundAuthNotify insertFundAuthNotify(@NotNull AlipayFundAuthNotify notify) {
        AlipayFundAuthNotifyDO notifyDO = BeanUtil.copy(notify, AlipayFundAuthNotifyDO.class);

        this.save(notifyDO);

        return notify;
    }

    private Date getDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }

        return DateUtil.getDateTime(date);
    }

}
