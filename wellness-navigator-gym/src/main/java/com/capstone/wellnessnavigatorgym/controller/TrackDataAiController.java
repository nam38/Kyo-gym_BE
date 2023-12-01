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

import java.util.Arrays;
import java.util.List;
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
        if (userDataDTO.getTrackDataAiId() != null) {
            trackDataAi = trackDataAiService.findTrackDataAiById(userDataDTO.getTrackDataAiId());
            if (trackDataAi == null) {
                return ResponseEntity.notFound().build();
            }
        } else {
//            trackDataAi = new TrackDataAi(userDataDTO);
//            // xử lý bất dồng bộ
//            ExecutorService executorService = Executors.newSingleThreadExecutor();
//            executorService.execute(() -> trackDataAiService.saveTrackDataAi(trackDataAi));
//            executorService.shutdown();
            trackDataAi = buildTrackDataAiFromUserData(userDataDTO);
            trackDataAiService.saveTrackDataAi(trackDataAi);
        }

        // Build decision tree
        List<TrackDataAi> trackDataAis = trackDataAiService.getAllTrackDataAi();
        TreeNode decisionTree = buildDecisionTree.buildDecisionTree(trackDataAis, getAttributeNames());

        // Make a prediction
        List<Course> recommendations = getRecommendationsFromLeafNode(decisionTree, trackDataAi);

        return ResponseEntity.ok(new RecommendationDTO(recommendations, "Course recommendations generated successfully."));
    }

    private TrackDataAi buildTrackDataAiFromUserData(UserDataDTO userDataDTO) {
        return new TrackDataAi(userDataDTO);
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
