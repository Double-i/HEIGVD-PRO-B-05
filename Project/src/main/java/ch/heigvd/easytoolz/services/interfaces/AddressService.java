package ch.heigvd.easytoolz.services.interfaces;

import ch.heigvd.easytoolz.exceptions.address.AddressFailedStoreException;
import ch.heigvd.easytoolz.exceptions.address.AddressNotFoundException;
import ch.heigvd.easytoolz.models.Address;

public interface AddressService {
    /**
     * Stores the new address in the database
     *
     * @param address the new address
     * @throws AddressFailedStoreException if the address isn't stored in the database
     * @return the new address stored
     */
    Address storeAddress(Address address) throws AddressFailedStoreException;

    /**
     * Update the address
     *
     * @param address the updated address
     * @param id      the id of the address which will be updated
     * @throws AddressNotFoundException if the address id is found
     */
    void updateAddress(Address address, int id) throws AddressNotFoundException;
}
