package com.example.demo.bytedance.api.bo.text;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class Body implements Serializable {

    private static final long serialVersionUID = -4773047490509322993L;

    @JSONField(name = "targets")
    private List<String>      targetList;

    /**
     * 检测任务列表.
     */
    @JSONField(name = "tasks")
    private List<Task>        taskList;

}
