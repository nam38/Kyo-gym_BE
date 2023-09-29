package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.ReviewAssignmentSuggestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IReviewAssignmentSuggestionsRepository extends JpaRepository<ReviewAssignmentSuggestions, Integer> {
}
