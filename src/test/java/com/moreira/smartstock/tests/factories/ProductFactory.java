package com.moreira.smartstock.tests.factories;

import com.moreira.smartstock.dtos.ProductSaveDTO;
import com.moreira.smartstock.entities.Category;
import com.moreira.smartstock.entities.Product;
import com.moreira.smartstock.entities.UnitMeasure;

public class ProductFactory {

    public static Product createProduct() {
        Product product = new Product(1L, "Martelo de Borracha 500g", 25.00, 100, UnitMeasure.UN);
        product.setCategory(new Category(1L, "Ferramentas"));
        return product;
    }

    public static ProductSaveDTO createProductSaveDTO() {
        Product product = createProduct();
        return new ProductSaveDTO(product.getName(), product.getPrice(), product.getUnitMeasure().name(), product.getCategory().getId());
    }
}
