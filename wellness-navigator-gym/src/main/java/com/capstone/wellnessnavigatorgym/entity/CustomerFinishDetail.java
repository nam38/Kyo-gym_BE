package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class CustomerFinishDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerFinishDetailId;
    private Double customerFinishDetailHeight;
    private Double customerFinishDetailWeight;
    private Double customerFinishDetailBmi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
