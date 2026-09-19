package ru.kutkovmax.yacos.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.kutkovmax.yacos.dto.CreateItemRequest;
import ru.kutkovmax.yacos.dto.ItemResponse;
import ru.kutkovmax.yacos.dto.UpdateItemRequest;
import ru.kutkovmax.yacos.service.ItemService;

import java.net.URI;

@Tag(name = "Items", description = "Управление складскими позициями")
@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service){
        this.service = service;
    }

    @Operation(summary = "Получить список позиций с пагинацией")
    @ApiResponse(responseCode = "200", description = "Список позиций получен")
    @GetMapping
    public ResponseEntity<Page<ItemResponse>> getAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @Operation(summary = "Получить позицию по идентификатору")
    @ApiResponse(responseCode = "200", description = "Позиция найдена")
    @ApiResponse(responseCode = "404", description = "Позиция не найдена")
    @GetMapping("/{id}")
    public ItemResponse getById(@PathVariable Long id){
        return service.getById(id);
    }

    @Operation(summary = "Создать новую позицию")
    @ApiResponse(responseCode = "201", description = "Позиция успешно создана")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    @ApiResponse(responseCode = "409", description = "Позиция с таким SKU уже существует")
    @PostMapping
    public ResponseEntity<ItemResponse> create(
            @Valid @RequestBody CreateItemRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        ItemResponse created = service.create(request);
        URI location = uriBuilder.path("/api/v1/items/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Обновить существующую позицию")
    @ApiResponse(responseCode = "200", description = "Позиция обновлена")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    @ApiResponse(responseCode = "404", description = "Позиция не найдена")
    @PutMapping("/{id}")
    public ItemResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemRequest request
            ) {
        return service.update(id, request);
    }

    @Operation(summary = "Удалить позицию")
    @ApiResponse(responseCode = "204", description = "Позиция удалена")
    @ApiResponse(responseCode = "404", description = "Позиция не найдена")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
