# DOCUMENTACIÓN DEL DESARROLLO Y PRUEBAS DE SOFTWARE

**Evidencia:** GA7-220501096-AA2-EV02. Módulos de software codificados y probados.

**Proyecto:** Sistema Eléctricos de Nariño  
**Aprendiz:** James Michael Ordoñez Zambrano  
**Programa:** Análisis y Desarrollo de Software (ADSO)  
**Versión:** 1.0  
**Fecha:** 8 de noviembre de 2026

---

## 1. INTRODUCCIÓN

En el desarrollo de esta evidencia se realizó la codificación e implementación del módulo de gestión de clientes del proyecto Sistema Eléctricos de Nariño. El propósito es mejorar el proceso de registro y administración de la información de los clientes mediante una aplicación web conectada a una base de datos.

Para su desarrollo se utilizaron tecnologías como Java, JSP, Servlets, Maven y MySQL. También se implementaron los métodos HTTP GET y POST para permitir la consulta y el procesamiento de información desde los formularios de la aplicación.

Adicionalmente, se utilizó Git y GitHub para llevar el control de versiones y almacenar el código fuente del proyecto.

## 2. OBJETIVO

Desarrollar y probar un módulo web que permita administrar los datos de los clientes de Eléctricos de Nariño, implementando formularios, procesamiento mediante Servlets, conexión con MySQL y control de versiones.

## 3. TECNOLOGÍAS Y HERRAMIENTAS UTILIZADAS

| Tecnología o herramienta | Utilización |
|---|---|
| Java 21 | Lenguaje de programación utilizado para la lógica del sistema. |
| JSP | Generación de la interfaz dinámica de clientes. |
| Servlets | Procesamiento de solicitudes HTTP GET y POST. |
| HTML | Estructura de los formularios y tablas. |
| CSS | Diseño y presentación de la interfaz. |
| MySQL | Almacenamiento de la información de los clientes. |
| JDBC | Conexión entre Java y MySQL. |
| Maven | Administración de dependencias y construcción del proyecto WAR. |
| Apache Tomcat 10.1 | Servidor utilizado para ejecutar la aplicación web. |
| Git | Control local de versiones. |
| GitHub | Almacenamiento remoto del código fuente. |
| Visual Studio Code | Entorno de trabajo y edición del proyecto. |

## 4. ESTRUCTURA DEL PROYECTO

El proyecto web se organizó mediante la siguiente estructura:

- `pom.xml`: configuración de Maven y dependencias.
- `src/main/java`: contiene las clases Java.
  - `conexion`: conexión con la base de datos.
  - `modelo`: clase Cliente.
  - `dao`: operaciones de acceso a datos.
  - `servlet`: procesamiento de solicitudes.
- `src/main/webapp`: contiene los recursos de la aplicación.
  - `clientes.jsp`: interfaz de gestión de clientes.
  - `clientes/clientes.css`: estilos de la interfaz.
  - `WEB-INF`: configuración de la aplicación.

## 5. IMPLEMENTACIÓN DEL MÓDULO DE CLIENTES

Se implementó un módulo que permite realizar las operaciones principales de administración de clientes.

Los datos gestionados son:

- Identificación del cliente.
- Nombres.
- Apellidos.
- Cédula.
- Teléfono.
- Correo electrónico.
- Dirección.

La información se almacena en la base de datos MySQL, dentro de la tabla `cliente` de la base de datos `electricos_de_narino`.

### 5.1. Registro de clientes

Se desarrolló un formulario que permite ingresar los datos de un nuevo cliente. Al enviar la información, el Servlet recibe los valores mediante POST y utiliza la clase DAO para almacenarlos en MySQL.

### 5.2. Consulta de clientes

Se implementó el método GET para consultar los registros almacenados en la base de datos y mostrarlos en una tabla dentro de la interfaz JSP.

### 5.3. Actualización de clientes

Se incorporó la opción de editar los datos de un cliente existente. El formulario carga la información seleccionada y permite modificarla y guardar los cambios en la base de datos.

### 5.4. Eliminación de clientes

Se agregó la opción de eliminar registros, solicitando confirmación antes de realizar la operación. El Servlet procesa la solicitud y el DAO ejecuta la eliminación en MySQL.

## 6. IMPLEMENTACIÓN DE LOS MÉTODOS GET Y POST

Se utilizaron los siguientes métodos HTTP:

**GET:** permite solicitar información al servidor, consultar los clientes registrados y mostrar los resultados en la página JSP.

**POST:** permite enviar información desde los formularios para registrar, actualizar o eliminar clientes.

Esta separación permite organizar el procesamiento de las solicitudes y las operaciones realizadas sobre la información.

## 7. CONEXIÓN CON LA BASE DE DATOS

La aplicación se conectó a MySQL mediante JDBC y el controlador MySQL Connector/J.

Se creó la clase `ConexionBD.java`, encargada de establecer la conexión con la base de datos `electricos_de_narino`.

También se implementó la clase `ClienteDAO.java`, que contiene las operaciones necesarias para registrar, consultar, actualizar y eliminar clientes mediante instrucciones SQL.

## 8. PRUEBAS REALIZADAS

Se realizaron pruebas funcionales directamente desde la aplicación web ejecutada en Apache Tomcat.

| Prueba | Procedimiento | Resultado obtenido |
|---|---|---|
| Registro | Ingresar los datos de un cliente y guardar el formulario. | El cliente se registra correctamente. |
| Consulta | Abrir el módulo de clientes y consultar los registros. | Se muestran los clientes almacenados. |
| Actualización | Seleccionar un cliente, modificar sus datos y guardar. | Los cambios se reflejan correctamente. |
| Eliminación | Seleccionar un registro de prueba y confirmar su eliminación. | El registro desaparece de la tabla. |
| Conexión | Consultar y modificar información desde la aplicación. | Se comprueba la comunicación con MySQL. |

**Resultado general:** las cuatro operaciones del CRUD fueron ejecutadas satisfactoriamente.

## 9. CONTROL DE VERSIONES

Para administrar las versiones del código fuente se utilizó Git.

Se creó un repositorio local mediante `git init`, se configuró el archivo `.gitignore` para excluir archivos temporales y generados por Maven, y se realizó el primer registro de cambios mediante un commit.

Posteriormente, el proyecto se publicó en GitHub para facilitar la consulta del código fuente y permitir su revisión.

**Repositorio del proyecto:**

https://github.com/ordnez/web-electricos

## 10. CONCLUSIONES

El desarrollo de esta evidencia permitió implementar y comprobar el funcionamiento del módulo de gestión de clientes del sistema Eléctricos de Nariño.

Mediante Java, JSP y Servlets se logró conectar la interfaz web con la base de datos MySQL, permitiendo realizar las operaciones de registro, consulta, actualización y eliminación de información.

También se comprobó la utilidad de los métodos GET y POST para el intercambio de información entre el navegador y el servidor.

Finalmente, la utilización de Maven facilitó la construcción del proyecto y GitHub permitió almacenar y organizar el código fuente mediante el control de versiones.

Como resultado, se obtuvo un módulo funcional que constituye una base para continuar desarrollando los demás componentes del sistema.