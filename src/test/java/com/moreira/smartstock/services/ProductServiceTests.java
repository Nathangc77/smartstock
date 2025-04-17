package com.moreira.smartstock.services;

import com.moreira.smartstock.dtos.ProductDTO;
import com.moreira.smartstock.dtos.ProductSaveDTO;
import com.moreira.smartstock.entities.Category;
import com.moreira.smartstock.entities.Product;
import com.moreira.smartstock.repositories.CategoryRepository;
import com.moreira.smartstock.repositories.ProductRepository;
import com.moreira.smartstock.services.exceptions.DatabaseException;
import com.moreira.smartstock.services.exceptions.ResourceNotFoundException;
import com.moreira.smartstock.tests.factories.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private Long existingId, nonExistingId;
    private Long existingCategoryId, nonExistingCategoryId;

    private Product product;
    private ProductSaveDTO productSaveDTO;
    private Category category;
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        existingCategoryId = 1L;
        nonExistingCategoryId = 2L;

        product = ProductFactory.createProduct();
        productSaveDTO = ProductFactory.createProductSaveDTO();
        category = new Category(1L, "Ferramentas");

        page = new PageImpl<>(List.of(product));

        Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(categoryRepository.findById(existingCategoryId)).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.findById(nonExistingCategoryId)).thenReturn(Optional.empty());

        Mockito.when(repository.save(any())).thenReturn(product);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);

        Mockito.doNothing().when(repository).deleteById(existingId);
    }

    @Test
    public void findAllShouldReturnProductsPaged() {
        Pageable pageable = PageRequest.of(0, 8);

        Page<ProductDTO> result = service.findAll(pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getSize());
        Assertions.assertEquals(product.getName(), result.iterator().next().getName());
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {

        ProductDTO result = service.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());
        Assertions.assertEquals(product.getName(), result.getName());
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });
    }

    @Test
    public void insertShouldReturnProductDTO() {
        ProductDTO result = service.insert(productSaveDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());
        Assertions.assertEquals(productSaveDTO.getName(), result.getName());
    }

    @Test
    public void insertShouldThrowResourceNotFoundExceptionWhenCategoryIdDoesNotExist() {
        productSaveDTO.setCategoryId(nonExistingCategoryId);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.insert(productSaveDTO));
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() {
        ProductDTO result = service.update(existingId, productSaveDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());
        Assertions.assertEquals(productSaveDTO.getName(), result.getName());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.update(nonExistingId, productSaveDTO));
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenCategoryIdDoesNotExist() {
        productSaveDTO.setCategoryId(nonExistingCategoryId);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.update(existingId, productSaveDTO));
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> service.delete(existingId));
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.delete(nonExistingId));
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenIntegrityViolated() {
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(existingId);

        Assertions.assertThrows(DatabaseException.class,
                () -> service.delete(existingId));
    }
}
