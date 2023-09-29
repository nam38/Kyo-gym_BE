package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class CustomerStartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerStartDetailId;
    private Double customerStartDetailHeight;
    private Double customerStartDetailWeight;
    private Double customerStartDetailBmi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
