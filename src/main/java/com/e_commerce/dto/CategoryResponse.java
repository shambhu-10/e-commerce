package com.e_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private Boolean active;

    // parent category info
    private Long parentId;
    private String parentName;

    // List of sub categories, only populated for root categories to show hierarchy
    private List<CategoryResponse> subCategories;
}
