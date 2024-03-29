package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.CustomerServiceMessageService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Custom;
import com.example.demo.weixin.api.bo.message.Image;
import com.example.demo.weixin.api.bo.message.Link;
import com.example.demo.weixin.api.bo.message.MiniProgramPage;
import com.example.demo.weixin.api.bo.message.MpNews;
import com.example.demo.weixin.api.bo.message.MpNewsArticle;
import com.example.demo.weixin.api.bo.message.Music;
import com.example.demo.weixin.api.bo.message.News;
import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Text;
import com.example.demo.weixin.api.bo.message.Typing;
import com.example.demo.weixin.api.bo.message.Video;
import com.example.demo.weixin.api.bo.message.Voice;
import com.example.demo.weixin.api.bo.message.WxCard;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class CustomerServiceMessageServiceImpl implements CustomerServiceMessageService {

    private static final Logger logger = LoggerFactory
        .getLogger(CustomerServiceMessageServiceImpl.class);

    /**
     *
     * @param accessToken
     * @param custom
     * @return
     * @throws RuntimeException
     */
    private Result send(String accessToken, Custom custom) throws RuntimeException {
        Result result;

        try {
            result = JSON.parseObject(
                HttpUtil.post(CustomerServiceMessageService.HTTPS_SEND_URL + accessToken,
                    JSON.toJSONString(custom)),
                Result.class);
        } catch (Exception e) {
            logger.error(custom.toString(), e);

            throw new RuntimeException(e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        if (result.getErrCode() != 0) {
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

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("text");
        custom.setText(text);

        return send(accessToken, custom);
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

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("image");
        custom.setImage(image);

        return send(accessToken, custom);
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

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("voice");
        custom.setVoice(voice);

        return send(accessToken, custom);
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

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("video");
        custom.setVideo(video);

        return send(accessToken, custom);
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

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("music");
        custom.setMusic(music);

        return send(accessToken, custom);
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

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("news");
        custom.setNews(news);

        return send(accessToken, custom);
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

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("mpnews");
        custom.setMpNews(mpNews);

        return send(accessToken, custom);
    }

    @Override
    public Result send(String accessToken, String toUser,
                       MpNewsArticle mpNewsArticle) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (mpNewsArticle == null || StringUtils.isBlank(mpNewsArticle.getArticleId())) {
            throw new RuntimeException("article_id cannot be null.");
        }

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("mpnewsarticle");
        custom.setMpNewsArticle(mpNewsArticle);

        return send(accessToken, custom);
    }

    @Override
    public Result send(String accessToken, String toUser, WxCard wxCard) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (wxCard == null || StringUtils.isBlank(wxCard.getCardId())) {
            throw new RuntimeException("card_id cannot be null.");
        }

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("wxcard");
        custom.setWxCard(wxCard);

        return send(accessToken, custom);
    }

    @Override
    public Result send(String accessToken, String toUser,
                       MiniProgramPage miniProgramPage) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (miniProgramPage == null || StringUtils.isBlank(miniProgramPage.getTitle())) {
            throw new RuntimeException("title cannot be null.");
        }

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("miniprogrampage");
        custom.setMiniProgramPage(miniProgramPage);

        return send(accessToken, custom);
    }

    @Override
    public Result send(String accessToken, String toUser, Link link) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (link == null || StringUtils.isBlank(link.getTitle())) {
            throw new RuntimeException("title cannot be null.");
        }

        Custom custom = new Custom();
        custom.setToUser(toUser);
        custom.setMsgType("link");
        custom.setLink(link);

        return send(accessToken, custom);
    }

    @Override
    public BaseResult setTyping(String accessToken, String toUser,
                                Typing typing) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (typing == null) {
            throw new RuntimeException("typing cannot be null.");
        }

        typing.setToUser(toUser);

        BaseResult result;

        try {
            result = JSON.parseObject(
                HttpUtil.post(CustomerServiceMessageService.HTTPS_TYPING_URL + accessToken,
                    JSON.toJSONString(typing)),
                BaseResult.class);
        } catch (Exception e) {
            logger.error(typing.toString(), e);

            throw new RuntimeException(e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        if (result.getErrCode() != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

}
