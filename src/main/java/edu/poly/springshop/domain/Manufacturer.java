package edu.poly.springshop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "manufacturer")
public class Manufacturer extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo")
    private String logo;



}