package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,String> {
}
