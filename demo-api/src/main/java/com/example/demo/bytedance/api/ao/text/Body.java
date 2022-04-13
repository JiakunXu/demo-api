package com.example.demo.bytedance.api.ao.text;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Body implements Serializable {

    private static final long serialVersionUID = -4773047490509322993L;

    @JSONField(name = "targets")
    private List<String>      targetList;

    @JSONField(name = "tasks")
    private List<Task>        taskList;

}
