package ch.heigvd.easytoolz.controllers;


import ch.heigvd.easytoolz.services.interfaces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.io.FileInputStream;

@RestController
@RequestMapping("/image")
public class StorageController {

    @Autowired
    StorageService storageService;

    @GetMapping(value = "/{filename}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public Resource getSingleItem(@PathVariable  String filename) throws Exception {

        Resource resource = storageService.loadAsRessource(filename);
        return resource;
    }
}
