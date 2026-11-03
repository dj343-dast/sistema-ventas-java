package com.sistemaventas.dao;

import com.sistemaventas.conexion.ConexionBd;
import com.sistemaventas.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos de la tabla cliente mediante JDBC: inserta, consulta, actualiza y elimina.
 * Usa PreparedStatement para que los datos escritos por el usuario no alteren la sentencia SQL.
 */
public class ClienteDao {

  private static final String SQL_INSERTAR =
      "INSERT INTO cliente (nombre, documento, telefono, correo, direccion) VALUES (?, ?, ?, ?, ?)";
  private static final String SQL_CONSULTAR_TODOS =
      "SELECT id_cliente, nombre, documento, telefono, correo, direccion FROM cliente ORDER BY nombre";
  private static final String SQL_CONSULTAR_POR_ID =
      "SELECT id_cliente, nombre, documento, telefono, correo, direccion FROM cliente "
          + "WHERE id_cliente = ?";
  private static final String SQL_ACTUALIZAR =
      "UPDATE cliente SET nombre = ?, documento = ?, telefono = ?, correo = ?, direccion = ? "
          + "WHERE id_cliente = ?";
  private static final String SQL_ELIMINAR = "DELETE FROM cliente WHERE id_cliente = ?";

  /**
   * Inserta un cliente nuevo.
   *
   * @param cliente datos del cliente; el id lo genera la base de datos.
   * @return id asignado al cliente.
   * @throws SQLException si la base de datos rechaza la insercion.
   */
  public int insertarCliente(Cliente cliente) throws SQLException {
    try (Connection conexion = ConexionBd.obtenerConexion();
        PreparedStatement sentencia =
            conexion.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
      sentencia.setString(1, cliente.getNombre());
      sentencia.setString(2, cliente.getDocumento());
      sentencia.setString(3, cliente.getTelefono());
      sentencia.setString(4, cliente.getCorreo());
      sentencia.setString(5, cliente.getDireccion());
      sentencia.executeUpdate();
      try (ResultSet clavesGeneradas = sentencia.getGeneratedKeys()) {
        return clavesGeneradas.next() ? clavesGeneradas.getInt(1) : 0;
      }
    }
  }
}