# demo

Maven 多模块 Java 后端，覆盖用户、企业、权限、字典、日志、聊天及第三方平台集成。可执行应用位于 `start`。部分接口仍为占位实现；项目可编译不表示所有业务可用。

## 开始开发

当前技术基线以 [父 POM](pom.xml) 为准：Java 25、Spring Boot 4.1.1、MyBatis-Plus 3.5.17；[Maven Wrapper](.mvn/wrapper/maven-wrapper.properties) 使用 Maven 3.9.16。

从仓库根目录执行：

```bash
java -version
./mvnw -version
./mvnw -DskipTests test-compile
./mvnw -pl demo-service -am test
```

最后一条执行 Service 及其上游模块测试，不包含 `start` 的全应用测试。若受限环境不能挂载 Mockito agent，按 [测试指南](docs/testing.md) 使用临时参数。启动需要外部配置及服务，参见 [开发环境](docs/development.md)。

## 文档入口

- [文档导航](docs/README.md)：按工作类型选择阅读材料。
- [AGENTS.md](AGENTS.md)：Codex 在本仓库的长期规则。
- [贡献指南](CONTRIBUTING.md)：修改、验证和 PR 交付。
- [架构说明](docs/architecture.md)：模块边界和关键数据流程。
- [Codex 工作流](docs/codex-workflow.md)：任务描述、上下文和持续迭代。
- [PLANS.md](PLANS.md)：复杂任务计划的编写和维护约定。
- [安全报告](SECURITY.md)：漏洞报告与信息保护。

文档中的现状记录维护于 2026-10-13；版本、命令和行为随代码变更更新。
