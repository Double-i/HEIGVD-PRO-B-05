package ch.heigvd.easytoolz.controllers;

import ch.heigvd.easytoolz.services.interfaces.TagService;
import ch.heigvd.easytoolz.models.Tag;
import ch.heigvd.easytoolz.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> index(
            @RequestParam(value = "name", required = false) String name){
        name = ServiceUtils.transformLike(name);
        if(name != null)
            return tagService.getByName(name);
        else
            return tagService.getAll();
    }
}
