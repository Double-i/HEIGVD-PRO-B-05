package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.config.FileStorageProperties;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    void init(FileStorageProperties prop) throws Exception;


    Path load(String fileName);
    Resource loadAsRessource(String filename) throws Exception;

    void store(MultipartFile file, EZObject ezObject, EZObjectImage img) throws Exception;

    void delete(String filename);
}
