# Sistema de ventas e inventario - Modulo de clientes

Modulo de clientes en Java con conexion a MySQL mediante JDBC. Permite registrar, consultar,
actualizar y eliminar clientes de dos formas: desde un menu de consola y desde una aplicacion
web con servlets y JSP desplegada en Apache Tomcat.

## Estructura

- `conexion/ConexionBd`: abre la conexion JDBC leyendo `config.properties`.
- `modelo/Cliente`: representa la tabla `cliente`.
- `dao/ClienteDao`: inserta, consulta, actualiza y elimina con `PreparedStatement`.
- `principal/Principal`: menu de consola.
- `controlador/ClienteServlet`: recibe las peticiones GET y POST, usa `ClienteDao` y entrega los datos a las vistas JSP.
- `webapp/`: pagina de inicio, estilos en `css/` y, dentro de `WEB-INF`, el `web.xml` y las vistas JSP (`lista.jsp`, `formulario.jsp`, `detalle.jsp`).

## Requisitos

- JDK 17 o superior.
- MySQL 8 con la base `sistema_ventas_inventario` y la tabla `cliente`.
- Driver MySQL Connector/J en la carpeta `lib`.
- Apache Tomcat 10.1 (usa Jakarta Servlet) para la version web.

## Configuracion

1. Ejecute `base_de_datos.sql` en MySQL para crear la base y las tablas.
2. Copie `config.properties.example` con el nombre `config.properties`.
3. Escriba su contrasena de MySQL en `db.contrasena`.
4. Para la version de consola, ejecute `Principal.java`. Para la version web, siga la seccion siguiente.

## Version web

### Despliegue

1. Revise la variable `TOMCAT_HOME` al inicio de `build.sh` (carpeta de Tomcat, con `/` en lugar de `\`).
2. Desde la raiz del proyecto ejecute `bash build.sh`. El script compila el codigo, arma la aplicacion y la copia a `webapps/sistema-ventas` de Tomcat. `config.properties` se copia solo a `WEB-INF/classes`.

### Ejecucion

1. Inicie Tomcat con `catalina.bat run` desde la carpeta `bin` de Tomcat.
2. Abra `http://localhost:8080/sistema-ventas/`.

### Funcionalidades

| Funcionalidad | Metodo | Direccion |
|---|---|---|
| Listar clientes | GET | `/clientes` |
| Formulario de nuevo cliente | GET | `/clientes?accion=nuevo` |
| Guardar (crear o editar) | POST | `/clientes` con `accion=guardar` |
| Buscar por id | GET | `/clientes?accion=buscar&id=N` |
| Formulario de edicion | GET | `/clientes?accion=editar&id=N` |
| Eliminar | POST | `/clientes` con `accion=eliminar` |

Las consultas usan GET y las operaciones que cambian datos usan POST.

### Elementos de JSP utilizados

Directivas (`<%@ page %>`), expresiones (`<%= %>`), scriptlets (`<% %>`), declaraciones (`<%! %>`) y el objeto implicito `request`. Las vistas estan en `WEB-INF/vistas`, por lo que solo se abren a traves del servlet.

## Estandares de codificacion

- Clases en UpperCamelCase, metodos y variables en lowerCamelCase, constantes en MAYUSCULAS_CON_GUIONES.
- Paquetes en minuscula.
- Los datos de conexion no se suben al repositorio.