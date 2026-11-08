<%@ page contentType="text/html; charset=UTF-8" language="java" %>
    <%@ page import="com.sistemaventas.modelo.Cliente" %>
        <!DOCTYPE html>
        <html lang="es">

        <head>
            <meta charset="UTF-8">
            <title>Detalle del cliente</title>
            <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilos.css">
        </head>

        <body>
            <% Cliente cliente=(Cliente) request.getAttribute("cliente"); %>
                <h1>Detalle del cliente</h1>
                <p><strong>Id:</strong>
                    <%= cliente.getIdCliente() %>
                </p>
                <p><strong>Nombre:</strong>
                    <%= cliente.getNombre() %>
                </p>
                <p><strong>Documento:</strong>
                    <%= cliente.getDocumento() %>
                </p>
                <p><strong>Telefono:</strong>
                    <%= cliente.getTelefono() %>
                </p>
                <p><strong>Correo:</strong>
                    <%= cliente.getCorreo() %>
                </p>
                <p><strong>Direccion:</strong>
                    <%= cliente.getDireccion() %>
                </p>
                <p><a href="<%= request.getContextPath() %>/clientes">Volver a la lista</a></p>
        </body>

        </html>