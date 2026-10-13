# demo 项目协作指南

适用于仓库根目录及全部子模块。面向在本仓库阅读、修改、测试和审查代码的 AI 编程代理。
使用中文沟通；代码标识符沿用项目英文命名。本文维护于 2026-10-13，版本、命令和代码位置应随项目演进更新。

## 1. 使用规则与工作流程

- 遵守运行环境的系统规则及用户当前明确要求；本文记录项目默认规范。更深目录的 `AGENTS.md` 可补充或覆盖该目录的项目约定，修改前检查目标目录的指令文件。
- 先读取相关代码、调用方和测试，再修改；不要仅依据类名、旧聊天记录或 README 推断行为。项目现状以 POM、源码、配置和实际运行结果为依据。
- 开始时检查 `git status --short`、相关 diff。保留用户已有修改、未跟踪文件和配置；不要擅自恢复文件、清理工作区或大范围格式化。
- 明确需求的输入、输出、失败场景及兼容性；简单任务直接完成。跨模块重构、依赖升级、数据库或协议调整，先列出影响范围、步骤和验收方式，再分阶段实施。
- 常规实现选择自行判断。只有缺少关键业务信息且无法从代码确定时才提问；不为普通读写文件、局部修复或测试反复确认。
- 修改范围围绕当前需求。发现无关缺陷时记录文件位置、触发条件和影响，不借机改写整个模块。
- 搜索优先使用 `rg` / `rg --files`，排除 `.git/`、`target/` 和 IDE 缓存。阅读配置、日志和差异时避免输出密钥。
- 长任务保留简短进度、关键决定、已验证事项和下一步；确有跨会话需要时才增加任务计划文档，不为每个小改动建立流程文件。
- 不因本文授权而自动提交、推送、创建 PR、合并或发布。用户已明确授权的操作按其范围执行；涉及真实外部系统的写入应确认环境和既有授权。

### 专项文档阅读入口

按任务读取 [文档导航](docs/README.md) 中的相关材料，普通 Markdown 不假定自动加载。构建/启动读 `docs/development.md` 与 `docs/runbook.md`，添加或运行测试读 `docs/testing.md`，架构调整读 `docs/architecture.md`，审查读 `docs/code-review.md`。复杂跨模块、迁移或需跨会话的任务遵循 [PLANS.md](PLANS.md)；简单修复无需独立计划。新需求和长期决策分别使用 `docs/requirements/` 与 `docs/decisions/`。代理专属文档仅面向 Codex，技能说明不表示已安装或已改变权限。

## 2. 项目结构与依赖边界

这是 Maven 多模块 Java 后端，父工程坐标为 `com.example:demo:1.0`。下列路径均相对于仓库根目录。

| 模块 | 职责与代码入口 | 主要仓库内依赖 |
| --- | --- | --- |
| `demo-api` | `com.example.demo.<业务>.api` 接口、`api.bo` 业务对象，以及 `framework.bo/exception/constant` | 无其他子模块依赖 |
| `demo-dao` | `<业务>.dao.dataobject`、`<业务>.dao.mapper`；SQL 在 `src/main/resources/mappers/` | 无其他子模块依赖 |
| `demo-util` | 通用工具 `com.example.demo.framework.util`；另含第三方来源的验证码、微信加解密代码 | 无其他子模块依赖 |
| `demo-open` | 对外服务契约与 assembly 打包描述；当前 `ICorpService` 为空接口 | `demo-api` |
| `demo-manager` | Redis、HTTP、消息、文件、第三方 SDK、WebSocket/SSE 等通用能力封装 | `demo-dao`、`demo-open`、`demo-util` |
| `demo-service` | 业务编排、权限/数据范围、事务、缓存协调；多数实现继承 MyBatis-Plus `ServiceImpl` | `demo-dao`、`demo-manager`、`demo-open`、`demo-util` |
| `demo-web` | Controller、请求/响应、Spring Security、过滤器、异常处理、Web 配置 | `demo-service` |
| `demo-task` | XXL-JOB 配置与任务 | `demo-service` |
| `start` | Spring Boot 启动及应用配置，生成可执行 JAR | `demo-task`、`demo-web` |

开发时遵循以下边界：

