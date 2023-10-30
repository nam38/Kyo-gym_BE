package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class CustomerCollectionWorkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerCollectionWorkoutId;
    private String customerCollectionWorkoutName;
    private String customerCollectionWorkoutType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
