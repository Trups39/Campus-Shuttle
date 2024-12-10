package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.Address;
import com.example.campusshuttle.entity.Location;
import com.example.campusshuttle.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId).orElse(null);
    }

    @Override
    public Iterable<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long addressId, Address updatedAddress) {
        if (addressRepository.existsById(addressId)) {
            updatedAddress.setAddressId(addressId);
            return addressRepository.save(updatedAddress);
        }
        return null;
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}
