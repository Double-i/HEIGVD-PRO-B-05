package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalisationRepository extends JpaRepository<Localisation,String> {

    Localisation findByLatitudeAndLongitude(float latitude,float longitude);
}
