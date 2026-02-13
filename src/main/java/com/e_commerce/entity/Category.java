package com.e_commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Category extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    // self-referencing relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> subCategories = new ArrayList<>();

    // @Transient fields doesn't get stored in db, calculated at runtime
    @Transient
    private boolean inStock;


    @PostLoad
    private void computeTransientFields() {
        this.inStock = this.inStockQuantity != null && thi.stockQuantity > 0;
    }


}
