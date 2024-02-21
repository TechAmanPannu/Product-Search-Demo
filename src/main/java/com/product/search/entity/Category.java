package com.product.search.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Data
@Entity
@Table(name = "categories")
public class Category implements Serializable {

    private static final long serialVersionUID = 6861330341858471206L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String code;

    @Column(columnDefinition = "jsonb")
    private String name;

    @Column(name = "category_paths")
    private String categoryPath;

    private String type;

    @Column(name = "inserted_at")
    private String insertedAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "pcs_code")
    private Integer pcsCode;

}
