CREATE TABLE user_profile (
    user_id BIGINT,
    profile_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id),
    PRIMARY KEY (user_id, profile_id)
);
