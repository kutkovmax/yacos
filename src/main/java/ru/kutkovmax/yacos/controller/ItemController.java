package ru.kutkovmax.yacos.controller;


import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service){
        this.service = service;
    }

    @GetMapping
    public Page<ItemResponse> getAll(@PageableDefault(size = 20) Pageable pageable){
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ItemResponse getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(
            @Valid @RequestBody CreateItemRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        ItemResponse created = service.create(request);
        URI location = uriBuilder.path("/api/v1/items/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ItemResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateItemRequest request
            ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

}
