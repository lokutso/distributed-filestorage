package com.lokutson.filestorage.service.Impl;

import com.lokutson.filestorage.dto.FileStorageDto;
import com.lokutson.filestorage.exception.StorageException;
import com.lokutson.filestorage.service.FileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final MinioClient minioClient;
    @Value("${minio.bucket}")
    private String bucket;

    @Override
    public void save(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(multipartFile.getOriginalFilename())
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(multipartFile.getContentType())
                            .build());
            log.info("save file : {}", multipartFile.getOriginalFilename());
        } catch (Exception e) {
            log.error("error save file : {}", multipartFile.getOriginalFilename());
            throw new StorageException(e.getMessage());
        }

    }

    @Override
    public void deleteFile(String name) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(name)
                            .build()
            );
            log.info("delete file : {}", name);
        } catch (Exception e) {
            log.error("error delete file : {}", name);
            throw new StorageException(e.getMessage());
        }
    }

    @Override
    public FileStorageDto getFile(String name) {
        try {
            StatObjectResponse metadata = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucket)
                            .object(name)
                            .build()
            );

            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(name)
                    .build());

            log.info("get file : {}", metadata.object());

            return new FileStorageDto(metadata.object(), inputStream);
        } catch (Exception e) {
            log.error("error get file");
            throw new StorageException(e.getMessage());
        }
    }
}
