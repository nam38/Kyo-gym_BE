package com.capstone.wellnessnavigatorgym.utils;

import com.capstone.wellnessnavigatorgym.dto.tree.TreeNode;
import com.capstone.wellnessnavigatorgym.entity.Course;
import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;

import java.util.*;
import java.util.stream.Collectors;

public class BuildDecisionTree {

    private DecisionTree decisionTree;

    public BuildDecisionTree() {
    }

    public BuildDecisionTree(List<TrackDataAi> trackDataAis) {
        this.decisionTree = new DecisionTree(trackDataAis);
    }

    public TreeNode buildDecisionTree(List<TrackDataAi> data, List<String> attributeNames) {
        if (attributeNames.isEmpty() || data.isEmpty()) {
            // Xác định một đề xuất cụ thể hoặc sử dụng giá trị mặc định
            return createLeafNode(data); // Tạo nút lá dựa trên dữ liệu còn lại
        }

        Map<String, Double> gainRatios = new HashMap<>();

        if (this.decisionTree == null) {
            this.decisionTree = new DecisionTree(data); // hoặc khởi tạo theo cách phù hợp
        }

        for (String attributeName : attributeNames) {
            gainRatios.put(attributeName, decisionTree.calculateGainRatio(attributeName));
        }

        List<Map.Entry<String, Double>> sortedAttributes = new ArrayList<>(gainRatios.entrySet());
        sortedAttributes.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));

        TreeNode node = null;
        for (Map.Entry<String, Double> attribute : sortedAttributes) {
            String bestAttribute = attribute.getKey();
            double bestGainRatio = attribute.getValue();

            node = new TreeNode(bestAttribute);
            node.setGainRatio(bestGainRatio);

            Set<Object> attributeValues = decisionTree.getDistinctAttributeValues(bestAttribute);
            for (Object value : attributeValues) {
                List<TrackDataAi> subset = decisionTree.filterByAttributeValue(data, bestAttribute, value);
                if (subset.isEmpty()) {
                    // Xác định một đề xuất cụ thể dựa trên dữ liệu còn lại
                    TreeNode leafNode = createLeafNode(data); // Sử dụng dữ liệu còn lại để tạo nút lá
                    node.getChildren().put(value, leafNode);
                } else {
                    // Xử lý dữ liệu con và tạo nhánh mới
                    List<String> remainingAttributes = new ArrayList<>(attributeNames);
                    remainingAttributes.remove(bestAttribute);
                    node.getChildren().put(value, buildDecisionTree(subset, remainingAttributes));
                }
            }
        }
        return node;
    }

    private TreeNode createLeafNode(List<TrackDataAi> data) {
        TreeNode leafNode = new TreeNode(null);
        leafNode.setIsLeaf(true);

        // Xác định phân loại đa số
        boolean classification = determineMajorityClassification(data);
        leafNode.setClassification(classification);

        // Tạo đề xuất dựa trên dữ liệu
        List<Course> recommendation = createRecommendation(data);
        leafNode.setRecommendation(recommendation);

        return leafNode;
    }

    private List<Course> createRecommendation(List<TrackDataAi> data) {
        Map<Integer, Integer> courseEffectiveness = new HashMap<>();
        Map<Integer, Integer> courseCount = new HashMap<>();
        Map<Integer, Course> uniqueCoursesMap = new HashMap<>();

        for (TrackDataAi exercise : data) {
            Course course = exercise.getCourse();
            uniqueCoursesMap.put(course.getCourseId(), course);
            courseCount.put(course.getCourseId(), courseCount.getOrDefault(course.getCourseId(), 0) + 1);
            if (exercise.getEffective()) {
                courseEffectiveness.put(course.getCourseId(), courseEffectiveness.getOrDefault(course.getCourseId(), 0) + 1);
            }
        }

        final double effectivenessThreshold = 0.5; // 50% effectiveness
        return uniqueCoursesMap.values().stream()
                .filter(c -> {
                    int courseId = c.getCourseId();
                    double effectivenessRatio = courseEffectiveness.getOrDefault(courseId, 0) / (double) courseCount.get(courseId);
                    return effectivenessRatio >= effectivenessThreshold;
                })
                .sorted((c1, c2) -> {
                    int courseId1 = c1.getCourseId();
                    int courseId2 = c2.getCourseId();
                    return Double.compare(
                            courseEffectiveness.getOrDefault(courseId2, 0) / (double) courseCount.get(courseId2),
                            courseEffectiveness.getOrDefault(courseId1, 0) / (double) courseCount.get(courseId1)
                    );
                })
                .limit(4)
                .collect(Collectors.toList());
    }
    
    private boolean determineMajorityClassification(List<TrackDataAi> data) {
        if (data.isEmpty()) {
            return false; // Or handle this case as per your requirement
        }

        int countTrue = 0;
        for (TrackDataAi exercise : data) {
            if (exercise.getEffective()) {
                countTrue++;
            }
        }
        return countTrue >= data.size() / 2;
    }
}
