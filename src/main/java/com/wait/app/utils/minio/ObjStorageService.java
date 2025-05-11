package com.wait.app.utils.minio;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author 天
 *
 * @description: Minio服务接口
 */

public interface ObjStorageService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    UploadAttachment storeFile(MultipartFile file);

    /**
     * 批量上传
     * @param files
     * @return
     */
    List<UploadAttachment> storeFiles(List<MultipartFile> files);

    /**
     * 文件下载
     * @param objectName
     * @return
     */
    InputStream getFile(String objectName);

    /**
     * 预览图片(默认为一小时)
     * @param objectName
     * @param expiryTime
     * @return
     */
    String getFileUrl(String objectName,Integer expiryTime);

    /**
     * 删除
     * @param objectName
     * @return
     */
    void remove(String objectName);

    /**
     * 批量删除
     * @param objectNames
     * @return
     */
    void removeList(List<String> objectNames);
}
