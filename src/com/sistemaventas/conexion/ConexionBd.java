
package com.sistemaventas.conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Crea las conexiones JDBC con la base de datos MySQL del sistema.
 * Los datos de conexion se leen del archivo config.properties, que no se sube al repositorio.
 */
public class ConexionBd {

  private static final String ARCHIVO_CONFIGURACION = "config.properties";
  private static final String CLASE_DRIVER = "com.mysql.cj.jdbc.Driver";

  private ConexionBd() {
  }

  /**
   * Abre una conexion nueva con la base de datos.
   *
   * @return conexion abierta; quien la recibe debe cerrarla.
   * @throws SQLException si falta el driver, el archivo de configuracion o las credenciales fallan.
   */
  public static Connection obtenerConexion() throws SQLException {
    Properties configuracion = cargarConfiguracion();
    try {
      Class.forName(CLASE_DRIVER);
    } catch (ClassNotFoundException excepcion) {
      throw new SQLException("No se encontro el driver JDBC de MySQL. Copie el .jar en la carpeta lib.",
          excepcion);
    }
    return DriverManager.getConnection(
        configuracion.getProperty("db.url"),
        configuracion.getProperty("db.usuario"),
        configuracion.getProperty("db.contrasena"));
  }

  private static Properties cargarConfiguracion() throws SQLException {
    Properties configuracion = new Properties();
    try (FileInputStream entrada = new FileInputStream(ARCHIVO_CONFIGURACION)) {
      configuracion.load(entrada);
    } catch (IOException excepcion) {
      throw new SQLException("No se pudo leer " + ARCHIVO_CONFIGURACION
          + ". Copie config.properties.example con ese nombre y complete sus datos.", excepcion);
    }
    return configuracion;
  }
}