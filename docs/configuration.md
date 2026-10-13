# 配置键与代码消费者

状态：源码配置索引与开发规范。维护于 2026-10-13。默认 YAML 配置项完整、地址和账号有效；本页不判断、展示或修改 YAML 中的配置值，不把缺失/无效值当作待处理问题。

## 范围与来源

配置入口为 [application.yml](../start/src/main/resources/application.yml)，显式 Java 消费主要为 @Value，日志使用 [logback-spring.xml](../start/src/main/resources/logback-spring.xml) 的 springProperty。下方索引按源码提取键和消费类型，不包含真实地址、账号、密码、密钥或私钥路径。

索引覆盖当前 YAML 的键结构、Java 直接占位符和日志引用，不是 Spring/SDK 全部可接受配置的完整目录。SDK 自动配置或运行环境提供的其他键在涉及它们的任务中另行查对应源码。新增配置时同步源码和本页。

## 配置分组与用途

| 分组 | 用途与消费者 | 类型/语义关注 |
| --- | --- | --- |
| server / spring.main / spring.threads | 服务绑定、容器装配与线程设置 | 监听端口、Bean 行为、线程边界 |
| spring.datasource / druid | MySQL 数据源与连接池 | 连接参数、池大小、超时与校验策略 |
| spring.data.redis | Redis 连接与池 | database、时长单位、序列化由 RedisConfigurer 决定 |
| spring.messages / mvc / servlet | 国际化、异步请求、上传 | 字符编码、超时、大小单位 |
| spring.elasticsearch / cloud | 搜索、注册发现与限流配置 | 自动配置与启用功能范围 |
| mybatis-plus | SQL 资源、别名、主键及逻辑删除 | AUTO、deleted、下划线映射等源码行为 |
| rocketmq / aliyun.ons / aliyun.mqtt | 消息与平台 SDK | 主题、组、实例及客户端生命周期 |
| dubbo | RPC 应用、注册与协议 | 消费者检查和服务装配 |
| xxl.job | 调度与执行器 | XxlJobConfig 注入与生命周期 |
| aliyun / huawei | 云平台与日志/存储等能力 | 共享身份、各服务 endpoint 与 SDK |
| alipay / wxpay / wxpay.partner | 支付配置与证书 | 商户/应用身份、回调及对应客户端 |
| weixin / bytedance / dingtalk | 平台登录、令牌、消息与回调 | 各平台验签/加解密和协议 |
| secret.key | 安全 TokenService 的 JWT 签名 | 是安全配置，不与第三方 app secret 混淆 |

“按功能使用”不代表现有 Bean 都具备开关。[ProducerBean](../demo-manager/src/main/java/com/example/demo/mqtt/manager/ProducerBean.java) 由 @Bean 启动/停止 MQTT producer；[XxlJobConfig](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) 注入执行器字段后创建 Bean。运行任务时仍按授权控制外部副作用，配置有效不是发送消息/支付的授权。

## Java 消费类型与默认表达式

下面的类型来自字段声明。@Value 的默认表达式属于代码的绑定语义，不能等同于 YAML 的取值。本页不使用默认表达式判断当前环境可用性；修改类型或表达式时核对消费者，不因字符串与数值互换而改变行为。

XxlJobConfig 的 logpath 使用嵌套 user.home 表达式，解析时应区分外层配置与内层系统属性。共享的 aliyun.accessKey 字段存在多个消费者，改键名时不能只同步单个服务。是否敏感按业务含义判断，不能仅凭键中有没有 password。

