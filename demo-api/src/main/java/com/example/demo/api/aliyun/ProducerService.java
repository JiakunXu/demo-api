package com.example.demo.api.aliyun;

/**
 * @author JiakunXu
 */
public interface ProducerService {

    /**
     *
     * @param topic
     * @param tags
     * @param body
     * @param key
     * @return
     */
    String sendAsync(String topic, String tags, byte[] body, String key);

    /**
     *
     * @param topic
     * @param tags
     * @param body
     * @param key
     * @param timeStamp
     *            定时消息，单位毫秒（ms），在指定时间戳（当前时间之后）进行投递，例如 2016-03-07 16:21:00
     *            投递。如果被设置成当前时间戳之前的某个时刻，消息将立刻投递给消费者。
     *            long timeStamp = new
     *            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-03-07 16:21:00"
     *            ).getTime();.
     * @return
     */
    String sendAsync(String topic, String tags, byte[] body, String key, long timeStamp);

}
