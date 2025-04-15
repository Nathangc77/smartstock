package com.moreira.smartstock.services;

import com.moreira.smartstock.dtos.EntryMovementDTO;
import com.moreira.smartstock.dtos.ExitMovementDTO;
import com.moreira.smartstock.dtos.StockMovementDTO;
import com.moreira.smartstock.entities.*;
import com.moreira.smartstock.repositories.ClientRepository;
import com.moreira.smartstock.repositories.ProductRepository;
import com.moreira.smartstock.repositories.ProviderRepository;
import com.moreira.smartstock.repositories.StockMovementRepository;
import com.moreira.smartstock.services.exceptions.ResourceNotFoundException;
import com.moreira.smartstock.tests.factories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class StockMovementServiceTests {

    @InjectMocks
    private StockMovementService service;

    @Mock
    private StockMovementRepository repository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserService userService;

    private StockMovement entryStockMovement, exitStockMovement;
    private PageImpl<StockMovement> page;
    private Long existingProductId, nonExistingProductId;
    private Long existingProviderId, nonExistingProviderId;
    private Long existingClientId, nonExistingClientId;
    private Long existingStockMovementId;
    private EntryMovementDTO entryMovementDTO;
    private ExitMovementDTO exitMovementDTO;
    private Product product;
    private Provider provider;
    private Client client;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        existingStockMovementId = 1L;
        existingProductId = 1L;
        nonExistingProductId = 2L;
        existingProviderId = 1L;
        nonExistingProviderId = 2L;
        existingClientId = 1L;
        nonExistingClientId = 2L;

        entryMovementDTO = StockMovementFactory.createEntryMovement(existingProductId, existingProviderId);
        exitMovementDTO = StockMovementFactory.createExitMovement(existingProductId, existingClientId);
        product = ProductFactory.createProduct(existingProductId);
        provider = ProviderFactory.createProvider(existingProviderId);
        client = ClientFactory.createCustomClient(existingClientId, "João Silva");
        user = UserFactory.createCustomUser("maria@gmail.com");
        entryStockMovement = StockMovementFactory.createEntryStockMovement(existingStockMovementId, existingProductId, existingProviderId, user.getUsername());
        exitStockMovement = StockMovementFactory.createExitStockMovement(2L, existingProductId, existingClientId, user.getUsername());

        page = new PageImpl<>(List.of(entryStockMovement));

        Mockito.when(repository.searchStockMovement(any())).thenReturn(page);

        Mockito.when(productRepository.findById(existingProductId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistingProductId)).thenReturn(Optional.empty());

        Mockito.when(providerRepository.findById(existingProviderId)).thenReturn(Optional.of(provider));
        Mockito.when(providerRepository.findById(nonExistingProviderId)).thenReturn(Optional.empty());

        Mockito.when(clientRepository.findById(existingClientId)).thenReturn(Optional.of(client));
        Mockito.when(clientRepository.findById(nonExistingProviderId)).thenReturn(Optional.empty());

        Mockito.when(userService.getUserLogged()).thenReturn(user);

        Mockito.when(repository.save(any())).thenReturn(entryStockMovement);
        Mockito.when(productRepository.save(any())).thenReturn(product);
    }

    @Test
    public void findAllShouldReturnPagedStockMovements() {
        Pageable pageable = PageRequest.of(0, 8);

        Page<StockMovementDTO> result = service.findAll(pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(entryStockMovement.getId(), result.iterator().next().getId());
    }

    @Test
    public void registerStockEntryShouldReturnStockMovementDTOWhenCorrectData() {
        StockMovementDTO result = service.registerStockEntry(entryMovementDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(existingProductId, result.getProduct().id());
        Assertions.assertEquals(existingProviderId, result.getProvider().id());
        Assertions.assertEquals(TypeMovement.ENTRADA.name(), result.getType());
    }

    @Test
    public void registerStockEntryShouldThrowsResourceNotFoundExceptionWhenProductIdDoesNotExist() {
        entryMovementDTO.setProductId(nonExistingProductId);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.registerStockEntry(entryMovementDTO));
    }

    @Test
    public void registerStockEntryShouldThrowsResourceNotFoundExceptionWhenProviderIdDoesNotExist() {
        entryMovementDTO.setProviderId(nonExistingProviderId);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.registerStockEntry(entryMovementDTO));
    }

    @Test
    public void registerStockExitShouldReturnStockMovementDTOWhenCorrectData() {
        Mockito.when(repository.save(any())).thenReturn(exitStockMovement);

        StockMovementDTO result = service.registerStockExit(exitMovementDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2L, result.getId());
        Assertions.assertEquals(existingProductId, result.getProduct().id());
        Assertions.assertEquals(existingClientId, result.getClient().id());
        Assertions.assertEquals(TypeMovement.SAIDA.name(), result.getType());
    }

    @Test
    public void registerStockExitShouldThrowsResourceNotFoundExceptionWhenProductIdDoesNotExist() {
        exitMovementDTO.setProductId(nonExistingProductId);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.registerStockExit(exitMovementDTO));
    }

    @Test
    public void registerStockExitShouldThrowsResourceNotFoundExceptionWhenClientIdDoesNotExist() {
        exitMovementDTO.setClientId(nonExistingClientId);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.registerStockExit(exitMovementDTO));
    }

    @Test
    public void registerStockExitShouldThrowsRuntimeExceptionWhenInvalidQuantity() {
        exitMovementDTO.setQuantity(150);

        Assertions.assertThrows(RuntimeException.class,
                () -> service.registerStockExit(exitMovementDTO));
    }
}
