package com.sistemaventas.controlador;

import com.sistemaventas.dao.ClienteDao;
import com.sistemaventas.modelo.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClienteServlet extends HttpServlet {

    private ClienteDao clienteDao;

    @Override
    public void init() throws ServletException {
        clienteDao = new ClienteDao();
    }

    @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        String accion = solicitud.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        try {
            switch (accion) {
                case "listar":
                    listarClientes(solicitud, respuesta);
                    break;
                case "nuevo":
                    solicitud.getRequestDispatcher("/WEB-INF/vistas/formulario.jsp")
                            .forward(solicitud, respuesta);
                    break;
                case "buscar":
                    verCliente(solicitud, respuesta);
                    break;
                case "editar":
                    editarCliente(solicitud, respuesta);
                    break;
                default:
                    respuesta.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (SQLException excepcion) {
            throw new ServletException("Error al consultar la base de datos", excepcion);
        }
    }

    @Override
    protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
        solicitud.setCharacterEncoding("UTF-8");
        String accion = solicitud.getParameter("accion");

        try {
            if ("guardar".equals(accion)) {
                guardarCliente(solicitud, respuesta);
            } else if ("eliminar".equals(accion)) {
                eliminarCliente(solicitud, respuesta);
            } else {
                respuesta.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException excepcion) {
            throw new ServletException("Error al guardar en la base de datos", excepcion);
        }
    }

    private void listarClientes(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws SQLException, ServletException, IOException {
        List<Cliente> clientes = clienteDao.consultarClientes();
        solicitud.setAttribute("clientes", clientes);
        solicitud.getRequestDispatcher("/WEB-INF/vistas/lista.jsp")
                .forward(solicitud, respuesta);
    }

    private void guardarCliente(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws SQLException, IOException {
        Cliente cliente = new Cliente(
                solicitud.getParameter("nombre"),
                solicitud.getParameter("documento"),
                solicitud.getParameter("telefono"),
                solicitud.getParameter("correo"),
                solicitud.getParameter("direccion"));

        String idTexto = solicitud.getParameter("idCliente");
        if (idTexto != null && !idTexto.isEmpty()) {
            cliente.setIdCliente(Integer.parseInt(idTexto));
            clienteDao.actualizarCliente(cliente);
        } else {
            clienteDao.insertarCliente(cliente);
        }
        respuesta.sendRedirect(solicitud.getContextPath() + "/clientes");
    }

    private void verCliente(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws SQLException, ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(solicitud.getParameter("id"));
        } catch (NumberFormatException excepcion) {
            respuesta.sendError(HttpServletResponse.SC_BAD_REQUEST, "El id debe ser un numero");
            return;
        }

        Optional<Cliente> cliente = clienteDao.consultarClientePorId(id);
        if (cliente.isPresent()) {
            solicitud.setAttribute("cliente", cliente.get());
            solicitud.getRequestDispatcher("/WEB-INF/vistas/detalle.jsp")
                    .forward(solicitud, respuesta);
        } else {
            respuesta.sendError(HttpServletResponse.SC_NOT_FOUND, "Cliente no encontrado");
        }
    }

    private void editarCliente(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws SQLException, ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(solicitud.getParameter("id"));
        } catch (NumberFormatException excepcion) {
            respuesta.sendError(HttpServletResponse.SC_BAD_REQUEST, "El id debe ser un numero");
            return;
        }

        Optional<Cliente> cliente = clienteDao.consultarClientePorId(id);
        if (cliente.isPresent()) {
            solicitud.setAttribute("cliente", cliente.get());
            solicitud.getRequestDispatcher("/WEB-INF/vistas/formulario.jsp")
                    .forward(solicitud, respuesta);
        } else {
            respuesta.sendError(HttpServletResponse.SC_NOT_FOUND, "Cliente no encontrado");
        }
    }

    private void eliminarCliente(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws SQLException, IOException {
        int id;
        try {
            id = Integer.parseInt(solicitud.getParameter("idCliente"));
        } catch (NumberFormatException excepcion) {
            respuesta.sendError(HttpServletResponse.SC_BAD_REQUEST, "El id debe ser un numero");
            return;
        }

        try {
            clienteDao.eliminarCliente(id);
            respuesta.sendRedirect(solicitud.getContextPath() + "/clientes");
        } catch (SQLException excepcion) {
            if (excepcion.getErrorCode() == 1451) {
                respuesta.sendRedirect(solicitud.getContextPath() + "/clientes?error=ventas");
            } else {
                throw excepcion;
            }
        }
    }
}