package com.example.shopping.repository;

import com.example.shopping.model.Product;
import com.example.shopping.model.enumRole.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    List<Product>findByCategory(CategoryProduct categoryProduct);

}
