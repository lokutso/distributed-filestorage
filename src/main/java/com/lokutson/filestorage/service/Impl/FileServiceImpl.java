package com.lokutson.filestorage.service.Impl;

import com.lokutson.filestorage.dto.FileDatabaseDto;
import com.lokutson.filestorage.dto.FileStorageDto;
import com.lokutson.filestorage.service.DatabaseService;
import com.lokutson.filestorage.service.FileService;
import com.lokutson.filestorage.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final DatabaseService databaseService;
    private final FileStorageService fileStorageService;

    @Override
    public List<FileDatabaseDto> getAll() {
        return databaseService.getAll();
    }

    @Transactional
    @Override
    public void save(MultipartFile multipartFile, String description) {
        databaseService.save(multipartFile, description);
        fileStorageService.save(multipartFile);
    }

    @Override
    public FileStorageDto getFile(String name) {
        return fileStorageService.getFile(name);
    }

    @Transactional
    @Override
    public void delete(String name) {
        databaseService.delete(name);
        fileStorageService.deleteFile(name);
    }
}
