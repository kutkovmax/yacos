CREATE TABLE items (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(64) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    price NUMERIC(12, 2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_items_quantity_non_negative CHECK (quantity >= 0),
    CONSTRAINT chk_items_price_positive CHECK (price > 0)
);

CREATE INDEX idx_items_sku ON items (sku);