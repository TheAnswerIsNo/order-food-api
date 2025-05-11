package com.wait.app.core.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 天
 *
 * description: minio配置
 */

@Data
@Configuration
public class MinioConfig {

    @Value(value = "${minio.client.endpoint}")
    private String endpoint;

    @Value(value = "${minio.client.accessKey}")
    private String accessKey;

    @Value(value = "${minio.client.secretKey}")
    private String secretKey;

    @Value(value = "${minio.client.bucketName}")
    private String bucketName;

    @Value(value = "${minio.client.pathPrefix}")
    private String pathPrefix;

    @Value(value = "${minio.client.virtualUrl}")
    private String virtualUrl;

    @Bean
    @SneakyThrows
    public MinioClient minioClient(){
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        return minioClient;
    }
}
