# 依赖管理、升级与兼容验证

状态：POM 已核查现状与项目开发规范。维护于 2026-10-13。适用于父 POM、子模块依赖、BOM、构建插件、SDK 升级和弃用 API 处理。本文不要求升级到最新版本，也不证明当前全部依赖组合经过官方认证或真实平台验证。

本页集中维护依赖约定；JDK/Maven 使用见 [开发环境](development.md)，测试范围与临时 JVM 参数见 [测试指南](testing.md)，类型/API 迁移语义见 [Java 约定](java-conventions.md)。默认 YAML 配置项完整、地址和账号有效，不核查或改写配置取值。

## 1. 版本与管理位置

| 位置 | 当前职责 | 修改时注意 |
| --- | --- | --- |
| [根 pom.xml](../pom.xml) 的 parent | 继承 spring-boot-starter-parent 4.1.1 | 同时影响 Boot 依赖与插件默认管理，不只影响启动类 |
| 根 properties | Java 25、UTF-8、项目指定库版本 | 属性只有被引用或被父 POM 约定使用才生效 |
| 根 dependencyManagement | 指定公共依赖版本、scope、exclusions，导入 BOM | 管理条目不等于添加到 classpath |
| 子模块 dependencies | 声明模块实际需要的依赖 | 无 version/scope 时核对继承的管理结果 |
| 根 build/pluginManagement | 配置 maven-compiler-plugin 的 Lombok annotationProcessorPaths | 依赖树不完整描述编译器插件及处理器环境 |
| 子模块 build/plugins | Open 的 assembly；Start 的 Boot repackage | 检查实际执行阶段、配置与最终产物 |
| [Wrapper 配置](../.mvn/wrapper/maven-wrapper.properties) | Maven 3.9.16，Wrapper 元数据版本 3.3.4 | Maven 发行版、Wrapper 实现、JVM 是不同维度 |
| Maven settings / 环境 | 镜像、本地仓库、认证及活动配置 | 不在文档/POM 中写个人路径或凭据 |