- Web 负责协议适配、参数和访问控制；业务流程放 Service，外部 SDK 细节放 Manager，SQL 放 DAO，共用的无业务工具放 Util。
- API/BO 不依赖 Service、Web 或 DAO 实现；不要引入模块循环依赖。当前部分 API 已使用第三方 SDK 类型，迁移这类契约需单独评估调用兼容性。
- 不因接口名称带 `Service` 就认定实现位于 `demo-service`：Redis、微信、支付等多个接口实际由 Manager 实现。
- 同名实现跨业务包存在，例如多个 `TokenServiceImpl`、`MessageServiceImpl`、`OssServiceImpl`。检查全限定类名、显式 Bean 名、`@Qualifier` / `@Resource`，避免注入歧义。
- 业务主体包括用户/企业/角色/菜单、配置/字典/日志、聊天/订阅、文件，以及微信、钉钉、字节跳动、支付宝、微信支付和云平台集成。
- 不把新功能随意塞入 `framework`。新增抽象应有明确复用需求，优先扩展已有业务包。

### 优先阅读的实现

| 修改类型 | 参考位置 |
| --- | --- |
| 普通 CRUD | `demo-service/src/main/java/com/example/demo/banner/service/BannerServiceImpl.java` |
| 权限、关联批量变更 | `demo-service/src/main/java/com/example/demo/user/service/UserRoleServiceImpl.java`、`role/service/RoleServiceImpl.java`、`role/service/RoleMenuServiceImpl.java` |
| 动态 SQL 与分页 | `demo-dao/src/main/resources/mappers/DictDataMapper.xml`、`RoleMapper.xml`、`ConfigMapper.xml` |
| 通用查询/持久化字段 | `demo-api/src/main/java/com/example/demo/framework/bo/BaseBO.java`、`demo-dao/src/main/java/com/example/demo/framework/query/BaseQuery.java`、`framework/dataobject/BaseDO.java` |
| Web 契约与安全 | `demo-web/src/main/java/com/example/demo/framework/`，特别是 `web/BaseController.java`、`config/WebSecurityConfig.java`、`config/JsonConfigurer.java`、`handler/BaseExceptionHandler.java` |
| Service 测试设施 | `demo-service/src/test/java/com/example/demo/support/ServiceTestSupport.java`、`TransactionProbe.java` |
| Manager 测试样例 | `demo-manager/src/test/java/com/example/demo/cache/manager/RedisServiceImplTest.java` |
| 事务提交后操作 | `demo-util/src/main/java/com/example/demo/framework/util/TransactionUtil.java` |

参考类提供局部模式，不代表其所有功能均已完整或经过真实数据库验证。

## 3. 技术基线、环境与依赖管理

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

## 4. 构建、测试与启动命令

### 日常验证

```bash
# 检查环境
java -version
./mvnw -version

# 全模块编译，同时编译测试源码，但不执行测试
./mvnw -DskipTests test-compile

# Service 及其上游模块的单元测试（包括 Manager、Util，不包含 start）
./mvnw -pl demo-service -am test

# 单个模块及上游：按本次修改选择
./mvnw -pl demo-manager -am test
./mvnw -pl demo-util -am test

# 定向测试；-am 纳入上游，避免误用本地仓库旧模块产物
./mvnw -pl demo-service -am test \
  -Dtest=LoginLogServiceImplTest,OperateLogServiceImplTest \
  -Dsurefire.failIfNoSpecifiedTests=false

# 全仓库测试：包括需要外部配置的 ApplicationTests
./mvnw test

# 环境具备时的完整生命周期验证
./mvnw verify

# 基本差异检查（同时人工检查新增未跟踪文件）
git diff --check
```

- `-DskipTests` 不是测试通过；`-Dmaven.test.skip=true` 连测试编译也可能跳过，不能用于证明接口改动兼容。
- `-Dsurefire.failIfNoSpecifiedTests=false` 仅用于定向 reactor 测试，让没有匹配类的上游模块继续；必须确认目标测试实际执行，不能把“运行 0 个测试”当成功。
- 仅在依赖及插件已缓存时加 `-o`。失败时区分源码错误、依赖未缓存、网络/权限限制和外部服务不可用，不反复执行同一个必然失败的命令。
- 测试结果查看各模块 `target/surefire-reports/`，同时检查退出码、失败/错误/跳过数量和 reactor 汇总。测试中的预期异常可能打印 ERROR 日志，应结合报告判断。
- 当前没有仓库配置的 Checkstyle、Spotless、PMD、JaCoCo 覆盖率门槛或专门的 Failsafe 集成测试流程。不要声称这些检查已自动执行；`verify` 不会自动提供未配置的检查。

