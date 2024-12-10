package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.Address;
import com.example.campusshuttle.entity.Location;

public interface AddressService {
    Address getAddressById(Long addressId);
    Iterable<Address> getAllAddresses();
    Address saveAddress(Address address);
    Address updateAddress(Long addressId, Address address);
    void deleteAddress(Long addressId);
}