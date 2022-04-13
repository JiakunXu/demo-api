package com.example.demo.getui.api;

/**
 * @author JiakunXu
 */
public interface PushApiService {

    /**
     *
     * @param cid
     * @param title
     * @param body
     * @param url
     * @return
     * @throws RuntimeException
     */
    String pushToSingleByCid(String cid, String title, String body,
                             String url) throws RuntimeException;

}
