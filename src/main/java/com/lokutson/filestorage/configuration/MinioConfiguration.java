package com.lokutson.filestorage.configuration;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class MinioConfiguration {

    @Value("${minio.url}")
    private String url;
    @Value("${minio.bucket}")
    private String bucket;
    @Value("${minio.access-key}")
    private String accessKey;
    @Value("${minio.secret-key}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        System.out.println("Start");
        MinioClient minioClient = null;
        try {
            minioClient =
                    MinioClient.builder()
                            .endpoint(url)
                            .credentials(accessKey, secretKey)
                            .build();

            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            } else {
                System.out.println("Bucket already exists.");
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }

        return minioClient;
    }
}
