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

  <% if ("ventas".equals(request.getParameter("error"))) { %>
  <p class="error">No se puede eliminar: el cliente tiene ventas asociadas.</p>
  <% } %>

  <p><a href="<%= request.getContextPath() %>/clientes?accion=nuevo">Nuevo cliente</a></p>

  <%
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
  %>

  <table>
    <tr>
      <th>Id</th><th>Nombre</th><th>Documento</th>
      <th>Telefono</th><th>Correo</th><th>Direccion</th>
      <th>Acciones</th>
    </tr>
    <% for (Cliente cliente : clientes) { %>
    <tr>
      <td><%= cliente.getIdCliente() %></td>
      <td><%= cliente.getNombre() %></td>
      <td><%= cliente.getDocumento() %></td>
      <td><%= cliente.getTelefono() %></td>
      <td><%= cliente.getCorreo() %></td>
      <td><%= cliente.getDireccion() %></td>
      <td>
        <a href="<%= request.getContextPath() %>/clientes?accion=buscar&id=<%= cliente.getIdCliente() %>">Ver</a>
        <a href="<%= request.getContextPath() %>/clientes?accion=editar&id=<%= cliente.getIdCliente() %>">Editar</a>
        <form method="post" action="<%= request.getContextPath() %>/clientes"
              onsubmit="return confirm('¿Eliminar este cliente?');" style="display:inline">
          <input type="hidden" name="accion" value="eliminar">
          <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">
          <button type="submit">Eliminar</button>
        </form>
      </td>
    </tr>
    <% } %>
  </table>

  <p><a href="<%= request.getContextPath() %>/">Volver al inicio</a></p>
</body>
</html>