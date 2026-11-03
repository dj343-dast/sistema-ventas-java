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
  /**
   * Consulta todos los clientes ordenados por nombre.
   *
   * @return lista de clientes; vacia si no hay registros.
   * @throws SQLException si falla la consulta.
   */
  public List<Cliente> consultarClientes() throws SQLException {
    List<Cliente> clientes = new ArrayList<>();
    try (Connection conexion = ConexionBd.obtenerConexion();
        PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
        ResultSet resultado = sentencia.executeQuery()) {
      while (resultado.next()) {
        clientes.add(convertirFilaEnCliente(resultado));
      }
    }
    return clientes;
  }

  /**
   * Consulta un cliente por su id.
   *
   * @param idCliente id del cliente buscado.
   * @return el cliente, o vacio si no existe.
   * @throws SQLException si falla la consulta.
   */
  public Optional<Cliente> consultarClientePorId(int idCliente) throws SQLException {
    try (Connection conexion = ConexionBd.obtenerConexion();
        PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {
      sentencia.setInt(1, idCliente);
      try (ResultSet resultado = sentencia.executeQuery()) {
        if (resultado.next()) {
          return Optional.of(convertirFilaEnCliente(resultado));
        }
        return Optional.empty();
      }
    }
  }

  private Cliente convertirFilaEnCliente(ResultSet resultado) throws SQLException {
    return new Cliente(
        resultado.getInt("id_cliente"),
        resultado.getString("nombre"),
        resultado.getString("documento"),
        resultado.getString("telefono"),
        resultado.getString("correo"),
        resultado.getString("direccion"));
  }
}