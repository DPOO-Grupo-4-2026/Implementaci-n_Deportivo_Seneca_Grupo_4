package uniandes.dpoo.cds.modelo.administracion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uniandes.dpoo.cds.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.cds.excepciones.CupoExcedidoException;
import uniandes.dpoo.cds.excepciones.JugadorNoHabilitadoException;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.competencias.Competencia;
import uniandes.dpoo.cds.modelo.conjunto.Categoria;
import uniandes.dpoo.cds.modelo.conjunto.DeporteConjunto;
import uniandes.dpoo.cds.modelo.conjunto.Equipo;
import uniandes.dpoo.cds.modelo.conjunto.Partido;
import uniandes.dpoo.cds.modelo.conjunto.PartidoInterno;
import uniandes.dpoo.cds.modelo.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.cds.modelo.individuales.DisciplinaIndividual;
import uniandes.dpoo.cds.modelo.individuales.Instalacion;
import uniandes.dpoo.cds.modelo.tienda.MovimientoInventario;
import uniandes.dpoo.cds.modelo.tienda.Producto;
import uniandes.dpoo.cds.modelo.tienda.SugerenciaProducto;
import uniandes.dpoo.cds.modelo.tienda.UbicacionInventario;
import uniandes.dpoo.cds.modelo.tienda.Venta;
import uniandes.dpoo.cds.modelo.usuarios.Administrador;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;
import uniandes.dpoo.cds.modelo.usuarios.Usuario;

/**
 * Raiz del estado de la aplicacion: de aqui cuelga todo lo que se guarda y se carga.
 */
public class Club
{
    private String nombre;
    private LocalTime horaApertura;
    private LocalTime horaCierre;
    private int minEntrenadoresPorFranja;
    private int minFisioterapeutasPorFranja;
    private List<Usuario> usuarios;
    private List<DeporteConjunto> deportes;
    private List<Categoria> categorias;
    private List<Equipo> equipos;
    private List<Partido> partidos;
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
        this.partidos = new ArrayList<Partido>();
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

    public List<Partido> getPartidos()
    {
        return partidos;
    }

    public void agregarPartido(Partido partido)
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

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Devuelve el usuario si el login existe, esta activo y la clave coincide.
     *
     * Reglas: RN-01
     *
     * Por que asi: Un solo mensaje para login inexistente, usuario inactivo o clave errada: no revela que logins existen.
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
     * Devuelve el usuario con ese login, o null.
     *
     * Reglas: RN-01
     *
     * Por que asi: Busqueda lineal: con decenas de usuarios no justifica un Map.
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
     * Crea un equipo. Recibe un Administrador: el tipo del parametro es el que impide que otro rol lo haga.
     *
     * Verifica:
     * - Que no exista otro equipo con ese nombre.
     * - Que el cupo sea positivo.
     *
     * Reglas: RN-02, RN-03, RN-35
     *
     * Por que asi: El parametro Administrador es el control de permisos: el compilador no deja pasar a otro rol.
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
     * Desactiva el equipo sin borrar su historia.
     *
     * Reglas: RN-35
     *
     * Por que asi: Baja logica: los partidos y estadisticas historicos siguen apuntando al equipo.
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
     * Desactiva el perfil del jugador sin borrar su historia.
     *
     * Reglas: RN-35
     *
     * Por que asi: Baja logica: las estadisticas y el ranking del jugador se conservan.
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
     * Crea un partido interno y lo deja en la lista de partidos.
     *
     * Verifica:
     * - Que los dos equipos sean distintos, del mismo deporte y de la misma categoria.
     * - Que cada uno alcance su minimo de habilitados.
     *
     * Reglas: RN-08, RN-09
     *
     * Por que asi: Club coordina; cada equipo responde por si mismo si puede jugar.
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
        for (Equipo equipo : new Equipo[] { local, visitante })
        {
            if (!equipo.puedeDisputarPartido())
            {
                throw new JugadorNoHabilitadoException("RN-09", equipo.getNombre() + " tiene " + equipo.contarHabilitados()
                        + " habilitados y necesita " + equipo.getDeporte().getMinJugadoresHabilitados());
            }
        }
        PartidoInterno partido = new PartidoInterno(id, fecha, local, visitante);
        agregarPartido(partido);
        return partido;
    }

    /**
     * Dice si el usuario no tiene en ese momento una reserva en una instalacion que prohibe bebida caliente.
     *
     * Reglas: RN-32
     */
    public boolean puedeRecibirBebidaCaliente(Usuario usuario, LocalDateTime momento)
    {
        throw new UnsupportedOperationException("Pendiente [RN-32]: Club.puedeRecibirBebidaCaliente");
    }

    /**
     * Cierra la venta, descuenta el inventario de la ubicacion y la deja en el historial.
     *
     * Que debe verificar:
     * - Que el comprador este activo y no sea administrador.
     * - Que la ubicacion sea del tipo de la venta.
     * - Que no lleve bebida caliente si el comprador esta en padel o mesa techada.
     * - Que haya existencias de cada linea, antes de cerrar la venta.
     *
     * Reglas: RN-32, RN-34
     */
    public void registrarVenta(Venta venta, UbicacionInventario ubicacion, int puntosARedimir) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-32, RN-34]: Club.registrarVenta");
    }

    /**
     * Traslada unidades entre ubicaciones y deja el MovimientoInventario.
     *
     * Que debe verificar:
     * - Que el origen tenga suficientes unidades.
     *
     * Reglas: RN-35
     */
    public void moverProducto(Administrador administrador, Producto producto, UbicacionInventario origen, UbicacionInventario destino, int cantidad) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-35]: Club.moverProducto");
    }

    /**
     * Ingresa unidades nuevas a una ubicacion y deja el MovimientoInventario.
     *
     * Reglas: RN-35
     */
    public void reabastecer(Administrador administrador, Producto producto, UbicacionInventario destino, int cantidad) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-35]: Club.reabastecer");
    }

    /**
     * Dice si a esa hora hay al menos el minimo de entrenadores y de fisioterapeutas con turno.
     *
     * Reglas: RN-37
     */
    public boolean hayCobertura(DayOfWeek dia, LocalTime hora)
    {
        throw new UnsupportedOperationException("Pendiente [RN-37]: Club.hayCobertura");
    }

    /**
     * Aprueba o rechaza la solicitud. Si aprueba, el turno pasa al reemplazo.
     *
     * Que debe verificar:
     * - Que la solicitud este pendiente.
     * - Antes de aprobar: revisar la cobertura cada 30 minutos en toda la franja, contando el turno como del reemplazo, sin modificarlo.
     *
     * Reglas: RN-37
     */
    public void resolverCambioTurno(Administrador administrador, SolicitudCambioTurno solicitud, boolean aprobar) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-37]: Club.resolverCambioTurno");
    }
}
