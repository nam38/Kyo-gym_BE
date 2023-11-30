package com.capstone.wellnessnavigatorgym.controller;

import com.capstone.wellnessnavigatorgym.dto.tree.TreeNode;
import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;
import com.capstone.wellnessnavigatorgym.utils.BuildDecisionTree;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/decision-tree")
@CrossOrigin(origins = "http://localhost:3000")
public class DecisionTreeController {


    private final BuildDecisionTree buildDecisionTree;

    public DecisionTreeController(BuildDecisionTree buildDecisionTree) {
        this.buildDecisionTree = buildDecisionTree;
    }

    @PostMapping("/build")
    public TreeNode buildDecisionTree(@RequestBody List<TrackDataAi> trackDataAiList,
                                      @RequestParam List<String> attributeNames) {
        return buildDecisionTree.buildDecisionTree(trackDataAiList, attributeNames);
    }

//
//    @GetMapping("/print")
//    public ResponseEntity<String> printDecisionTree() {
//        List<TrackDataAi> trackDataAis = trackDataAiService.getAllTrackDataAi();
//        List<String> attributeNames = Arrays.asList("activity_level", "age", "gender", "bmi", "suggested_exercises", "training_goals", "training_history", "customer_id", "effective");
//
//        TreeNode root = buildDecisionTree.buildDecisionTree(trackDataAis, attributeNames);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(baos);
//
//        buildDecisionTree.printTree(root, "", printStream);
//
//        return new ResponseEntity<>(baos.toString(), HttpStatus.OK);
//    }
}
