package com.example.demo.subscribe.dao.mapper;

import com.example.demo.framework.mapper.BaseMapper;
import com.example.demo.subscribe.dao.dataobject.SubscribeDO;
import com.example.demo.tunnel.dao.dataobject.TunnelDO;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface SubscribeMapper extends BaseMapper<SubscribeDO> {

    /**
     *
     * @param subscribeDO
     * @return
     */
    int count0(SubscribeDO subscribeDO);

    /**
     *
     * @param subscribeDO
     * @return
     */
    int count1(SubscribeDO subscribeDO);

    /**
     *
     * @param subscribeDO
     * @return
     */
    List<TunnelDO> listSubscribes(SubscribeDO subscribeDO);

}
