package com.example.ecom_backend.service;

import com.example.ecom_backend.dto.request.AddressRequest;
import com.example.ecom_backend.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse addAddress(AddressRequest request);

    AddressResponse updateAddress(Long id, AddressRequest request);

    void deleteAddress(Long id);

    List<AddressResponse> getMyAddresses();

    AddressResponse getAddress(Long id);

    AddressResponse setDefaultAddress(Long id);
}
