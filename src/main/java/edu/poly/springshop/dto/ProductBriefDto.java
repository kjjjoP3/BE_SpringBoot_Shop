package edu.poly.springshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.poly.springshop.domain.ProductStatus;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link edu.poly.springshop.domain.Product} entity
 */
@Data
public class ProductBriefDto implements Serializable {
    private  Long id;
    private  String name;
    private  Integer quantity;
    private  Double price;
    private  Float discount;
    private  Long viewCount;
    private  Boolean isFeatured;
    private  String brief;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private  Date manufactureDate;
    private  ProductStatus status;
    private String categoryName;
    private String manufacturerName;
    private String imageFileName;
}