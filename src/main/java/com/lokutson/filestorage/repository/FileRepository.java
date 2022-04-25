package com.lokutson.filestorage.repository;

import com.lokutson.filestorage.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
    void deleteFileByName(String name);
}
