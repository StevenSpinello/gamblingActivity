package ru.efimkin.bredik.productservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.efimkin.bredik.productservice.model.ProductModel;
import ru.efimkin.bredik.productservice.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductModel getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    public ProductModel addProduct(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public ProductModel changeQuantity(Long id, int quantity) {
        ProductModel updatedProductModel = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        updatedProductModel.setStock(quantity);
        return productRepository.save(updatedProductModel);
    }

    public void  deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
