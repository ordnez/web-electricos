
package com.electricosdenarino.servlet;

import com.electricosdenarino.dao.ClienteDAO;
import com.electricosdenarino.modelo.Cliente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/clientes")
public class ClientesApiServlet extends HttpServlet {

    private ClienteDAO clienteDAO;

    @Override
    public void init() {
        clienteDAO = new ClienteDAO();
    }

    // Consultar todos los clientes y devolverlos en formato JSON.
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Cliente> clientes = clienteDAO.listar();
            PrintWriter salida = response.getWriter();

            salida.print("[");

            for (int i = 0; i < clientes.size(); i++) {
                Cliente cliente = clientes.get(i);

                if (i > 0) {
                    salida.print(",");
                }

                salida.print("{");
                salida.print("\"idCliente\":" + cliente.getIdCliente() + ",");
                salida.print("\"nombres\":\"" + escapar(cliente.getNombres()) + "\",");
                salida.print("\"apellidos\":\"" + escapar(cliente.getApellidos()) + "\",");
                salida.print("\"cedula\":\"" + escapar(cliente.getCedula()) + "\",");
                salida.print("\"telefono\":\"" + escapar(cliente.getTelefono()) + "\",");
                salida.print("\"correo\":\"" + escapar(cliente.getCorreo()) + "\",");
                salida.print("\"direccion\":\"" + escapar(cliente.getDireccion()) + "\"");
                salida.print("}");
            }

            salida.print("]");

        } catch (SQLException e) {
            enviarError(response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No fue posible consultar los clientes.");
        }
    }

    // Registrar un cliente recibido desde React.
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Cliente cliente = obtenerCliente(request);
            cliente.setIdCliente(clienteDAO.obtenerSiguienteId());

            boolean registrado = clienteDAO.registrar(cliente);

            if (registrado) {
                enviarMensaje(response, HttpServletResponse.SC_CREATED,
                        "Cliente registrado correctamente.");
            } else {
                enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                        "No se pudo registrar el cliente.");
            }

        } catch (SQLException e) {
            enviarError(response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al registrar el cliente.");
        }
    }

    // Actualizar un cliente existente mediante su identificador.
    @Override
    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Map<String, String> parametros = leerParametros(request);

            String id = request.getParameter("idCliente");

            if (id == null || id.trim().isEmpty()) {
                throw new NumberFormatException("Falta el identificador.");
            }

            int idCliente = Integer.parseInt(id);

            Cliente cliente = new Cliente();
            cliente.setIdCliente(idCliente);
            cliente.setNombres(parametros.get("nombres"));
            cliente.setApellidos(parametros.get("apellidos"));
            cliente.setCedula(parametros.get("cedula"));
            cliente.setTelefono(parametros.get("telefono"));
            cliente.setCorreo(parametros.get("correo"));
            cliente.setDireccion(parametros.get("direccion"));

            boolean actualizado = clienteDAO.actualizar(cliente);

            if (actualizado) {
                enviarMensaje(response, HttpServletResponse.SC_OK,
                        "Cliente actualizado correctamente.");
            } else {
                enviarError(response, HttpServletResponse.SC_NOT_FOUND,
                        "No se encontró el cliente.");
            }

        } catch (SQLException | NumberFormatException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "No fue posible actualizar el cliente.");
        }
    }

    // Eliminar un cliente mediante su identificador.
    @Override
    protected void doDelete(HttpServletRequest request,
                            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idCliente = obtenerId(request);

            boolean eliminado = clienteDAO.eliminar(idCliente);

            if (eliminado) {
                enviarMensaje(response, HttpServletResponse.SC_OK,
                        "Cliente eliminado correctamente.");
            } else {
                enviarError(response, HttpServletResponse.SC_NOT_FOUND,
                        "No se encontró el cliente.");
            }

        } catch (SQLException | NumberFormatException e) {
            enviarError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "No fue posible eliminar el cliente.");
        }
    }

    // Construir un objeto Cliente con los datos recibidos.
    private Cliente obtenerCliente(HttpServletRequest request) {
        Cliente cliente = new Cliente();

        cliente.setNombres(request.getParameter("nombres"));
        cliente.setApellidos(request.getParameter("apellidos"));
        cliente.setCedula(request.getParameter("cedula"));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setCorreo(request.getParameter("correo"));
        cliente.setDireccion(request.getParameter("direccion"));

        return cliente;
    }

    // Leer los parámetros enviados en el cuerpo de una solicitud PUT.
    private Map<String, String> leerParametros(HttpServletRequest request)
            throws IOException {

        Map<String, String> parametros = new HashMap<>();
        StringBuilder cuerpo = new StringBuilder();
        String linea;

        try (java.io.BufferedReader lector = request.getReader()) {
            while ((linea = lector.readLine()) != null) {
                cuerpo.append(linea);
            }
        }

        String[] pares = cuerpo.toString().split("&");

        for (String par : pares) {
            String[] partes = par.split("=", 2);

            String clave = URLDecoder.decode(
                    partes[0], StandardCharsets.UTF_8);

            String valor = partes.length > 1
                    ? URLDecoder.decode(partes[1], StandardCharsets.UTF_8)
                    : "";

            parametros.put(clave, valor);
        }

        return parametros;
    }

    // Obtener el identificador enviado en la solicitud.
    private int obtenerId(HttpServletRequest request) {
        String id = request.getParameter("idCliente");

        if (id == null || id.trim().isEmpty()) {
            throw new NumberFormatException("Falta el identificador.");
        }

        return Integer.parseInt(id);
    }

    // Enviar una respuesta JSON de éxito.
    private void enviarMensaje(HttpServletResponse response,
                               int estado, String mensaje)
            throws IOException {

        response.setStatus(estado);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().print(
                "{\"mensaje\":\"" + escapar(mensaje) + "\"}"
        );
    }

    // Enviar una respuesta JSON de error.
    private void enviarError(HttpServletResponse response,
                             int estado, String mensaje)
            throws IOException {

        response.setStatus(estado);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().print(
                "{\"error\":\"" + escapar(mensaje) + "\"}"
        );
    }

    // Evitar errores de formato JSON con caracteres especiales.
    private String escapar(String valor) {
        if (valor == null) {
            return "";
        }

        return valor.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
