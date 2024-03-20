CREATE DATABASE IF NOT EXISTS acme;
USE acme;


CREATE TABLE  Utilisateur (
    id_utilisateur INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255),
    prenom VARCHAR(255),
    ville VARCHAR(60),
    code_postal VARCHAR(10),
    numero_rue INT,
    libelle_rue VARCHAR(255),
    email VARCHAR(255),
    telephone VARCHAR(10),
    archive BOOLEAN
);

-- Table de l'employé
CREATE TABLE Employe (
    id_utilisateur INT PRIMARY KEY AUTO_INCREMENT,
	mot_de_passe VARCHAR(255),
    matricule VARCHAR(6),
    date_embauche DATE,
    role VARCHAR(15),
    FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);

-- Table du client
CREATE TABLE Client (
    id_utilisateur INT PRIMARY KEY AUTO_INCREMENT,
    date_affiliation DATE,
    preferencecom VARCHAR(50),
    FOREIGN KEY (id_utilisateur) REFERENCES Utilisateur(id_utilisateur)
);

-- Table des Produits
CREATE TABLE Produit (
    id_produit INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255),
    description TEXT,
    stock INT,
    prix FLOAT(10, 2),
    image VARCHAR (255),
    categorie VARCHAR (255),
    archive BOOLEAN
);

-- Table des commandes
CREATE TABLE Commande (
    id_commande INT PRIMARY KEY AUTO_INCREMENT,
    date_commande DATE,
    numero_commande INT,
    statut_livraison VARCHAR(50),
    id_utilisateur INT,
    FOREIGN KEY (id_utilisateur) REFERENCES Client(id_utilisateur)
);

/* TABLES ASSOCIATIVES*/
-- Table de jointure Commande-Employe
CREATE TABLE Commande_Employe (
id_commande_employe INT PRIMARY KEY AUTO_INCREMENT,
id_commande INT,
id_utilisateur INT,
FOREIGN KEY (id_commande) REFERENCES Commande(id_commande),
FOREIGN KEY (id_utilisateur) REFERENCES Employe(id_utilisateur)
);

-- Table de jointure Produit-Employe
CREATE TABLE Employe_Produit (
id_employe_produit INT PRIMARY KEY AUTO_INCREMENT,
id_produit INT,
id_utilisateur INT,
FOREIGN KEY (id_produit) REFERENCES Produit(id_produit),
FOREIGN KEY (id_utilisateur) REFERENCES Employe(id_utilisateur)
);

-- Table de jointure Commande-Produit
CREATE TABLE Commande_Produit (
id_commande_produit INT PRIMARY KEY AUTO_INCREMENT,
id_produit INT,
id_commande INT,
quantite INT,
FOREIGN KEY (id_produit) REFERENCES Produit(id_produit),
FOREIGN KEY (id_commande) REFERENCES Commande(id_commande)
);
-- Insertion des données dans la table Utilisateur
INSERT INTO Utilisateur (nom, prenom, ville, code_postal, numero_rue, libelle_rue, email, telephone, archive)
VALUES
("Test", "Test", "Test", "12345", 123, "Test", "test@test.com", "123456", FALSE),
("Test1", "Test1", "Test1", "12345", 123, "Test1", "test1@test.com", "123456", FALSE),
("Test2", "Test2", "Test2", "12345", 123, "Test2", "test2@test.com", "123456", FALSE),
("Johnson", "Cave", "Ville d'Aperture", "12345", 123, "Rue de l'Aperture", "cjohnson@aperturescience.com", "0623232325", FALSE),
("Wolman", "Caroline", "Ville d'Aperture", "12345", 456, "Avenue de la Chambre de Test", "caroline@aperturescience.com", "0623232324", FALSE),
("Shell", "Chell", "Ville d'Aperture", "12345", 789, "Allée du Portail", "testsubject@aperturescience.com", "0623232323", FALSE),
("Turrell", "Doug", "Ville d'Aperture", "12345", 1011, "Rue des Tourelles", "dturrell@aperturescience.com", "0623232322", FALSE),
("Rattmann", "Doug", "Ville d'Aperture", "12345", 2022, "Rue des Graffitis", "drattmann@aperturescience.com", "0623232321", FALSE),
("Gladys", "Grenouille", "Ville d'Aperture", "12345", 3033, "Avenue du Marais", "ggrenouille@aperturescience.com", "0623232320", FALSE),
("Mel", "Manque", "Ville d'Aperture", "12345", 4044, "Rue du Passé", "mmanque@aperturescience.com", "0623232329", FALSE),
("Rick", "Spectre", "Ville d'Aperture", "12345", 5055, "Avenue des Échos", "rspectre@aperturescience.com", "0623232328", FALSE),
("Batard", "Clément", "Ville d'Aperture", "12345", 5055, "Avenue des Échos", "rspectre@aperturescience.com", "0623232328", FALSE),
("Porcher", "Samuel", "Ville d'Aperture", "12345", 5055, "Avenue des Échos", "rspectre@aperturescience.com", "0623232328", FALSE),
("Bellanger", "Quentin", "Ville d'Aperture", "12345", 5055, "Avenue des Échos", "rspectre@aperturescience.com", "0623232328", FALSE);

