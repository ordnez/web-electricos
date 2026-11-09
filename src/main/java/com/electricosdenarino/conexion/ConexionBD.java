
package com.electricosdenarino.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/electricos_de_narino";

    private static final String USUARIO = "root";

    private static final String CONTRASENA = "";

    public static Connection obtenerConexion() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "No se encontró el controlador JDBC de MySQL.", e);
        }

        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
