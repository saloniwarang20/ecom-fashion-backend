package com.example.ecom_backend.specification;

import com.example.ecom_backend.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ProductSpecification {

    public static Specification<Product> hasSubCategory(Long subCategoryId){
        return (root, query, criteriaBuilder) ->
                subCategoryId == null
                ?null
                        : criteriaBuilder.equal(root.get("subCategory").get("id"),subCategoryId);
    }

    public static Specification<Product> hasBrand(String brand){
        return (root, query, criteriaBuilder) ->
                brand == null || brand.isBlank()
                        ?null
                        : criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")),brand.toLowerCase());
    }

    public static Specification<Product> minPrice(Double minPrice){
        return (root, query, criteriaBuilder) ->
                minPrice == null
                        ?null
                        : criteriaBuilder.greaterThanOrEqualTo(root.get("price"),minPrice);
    }

    public static Specification<Product> maxPrice(Double maxPrice){
        return (root, query, criteriaBuilder) ->
                maxPrice == null
                        ?null
                        : criteriaBuilder.lessThanOrEqualTo(root.get("price"),maxPrice);
    }

    public static Specification<Product> hasColors(List<String> colors){
        return (root, query, criteriaBuilder) ->
        {
            if(colors == null || colors.isEmpty())
                return null;

            query.distinct(true);

            return root.join("productVariantList")
                    .get("color")
                    .in(colors);
        };
    }

    public static Specification<Product> hasSizes(List<String> sizes){
        return (root, query, criteriaBuilder) ->
        {
            if(sizes == null || sizes.isEmpty())
                return null;

            query.distinct(true);

            return root.join("productVariantList")
                    .get("size")
                    .in(sizes);
        };
    }
}
