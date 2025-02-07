package com.example.shopping.service;

import com.example.shopping.model.Product;
import com.example.shopping.model.enumRole.CategoryProduct;
import com.example.shopping.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }


    public Product findById(long Idproduct) {
        return productRepository.findById(Idproduct).get();
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public List<Product> getByCategory(CategoryProduct categoryProduct) {
        return productRepository.findByCategory(categoryProduct);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product editProduct(long id, Product product) {
        Product productFind = productRepository.findById(id).get();
        productFind.setName(product.getName());
        productFind.setDescription(product.getDescription());
        productFind.setCategory(product.getCategory());
        productFind.setPrice(product.getPrice());
        productFind.setAmount(product.getAmount());
        return productRepository.save(productFind);
    }

}
