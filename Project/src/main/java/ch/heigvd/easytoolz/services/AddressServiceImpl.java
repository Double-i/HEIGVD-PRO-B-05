package ch.heigvd.easytoolz.services;

import ch.heigvd.easytoolz.models.Address;
import ch.heigvd.easytoolz.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public boolean storeAddress(Address address) {
        addressRepository.save(address);
        if(addressRepository.findById(address.getId()).isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
