# 工程现状与演进建议

维护于 2026-10-13。以下分为已核查现状和建议，相关任务开始前重新确认源码。本页不要求每次修改同时修复所有问题。

## 已定位的功能缺口

| 位置 | 现状与影响 | 验收方向 |
| --- | --- | --- |
| [RedisServiceImpl](../demo-manager/src/main/java/com/example/demo/cache/manager/RedisServiceImpl.java) | 三个 replace 重载返回 null，尚不能视为完整替换功能 | 明确不存在键、TTL 和原值返回语义，补充测试 |
| [UserDetailsServiceImpl](../demo-service/src/main/java/com/example/demo/security/service/UserDetailsServiceImpl.java) | loadUserById 返回 null | 明确用户缺失和状态契约，实现并验证认证消费者 |
| [BannerMapper](../demo-dao/src/main/java/com/example/demo/banner/dao/mapper/BannerMapper.java)、[ArticleMapper](../demo-dao/src/main/java/com/example/demo/article/dao/mapper/ArticleMapper.java) | 自定义 count/list 未发现对应 XML 或方法注解 | 核对注入器及绑定，用真实映射测试验证 |
| [日志 SQL](../demo-dao/src/main/resources/mappers/OperateLogMapper.xml)及 [登录日志 SQL](../demo-dao/src/main/resources/mappers/LoginLogMapper.xml) | 使用动态 sort/dir 拼接，非空条件无法防注入 | 追踪输入，建立允许列名/方向映射和回归测试 |
| [ChatDetailServiceImpl](../demo-service/src/main/java/com/example/demo/chat/service/ChatDetailServiceImpl.java) | 事务中发送消息，数据库和消息不是同一回滚边界 | 验证发送失败、回滚、重复和提交时序 |

上述检查不代表全仓库安全审计；其他未实现接口应在涉及它们的任务中进一步确认。

## 当前基础设施边界

已有 Util、Manager、Service 单元测试和 Spring 全上下文测试。Mapper mock 无法验证 SQL，事务探针不模拟真实数据库回滚。当前没有完整隔离 MySQL 测试设施、版本化 schema/迁移流程、独立本地依赖编排或测试 profile。

现有 CI 配置为 [CodeQL](../.github/workflows/codeql-analysis.yml) 与 [Dependency Review](../.github/workflows/dependency-review.yml)，没有专门的单元测试任务或覆盖率门槛。配置存在不证明远程执行成功；其中 action 版本及 Java 25 构建方式需另行核查。

## 建议优先顺序（尚未实施）

1. 修复本次业务实际使用的占位接口和 Mapper 映射，先定义验收契约。
2. 建立不依赖真实平台的单元测试 CI，记录 JDK、agent 参数及报告；成功后配置实际分支检查。
3. 建立隔离 MySQL 集成测试和迁移基线，验证默认值、软删除、约束、并发和回滚。
4. 明确本地配置及依赖启动方案，减少全应用测试对未知外部条件的依赖。
5. 按实际风险完善排序白名单、数据归属和消息一致性，长期选择记录 ADR。
6. 基于数据决定覆盖率和静态检查门槛，避免为数字而写镜像断言或强制全库格式化。

每项建设需要具体需求、影响分析、验证和维护安排；不因写入本页而视为已落实，也不是 OpenAI 对 Java 项目的强制选型。
