# 测试指南

## 测试范围

当前测试主要位于 `demo-util`、`demo-manager`、`demo-service`，使用 JUnit Jupiter、Mockito、AssertJ。`start` 另有 [ApplicationTests](../start/src/test/java/com/example/demo/ApplicationTests.java)，它加载全应用并受外部配置影响。

| 层次 | 应验证 | 能力边界 |
| --- | --- | --- |
| Util | 转换、空值、边界、编码、资源关闭 | 当前工具测试范围限 `framework.util` |
| Manager | SDK 参数、响应解析、失败转换、缓存 TTL | mock 外部服务，不证明真实平台兼容 |
| Service | 权限范围、审计、ID 回填、状态、批量与缓存副作用 | mock Mapper 不证明 SQL 正确 |
| 代理行为 | 参数校验、事务 advice | 不代表真实数据库回滚 |
| 数据库集成 | SQL 绑定、默认值、约束、逻辑删除、并发 | 当前尚无完整隔离设施 |
| 应用上下文 | Bean 和配置装配 | 可能需要真实外部依赖 |

## 运行方法

从仓库根目录选择与改动相符的命令：

```bash
# Service 和上游单元测试，不包含 start
./mvnw -pl demo-service -am test

# Manager 或 Util 及其上游
./mvnw -pl demo-manager -am test
./mvnw -pl demo-util -am test

# 定向测试；上游没有匹配类也能继续
./mvnw -pl demo-service -am test   -Dtest=LoginLogServiceImplTest,OperateLogServiceImplTest   -Dsurefire.failIfNoSpecifiedTests=false

# 公共接口变更：所有模块的主代码和测试代码编译
./mvnw -DskipTests test-compile

# 环境具备时运行全仓库测试，包括应用上下文
./mvnw test
```

`-am` 纳入上游，避免误用本地旧模块产物。目标测试必须实际执行，运行 0 个测试不能作为通过证据。`-DskipTests` 不表示测试通过；`-Dmaven.test.skip=true` 还可能跳过测试编译。缓存齐备时可加 `-o`，不具备缓存时先解决依赖。

## JDK 25 的临时参数

若 inline/static mock 不能动态挂载 agent，或字体测试缺少图形环境，可使用实际解析版本的 Mockito JAR：

```bash
MOCKITO_JAR=/absolute/path/to/mockito-core-resolved-version.jar
test -f "$MOCKITO_JAR"
./mvnw -pl demo-service -am test   "-DargLine=-javaagent:"$MOCKITO_JAR" -Djava.awt.headless=true"
```

该路径是待替换示例。通过 effective POM、依赖树和 Maven settings 核对实际版本及仓库位置，不能盲选目录中最高版本。命令中的 `argLine` 若已有其他 agent 参数应合并。项目已选择不在 POM 增加 Mockito agent/headless 配置，保留临时命令参数方式。

## 用例设计

从需求和历史缺陷出发：给定输入、执行行为、断言结果和关键副作用。覆盖正常、边界、非法输入、缺失数据、依赖失败及重复操作。不要复制实现逻辑作为测试判定，也不要 mock 掉待验证的业务方法。

Service 可复用 [ServiceTestSupport](../demo-service/src/test/java/com/example/demo/support/ServiceTestSupport.java) 初始化 Mapper/TableInfo，通过 `ArgumentCaptor` 验证可信 ID、企业范围、审计值和状态。事务代理设施 [TransactionProbe](../demo-service/src/test/java/com/example/demo/support/TransactionProbe.java) 仅验证 advice。校验代理样例见 [ValidateAspectTest](../demo-service/src/test/java/com/example/demo/role/service/ValidateAspectTest.java)。

外部平台测试使用虚构数据，不访问真实支付、短信或通知服务。时间与随机数可控；静态 mock、时区、系统属性和 SecurityContext 用后恢复。协议与密码学需要独立固定向量，仅往返加解密不足以证明兼容。

## 判断成功与报告

检查退出码、reactor、目标模块 `target/surefire-reports/` 中失败/错误/跳过及实际执行数量。预期异常可能输出 ERROR，按报告判断。报告本次准确命令、范围、结果和未验证项，不使用历史数量证明本次通过。

按“目标测试 → 模块与上游 → 受影响消费者”推进。纯文档不新增业务测试。数据库、协议、序列化或依赖变更按风险扩大验证。当前无配置好的 JaCoCo 门槛、Checkstyle、Spotless、PMD 或独立 Failsafe 流程，不能声称 `verify` 自动执行这些检查。
