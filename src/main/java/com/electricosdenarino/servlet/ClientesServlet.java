
package com.electricosdenarino.servlet;

import com.electricosdenarino.dao.ClienteDAO;
import com.electricosdenarino.modelo.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/clientes")
public class ClientesServlet extends HttpServlet {

    private ClienteDAO clienteDAO;

    @Override
    public void init() {
        clienteDAO = new ClienteDAO();
    }

    // Consultar clientes y mostrar mensajes
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setAttribute("clientes", clienteDAO.listar());

            String mensaje = request.getParameter("mensaje");

            if (mensaje != null) {
                request.setAttribute("mensaje", mensaje);
            }

            request.getRequestDispatcher("/clientes.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Error al consultar los clientes.", e);
        }
    }

    // Registrar, actualizar o eliminar clientes
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        try {

            if ("editar".equals(accion)) {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(
                        Integer.parseInt(request.getParameter("idCliente")));
                cliente.setNombres(request.getParameter("nombres"));
                cliente.setApellidos(request.getParameter("apellidos"));
                cliente.setCedula(request.getParameter("cedula"));
                cliente.setTelefono(request.getParameter("telefono"));
                cliente.setCorreo(request.getParameter("correo"));
                cliente.setDireccion(request.getParameter("direccion"));

                clienteDAO.actualizar(cliente);

                response.sendRedirect(
                        request.getContextPath()
                        + "/clientes?mensaje=actualizado");

            } else if ("eliminar".equals(accion)) {

                int idCliente = Integer.parseInt(
                        request.getParameter("idCliente"));

                clienteDAO.eliminar(idCliente);

                response.sendRedirect(
                        request.getContextPath()
                        + "/clientes?mensaje=eliminado");

            } else {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(clienteDAO.obtenerSiguienteId());
                cliente.setNombres(request.getParameter("nombres"));
                cliente.setApellidos(request.getParameter("apellidos"));
                cliente.setCedula(request.getParameter("cedula"));
                cliente.setTelefono(request.getParameter("telefono"));
                cliente.setCorreo(request.getParameter("correo"));
                cliente.setDireccion(request.getParameter("direccion"));

                clienteDAO.registrar(cliente);

                response.sendRedirect(
                        request.getContextPath()
                        + "/clientes?mensaje=exito");
            }

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(
                    "Error al procesar la operación del cliente.", e);
        }
    }
}
