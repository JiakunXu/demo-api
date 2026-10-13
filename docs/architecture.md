# 架构说明

维护于 2026-10-13。模块依赖以各 POM 为准，业务行为以源码为准。

## 模块与调用方向

主流程为 Web 协议适配 → Service 业务编排 → Mapper 数据访问 / Manager 外部能力。API 提供业务接口与 BO，DAO 定义 DO、Mapper 和 SQL，Util 提供通用工具，Open 提供对外契约，Task 执行任务，Start 装配应用。

| 模块 | 仓库内直接依赖 |
| --- | --- |
| demo-api / demo-dao / demo-util | 无其他子模块 |
| demo-open | demo-api |
| demo-manager | demo-dao、demo-open、demo-util |
| demo-service | demo-dao、demo-manager、demo-open、demo-util |
| demo-web / demo-task | demo-service |
| start | demo-web、demo-task |

API 通过传递依赖供上层使用。不要新增循环依赖，或让 API 引用 Service/Web 实现。SDK 类型已经进入部分 API 契约，改变它们需要评估消费者兼容性。同名类存在于不同业务包，注入时核对全限定名与 Bean 名。

## 数据与契约

BO 表达 API/业务数据，DO 表达持久化映射。转换集中在 [BeanUtil](../demo-util/src/main/java/com/example/demo/framework/util/BeanUtil.java)：Class 重载经 Fastjson 转换，Supplier 重载使用 BeanCopier，两者不具有相同的嵌套及注解语义。

[BaseDO](../demo-dao/src/main/java/com/example/demo/framework/dataobject/BaseDO.java) 使用 `is_deleted/create_by/update_by`；自定义 SQL 要自己保证软删除和数据范围。MyBatis-Plus 通用 CRUD 参考 [BannerServiceImpl](../demo-service/src/main/java/com/example/demo/banner/service/BannerServiceImpl.java)，其中自定义查询是否具有映射需另行核对。

[ChatServiceImpl](../demo-service/src/main/java/com/example/demo/chat/service/ChatServiceImpl.java) 的双用户业务键更新后插入语义不同于通用主键 saveOrUpdate。不能为复用 CRUD 改变业务键判断。

HTTP 沿用项目 response 对象。[JsonConfigurer](../demo-web/src/main/java/com/example/demo/framework/config/JsonConfigurer.java) 使用 Fastjson、固定日期格式和 `codes` 排除；[BaseExceptionHandler](../demo-web/src/main/java/com/example/demo/framework/handler/BaseExceptionHandler.java) 的响应体错误码不等于真实 HTTP status。协议变更需要调用方回归。

## 事务与外部状态

Service 负责数据库事务边界。Redis、MQ、HTTP 不参与普通本地数据库回滚。[ChatDetailServiceImpl](../demo-service/src/main/java/com/example/demo/chat/service/ChatDetailServiceImpl.java) 当前在事务方法中写记录并发送消息，存在事务提交与消息可见性协调需求。

[TransactionUtil](../demo-util/src/main/java/com/example/demo/framework/util/TransactionUtil.java) 可注册提交后同步操作，需要活动事务；失败时数据库已经提交，不提供持久化投递或自动重试。可靠消息需另行设计幂等、outbox 或补偿，并用 ADR 记录选择。

[RedisConfigurer](../demo-web/src/main/java/com/example/demo/framework/config/RedisConfigurer.java) 设置默认 Fastjson 序列化，不能假设 key 一定是普通字符串字节。改变序列化、TTL 或认证缓存要考虑旧数据兼容及失效策略。

## 演进方式

架构变化先明确边界、契约、数据和失败路径，用 [执行计划](../PLANS.md) 安排实施，长期取舍写 [ADR](decisions/README.md)。当前缺口见 [工程现状](engineering-roadmap.md)；本说明不表示所有接口已落地。
