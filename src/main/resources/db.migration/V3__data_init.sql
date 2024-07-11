-- USERS
INSERT INTO users (id, created_at, modified_at, first_name, last_name, patronymic, phone_number, email, birth_date, password)
VALUES
    ('2892dcd4-17ff-454b-b67c-f7efa85bd600','2024-07-11 15:54:00', '2024-07-11 15:54:00', 'Aleksey', 'Shumkov', 'Sergeevich', '+79061982546', 'shumleshas@gmail.com', '2005-01-21', '$2a$10$4sf.7lUwA6z5sPxoWSt..u0Y.nX766aks1SOLGbms8nyaa.T0sOEu'),
    ('18a86bb2-34b2-4fd1-beee-b8fb1e23f5f7','2024-07-11 15:54:00', '2024-07-11 15:54:00', 'Random', 'Randomov', 'Randomovich', '+77777777777', 'randomov@random.com', '1965-04-15', '$2a$10$4sf.7lUwA6z5sPxoWSt..u0Y.nX766aks1SOLGbms8nyaa.T0sOEu');


-- WALLETS
INSERT INTO wallets (id, created_at, modified_at, owner_id, balance)
VALUES
    ('547055b2-75c3-4c65-a5fd-62774d0e10b5', '2024-07-11 15:54:50', '2024-07-11 15:54:50', '2892dcd4-17ff-454b-b67c-f7efa85bd600', 100),
    ('7452f2dd-a66f-4465-bb47-134eb2c63b28', '2024-07-11 15:54:50', '2024-07-11 15:54:50', '18a86bb2-34b2-4fd1-beee-b8fb1e23f5f7', 100);

