package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class ReviewAssignmentSuggestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewAssignmentSuggestionsId;
    private String reviewAssignmentSuggestionsName;
    private Integer quantity;
    private String reviewAssignmentSuggestionsDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;
}
