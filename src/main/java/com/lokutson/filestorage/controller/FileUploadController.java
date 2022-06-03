package com.lokutson.filestorage.controller;

import com.lokutson.filestorage.dto.FileDatabaseDto;
import com.lokutson.filestorage.dto.FileStorageDto;
import com.lokutson.filestorage.exception.DatabaseException;
import com.lokutson.filestorage.exception.StorageException;
import com.lokutson.filestorage.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("filestorage")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;

    @Operation(summary = "Get a list of files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Got a list of files",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FileDatabaseDto.class)))})})
    @GetMapping("/")
    public List<FileDatabaseDto> listFile() {

        return fileService.getAll();
    }

    @Operation(summary = "Upload file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "String", format = "byte"))})})
    @GetMapping("/{name}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String name) throws IOException {

        FileStorageDto fileStorageDto = fileService.getFile(name);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileStorageDto.getName() + "\"")
                .body(fileStorageDto.getStream().readAllBytes());
    }

    @Operation(summary = "Save file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File saved")})
    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestPart("file") MultipartFile multipartFile, @RequestParam("description") String description) {

        fileService.save(multipartFile, description);
    }

    @Operation(summary = "Delete a file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File deleted")})
    @DeleteMapping("/{name}")
    public void deleteFile(@PathVariable("name") String name) {

        fileService.delete(name);
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageException(StorageException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<?> handleDatabaseException(DatabaseException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}