package uniandes.dpoo.proyecto1.competencias;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.conjunto.Categoria;
import uniandes.dpoo.proyecto1.conjunto.DeporteConjunto;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.CupoExcedidoException;
import uniandes.dpoo.proyecto1.excepciones.JugadorNoHabilitadoException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Circuito externo en el que participa el club. Guarda lo común de los dos formatos.
 */
public abstract class Competencia implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String entidadOrganizadora;
    private String temporada;
    private int nominaMinima;
    private DeporteConjunto deporte;
    private Categoria categoria;
    private List<InscripcionCompetencia> inscripciones;
    private List<PartidoCompetencia> partidos;
    private List<TablaPosiciones> tablas;

    public Competencia(String nombre, String entidadOrganizadora, String temporada, int nominaMinima, DeporteConjunto deporte, Categoria categoria)
    {
        this.nombre = nombre;
        this.entidadOrganizadora = entidadOrganizadora;
        this.temporada = temporada;
        this.nominaMinima = nominaMinima;
        this.deporte = deporte;
        this.categoria = categoria;
        this.inscripciones = new ArrayList<InscripcionCompetencia>();
        this.partidos = new ArrayList<PartidoCompetencia>();
        this.tablas = new ArrayList<TablaPosiciones>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getEntidadOrganizadora()
    {
        return entidadOrganizadora;
    }

    public String getTemporada()
    {
        return temporada;
    }

    public int getNominaMinima()
    {
        return nominaMinima;
    }

    public DeporteConjunto getDeporte()
    {
        return deporte;
    }

    public Categoria getCategoria()
    {
        return categoria;
    }

    public List<InscripcionCompetencia> getInscripciones()
    {
        return inscripciones;
    }

    public void agregarInscripcion(InscripcionCompetencia inscripcion)
    {
        inscripciones.add(inscripcion);
    }

    public List<PartidoCompetencia> getPartidos()
    {
        return partidos;
    }

    public void agregarPartido(PartidoCompetencia partido)
    {
        partidos.add(partido);
    }

    public List<TablaPosiciones> getTablas()
    {
        return tablas;
    }

    public void agregarTabla(TablaPosiciones tabla)
    {
        tablas.add(tabla);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-A-03, RF-E-08 - Crear competencias e inscribir equipos / Proponer la nómina de una competencia
     *
     * El administrador inscribe un equipo del club en la competencia, con la lista de jugadores que van a
     * jugar (la nómina). La nómina no es todo el equipo: son los que la competencia deja jugar.
     *
     * Reglas: RN-15, RN-16
     */
    public InscripcionCompetencia inscribirEquipo(Equipo equipo, List<PerfilJugador> nomina) throws ReglaDominioException
    {
        if (buscarInscripcion(equipo) != null)
        {
            throw new AccionNoPermitidaException("RN-15", equipo.getNombre() + " ya esta inscrito en " + nombre);
        }
        if (equipo.getDeporte() != deporte || equipo.getCategoria() != categoria)
        {
            throw new AccionNoPermitidaException("RN-15", nombre + " es de " + deporte.getNombre() + " " + categoria.getNombre());
        }
        if (nomina.size() < nominaMinima)
        {
            throw new CupoExcedidoException("RN-16", "La nomina tiene " + nomina.size() + " jugadores y el minimo es " + nominaMinima);
        }
        for (PerfilJugador jugador : nomina)
        {
            InscripcionEquipo enEquipo = jugador.buscarInscripcionEnDeporte(deporte);
            if (enEquipo == null || enEquipo.getEquipo() != equipo)
            {
                throw new JugadorNoHabilitadoException("RN-16", jugador.getUsuario().getNombre() + " no esta inscrito en " + equipo.getNombre());
            }
        }

        NominaCompetencia lista = new NominaCompetencia();
        for (PerfilJugador jugador : nomina)
        {
            lista.agregarRenglon(new RenglonNomina(jugador));
        }
        InscripcionCompetencia inscripcion = new InscripcionCompetencia(equipo, this, lista);
        inscripciones.add(inscripcion);
        return inscripcion;
    }

    /**
     * RF-E-08 - Proponer la nómina de una competencia
     *
     * Busca la inscripción de un equipo en esta competencia, para llegar a su nómina.
     *
     * Reglas: RN-17
     */
    public InscripcionCompetencia buscarInscripcion(Equipo equipo)
    {
        for (InscripcionCompetencia inscripcion : inscripciones)
        {
            if (inscripcion.getEquipo() == equipo)
            {
                return inscripcion;
            }
        }
        return null;
    }

    /**
     * RF-A-03, RF-E-05 - Crear competencias e inscribir equipos / Registrar el partido y sus estadísticas
     *
     * Se programa un partido oficial de un equipo del club contra otra institución. Solo se puede si el
     * equipo está inscrito en la competencia.
     *
     * Reglas: RN-15
     */
    public PartidoCompetencia programarPartido(String id, LocalDate fecha, Equipo equipo, String institucionRival, String escenario, String fase) throws ReglaDominioException
    {
        if (buscarInscripcion(equipo) == null)
        {
            throw new AccionNoPermitidaException("RN-15", equipo.getNombre() + " no esta inscrito en " + nombre);
        }
        PartidoCompetencia partido = new PartidoCompetencia(id, fecha, this, equipo, institucionRival, escenario, fase);
        partidos.add(partido);
        return partido;
    }

    /**
     * RF-A-03 - Crear competencias e inscribir equipos
     *
     * Al registrar un resultado hay que sumar en la tabla. Este método encuentra la fila del equipo en la
     * tabla que corresponde, y si todavía no existe la crea.
     *
     * Reglas: RN-21
     */
    protected FilaTabla obtenerFila(String nombreTabla, Equipo equipo)
    {
        TablaPosiciones tabla = null;
        for (TablaPosiciones t : tablas)
        {
            if (t.getNombre().equals(nombreTabla))
            {
                tabla = t;
            }
        }
        if (tabla == null)
        {
            tabla = new TablaPosiciones(nombreTabla);
            tablas.add(tabla);
        }
        FilaTabla fila = tabla.buscarFila(equipo);
        if (fila == null)
        {
            fila = new FilaTabla(equipo);
            tabla.agregarFila(fila);
        }
        return fila;
    }

    /**
     * RF-E-05, RF-A-03 - Registrar el partido y sus estadísticas / Crear competencias e inscribir equipos
     *
     * Registra el marcador de un partido oficial y actualiza la clasificación. La liga y la competencia
     * por fases lo hacen distinto, por eso cada una tiene su version.
     *
     * Reglas: RN-18, RN-19, RN-20, RN-21
     */
    public abstract void registrarResultado(PartidoCompetencia partido, int marcadorClub, int marcadorRival) throws ReglaDominioException;
}
