/*

package com.capstone.wellnessnavigatorgym.utils;

import com.capstone.wellnessnavigatorgym.dto.tree.TreeNode;
import com.capstone.wellnessnavigatorgym.entity.Course;
import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class BuildDecisionTree {

    private static DecisionTree decisionTree;

    public BuildDecisionTree() {
    }

    public BuildDecisionTree(List<TrackDataAi> trackDataAiDtoList) {
        this.decisionTree = new DecisionTree(trackDataAiDtoList);
    }

    */
/*public TreeNode buildDecisionTree(List<TrackDataAi> data, List<String> attributeNames) {
        if (attributeNames.isEmpty() || data.isEmpty()) {
            // Xác định một đề xuất cụ thể hoặc sử dụng giá trị mặc định
            return createLeafNode(data); // Tạo nút lá dựa trên dữ liệu còn lại
        }

        Map<String, Double> gainRatios = new HashMap<>();
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
        List<Integer> recommendation = createRecommendation(data);
        leafNode.setRecommendation(recommendation);

        return leafNode;
    }

    private List<Course> createRecommendation(List<TrackDataAi> data) {
        Map<Course, Integer> exerciseCount = new HashMap<>();
        for (TrackDataAi trackDataAi : data) {
            if (trackDataAi.getEffective()) {
                Course exerciseType = trackDataAi.getCourse();
                exerciseCount.put(exerciseType, exerciseCount.getOrDefault(exerciseType, 0) + 1);
            }
        }
        if (exerciseCount.isEmpty()) {
            return Collections.emptyList(); // Trả về danh sách trống nếu không có dữ liệu hiệu quả
        }

        // Tìm số lần xuất hiện tối đa
        int maxCount = Collections.max(exerciseCount.values());

        // Lọc và sắp xếp các course_id có số lần xuất hiện bằng maxCount
        return exerciseCount.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(Course::getCourseName))
                .limit(4) // Giới hạn kết quả đề xuất tối đa 4 course_id
                .collect(Collectors.toList());
    }*//*

    public TreeNode buildDecisionTree(List<TrackDataAi> data, List<String> attributeNames) {

        if (data.isEmpty() || attributeNames.isEmpty()) {
            return createLeafNode(data);
        }

        String bestAttribute = selectBestAttribute(data, attributeNames);

        TreeNode node = new TreeNode(bestAttribute);
        node.setGainRatio(decisionTree.calculateGainRatio(bestAttribute));

        Set<Object> attributeValues = decisionTree.getDistinctAttributeValues(bestAttribute);

        for (Object value : attributeValues) {

            List<TrackDataAi> subset = decisionTree.filterByAttributeValue(data, bestAttribute, value);

            if (subset.isEmpty()) {
                node.getChildren().put(value, createLeafNode(data));
            } else {
                List<String> remainingAttributes = new ArrayList<>(attributeNames);
                remainingAttributes.remove(bestAttribute);

                node.getChildren().put(value,
                        buildDecisionTree(subset, remainingAttributes));
            }
        }

        return node;
    }

    private TreeNode createLeafNode(List<TrackDataAi> data) {

        TreeNode leafNode = new TreeNode(null);
        leafNode.setIsLeaf(true);

        leafNode.setClassification(determineMajority(data));

        leafNode.setRecommendation(createRecommendation(data));

        return leafNode;
    }

    private boolean determineMajority(List<TrackDataAi> data) {

        long trueCount = data.stream()
                .filter(TrackDataAi::getEffective)
                .count();

        return trueCount >= data.size() / 2;
    }

    private List<Course> createRecommendation(List<TrackDataAi> data) {

        Map<Course, Integer> courseCount = new HashMap<>();

        for (TrackDataAi trackData : data) {
            if (trackData.getEffective()) {
                Course course = trackData.getCourse();
                courseCount.merge(course, 1, Integer::sum);
            }
        }

        if (courseCount.isEmpty()) {
            return Collections.emptyList();
        }

        int maxCount = Collections.max(courseCount.values());

        return courseCount.entrySet().stream()
                .filter(e -> e.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .sorted(Comparator.comparing(Course::getCourseName))
                .limit(4)
                .collect(Collectors.toList());
    }


    private boolean determineMajorityClassification(List<TrackDataAi> data) {
        if (data.isEmpty()) {
            return false; // Or handle this case as per your requirement
        }

        int countTrue = 0;
        for (TrackDataAi trackDataAi : data) {
            if (trackDataAi.getEffective()) {
                countTrue++;
            }
        }
        return countTrue >= data.size() / 2;
    }

    private boolean areAllExamplesEffective(List<TrackDataAi> data) {
        if (data.isEmpty()) return false;

        boolean firstClassification = data.get(0).getEffective();
        for (TrackDataAi trackDataAi : data) {
            if (trackDataAi.getEffective() != firstClassification) {
                return false;
            }
        }
        return true;
    }

    private String selectBestAttribute(List<TrackDataAi> data, List<String> attributeNames) {
        double maxGainRatio = Double.MIN_VALUE;
        String bestAttribute = null;

        for (String attributeName : attributeNames) {
            double gainRatio = decisionTree.calculateGainRatio(attributeName);

            if (gainRatio > maxGainRatio) {
                maxGainRatio = gainRatio;
                bestAttribute = attributeName;
            }
        }
        return bestAttribute;
    }

//    public void printTree(TreeNode node, String indent) {
//        if (node == null) {
//            return;
//        }
//
//        // In thông tin của nút hiện tại
//        System.out.println(indent + "Node: " + (node.getAttributeName() == null ? "Leaf" : node.getAttributeName()));
//        if (node.getIsLeaf()) {
//            System.out.println(indent + "  Recommendation: " + node.getRecommendation());
//        }
//
//        // Duyệt qua các nút con
//        if (node.getChildren() != null) {
//            for (Map.Entry<Object, TreeNode> entry : node.getChildren().entrySet()) {
//                System.out.println(indent + "  Value: " + entry.getKey());
//                printTree(entry.getValue(), indent + "    ");
//            }
//        }
//    }
}

*/
