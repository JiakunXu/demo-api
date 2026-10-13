# 开发环境与构建

## 基线与依赖

当前版本见 [父 POM](../pom.xml) 和 [Wrapper 配置](../.mvn/wrapper/maven-wrapper.properties)。使用 JDK 25 与 Maven 3.9.16；实际 JVM 以 `java -version` 和 Maven `-version` 为准。IDE、终端和 CI 各自核对，`JAVA_HOME` 指向 JDK 根目录。

优先使用 Wrapper。首次可能下载 Maven；离线 Maven 参数不能替代未缓存的 Wrapper 发行包。受限环境可使用已有兼容 Maven；不要把本机绝对路径写进 POM。

从仓库根执行：

```bash
java -version
./mvnw -version
./mvnw -DskipTests test-compile
```

最后一条验证所有模块的主代码和测试代码编译，不执行测试。单元测试参见 [测试指南](testing.md)。父 POM 管理公共版本与 BOM，子模块按需声明依赖。新增依赖先检查现有工具，升级时核对 JDK、Boot、Cloud、MyBatis 与 SDK 兼容性，并编译受影响消费者。

## 配置与启动前提

应用配置入口为 [application.yml](../start/src/main/resources/application.yml)，日志入口为 [logback-spring.xml](../start/src/main/resources/logback-spring.xml)。配置还可能只出现在 `@Value` 或 SDK 配置类中，不能只扫描 YAML。

按启用功能核对 MySQL、Redis、注册/配置中心、RocketMQ、MQTT、XXL-JOB 和平台证书。MQTT [ProducerBean](../demo-manager/src/main/java/com/example/demo/mqtt/manager/ProducerBean.java) 初始化需要平台参数；空白配置可能在业务调用前就阻塞启动。

使用外部配置或环境变量覆盖个人设置，禁止提交真实凭据。当前没有完整数据库初始化、独立测试 profile 或容器依赖编排，空白环境不能假定直接可运行。新增本地环境方案需明确哪些外部功能启用以及数据隔离方式。

打包与启动步骤见 [运行手册](runbook.md)。不要将启动全应用作为纯单元测试的前置条件。

## 常见构建问题

| 表现 | 排查方向 |
| --- | --- |
| Java 版本或编译目标不匹配 | 检查 Maven 实际 JVM、IDE Maven runner、JAVA_HOME |
| 依赖解析失败 | 区分版本不存在、缓存缺失、镜像设置、网络和权限 |
| 测试静态 mock 无法初始化 | 按测试指南使用已解析 Mockito agent |
| POI/字体测试失败 | 检查图形环境，必要时临时启用 headless |
| 全应用测试初始化失败 | 找最早有效原因，检查外部配置与 Bean 初始化 |

不能通过跳过断言、全局忽略异常或关闭认证来修复上述问题。
