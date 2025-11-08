CREATE TABLE IF NOT EXISTS order_read_model (
    id UUID PRIMARY KEY,
    status VARCHAR(64) NOT NULL,
    customer_id UUID NOT NULL,
    total_amount NUMERIC(19,2) NOT NULL,
    updated_at TIMESTAMP NOT NULL
);