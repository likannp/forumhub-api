CREATE TABLE answers (
    id SERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    solution BOOLEAN DEFAULT FALSE,
    topic_id BIGINT NOT NULL,
    author_id BIGINT,
    CONSTRAINT fk_topic FOREIGN KEY (topic_id) REFERENCES topics (id) ON DELETE CASCADE,
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE SET NULL
);
