<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.sistemaventas.modelo.Cliente" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Lista de clientes</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
  <h1>Lista de clientes</h1>
    <p><a href="<%= request.getContextPath() %>/clientes?accion=nuevo">Nuevo cliente</a></p>
    <%
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
  %>
    <table>
    <tr>
      <th>Id</th><th>Nombre</th><th>Documento</th>
      <th>Telefono</th><th>Correo</th><th>Direccion</th>
    </tr>
    <% for (Cliente cliente : clientes) { %>
    <tr>
      <td><%= cliente.getIdCliente() %></td>
      <td><%= cliente.getNombre() %></td>
      <td><%= cliente.getDocumento() %></td>
      <td><%= cliente.getTelefono() %></td>
      <td><%= cliente.getCorreo() %></td>
      <td><%= cliente.getDireccion() %></td>
    </tr>
    <% } %>
  </table>
</body>
</html>