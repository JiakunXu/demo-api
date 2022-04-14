package com.example.demo.getui.manager;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.getui.api.PushApiService;
import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
import com.getui.push.v2.sdk.dto.req.message.android.GTNotification;
import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
import com.getui.push.v2.sdk.dto.req.message.android.Ups;
import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @author JiakunXu
 */
@Service
public class PushApiServiceImpl implements PushApiService {

    private static final PushApi pushApi;

    static {
        GtApiConfiguration apiConfiguration = new GtApiConfiguration();
        apiConfiguration.setAppId("");
        apiConfiguration.setAppKey("");
        apiConfiguration.setMasterSecret("");

        // TODO pushApi = ApiHelper.build(apiConfiguration).creatApi(PushApi.class);
        pushApi = null;
    }

    @Override
    public String pushToSingleByCid(String cid, String title, String body,
                                    String url) throws RuntimeException {
        JSONObject object = new JSONObject();
        object.put("title", title);
        object.put("body", body);
        object.put("url", url);

        String payload = object.toJSONString();

        Audience audience = new Audience();
        audience.addCid(cid);

        GTNotification notification = new GTNotification();
        notification.setTitle(title);
        notification.setBody(body);
        notification.setClickType("payload");
        notification.setPayload(payload);

        PushMessage pushMessage = new PushMessage();
        pushMessage.setNotification(notification);

        // ios
        Alert alert = new Alert();
        alert.setTitle(title);
        alert.setBody(body);

        Aps aps = new Aps();
        aps.setAlert(alert);
        aps.setContentAvailable(0);

        IosDTO iosDTO = new IosDTO();
        iosDTO.setAps(aps);
        iosDTO.setAutoBadge("+1");
        iosDTO.setPayload(payload);

        // android
        ThirdNotification thirdNotification = new ThirdNotification();
        thirdNotification.setTitle(title);
        thirdNotification.setBody(body);
        thirdNotification.setClickType("payload");
        thirdNotification.setPayload(payload);

        Ups ups = new Ups();
        ups.setNotification(thirdNotification);

        AndroidDTO androidDTO = new AndroidDTO();
        androidDTO.setUps(ups);

        PushChannel pushChannel = new PushChannel();
        pushChannel.setIos(iosDTO);
        pushChannel.setAndroid(androidDTO);

        PushDTO<Audience> pushDTO = new PushDTO<>();
        pushDTO.setRequestId(UUID.randomUUID().toString().replace("-", ""));
        pushDTO.setAudience(audience);
        pushDTO.setPushMessage(pushMessage);
        pushDTO.setPushChannel(pushChannel);

        ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushToSingleByCid(pushDTO);

        if (apiResult.isSuccess()) {
            for (Map.Entry<String, Map<String, String>> m : apiResult.getData().entrySet()) {
                for (Map.Entry<String, String> n : m.getValue().entrySet()) {
                    return n.getValue();
                }
            }
        }

        throw new RuntimeException("code: " + apiResult.getCode() + ", msg: " + apiResult.getMsg());
    }

}
