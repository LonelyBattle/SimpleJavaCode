package com.zs.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    private final Path fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public FileStorageService() throws IOException {
        Files.createDirectories(fileStorageLocation);
    }

    public String storeFile(MultipartFile file) throws IOException {
        Path targetLocation = fileStorageLocation.resolve(file.getOriginalFilename());
        System.out.println("targetLocation:" + targetLocation);
        Files.copy(file.getInputStream(), targetLocation);
        return file.getOriginalFilename();
    }

    public Path loadFile(String filename) {
        return fileStorageLocation.resolve(filename);
    }
}
