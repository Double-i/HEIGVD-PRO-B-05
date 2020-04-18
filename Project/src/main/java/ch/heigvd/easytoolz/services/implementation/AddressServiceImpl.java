package ch.heigvd.easytoolz.services.implementation;

import ch.heigvd.easytoolz.exceptions.address.AddressFailedStoreException;
import ch.heigvd.easytoolz.exceptions.address.AddressNotFoundException;
import ch.heigvd.easytoolz.models.Address;
import ch.heigvd.easytoolz.repositories.AddressRepository;
import ch.heigvd.easytoolz.services.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void storeAddress(Address address) {
        addressRepository.save(address);
        if (addressRepository.findById(address.getId()).isEmpty()) {
            throw new AddressFailedStoreException();
        }
    }

    @Override
    public void updateAddress(Address address, int id) {
        if (address == null)
            throw new IllegalArgumentException("parameter address : address is null");

        Optional<Address> optionalAddress = addressRepository.findById(id);

        if (optionalAddress.isPresent())
            throw new AddressNotFoundException(id);

        Address oldAddress = optionalAddress.get();

        if (address.getCity() != null) oldAddress.setCity(address.getCity());
        if (!address.getDistrict().isEmpty()) oldAddress.setDistrict(address.getDistrict());
        if (address.getLat() != null) oldAddress.setLat(address.getLat());
        if (address.getLng() != null) oldAddress.setLng(address.getLng());
        if (!address.getAddress().isEmpty()) oldAddress.setAddress(address.getAddress());
        if (!address.getPostalCode().isEmpty()) oldAddress.setPostalCode(address.getPostalCode());

        addressRepository.save(oldAddress);
    }
}