-- Insertion des données dans la table Employe
INSERT INTO Employe (id_utilisateur, mot_de_passe, matricule, date_embauche, role)
VALUES
(1, "$2a$10$Xz3JhdFVI0dcL9fcuM5Hr.5CknZ1vs2wAjpKV2Q9TlJRSFB05QMIK", "test", "2024-02-29", "ADMIN"),
(2, "$2a$10$Xz3JhdFVI0dcL9fcuM5Hr.5CknZ1vs2wAjpKV2Q9TlJRSFB05QMIK", "test1", "2024-02-29", "RESPONSABLE"),
(3, "$2a$10$Xz3JhdFVI0dcL9fcuM5Hr.5CknZ1vs2wAjpKV2Q9TlJRSFB05QMIK", "test2", "2024-02-29", "EMPLOYE"),
(12, "$2a$10$Xz3JhdFVI0dcL9fcuM5Hr.5CknZ1vs2wAjpKV2Q9TlJRSFB05QMIK", "Clem", "2024-02-29", "EMPLOYE"),
(13, "$2a$10$Xz3JhdFVI0dcL9fcuM5Hr.5CknZ1vs2wAjpKV2Q9TlJRSFB05QMIK", "Sam", "2024-02-29", "EMPLOYE"),
(14, "$2a$10$Xz3JhdFVI0dcL9fcuM5Hr.5CknZ1vs2wAjpKV2Q9TlJRSFB05QMIK", "Kenit", "2024-02-29", "EMPLOYE");

-- Insertion des données dans la table Client
INSERT INTO Client (id_utilisateur, date_affiliation, preferencecom)
VALUES
(4, "2016-02-01", "SMS"),
(5, "2017-03-01", "Email"),
(6, "2018-04-01", "Courrier"),
(7, "2019-05-01", "Téléphone"),
(8, "2015-01-01", "Email"),
(9, "2014-01-01", "Email"),
(10, "2012-01-01", "Email"),
(11, "2010-01-01", "Email");

