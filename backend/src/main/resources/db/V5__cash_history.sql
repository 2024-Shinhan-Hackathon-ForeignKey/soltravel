ALTER TABLE cash_history
    MODIFY COLUMN cash_history_id BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE account_book
    MODIFY COLUMN account_book_id BIGINT NOT NULL AUTO_INCREMENT;
