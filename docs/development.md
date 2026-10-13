# 开发环境、构建与依赖管理

状态：技术基线与项目规范。维护于 2026-10-13。依赖管理与升级见 [依赖指南](dependencies.md)，配置用途和消费者见 [配置说明](configuration.md)，测试选择见 [测试指南](testing.md)，模块关系见 [架构](architecture.md)。

## 技术基线与依赖

当前根 `pom.xml`：Java **25**、Spring Boot **4.1.1**、MyBatis-Plus **3.5.17**、Fastjson2 **2.0.65**、Lombok **1.18.48**。Maven Wrapper 指定 **3.9.16**。这些是仓库当前版本，不是要求永远固定的版本，也不是对所有第三方兼容性的保证。

- 默认使用 JDK 25，先用 `java -version` 和 Maven `-version` 确认实际 JVM；IDE 和终端可能使用不同 JDK。`JAVA_HOME` 应指向 JDK 根目录。
- 仓库带 `mvnw`、`mvnw.cmd` 与 `.mvn/wrapper/maven-wrapper.properties`。优先 Wrapper；受限环境缺少发行包时可使用已安装且兼容的 Maven，例如 IDEA 自带 Maven。
- 以下命令从仓库根目录执行。Unix 使用 `./mvnw`，Windows 使用 `mvnw.cmd`；若选用本机 Maven，替换命令前缀即可。
- Wrapper 首次运行可能下载 Maven；Maven `-o` 只控制依赖/插件解析，不能使未缓存的 Wrapper 发行包凭空可用。

## 构建与启动

### 打包与启动

```bash
# 仅打包，不表示测试已通过；适合测试已另行执行或打包诊断
./mvnw -pl start -am -DskipTests package

# 核对实际产物名后启动；以下对应当前版本
java -jar start/target/start-1.0.jar
```

启动前核对 `start/src/main/resources/application.yml` 和相关 `@Value` / 配置类。当前配置绑定 `127.0.0.1:8080`；外部依赖涉及 MySQL、Redis、注册/配置中心、MQ/MQTT、XXL-JOB，以及按功能启用的第三方平台和证书。

- 不把完整启动当作纯单元测试前置条件。初始化 Bean 可能连接外部服务或注册任务；只有明确需要并具备适当环境时才启动。
- 使用环境变量、外部配置文件或适当的本地配置覆盖真实凭据，不把个人配置提交到仓库。
- 当前没有完整的独立测试 profile、容器化依赖编排或数据库初始化流程；不能假设空白机器可以直接启动。配置键不仅存在于 YAML，也可能仅在 `@Value` 中出现。
- `ApplicationTests.contextLoads` 使用 `@SpringBootTest` 加载全应用，受真实配置和初始化逻辑影响。单元测试全部通过不等于它通过；必须分别报告。

## 配置协作前提

按用户明确约定，默认 YAML 配置项完整、地址和账号有效；不将配置缺失、地址失效或账号无效作为推测性阻塞，不核查或改写其中取值。[configuration.md](configuration.md) 只解释键、类型、用途和源码消费者。

完整应用启动会初始化组件或注册任务，配置有效不等于本次任务已授权对真实平台进行测试写入。纯单元测试继续隔离外部平台。出现实际运行错误时根据异常与代码定位，不预先归因于 YAML 值。

## 依赖变更入口

版本管理位置、BOM 与实际解析、scope/exclusions、SDK 兼容性、升级步骤及弃用 API 处理集中维护于 [dependencies.md](dependencies.md)。修改 POM 前读取对应模块和该指南，按测试选择矩阵编译与验证消费者；本页只维护环境与构建入口。
