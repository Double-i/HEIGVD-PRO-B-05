package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.models.Tag;
import ch.heigvd.easytoolz.repositories.TagRepository;
import ch.heigvd.easytoolz.services.interfaces.TagService;
import ch.heigvd.easytoolz.util.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> getByName(String name) {
        name = ServiceUtils.transformLike(name);
        return tagRepository.findByNameLike(name);
    }
}
