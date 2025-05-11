package com.wait.app.utils.minio;

import cn.hutool.core.util.StrUtil;
import com.wait.app.core.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


/**
 * @author 天
 *
 * @description: minio接口实现
 */
@Slf4j
@Service("minioService")
public class MinioServiceImpl implements ObjStorageService {

    private final MinioClient minioClient;

    private final MinioConfig minioConfig;

    @Autowired
    public MinioServiceImpl(MinioClient minioClient, MinioConfig minioConfig) {
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

    /**
     * description: 判断bucket是否存在，不存在则创建
     *
     * @return: void
     */
    @SneakyThrows
    public void existBucket() {
        String bucketName = minioConfig.getBucketName();
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 文件上传
     *
     * @param file
     */
    @Override
    @SneakyThrows
    public UploadAttachment storeFile(MultipartFile file) {
        UploadAttachment uploadAttachment = new UploadAttachment();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String objectName = minioConfig.getPathPrefix() + StrUtil.SLASH + uuid + "-" + file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .object(objectName)
                        .stream(inputStream, file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        uploadAttachment.setName(file.getOriginalFilename());
        uploadAttachment.setObjName(objectName);
        String fileUrl = this.getFileUrl(objectName, null);
        uploadAttachment.setUrl(fileUrl);
        uploadAttachment.setPath(fileUrl.substring(0, fileUrl.indexOf("?")));
        uploadAttachment.setType(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1]);
        uploadAttachment.setSize(file.getSize());
        inputStream.close();
        return uploadAttachment;
    }

    /**
     * 批量文件上传
     *
     * @param files
     */
    @Override
    public List<UploadAttachment> storeFiles(List<MultipartFile> files) {
        List<UploadAttachment> uploadAttachmentList = new ArrayList<>();
        if (!Objects.isNull(files) && !files.isEmpty()) {
            files.forEach(file -> {
                UploadAttachment uploadAttachment = this.storeFile(file);
                uploadAttachmentList.add(uploadAttachment);
            });
        }
        return uploadAttachmentList;
    }

    /**
     * 文件下载
     *
     * @param objectName
     * @return
     */
    @Override
    @SneakyThrows
    public InputStream getFile(String objectName) {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioConfig.getBucketName())
                        .object(objectName)
                        .build());
    }

    /**
     * 预览图片(默认时间为一小时)
     *
     * @param objectName
     * @return url
     */
    @Override
    @SneakyThrows
    public String getFileUrl(String objectName, Integer expiryTime) {
        if (Objects.isNull(expiryTime)) {
            expiryTime = 60 * 60;
        }
        // 查看文件地址
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs
                        .builder()
                        .bucket(minioConfig.getBucketName())
                        .object(objectName)
                        .method(Method.GET)
                        .expiry(expiryTime)
                        .build());
        //用虚拟地址替换实际地址
        url = url.replace(minioConfig.getEndpoint(), minioConfig.getVirtualUrl());
        return url;
    }

    /**
     * 删除
     *
     * @param objectName
     * @return
     */
    @Override
    @SneakyThrows
    public void remove(String objectName) {
        minioClient.removeObject(
                RemoveObjectArgs
                        .builder()
                        .bucket(minioConfig.getBucketName())
                        .object(objectName)
                        .build());
    }

    /**
     * 批量删除
     *
     * @param objectNames
     * @return
     */
    @Override
    @SneakyThrows
    public void removeList(List<String> objectNames) {
        List<DeleteObject> deleteObjectList = new ArrayList<DeleteObject>();
        objectNames.forEach(objectName -> {
            deleteObjectList.add(new DeleteObject(objectName));
        });

        RemoveObjectsArgs removeObjectsArgs =
                RemoveObjectsArgs
                        .builder()
                        .bucket(minioConfig.getBucketName())
                        .objects(deleteObjectList)
                        .build();
        for (Result<DeleteError> errorResult : minioClient.removeObjects(removeObjectsArgs)) {
            DeleteError error = errorResult.get();
            log.error("删除失败 '" + error.objectName() + "'. 错误原因:" + error.message());
        }
    }
}
