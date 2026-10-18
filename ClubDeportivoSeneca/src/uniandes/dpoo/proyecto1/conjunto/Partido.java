package uniandes.dpoo.proyecto1.conjunto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.JugadorNoHabilitadoException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Encuentro de cualquier tipo. Guarda lo común: identificador, fecha, marcador y estadísticas.
 */
public abstract class Partido
{
    private String id;
    private LocalDate fecha;
    private int marcadorLocal;
    private int marcadorVisitante;
    private boolean jugado;
    private List<EstadisticaPartido> estadisticas;

    public Partido(String id, LocalDate fecha)
    {
        this.id = id;
        this.fecha = fecha;
        this.marcadorLocal = 0;
        this.marcadorVisitante = 0;
        this.jugado = false;
        this.estadisticas = new ArrayList<EstadisticaPartido>();
    }

    public String getId()
    {
        return id;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public int getMarcadorLocal()
    {
        return marcadorLocal;
    }

    public void setMarcadorLocal(int marcadorLocal)
    {
        this.marcadorLocal = marcadorLocal;
    }

    public int getMarcadorVisitante()
    {
        return marcadorVisitante;
    }

    public void setMarcadorVisitante(int marcadorVisitante)
    {
        this.marcadorVisitante = marcadorVisitante;
    }

    public boolean isJugado()
    {
        return jugado;
    }

    public void setJugado(boolean jugado)
    {
        this.jugado = jugado;
    }

    public List<EstadisticaPartido> getEstadisticas()
    {
        return estadisticas;
    }

    public void agregarEstadistica(EstadisticaPartido estadistica)
    {
        estadisticas.add(estadistica);
    }

    /**
     * Lo que cada tipo de partido hace al cerrarse. Por defecto, nada.
     * PartidoInterno y PartidoCompetencia lo redefinen para descontar sanciones y aplicar tarjetas.
     */
    protected void despuesDeCerrar()
    {
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-05 - Registrar el partido y sus estadísticas
     *
     * El entrenador registra como le fue a un jugador en el partido. Revisamos que el partido no esté
     * cerrado, que el jugador pueda jugarlo y que no tenga ya una estadística en este partido. Quien puede
     * jugar depende del tipo de partido, por eso se lo preguntamos a puedeParticipar.
     *
     * Reglas: RN-10, RN-12
     */
    public void registrarEstadistica(EstadisticaPartido estadistica) throws ReglaDominioException
    {
        if (jugado)
        {
            throw new AccionNoPermitidaException("RN-10", "El partido " + id + " ya esta cerrado");
        }
        if (estadistica.getPartido() != this)
        {
            throw new AccionNoPermitidaException("RN-10", "La estadistica es de otro partido");
        }
        PerfilJugador jugador = estadistica.getJugador();
        if (!puedeParticipar(jugador))
        {
            throw new JugadorNoHabilitadoException("RN-12", jugador.getUsuario().getNombre() + " no puede jugar el partido " + id);
        }
        for (EstadisticaPartido existente : estadisticas)
        {
            if (existente.getJugador() == jugador)
            {
                throw new AccionNoPermitidaException("RN-10", jugador.getUsuario().getNombre() + " ya tiene estadistica en el partido " + id);
            }
        }
        agregarEstadistica(estadistica);
    }

    /**
     * Cada tipo de partido decide quién puede jugar: inscrito y habilitado, en nómina, o participante de la reserva.
     *
     * Reglas: RN-12, RN-17
     */
    public abstract boolean puedeParticipar(PerfilJugador jugador);

    /**
     * RF-S-04, RF-A-07 - Consultar el calendario / el historial de partidos
     *
     * Dice en una línea quiénes juegan. Cada tipo de partido lo sabe a su manera: dos equipos del club, un
     * equipo contra otra institución, o socios en una reserva.
     */
    public abstract String describir();

    /**
     * RF-E-05 - Registrar el partido y sus estadísticas
     *
     * Se termina el partido: guardamos el marcador y lo marcamos como jugado. Al final llamamos a
     * despuesDeCerrar, donde cada tipo de partido hace lo suyo (por ejemplo, las sanciones).
     *
     * Reglas: RN-19
     */
    public void cerrar(int marcadorLocal, int marcadorVisitante) throws ReglaDominioException
    {
        if (jugado)
        {
            throw new AccionNoPermitidaException("RN-19", "El partido " + id + " ya esta cerrado");
        }
        if (marcadorLocal < 0 || marcadorVisitante < 0)
        {
            throw new AccionNoPermitidaException("RN-19", "El marcador no puede ser negativo");
        }
        this.marcadorLocal = marcadorLocal;
        this.marcadorVisitante = marcadorVisitante;
        this.jugado = true;
        despuesDeCerrar();
    }
}
