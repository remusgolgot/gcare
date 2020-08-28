package com.gcare.services;

import com.gcare.exceptions.FileStorageException;
import com.gcare.exceptions.MyFileNotFoundException;
import com.gcare.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String folder) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(folder + "\\" + fileName);
            createFolderIfNotExists(folder);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new FileStorageException("Could not store file " + fileName + ". Please try again! Error Reason : ", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("Document not found " + Paths.get(fileName).getFileName().toString());
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("Document not found " + Paths.get(fileName).getFileName().toString(), ex);
        }
    }

    public void createFolderIfNotExists(String folderPath) {
        System.out.println(folderPath);
        Path folderCC = this.fileStorageLocation.resolve(folderPath);
        System.out.println(folderCC.toString());
        if (!folderCC.toFile().exists()) {
            folderCC.toFile().mkdirs();
        }
    }
}

