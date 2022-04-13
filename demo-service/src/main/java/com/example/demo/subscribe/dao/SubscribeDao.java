package com.example.demo.subscribe.dao;

import com.example.demo.subscribe.api.bo.Subscribe;
import com.example.demo.tunnel.api.bo.Tunnel;

import java.util.List;

/**
 * @author JiakunXu
 */
public interface SubscribeDao {

    /**
     *
     * @param subscribe
     * @return
     */
    int countSubscribe0(Subscribe subscribe);

    /**
     *
     * @param subscribe
     * @return
     */
    int countSubscribe1(Subscribe subscribe);

    /**
     *
     * @param subscribe
     * @return
     */
    List<Tunnel> listSubscribes(Subscribe subscribe);

    /**
     *
     * @param subscribe
     * @return
     */
    Subscribe getSubscribe(Subscribe subscribe);

    /**
     *
     * @param subscribe
     * @return
     */
    int insertSubscribe(Subscribe subscribe);

}