| 配置键 | 字段类型 | 首个直接消费者（其他位置可按键名搜索） |
| --- | --- | --- |
| `alipay.app.id` | `String` | [AlipayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/AlipayConfigurer.java) |
| `alipay.cert.path` | `String` | [AlipayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/AlipayConfigurer.java) |
| `alipay.encrypt.key` | `String` | [AlipayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/AlipayConfigurer.java) |
| `alipay.merchantCert.path` | `String` | [AlipayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/AlipayConfigurer.java) |
| `alipay.notify.fund.auth.url` | `String` | [FundAuthOrderServiceImpl.java](../demo-manager/src/main/java/com/example/demo/alipay/manager/FundAuthOrderServiceImpl.java) |
| `alipay.notify.url` | `String` | [AlipayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/AlipayConfigurer.java) |
| `alipay.privateKey.path` | `String` | [AlipayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/AlipayConfigurer.java) |
| `alipay.rootCert.path` | `String` | [AlipayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/AlipayConfigurer.java) |
| `aliyun.accessKey.id` | `String` | [MailServiceImpl.java](../demo-manager/src/main/java/com/example/demo/aliyun/manager/MailServiceImpl.java)；共 8 处 |
| `aliyun.accessKey.secret` | `String` | [MailServiceImpl.java](../demo-manager/src/main/java/com/example/demo/aliyun/manager/MailServiceImpl.java)；共 8 处 |
| `aliyun.cdn.privateKey` | `String` | [CdnServiceImpl.java](../demo-service/src/main/java/com/example/demo/aliyun/service/CdnServiceImpl.java) |
| `aliyun.dm.endpoint` | `String` | [MailServiceImpl.java](../demo-manager/src/main/java/com/example/demo/aliyun/manager/MailServiceImpl.java) |
| `aliyun.mqtt.domain` | `String` | [ProducerBean.java](../demo-manager/src/main/java/com/example/demo/mqtt/manager/ProducerBean.java)；共 3 处 |
| `aliyun.mqtt.groupId` | `String` | [ClientStatusBean.java](../demo-service/src/main/java/com/example/demo/mqtt/service/ClientStatusBean.java) |
| `aliyun.mqtt.instanceId` | `String` | [ProducerBean.java](../demo-manager/src/main/java/com/example/demo/mqtt/manager/ProducerBean.java)；共 3 处 |
| `aliyun.mqtt.port` | `int` | [ProducerBean.java](../demo-manager/src/main/java/com/example/demo/mqtt/manager/ProducerBean.java)；共 3 处 |
| `aliyun.mqtt.topic` | `String` | [ConsumerBean.java](../demo-service/src/main/java/com/example/demo/mqtt/service/ConsumerBean.java) |
| `aliyun.ons.topic` | `String` | [LogAspect.java](../demo-web/src/main/java/com/example/demo/framework/aspectj/LogAspect.java) |
| `aliyun.oss.bucketName` | `String` | [FileServiceImpl.java](../demo-service/src/main/java/com/example/demo/file/service/FileServiceImpl.java) |
| `aliyun.oss.endpoint` | `String` | [OssServiceImpl.java](../demo-manager/src/main/java/com/example/demo/aliyun/manager/OssServiceImpl.java) |
| `aliyun.oss.url` | `String` | [FileServiceImpl.java](../demo-service/src/main/java/com/example/demo/file/service/FileServiceImpl.java) |
| `aliyun.region.id` | `String` | [PosChEcomServiceImpl.java](../demo-manager/src/main/java/com/example/demo/aliyun/manager/PosChEcomServiceImpl.java) |
| `bytedance.app.id` | `String` | [MessageServiceImpl.java](../demo-manager/src/main/java/com/example/demo/bytedance/manager/MessageServiceImpl.java) |
| `bytedance.app.secret` | `String` | [MessageServiceImpl.java](../demo-manager/src/main/java/com/example/demo/bytedance/manager/MessageServiceImpl.java) |
| `bytedance.token` | `String` | [MessageServiceImpl.java](../demo-manager/src/main/java/com/example/demo/bytedance/manager/MessageServiceImpl.java) |
| `dingtalk.app.key` | `String` | [MessageServiceImpl.java](../demo-manager/src/main/java/com/example/demo/dingtalk/manager/MessageServiceImpl.java) |
| `dingtalk.encodingAesKey` | `String` | [MessageServiceImpl.java](../demo-manager/src/main/java/com/example/demo/dingtalk/manager/MessageServiceImpl.java) |
| `dingtalk.token` | `String` | [MessageServiceImpl.java](../demo-manager/src/main/java/com/example/demo/dingtalk/manager/MessageServiceImpl.java) |
| `google.maps.api.key` | `String` | [GeocodingServiceImpl.java](../demo-manager/src/main/java/com/example/demo/google/manager/GeocodingServiceImpl.java) |
| `google.maps.base.url` | `String` | [GeocodingServiceImpl.java](../demo-manager/src/main/java/com/example/demo/google/manager/GeocodingServiceImpl.java) |
| `huawei.accessKey.id` | `String` | [ObsServiceImpl.java](../demo-manager/src/main/java/com/example/demo/huawei/manager/ObsServiceImpl.java) |
| `huawei.accessKey.secret` | `String` | [ObsServiceImpl.java](../demo-manager/src/main/java/com/example/demo/huawei/manager/ObsServiceImpl.java) |
| `huawei.obs.endpoint` | `String` | [ObsServiceImpl.java](../demo-manager/src/main/java/com/example/demo/huawei/manager/ObsServiceImpl.java) |
| `secret.key` | `String` | [TokenServiceImpl.java](../demo-service/src/main/java/com/example/demo/security/service/TokenServiceImpl.java) |
| `weixin.app.id` | `String` | [MessageServiceImpl.java](../demo-manager/src/main/java/com/example/demo/weixin/manager/MessageServiceImpl.java)；共 2 处 |
| `weixin.app.secret` | `String` | [JsapiServiceImpl.java](../demo-service/src/main/java/com/example/demo/weixin/service/JsapiServiceImpl.java) |
| `weixin.encodingAesKey` | `String` | [MessageServiceImpl.java](../demo-manager/src/main/java/com/example/demo/weixin/manager/MessageServiceImpl.java) |
| `weixin.token` | `String` | [MessageServiceImpl.java](../demo-manager/src/main/java/com/example/demo/weixin/manager/MessageServiceImpl.java) |
| `wxpay.apiV3Key` | `String` | [WxpayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/WxpayConfigurer.java) |
| `wxpay.app.id` | `String` | [WxpayServiceImpl.java](../demo-service/src/main/java/com/example/demo/wxpay/service/WxpayServiceImpl.java) |
| `wxpay.merchant.id` | `String` | [WxpayServiceImpl.java](../demo-service/src/main/java/com/example/demo/wxpay/service/WxpayServiceImpl.java)；共 2 处 |
| `wxpay.merchant.serialNumber` | `String` | [WxpayRefundServiceImpl.java](../demo-service/src/main/java/com/example/demo/wxpay/service/WxpayRefundServiceImpl.java)；共 3 处 |
| `wxpay.notify.url` | `String` | [WxpayServiceImpl.java](../demo-service/src/main/java/com/example/demo/wxpay/service/WxpayServiceImpl.java) |
| `wxpay.partner.apiV3Key` | `String` | [WxpayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/WxpayConfigurer.java) |
| `wxpay.partner.app.id` | `String` | [WxpayServiceImpl.java](../demo-service/src/main/java/com/example/demo/wxpay/service/WxpayServiceImpl.java) |
| `wxpay.partner.merchant.id` | `String` | [WxpayServiceImpl.java](../demo-service/src/main/java/com/example/demo/wxpay/service/WxpayServiceImpl.java)；共 2 处 |
| `wxpay.partner.merchant.serialNumber` | `String` | [WxpayRefundServiceImpl.java](../demo-service/src/main/java/com/example/demo/wxpay/service/WxpayRefundServiceImpl.java)；共 3 处 |
| `wxpay.partner.notify.url` | `String` | [WxpayServiceImpl.java](../demo-service/src/main/java/com/example/demo/wxpay/service/WxpayServiceImpl.java) |
| `wxpay.partner.privateKey.path` | `String` | [WxpayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/WxpayConfigurer.java) |
| `wxpay.privateKey.path` | `String` | [WxpayConfigurer.java](../demo-web/src/main/java/com/example/demo/framework/config/WxpayConfigurer.java) |
| `xxl.job.admin.accessToken` | `String` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.admin.addresses` | `String` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.admin.timeout` | `int` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.executor.address` | `String` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.executor.appname` | `String` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.executor.enabled` | `Boolean` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.executor.excludedpackage` | `String` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.executor.ip` | `String` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.executor.logpath` | `String` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.executor.logretentiondays` | `int` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |
| `xxl.job.executor.port` | `int` | [XxlJobConfig.java](../demo-task/src/main/java/com/example/demo/framework/config/XxlJobConfig.java) |

## 日志引用

springProperty 读取以下键，并交给现有日志 appender；配置值不在文档中复述：

`aliyun.sls.endpoint`, `aliyun.accessKey.id`, `aliyun.accessKey.secret`, `aliyun.sls.project`, `aliyun.sls.logStore`, `aliyun.sls.topic`.

## YAML 键索引

仅列键结构，不包含取值或有效性判断。需修改配置消费者时可用本索引定位所属分组，再读取对应类。配置的实际值继续由用户环境管理。

### alipay

- `alipay.app.id`
- `alipay.cert.path`
- `alipay.encrypt.key`
- `alipay.merchantCert.path`
- `alipay.notify.fund.auth.url`
- `alipay.notify.url`
- `alipay.privateKey.path`
- `alipay.rootCert.path`

### aliyun

- `aliyun.accessKey.id`
- `aliyun.accessKey.secret`
- `aliyun.cdn.privateKey`
- `aliyun.dashscope.apiKey`
- `aliyun.dm.accountName`
- `aliyun.dm.endpoint`
- `aliyun.ons.accessKey`
- `aliyun.ons.groupId`
- `aliyun.ons.namesrvAddr`
- `aliyun.ons.secretKey`
- `aliyun.ons.topic`
- `aliyun.oss.bucketName`
- `aliyun.oss.endpoint`
- `aliyun.oss.url`
- `aliyun.region.id`
- `aliyun.sls.endpoint`
- `aliyun.sls.logStore`
- `aliyun.sls.project`
- `aliyun.sls.topic`
- `aliyun.uid`

### bytedance

- `bytedance.app.id`
- `bytedance.app.secret`
- `bytedance.token`

### dingtalk

- `dingtalk.app.key`
- `dingtalk.app.secret`
- `dingtalk.encodingAesKey`
- `dingtalk.token`

### dubbo

- `dubbo.application.logger`
- `dubbo.application.name`
- `dubbo.application.qos-enable`
- `dubbo.consumer.check`
- `dubbo.consumer.filter`
- `dubbo.protocol.name`
- `dubbo.protocol.port`
- `dubbo.registry.address`
- `dubbo.registry.group`
- `dubbo.registry.password`
- `dubbo.registry.register`
- `dubbo.registry.register-mode`
- `dubbo.registry.username`

### huawei

- `huawei.accessKey.id`
- `huawei.accessKey.secret`
- `huawei.obs.endpoint`

### mybatis-plus

- `mybatis-plus.configuration.auto-mapping-unknown-column-behavior`
- `mybatis-plus.configuration.default-fetch-size`
- `mybatis-plus.configuration.default-statement-timeout`
- `mybatis-plus.configuration.lazy-loading-enabled`
- `mybatis-plus.configuration.log-impl`
- `mybatis-plus.configuration.map-underscore-to-camel-case`
- `mybatis-plus.global-config.banner`
- `mybatis-plus.global-config.db-config.id-type`
- `mybatis-plus.global-config.db-config.logic-delete-field`
- `mybatis-plus.global-config.db-config.logic-delete-value`
- `mybatis-plus.global-config.db-config.logic-not-delete-value`
- `mybatis-plus.mapper-locations`
- `mybatis-plus.type-aliases-package`

### rocketmq

- `rocketmq.consumer.access-key`
- `rocketmq.consumer.secret-key`
- `rocketmq.name-server`
- `rocketmq.producer.group`

### secret

- `secret.key`

### server

- `server.address`
- `server.port`

### spring

- `spring.application.name`
- `spring.cloud.nacos.discovery.group`
- `spring.cloud.nacos.discovery.namespace`
- `spring.cloud.nacos.discovery.password`
- `spring.cloud.nacos.discovery.server-addr`
- `spring.cloud.nacos.discovery.username`
- `spring.cloud.sentinel.datasource.flow.nacos.data-id`
- `spring.cloud.sentinel.datasource.flow.nacos.data-type`
- `spring.cloud.sentinel.datasource.flow.nacos.group-id`
- `spring.cloud.sentinel.datasource.flow.nacos.namespace`
- `spring.cloud.sentinel.datasource.flow.nacos.password`
- `spring.cloud.sentinel.datasource.flow.nacos.rule-type`
- `spring.cloud.sentinel.datasource.flow.nacos.server-addr`
- `spring.cloud.sentinel.datasource.flow.nacos.username`
- `spring.cloud.sentinel.datasource.param-flow.nacos.data-id`
- `spring.cloud.sentinel.datasource.param-flow.nacos.data-type`
- `spring.cloud.sentinel.datasource.param-flow.nacos.group-id`
- `spring.cloud.sentinel.datasource.param-flow.nacos.namespace`
- `spring.cloud.sentinel.datasource.param-flow.nacos.password`
- `spring.cloud.sentinel.datasource.param-flow.nacos.rule-type`
- `spring.cloud.sentinel.datasource.param-flow.nacos.server-addr`
- `spring.cloud.sentinel.datasource.param-flow.nacos.username`
- `spring.data.redis.database`
- `spring.data.redis.host`
- `spring.data.redis.lettuce.pool.max-active`
- `spring.data.redis.lettuce.pool.max-idle`
- `spring.data.redis.lettuce.pool.max-wait`
- `spring.data.redis.lettuce.pool.min-idle`
- `spring.data.redis.password`
- `spring.data.redis.port`
- `spring.data.redis.timeout`
- `spring.datasource.druid.async-init`
- `spring.datasource.druid.initial-size`
- `spring.datasource.druid.keep-alive`
- `spring.datasource.druid.max-active`
- `spring.datasource.druid.max-evictable-idle-time-millis`
- `spring.datasource.druid.max-wait`
- `spring.datasource.druid.min-evictable-idle-time-millis`
- `spring.datasource.druid.min-idle`
- `spring.datasource.druid.phy-max-use-count`
- `spring.datasource.druid.test-on-borrow`
- `spring.datasource.druid.test-on-return`
- `spring.datasource.druid.test-while-idle`
- `spring.datasource.druid.time-between-eviction-runs-millis`
- `spring.datasource.druid.validation-query`
- `spring.datasource.password`
- `spring.datasource.url`
- `spring.datasource.username`
- `spring.elasticsearch.password`
- `spring.elasticsearch.uris`
- `spring.elasticsearch.username`
- `spring.main.allow-circular-references`
- `spring.messages.basename`
- `spring.messages.encoding`
- `spring.mvc.async.request-timeout`
- `spring.servlet.multipart.max-file-size`
- `spring.servlet.multipart.max-request-size`
- `spring.threads.virtual.enabled`

### weixin

- `weixin.app.id`
- `weixin.app.secret`
- `weixin.encodingAesKey`
- `weixin.token`

### wxpay

- `wxpay.apiV3Key`
- `wxpay.app.id`
- `wxpay.merchant.id`
- `wxpay.merchant.name`
- `wxpay.merchant.serialNumber`
- `wxpay.notify.refund.url`
- `wxpay.notify.service.url`
- `wxpay.notify.url`
- `wxpay.partner.apiV3Key`
- `wxpay.partner.app.id`
- `wxpay.partner.merchant.id`
- `wxpay.partner.merchant.name`
- `wxpay.partner.merchant.serialNumber`
- `wxpay.partner.notify.refund.url`
- `wxpay.partner.notify.service.url`
- `wxpay.partner.notify.url`
- `wxpay.partner.privateKey.path`
- `wxpay.privateKey.path`

### xxl

- `xxl.job.admin.accessToken`
- `xxl.job.admin.addresses`
- `xxl.job.executor.appname`
- `xxl.job.executor.ip`
- `xxl.job.executor.port`

## 修改与验证

新增或重命名键时确认所有 @Value、配置类、日志和 SDK 消费者，说明类型/单位及是否敏感。绑定字段、默认表达式和生命周期改变需要相应验证；本批只整理文档，不实施配置迁移。

不复制真实值到测试、示例或 PR，不将配置有效视为已执行应用测试或已验证平台协议。实际运行失败时按异常分析，不默认归因于 YAML 值；纯文档修改不启动外部组件。
