INSERT INTO user_session (user_id, refresh_token_id, device_info, user_agent, started_at, last_activity_at, ended_at)
VALUES
    (1, 1, 'Alice iPhone', 'Mozilla/5.0 (iPhone)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '1 hour', NULL),
    (2, 2, 'Bob MacBook', 'Mozilla/5.0 (Macintosh)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (3, 3, 'Charlie Android', 'Mozilla/5.0 (Linux; Android)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '3 hours', NULL),
    (4, 4, 'David iPad', 'Mozilla/5.0 (iPad)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '4 hours', NULL),
    (5, 5, 'Eve Laptop', 'Mozilla/5.0 (Windows NT 10.0)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '5 hours', NULL),
    (6, 6, 'Frank Desktop', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '6 hours', NULL),
    (7, 7, 'Grace Tablet', 'Mozilla/5.0 (iPad; CPU OS 14_0)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '7 hours', NULL),
    (8, 8, 'Heidi Phone', 'Mozilla/5.0 (Android 12)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '8 hours', NULL),
    (9, 9, 'Ivan Laptop', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '9 hours', NULL),
    (10, 10, 'Judy iPhone', 'Mozilla/5.0 (iPhone)', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '10 hours', NULL);