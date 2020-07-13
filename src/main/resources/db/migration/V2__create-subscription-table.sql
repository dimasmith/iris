CREATE TABLE subscription
(
    id          BIGINT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    url         VARCHAR(255),
    description VARCHAR(255),
    rate_currency VARCHAR(3),
    rate_amount DECIMAL(10, 2)
);
