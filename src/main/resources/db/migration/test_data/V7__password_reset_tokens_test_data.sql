INSERT INTO password_reset_tokens (user_id, token_hash, expires_at, created_at, used_at)
VALUES
    (1, 'pwreset_alice_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (2, 'pwreset_bob_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (3, 'pwreset_charlie_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (4, 'pwreset_david_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (5, 'pwreset_eve_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (6, 'pwreset_frank_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (7, 'pwreset_grace_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (8, 'pwreset_heidi_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (9, 'pwreset_ivan_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (10, 'pwreset_judy_1', CURRENT_TIMESTAMP + INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL);
