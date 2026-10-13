# 编码与持久化检查指南

具体规则以 [AGENTS.md](../AGENTS.md) 第 5–8 节为准。本页提供修改时的阅读顺序和验收重点，避免维护第二份规则全文。

## Java 与公共接口

先读接口、实现、调用方和测试，保留空值、错误码、日期、字段、请求路径及返回结构。沿用文件风格，不使用本地 formatter 对全仓库格式化。序列化类保留稳定 UID，区分 JSpecify 空值契约和项目自定义运行期校验。

新增集合判断可复用 CollectionUtils；不可修改的 `List.of()` 不适合后续追加。API 迁移检查异常与大小写语义，不机械换 import。`javax.crypto` 等 JDK 包不属于 Jakarta 全局替换对象。

## CRUD 检查顺序

1. 可信 ID、用户/企业范围和审计人来自哪里？
2. 插入的必要字段及数据库默认值是否确认？BO 是否回填生成 ID？
3. 更新/删除 WHERE 是否保持归属条件和软删除语义？
4. 零影响行在该业务中应报错还是视为幂等成功？
5. 是否涉及权限刷新、缓存失效和事务？
6. 批量化是否保留 ID 对应关系与失败回滚？

常规参考 Banner；权限关联参考 UserRole/RoleMenu。不要把特有业务键、join 或状态流转强行改为通用 CRUD。普通数据库异常沿用统一处理，只有有明确业务意义的冲突才专门转换。

## SQL 与分页

参考 [RoleMapper](../demo-dao/src/main/resources/mappers/RoleMapper.xml)、[DictDataMapper](../demo-dao/src/main/resources/mappers/DictDataMapper.xml)、[ConfigMapper](../demo-dao/src/main/resources/mappers/ConfigMapper.xml)。核对 namespace、方法 id、参数、别名、字段和打包路径；mock Mapper 不能发现缺失绑定。

计数链路使用 long/Long，影响行数仍是 int。Role/DictData 支持可选分页，其他查询不得无差别改成全量。公开分页输入还须限制非法值、上限与偏移溢出。

共享 count/list 过滤条件可提取局部 queryCondition；保持各自 SELECT，不把排序或分页放入 count。普通 SQL 不包多余 CDATA，XML 特殊字符局部转义或使用 CDATA；动态节点不能放入 CDATA。普通 if 的 SQL 多一级 Tab，已有单行 CDATA 保持布局。

值使用 `#{}`，排序字段和方向使用固定白名单或 choose。日志 Mapper 现有 `${sort} ${dir}` 需要特别审查，字符串非空不等于安全。

## 数据库变更

当前尚无完整 schema/迁移体系。需变更时新增明确版本的迁移 SQL，说明回填、执行前提、兼容窗口、锁表影响、验证与回退。先在隔离 MySQL 验证；生成迁移不代表执行迁移。字段统一使用 createBy/updateBy 与 create_by/update_by。
