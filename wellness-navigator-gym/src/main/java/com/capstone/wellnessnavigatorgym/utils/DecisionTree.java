package com.capstone.wellnessnavigatorgym.utils;

import com.capstone.wellnessnavigatorgym.entity.TrackDataAi;

import java.util.*;

public class DecisionTree {

    private ArrayList<TrackDataAi> trackDataAiData;

    public DecisionTree(List<TrackDataAi> trackDataAis) {
        this.trackDataAiData = new ArrayList<>(trackDataAis);
    }

    public double calculateInformationGain(String attributeName) {
        double totalSize = trackDataAiData.size();
        if (totalSize == 0) return 0;

        double entropyS = calculateEntropyForWholeDataSet();
        double informationGain = entropyS;

        Map<Object, List<TrackDataAi>> subsets = getSubsetsByAttributeValue(attributeName);
        for (List<TrackDataAi> subset : subsets.values()) {
            double subsetSize = subset.size();
            double subsetEntropy = calculateEntropyForSubset(subset);
            informationGain -= (subsetSize / totalSize) * subsetEntropy;
        }
        return informationGain;
    }

    private Map<Object, List<TrackDataAi>> getSubsetsByAttributeValue(String attributeName) {
        Map<Object, List<TrackDataAi>> subsets = new HashMap<>();
        for (TrackDataAi exercise : trackDataAiData) {
            Object value = exercise.getAttributeValue(attributeName);
            subsets.computeIfAbsent(value, k -> new ArrayList<>()).add(exercise);
        }
        return subsets;
    }



    // Đếm số lượng mục tiêu tích cực hoặc tiêu cực trong một danh sách cụ thể của Exercise I(S)
    private double calculateEntropyForWholeDataSet() {
        int positiveCount = countEffective(trackDataAiData, true);
        int negativeCount = trackDataAiData.size() - positiveCount;
        return calculateEntropy(positiveCount, negativeCount);
    }


    // Tính toán entropy cho một subset cụ thể của dữ liệu
    private double calculateEntropyForSubset(List<TrackDataAi> subset) {
        int positiveCount = countEffective(subset, true);
        int negativeCount = subset.size() -positiveCount;
        return calculateEntropy(positiveCount, negativeCount);
    }

    private int countEffective(List<TrackDataAi> dataList, boolean effective) {
        int count = 0;
        for (TrackDataAi data : dataList) {
            if (data.getEffective() == effective) {
                count++;
            }
        }
        return count;
    }

    // tính toán entropy dựa trên số lượng ví dụ dương và âm
    private double calculateEntropy(int positiveCount, int negativeCount) {
        if (positiveCount < 0 || negativeCount < 0) {
            throw new IllegalArgumentException("Count values cannot be negative.");
        }
        if (positiveCount == 0 || negativeCount == 0) {
            return 0.0; // Không có entropy nếu không có sự đa dạng
        }
        int totalCount = positiveCount + negativeCount;
        return -calculateEntropyPart(positiveCount, totalCount) - calculateEntropyPart(negativeCount, totalCount);
    }


    private double calculateEntropyPart(int count, int totalCount) {
        double probability = (double) count / totalCount;
        return probability * Math.log(probability) / Math.log(2);
    }


    // Trả về tập hợp các giá trị duy nhất cho một thuộc tính cụ thể
    public Set<Object> getDistinctAttributeValues(String attributeName) {
        Set<Object> distinctValues = new HashSet<>();
        for (TrackDataAi exercise : trackDataAiData) {
            Object value = exercise.getAttributeValue(attributeName);
            distinctValues.add(value);
        }
        return distinctValues;
    }

    public List<TrackDataAi> filterByAttributeValue(List<TrackDataAi> data, String attributeName, Object value) {
        List<TrackDataAi> subset = new ArrayList<>();
        for (TrackDataAi exercise : data) {
            Object attributeValue = exercise.getAttributeValue(attributeName); // You need to implement getAttributeValue() in Exercise
            if (attributeValue != null && attributeValue.equals(value)) {
                subset.add(exercise);
            }
        }
        return subset;
    }

    public double calculateSplitInfo(String attributeName) {
        double totalSize = trackDataAiData.size();
        double splitInfo = 0.0;

        Set<Object> distinctAttributeValues = getDistinctAttributeValues(attributeName);
        for (Object attributeValue : distinctAttributeValues) {
            List<TrackDataAi> subset = filterByAttributeValue(trackDataAiData, attributeName, attributeValue);
            double proportion = subset.size() / totalSize;
            if (proportion > 0) {
                splitInfo -= proportion * Math.log(proportion) / Math.log(2);
            }
        }

        return splitInfo;
    }

    public double calculateGainRatio(String attributeName) {
        double informationGain = calculateInformationGain(attributeName);
        double splitInfo = calculateSplitInfo(attributeName);

        // Avoid division by zero
        if (splitInfo == 0) {
            return 0;
        }

        return informationGain / splitInfo;
    }

}