package com.example.demo.weixin.api.bo.message;

import java.io.Serializable;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
@ToString
public class Custom implements Serializable {

    private static final long serialVersionUID = 733672350056046345L;

    @JSONField(name = "touser")
    private String            toUser;

    @JSONField(name = "msgtype")
    private String            msgType;

    private Text              text;

    private Image             image;

    private Voice             voice;

    private Video             video;

    private Music             music;

    private News              news;

    @JSONField(name = "mpnews")
    private MpNews            mpNews;

    @JSONField(name = "mpnewsarticle")
    private MpNewsArticle     mpNewsArticle;

    @JSONField(name = "wxcard")
    private WxCard            wxCard;

    @JSONField(name = "miniprogrampage")
    private MiniProgramPage   miniProgramPage;

    private Link              link;

}
