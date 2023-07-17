package com.example.demo.wxpay.api.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TransferDetail implements Serializable {

    private static final long serialVersionUID = -5768992890769752185L;

    /**
     * 商家明细单号
     */
    @JSONField(name = "out_detail_no")
    private String            outDetailNo;

    /**
     * 转账金额
     */
    @JSONField(name = "transfer_amount")
    private Integer           transferAmount;

    /**
     * 转账备注
     */
    @JSONField(name = "transfer_remark")
    private String            transferRemark;

    /**
     * 用户在直连商户应用下的用户标示
     */
    private String            openid;

    /**
     * 收款用户姓名
     */
    @JSONField(name = "user_name")
    private String            userName;

}
