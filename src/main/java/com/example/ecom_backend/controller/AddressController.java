package com.example.ecom_backend.controller;

import com.example.ecom_backend.dto.request.AddressRequest;
import com.example.ecom_backend.dto.response.AddressResponse;
import com.example.ecom_backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public AddressResponse addAddress(@RequestBody AddressRequest request){
        return addressService.addAddress(request);
    }

    @PutMapping("/{id}")
    public AddressResponse updateAddress(@PathVariable Long id, @RequestBody AddressRequest request){
        return addressService.updateAddress(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
    }

    @GetMapping
    public List<AddressResponse> getMyAddresses(){
        return addressService.getMyAddresses();
    }

    @GetMapping("/{id}")
    public AddressResponse getAddress(@PathVariable Long id){
        return addressService.getAddress(id);
    }

    @PutMapping("/{id}/default")
    public AddressResponse setDefaultAddress(@PathVariable Long id){
        return addressService.setDefaultAddress(id);
    }
}
