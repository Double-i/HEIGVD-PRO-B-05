package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.config.FileStorageProperties;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    /**
     * Set the root of image directory
     * @param prop proprety to be loaded from application propreties files
     * @throws Exception
     */
    void init(FileStorageProperties prop) throws Exception;

    /**
     * Load images
     * @param fileName
     * @return
     */
    Path load(String fileName);
    Resource loadAsRessource(String filename) throws Exception;

    /**
     * Store image into server
     * @param file
     * @param ezObject
     * @param img
     * @throws Exception
     */
    void store(MultipartFile file, EZObject ezObject, EZObjectImage img) throws Exception;

}
