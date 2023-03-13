package edu.poly.springshop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * A DTO for the {@link edu.poly.springshop.domain.Manufacturer} entity
 */
@Data
public class ManufacturerDto implements Serializable {
    private  Long id;
    private  String name;
    private  String logo;

    @JsonIgnore
    private MultipartFile logoFile;
}