### JDK 25 / 受限运行环境的测试参数

Mockito 的 inline/static mock 在部分环境不能动态挂载 agent；POI/字体相关测试在无图形环境可能需要 headless。遇到对应问题时使用命令行参数，保留项目 POM 的现状：

```bash
# 用实际解析版本和实际 Maven 本地仓库路径替换；不要盲选目录中最高版本
MOCKITO_JAR=/absolute/path/to/mockito-core-resolved-version.jar
test -f "$MOCKITO_JAR"
./mvnw -pl demo-service -am test \
  "-DargLine=-javaagent:\"$MOCKITO_JAR\" -Djava.awt.headless=true"
```

可通过 effective POM / dependency tree 核对 Mockito 版本，通过 Maven settings 核对本地仓库；这些插件也需要可用缓存或网络。设置 `argLine` 前检查是否已有其他 agent 参数（如覆盖率），必要时合并，不要覆盖。项目已明确选择不在 POM 中额外加入 Mockito agent/headless 的 Surefire 配置；需要长期改变时按新的需求实施。

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

## 5. Java、API 与对象约定

- 主包为 `com.example.demo`，沿用现有业务包布局、类命名和 import 风格。Java 通常为 4 空格缩进，POM 为 4 空格，Mapper XML 采用现有 Tab 布局。
- 当前本地 `formatter.xml` 包含多个不同 profile，尚不能视为唯一、自动执行的格式规范。保持目标文件风格；不要擅自全库套用其中一个 profile。
- 适当使用现有 Lombok `@Getter/@Setter/@Slf4j` 等；不要为风格统一给所有对象加 `@Data`，避免无意改变 equals/hashCode/toString。
- `Serializable` 类使用自身稳定的 `serialVersionUID` 和 `@Serial`。不要引用 Spring Security 内部序列化版本常量；已有值无兼容性理由不变更。
- `CollectionUtils.isEmpty/isNotEmpty` 可简化集合 null/empty 判断；数组用数组判断或对应工具，字符串按 blank/empty 的实际语义处理。`List.of()` 不可修改，不能用于后续需要追加元素的集合。
- 延续已迁移 API：MyBatis-Plus `com.baomidou.mybatisplus.spring.service.impl.ServiceImpl`；静态空值契约采用 `org.jspecify.annotations.NonNull`；Redis 时长使用 `Duration`；字符串前缀相关操作采用合适的 `Strings.CS/CI`；URL 构造使用 `URI`；反射实例化使用 `getDeclaredConstructor().newInstance()`。修改时检查异常与大小写语义，不能仅机械换 import。
- 不全局替换 Java `javax.*`：Servlet 等使用 Jakarta，JDK 自身的 `javax.crypto` 等仍是有效包。
- `BeanUtil.copy(source, Class)` 基于 JSON 转换，`BeanUtil.copy(source, Supplier)` 基于 BeanCopier；两者在类型转换、嵌套对象及 JSON 注解上的语义不同，不能仅为性能互换。
- 保留 API 的路径、请求方法、参数、响应结构、日期格式、枚举值、null/空列表语义及错误码；确需改变时同步调用方、测试和兼容说明。
- HTTP 响应沿用 `ObjectResponse/ListResponse/PageResponse` 等。`AbstractResponse.code` 是响应体字段，不应与真实 HTTP status 混为一谈；现有 `BaseExceptionHandler` 没有统一设置同值 HTTP status。
- `JsonConfigurer` 使用 Fastjson2，日期格式为 `yyyy-MM-dd HH:mm:ss`，排除 `codes` 字段。修改序列化配置属于契约变更；支付 SDK 的 snake_case 字段、注解映射也须验证。
- 对外输入应验证类型、长度、范围、枚举和数据归属。不要直接信任客户端传入的审计人、企业 ID、角色权限或可写字段；必要时显式映射允许修改的字段。

### 参数校验的特殊情况

项目 `com.example.demo.framework.annotation.NotNull/NotBlank/NotEmpty` 是自定义注解，由 `ValidateAspect` 对 `com.example.demo.*.service.*` 的 Spring AOP 调用执行：

