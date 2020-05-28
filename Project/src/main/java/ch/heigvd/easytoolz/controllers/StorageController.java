package ch.heigvd.easytoolz.controllers;


import ch.heigvd.easytoolz.services.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/image")
public class StorageController {

    @Autowired
    StorageService storageService;

    /**
     * @param filename the filename of the image
     * @return the image stored on the server
     * @throws Exception
     */
    @GetMapping(value = "/{filename}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public Resource getSingleItem(@PathVariable  String filename) throws Exception {
        return storageService.loadAsRessource(filename);
    }

    @DeleteMapping
    public void deleteImage(@PathVariable String filename) throws Exception
    {

    }
}
