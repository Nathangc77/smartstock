package com.moreira.smartstock.services;

import com.moreira.smartstock.dtos.ProductSaveDTO;
import com.moreira.smartstock.dtos.ProductDTO;
import com.moreira.smartstock.entities.Category;
import com.moreira.smartstock.entities.Product;
import com.moreira.smartstock.entities.UnitMeasure;
import com.moreira.smartstock.repositories.CategoryRepository;
import com.moreira.smartstock.repositories.ProductRepository;
import com.moreira.smartstock.services.exceptions.DatabaseException;
import com.moreira.smartstock.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public ProductDTO insert(ProductSaveDTO dto) {
        try {
            Product entity = new Product();
            copyDtoForEntity(dto, entity);
            entity.setQuantity(0);

            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    public ProductDTO update(Long id, ProductSaveDTO dto) {
        Product entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Produto não encontrado"));
        try {
            copyDtoForEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha na integridade referencial");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Produto não encontrado");
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha na integridade referencial");
        }
    }

    private void copyDtoForEntity(ProductSaveDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
        Category cat = categoryRepository.getReferenceById(dto.getCategoryId());
        entity.setCategory(cat);
        entity.setUnitMeasure(UnitMeasure.valueOf(dto.getUnitMeasure()));
    }
}
