package ch.heigvd.easytoolz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propretie that can be added into the propreties files
 * file.upload-dir=./images
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    /**
     * @return the current upload directory
     */
    public String getUploadDir() {
        return uploadDir;
    }

    /**
     * sets the upload directory with the new upload directory passed in parameter
     * @param uploadDir the new upload directory
     */
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}