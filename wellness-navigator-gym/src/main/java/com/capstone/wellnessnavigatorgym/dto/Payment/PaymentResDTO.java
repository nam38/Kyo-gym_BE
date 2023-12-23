package com.capstone.wellnessnavigatorgym.dto.Payment;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class PaymentResDTO implements Serializable {
    private  String status;
    private  String message;
    private  String URL;

}
