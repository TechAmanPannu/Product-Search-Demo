package com.product.search.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = -8170157170558487083L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String liam;

//    private String uom;
//
    private String status;
//
    @Column(name = "ah_code")
    private String ahCode;

    @Column(name = "mch_code")
    private  String mchCode;

    @Column(columnDefinition = "jsonb")
    private String data;

    @Column(name = "inserted_at")
    private String insertedAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(columnDefinition = "jsonb")
    private String enrichment;
//
//    private Long version;
//
    @Column(name = "brand_code")
    private String brandCode;
//
////    private String date;
//
//    @Column(name = "weight_required")
//    private Boolean weightRequired;
//
//    @Column(name = "scale_indicator")
//    private Boolean scaleIndicator;
//
//    @Column(name = "article_number")
//    private String articleNumber;
//
//    @Column(name = "sub_brand_id")
//    private Long subBrandId;
//
//    @Column(name = "image_angles", columnDefinition = "text")
//    private String imageAngles;
//
//    @Column(name = "selling_type")
//    private String sellingType;
//
//
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
//
//    @Column(name = "nav_categories")
//    private String navCategories;
//
//    @Column(name = "ah_map_type")
//    private Integer ahMapType;
//
//    @Column(name = "primary_variant")
//    private Boolean primaryVariant;
//
    @Column(name = "variant_data", columnDefinition = "jsonb")
    private String variantData;
//
//    @Column(name = "variant_group")
//    private String variantGroup;
//
//    @Column(name = "variant_template_id")
//    private Long variantTemplateId;
//
//    @Column(name = "jf_enrichment_version")
//    private Long jfEnrichmentVersion;
//
//    private String type;
//
//    private String fineline;
//
//    @Column(columnDefinition = "text")
//    private String modiface;
//
//    @Column(columnDefinition = "integer[]")
//    private Integer[] arr;
//
//    private UUID bid;
//
//    private String upc;
//
//    @Column(columnDefinition = "jsonb")
//    private String exclusions;

//    @Column(name = "activation_fee_liam")
//    private String activationFeeLiam;
}
