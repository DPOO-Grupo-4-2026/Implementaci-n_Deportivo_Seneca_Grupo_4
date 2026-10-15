package uniandes.dpoo.proyecto1.administracion;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.competencias.Competencia;
import uniandes.dpoo.proyecto1.competencias.PartidoCompetencia;
import uniandes.dpoo.proyecto1.conjunto.Categoria;
import uniandes.dpoo.proyecto1.conjunto.DeporteConjunto;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaPartido;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.conjunto.PartidoInterno;
import uniandes.dpoo.proyecto1.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.CoberturaInsuficienteException;
import uniandes.dpoo.proyecto1.excepciones.CupoExcedidoException;
import uniandes.dpoo.proyecto1.excepciones.JugadorNoHabilitadoException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.excepciones.VentaRechazadaException;
import uniandes.dpoo.proyecto1.individuales.DisciplinaIndividual;
import uniandes.dpoo.proyecto1.individuales.Instalacion;
import uniandes.dpoo.proyecto1.individuales.Reserva;
import uniandes.dpoo.proyecto1.tienda.Existencia;
import uniandes.dpoo.proyecto1.tienda.LineaVenta;
import uniandes.dpoo.proyecto1.tienda.MovimientoInventario;
import uniandes.dpoo.proyecto1.tienda.PagoMensualidad;
import uniandes.dpoo.proyecto1.tienda.Producto;
import uniandes.dpoo.proyecto1.tienda.SugerenciaProducto;
import uniandes.dpoo.proyecto1.tienda.TipoMovimiento;
import uniandes.dpoo.proyecto1.tienda.UbicacionInventario;
import uniandes.dpoo.proyecto1.tienda.Venta;
import uniandes.dpoo.proyecto1.usuarios.Administrador;
import uniandes.dpoo.proyecto1.usuarios.Empleado;
import uniandes.dpoo.proyecto1.usuarios.Entrenador;
import uniandes.dpoo.proyecto1.usuarios.Fisioterapeuta;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Raíz del estado de la aplicación: de aquí cuelga todo lo que se guarda y se carga.
 */
