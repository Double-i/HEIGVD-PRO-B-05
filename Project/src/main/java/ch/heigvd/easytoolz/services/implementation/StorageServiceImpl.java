package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.FileStorageProperties;
import ch.heigvd.easytoolz.models.EZObject;
import ch.heigvd.easytoolz.models.EZObjectImage;
import ch.heigvd.easytoolz.services.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

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
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String fileName) {
        return null;
    }

    @Override
    public Resource loadAsRessource(String filename) {
        return null;
    }

    @Override
    public void store(MultipartFile file, EZObject obj, EZObjectImage img) throws Exception{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(filename.contains(".."))
                throw new Exception("Illegal file");

            Path target = this.storageLocation.resolve(obj.getID() + obj.getOwnerUserName() +filename );
            img.setPathToImage(target.toString());

            Files.copy(file.getInputStream(),target,StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException io)
        {
            throw new Exception("Could not store file with filename " + filename);
        }
    }

    @Override
    public void loadMultiple(List<MultipartFile> files) {

    }
}
