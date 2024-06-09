CREATE TABLE shares_users (
        id MEDIUMINT NOT NULL AUTO_INCREMENT,
        user_id MEDIUMINT NOT NULL,
        share_id MEDIUMINT NOT NULL,
        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        expire_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

        FOREIGN KEY (user_id) REFERENCES users(id),
        PRIMARY KEY (id)
);

INSERT INTO shares_users (user_id, share_id, date, expire_date) VALUES
    (1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY))