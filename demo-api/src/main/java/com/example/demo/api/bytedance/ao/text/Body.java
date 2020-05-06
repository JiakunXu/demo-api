package com.example.demo.api.bytedance.ao.text;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
public class Body implements Serializable {

    private static final long serialVersionUID = -4773047490509322993L;

    @JSONField(name = "tasks")
    private List<Task>        taskList;

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

}
