# 开发环境、构建与依赖管理

状态：技术基线与项目规范。维护于 2026-10-13。配置用途和消费者见 [配置说明](configuration.md)，测试选择见 [测试指南](testing.md)，模块关系见 [架构](architecture.md)。

## 技术基线与依赖

当前根 `pom.xml`：Java **25**、Spring Boot **4.1.1**、MyBatis-Plus **3.5.17**、Fastjson2 **2.0.65**、Lombok **1.18.48**。Maven Wrapper 指定 **3.9.16**。这些是仓库当前版本，不是要求永远固定的版本，也不是对所有第三方兼容性的保证。

- 默认使用 JDK 25，先用 `java -version` 和 Maven `-version` 确认实际 JVM；IDE 和终端可能使用不同 JDK。`JAVA_HOME` 应指向 JDK 根目录。
- 仓库带 `mvnw`、`mvnw.cmd` 与 `.mvn/wrapper/maven-wrapper.properties`。优先 Wrapper；受限环境缺少发行包时可使用已安装且兼容的 Maven，例如 IDEA 自带 Maven。
- 以下命令从仓库根目录执行。Unix 使用 `./mvnw`，Windows 使用 `mvnw.cmd`；若选用本机 Maven，替换命令前缀即可。
- Wrapper 首次运行可能下载 Maven；Maven `-o` 只控制依赖/插件解析，不能使未缓存的 Wrapper 发行包凭空可用。
- 依赖版本优先由父 POM 的 `properties` / `dependencyManagement` 和已有 BOM 管理。子模块按实际需要声明依赖，测试依赖必须使用 `test` scope。
- 新增依赖前检查现有能力；避免为小功能引入框架或重复 JSON/HTTP 工具链。确需新增时说明用途、兼容性、维护状态及许可证影响。
- 升级时检查 JDK、Spring Boot/Framework/Security、Cloud/Alibaba、Dubbo、MyBatis、SDK 和注解处理器的兼容性。引用对应官方文档/发行说明，不凭版本号相近判断兼容。
- 不通过关闭版本兼容检查、全局忽略警告或大范围 `--add-opens` 掩盖问题；先定位依赖链与实际调用。
- 不将开发者绝对路径、私有 Maven 仓库凭据或临时 JVM 参数写入 POM。修改依赖后编译所有受影响消费者并执行相关测试。

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

## 依赖变更的实施顺序

先从父 POM 与模块 POM 确认直接/传递依赖、BOM 和注解处理器，再核对对应官方兼容说明。明确为什么升级、受影响契约和回退范围，分阶段编译和测试。

升级后检查新 API 的异常、序列化、代理、Bean 装配和客户端行为。Lombok 警告要区分注解处理器与应用代码来源，不能为消除日志全局关闭警告。SDK 兼容性不能只通过编译推断。

Maven Wrapper 版本、实际 JVM 和本地缓存是不同维度；选择已有 Maven 时报告实际命令。禁止把个人路径、凭据或一次性 JVM 参数固化进 POM。
