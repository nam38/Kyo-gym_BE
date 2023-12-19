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
        Map<Integer, Course> uniqueCoursesMap = new HashMap<>();

        for (TrackDataAi exercise : data) {
            if (exercise.getEffective()) {
                Course course = exercise.getCourse();
                uniqueCoursesMap.putIfAbsent(course.getCourseId(), course);
            }
        }

        // Trả về danh sách các khóa học duy nhất, giới hạn tối đa 4 khóa học
        return new ArrayList<>(uniqueCoursesMap.values()).stream()
                .limit(4) // Giới hạn số lượng khóa học
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
