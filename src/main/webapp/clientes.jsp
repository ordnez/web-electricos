
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="com.electricosdenarino.modelo.Cliente" %>

<!DOCTYPE html>
<html lang="es" translate="no">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Gestión de Clientes | Eléctricos de Nariño</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/clientes/clientes.css">
</head>

<body>

    <!-- MENÚ LATERAL -->
    <div class="sidebar">

        <div class="logo">
            <img src="${pageContext.request.contextPath}/clientes/img/logo.png" alt="Logo Eléctricos de Nariño">
            <h3>ELÉCTRICOS DE NARIÑO</h3>
            <p>EXPERIENCIA, SERIEDAD Y CUMPLIMIENTO</p>
        </div>

        <ul>
            <li><a href="#"><i class="fa-solid fa-house"></i> Inicio</a></li>

            <li class="activo">
                <a href="${pageContext.request.contextPath}/clientes">
                    <i class="fa-solid fa-users"></i> Clientes
                </a>
            </li>

            <li><a href="#"><i class="fa-solid fa-tv"></i> Equipos</a></li>
            <li><a href="#"><i class="fa-solid fa-screwdriver-wrench"></i> Reparaciones</a></li>
            <li><a href="#"><i class="fa-solid fa-boxes-stacked"></i> Inventario</a></li>
            <li><a href="#"><i class="fa-solid fa-cart-shopping"></i> Ventas</a></li>
            <li><a href="#"><i class="fa-solid fa-file-invoice-dollar"></i> Facturación</a></li>
            <li><a href="#"><i class="fa-solid fa-chart-column"></i> Reportes</a></li>
            <li><a href="#"><i class="fa-solid fa-gear"></i> Configuración</a></li>
        </ul>

        <button id="cerrar" type="button">
            <i class="fa-solid fa-right-from-bracket"></i>
            Cerrar sesión
        </button>
    </div>

    <!-- CONTENIDO PRINCIPAL -->
    <div class="contenido">

        <div class="encabezado">
            <div></div>
            <div class="iconos">
                <i class="fa-regular fa-bell"></i>
                <i class="fa-regular fa-circle-user"></i>
            </div>
        </div>

        <div class="titulo">
            <h2>Gestión de Clientes</h2>
        </div>

        <!-- MENSAJES -->
        <%
            String mensaje = (String) request.getAttribute("mensaje");
            if ("exito".equals(mensaje)) {
        %>
            <div class="alert alert-success">Cliente registrado correctamente.</div>
        <%
            } else if ("actualizado".equals(mensaje)) {
        %>
            <div class="alert alert-success">Cliente actualizado correctamente.</div>
        <%
            } else if ("eliminado".equals(mensaje)) {
        %>
            <div class="alert alert-success">Cliente eliminado correctamente.</div>
        <%
            }
        %>

        <!-- FORMULARIO DE REGISTRO Y EDICIÓN -->
        <div class="formulario">

            <form method="POST" action="${pageContext.request.contextPath}/clientes" id="formCliente">

                <input type="hidden" name="accion" id="accion" value="registrar">
                <input type="hidden" name="idCliente" id="idCliente">

                <div class="row">

                    <div class="col-md-6 mb-3">
                        <label class="form-label">
                            <i class="fa-solid fa-user"></i> Nombres
                        </label>
                        <input type="text" name="nombres" id="nombres"
                               class="form-control" placeholder="Ingrese los nombres" required>
                    </div>

                    <div class="col-md-6 mb-3">
                        <label class="form-label">
                            <i class="fa-solid fa-user"></i> Apellidos
                        </label>
                        <input type="text" name="apellidos" id="apellidos"
                               class="form-control" placeholder="Ingrese los apellidos" required>
                    </div>

                </div>

                <div class="row">

                    <div class="col-md-4 mb-3">
                        <label class="form-label">
                            <i class="fa-regular fa-id-card"></i> Cédula
                        </label>
                        <input type="text" name="cedula" id="cedula"
                               class="form-control" placeholder="Número de cédula" required>
                    </div>

                    <div class="col-md-4 mb-3">
                        <label class="form-label">
                            <i class="fa-solid fa-phone"></i> Teléfono
                        </label>
                        <input type="text" name="telefono" id="telefono"
                               class="form-control" placeholder="Número telefónico" required>
                    </div>

                    <div class="col-md-4 mb-3">
                        <label class="form-label">
                            <i class="fa-solid fa-envelope"></i> Correo electrónico
                        </label>
                        <input type="email" name="correo" id="correo"
                               class="form-control" placeholder="correo@ejemplo.com" required>
                    </div>

                </div>

                <div class="row">

                    <div class="col-md-12 mb-4">
                        <label class="form-label">
                            <i class="fa-solid fa-location-dot"></i> Dirección
                        </label>
                        <input type="text" name="direccion" id="direccion"
                               class="form-control" placeholder="Ingrese la dirección" required>
                    </div>

                </div>

                <div class="botones">

                    <button type="submit" id="guardar" class="btn btn-warning">
                        <i class="fa-solid fa-floppy-disk"></i>
                        Guardar Cliente
                    </button>

                    <button type="reset" id="limpiar" class="btn btn-secondary">
                        <i class="fa-solid fa-broom"></i>
                        Limpiar
                    </button>

                    <a href="${pageContext.request.contextPath}/clientes"
                       class="btn btn-primary">
                        <i class="fa-solid fa-arrow-left"></i>
                        Volver
                    </a>

                </div>

            </form>
        </div>

        <br>

        <!-- TABLA DE CLIENTES -->
        <div class="tabla">

            <h4>
                <i class="fa-solid fa-users"></i>
                Clientes Registrados
            </h4>

            <div class="table-responsive">
                <table class="table table-striped table-hover align-middle">

                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Nombres</th>
                            <th>Apellidos</th>
                            <th>Cédula</th>
                            <th>Teléfono</th>
                            <th>Correo</th>
                            <th>Dirección</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>

                    <tbody>

                        <%
                            List<Cliente> clientes =
                                (List<Cliente>) request.getAttribute("clientes");

                            if (clientes != null && !clientes.isEmpty()) {

                                int contador = 1;

                                for (Cliente cliente : clientes) {
                        %>

                        <tr data-id="<%= cliente.getIdCliente() %>">
                            <td><%= contador++ %></td>
                            <td><%= cliente.getNombres() %></td>
                            <td><%= cliente.getApellidos() %></td>
                            <td><%= cliente.getCedula() == null ? "" : cliente.getCedula() %></td>
                            <td><%= cliente.getTelefono() %></td>
                            <td><%= cliente.getCorreo() %></td>
                            <td><%= cliente.getDireccion() == null ? "" : cliente.getDireccion() %></td>

                            <td>
                                <div class="d-flex gap-2">

                                    <button type="button"
                                            class="btn btn-sm btn-primary editar">
                                        <i class="fa-solid fa-pen-to-square"></i>
                                        Editar
                                    </button>

                                    <form method="POST"
                                          action="${pageContext.request.contextPath}/clientes"
                                          onsubmit="return confirm('¿Está seguro de eliminar este cliente?');">

                                        <input type="hidden" name="accion" value="eliminar">
                                        <input type="hidden" name="idCliente"
                                               value="<%= cliente.getIdCliente() %>">

                                        <button type="submit" class="btn btn-sm btn-danger">
                                            <i class="fa-solid fa-trash"></i>
                                            Eliminar
                                        </button>

                                    </form>

                                </div>
                            </td>
                        </tr>

                        <%
                                }

                            } else {
                        %>

                        <tr>
                            <td colspan="8" style="text-align:center;color:gray;">
                                No hay clientes registrados.
                            </td>
                        </tr>

                        <%
                            }
                        %>

                    </tbody>
                </table>
            </div>
        </div>

        <!-- PIE DE PÁGINA -->
        <div class="footer">
            <p>
                © 2025 Eléctricos de Nariño | Sistema de Gestión de Reparaciones
            </p>
        </div>

    </div>

    <!-- FUNCIONES DE LA INTERFAZ -->
    <script>

        const formulario = document.getElementById("formCliente");

        document.querySelectorAll(".editar").forEach(function(boton) {

            boton.addEventListener("click", function() {

                const fila = this.closest("tr");
                const celdas = fila.querySelectorAll("td");

                document.getElementById("idCliente").value = fila.dataset.id;
                document.getElementById("nombres").value = celdas[1].textContent.trim();
                document.getElementById("apellidos").value = celdas[2].textContent.trim();
                document.getElementById("cedula").value = celdas[3].textContent.trim();
                document.getElementById("telefono").value = celdas[4].textContent.trim();
                document.getElementById("correo").value = celdas[5].textContent.trim();
                document.getElementById("direccion").value = celdas[6].textContent.trim();

                document.getElementById("accion").value = "editar";
                document.getElementById("guardar").innerHTML =
                    '<i class="fa-solid fa-pen-to-square"></i> Actualizar Cliente';

                formulario.scrollIntoView({ behavior: "smooth" });
            });

        });

        document.getElementById("limpiar").addEventListener("click", function() {

            document.getElementById("accion").value = "registrar";
            document.getElementById("idCliente").value = "";

            document.getElementById("guardar").innerHTML =
                '<i class="fa-solid fa-floppy-disk"></i> Guardar Cliente';
        });

        document.getElementById("cerrar").addEventListener("click", function() {

            if (confirm("¿Desea cerrar sesión?")) {
                window.location.href = "${pageContext.request.contextPath}/";
            }

        });

    </script>

</body>
</html>
