package ch.heigvd.easytoolz.repositories;

import ch.heigvd.easytoolz.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
