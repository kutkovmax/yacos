package ru.kutkovmax.yacos.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.kutkovmax.yacos.dto.CreateItemRequest;
import ru.kutkovmax.yacos.dto.ItemResponse;
import ru.kutkovmax.yacos.repository.ItemRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class ItemControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

    @LocalServerPort
    private int port;

    @Autowired
    private ItemRepository repository;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        restClient = RestClient.builder()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void shouldCreateItemAndPersistInDatabase() {
        var request = new CreateItemRequest(
                "TEST-SKU-1",
                "Умная мышка",
                5,
                new BigDecimal("49.99")
        );

        ResponseEntity<ItemResponse> response = restClient.post()
                .uri("/api/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .toEntity(ItemResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().getLocation()).isNotNull();

        ItemResponse body = response.getBody();
        assertThat(body).isNotNull();
        assertThat(body.id()).isNotNull();
        assertThat(body.sku()).isEqualTo("TEST-SKU-1");
        assertThat(body.name()).isEqualTo("Умная мышка");

        assertThat(repository.existsBySku("TEST-SKU-1")).isTrue();
    }
}