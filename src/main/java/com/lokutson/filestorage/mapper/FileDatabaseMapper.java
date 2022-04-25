package com.lokutson.filestorage.mapper;

import com.lokutson.filestorage.dto.FileDatabaseDto;
import com.lokutson.filestorage.entity.File;

import java.util.ArrayList;
import java.util.List;

public class FileDatabaseMapper {

    private static FileDatabaseDto toFileDatabaseDto(File file) {
        return new FileDatabaseDto(file.getId(), file.getName(), file.getDescription());
    }

    public static List<FileDatabaseDto> listFileToListFileDatabaseDto(List<File> files) {
        List<FileDatabaseDto> list = new ArrayList<>();

        files.forEach(file -> list.add(toFileDatabaseDto(file)));

        return list;
    }
}