public class Club implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String nombre;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private int minEntrenadoresPorFranja;
    private int minFisioterapeutasPorFranja;
    private List<Usuario> usuarios;
    private List<DeporteConjunto> deportes;
    private List<Categoria> categorias;
    private List<Equipo> equipos;
    private List<PartidoInterno> partidos;
    private List<Competencia> competencias;
    private List<DisciplinaIndividual> disciplinas;
    private List<Instalacion> instalaciones;
    private List<SesionEntrenamiento> sesiones;
    private List<Producto> productos;
    private List<UbicacionInventario> ubicaciones;
    private List<Venta> ventas;
    private List<MovimientoInventario> movimientosInventario;
    private List<SugerenciaProducto> sugerencias;
    private List<Turno> turnos;
    private List<SolicitudCambioTurno> solicitudes;
    private List<PagoMensualidad> pagos;

    public Club(String nombre, LocalTime horaApertura, LocalTime horaCierre)
    {
        this.nombre = nombre;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.minEntrenadoresPorFranja = 2;
        this.minFisioterapeutasPorFranja = 1;
        this.usuarios = new ArrayList<Usuario>();
        this.deportes = new ArrayList<DeporteConjunto>();
        this.categorias = new ArrayList<Categoria>();
        this.equipos = new ArrayList<Equipo>();
        this.partidos = new ArrayList<PartidoInterno>();
        this.competencias = new ArrayList<Competencia>();
        this.disciplinas = new ArrayList<DisciplinaIndividual>();
        this.instalaciones = new ArrayList<Instalacion>();
        this.sesiones = new ArrayList<SesionEntrenamiento>();
        this.productos = new ArrayList<Producto>();
        this.ubicaciones = new ArrayList<UbicacionInventario>();
        this.ventas = new ArrayList<Venta>();
        this.movimientosInventario = new ArrayList<MovimientoInventario>();
        this.sugerencias = new ArrayList<SugerenciaProducto>();
        this.turnos = new ArrayList<Turno>();
        this.solicitudes = new ArrayList<SolicitudCambioTurno>();
        this.pagos = new ArrayList<PagoMensualidad>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public LocalTime getHoraApertura()
    {
        return horaApertura;
    }

    public LocalTime getHoraCierre()
    {
        return horaCierre;
    }

    public int getMinEntrenadoresPorFranja()
    {
        return minEntrenadoresPorFranja;
    }

    public int getMinFisioterapeutasPorFranja()
    {
        return minFisioterapeutasPorFranja;
    }

    public List<Usuario> getUsuarios()
    {
        return usuarios;
    }

    public void agregarUsuario(Usuario usuario)
    {
        usuarios.add(usuario);
    }

    public List<DeporteConjunto> getDeportes()
    {
        return deportes;
    }

    public void agregarDeporte(DeporteConjunto deporte)
    {
        deportes.add(deporte);
    }

    public List<Categoria> getCategorias()
    {
        return categorias;
    }

    public void agregarCategoria(Categoria categoria)
    {
        categorias.add(categoria);
    }

    public List<Equipo> getEquipos()
    {
        return equipos;
    }

    public void agregarEquipo(Equipo equipo)
    {
        equipos.add(equipo);
    }

    public List<PartidoInterno> getPartidos()
    {
        return partidos;
    }

    public void agregarPartido(PartidoInterno partido)
    {
        partidos.add(partido);
    }

    public List<Competencia> getCompetencias()
    {
        return competencias;
    }

    public void agregarCompetencia(Competencia competencia)
    {
        competencias.add(competencia);
    }

    public List<DisciplinaIndividual> getDisciplinas()
    {
        return disciplinas;
    }

    public void agregarDisciplina(DisciplinaIndividual disciplina)
    {
        disciplinas.add(disciplina);
    }

    public List<Instalacion> getInstalaciones()
    {
        return instalaciones;
    }

    public void agregarInstalacion(Instalacion instalacion)
    {
        instalaciones.add(instalacion);
    }

    public List<SesionEntrenamiento> getSesiones()
    {
        return sesiones;
    }

    public void agregarSesion(SesionEntrenamiento sesion)
    {
        sesiones.add(sesion);
    }

    public List<Producto> getProductos()
    {
        return productos;
    }

    public void agregarProducto(Producto producto)
    {
        productos.add(producto);
    }

    public List<UbicacionInventario> getUbicaciones()
    {
        return ubicaciones;
    }

    public void agregarUbicacion(UbicacionInventario ubicacion)
    {
        ubicaciones.add(ubicacion);
    }

    public List<Venta> getVentas()
    {
        return ventas;
    }

    public void agregarVenta(Venta venta)
    {
        ventas.add(venta);
    }

    public List<MovimientoInventario> getMovimientosInventario()
    {
        return movimientosInventario;
    }

    public void agregarMovimientoInventario(MovimientoInventario movimientoInventario)
    {
        movimientosInventario.add(movimientoInventario);
    }

    public List<SugerenciaProducto> getSugerencias()
    {
        return sugerencias;
    }

    public void agregarSugerencia(SugerenciaProducto sugerencia)
    {
        sugerencias.add(sugerencia);
    }

    public List<Turno> getTurnos()
    {
        return turnos;
    }

    public void agregarTurno(Turno turno)
    {
        turnos.add(turno);
    }

    public List<SolicitudCambioTurno> getSolicitudes()
    {
        return solicitudes;
    }

    public void agregarSolicitud(SolicitudCambioTurno solicitud)
    {
        solicitudes.add(solicitud);
    }

    public List<PagoMensualidad> getPagos()
    {
        return pagos;
    }

    public void agregarPago(PagoMensualidad pago)
    {
        pagos.add(pago);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-01, RF-E-01, RF-A-01 - Autenticarse
     *
     * Alguien entra al sistema con login y clave. Si el login no existe, el usuario está inactivo o la
     * clave no coincide, se rechaza con el mismo mensaje en los tres casos, para no decirle a nadie que
     * logins existen.
     *
     * Reglas: RN-01
     */
    public Usuario autenticar(String login, String password) throws ReglaDominioException
    {
        Usuario usuario = buscarUsuario(login);
        if (usuario == null || !usuario.isActivo() || !usuario.validarPassword(password))
        {
            throw new AccionNoPermitidaException("RN-01", "Login o clave incorrectos");
        }
        return usuario;
    }

    /**
     * RF-S-01, RF-E-01, RF-A-01 - Autenticarse
     *
     * Busca un usuario por su login entre todos los usuarios del club.
     *
     * Reglas: RN-01
     */
    public Usuario buscarUsuario(String login)
    {
        for (Usuario usuario : usuarios)
        {
            if (usuario.getLogin().equals(login))
            {
                return usuario;
            }
        }
        return null;
    }

    /**
     * RF-A-02 - Crear y dar de baja equipos y jugadores
     *
     * El administrador crea un equipo nuevo. No puede haber dos equipos con el mismo nombre. Que el
     * parametro sea Administrador es lo que impide que un socio o un empleado lo haga.
     *
     * Reglas: RN-02, RN-03, RN-35
     */
    public Equipo crearEquipo(Administrador administrador, String nombre, int cupoMaximo, DeporteConjunto deporte, Categoria categoria) throws ReglaDominioException
    {
        for (Equipo existente : equipos)
        {
            if (existente.getNombre().equalsIgnoreCase(nombre))
            {
                throw new AccionNoPermitidaException("RN-02", "Ya existe un equipo llamado " + nombre);
            }
        }
        if (cupoMaximo <= 0)
        {
            throw new CupoExcedidoException("RN-03", "El cupo de un equipo debe ser positivo");
        }
        Equipo equipo = new Equipo(nombre, cupoMaximo, deporte, categoria);
        agregarEquipo(equipo);
        return equipo;
    }

    /**
     * RF-A-02 - Crear y dar de baja equipos y jugadores
     *
     * El administrador le crea el perfil de jugador a un usuario del club, por ejemplo a un socio nuevo.
     *
     * Reglas: RN-35, RN-38
     */
    public PerfilJugador crearPerfilJugador(Administrador administrador, Usuario usuario) throws ReglaDominioException
    {
        return usuario.crearPerfilJugador();
    }

    /**
     * RF-A-02 - Crear y dar de baja equipos y jugadores
     *
     * El administrador da de baja un equipo. No se borra: queda inactivo, así sus partidos viejos siguen
     * existiendo.
     *
     * Reglas: RN-35
     */
    public void darDeBajaEquipo(Administrador administrador, Equipo equipo) throws ReglaDominioException
    {
        if (!equipo.isActivo())
        {
            throw new AccionNoPermitidaException("RN-35", "El equipo " + equipo.getNombre() + " ya estaba dado de baja");
        }
        equipo.setActivo(false);
    }

    /**
     * RF-A-02 - Crear y dar de baja equipos y jugadores
     *
     * El administrador da de baja a un jugador. Tampoco se borra: deja de poder jugar, pero su historia se
     * conserva.
     *
     * Reglas: RN-35
     */
    public void darDeBajaJugador(Administrador administrador, PerfilJugador jugador) throws ReglaDominioException
    {
        if (!jugador.isActivo())
        {
            throw new AccionNoPermitidaException("RN-35", "El jugador " + jugador.getUsuario().getNombre() + " ya estaba dado de baja");
        }
        jugador.setActivo(false);
    }

    /**
     * RF-E-05, RF-S-04 - Registrar el partido y sus estadísticas / Consultar el calendario de partidos
     *
     * Se programa un partido entre dos equipos del club. Tienen que ser del mismo deporte y categoría, y
     * cada uno tiene que tener el mínimo de jugadores habilitados.
     *
     * Reglas: RN-08, RN-09
     */
    public PartidoInterno programarPartidoInterno(String id, Equipo local, Equipo visitante, LocalDate fecha) throws ReglaDominioException
    {
        if (local == visitante)
        {
            throw new AccionNoPermitidaException("RN-08", "Un equipo no puede jugar contra si mismo");
        }
        if (local.getDeporte() != visitante.getDeporte() || local.getCategoria() != visitante.getCategoria())
        {
            throw new AccionNoPermitidaException("RN-08", "Los equipos deben ser del mismo deporte y categoria");
        }
        int minimo = local.getDeporte().getMinJugadoresHabilitados();
        if (!local.puedeDisputarPartido())
        {
            throw new JugadorNoHabilitadoException("RN-09", local.getNombre() + " tiene " + local.contarHabilitados()
                    + " habilitados y necesita " + minimo);
        }
        if (!visitante.puedeDisputarPartido())
        {
            throw new JugadorNoHabilitadoException("RN-09", visitante.getNombre() + " tiene " + visitante.contarHabilitados()
                    + " habilitados y necesita " + minimo);
        }
        PartidoInterno partido = new PartidoInterno(id, fecha, local, visitante);
        agregarPartido(partido);
        return partido;
    }

    /**
     * RF-S-04 - Consultar el calendario de partidos
     *
     * El socio quiere ver los partidos de sus equipos: los internos y los de competencia, jugados o por
     * jugar.
     *
     * Reglas: RN-08
     */
    public List<Partido> consultarCalendario(PerfilJugador jugador)
    {
        List<Partido> calendario = new ArrayList<Partido>();
        for (InscripcionEquipo inscripcion : jugador.consultarEquipos())
        {
            Equipo equipo = inscripcion.getEquipo();
            for (PartidoInterno partido : partidos)
            {
                if (partido.getLocal() == equipo || partido.getVisitante() == equipo)
                {
                    calendario.add(partido);
                }
            }
            for (Competencia competencia : competencias)
            {
                for (PartidoCompetencia partido : competencia.getPartidos())
                {
                    if (partido.getEquipo() == equipo)
                    {
                        calendario.add(partido);
                    }
                }
            }
        }
        return calendario;
    }

    /**
     * RF-S-05 - Consultar mis estadísticas
     *
     * El socio quiere ver sus estadísticas. Buscamos su actuación en todos los partidos del club y de las
     * competencias.
     *
     * Reglas: RN-10, RN-21
     */
    public List<EstadisticaPartido> consultarEstadisticas(PerfilJugador jugador)
    {
        List<EstadisticaPartido> resultado = new ArrayList<EstadisticaPartido>();
        for (Partido partido : partidos)
        {
            for (EstadisticaPartido estadistica : partido.getEstadisticas())
            {
                if (estadistica.getJugador() == jugador)
                {
                    resultado.add(estadistica);
                }
            }
        }
        for (Competencia competencia : competencias)
        {
            for (PartidoCompetencia partido : competencia.getPartidos())
            {
                for (EstadisticaPartido estadistica : partido.getEstadisticas())
                {
                    if (estadistica.getJugador() == jugador)
                    {
                        resultado.add(estadistica);
                    }
                }
            }
        }
        return resultado;
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * Responde si en ese momento el usuario está usando una instalación donde no se permite bebida
     * caliente.
     *
     * Reglas: RN-32
     */
    public boolean puedeRecibirBebidaCaliente(Usuario usuario, LocalDateTime momento)
    {
        for (DisciplinaIndividual disciplina : disciplinas)
        {
            for (Reserva reserva : disciplina.getReservas())
            {
                boolean participa = reserva.getSolicitante() == usuario
                        || (usuario.getPerfilJugador() != null && reserva.getParticipantes().contains(usuario.getPerfilJugador()));
                if (participa && reserva.estaEnCurso(momento) && reserva.getInstalacion().prohibeBebidaCaliente())
                {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * RF-S-12, RF-S-13 - Comprar en la tienda / Comprar en la cafetería
     *
     * Registra una compra: revisa la bebida caliente y las existencias, cierra la venta y saca los
     * productos del inventario.
     *
     * Reglas: RN-32, RN-34
     */
    public void registrarVenta(Venta venta, UbicacionInventario ubicacion, int puntosARedimir) throws ReglaDominioException
    {
        Usuario comprador = venta.getComprador();
        if (!comprador.isActivo())
        {
            throw new VentaRechazadaException("RN-34", comprador.getNombre() + " esta dado de baja");
        }
        for (LineaVenta linea : venta.getLineas())
        {
            Producto producto = linea.getProducto();
            if (producto.esBebidaCaliente() && !puedeRecibirBebidaCaliente(comprador, venta.getFecha()))
            {
                throw new VentaRechazadaException("RN-32", comprador.getNombre() + " esta en una instalacion donde no se permite bebida caliente");
            }
            Existencia existencia = ubicacion.buscarExistencia(producto);
            if (existencia == null || existencia.getCantidadDisponible() < linea.getCantidad())
            {
                throw new VentaRechazadaException("RN-34", "No hay suficiente " + producto.getNombre() + " en " + ubicacion.getNombre());
            }
        }

        venta.cerrar(puntosARedimir);
        for (LineaVenta linea : venta.getLineas())
        {
            ubicacion.retirar(linea.getProducto(), linea.getCantidad());
            movimientosInventario.add(new MovimientoInventario(venta.getFecha().toLocalDate(), linea.getProducto(), linea.getCantidad(),
                    TipoMovimiento.VENTA, ubicacion, null));
        }
        ventas.add(venta);
    }

    /**
     * RF-A-05 - Mover y reabastecer artículos
     *
     * El administrador pasa productos de una ubicación a otra, por ejemplo de la bodega a la tienda.
     *
     * Reglas: RN-35
     */
    public void moverProducto(Administrador administrador, Producto producto, UbicacionInventario origen, UbicacionInventario destino, int cantidad, LocalDate fecha) throws ReglaDominioException
    {
        if (cantidad <= 0)
        {
            throw new AccionNoPermitidaException("RN-35", "La cantidad tiene que ser mayor que cero");
        }
        if (origen == destino)
        {
            throw new AccionNoPermitidaException("RN-35", "El origen y el destino son el mismo lugar");
        }
        origen.retirar(producto, cantidad);
        destino.ingresar(producto, cantidad);
        movimientosInventario.add(new MovimientoInventario(fecha, producto, cantidad, TipoMovimiento.TRASLADO, origen, destino));
    }

    /**
     * RF-A-05 - Mover y reabastecer artículos
     *
     * Llega mercancía nueva y el administrador la ingresa en una ubicación.
     *
     * Reglas: RN-35
     */
    public void reabastecer(Administrador administrador, Producto producto, UbicacionInventario destino, int cantidad, LocalDate fecha) throws ReglaDominioException
    {
        if (cantidad <= 0)
        {
            throw new AccionNoPermitidaException("RN-35", "La cantidad tiene que ser mayor que cero");
        }
        destino.ingresar(producto, cantidad);
        movimientosInventario.add(new MovimientoInventario(fecha, producto, cantidad, TipoMovimiento.REABASTECIMIENTO, null, destino));
    }

    /**
     * RF-A-04 - Consultar el inventario
     *
     * El administrador ve qué hay en una ubicación y cuánto de cada producto.
     *
     * Reglas: RN-35
     */
    public List<Existencia> consultarInventario(UbicacionInventario ubicacion)
    {
        return ubicacion.getExistencias();
    }

    /**
     * RF-E-11 - Sugerir productos
     *
     * Un empleado sugiere un artículo o snack nuevo. Queda pendiente hasta que el administrador lo revise.
     *
     * Reglas: RN-36
     */
    public SugerenciaProducto registrarSugerencia(Empleado autor, String descripcion, LocalDate fecha) throws ReglaDominioException
    {
        if (descripcion == null || descripcion.trim().isEmpty())
        {
            throw new AccionNoPermitidaException("RN-36", "La sugerencia tiene que tener una descripcion");
        }
        SugerenciaProducto sugerencia = new SugerenciaProducto(descripcion, fecha, autor);
        sugerencias.add(sugerencia);
        return sugerencia;
    }

    /**
     * RF-A-08 - Administrar turnos
     *
     * Responde si a esa hora hay al menos 2 entrenadores y 1 fisioterapeuta trabajando.
     *
     * Reglas: RN-37
     */
    public boolean hayCobertura(DayOfWeek dia, LocalTime hora)
    {
        // Fuera del horario de atención no se exige personal
        if (hora.isBefore(horaApertura) || !hora.isBefore(horaCierre))
        {
            return true;
        }
        return hayCoberturaConCambio(dia, hora, null, null);
    }

    /**
     * RF-A-08, RF-A-09 - Administrar turnos / Aprobar o rechazar cambios de turno
     *
     * Cuenta cuántos entrenadores y fisioterapeutas trabajan a esa hora. Sirve para dos cosas: ver la
     * cobertura normal (sin ningún cambio) y ver qué pasaría si un turno pasara a otro empleado, sin
     * cambiarlo de verdad.
     *
     * Reglas: RN-37
     */
    private boolean hayCoberturaConCambio(DayOfWeek dia, LocalTime hora, Turno turnoCambiado, Empleado nuevoEmpleado)
    {
        int entrenadores = 0;
        int fisioterapeutas = 0;
        for (Turno turno : turnos)
        {
            if (turno.cubre(dia, hora))
            {
                Empleado quien = turno.getEmpleado();
                if (turno == turnoCambiado)
                {
                    quien = nuevoEmpleado;
                }
                if (quien.getRol().equals("Entrenador"))
                {
                    entrenadores++;
                }
                else if (quien.getRol().equals("Fisioterapeuta"))
                {
                    fisioterapeutas++;
                }
            }
        }
        return entrenadores >= minEntrenadoresPorFranja && fisioterapeutas >= minFisioterapeutasPorFranja;
    }

    /**
     * RF-E-10 - Solicitar un cambio de turno
     *
     * Un empleado pide ceder uno de sus turnos a otro empleado. Queda pendiente hasta que el administrador
     * decida.
     *
     * Reglas: RN-37
     */
    public SolicitudCambioTurno solicitarCambioTurno(Empleado solicitante, Turno turno, Empleado reemplazo, LocalDate fecha, String motivo) throws ReglaDominioException
    {
        if (turno.getEmpleado() != solicitante)
        {
            throw new AccionNoPermitidaException("RN-37", "Ese turno no es de " + solicitante.getNombre());
        }
        if (reemplazo == solicitante)
        {
            throw new AccionNoPermitidaException("RN-37", "El reemplazo tiene que ser otro empleado");
        }
        SolicitudCambioTurno solicitud = new SolicitudCambioTurno(turno, solicitante, reemplazo, fecha, motivo);
        solicitudes.add(solicitud);
        return solicitud;
    }

    /**
     * RF-A-09 - Aprobar o rechazar cambios de turno
     *
     * El administrador aprueba o rechaza un cambio de turno. Solo se aprueba si el club no se queda sin el
     * personal mínimo.
     *
     * Reglas: RN-37
     */
    public void resolverCambioTurno(Administrador administrador, SolicitudCambioTurno solicitud, boolean aprobar) throws ReglaDominioException
    {
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE)
        {
            throw new AccionNoPermitidaException("RN-37", "La solicitud ya fue resuelta");
        }
        if (!aprobar)
        {
            solicitud.setEstado(EstadoSolicitud.RECHAZADA);
            solicitud.setResueltaPor(administrador);
            return;
        }

        Turno turno = solicitud.getTurno();
        Empleado reemplazo = solicitud.getReemplazo();
        // El reemplazo no puede tener otro turno a la misma hora
        for (Turno otro : reemplazo.getTurnos())
        {
            if (otro.getDia() == turno.getDia() && otro.getHoraInicio().isBefore(turno.getHoraFin())
                    && turno.getHoraInicio().isBefore(otro.getHoraFin()))
            {
                throw new AccionNoPermitidaException("RN-37", reemplazo.getNombre() + " ya tiene un turno a esa hora");
            }
        }
        // Se revisa la cobertura cada 30 minutos, contando el turno como si ya fuera del reemplazo.
        // Nada se cambia hasta saber que el cambio se puede hacer.
        LocalTime hora = turno.getHoraInicio();
        while (hora.isBefore(turno.getHoraFin()))
        {
            if (!hayCoberturaConCambio(turno.getDia(), hora, turno, reemplazo))
            {
                throw new CoberturaInsuficienteException("RN-37", "Con el cambio, a las " + hora + " de ese dia no queda el personal minimo");
            }
            hora = hora.plusMinutes(30);
        }

        solicitud.getSolicitante().getTurnos().remove(turno);
        reemplazo.agregarTurno(turno);
        turno.setEmpleado(reemplazo);
        solicitud.setEstado(EstadoSolicitud.APROBADA);
        solicitud.setResueltaPor(administrador);
    }
}
