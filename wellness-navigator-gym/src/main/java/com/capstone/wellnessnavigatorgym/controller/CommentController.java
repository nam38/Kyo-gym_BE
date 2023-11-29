package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.entity.Comment;
import com.capstone.wellnessnavigatorgym.service.ICommentService;
import com.capstone.wellnessnavigatorgym.service.ICustomerService;
import com.capstone.wellnessnavigatorgym.service.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IExerciseService exerciseService;

    @GetMapping("")
    public ResponseEntity<List<Comment>> getAllComment() {
        List<Comment> commentList = this.commentService.findAll();
        if (commentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer id) {
        return new ResponseEntity<>(commentService.findCommentById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        try {
            if (!customerService.existsById(comment.getCustomer().getCustomerId())) {
                return new ResponseEntity<>("Customer with ID " + comment.getCustomer().getCustomerId() + " not found.", HttpStatus.NOT_FOUND);
            }

            if (!exerciseService.existsById(comment.getExercise().getExerciseId())) {
                return new ResponseEntity<>("Exercise with ID " + comment.getExercise().getExerciseId() + " not found.", HttpStatus.NOT_FOUND);
            }

            Comment savedComment = commentService.saveComment(comment);

            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating the comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
