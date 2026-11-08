<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Nuevo cliente</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
</head>
<body>
  <h1>Nuevo cliente</h1>
    <form method="post" action="<%= request.getContextPath() %>/clientes">
    <input type="hidden" name="accion" value="guardar">

    <label for="nombre">Nombre</label>
    <input type="text" id="nombre" name="nombre" required>

    <label for="documento">Documento</label>
    <input type="text" id="documento" name="documento" required>

    <label for="telefono">Telefono</label>
    <input type="text" id="telefono" name="telefono">

    <label for="correo">Correo</label>
    <input type="email" id="correo" name="correo">

    <label for="direccion">Direccion</label>
    <input type="text" id="direccion" name="direccion">

    <button type="submit">Guardar</button>
  </form>
    <p><a href="<%= request.getContextPath() %>/clientes">Volver a la lista</a></p>
</body>
</html>