package com.capstone.wellnessnavigatorgym.repository;

import com.capstone.wellnessnavigatorgym.entity.Comment;
import com.capstone.wellnessnavigatorgym.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ICommentRepository extends JpaRepository<Comment, Integer> {
    @Query(value = "SELECT * FROM `comments` where comment_id = :id", nativeQuery = true)
    Optional<Comment> findCommentById(@Param("id") Integer id);
}
