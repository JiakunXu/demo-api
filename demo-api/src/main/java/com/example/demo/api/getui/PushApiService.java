package com.example.demo.api.getui;

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
