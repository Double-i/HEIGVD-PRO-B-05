package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.config.FileStorageProperties;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    /**
     * inits the service with the property prop
     * @param prop the property
     * @throws Exception
     */
    void init(FileStorageProperties prop) throws Exception;

    /**
     * loads the path of the filename
     * @param fileName name of the filename
     * @return the Path of the filename
     */
    Path load(String fileName);

    /**
     * loads the ressource of the filename
     * @param filename name of the filename
     * @return the Resource of the filename
     * @throws Exception
     */
    Resource loadAsRessource(String filename) throws Exception;

    /**
     * stores the resources the file into the host
     * @param file the file
     * @param ezObject the object EZObject stored which will be linked to file
     * @param img the object EZObjectImage which will be linked to file
     * @throws Exception
     */
    void store(MultipartFile file, EZObject ezObject, EZObjectImage img) throws Exception;

    /**
     * delete the resource which stored to "filename"
     * @param filename the name of the file
     */
    void delete(String filename);
}
