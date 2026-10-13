# 项目文档导航

维护于 2026-10-13。本目录服务于本项目和 Codex 协作；除特殊入口外，普通 Markdown 不会因存在于仓库而自动成为模型指令。按任务读取相关材料，避免每次加载所有文档。

| 工作类型 | 阅读入口 | 作用 |
| --- | --- | --- |
| 首次进入项目 | [根 README](../README.md)、[开发环境](development.md) | 技术基线、构建与启动前提 |
| Codex 协作 | [AGENTS.md](../AGENTS.md)、[Codex 工作流](codex-workflow.md) | 长期规则与任务上下文 |
| 修改模块边界、数据流程 | [架构说明](architecture.md) | 依赖方向和关键行为 |
| Java、CRUD、SQL 修改 | [编码规范](coding-standards.md) | 参考实现、风险与检查项 |
| 添加或运行测试 | [测试指南](testing.md) | 测试范围、运行方法、结果解释 |
| 代码审查、PR | [审查指南](code-review.md)、[贡献指南](../CONTRIBUTING.md) | 有实际影响的问题与交付证据 |
| 安全、支付、回调 | [安全开发](security.md)、[安全报告政策](../SECURITY.md) | 信任边界与报告渠道 |
| 启动、打包、故障排查 | [运行手册](runbook.md) | 环境核对、定位和回退 |
| 新功能需求 | [需求说明](requirements/README.md)、[需求模板](requirements/template.md) | 可验证的业务契约 |
| 复杂任务 | [PLANS.md](../PLANS.md)、[计划模板](plans/template.md) | 阶段、决策、验证及续接 |
| 长期技术决策 | [ADR 索引](decisions/README.md) | 理由、替代方案、后果 |
| 重复操作 | [技能设计说明](skills/README.md) | 何时值得整理成 Codex 技能 |
| 工程建设 | [工程现状与建议](engineering-roadmap.md) | 已知缺口与待建设能力 |

## 更新约定

`AGENTS.md` 保留当前项目规则；专项文档提供背景、操作和示例，避免重复维护规则全文。需求、计划、ADR 分别记录行为、实施路径、长期取舍。发生冲突先核对用户指令和实际源码，不凭旧文档断言现状。

修改相关代码或配置的作者同步文档；审查者核对命令、路径、链接和事实。示例仅用虚构数据，不保存个人路径、真实配置值、历史聊天记录或完整运行日志。未来建设应标明未落地，不作为已存在的质量门禁。
