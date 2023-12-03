package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.dto.tree.RecommendationDTO;
import com.capstone.wellnessnavigatorgym.dto.tree.TreeNode;
import com.capstone.wellnessnavigatorgym.dto.tree.UserDataDTO;
import com.capstone.wellnessnavigatorgym.entity.Course;
import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;
import com.capstone.wellnessnavigatorgym.service.ITrackDataAiService;
import com.capstone.wellnessnavigatorgym.utils.BuildDecisionTree;
import com.capstone.wellnessnavigatorgym.utils.DecisionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        TrackDataAi trackDataAi;

        // Lấy danh sách thuộc tính và kiểm tra chúng
        List<String> attributeNames = getAttributeNames();
        if (attributeNames == null || attributeNames.isEmpty()) {
            return ResponseEntity.badRequest().body(new RecommendationDTO(null, "Attribute names are missing or invalid"));
        }
        // Lấy dữ liệu và xây dựng cây quyết định
        List<TrackDataAi> trackDataAis = trackDataAiService.getAllTrackDataAi();
        if (trackDataAis.isEmpty()) {
            return ResponseEntity.badRequest().body(new RecommendationDTO(null, "No track data available to build the decision tree"));
        }

        TreeNode decisionTree = buildDecisionTree.buildDecisionTree(trackDataAis, attributeNames);

        // Tạo map dữ liệu người dùng từ TrackDataAi
        Map<String, Object> userData = extractUserDataFromTrackDataAi(userDataDTO);

        // Duyệt qua cây quyết định và tìm đề xuất
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

    private static List<Course> traverseDecisionTree(TreeNode node, Map<String, Object> userData) {
        if (node == null) {
            return Collections.emptyList(); // Trả về danh sách rỗng nếu node là null
        }

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
    }



    private List<String> getAttributeNames() {
        return Arrays.asList("activityLevel", "age", "gender", "bmi", "trainingGoals", "trainingHistory");
    }

    private List<Course> getRecommendationsFromLeafNode(TreeNode node, TrackDataAi trackDataAi) {
        if (node.getIsLeaf()) {
            return node.getRecommendation();
        }

        String attributeName = node.getAttributeName();
        Object attributeValue = trackDataAi.getAttributeValue(attributeName);
        return getRecommendationsFromLeafNode(node.getChildren().get(attributeValue), trackDataAi);
    }
}
