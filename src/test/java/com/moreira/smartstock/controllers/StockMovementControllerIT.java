package com.moreira.smartstock.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moreira.smartstock.dtos.EntryMovementDTO;
import com.moreira.smartstock.dtos.ExitMovementDTO;
import com.moreira.smartstock.tests.utils.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StockMovementControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminUsername, adminPassword;
    private String operatorUsername, operatorPassword;
    private String adminToken, operatorToken, invalidToken;
    private EntryMovementDTO entryMovementDTO;
    private ExitMovementDTO exitMovementDTO;
    private Long nonExistingProductId;
    private Long nonExistingProviderId;
    private Long nonExistingClientId;

    @BeforeEach
    void setUp() throws Exception {
        adminUsername = "maria@gmail.com";
        adminPassword = "123456";
        operatorUsername = "alex@gmail.com";
        operatorPassword = "123456";

        adminToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
        operatorToken = tokenUtil.obtainAccessToken(mockMvc, operatorUsername, operatorPassword);
        invalidToken = adminToken + "xpto";

        entryMovementDTO = new EntryMovementDTO(100, "Entrada de estoque", 1L, 2L);
        exitMovementDTO = new ExitMovementDTO(100, "Venda de produto", 1L, 2L);

        nonExistingProductId = 9999L;
        nonExistingProviderId = 9999L;
        nonExistingClientId = 9999L;
    }

    @Test
    public void findAllShouldReturnPagedStockMovementsWhenAdminLogged() throws Exception {
        mockMvc.perform(get("/movements")
                        .header("Authorization", "Bearer " + adminToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].id").isNumber())
                .andExpect(jsonPath("$.content[0].quantity").isNumber())
                .andExpect(jsonPath("$.content[0].type").isString())
                .andExpect(jsonPath("$.content[0].product.id").isNumber());
    }

    @Test
    public void registerStockEntryShouldReturnStockMovementDTOWhenAdminLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(entryMovementDTO);
        mockMvc.perform(post("/movements/entry")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").exists())
                    .andExpect(jsonPath("$.quantity").value(entryMovementDTO.getQuantity()))
                    .andExpect(jsonPath("$.type").value("ENTRADA"))
                    .andExpect(jsonPath("$.product.id").value(entryMovementDTO.getProductId()))
                    .andExpect(jsonPath("$.provider.id").value(entryMovementDTO.getProviderId()));
    }

    @Test
    public void registerStockEntryShouldReturnStockMovementDTOWhenOperatorLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(entryMovementDTO);
        mockMvc.perform(post("/movements/entry")
                        .header("Authorization", "Bearer " + operatorToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.quantity").value(entryMovementDTO.getQuantity()))
                .andExpect(jsonPath("$.type").value("ENTRADA"))
                .andExpect(jsonPath("$.product.id").value(entryMovementDTO.getProductId()))
                .andExpect(jsonPath("$.provider.id").value(entryMovementDTO.getProviderId()));
    }

    @Test
    public void registerStockEntryShouldThrowsUnauthorizedWhenInvalidToken() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(entryMovementDTO);
        mockMvc.perform(post("/movements/entry")
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void registerStockEntryShouldThrowsResourceNotFoundExceptionWhenProductIdDoesNotExist() throws Exception {
        entryMovementDTO.setProductId(nonExistingProductId);
        String jsonBody = objectMapper.writeValueAsString(entryMovementDTO);
        mockMvc.perform(post("/movements/entry")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void registerStockEntryShouldThrowsResourceNotFoundExceptionWhenProviderIdDoesNotExist() throws Exception {
        entryMovementDTO.setProviderId(nonExistingProviderId);
        String jsonBody = objectMapper.writeValueAsString(entryMovementDTO);
        mockMvc.perform(post("/movements/entry")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void registerStockEntryShouldThrowsUnprocessableEntityWhenInvalidQuantity() throws Exception {
        entryMovementDTO.setQuantity(0);
        String jsonBody = objectMapper.writeValueAsString(entryMovementDTO);
        mockMvc.perform(post("/movements/entry")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void registerStockEntryShouldThrowsUnprocessableEntityWhenInvalidReason() throws Exception {
        entryMovementDTO.setReason("ab");
        String jsonBody = objectMapper.writeValueAsString(entryMovementDTO);
        mockMvc.perform(post("/movements/entry")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void registerStockEntryShouldThrowsUnprocessableEntityWhenInvalidProductId() throws Exception {
        entryMovementDTO.setProductId(null);
        String jsonBody = objectMapper.writeValueAsString(entryMovementDTO);
        mockMvc.perform(post("/movements/entry")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void registerStockEntryShouldThrowsUnprocessableEntityWhenInvalidProviderId() throws Exception {
        entryMovementDTO.setProviderId(null);
        String jsonBody = objectMapper.writeValueAsString(entryMovementDTO);
        mockMvc.perform(post("/movements/entry")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void registerStockExitShouldReturnStockMovementDTOWhenAdminLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.quantity").value(exitMovementDTO.getQuantity()))
                .andExpect(jsonPath("$.type").value("SAIDA"))
                .andExpect(jsonPath("$.product.id").value(exitMovementDTO.getProductId()))
                .andExpect(jsonPath("$.client.id").value(exitMovementDTO.getClientId()));
    }

    @Test
    public void registerStockExitShouldReturnStockMovementDTOWhenOperatorLogged() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + operatorToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.quantity").value(exitMovementDTO.getQuantity()))
                .andExpect(jsonPath("$.type").value("SAIDA"))
                .andExpect(jsonPath("$.product.id").value(exitMovementDTO.getProductId()))
                .andExpect(jsonPath("$.client.id").value(exitMovementDTO.getClientId()));
    }

    @Test
    public void registerStockExitShouldThrowsUnauthorizedWhenInvalidToken() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void registerStockExitShouldThrowsResourceNotFoundExceptionWhenProductIdDoesNotExist() throws Exception {
        exitMovementDTO.setProductId(nonExistingProductId);
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void registerStockExitShouldThrowsResourceNotFoundExceptionWhenClientIdDoesNotExist() throws Exception {
        exitMovementDTO.setClientId(nonExistingClientId);
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void registerStockExitShouldThrowsInsufficientStockExceptionWhenQuantityIsGreaterThanStock() throws Exception {
        exitMovementDTO.setQuantity(9999);
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void registerStockExitShouldThrowsUnprocessableEntityWhenInvalidQuantity() throws Exception {
        exitMovementDTO.setQuantity(0);
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void registerStockExitShouldThrowsUnprocessableEntityWhenInvalidReason() throws Exception {
        exitMovementDTO.setReason("ab");
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void registerStockExitShouldThrowsUnprocessableEntityWhenInvalidProductId() throws Exception {
        exitMovementDTO.setProductId(null);
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void registerStockExitShouldThrowsUnprocessableEntityWhenInvalidClientId() throws Exception {
        exitMovementDTO.setClientId(null);
        String jsonBody = objectMapper.writeValueAsString(exitMovementDTO);
        mockMvc.perform(post("/movements/exit")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }
}
