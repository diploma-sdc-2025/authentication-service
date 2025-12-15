INSERT INTO user_session (user_id, refresh_token_id, device_info, started_at, last_activity_at, ended_at)
VALUES
    (1, 1, 'Alice iPhone', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '1 hour', NULL),
    (2, 2, 'Bob MacBook', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '2 hours', NULL),
    (3, 3, 'Charlie Android', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '3 hours', NULL),
    (4, 4, 'David iPad',  CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '4 hours', NULL),
    (5, 5, 'Eve Laptop', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '5 hours', NULL),
    (6, 6, 'Frank Desktop',  CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '6 hours', NULL),
    (7, 7, 'Grace Tablet', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '7 hours', NULL),
    (8, 8, 'Heidi Phone',  CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '8 hours', NULL),
    (9, 9, 'Ivan Laptop', CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '9 hours', NULL),
    (10, 10, 'Judy iPhone',  CURRENT_TIMESTAMP - INTERVAL '1 day', CURRENT_TIMESTAMP - INTERVAL '10 hours', NULL);