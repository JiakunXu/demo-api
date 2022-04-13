package com.example.demo.subscribe.dao.mapper;

import com.example.demo.framework.mapper.BaseMapper;
import com.example.demo.subscribe.dao.dataobject.SubscribeDO;

/**
 * @author JiakunXu
 */
public interface SubscribeMapper extends BaseMapper<SubscribeDO> {

    /**
     *
     * @param subscribeDO
     * @return
     */
    int countSubscribe0(SubscribeDO subscribeDO);

    /**
     *
     * @param subscribeDO
     * @return
     */
    int countSubscribe1(SubscribeDO subscribeDO);

    /**
     *
     * @param subscribeDO
     * @return
     */
    List<Tunnel> listSubscribes(SubscribeDO subscribeDO);

}
