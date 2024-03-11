package com.Pasternak.DietProducts.services;

import com.Pasternak.DietProducts.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
