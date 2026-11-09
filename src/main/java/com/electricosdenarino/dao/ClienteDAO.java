
package com.electricosdenarino.dao;

import com.electricosdenarino.conexion.ConexionBD;
import com.electricosdenarino.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Obtener el siguiente ID disponible
    public int obtenerSiguienteId() throws SQLException {

        String sql = "SELECT COALESCE(MAX(idCliente), 0) + 1 FROM cliente";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {

            if (resultado.next()) {
                return resultado.getInt(1);
            }
        }

        return 1;
    }

    // Registrar un cliente
    public boolean registrar(Cliente cliente) throws SQLException {

        String sql = "INSERT INTO cliente "
                + "(idCliente, nombreCliente, apellidoCliente, "
                + "telefonoCliente, correoCliente, cedulaCliente, direccionCliente) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, cliente.getIdCliente());
            consulta.setString(2, cliente.getNombres());
            consulta.setString(3, cliente.getApellidos());
            consulta.setString(4, cliente.getTelefono());
            consulta.setString(5, cliente.getCorreo());
            consulta.setString(6, cliente.getCedula());
            consulta.setString(7, cliente.getDireccion());

            return consulta.executeUpdate() > 0;
        }
    }

    // Consultar todos los clientes
    public List<Cliente> listar() throws SQLException {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM cliente ORDER BY idCliente";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {

            while (resultado.next()) {

                Cliente cliente = new Cliente();

                cliente.setIdCliente(resultado.getInt("idCliente"));
                cliente.setNombres(resultado.getString("nombreCliente"));
                cliente.setApellidos(resultado.getString("apellidoCliente"));
                cliente.setTelefono(resultado.getString("telefonoCliente"));
                cliente.setCorreo(resultado.getString("correoCliente"));
                cliente.setCedula(resultado.getString("cedulaCliente"));
                cliente.setDireccion(resultado.getString("direccionCliente"));

                clientes.add(cliente);
            }
        }

        return clientes;
    }

    // Actualizar los datos de un cliente
    public boolean actualizar(Cliente cliente) throws SQLException {

        String sql = "UPDATE cliente SET "
                + "nombreCliente = ?, "
                + "apellidoCliente = ?, "
                + "telefonoCliente = ?, "
                + "correoCliente = ?, "
                + "cedulaCliente = ?, "
                + "direccionCliente = ? "
                + "WHERE idCliente = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setString(1, cliente.getNombres());
            consulta.setString(2, cliente.getApellidos());
            consulta.setString(3, cliente.getTelefono());
            consulta.setString(4, cliente.getCorreo());
            consulta.setString(5, cliente.getCedula());
            consulta.setString(6, cliente.getDireccion());
            consulta.setInt(7, cliente.getIdCliente());

            return consulta.executeUpdate() > 0;
        }
    }

    // Eliminar un cliente por su ID
    public boolean eliminar(int idCliente) throws SQLException {

        String sql = "DELETE FROM cliente WHERE idCliente = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement consulta = conexion.prepareStatement(sql)) {

            consulta.setInt(1, idCliente);

            return consulta.executeUpdate() > 0;
        }
    }
}