-- Insertion des données dans la table Produit
INSERT INTO Produit (nom, description, stock, prix, image, categorie, archive) VALUES
("Portal Gun", "Appareil utilisé pour créer des portails inter-spatiaux", 120, 300, "Produit_41cdf77d-6d5c-4d62-85da-199449f77251.jpg", "Armes", 0),
("Cube Compagnon", "Cube de stockage pondéré", 99, 19.99, "Produit_1a962e2f-d45d-4958-b281-62467ce0891f.jpg", "Accessoires", 0),
("Manuel de Science Aperture", "Manuel du bon sens chez aperture", 29, 199.99, "Produit_b5ff3dae-778e-45d0-a9b2-02b4aa05972f.jpg", "Accessoires", 0),
("Bottes de Chute courte", "Bottes de protection qui évitent les dommages dus à la chute", 49, 150, "Produit_9a788d28-f3d3-4c45-8a64-bcb17de14ad2.jpg", "Vêtements", 0),
("Cube de Stockage Pondéré de Science Aperture", "Partie intégrante de nombreux chambres de test", 199, 29.99, "Produit_c065e505-e94d-4065-9dbc-69c0e5ffe5d8.jpg", "Accessoires", 0),
("Tourelle Automatique de Science Aperture", "Tourelle de fusil autonome utilisée à des fins de test", 14, 399.99, "Produit_08e73ec8-dbb2-4950-9c81-ac57fc831e6c.jpg", "Armes", 0),
("Grille d'Émancipation de Matériel de Science Aperture", "Champ d'énergie utilisé pour détruire les objets non autorisés", 9, 499.99, "Produit_836ff001-86d7-4a55-a827-51d55aaa72db.jpg", "Accessoires", 0),
("Pont de Lumière Dure de Science Aperture", "Surface de lumière solide pour traverser", 14, 799.99, "Produit_a2e065e0-630b-40db-8919-14992db02bf7.jpg", "Accessoires", 0),
("Gel de Suppression de Science Aperture", "Gel incolore qui détruit tout les autres gels.", 99, 49.99, "Produit_404b4117-e877-4516-9e4f-2dfb859c86ff.jpg", "Consommables", 0),
("Gel de Propulsion de Science Aperture", "Gel bleu pour la rebondir !", 99, 49.99, "Produit_63a6d178-555d-49ee-97bf-b06b9ebf9d99.jpg", "Consommables", 0),
("Gel de Conversion de Science Aperture", "Gel blanc qui convertit les matériaux", 99, 49.99, "Produit_404b4117-e877-4516-9e4f-2dfb859c86fe.jpg", "Consommables", 0),
("Noyau de Personnalité GLaDOS", "Noyau de personnalité IA central", 4, 999.99, "Produit_a791ceaa-34ce-4b56-b731-781665cf8f91.jpg", "Accessoires", 0),
("Noyau de Personnalité Wheatley", "Noyau de personnalité avec un accent britannique", 4, 999.99, "Produit_c2fe9533-c7b3-4b54-bedf-66aa3a37a6c7.jpg", "Accessoires", 0),
("Combinaison de Sujet de Test de Science Aperture", "Combinaison standard pour les sujets de test", 194, 99.99, "Produit_3b6a40c5-337c-4ef8-b6c1-68e0cc9acda8.jpg", "Vêtements", 0),
("Casque de Sujet de Test de Science Aperture", "Casque de protection pour les sujets de test", 199, 79.99, NULL, "Vêtements", 1),
("Bottes de Sujet de Test de Science Aperture", "Bottes robustes pour les sujets de test", 192, 69.99, NULL, "Vêtements", 1),
("Porte de Chambre de Test de Science Aperture", "Porte standard de chambre de test", 50, 999.99, NULL, "Accessoires", 1),
("Serviette de Science Aperture", "Serviette  haute qualité", 100, 29.99, "Produit_3dd5e35f-d42a-4971-a1cd-477d0a5c5001.jpg", "Accessoires", 0),
("Mug de Science Aperture", "Tasse avec le logo de Science Aperture", 200, 9.99, NULL, "Accessoires", 1),
("Défenseur de Bureau USB Tourelle de Science Aperture", "Tourelle USB pour défendre votre bureau", 50, 49.99, "Produit_75c62f2c-98b3-40bc-bd34-60298d20ebed.jpg", "Armes", 0),
("Bouteille d'Eau Laboratoires de Science Aperture", "Bouteille d'eau avec le logo des Laboratoires de Science Aperture", 100, 14.99, NULL, "Accessoires", 1),
("Tapis de Souris Laboratoires de Science Aperture", "Tapis de souris avec le logo des Laboratoires de Science Aperture", 200, 7.99, "Produit_104b1318-a28c-4fca-9dd3-cbb0933a3a8c.jpg", "Accessoires", 0),
("Ensemble de Stylos Laboratoires de Science Aperture", "Ensemble de stylos avec le logo des Laboratoires de Science Aperture", 150, 12.99, NULL, "Accessoires", 1),
("Bloc-Notes Laboratoires de Science Aperture", "Bloc-notes avec le logo des Laboratoires de Science Aperture", 150, 5.99, NULL, "Accessoires", 1),
("Lampe de Plafond GlaDOS", "Lampe de bureau avec le logo des Laboratoires de Science Aperture", 50, 39.99, "Produit_cd50835c-3c68-47be-8662-693e8efd4f11.jpg", "Décoration", 0),
("Peluche Turret de Science Aperture", "Peluche douce et câline en forme de tourelle", 100, 14.99, "Produit_83c87b02-b392-4066-adf7-3a030f2ffde7.jpg", "Jouets", 0),
("Peluche Companion Cube de Science Aperture", "Peluche en forme de cube compagnon", 100, 14.99, "Produit_feff4dc8-3745-4566-bdd8-1c7ee3c40559.jpg", "Jouets", 0),
("Affiche de Science Aperture", "Affiche de la compagnie Aperture Science", 200, 9.99, "Produit_81c632e5-eb01-4f9e-9a50-d885ad77dbfd.jpg", "Décoration", 0),
("Tasse de Café de Science Aperture", "Tasse à café avec logo Aperture Science", 200, 9.99, "Produit_1e6d7a54-cd3e-432f-a8e5-5b6105388e8d.jpg", "Décoration", 0),
("Portrait Fait par un peintre super connu","portrait du leader d'Aperture", 1, 10000000, "Produit_515c42f0-843e-4697-8736-ffadbd3dd36e.jpg","Décoration",0);

