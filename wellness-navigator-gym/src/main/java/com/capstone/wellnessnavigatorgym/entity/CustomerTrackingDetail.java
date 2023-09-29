package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class CustomerTrackingDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerTrackingDetailId;
    private String customerTrackingDetailName;
    private Date date;
    private Double customerTrackingDetailHeight;
    private Double customerTrackingDetailWeight;
    private Double customerTrackingDetailBmi;
    private Boolean isEnable;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