- 自定义 `@NotNull` 还拒绝空 List 和空数组；`@NotEmpty` 当前主要针对 null 和空字符串；`@NotBlank` 针对 null 和空白字符串。
- 它们不是 Jakarta Bean Validation，也不是 JSpecify 静态空值注解；不得按名称相同直接替换。
- 直接 `new`、私有方法、自调用等路径不能假设经过代理校验。注解变动需要代理层测试，必要的业务校验不能只依赖注解。
- 新增校验要考虑现有调用者；例如“空列表表示清空关联”与“空列表为非法参数”必须依据业务契约区分。

## 6. Service、CRUD 与事务

- 常规 CRUD 参考 `BannerServiceImpl`，同时保留目标业务特有的归属、状态、权限及异常处理。
- 保存时设置必要业务字段与 `createBy`；对已由 MySQL 默认值管理的 `deleted/createTime` 等不重复赋值。数据库 DDL 未确认时先核对，不能假设每张表都有相同默认值。
- BO 需要保存后的 ID 时，在 `save` 后从 DO 回填；不能把返回了原 BO 误认为 ID 已自动同步。`LoginLogService.insertLog` 与 `OperateLogService.insertLog` 当前返回 **void**，仍回填传入对象 ID。
- 审计命名统一：Java `createBy/updateBy`，数据库 `create_by/update_by`。不要再次引入本项目旧字段 `creator/modifier`；第三方协议中的 `creatorId/modifierId` 不属于此次命名规则。
- 普通保存无需包裹通用 `try/catch`；数据库异常交由既有异常处理。确需将唯一键冲突转换为业务提示时捕获 `DuplicateKeyException` 等具体异常；外部 SDK 边界的异常转换按 Manager 契约处理。
- 更新以方法参数中的可信 ID/业务范围为准，审计人来自可信调用上下文。禁止生成没有有效 WHERE 限制的更新/删除。
- `updateById` / `update` 返回 false 时，按已有业务约定处理未更新记录，不能无条件返回成功；批量或幂等操作的零影响行是否正常应单独判断。
- 软删除通常设置 `deleted=true` 和 `updateBy`，保留原范围条件。不要用物理删除替换；也不要在带 `@TableLogic`/全局逻辑删除配置的实体上假设任意自定义 XML 自动过滤删除数据。
- 能用 MyBatis-Plus `getById` / lambda wrapper 表达且语义一致的简单操作可复用；涉及 join、归属过滤、特别排序或聚合时保留自定义 SQL。
- 多条同类独立插入可先构造 DO 集合再 `saveBatch`，处理空集合、批量大小、事务失败以及需要时的 ID 回填映射。不能将依赖前一条生成 ID、不同实体或交错外部副作用的循环机械批量化。
- `ChatServiceImpl.saveOrUpdate(userId, friendId, chat)` 先按业务键执行自定义 `updateChat`，影响行数不为 1 时才插入；不能改成按主键的通用 `saveOrUpdate` 而改变原语义。并发幂等、唯一约束和影响行数约定需另行验证。
- 多表/多步骤原子变更在 Service 层设置合适事务边界；维持现有回滚语义，检查受检异常、自调用、异步线程和代理绕过。
- 数据库事务不能回滚 Redis、MQ、HTTP 或支付操作。需要提交后执行的动作可考虑 `TransactionUtil.afterCommit`：要求活动事务，同步回调，失败时数据库已经提交，既不持久化也不自动重试。可靠投递需求应另行设计 outbox/补偿和幂等。
- 改动角色/菜单/用户关系时检查权限刷新、令牌失效、缓存清理和关联记录生命周期，不能只修改数据库单行。

## 7. MyBatis 与数据库规则

- DO 使用 `@TableName` / `@TableField` 映射，继承 `BaseDO` 的实体具有审计/软删除字段；查询附加属性在 `BaseQuery` 中使用 `@TableField(exist=false)`。
- 当前应用配置 `id-type=AUTO`、`logic-delete-field=deleted`、删除值 1/正常值 0、下划线转驼峰。改用手工 MyBatis 配置或独立测试环境时需显式核对这些行为。
- 计数 API、实现、Mapper 方法、Controller 局部变量使用 `long`；XML `COUNT` 的 `resultType` 使用 `java.lang.Long`。不要与 insert/update/delete 的影响行数 `int` 混淆。
- `pageNo/pageSize` 使用可空 `Long`，页码从 1 开始；`BaseQuery.getOffset()` 在任一参数为 null 时返回 null，否则计算 `(pageNo - 1) * pageSize`。
- 目前 Role、DictData 的可选分页使用以下条件；需要“未传分页则返回完整业务列表”的接口遵循该模式，不硬编码 99/999 条伪装全量：

