CREATE TABLE currencies
(
    id        INTEGER PRIMARY KEY AUTOINCREMENT,
    code      TEXT,
    full_name TEXT,
    sign      TEXT
);

CREATE UNIQUE INDEX currency_code_index
    ON currencies (code);

CREATE TABLE exchange_rates
(
    id                 INTEGER PRIMARY KEY AUTOINCREMENT,
    base_currency_id   INTEGER,
    target_currency_id INTEGER,
    rate               DECIMAL(6),

    FOREIGN KEY (base_currency_id) REFERENCES currencies (id),
    FOREIGN KEY (target_currency_id) REFERENCES currencies (id)
);

CREATE UNIQUE INDEX exchange_rates_index
    ON exchange_rates (base_currency_id, target_currency_id);

INSERT INTO currencies (code, full_name, sign)
VALUES ('JPY', 'Japan Yen', '¥'),
       ('MNT', 'Mongolia Tughrik	', '₮'),
       ('CZK', 'Czech Republic Koruna', 'Kč'),
       ('RUB', 'Russia Ruble', '₽'),
       ('CHF', 'Switzerland Franc', 'CHF'),
       ('THB', 'Thailand Baht', '฿'),
       ('GBP', 'United Kingdom Pound', '£'),
       ('USD', 'United States Dollar', '$'),
       ('EUR', 'Euro', '€'),
       (' ', ' ', ' ');

INSERT INTO exchange_rates (base_currency_id, target_currency_id, rate)
VALUES (8, 9, 1.080800),
       (9, 1, 162.412500),
       (null, null, null),
       (3, 8, 0.042700);