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
public class ProductDto implements Serializable {
    private  Long id;
    @NotEmpty(message = "Name ís required")
    private  String name;
    @Min(value = 0)
    private  Integer quantity;
    @Min(value = 0)
    private  Double price;
    @Min(value = 0)
    @Max(value = 100)
    private  Float discount;

    private  Long viewCount;
    private  Boolean isFeatured;
    private  String brief;
    private  String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private  Date manufactureDate;
    private  ProductStatus status;

    private Long categoryId;
    private Long manufacturerId;

    private List<ProductImageDto> images;

    private ProductImageDto image;

    private CategoryDto category;
    private ManufacturerDto manufacturer;
}