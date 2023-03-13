package edu.poly.springshop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends AbstractEntity {


    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Enumerated
    @Column(name = "status", nullable = false)
    private CategoryStatus status;


}