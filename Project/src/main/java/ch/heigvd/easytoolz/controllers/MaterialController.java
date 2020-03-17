package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.Material.Material;
import ch.heigvd.easytoolz.repositories.MaterialRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;


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
