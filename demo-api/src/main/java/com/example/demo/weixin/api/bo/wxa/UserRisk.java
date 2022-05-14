package com.example.demo.weixin.api.bo.wxa;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class UserRisk extends BaseResult {

    private static final long serialVersionUID = 7866898695750460896L;

    /**
     * 唯一请求标识，标记单次请求.
     */
    @JSONField(name = "unoin_id")
    private Long              unoinId;

    /**
     * 用户风险等级.
     */
    @JSONField(name = "risk_rank")
    private Integer           riskRank;

}