-- Insertions des commandes
INSERT INTO Commande (id_commande, date_commande, numero_commande, statut_livraison, id_utilisateur) VALUES
(1, "2023-01-05", 2024000001, "LIVREE", 4),
(2, "2023-02-10", 2024000002, "LIVREE", 5),
(3, "2023-03-15", 2024000003, "EN_PREPARATION", 6),
(4, "2023-04-20", 2024000004, "LIVREE", 7),
(5, "2023-05-25", 2024000005, "LIVREE", 8),
(6, "2023-06-30", 2024000006, "EN_COURS_ACHEMINEMENT", 9),
(7, "2023-07-05", 2024000007, "LIVREE", 10),
(8, "2023-08-10", 2024000008, "LIVREE", 11),
(9, "2023-09-15", 2024000009, "LIVREE", 11),
(10, "2023-10-20", 2024000010, "EN_COURS_ACHEMINEMENT", 10),
(11, "2023-11-25", 2024000011, "LIVREE", 9),
(12, "2023-12-30", 2024000012, "LIVREE", 4),
(13, "2024-01-05", 2024000013, "EN_PREPARATION", 5),
(14, "2024-02-10", 2024000014, "EN_COURS_ACHEMINEMENT", 6),
(15, "2024-03-15", 2024000015, "LIVREE", 7),
(16, "2024-04-20", 2024000016, "LIVREE", 8),
(17, "2024-05-25", 2024000017, "LIVREE", 9),
(18, "2024-06-30", 2024000018, "LIVREE", 10),
(19, "2024-07-05", 2024000019, "EN_PREPARATION", 11),
(20, "2024-08-10", 2024000020, "LIVREE", 11),
(21, "2024-09-15", 2024000021, "LIVREE", 11),
(22, "2024-10-20", 2024000022, "LIVREE", 7),
(23, "2024-11-25", 2024000023, "LIVREE", 4),
(24, "2024-12-30", 2024000024, "LIVREE", 5),
(25, "2025-01-05", 2024000025, "EN_PREPARATION", 6),
(26, "2025-02-10", 2024000026, "LIVREE", 7),
(27, "2025-03-15", 2024000027, "LIVREE", 8),
(28, "2025-04-20", 2024000028, "LIVREE", 9),
(29, "2025-05-25", 2024000029, "EN_COURS_ACHEMINEMENT", 10),
(30, "2025-06-30", 2024000030, "LIVREE", 11),
(31, "2025-07-05", 2024000031, "LIVREE", 7),
(32, "2025-08-10", 2024000032, "EN_PREPARATION", 6),
(33, "2025-09-15", 2024000033, "LIVREE", 7),
(34, "2025-10-20", 2024000034, "LIVREE", 4),
(35, "2025-11-25", 2024000035, "LIVREE", 5),
(36, "2025-12-30", 2024000036, "LIVREE", 6),
(37, "2026-01-05", 2024000037, "EN_COURS_ACHEMINEMENT", 7),
(38, "2026-02-10", 2024000038, "LIVREE", 8),
(39, "2026-03-15", 2024000039, "LIVREE", 9),
(40, "2026-04-20", 2024000040, "LIVREE", 10);

-- Insertions dans la table Commande_Produit
INSERT INTO Commande_Produit (id_produit, id_commande, quantite) VALUES
(1, 1, 2),
(1, 2, 3),
(2, 3, 1),
(3, 4, 2),
(3, 5, 1),
(4, 6, 1),
(4, 7, 1),
(4, 8, 3),
(5, 9, 2),
(6, 10, 1),
(7, 11, 1),
(7, 12, 2),
(8, 13, 3),
(8, 14, 2),
(9, 15, 1),
(10, 16, 1),
(10, 17, 1),
(11, 18, 2),
(11, 19, 1),
(11, 20, 3),
(12, 21, 1),
(13, 22, 2),
(14, 23, 1),
(14, 24, 1),
(15, 25, 3),
(16, 26, 2),
(16, 27, 1),
(17, 28, 1),
(18, 29, 2),
(18, 30, 3),
(19, 31, 1),
(20, 32, 2),
(20, 33, 1),
(21, 34, 1),
(22, 35, 2),
(23, 36, 3),
(24, 37, 2),
(24, 38, 1),
(25, 39, 1),
(26, 40, 1);


-- Insertions dans la table Commande_Employe
INSERT INTO Commande_Employe (id_commande, id_utilisateur) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 12),
(5, 13),
(6, 14),
(7, 1),
(8, 2),
(9, 3),
(10, 12),
(11, 13),
(12, 14),
(13, 1),
(14, 2),
(15, 3),
(16, 12),
(17, 13),
(18, 14),
(19, 1),
(20, 2),
(21, 3),
(22, 12),
(23, 13),
(24, 14),
(25, 1),
(26, 2),
(27, 3),
(28, 12),
(29, 13),
(30, 14),
(31, 1),
(32, 2),
(33, 3),
(34, 12),
(35, 13),
(36, 14),
(37, 1),
(38, 2),
(39, 3),
(40, 12);


