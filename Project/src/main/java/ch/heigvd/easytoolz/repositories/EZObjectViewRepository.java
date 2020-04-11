package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Tag;
import ch.heigvd.easytoolz.views.EZObjectView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface EZObjectViewRepository extends JpaRepository<EZObjectView,String> {


    List<EZObjectView> findByObjectOwner(String owner);

    EZObjectView findByObjectId(int id);

    List<EZObjectView> findByOwnerLatAndOwnerLng(BigDecimal lat, BigDecimal lng);

    //List<EZObjectView> findByObjectTagIn(List<Tag> tags);

    List<EZObjectView> findByObjectDescriptionContaining(String content);

    List<EZObjectView> findByObjectNameContaining(String objectName);
}
