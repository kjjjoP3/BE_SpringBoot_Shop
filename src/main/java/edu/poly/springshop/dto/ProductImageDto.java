package edu.poly.springshop.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link edu.poly.springshop.domain.ProductImage} entity
 */
@Data
public class ProductImageDto implements Serializable {
    private  Long id;
    private String uid;
    private  String name;
    private  String fileName;
    private  String url;
    private String status;
    private String response = "{\"status\": \"success\"}";
}