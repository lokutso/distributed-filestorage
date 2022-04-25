package com.lokutson.filestorage.service;

import com.lokutson.filestorage.dto.FileStorageDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void save(MultipartFile multipartFile);

    void deleteFile(String name);

    FileStorageDto getFile(String name);

}