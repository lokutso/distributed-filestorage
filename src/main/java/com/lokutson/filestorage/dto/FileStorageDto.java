package com.lokutson.filestorage.dto;

import lombok.Data;

import java.io.InputStream;

@Data
public class FileStorageDto {
    private final String name;
    private final InputStream stream;
}
