CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       is_active BOOLEAN NOT NULL DEFAULT TRUE,
                       is_verified BOOLEAN NOT NULL DEFAULT FALSE,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       last_login_at TIMESTAMP,

                       CONSTRAINT uq_users_username UNIQUE (username),
                       CONSTRAINT uq_users_email UNIQUE (email),
                       CONSTRAINT chk_users_username_length CHECK (LENGTH(TRIM(username)) >= 3),
                       CONSTRAINT chk_users_username_format CHECK (username ~ '^[a-zA-Z0-9_]+$'),
                       CONSTRAINT chk_users_email_format CHECK
                            (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
                       CONSTRAINT chk_users_password_hash_length CHECK
                            (LENGTH(password_hash) >= 60)
);

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_is_active ON users(is_active) WHERE is_active = TRUE;
CREATE INDEX idx_users_created_at ON users(created_at DESC);
CREATE INDEX idx_users_last_login ON users(last_login_at DESC NULLS LAST);