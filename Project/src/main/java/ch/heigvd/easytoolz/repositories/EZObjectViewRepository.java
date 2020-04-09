package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.views.EZObjectView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface EZObjectViewRepository extends JpaRepository<EZObjectView,String> {


    List<EZObjectView> findByObjectOwner(String owner);

    List<EZObjectView> findByObjectId(int id);

    List<EZObjectView> findByObjectDescriptionContaining(String contains);

    List<EZObjectView> findByOwnerLatAndOwnerLng(BigDecimal lat, BigDecimal lng);

}
