package com.lokutson.filestorage.dto;

import lombok.Data;

@Data
public class FileDatabaseDto {
    private final long id;

    private final String name;

    private final String description;
}
