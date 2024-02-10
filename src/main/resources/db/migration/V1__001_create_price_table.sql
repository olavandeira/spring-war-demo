CREATE TABLE price
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    product_id     BIGINT,
    brand_id       INT,
    date_start     DATETIME,
    date_end       DATETIME,
    priority       INT,
    price          DECIMAL(10, 2),
    price_currency VARCHAR(10),
    PRIMARY KEY (id)
);

INSERT INTO price (product_id, brand_id, date_start, date_end, priority, price, price_currency)
VALUES (35455, 1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 0, 35.50, 'EUR'),
       (35455, 1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 1, 25.45, 'EUR'),
       (35455, 1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 1, 30.50, 'EUR'),
       (35455, 1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 1, 38.95, 'EUR');

