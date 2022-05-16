package com.example.demo.weixin.api.bo.material;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
public class Material extends BaseResult {

    private static final long serialVersionUID = -2800232607622280767L;

    /**
     * 该类型的素材的总数.
     */
    @JSONField(name = "total_count")
    private int               totalCount;

    /**
     * 本次调用获取的素材的数量.
     */
    @JSONField(name = "item_count")
    private int               itemCount;

    @JSONField(name = "item")
    private List<Item>        itemList;

}
