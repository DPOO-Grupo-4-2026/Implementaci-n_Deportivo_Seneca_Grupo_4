package uniandes.dpoo.cds.modelo.conjunto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.cds.excepciones.JugadorNoHabilitadoException;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.competencias.PartidoCompetencia;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Encuentro de cualquier tipo. Guarda lo comun: identificador, fecha, marcador y estadisticas.
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

    public int getMarcadorVisitante()
    {
        return marcadorVisitante;
    }

    public boolean isJugado()
    {
        return jugado;
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
     * Lo que cada tipo de partido hace al cerrarse. Por defecto, nada (D-17).
     * PartidoInterno y PartidoCompetencia lo redefinen para descontar sanciones y aplicar tarjetas.
     */
    protected void despuesDeCerrar()
    {
    }

    // ------------------------------------------------------------------
    // Logica del dominio
    // ------------------------------------------------------------------

    /**
     * Agrega la actuacion de un jugador en este partido.
     *
     * Verifica:
     * - Que el partido no este cerrado y que la estadistica sea de este partido.
     * - Que el jugador pueda participar (ver puedeParticipar).
     * - Que no tenga ya una estadistica en este partido.
     *
     * Reglas: RN-10, RN-12
     *
     * Por que asi: Metodo plantilla: el flujo es comun y la pregunta de quien puede jugar la responde cada subclase.
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
     * Cada tipo de partido decide quien puede jugar: inscrito y habilitado, en nomina, o participante de la reserva.
     *
     * Reglas: RN-12, RN-17
     */
    public abstract boolean puedeParticipar(PerfilJugador jugador);

    /**
     * Registra el marcador final, marca el partido como jugado y llama a despuesDeCerrar.
     *
     * Verifica:
     * - Que el partido no este ya cerrado.
     * - Que el marcador no sea negativo.
     *
     * Reglas: RN-19
     *
     * Por que asi: Cerrar es comun a todos los partidos; lo que cambia por tipo va en despuesDeCerrar (D-17).
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
