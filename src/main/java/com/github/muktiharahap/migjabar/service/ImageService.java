package com.github.muktiharahap.migjabar.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * @author mukti on 9/21/2017.
 */
@Service
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);
    @Value("${app.folder.upload}")
    public String uploadDir;

    public void moveFile(String fotoString64, String name) {
        try {
            String folder = uploadDir + File.separator;
            File folderImages = new File(folder);
            if (!folderImages.exists()) {
                log.info("Creating folder [{}]", folderImages.getAbsolutePath());
                Files.createDirectories(folderImages.toPath());
            }
            File originalFile = new File(folder + name);
            byte[] decodedBytes = Base64.getDecoder().decode(fotoString64);
            Files.write(originalFile.toPath(), decodedBytes);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }

    private Path load(String folder, String filename){
        return new File(uploadDir).toPath().resolve(folder+"/"+filename);
    }

    public Resource loadResource(String filename, String tpe) throws MalformedURLException {
        Path filePath = load(tpe, filename);
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new MalformedURLException("Could not read file: " + filename);
        }
    }
}
