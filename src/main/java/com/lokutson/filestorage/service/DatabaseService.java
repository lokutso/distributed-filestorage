package com.lokutson.filestorage.service;

import com.lokutson.filestorage.dto.FileDatabaseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DatabaseService {

    List<FileDatabaseDto> getAll();

    void save(MultipartFile multipartFile, String description);

    void delete(String name);
}
