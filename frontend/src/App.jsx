
import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [clientes, setClientes] = useState([])
  const [nombre, setNombre] = useState('')
  const [apellido, setApellido] = useState('')
  const [cedula, setCedula] = useState('')
  const [telefono, setTelefono] = useState('')
  const [correo, setCorreo] = useState('')
  const [direccion, setDireccion] = useState('')
  const [mensaje, setMensaje] = useState('')
  const [cargando, setCargando] = useState(true)
  const [idEditar, setIdEditar] = useState(null)

  // Consultar los clientes almacenados en MySQL.
  const cargarClientes = async () => {
    try {
      const respuesta = await fetch('/api/clientes')

      if (!respuesta.ok) {
        throw new Error('No fue posible consultar los clientes.')
      }

      const datos = await respuesta.json()
      setClientes(datos)
    } catch (error) {
      setMensaje('Error al cargar los clientes: ' + error.message)
    } finally {
      setCargando(false)
    }
  }

  // Cargar los datos al abrir el módulo.
  useEffect(() => {
    cargarClientes()
  }, [])

  // Limpiar el formulario.
  const limpiarFormulario = () => {
    setNombre('')
    setApellido('')
    setCedula('')
    setTelefono('')
    setCorreo('')
    setDireccion('')
    setIdEditar(null)
  }

  // Registrar o actualizar un cliente.
  const guardarCliente = async (e) => {
    e.preventDefault()
    setMensaje('')

    const datos = new URLSearchParams({
      nombres: nombre,
      apellidos: apellido,
      cedula,
      telefono,
      correo,
      direccion
    })

    try {
      const editando = idEditar !== null
      const url = editando
        ? `/api/clientes?idCliente=${idEditar}`
        : '/api/clientes'

      const respuesta = await fetch(url, {
        method: editando ? 'PUT' : 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: datos.toString()
      })

      const resultado = await respuesta.json()

      if (!respuesta.ok) {
        throw new Error(resultado.error || 'No se pudo guardar el cliente.')
      }

      setMensaje(
        editando
          ? 'Cliente actualizado correctamente.'
          : 'Cliente registrado correctamente.'
      )

      limpiarFormulario()
      await cargarClientes()

    } catch (error) {
      setMensaje('Error: ' + error.message)
    }
  }

  // Cargar los datos del cliente seleccionado en el formulario.
  const editarCliente = (cliente) => {
    setIdEditar(cliente.idCliente)
    setNombre(cliente.nombres)
    setApellido(cliente.apellidos)
    setCedula(cliente.cedula)
    setTelefono(cliente.telefono)
    setCorreo(cliente.correo)
    setDireccion(cliente.direccion)
    setMensaje('Editando los datos del cliente seleccionado.')
  }

  // Eliminar un cliente de la base de datos.
  const eliminarCliente = async (idCliente) => {
    const confirmar = window.confirm(
      '¿Estás seguro de que deseas eliminar este cliente?'
    )

    if (!confirmar) return

    try {
      const respuesta = await fetch(
        `/api/clientes?idCliente=${idCliente}`,
        { method: 'DELETE' }
      )

      const resultado = await respuesta.json()

      if (!respuesta.ok) {
        throw new Error(resultado.error || 'No se pudo eliminar el cliente.')
      }

      if (idEditar === idCliente) {
        limpiarFormulario()
      }

      setMensaje('Cliente eliminado correctamente.')
      await cargarClientes()

    } catch (error) {
      setMensaje('Error al eliminar: ' + error.message)
    }
  }

  return (
    <div className="aplicacion">
      {/* Menú lateral del sistema */}
      <aside className="sidebar">
        <div>
          <div className="logo">
            <img src="/logo.png" alt="Logo Eléctricos de Nariño" />
            <h3>Eléctricos<br />de Nariño</h3>
          </div>

          <ul>
            <li><a href="#inicio">⌂ Inicio</a></li>
            <li className="activo"><a href="#clientes">♙ Clientes</a></li>
            <li><a href="#reparaciones">⚙ Reparaciones</a></li>
            <li><a href="#inventario">▣ Inventario</a></li>
            <li><a href="#ventas">▤ Ventas</a></li>
            <li><a href="#facturacion">▧ Facturación</a></li>
          </ul>
        </div>

        <button className="cerrar" type="button">
          Cerrar sesión
        </button>
      </aside>

      <main className="contenido">
        <header className="encabezado">
          <div className="iconos">
            <span>⚡ Sistema de administración</span>
          </div>
          <strong style={{ color: 'white' }}>Administrador</strong>
        </header>

        <section className="titulo">
          <h2>Gestión de Clientes</h2>
          <p>Administración de clientes del taller</p>
        </section>

        <section className="formulario">
          <h3>
            {idEditar !== null
              ? 'Actualizar datos del cliente'
              : 'Registrar nuevo cliente'}
          </h3>

          <form onSubmit={guardarCliente}>
            <div className="formulario-grid">
              <div className="campo">
                <label>Nombres</label>
                <input
                  className="form-control"
                  type="text"
                  value={nombre}
                  onChange={(e) => setNombre(e.target.value)}
                  required
                />
              </div>

              <div className="campo">
                <label>Apellidos</label>
                <input
                  className="form-control"
                  type="text"
                  value={apellido}
                  onChange={(e) => setApellido(e.target.value)}
                  required
                />
              </div>

              <div className="campo">
                <label>Cédula</label>
                <input
                  className="form-control"
                  type="text"
                  value={cedula}
                  onChange={(e) => setCedula(e.target.value)}
                  required
                />
              </div>

              <div className="campo">
                <label>Teléfono</label>
                <input
                  className="form-control"
                  type="tel"
                  value={telefono}
                  onChange={(e) => setTelefono(e.target.value)}
                  required
                />
              </div>

              <div className="campo">
                <label>Correo electrónico</label>
                <input
                  className="form-control"
                  type="email"
                  value={correo}
                  onChange={(e) => setCorreo(e.target.value)}
                  required
                />
              </div>

              <div className="campo">
                <label>Dirección</label>
                <input
                  className="form-control"
                  type="text"
                  value={direccion}
                  onChange={(e) => setDireccion(e.target.value)}
                  required
                />
              </div>
            </div>

            <div className="botones">
              <button className="btn-principal" type="submit">
                {idEditar !== null ? 'Guardar cambios' : 'Registrar cliente'}
              </button>

              {idEditar !== null && (
                <button
                  className="btn-secundario"
                  type="button"
                  onClick={limpiarFormulario}
                >
                  Cancelar edición
                </button>
              )}
            </div>
          </form>

          {mensaje && (
            <p role="status" style={{ color: '#08224f', marginTop: '15px' }}>
              {mensaje}
            </p>
          )}
        </section>

        <section className="tabla">
          <h3>Clientes registrados</h3>

          <p className="total-clientes">
            Total de clientes: {clientes.length}
          </p>

          {cargando ? (
            <p>Cargando clientes...</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Cédula</th>
                  <th>Teléfono</th>
                  <th>Correo</th>
                  <th>Dirección</th>
                  <th>Acciones</th>
                </tr>
              </thead>

              <tbody>
                {clientes.map((cliente) => (
                  <tr key={cliente.idCliente}>
                    <td>{cliente.nombres} {cliente.apellidos}</td>
                    <td>{cliente.cedula}</td>
                    <td>{cliente.telefono}</td>
                    <td>{cliente.correo}</td>
                    <td>{cliente.direccion}</td>
                    <td>
                      <button
                        className="btn-editar"
                        type="button"
                        onClick={() => editarCliente(cliente)}
                      >
                        Editar
                      </button>

                      <button
                        className="btn-eliminar"
                        type="button"
                        onClick={() => eliminarCliente(cliente.idCliente)}
                      >
                        Eliminar
                      </button>
                    </td>
                  </tr>
                ))}

                {clientes.length === 0 && (
                  <tr>
                    <td colSpan="6">
                      Todavía no hay clientes registrados.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          )}
        </section>
      </main>
    </div>
  )
}

export default App
