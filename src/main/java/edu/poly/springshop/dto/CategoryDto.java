package edu.poly.springshop.dto;

import edu.poly.springshop.domain.CategoryStatus;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * A DTO for the {@link edu.poly.springshop.domain.Category} entity
 */
@Data
public class CategoryDto implements Serializable {
    private Long id;

    @NotEmpty(message = "Category name is required")
    private String name;
    private CategoryStatus status;
}