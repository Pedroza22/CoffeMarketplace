CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY,
    status VARCHAR(32) NOT NULL,
    customer_id UUID NOT NULL,
    total_amount NUMERIC(19,2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);