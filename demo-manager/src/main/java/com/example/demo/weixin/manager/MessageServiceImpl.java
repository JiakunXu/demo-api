package com.example.demo.weixin.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MessageService;
import com.example.demo.weixin.api.bo.message.Image;
import com.example.demo.weixin.api.bo.message.Message;
import com.example.demo.weixin.api.bo.message.MpNews;
import com.example.demo.weixin.api.bo.message.Music;
import com.example.demo.weixin.api.bo.message.News;
import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Template;
import com.example.demo.weixin.api.bo.message.Text;
import com.example.demo.weixin.api.bo.message.Video;
import com.example.demo.weixin.api.bo.message.Voice;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    /**
     * 
     * @param accessToken
     * @param message
     * @return
     * @throws RuntimeException
     */
    private Result send(String accessToken, Message message) throws RuntimeException {
        Result result = null;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageService.HTTPS_CUSTOM_URL + accessToken.trim(),
                    JSON.toJSONString(message)), Result.class);
        } catch (Exception e) {
            logger.error(message.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        Integer errCode = result.getErrCode();
        if (errCode != null && errCode != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

    @Override
    public Result send(String accessToken, String toUser, Text text) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (text == null || StringUtils.isBlank(text.getContent())) {
            throw new RuntimeException("content cannot be null.");
        }

        Message message = new Message();
        message.setToUser(toUser);
        message.setMsgType("text");
        message.setText(text);

        return send(accessToken, message);
    }

    @Override
    public Result send(String accessToken, String toUser, Image image) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (image == null || StringUtils.isBlank(image.getMediaId())) {
            throw new RuntimeException("media_id cannot be null.");
        }

        Message message = new Message();
        message.setToUser(toUser);
        message.setMsgType("image");
        message.setImage(image);

        return send(accessToken, message);
    }

    @Override
    public Result send(String accessToken, String toUser, Voice voice) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (voice == null || StringUtils.isBlank(voice.getMediaId())) {
            throw new RuntimeException("media_id cannot be null.");
        }

        Message message = new Message();
        message.setToUser(toUser);
        message.setMsgType("voice");
        message.setVoice(voice);

        return send(accessToken, message);
    }

    @Override
    public Result send(String accessToken, String toUser, Video video) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (video == null || StringUtils.isBlank(video.getMediaId())) {
            throw new RuntimeException("media_id cannot be null.");
        }

        Message message = new Message();
        message.setToUser(toUser);
        message.setMsgType("video");
        message.setVideo(video);

        return send(accessToken, message);
    }

    @Override
    public Result send(String accessToken, String toUser, Music music) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (music == null || StringUtils.isBlank(music.getTitle())) {
            throw new RuntimeException("title cannot be null.");
        }

        Message message = new Message();
        message.setToUser(toUser);
        message.setMsgType("music");
        message.setMusic(music);

        return send(accessToken, message);
    }

    @Override
    public Result send(String accessToken, String toUser, News news) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (news == null || news.getArticleList() == null) {
            throw new RuntimeException("articles cannot be null.");
        }

        Message message = new Message();
        message.setToUser(toUser);
        message.setMsgType("news");
        message.setNews(news);

        return send(accessToken, message);
    }

    @Override
    public Result send(String accessToken, String toUser, MpNews mpNews) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (mpNews == null || StringUtils.isBlank(mpNews.getMediaId())) {
            throw new RuntimeException("media_id cannot be null.");
        }

        Message message = new Message();
        message.setToUser(toUser);
        message.setMsgType("mpnews");
        message.setMpNews(mpNews);

        return send(accessToken, message);
    }

    @Override
    public Result send(String accessToken, String toUser,
                       Template template) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (template == null || template.getData() == null) {
            throw new RuntimeException("template cannot be null.");
        }

        template.setToUser(toUser);

        Result result = null;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageService.HTTPS_TEMPLATE_URL + accessToken.trim(),
                    JSON.toJSONString(template)), Result.class);
        } catch (Exception e) {
            logger.error(template.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        Integer errCode = result.getErrCode();
        if (errCode != null && errCode != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

}
