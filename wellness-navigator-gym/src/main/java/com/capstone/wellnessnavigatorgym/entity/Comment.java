package com.capstone.wellnessnavigatorgym.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private String commentName;
    private Integer rating;
    private String commentText;
    private String avatar;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
}
