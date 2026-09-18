package ru.kutkovmax.yacos.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String sku;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 12, scale =2 )
    private BigDecimal price;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected Item() {}

    public Item(String sku, String name, Integer quantity, BigDecimal price){
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = Instant.now();
    }

    public Long getId() {return id;}
    public String getSku() {return sku;}
    public String getName() {return name;}
    public Integer getQuantity(){return quantity;}
    public BigDecimal getPrice(){return price;}
    public Instant getCreatedAt(){return createdAt;}

    public void updateDetails(String name, Integer quantity, BigDecimal price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}
