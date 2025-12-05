INSERT INTO refresh_tokens (user_id, token_hash, expires_at, created_at, device_info)
VALUES
    (1, 'token_alice_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Alice iPhone'),
    (2, 'token_bob_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Bob MacBook'),
    (3, 'token_charlie_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Charlie Android'),
    (4, 'token_david_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'David iPad'),
    (5, 'token_eve_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Eve Laptop'),
    (6, 'token_frank_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Frank Desktop'),
    (7, 'token_grace_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Grace Tablet'),
    (8, 'token_heidi_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Heidi Phone'),
    (9, 'token_ivan_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Ivan Laptop'),
    (10, 'token_judy_1', CURRENT_TIMESTAMP + INTERVAL '30 days', CURRENT_TIMESTAMP - INTERVAL '1 day', 'Judy iPhone');
