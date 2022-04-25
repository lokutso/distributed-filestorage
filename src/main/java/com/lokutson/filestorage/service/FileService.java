package com.lokutson.filestorage.service;

import com.lokutson.filestorage.dto.FileDatabaseDto;
import com.lokutson.filestorage.dto.FileStorageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<FileDatabaseDto> getAll();

    void save(MultipartFile multipartFile, String description);

    FileStorageDto getFile(String name);

    void delete(String name);
}
