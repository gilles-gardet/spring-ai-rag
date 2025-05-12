-- Insert initial persons
INSERT INTO person (first_name, last_name, birth_date, email, phone)
VALUES
    ('John', 'Doe', '1980-05-15', 'john.doe@example.com', '+33612345678'),
    ('Jane', 'Smith', '1985-08-22', 'jane.smith@example.com', '+33623456789'),
    ('Michael', 'Johnson', '1975-11-30', 'michael.johnson@example.com', '+33634567890'),
    ('Emily', 'Williams', '1990-02-10', 'emily.williams@example.com', '+33645678901'),
    ('David', 'Brown', '1982-07-18', 'david.brown@example.com', '+33656789012'),
    ('Sarah', 'Miller', '1988-04-25', 'sarah.miller@example.com', '+33667890123');

-- Insert addresses
INSERT INTO address (person_id, street, city, state, postal_code, country, is_primary)
VALUES
    (1, '123 Main St', 'Paris', 'Île-de-France', '75001', 'France', TRUE),
    (2, '456 Oak Ave', 'Lyon', 'Auvergne-Rhône-Alpes', '69001', 'France', TRUE),
    (3, '789 Pine Blvd', 'Marseille', 'Provence-Alpes-Côte d''Azur', '13001', 'France', TRUE),
    (4, '101 Elm St', 'Bordeaux', 'Nouvelle-Aquitaine', '33000', 'France', TRUE),
    (5, '202 Cedar Ln', 'Lille', 'Hauts-de-France', '59000', 'France', TRUE),
    (6, '303 Maple Dr', 'Toulouse', 'Occitanie', '31000', 'France', TRUE);
