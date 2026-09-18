package ru.kutkovmax.yacos.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kutkovmax.yacos.dto.CreateItemRequest;
import ru.kutkovmax.yacos.dto.ItemResponse;
import ru.kutkovmax.yacos.dto.UpdateItemRequest;
import ru.kutkovmax.yacos.model.Item;
import ru.kutkovmax.yacos.repository.ItemRepository;

@Service
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository){
        this.repository = repository;
    }

    public Page<ItemResponse> getAll(Pageable pageable){
        return repository.findAll(pageable).map(this::toResponse);
    }

    public ItemResponse getById(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Item with id " + id + " not found"));
    }

    @Transactional
    public ItemResponse create(CreateItemRequest req) {
        String cleanSku = req.sku().trim().toUpperCase();
        if (repository.existsBySku(cleanSku)) {
            throw new IllegalArgumentException("SKU already exists: " + cleanSku);
        }

        Item item = new Item(
                cleanSku,
                req.name().trim(),
                req.quantity(),
                req.price()
        );

        return toResponse(repository.save(item));
    }

    @Transactional
    public ItemResponse update(Long id, UpdateItemRequest req) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item with id " + id + " not found"));

        item.updateDetails(
                req.name().trim(),
                req.quantity(),
                req.price()
        );

        return toResponse(item);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Item with id " + id + " not found");
        }
        repository.deleteById(id);
    }



    private ItemResponse toResponse(Item item){
        return new ItemResponse(
                item.getId(),
                item.getSku(),
                item.getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getCreatedAt()
        );
    }
}