```xml
<if test="pageNo != null and pageNo > 0 and pageSize != null and pageSize > 0">
	LIMIT #{offset}, #{pageSize}
</if>
```

- 其他 Mapper 尚存在必须分页的查询；不得全库无差别切换。公开列表接口仍应按需求验证页码、页大小上限及偏移溢出，避免非法分页绕过 LIMIT 造成无界查询。可选分页与公开端点的输入约束分别处理。
- count/list 相同过滤条件提取到局部 `<sql id="queryCondition">` 并通过 `<include>` 复用，保留 SQL 表别名与参数作用域；不要把分页/排序加入 count。
- 默认保留 list/get 各自 SELECT 字段，项目已选择不为这两者抽取公共 SELECT 列清单。确有新需求再调整，避免因共用投影改变返回字段。
- 普通 SQL 文本不使用不必要的 CDATA；`<`、`&` 等 XML 特殊字符需要转义或局部 CDATA。MyBatis 的 `<if>/<foreach>` 等动态节点不能放进 CDATA。
- Mapper 格式遵循周边文件：普通 `<if>` 中 SQL 文本比 `<if>` 多一级 Tab；已有单行 `<![CDATA[ AND ... ]]>` 保持其特殊布局，不因统一缩进额外右移。SELECT 列、WHERE/ORDER BY 对齐保持一致。
- 值绑定使用 `#{}`，不拼接用户值。动态列名/排序方向不能通过 `#{}` 代替标识符；应使用固定映射/白名单或 `<choose>`。现有日志 Mapper 的 `${sort} ${dir}` 是需要特别审查的点，非空检查和 ASC/DESC 文本转换并不能防止注入。
- 模糊查询沿用 `WildcardTypeHandler` 的约定，核对转义与前后通配符语义；IN 集合处理空值/空集合，避免 SQL 语法错误或删除范围意外扩大。
- 手写 SQL 显式保留 `is_deleted`、用户/企业范围、状态及 join 过滤条件。检索后再更新不能替代更新语句中的数据范围限制。
- 日期边界、时区、排序稳定性、分页重复/遗漏均属于行为；优化应验证。性能问题先检查调用次数、索引和执行计划，再选择批量查询、索引、分页或缓存，避免无证据重写。
- 每个自定义 Mapper 方法应有对应 XML/注解/注入器映射，检查 namespace、id、参数名、type alias、resultType 和资源打包位置。仅 mock Mapper 的测试无法发现 SQL 未绑定。

### 数据库变更的交付要求（新增开发规范）

当前仓库没有完整的版本化 DDL 或 Flyway/Liquibase 配置。涉及表结构、字段重命名、唯一索引、默认值时：

1. 根据实际目标数据库版本及现有结构编写迁移 SQL，建议放 `sql/migrations/YYYYMMDD_description.sql`；首次需要时创建目录。
2. 说明执行前提、数据回填、兼容窗口、锁表/索引影响和回退办法。区别“查询生成 ALTER 文本”与“实际执行 ALTER”。
3. 同步 Java、Mapper、序列化、测试及受影响消费者，评估滚动发布时新旧代码共存。
4. 在隔离数据库验证；未获真实环境操作授权不得执行迁移。代码编译通过不能证明线上表结构已更新。

## 8. 外部平台、缓存、消息与安全

