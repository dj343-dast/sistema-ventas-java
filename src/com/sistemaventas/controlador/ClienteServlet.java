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
public class ClienteServlet extends HttpServlet {

    private ClienteDao clienteDao;
        @Override
    public void init() throws ServletException {
        clienteDao = new ClienteDao();
    }
        @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {        String accion = solicitud.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        try {
            switch (accion) {
                case "listar":
                    listarClientes(solicitud, respuesta);
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
    }
        private void listarClientes(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws SQLException, ServletException, IOException {
        List<Cliente> clientes = clienteDao.consultarClientes();
        solicitud.setAttribute("clientes", clientes);
        solicitud.getRequestDispatcher("/WEB-INF/vistas/lista.jsp")
                .forward(solicitud, respuesta);
    }
}