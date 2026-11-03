package com.sistemaventas.principal;

import com.sistemaventas.dao.ClienteDao;
import com.sistemaventas.modelo.Cliente;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Menu de consola del modulo de clientes del Sistema de ventas e inventario.
 */
public class Principal {

  private static final Scanner teclado = new Scanner(System.in);
  private static final ClienteDao clienteDao = new ClienteDao();

  public static void main(String[] args) {
    int opcion;
    do {
      mostrarMenu();
      opcion = leerEntero("Seleccione una opcion: ");
      ejecutarOpcion(opcion);
    } while (opcion != 0);
    System.out.println("Hasta luego.");
  }

  private static void mostrarMenu() {
    System.out.println();
    System.out.println("=== MODULO DE CLIENTES ===");
    System.out.println("1. Registrar cliente");
    System.out.println("2. Consultar todos los clientes");
    System.out.println("3. Consultar cliente por id");
    System.out.println("4. Actualizar cliente");
    System.out.println("5. Eliminar cliente");
    System.out.println("0. Salir");
  }

  private static void ejecutarOpcion(int opcion) {
    try {
      switch (opcion) {
        case 1:
          registrarCliente();
          break;
        case 2:
          mostrarClientes();
          break;
        case 3:
          mostrarClientePorId();
          break;
        case 4:
          modificarCliente();
          break;
        case 5:
          borrarCliente();
          break;
        case 0:
          break;
        default:
          System.out.println("Opcion no valida.");
      }
    } catch (SQLIntegrityConstraintViolationException excepcion) {
      System.out.println("La base de datos rechazo la operacion: " + excepcion.getMessage());
    } catch (SQLException excepcion) {
      System.out.println("Error de base de datos: " + excepcion.getMessage());
    }
  }

  private static void registrarCliente() throws SQLException {
    String nombre = leerTextoObligatorio("Nombre: ");
    String documento = leerTextoObligatorio("Documento: ");
    String telefono = leerTextoOpcional("Telefono (opcional): ");
    String correo = leerTextoOpcional("Correo (opcional): ");
    String direccion = leerTextoOpcional("Direccion (opcional): ");

    Cliente cliente = new Cliente(nombre, documento, telefono, correo, direccion);
    int idCliente = clienteDao.insertarCliente(cliente);
    System.out.println("Cliente registrado con id " + idCliente + ".");
  }

  private static void mostrarClientes() throws SQLException {
    List<Cliente> clientes = clienteDao.consultarClientes();
    if (clientes.isEmpty()) {
      System.out.println("No hay clientes registrados.");
      return;
    }
    System.out.printf("%-5s %-25s %-15s %-15s %-28s %s%n",
        "ID", "NOMBRE", "DOCUMENTO", "TELEFONO", "CORREO", "DIRECCION");
    for (Cliente cliente : clientes) {
      imprimirCliente(cliente);
    }
  }

  private static void mostrarClientePorId() throws SQLException {
    int idCliente = leerEntero("Id del cliente: ");
    Optional<Cliente> cliente = clienteDao.consultarClientePorId(idCliente);
    if (cliente.isPresent()) {
      imprimirCliente(cliente.get());
    } else {
      System.out.println("No existe un cliente con id " + idCliente + ".");
    }
  }

  private static void modificarCliente() throws SQLException {
    int idCliente = leerEntero("Id del cliente a actualizar: ");
    Optional<Cliente> encontrado = clienteDao.consultarClientePorId(idCliente);
    if (!encontrado.isPresent()) {
      System.out.println("No existe un cliente con id " + idCliente + ".");
      return;
    }

    Cliente cliente = encontrado.get();
    System.out.println("Pulse Enter para conservar el valor actual.");
    cliente.setNombre(leerTextoConValorActual("Nombre", cliente.getNombre()));
    cliente.setDocumento(leerTextoConValorActual("Documento", cliente.getDocumento()));
    cliente.setTelefono(leerTextoConValorActual("Telefono", cliente.getTelefono()));
    cliente.setCorreo(leerTextoConValorActual("Correo", cliente.getCorreo()));
    cliente.setDireccion(leerTextoConValorActual("Direccion", cliente.getDireccion()));

    clienteDao.actualizarCliente(cliente);
    System.out.println("Cliente actualizado.");
  }

  private static void borrarCliente() throws SQLException {
    int idCliente = leerEntero("Id del cliente a eliminar: ");
    String confirmacion = leerTextoOpcional("Escriba S para confirmar la eliminacion: ");
    if (confirmacion == null || !confirmacion.equalsIgnoreCase("S")) {
      System.out.println("Eliminacion cancelada.");
      return;
    }
    if (clienteDao.eliminarCliente(idCliente)) {
      System.out.println("Cliente eliminado.");
    } else {
      System.out.println("No existe un cliente con id " + idCliente + ".");
    }
  }

  private static void imprimirCliente(Cliente cliente) {
    System.out.printf("%-5d %-25s %-15s %-15s %-28s %s%n",
        cliente.getIdCliente(),
        cliente.getNombre(),
        cliente.getDocumento(),
        textoOGuion(cliente.getTelefono()),
        textoOGuion(cliente.getCorreo()),
        textoOGuion(cliente.getDireccion()));
  }

  private static String textoOGuion(String texto) {
    return texto == null ? "-" : texto;
  }

  private static int leerEntero(String mensaje) {
    while (true) {
      System.out.print(mensaje);
      String linea = teclado.nextLine().trim();
      try {
        return Integer.parseInt(linea);
      } catch (NumberFormatException excepcion) {
        System.out.println("Escriba un numero entero.");
      }
    }
  }

  private static String leerTextoObligatorio(String mensaje) {
    while (true) {
      System.out.print(mensaje);
      String linea = teclado.nextLine().trim();
      if (!linea.isEmpty()) {
        return linea;
      }
      System.out.println("Este dato es obligatorio.");
    }
  }

  private static String leerTextoOpcional(String mensaje) {
    System.out.print(mensaje);
    String linea = teclado.nextLine().trim();
    return linea.isEmpty() ? null : linea;
  }

  private static String leerTextoConValorActual(String etiqueta, String valorActual) {
    System.out.print(etiqueta + " [" + textoOGuion(valorActual) + "]: ");
    String linea = teclado.nextLine().trim();
    return linea.isEmpty() ? valorActual : linea;
  }
}