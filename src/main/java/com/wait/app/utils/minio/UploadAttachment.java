package com.wait.app.utils.minio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 天
 *
 * @description: 上传附件类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadAttachment {

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件存储名称
     */
    private String objName;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 获取文件资源路径
     */
    private String url;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小
     */
    private Long size;
}
