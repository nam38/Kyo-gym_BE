package com.capstone.wellnessnavigatorgym.dto.tree;

import com.capstone.wellnessnavigatorgym.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private String attributeName;
    private Map<Object, TreeNode> children;
    private Boolean isLeaf;
    private Boolean classification;
    private Double gainRatio;
    private List<Course> recommendation;

    public TreeNode(String attributeName) {
        this.attributeName = attributeName;
        this.children = new HashMap<>();
        this.isLeaf = false;
        this.classification = false;
        this.gainRatio = 0.0; // Khởi tạo Gain Ratio
    }
}
