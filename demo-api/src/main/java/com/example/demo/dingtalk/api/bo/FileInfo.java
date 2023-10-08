package com.example.demo.dingtalk.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 493509740060474470L;

    /**
     * 文件Id
     */
    private String            id;

    /**
     * 所在空间Id
     */
    private String            spaceId;

    /**
     * 父目录Id。根目录时，该参数是0
     */
    private String            parentId;

    /**
     * 类型，本接口返回类型只有FILE
     */
    private String            type;

    /**
     * 文件名称
     */
    private String            name;

    /**
     * 文件大小，单位Byte
     */
    private Long              size;

    /**
     * 文件在空间内的路径
     */
    private String            path;

    /**
     * 版本
     */
    private Long              version;

    /**
     * 状态
     */
    private String            status;

    /**
     * 文件后缀
     */
    private String            extension;

    /**
     * 创建者unionId
     */
    private String            creatorId;

    /**
     * 修改者unionId
     */
    private String            modifierId;

    /**
     * 创建时间，iso8601格式，例如：2022-07-29T14:55Z
     */
    private String            createTime;

    /**
     * 修改时间，iso8601格式，例如：2022-07-29T14:55Z
     */
    private String            modifiedTime;

    /**
     * 唯一标识
     */
    private String            uuid;

    /**
     * 存储分区
     */
    private String            partitionType;

    /**
     * 驱动类型
     */
    private String            storageDriver;

}
