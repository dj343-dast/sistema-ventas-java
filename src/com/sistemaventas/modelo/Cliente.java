package com.sistemaventas.modelo;

/**
 * Representa un cliente del sistema. Corresponde a la tabla cliente de la base de datos.
 */
public class Cliente {

  private int idCliente;
  private String nombre;
  private String documento;
  private String telefono;
  private String correo;
  private String direccion;

  public Cliente() {
  }

  public Cliente(String nombre, String documento, String telefono, String correo, String direccion) {
    this.nombre = nombre;
    this.documento = documento;
    this.telefono = telefono;
    this.correo = correo;
    this.direccion = direccion;
  }

  public Cliente(int idCliente, String nombre, String documento, String telefono, String correo,
      String direccion) {
    this(nombre, documento, telefono, correo, direccion);
    this.idCliente = idCliente;
  }

  public int getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(int idCliente) {
    this.idCliente = idCliente;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDocumento() {
    return documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }
}
