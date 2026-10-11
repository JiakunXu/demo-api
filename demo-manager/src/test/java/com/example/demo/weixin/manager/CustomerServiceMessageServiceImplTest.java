package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.support.HttpContract;
import com.example.demo.support.HttpContract.Endpoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.DynamicTest;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static com.example.demo.support.HttpContract.jsonBody;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Custom;
import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Typing;

class CustomerServiceMessageServiceImplTest {
    @TestFactory
    Stream<DynamicTest> sendsText() {
        return HttpContract.json("text", () -> {
            Custom.Text payload = JSON.parseObject("{\"content\": \"hello\"}", Custom.Text.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"text\", \"text\": {\"content\": \"hello\"}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsImage() {
        return HttpContract.json("image", () -> {
            Custom.Image payload = JSON.parseObject("{\"media_id\": \"media\"}", Custom.Image.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"image\", \"image\": {\"media_id\": \"media\"}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsVoice() {
        return HttpContract.json("voice", () -> {
            Custom.Voice payload = JSON.parseObject("{\"media_id\": \"media\"}", Custom.Voice.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"voice\", \"voice\": {\"media_id\": \"media\"}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsVideo() {
        return HttpContract.json("video", () -> {
            Custom.Video payload = JSON.parseObject("{\"media_id\": \"media\", \"thumb_media_id\": \"thumbnail\", \"title\": \"title\", \"description\": \"description\"}", Custom.Video.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"video\", \"video\": {\"media_id\": \"media\", \"thumb_media_id\": \"thumbnail\", \"title\": \"title\", \"description\": \"description\"}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsMusic() {
        return HttpContract.json("music", () -> {
            Custom.Music payload = JSON.parseObject("{\"title\": \"title\", \"description\": \"description\", \"thumb_media_id\": \"thumbnail\"}", Custom.Music.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"music\", \"music\": {\"title\": \"title\", \"description\": \"description\", \"thumb_media_id\": \"thumbnail\"}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsNews() {
        return HttpContract.json("news", () -> {
            Custom.News payload = JSON.parseObject("{}", Custom.News.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"news\", \"news\": {}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsMpNews() {
        return HttpContract.json("mpnews", () -> {
            Custom.MpNews payload = JSON.parseObject("{\"media_id\": \"media\"}", Custom.MpNews.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"mpnews\", \"mpnews\": {\"media_id\": \"media\"}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsMpNewsArticle() {
        return HttpContract.json("mpnewsarticle", () -> {
            Custom.MpNewsArticle payload = JSON.parseObject("{}", Custom.MpNewsArticle.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"mpnewsarticle\", \"mpnewsarticle\": {}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsWxCard() {
        return HttpContract.json("wxcard", () -> {
            Custom.WxCard payload = JSON.parseObject("{}", Custom.WxCard.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"wxcard\", \"wxcard\": {}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsMiniProgramPage() {
        return HttpContract.json("miniprogrampage", () -> {
            Custom.MiniProgramPage payload = JSON.parseObject("{\"title\": \"title\", \"appid\": \"app\", \"pagepath\": \"pages/index\", \"thumb_media_id\": \"thumbnail\"}", Custom.MiniProgramPage.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"miniprogrampage\", \"miniprogrampage\": {\"title\": \"title\", \"appid\": \"app\", \"pagepath\": \"pages/index\", \"thumb_media_id\": \"thumbnail\"}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> sendsLink() {
        return HttpContract.json("link", () -> {
            Custom.Link payload = JSON.parseObject("{\"title\": \"title\", \"description\": \"description\", \"url\": \"https://example.test\", \"thumb_url\": \"thumbnail\"}", Custom.Link.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=token"),
                jsonBody("{\"touser\": \"open\", \"msgtype\": \"link\", \"link\": {\"title\": \"title\", \"description\": \"description\", \"url\": \"https://example.test\", \"thumb_url\": \"thumbnail\"}}")), () -> new CustomerServiceMessageServiceImpl().send("token", "open", payload), Result.class,
                "{\"errcode\":0,\"errmsg\":\"ok\"}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @TestFactory
    Stream<DynamicTest> typing() {
        return HttpContract.json("typing", () -> {
            Typing input = JSON.parseObject("{\"command\":\"Typing\"}", Typing.class);
            return new Endpoint(() -> HttpUtil.post(eq("https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=token"),
                jsonBody("{\"touser\":\"open\",\"command\":\"Typing\"}")),
                () -> new CustomerServiceMessageServiceImpl().setTyping("token", "open", input), BaseResult.class,
                "{\"errcode\":0}", "{\"errcode\":40001,\"errmsg\":\"provider rejected\"}", "result is null.");
        });
    }

    @Test
    void rejectsMissingTyping() {
        assertThatThrownBy(() -> new CustomerServiceMessageServiceImpl().setTyping("token", "open", null))
            .hasMessage("typing cannot be null.");
    }
}
