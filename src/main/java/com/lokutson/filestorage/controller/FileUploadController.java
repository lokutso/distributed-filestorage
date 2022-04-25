package com.lokutson.filestorage.controller;

import com.lokutson.filestorage.dto.FileStorageDto;
import com.lokutson.filestorage.exception.DatabaseException;
import com.lokutson.filestorage.exception.StorageException;
import com.lokutson.filestorage.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;

    @GetMapping("/")
    public String listFile(Model model) {

        model.addAttribute("listFileDatabaseDto", fileService.getAll());

        return "index";
    }

    @GetMapping("/download/{name:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String name) throws IOException {

        FileStorageDto fileStorageDto = fileService.getFile(name);
        ByteArrayResource resource = new ByteArrayResource(fileStorageDto.getStream().readAllBytes());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileStorageDto.getName() + "\"")
                .body(resource);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("description") String description) {

        fileService.save(multipartFile, description);

        return "redirect:/";
    }

    @GetMapping("/delete/{name:.+}")
    public String deleteFile(@PathVariable("name") String name) {

        fileService.delete(name);

        return "redirect:/";
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