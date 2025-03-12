package com.moreira.smartstock.services;

import com.moreira.smartstock.dtos.InsertProductDTO;
import com.moreira.smartstock.dtos.ProductDTO;
import com.moreira.smartstock.entities.Category;
import com.moreira.smartstock.entities.Product;
import com.moreira.smartstock.entities.UnitMeasure;
import com.moreira.smartstock.repositories.CategoryRepository;
import com.moreira.smartstock.repositories.ProductRepository;
import com.moreira.smartstock.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> result = repository.findAll(pageable);
        return result.map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado"));
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO insert(InsertProductDTO dto) {
        try {
            Product entity = new Product();
            entity.setName(dto.getName());
            entity.setPrice(dto.getPrice());
            entity.setQuantity(0);
            Category cat = categoryRepository.getReferenceById(dto.getCategoryId());
            entity.setCategory(cat);
            entity.setUnitMeasure(UnitMeasure.valueOf(dto.getUnitMeasure()));

            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }
}
