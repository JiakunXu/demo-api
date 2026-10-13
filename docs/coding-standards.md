# 编码规则导航与检查顺序

维护于 2026-10-13。具体规则由以下文档各自维护，根 [AGENTS.md](../AGENTS.md) 保留长期约束与阅读路由，避免重复维护全部规则。

| 工作 | 专项规范 |
| --- | --- |
| 类型、对象、转换、Lombok、空值、注解、异常和资源 | [java-conventions.md](java-conventions.md) |
| 缩进、换行、import、注释、修改范围 | [code-style.md](code-style.md) |
| 请求、响应、序列化、权限、分页和兼容 | [api-conventions.md](api-conventions.md) |
| DO/Mapper、CRUD、审计、SQL、批量和事务 | [persistence.md](persistence.md) |
| 平台、缓存、消息、文件和安全边界 | [security.md](security.md) |
| 配置键、用途和消费者 | [configuration.md](configuration.md) |
| POM、BOM、SDK、scope、插件、升级及弃用处理 | [dependencies.md](dependencies.md) |
| 验证范围与实际运行 | [testing.md](testing.md) |

## 修改前后检查

先读取接口、实现、调用方与已有测试，明确需要保留的行为。然后核对可信输入、持久化范围、默认值来源、生成 ID、零影响行、异常、副作用和兼容。最后按实际风险验证并检查差异，不为局部修复重排整个模块。

不能用“遵循规范”代替实际验证：mock 无法证明 SQL，事务探针无法证明数据库回滚，序列化与 Web 状态需要对应契约证据。未实施的工具门禁或集成设施明确说明。
