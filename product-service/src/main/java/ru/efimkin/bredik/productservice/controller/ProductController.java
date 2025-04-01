package ru.efimkin.bredik.productservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.efimkin.bredik.productservice.model.ProductModel;
import ru.efimkin.bredik.productservice.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity <List<ProductModel>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity <ProductModel> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity <ProductModel> addProduct (@RequestBody ProductModel product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity <ProductModel> changeQuantity (@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(productService.changeQuantity(id,quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
