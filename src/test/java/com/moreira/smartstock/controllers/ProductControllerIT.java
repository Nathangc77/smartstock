package com.moreira.smartstock.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moreira.smartstock.dtos.ProductSaveDTO;
import com.moreira.smartstock.tests.utils.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminUsername, adminPassword;
    private String operatorUsername, operatorPassword;
    private String adminToken, operatorToken, invalidToken;
    private ProductSaveDTO productSaveDTO;
    private Long productId, nonExistingProductId, dependentProductId;

    @BeforeEach
    void setUp() throws Exception {
        adminUsername = "maria@gmail.com";
        adminPassword = "123456";
        operatorUsername = "alex@gmail.com";
        operatorPassword = "123456";

        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        operatorToken = tokenUtil.obtainAccessToken(mockMvc, operatorUsername, operatorPassword);
        invalidToken = adminToken + "xpto";

        productSaveDTO = new ProductSaveDTO("Trincha Pincel 1.1/2 Pol 38mm", 12.05, "UN", 1L);

        productId = 10L;
        nonExistingProductId = 9999L;
        dependentProductId = 2L;
    }

    @Test
    public void findAllShouldReturnPagedProductsWhenAdminLogged() throws Exception {
        mockMvc.perform(get("/products")
                .header("Authorization", "Bearer " + adminToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("Parafuso Phillips C/bucha 8 Anel 8mm"));
    }

    @Test
    public void findAllShouldReturnPagedProductsWhenOperatorLogged() throws Exception {
        mockMvc.perform(get("/products")
                        .header("Authorization", "Bearer " + operatorToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].name").value("Parafuso Phillips C/bucha 8 Anel 8mm"));
    }

    @Test
    public void findAllShouldThrowsUnauthorizedWhenInvalidToken() throws Exception {
        mockMvc.perform(get("/products")
                        .header("Authorization", "Bearer " + invalidToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenAdminLogged() throws Exception {
        mockMvc.perform(get("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Tomada 10A Branca"));
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenOperatorLogged() throws Exception {
        mockMvc.perform(get("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + operatorToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Tomada 10A Branca"));
    }

    @Test
    public void findByIdShouldThrowsUnauthorizedWhenInvalidToken() throws Exception {
        mockMvc.perform(get("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + invalidToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void findByIdShouldThrowsResourceNotFoundExceptionWhenProductIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/products/{productId}", nonExistingProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void insertShouldReturnProductDTOWhenAdminLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(14L))
                .andExpect(jsonPath("$.name").value("Trincha Pincel 1.1/2 Pol 38mm"));
    }

    @Test
    public void insertShouldThrowsForbiddenWhenOperatorLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + operatorToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void insertShouldThrowsUnauthorizedWhenInvalidToken() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenInvalidName() throws Exception {
        productSaveDTO.setName("ab");
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenInvalidPrice() throws Exception {
        productSaveDTO.setPrice(0.0);
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenInvalidUnitMeasure() throws Exception {
        productSaveDTO.setUnitMeasure("CMT");
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowsUnprocessableEntityWhenCategoryIdIsNull() throws Exception {
        productSaveDTO.setCategoryId(null);
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void insertShouldThrowsResourceNotFoundExceptionWhenInvalidCategoryId() throws Exception {
        productSaveDTO.setCategoryId(9999L);
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(post("/products")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnProductDTOWhenAdminLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(put("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Trincha Pincel 1.1/2 Pol 38mm"));
    }

    @Test
    public void updateShouldThrowsForbiddenWhenOperatorLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(put("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + operatorToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateShouldThrowsUnauthorizedWhenInvalidToken() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(put("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void updateShouldThrowsUnprocessableEntityWhenInvalidName() throws Exception {
        productSaveDTO.setName("ab");
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(put("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateShouldThrowsUnprocessableEntityWhenInvalidPrice() throws Exception {
        productSaveDTO.setPrice(0.0);
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(put("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateShouldThrowsUnprocessableEntityWhenInvalidUnitMeasure() throws Exception {
        productSaveDTO.setUnitMeasure("CMT");
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(put("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateShouldThrowsUnprocessableEntityWhenCategoryIdIsNull() throws Exception {
        productSaveDTO.setCategoryId(null);
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(put("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateShouldThrowsResourceNotFoundExceptionWhenInvalidProductId() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(put("/products/{productId}", nonExistingProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldThrowsResourceNotFoundExceptionWhenCategoryIdDoesNotExist() throws Exception {
        productSaveDTO.setCategoryId(9999L);
        String jsonBody = objectMapper.writeValueAsString(productSaveDTO);

        mockMvc.perform(put("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenAdminLogged() throws Exception {
        mockMvc.perform(delete("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteShouldThrowsForbiddenWhenOperatorLogged() throws Exception {
        mockMvc.perform(delete("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + operatorToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteShouldThrowsUnauthorizedWhenInvalidToken() throws Exception {
        mockMvc.perform(delete("/products/{productId}", productId)
                        .header("Authorization", "Bearer " + invalidToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteShouldThrowsResourceNotFoundExceptionWhenProductIdDoesNotExist() throws Exception {
        mockMvc.perform(delete("/products/{productId}", nonExistingProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteShouldThrowsDatabaseExceptionWhenProductIdIsDependent() throws Exception {
        mockMvc.perform(delete("/products/{productId}", dependentProductId)
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
