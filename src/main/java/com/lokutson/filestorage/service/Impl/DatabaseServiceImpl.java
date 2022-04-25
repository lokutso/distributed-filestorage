package com.lokutson.filestorage.service.Impl;

import com.lokutson.filestorage.dto.FileDatabaseDto;
import com.lokutson.filestorage.entity.File;
import com.lokutson.filestorage.exception.DatabaseException;
import com.lokutson.filestorage.mapper.FileDatabaseMapper;
import com.lokutson.filestorage.repository.FileRepository;
import com.lokutson.filestorage.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    private final FileRepository fileRepository;

    @Override
    public List<FileDatabaseDto> getAll() {
        List<FileDatabaseDto> list = FileDatabaseMapper.listFileToListFileDatabaseDto(fileRepository.findAll());
        log.info("get all files");
        return list;
    }

    @Override
    public void save(MultipartFile multipartFile, String description) {
        try {
            File file = new File(multipartFile.getOriginalFilename(), description);
            fileRepository.save(file);
            log.info("save file : {}", file.getName());
        } catch (Exception e) {
            log.error("error save file : {}", multipartFile.getOriginalFilename());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(String name) {
        fileRepository.deleteFileByName(name);
        log.info("delete file : {}", name);
    }
}
