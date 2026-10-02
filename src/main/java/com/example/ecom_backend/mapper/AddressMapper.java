package com.example.ecom_backend.mapper;

import com.example.ecom_backend.dto.request.AddressRequest;
import com.example.ecom_backend.dto.response.AddressResponse;
import com.example.ecom_backend.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressResponse convertToResponse(Address address){
        AddressResponse response = new AddressResponse();

        response.setId(address.getId());
        response.setFullName(address.getFullName());
        response.setPhoneNumber(address.getPhoneNumber());
        response.setAddressLine1(address.getAddressLine1());
        response.setAddressLine2(address.getAddressLine2());
        response.setCity(address.getCity());
        response.setState(address.getState());
        response.setCountry(address.getCountry());
        response.setPostalCode(address.getPostalCode());
        response.setDefault(address.isDefault());

        return response;
    }

    public void mapRequestToAddress(Address address, AddressRequest request){
       address.setFullName(request.getFullName());
       address.setPhoneNumber(request.getPhoneNumber());
       address.setAddressLine1(request.getAddressLine1());
       address.setAddressLine2(request.getAddressLine2());
       address.setCity(request.getCity());
       address.setState(request.getState());
       address.setCountry(request.getCountry());
       address.setPostalCode(request.getPostalCode());
       address.setDefault(request.isDefault());
    }
}
