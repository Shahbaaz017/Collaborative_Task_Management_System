CREATE DATABASE IF NOT EXISTS taskdb;
USE taskdb;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS collaborations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(100),
    collaborators VARCHAR(255),
    meeting_link VARCHAR(255),
    meeting_date DATETIME,
    priority VARCHAR(50),
    head VARCHAR(100)
);
