package com.shadhin.importleads.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@Service
public class FileStorageService {

    private final Path rootLocation;

    public FileStorageService(StorageProperty storageProperty) {
        this.rootLocation = Paths.get(storageProperty.getLocation());
        //create directory if not exists
        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectories(rootLocation);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize storage");
            }
        }
    }

    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalStateException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(UUID.randomUUID().toString() +"-"+file.getOriginalFilename())).normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new IllegalStateException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                return destinationFile.toString();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to store file.", e);
        }
    }


    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new IllegalStateException( "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new IllegalStateException("Could not read file: " + filename, e);
        }
    }

    public void deleteFile(String filename) {
        try {
            Path file = load(filename);
            if(Files.exists(file)) {
                Files.delete(file);
            }
        }
        catch (IOException e) {
            throw new IllegalStateException("Could not delete file: " + filename, e);
        }
    }
}
