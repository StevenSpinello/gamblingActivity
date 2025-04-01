package ru.efimkin.bredik.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.efimkin.bredik.productservice.model.ProductModel;

@Repository
public interface ProductRepository extends JpaRepository <ProductModel, Long> {
}
