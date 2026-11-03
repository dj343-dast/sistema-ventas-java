   CREATE SCHEMA IF NOT EXISTS `sistema_ventas_inventario` DEFAULT CHARACTER SET utf8mb4;
   USE `sistema_ventas_inventario`;

   CREATE TABLE IF NOT EXISTS `cliente` (
     `id_cliente` INT NOT NULL AUTO_INCREMENT,
     `nombre` VARCHAR(100) NOT NULL,
     `documento` VARCHAR(20) NOT NULL,
     `telefono` VARCHAR(20) NULL,
     `correo` VARCHAR(100) NULL,
     `direccion` VARCHAR(150) NULL,
     PRIMARY KEY (`id_cliente`))
   ENGINE = InnoDB;