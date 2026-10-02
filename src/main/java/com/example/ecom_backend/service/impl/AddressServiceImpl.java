package com.example.ecom_backend.service.impl;

import com.example.ecom_backend.dto.request.AddressRequest;
import com.example.ecom_backend.dto.response.AddressResponse;
import com.example.ecom_backend.entity.Address;
import com.example.ecom_backend.entity.User;
import com.example.ecom_backend.mapper.AddressMapper;
import com.example.ecom_backend.repository.AddressRepository;
import com.example.ecom_backend.repository.UserRepository;
import com.example.ecom_backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    private User getAuthenticateUser(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public AddressResponse addAddress(AddressRequest request) {
        User user = getAuthenticateUser();

        Address address = new Address();

        addressMapper.mapRequestToAddress(address,request);
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);

        return addressMapper.convertToResponse(savedAddress);
    }

    @Override
    public AddressResponse updateAddress(Long id, AddressRequest request) {
        User user = getAuthenticateUser();

        Address address = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new RuntimeException("Address not found"));

        addressMapper.mapRequestToAddress(address,request);

        Address updatedAddress = addressRepository.save(address);

        return addressMapper.convertToResponse(updatedAddress);
    }

    @Override
    public void deleteAddress(Long id) {

        User user = getAuthenticateUser();

        Address address = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new RuntimeException("Address not found"));

        addressRepository.delete(address);
    }

    @Override
    public List<AddressResponse> getMyAddresses() {
        User user = getAuthenticateUser();

        return addressRepository.findByUserId(user.getId())
                .stream()
                .map(addressMapper::convertToResponse)
                .toList();
    }

    @Override
    public AddressResponse getAddress(Long id) {
        User user = getAuthenticateUser();

        Address address = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new RuntimeException("Address not found"));

        return addressMapper.convertToResponse(address);
    }

    @Override
    public AddressResponse setDefaultAddress(Long id) {
        User user = getAuthenticateUser();

        Address selectedAddress = addressRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(()-> new RuntimeException("Address not found"));

        List<Address> addresses = addressRepository.findByUserId(user.getId());

        for(Address address: addresses){
            address.setDefault(false);
        }

        selectedAddress.setDefault(true);

        addressRepository.saveAll(addresses);

        return addressMapper.convertToResponse(selectedAddress);
    }
}
