# Sistema de ventas e inventario - Modulo de clientes

Modulo de clientes en Java con conexion a MySQL mediante JDBC. Permite registrar, consultar,
actualizar y eliminar clientes desde un menu de consola.

## Estructura

- `conexion/ConexionBd`: abre la conexion JDBC leyendo `config.properties`.
- `modelo/Cliente`: representa la tabla `cliente`.
- `dao/ClienteDao`: inserta, consulta, actualiza y elimina con `PreparedStatement`.
- `principal/Principal`: menu de consola.

## Requisitos

- JDK 17 o superior.
- MySQL 8 con la base `sistema_ventas_inventario` y la tabla `cliente`.
- Driver MySQL Connector/J en la carpeta `lib`.

## Configuracion

1. Copie `config.properties.example` con el nombre `config.properties`.
2. Escriba su contrasena de MySQL en `db.contrasena`.
3. Ejecute `Principal.java`.

## Estandares de codificacion

- Clases en UpperCamelCase, metodos y variables en lowerCamelCase, constantes en MAYUSCULAS_CON_GUIONES.
- Paquetes en minuscula.
- Los datos de conexion no se suben al repositorio.