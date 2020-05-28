package ch.heigvd.easytoolz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propretie that can be added into the propreties files
 * file.upload-dir=./images
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}