- Manager 应隔离第三方协议、SDK 和错误映射，使用可替换依赖便于测试。保留平台错误码、业务标识和原因链，但不得泄漏凭据。
- HTTP/SDK 调用明确超时、响应状态、空体/坏 JSON、业务失败处理及资源关闭。重试只用于可重试且幂等的请求；支付/退款/消息发送不可无条件重复执行。
- 支付、退款、分账与回调必须核对签名、商户/应用身份、订单、金额单位、状态转换和重复通知；普通成功 mock 不能证明真实验签正确。
- 密码沿用合适的密码哈希机制（现有认证使用 BCrypt）。不要把通用摘要当密码加密；`EncryptUtil.encryptSha` 当前实际为 SHA-512，协议必须按算法而非方法名核对。
- 保留回调原始请求体及签名相关头的语义，避免重新序列化后验签。证书、时间戳、nonce、重放和幂等测试应按具体平台协议设计。
- Redis TTL 以现有接口的秒为单位并转换为 `Duration`；区分缓存缺失、服务异常、写入失败与过期。缓存键/序列化/有效期改变需要考虑旧缓存兼容和清理。
- 缓存可降级与认证失败不可绕过是不同问题。字典缓存等按既有降级策略处理；不能为提升可用性把鉴权异常转换为认证成功。
- MQ/MQTT 消费考虑重复、乱序、重试及失败重投；以业务幂等约束保障一致性。新增消费者/定时任务须说明并发执行、重复执行和关闭资源策略。
- WebSocket/SSE 需验证连接用户与目标通道归属、断连清理、超时、并发访问及发送失败，不能仅凭客户端 tunnel/session ID 授权。
- URL 下载、文件上传/导出应按入口处理 SSRF、路径穿越、文件大小/类型限制、资源泄漏；文件名、对象键和回调 URL 都是不可信输入。
- 禁止在源码、测试、日志、文档、PR 中加入真实 token、密钥、私钥、密码、完整支付数据或未脱敏用户资料。测试只使用虚构数据；发现旧日志泄密风险时提出具体修复。
- 不为通过测试关闭认证/授权/验签，不随意扩大 `permitAll`、CORS 或数据权限。新增公开路径必须说明公开原因和入口校验。
- 数据库/第三方错误的内部详情保留在受控日志，对外返回稳定、安全的提示；修改公共异常处理要有回归验证。

## 9. 单元测试与验证策略

当前测试集中于 `demo-util`、`demo-manager`、`demo-service`，使用 JUnit Jupiter、Mockito、AssertJ；测试类放在相同业务包的 `src/test/java`，以 `*Test.java` 命名。`start` 另有全应用上下文测试。

- 从需求、契约和历史缺陷设计用例，不复制实现制造必然通过的断言。修复缺陷优先添加能暴露该缺陷的回归测试；纯文档/缩进改动无需新增业务测试。
- 覆盖有效输入、边界、非法输入、缺失数据、外部失败和关键副作用。按风险补充权限范围、事务、ID 回填、批量部分失败、长整型计数、缓存失效与幂等场景。
- Util 测试当前限于 `com.example.demo.framework.util` 包。未经相应任务要求，不为第三方来源验证码/微信工具包扩张测试范围。
- Manager 测试 mock 外部 HTTP、SDK、Redis、MQ，不访问真实平台、不发送通知/短信、不创建真实订单。
- Service 测试 mock Mapper/Manager，必要时用 spy 隔离 MyBatis-Plus 持久化。已有 `ServiceTestSupport` 可初始化 Mapper 和 TableInfo；不要 mock 掉待验证的业务方法本身。
- 用 `ArgumentCaptor` 验证业务范围、审计值、状态和请求参数；避免仅验证“某方法被调用一次”而不检查结果。
- 自定义校验和事务注解通过代理测试；`TransactionProbe` 验证 Spring 事务 advice 的提交/回滚路径，不代表真实数据库回滚能力。
- 真实 SQL、约束、默认值、逻辑删除、并发与隔离级别需要数据库集成测试。当前尚无完备设施；需要时建立隔离 MySQL 测试环境，不能用 H2 自动代替 MySQL 特性并宣称等价。
- 时间/随机数使用固定值、可控时钟或合理边界断言，避免 sleep；静态 mock、全局时间/时区、系统属性及 SecurityContext 用后恢复。临时文件使用 JUnit 临时目录并正确关闭资源。
- 为协议/密码学编写独立固定向量或已知样例；只做加密后再解密不能充分证明兼容外部协议。
- 不删除失败断言、不批量加 `@Disabled`、不把异常吞掉来“修绿”。未实现接口不能通过断言 null/空对象被包装为功能完成。
- 按改动逐步验证：目标测试 → 所属模块及上游 → 公共契约的下游编译/回归。变更 `demo-api`、基础对象、序列化或父 POM 时，应覆盖全模块编译；新增证据出现前不无意义重复全量测试。
- 报告本次实际命令和结果；明确跳过、未执行、环境阻塞及其影响。历史通过数量和覆盖率不能当作本次结果。

## 10. 已知边界与后续演进

