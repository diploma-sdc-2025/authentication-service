INSERT INTO users (username, email, password_hash, is_active, is_verified, created_at, updated_at, last_login_at)
VALUES
    ('alice123', 'alice@example.com', repeat('a',60), TRUE, TRUE, CURRENT_TIMESTAMP - INTERVAL '10 days', CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '1 day'),
    ('bob456', 'bob@example.com', repeat('b',60), TRUE, TRUE, CURRENT_TIMESTAMP - INTERVAL '9 days', CURRENT_TIMESTAMP - INTERVAL '4 days', CURRENT_TIMESTAMP - INTERVAL '2 days'),
    ('charlie789', 'charlie@example.com', repeat('c',60), TRUE, FALSE, CURRENT_TIMESTAMP - INTERVAL '8 days', CURRENT_TIMESTAMP - INTERVAL '3 days', NULL),
    ('david321', 'david@example.com', repeat('d',60), TRUE, TRUE, CURRENT_TIMESTAMP - INTERVAL '7 days', CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP - INTERVAL '3 hours'),
    ('eve654', 'eve@example.com', repeat('e',60), TRUE, FALSE, CURRENT_TIMESTAMP - INTERVAL '6 days', CURRENT_TIMESTAMP - INTERVAL '1 day', NULL),
    ('frank987', 'frank@example.com', repeat('f',60), TRUE, TRUE, CURRENT_TIMESTAMP - INTERVAL '5 days', CURRENT_TIMESTAMP - INTERVAL '12 hours', CURRENT_TIMESTAMP - INTERVAL '6 hours'),
    ('grace111', 'grace@example.com', repeat('g',60), TRUE, TRUE, CURRENT_TIMESTAMP - INTERVAL '4 days', CURRENT_TIMESTAMP - INTERVAL '10 hours', CURRENT_TIMESTAMP - INTERVAL '2 hours'),
    ('heidi222', 'heidi@example.com', repeat('h',60), TRUE, FALSE, CURRENT_TIMESTAMP - INTERVAL '3 days', CURRENT_TIMESTAMP - INTERVAL '6 hours', NULL),
    ('ivan333', 'ivan@example.com', repeat('i',60), TRUE, TRUE, CURRENT_TIMESTAMP - INTERVAL '2 days', CURRENT_TIMESTAMP - INTERVAL '4 hours', CURRENT_TIMESTAMP - INTERVAL '1 hour'),
    ('judy444', 'judy@example.com', repeat('j',60), TRUE, FALSE, CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL);

