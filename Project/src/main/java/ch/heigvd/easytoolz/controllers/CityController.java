package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.models.City;
import ch.heigvd.easytoolz.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static ch.heigvd.easytoolz.utils.Utils.transformLike;

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping
    public List<City> index(
            @RequestParam("name") String name
    ){
        name = transformLike(name);

        if(name != null){
            return cityRepository.findByCity(name);
        }else{
            return cityRepository.findAll();
        }
    }
}
