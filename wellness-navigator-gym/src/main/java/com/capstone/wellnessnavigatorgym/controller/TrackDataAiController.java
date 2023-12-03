package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.dto.tree.RecommendationDTO;
import com.capstone.wellnessnavigatorgym.dto.tree.TreeNode;
import com.capstone.wellnessnavigatorgym.dto.tree.UserDataDTO;
import com.capstone.wellnessnavigatorgym.entity.Course;
import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;
import com.capstone.wellnessnavigatorgym.service.ITrackDataAiService;
import com.capstone.wellnessnavigatorgym.utils.BuildDecisionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/track-data-ai")
@CrossOrigin(origins = "http://localhost:3000")
public class TrackDataAiController {

    @Autowired
    private ITrackDataAiService trackDataAiService;

    @Autowired
    private BuildDecisionTree buildDecisionTree;

    @GetMapping("")
    public ResponseEntity<List<TrackDataAi>> getAllTrackDataAi() {
        List<TrackDataAi> trackDataAis = this.trackDataAiService.getAllTrackDataAi();
        if (trackDataAis.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(trackDataAis, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackDataAi> getTrackDataAiById(@PathVariable Integer id) {
        return new ResponseEntity<>(trackDataAiService.findTrackDataAiById(id), HttpStatus.OK);
    }

    @PostMapping("/recommend-course")
    public ResponseEntity<RecommendationDTO> recommendCourse(@RequestBody UserDataDTO userDataDTO) {
        // Get attribute names
        List<String> attributeNames = getAttributeNames();
        if (attributeNames == null || attributeNames.isEmpty())     {
            return ResponseEntity.badRequest().body(new RecommendationDTO(null, "Attribute names are missing or invalid"));
        }

        // Get track data and build the decision tree
        List<TrackDataAi> trackDataAis = trackDataAiService.getAllTrackDataAi();
        if (trackDataAis.isEmpty()) {
            return ResponseEntity.badRequest().body(new RecommendationDTO(null, "No track data available to build the decision tree"));
        }

        TreeNode decisionTree= buildDecisionTree.buildDecisionTree(trackDataAis, attributeNames);

        // Extract user data from UserDataDTO
        Map<String, Object> userData = extractUserDataFromTrackDataAi(userDataDTO);

        // Traverse the decision tree and get recommendations
        List<Course> recommendations = traverseDecisionTree(decisionTree, userData);

        return ResponseEntity.ok(new RecommendationDTO(recommendations, "Course recommendations generated successfully"));
    }

    private Map<String, Object> extractUserDataFromTrackDataAi(UserDataDTO userDataDTO) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("activityLevel", userDataDTO.getActivityLevel());
        userData.put("age", userDataDTO.getAge());
        userData.put("gender", userDataDTO.getGender());
        userData.put("bmi", userDataDTO.getBmi());
        userData.put("trainingGoals", userDataDTO.getTrainingGoals());
        userData.put("trainingHistory", userDataDTO.getTrainingHistory());
        return userData;
    }

/*    private static List<Course> traverseDecisionTree(TreeNode node, Map<String, Object> userData) {
        if (node.getIsLeaf()) {
            return node.getRecommendation();
        } else {
            Object attributeValue = userData.get(node.getAttributeName());
            if (attributeValue != null) {
                TreeNode childNode = node.getChildren().get(attributeValue);
                if (childNode != null) {
                    return traverseDecisionTree(childNode, userData);
                }
            }
            return Collections.emptyList();
        }
    }*/

    private static List<Course> traverseDecisionTree(TreeNode node, Map<String, Object> userData) {
        if (!node.getIsLeaf()) {
            Object attributeValue = userData.get(node.getAttributeName());
            if (attributeValue != null) {
                TreeNode childNode = node.getChildren().get(attributeValue);
                if (childNode != null) {
                    return traverseDecisionTree(childNode, userData);
                }
            }
        }
        return node.getRecommendation();
    }


    private List<String> getAttributeNames() {
        return Arrays.asList("activityLevel", "age", "gender", "bmi", "trainingGoals", "trainingHistory");
    }
}