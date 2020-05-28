package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.config.FileStorageProperties;
import ch.heigvd.easytoolz.exceptions.filesystem.FileUploadException;
import ch.heigvd.easytoolz.exceptions.filesystem.StorageException;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectImage;
import ch.heigvd.easytoolz.services.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {


    private  Path storageLocation;

    @Autowired
    public void init(FileStorageProperties prop) throws Exception {
        this.storageLocation = Paths.get(prop.getUploadDir()).toAbsolutePath().normalize();

        try{
            Files.createDirectories(this.storageLocation);
        } catch (IOException e) {
            throw new Exception("Could not create directory at specified location");
        }
    }

    @Override
    public Path load(String fileName) {
         		return storageLocation.resolve(fileName);
    }

    @Override
    public Resource loadAsRessource(String filename) throws StorageException {

        try {
            Path file = load( filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void store(MultipartFile file, EZObject obj, EZObjectImage img) throws FileUploadException{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(filename.contains(".."))
                throw new FileUploadException("Illegal file");

            Path target = this.storageLocation.resolve(obj.getID() + obj.getOwnerUserName() +filename );
            img.setPathToImage(target.getFileName().toString());

            Files.copy(file.getInputStream(),target,StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException io)
        {
            throw new FileUploadException("Could not store file with filename " + filename);
        }
    }


}
