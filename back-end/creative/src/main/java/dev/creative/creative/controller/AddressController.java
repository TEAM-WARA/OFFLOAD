package dev.creative.creative.controller;


import dev.creative.creative.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/address")
@ApiIgnore
public class AddressController {
    private final static Logger logger = LoggerFactory.getLogger(AddressController.class);
    private final AddressService addressService;

    public AddressController(
            @Autowired AddressService addressService
    ){
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public String toAddress(
            @RequestParam("x") Double x,
            @RequestParam("y") Double y
    ){
        return this.addressService.toAddress(x, y);
    }
    @GetMapping("/coordinate")
    public LinkedHashMap toCoordinate(
            @RequestParam("addr") String address
    ){
      return this.addressService.toCoordinate(address);
    }


}
