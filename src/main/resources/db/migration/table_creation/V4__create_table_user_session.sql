CREATE TABLE user_session (
                               id SERIAL PRIMARY KEY,
                               user_id INT NOT NULL,
                               refresh_token_id INT,
                               device_info VARCHAR(255),
                               started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               last_activity_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               ended_at TIMESTAMP,

                               CONSTRAINT fk_user_sessions_user_id
                                   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                               CONSTRAINT fk_user_sessions_refresh_token_id
                                   FOREIGN KEY (refresh_token_id) REFERENCES refresh_tokens(id) ON DELETE SET NULL
);

CREATE INDEX idx_user_sessions_user_id ON user_session(user_id);
CREATE INDEX idx_user_sessions_refresh_token_id ON user_session(refresh_token_id);

