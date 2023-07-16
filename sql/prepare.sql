DROP USER IF EXISTS bankwiz_user@'localhost';
DROP USER IF EXISTS bankwiz_user@'%';
DROP DATABASE IF EXISTS bankwiz_db;

CREATE DATABASE bankwiz_db;
CREATE USER bankwiz_user@'localhost' IDENTIFIED BY 'BankwizPass2023';
CREATE USER bankwiz_user@'%' IDENTIFIED BY 'BankwizPass2023';

GRANT ALL PRIVILEGES ON bankwiz_db.* TO bankwiz_user@'localhost';
GRANT ALL PRIVILEGES ON bankwiz_db.* TO bankwiz_user@'%';
FLUSH PRIVILEGES;