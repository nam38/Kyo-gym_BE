package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Nutritional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer nutritionalId;
    private String nutritionalName;
    private String nutritionalDescription;
    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbohydrates;
    private Integer serving;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "nutritional_type_id")
    private NutritionalType nutritionalType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "day_id")
    private Day day;
}