Spring Boot parent 提供依赖和插件管理；单独导入 Boot BOM 不提供同样的插件管理。不要在更换 parent 时仅补一个 BOM 便认为原构建行为不变。[Spring Boot 官方说明](https://docs.spring.io/spring-boot/maven-plugin/using.html)

### 当前项目显式版本属性

下面直接记录根 POM 的 version 属性；它们是声明基线，不是本次解析的完整依赖树，也不是版本升级推荐。父 POM/BOM 管理的 JUnit、Mockito、Spring Framework/Security、MySQL 驱动等版本不在此凭记忆填写。

| 属性 | 根 POM 当前值 |
| --- | --- |
| `java.version` | `25` |
| `alipay-easysdk.version` | `2.2.3` |
| `aliyun-sdk-oss.version` | `3.18.5` |
| `commons-collections4.version` | `4.6.0` |
| `commons-io.version` | `2.22.0` |
| `commons-lang3.version` | `3.21.0` |
| `commons-pool2.version` | `2.13.1` |
| `dashscope-sdk-java.version` | `2.23.1` |
| `druid-spring-boot.version` | `1.2.28` |
| `dubbo-spring-boot.version` | `3.3.6` |
| `fastjson2.version` | `2.0.65` |
| `google-maps-services.version` | `2.2.0` |
| `httpclient5.version` | `5.6.4` |
| `httpcore5.version` | `5.4.4` |
| `jjwt.version` | `0.13.0` |
| `joda-time.version` | `2.14.4` |
| `lombok.version` | `1.18.48` |
| `mybatis-plus-spring-boot4.version` | `3.5.17` |
| `nacos-client.version` | `3.2.4` |
| `poi.version` | `5.5.1` |
| `rocketmq-spring-boot.version` | `2.3.6` |
| `server-sdk.version` | `1.0.9.Final` |
| `wechatpay-java.version` | `0.2.17` |
| `xxl-job.version` | `3.4.2` |

根还以字面量管理部分库，例如 Spring Cloud Alibaba BOM 2025.1.0.0、Sentinel datasource 1.8.10、钉钉/阿里云/个推/华为 SDK 等；完整清单以根 dependencyManagement 为准。不是所有库都具有独立 properties 字段。

## 2. 继承、BOM 与解析结果

本项目继承 Boot parent，另外直接导入 `com.alibaba.cloud:spring-cloud-alibaba-dependencies:2025.1.0.0`，type=pom、scope=import。根 POM 未另外直接导入 Spring Cloud release-train BOM；不凭模块名称假定存在，完整结果通过 effective POM 确认。

Maven 的管理条目为实际依赖提供版本、scope 等信息；BOM 导入的是管理清单。冲突解析不是“最高版本自动获胜”。管理、直接声明和传递路径都影响结果；需要证据时同时检查 effective POM 与依赖树。[Maven 依赖机制](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)

本项目规定：公共版本优先由父 properties / dependencyManagement 或既有 BOM 管理；子模块只按职责声明依赖。不要为解决一个类缺失在所有模块重复固定版本。确需覆盖时写明坐标、原因、消费者与回退，并核对最终解析结果，不能只修改一个看似相关的属性。

直接使用某个库的模块宜显式声明该依赖，减少对偶然传递路径的依赖；现有布局调整须单独评估，本文不自动修改 POM 或模块边界。

## 3. 模块中的实际声明

以下列主要直接声明，不是全部传递依赖；仓库内模块关系见 [架构说明](architecture.md)。

| 模块 POM | 主要外部依赖及职责 |
| --- | --- |
| [demo-api](../demo-api/pom.xml) | Fastjson2、Spring Security、支付宝/微信支付 SDK、Lombok；部分对外接口直接使用 SDK 类型 |
| [demo-dao](../demo-dao/pom.xml) | MyBatis-Plus Boot 4 starter、Fastjson2、Lombok |
| [demo-util](../demo-util/pom.xml) | Commons 系列、HttpClient5、POI、Joda-Time、Spring beans/tx、Fastjson2、验证码 filters |
| [demo-open](../demo-open/pom.xml) | 直接依赖 demo-api；使用自定义 assembly 描述构建对外包 |
| [demo-manager](../demo-manager/pom.xml) | 云/支付/消息 SDK，Redis、WebFlux、WebSocket starter；封装平台和通用能力 |
| [demo-service](../demo-service/pom.xml) | JJWT、Dubbo、Dubbo Nacos starter、AspectJ starter；业务编排与认证 |
| [demo-web](../demo-web/pom.xml) | WebMVC、AspectJ、Sentinel/Nacos、Fastjson Spring 5/6 扩展；Web 适配 |
| [demo-task](../demo-task/pom.xml) | XXL-JOB 与 Lombok；任务执行 |
| [start](../start/pom.xml) | MySQL 驱动、阿里云日志 appender；Boot 可执行 JAR |

starter-test 当前由 demo-util、demo-manager、demo-service、start 以 test scope 声明。主代码不得依赖仅测试 classpath 的类。Manager 声明 WebFlux 不代表整个项目转为响应式应用；WebMVC 仍由 Web 层使用，实际装配以代码和产物验证。

## 4. scope、排除与打包现状

根将 Lombok 管理为 provided，子模块未写 scope 时可继承它；同时编译器显式配置 Lombok 注解处理器。不能从子 POM 没有 scope 推断其有效结果就是 compile。

根将 jjwt-impl / jjwt-jackson 管理为 runtime，jjwt-api 用于编译；Service 没重复 scope。升级时核对三者解析版本与运行产物，不能因编译成功推断运行时实现和序列化组件齐全。

根存在以下 exclusions：

| 依赖 | 当前排除项 | 升级时验证 |
| --- | --- | --- |
| dashscope-sdk-java | slf4j-simple | 是否由应用提供合适日志实现，SDK 日志仍可用 |
| aliyun-log-logback-appender | protobuf-java、commons-logging | 是否存在有效替代路径，运行是否出现缺类/方法冲突 |
| aliyun-sdk-oss | commons-logging | 实际日志桥接及 SDK 行为 |

排除只作用于相应依赖路径，不保证其他路径也不再引入。保留现有排除不代表原因仍适用于升级后的版本；删除或增加前追踪依赖树并验证消费者。

Druid Boot 3 starter 当前在父 POM 被管理，但 Start 的依赖声明处于注释中。HTTP core5/h2、Nacos client、Fastjson 两套扩展等也要区分“管理条目”“直接声明”“实际传递引入”；没有完成解析时不把它们判断成一定加载或一定未使用。

[Start POM](../start/pom.xml) 执行 Boot repackage；[Open POM](../demo-open/pom.xml) 在 package 执行 assembly，且 [描述文件](../demo-open/src/main/resources/jar-with-dependencies.xml) 只解包指定包与资源。Open 的 jar-with-dependencies 不是 Start 可执行 JAR 的替代品；SDK、ServiceLoader/META-INF 或运行依赖变更需要核对相应产物内容。

插件版本与项目库版本分开管理。根没有固定所有插件的版本；读取 effective POM 与实际执行结果，不声称依赖树已覆盖插件运行环境。[Maven 插件配置](https://maven.apache.org/guides/mini/guide-configuring-plugins.html)

## 5. 解析与诊断命令

从仓库根执行；优先 Wrapper，使用本机 Maven 时替换前缀并报告实际 JVM。诊断插件也需要缓存或网络，`-o` 仅在所需发行包、POM、依赖及插件已缓存时使用。

```bash
# JVM 与实际 Maven
java -version
./mvnw -version

# 查看目标模块继承后的声明、scope、插件和来源
./mvnw -pl start help:effective-pom \
  -Dverbose=true -Doutput=/tmp/demo-start-effective-pom.xml

# 查看实际解析及冲突省略项；不要只查看聚合根的空依赖树
./mvnw -pl start -am dependency:tree -Dverbose=true

# 定向核对公共框架与 SDK 引入的日志/JSON/网络组件
./mvnw -pl start -am dependency:tree -Dverbose=true \
  '-Dincludes=org.springframework*:*,com.alibaba.cloud:*,com.baomidou:*,org.slf4j:*,com.fasterxml.jackson*:*,io.netty:*'

# 核对测试模块实际解析的 Mockito，不猜测 Boot 默认版本
./mvnw -pl demo-service -am dependency:tree \
  '-Dincludes=org.mockito:*'

# 仅输出本地仓库位置；避免公开完整 settings 和认证信息
./mvnw help:evaluate -Dexpression=settings.localRepository -q -DforceStdout
```

`help:effective-pom` 展示继承与活动 profile 合并后的模型，verbose 可标记声明来源；它不替代实际依赖冲突解析。[effective-pom 参数](https://maven.apache.org/plugins/maven-help-plugin/effective-pom-mojo.html)

`dependency:tree` 展示解析结果，includes 过滤输出，verbose 可包含省略项。过滤树不能证明被过滤掉的库不存在；运行时和测试范围按任务分别检查。[dependency:tree 参数](https://maven.apache.org/plugins/maven-dependency-plugin/tree-mojo.html)

`-am` 选择上游项目，但直接运行 tree 这类插件目标不会自动替它们构建当前产物。若诊断使用旧本地产物或无法解析仓库内依赖，需要在同一 reactor 先构建再诊断，例如：

```bash
./mvnw -pl start -am -DskipTests package dependency:tree -Dverbose=true
```

该命令会编译测试并打包，不执行测试，也不是测试通过证据；仅在需要构建当前产物时使用，不为普通文档修改执行。诊断输出置于临时路径，审阅后仅记录必要且脱敏的结论，不提交生成的 effective POM 或完整环境信息。

## 6. 新增与覆盖的项目规则

- 依赖版本优先由父 POM 的 `properties` / `dependencyManagement` 和已有 BOM 管理。子模块按实际需要声明依赖，测试依赖必须使用 `test` scope。
- 新增依赖前检查现有能力；避免为小功能引入框架或重复 JSON/HTTP 工具链。确需新增时说明用途、兼容性、维护状态及许可证影响。
- 升级时检查 JDK、Spring Boot/Framework/Security、Cloud/Alibaba、Dubbo、MyBatis、SDK 和注解处理器的兼容性。引用对应官方文档/发行说明，不凭版本号相近判断兼容。
- 不通过关闭版本兼容检查、全局忽略警告或大范围 `--add-opens` 掩盖问题；先定位依赖链与实际调用。
- 不将开发者绝对路径、私有 Maven 仓库凭据或临时 JVM 参数写入 POM。修改依赖后编译所有受影响消费者并执行相关测试。

另外，新增直接依赖放在真正使用它的模块；为测试加入的容器/工具保持 test scope。不要改为 systemPath 引用个人 JAR，不用动态版本或未经验证的 SNAPSHOT 作为正常发布基线。明确维护状态、许可证及供应链来源，出现实际问题时依据官方公告与受影响路径处理，不把单一扫描结果当作兼容性证明。

## 7. 兼容性核查矩阵

这是结合源码的核查方向，不是当前组合已获得官方兼容认证的结论。升级时阅读目标版本官方发行/迁移说明，记录对应版本与日期；不依赖本文维护日之后的“最新版本”猜测。

| 组合 | 本项目需重点核查 |
| --- | --- |
| JDK / Lombok / compiler | Java 目标、注解处理器加载、处理器与 JDK API、编译期告警来源 |
| Boot / Framework / Security / Data | parent 管理结果、starter 与自动配置变化、Web 转换器、过滤器、密码与序列化契约 |
| Boot / Cloud Alibaba / Sentinel / Nacos / Dubbo | 官方支持范围、BOM 覆盖、实际 starter/客户端组合、代理与注册行为 |
| Boot / MyBatis-Plus | Boot 4 starter、ServiceImpl 包名、Mapper 注入、主键与逻辑删除、XML 绑定 |
| Fastjson / Spring 扩展 / Redis | Spring 5/6 扩展的实际使用、HttpMessageConverter、Redis 序列化、BO/DO 注解映射 |
| HttpClient5 / HttpCore5 / HTTP SDK | core/http2 传递版本、资源释放、超时、响应体与异常行为 |
| WebMVC / WebFlux / WebSocket / Netty | 共存后的装配、客户端与服务端使用位置、线程与生命周期 |
| JJWT / JSON / Security | api/impl/jackson 版本与 scope、签名解析、JWT ID、缓存和令牌失效 |
| 支付/云/消息 SDK / 日志 | 签名与协议、固定向量、重试幂等、日志桥接、Netty/JSON 等共享依赖 |
| POI / 图形 / JDK | 文件兼容、流与资源、字体/headless、真实导出行为 |
| Boot repackage / Open assembly | runtime 库、过滤资源、可执行/对外产物和消费方 classpath |

没有官方支持说明或所需环境证据时记录尚未验证的范围；编译、mock 测试、应用装配与真实协议兼容分别表述。默认配置有效不代表已经执行所有平台回归。

## 8. 升级实施步骤

1. 定义目标与范围：升级原因、具体坐标、旧/新版本、受影响模块和消费者；复杂升级按 [PLANS.md](../PLANS.md) 分阶段推进。
2. 保存基线：核对当前 JVM、声明来源、effective POM、目标依赖树与已有验证；不要顺手升级所有库。
3. 查目标官方资料：支持范围、破坏性变更、弃用与移除、配置/协议/序列化变化、许可证；没有证据的结论标为待验证。
4. 修改最小版本来源：parent、BOM 或具体 property/管理条目；同步实际消费者 API 与处理器，不在子模块各自补版本掩盖冲突。
5. 重新解析比较：确认目标版本真正选中，scope/exclusions 未意外改变，运行/测试库与打包插件仍齐全。
6. 按影响验证：先目标回归，再模块及上游，最后公共消费者编译和必要的打包/装配/隔离集成。发现实际失败再扩大范围。
7. 审阅与交付：只包含本次升级、迁移及必要文档，列明真实命令与结果、未验证项和回退条件。

如果升级改变公共 SDK 类型、数据库字段、缓存或响应协议，应先明确新旧版本兼容窗口。回退版本必须连同相应 API 适配一起评估；已写入的新格式数据、消息和外部操作不会自动因 POM 回退恢复。

## 9. 下游验证与打包

| 修改 | 最低验证方向 |
| --- | --- |
| 父 parent、BOM、公共版本 | 全模块主代码与测试代码编译，受影响模块测试，必要产物检查 |
| API 的支付 SDK / Security 类型 | API/Open 及全部消费者编译，真实序列化与接口兼容 |
| Util 公共工具库 | 工具回归、Manager/Service 消费者与传递版本 |
| Manager Redis/HTTP/SDK | 参数、解析、异常和资源回归，Service 消费者；协议需独立验证 |
| DAO/MyBatis-Plus | CRUD 与代理回归、Mapper 绑定、真实 SQL/约束/默认值 |
| Web JSON/Security/starter | 请求响应、序列化、认证与装配；不能只运行 Service mock |
| Task/Start/构建插件 | 任务生命周期及 JAR 内容，按授权验证装配与外部初始化 |

以下为按需选用的入口，命令须配合 [测试指南](testing.md) 的 JVM 参数和结果判断：

```bash
# 全模块主代码与测试代码编译，不执行测试
./mvnw -DskipTests test-compile

# Service 和上游单元测试；不包含 start 的上下文测试
./mvnw -pl demo-service -am test

# 仅在验证受影响产物时打包；测试应已另外完成
./mvnw -pl start -am -DskipTests package
```

`-DskipTests` 不证明测试通过。目标测试实际执行数须非零；省略/失败/环境阻塞分别报告。Mockito agent/headless 继续采用临时命令参数，不新增 POM 配置。不要为文档或依赖诊断访问真实支付/通知平台。

## 10. 弃用 API 与告警处理

先区分应用代码、仓库中的第三方来源代码、注解处理器、传递依赖、测试动态 agent 和 JVM 警告，再决定修复位置。标记 Deprecated 不等于已移除，但 forRemoval、目标版本移除和实际行为变化需优先评估。不要批量 suppress 或改 import 便认为语义保持。

| 已采用的迁移模式 | 本项目参考位置 | 需要保留的语义 |
| --- | --- | --- |
| MyBatis-Plus ServiceImpl 新包 | [BannerServiceImpl](../demo-service/src/main/java/com/example/demo/banner/service/BannerServiceImpl.java) | Boot 4 starter、注入与 CRUD 行为 |
| Spring NonNull → JSpecify | [BeanUtil](../demo-util/src/main/java/com/example/demo/framework/util/BeanUtil.java) | 静态空值契约，不替代运行期业务校验 |
| Redis long+TimeUnit → Duration | [RedisServiceImpl](../demo-manager/src/main/java/com/example/demo/cache/manager/RedisServiceImpl.java) | 秒单位、默认 TTL、期限与异常 |
| StringUtils 前缀 → Strings.CS/CI | [RouterServiceImpl](../demo-service/src/main/java/com/example/demo/router/service/RouterServiceImpl.java) | CS/CI 的大小写与 null 行为 |
| URL(String) → URI 转 URL | [钉钉 OssServiceImpl](../demo-manager/src/main/java/com/example/demo/dingtalk/manager/OssServiceImpl.java) | URI 语法、异常、资源及 URL 来源 |
| Class.newInstance → 构造器反射 | [ExcelUtil](../demo-util/src/main/java/com/example/demo/framework/util/ExcelUtil.java) | 构造器可访问性、原因链和受检异常 |
| 框架 UID → 类自身稳定 UID | [LoginUser](../demo-api/src/main/java/com/example/demo/security/api/bo/LoginUser.java) | 保持历史兼容值与 @Serial |

这些是源码样例，不是声明全仓库无弃用项。例如第三方来源的 [ConfigHelper](../demo-util/src/main/java/com/google/code/kaptcha/util/ConfigHelper.java) 仍有 Class.newInstance 调用，按修改范围和来源单独处理，不为本文件扩大代码改动。

处理器/SDK 的 Unsafe 等告警先定位实际 JAR、解析版本及调用阶段，查官方修复说明后选择兼容版本；普通业务代码重写不能修复工具自身内部调用。测试动态 agent 警告按测试指南诊断。若需要附加编译告警参数，先确认当前 Maven compiler 配置支持并且确实生效，不在文档中凭空给未验证参数。

## 11. 维护与完成标准

POM、BOM、scope/exclusions、SDK 契约、Wrapper 或插件变化时同步本文的相应现状与验证方向。基线来自 POM；解析版本来自本次命令；兼容结论来自对应官方支持说明和实际行为证据，三者不能混写。

交付说明应包含：变更原因、旧/新坐标与来源、模块/消费者、实际解析变化、代码适配、测试/编译/产物证据、未验证范围和回退条件。只有文档修改时检查 POM 对照、链接、命令语法及 diff，无需重跑业务测试。

本文件中的 Maven/Boot 机制参考对应官方文档；版本清单和 Java 工程规范来自本仓库，不是 OpenAI 强制依赖选型。Codex 在任务中先读取本页和实际 POM，再根据证据实施，不依靠旧聊天的依赖数字推断现状。
