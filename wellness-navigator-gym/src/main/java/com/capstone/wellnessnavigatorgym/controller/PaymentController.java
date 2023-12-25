package com.capstone.wellnessnavigatorgym.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Value("${stripe.apikey}")
    String stripeApikey;
    @GetMapping("/")
    public String hello() {
        return "hello"+ stripeApikey;
    }

}