规划功能、涉及外部集成/SQL/安全、补充测试或评估工程能力时，读取 [工程现状与演进建议](docs/engineering-roadmap.md)，按相关条目核查代码。该文档包含已定位的占位实现、SQL 绑定缺口、启动依赖、现有 CI/文档限制，以及尚未落地的建设建议。

优先牢记：

- 部分 Redis/登录/支付/通知接口仍未实现，Banner/Article 的自定义 count/list 未发现对应 SQL 映射。类存在、编译通过及 mock 测试通过不能证明功能可用。
- 自定义排序、角色归属校验和事务内 MQ 发送存在待完善点；相关修改需验证安全与一致性。
- 尚无完整的 schema/迁移体系、独立集成测试环境、专门单元测试 CI 或覆盖率门槛。不得将未来建议描述成项目现状。

这些缺口不要求在每个任务中一并修复；按当前需求实施，发现涉及本次变更的阻塞问题则明确报告。后续建议属于本项目工程判断，不是 OpenAI 对 Java 项目的强制技术选型。

## 11. 审查、Git 与完成标准

### Review guidelines

代码审查优先报告有明确触发条件和实际影响的问题，给出文件/行号、后果和修复方向。重点检查：

- 权限或用户/企业范围遗漏、无条件更新/删除、SQL 拼接、敏感日志。
- 事务/缓存/MQ 时序、支付与回调幂等、失败被当作成功。
- count 类型截断、分页退化成无界查询、字段或响应契约不兼容。
- Mapper 方法与 SQL 映射遗漏、软删除/审计字段丢失、插入 ID 回填缺失。
- 测试是否覆盖真实变化、mock 是否掩盖错误、是否新增外部服务依赖。

不要把个人风格偏好当作阻塞问题；对推测明确标注，不凭不存在的统一规范提出整改。

### Git 与 PR

- 沿用用户当前工作分支；需要新建时使用有意义的名称（Codex 默认 `codex/` 前缀）。不要擅自切走或重置用户工作。
- 未明确要求时不提交或推送；正常交付通过分支和 PR 合入 `master`，不要默认直接推主分支。实际远程保护配置应核实，不能只凭本文宣称已启用。
- 一次提交聚焦一个可解释的变更；排除 `target/`、日志、JVM dump、密钥和机器专属设置。
- PR 描述说明问题、修改后的行为、兼容/迁移影响、验证命令及结果；数据库或发布变更还应提供执行顺序和回退策略。

### Definition of done

1. 修改满足需求，并保持未要求改变的业务契约；所有受影响的接口、实现、调用者、Mapper、测试和配置已同步。
2. 针对风险完成必要测试及编译，确认目标测试确实运行；无法验证的部分明确说明原因及影响。
3. 审阅 diff，检查格式、无关改动、敏感数据、遗漏调用点和新增文件；`git diff --check` 无本次引入的问题。
4. 必要的配置说明、迁移脚本、使用文档及规则更新齐全；“脚本已生成”“已验证”“已执行上线”分别表述。
5. 最终用中文说明具体变化、验证结果及剩余限制；按需给文件链接。未实际完成的检查或功能不标记成功。

## 12. 本文件维护与官方参考

`AGENTS.md` 是面向代理的 Markdown 项目指令，不存在要求所有项目采用同一章节的固定模板。本文按官方推荐的仓库结构、运行/构建/测试、工程约定、约束和完成标准组织；Java、SQL、业务和未来能力规则来自本仓库及项目需要。

- [OpenAI：AGENTS.md 指令发现与作用范围](https://learn.chatgpt.com/docs/agent-configuration/agents-md)
- [OpenAI：Codex 最佳实践](https://learn.chatgpt.com/guides/best-practices)
- [OpenAI：自定义代码审查规则](https://developers.openai.com/blog/custom-code-review-rules-for-codex)

官方文档说明项目指令默认有合计 32 KiB 的读取上限，可由配置调整。因此维护时保持准确、去重；专项内容增长后移入按任务读取的文档，或在相应模块建立更具体的 `AGENTS.md`，同时检查适用指令合计大小。不要堆积过期任务日志、历史测试数字或未经验证的命令。

新增模块、调整基础设施/测试命令、改变公共契约、修复本文列出的缺口时，同步更新相关章节。不要修改本文来掩盖代码或测试不符合需求的问题。
