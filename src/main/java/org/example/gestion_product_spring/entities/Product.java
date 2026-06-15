package org.example.gestion_product_spring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author macbook
 **/
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class Product {
    @Id @GeneratedValue
    private Long id;
    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;
    @Min(0)
    private Double price;
    @Min(1)
    private double quantity;


}
