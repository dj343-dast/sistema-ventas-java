<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.sistemaventas.modelo.Cliente" %>
<%!
  private String texto(String valor) {
    return valor == null ? "" : valor;
  }
%>
<%
  Cliente cliente = (Cliente) request.getAttribute("cliente");
  boolean editando = cliente != null;
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title><%= editando ? "Editar cliente" : "Nuevo cliente" %></title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
  <h1><%= editando ? "Editar cliente" : "Nuevo cliente" %></h1>

  <form method="post" action="<%= request.getContextPath() %>/clientes">
    <input type="hidden" name="accion" value="guardar">
    <% if (editando) { %>
    <input type="hidden" name="idCliente" value="<%= cliente.getIdCliente() %>">
    <% } %>

    <label for="nombre">Nombre</label>
    <input type="text" id="nombre" name="nombre"
           value="<%= texto(editando ? cliente.getNombre() : null) %>" required>

    <label for="documento">Documento</label>
    <input type="text" id="documento" name="documento"
           value="<%= texto(editando ? cliente.getDocumento() : null) %>" required>

    <label for="telefono">Telefono</label>
    <input type="text" id="telefono" name="telefono"
           value="<%= texto(editando ? cliente.getTelefono() : null) %>">

    <label for="correo">Correo</label>
    <input type="email" id="correo" name="correo"
           value="<%= texto(editando ? cliente.getCorreo() : null) %>">

    <label for="direccion">Direccion</label>
    <input type="text" id="direccion" name="direccion"
           value="<%= texto(editando ? cliente.getDireccion() : null) %>">

    <button type="submit">Guardar</button>
  </form>

  <p><a href="<%= request.getContextPath() %>/clientes">Volver a la lista</a></p>
</body>
</html>