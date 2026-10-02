package com.example.ecom_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = false)
    private Category category;
}
