package com.sistemaventas.controlador;

import com.sistemaventas.dao.ClienteDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
public class ClienteServlet extends HttpServlet {

    private ClienteDao clienteDao;
        @Override
    public void init() throws ServletException {
        clienteDao = new ClienteDao();
    }
        @Override
    protected void doGet(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest solicitud, HttpServletResponse respuesta)
            throws ServletException, IOException {
    }
}