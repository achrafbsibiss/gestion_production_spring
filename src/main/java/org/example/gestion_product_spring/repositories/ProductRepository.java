package org.example.gestion_product_spring.repositories;

import org.example.gestion_product_spring.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // derived query
    List<Product> findByNameContainsIgnoreCase(String keyword);

    // explicit JPQL search
    @Query("select p from Product p where lower(p.name) like lower(concat('%', :kw, '%'))")
    List<Product> search(@Param("kw") String keyword);
}
