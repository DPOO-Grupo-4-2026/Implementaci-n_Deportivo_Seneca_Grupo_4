package uniandes.dpoo.cds.modelo.competencias;

import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.conjunto.Categoria;
import uniandes.dpoo.cds.modelo.conjunto.DeporteConjunto;
import uniandes.dpoo.cds.modelo.conjunto.Equipo;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Circuito externo en el que participa el club. Guarda lo comun de los dos formatos.
 */
public abstract class Competencia
{
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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Inscribe un equipo del club con su nomina y devuelve la inscripcion.
     *
     * Que debe verificar:
     * - Que el equipo no este ya inscrito.
     * - Que el deporte y la categoria del equipo sean los de la competencia.
     * - Que la nomina alcance la nomina minima.
     * - Que todos los de la nomina esten inscritos en ese equipo.
     *
     * Reglas: RN-15, RN-16
     */
    public InscripcionCompetencia inscribirEquipo(Equipo equipo, List<PerfilJugador> nomina) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-15, RN-16]: Competencia.inscribirEquipo");
    }

    /**
     * Devuelve la inscripcion de ese equipo, o null.
     *
     * Reglas: RN-17
     */
    public InscripcionCompetencia buscarInscripcion(Equipo equipo)
    {
        throw new UnsupportedOperationException("Pendiente [RN-17]: Competencia.buscarInscripcion");
    }

    /**
     * Busca la tabla por nombre y la fila del equipo, y crea la que falte (D-18).
     *
     * Reglas: RN-21
     */
    protected FilaTabla obtenerFila(String nombreTabla, Equipo equipo)
    {
        throw new UnsupportedOperationException("Pendiente [RN-21]: Competencia.obtenerFila");
    }

    /**
     * Cierra el partido y actualiza la clasificacion. Cada formato lo hace distinto: aqui esta el polimorfismo.
     *
     * Reglas: RN-18, RN-19, RN-20, RN-21
     */
    public abstract void registrarResultado(PartidoCompetencia partido, int marcadorClub, int marcadorRival) throws ReglaDominioException;
}
