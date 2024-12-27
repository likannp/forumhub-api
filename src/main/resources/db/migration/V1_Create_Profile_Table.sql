CREATE TABLE profiles (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
