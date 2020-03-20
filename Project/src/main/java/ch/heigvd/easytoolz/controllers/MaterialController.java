package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.Material;
import ch.heigvd.easytoolz.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    MaterialRepository materialRepository;

    @GetMapping
    public List<Material> index()
    {
        return materialRepository.findAll();
    }
}
