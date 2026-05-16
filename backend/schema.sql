-- ============================================================
-- schema.sql — Database skema + sample data untuk LoveFasilitas
-- Deploy ke InfinityFree via phpMyAdmin (tab SQL → paste → Go)
-- ============================================================

CREATE DATABASE IF NOT EXISTS lovefasilitas_db;
USE lovefasilitas_db;

-- ==================== TABEL FACILITIES ====================
CREATE TABLE IF NOT EXISTS facilities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    category VARCHAR(50) NOT NULL COMMENT 'hall, room, studio, outdoor',
    price INT NOT NULL COMMENT 'Harga per hari (full day)',
    capacity INT NOT NULL,
    location VARCHAR(200) NOT NULL,
    description TEXT,
    rating DECIMAL(2,1) DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================== TABEL USERS ====================
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    password VARCHAR(255) NOT NULL COMMENT 'hash bcrypt',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==================== SAMPLE DATA ====================
-- 5 fasilitas
INSERT INTO facilities (name, category, price, capacity, location, description, rating) VALUES
('Main Hall',           'hall',    10000000, 500, 'Building A, Floor 1', 'Large main hall for events and conferences. Fully equipped with sound system, stage lighting, and AC.',    4.7),
('Seminar Room',        'room',     4500000, 100, 'Building A, Floor 2', 'Medium-sized room for seminars and workshops. Includes projector, whiteboard, and comfortable seating.', 4.5),
('Ball Room',           'hall',     8000000, 300, 'Building B, Floor 1', 'Elegant ball room for celebrations and galas. Beautiful chandeliers and marble flooring.',               4.3),
('Auditorium',          'hall',    12000000, 800, 'Building C, Floor 1', 'Professional auditorium for large gatherings. Stadium seating with advanced AV system.',                4.8),
('Creative Studio',     'studio',   3500000,  30, 'Building D, Floor 3', 'Modern creative studio with professional lighting, backdrops, and photo equipment.',                     4.2);

-- 1 user admin (password: admin123)
INSERT INTO users (username, name, email, phone, password) VALUES
('admin', 'Administrator', 'admin@lovefasilitas.com', '081234567890', '$2y$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy');
-- bcrypt hash of "admin123"
