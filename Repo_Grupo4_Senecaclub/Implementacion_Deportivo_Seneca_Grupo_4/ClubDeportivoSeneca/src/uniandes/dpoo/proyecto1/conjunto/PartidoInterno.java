package uniandes.dpoo.proyecto1.conjunto;

import java.time.LocalDate;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Partido de práctica entre dos equipos del club.
 */
public class PartidoInterno extends Partido
{
    private static final long serialVersionUID = 1L;

    private Equipo local;
    private Equipo visitante;

    public PartidoInterno(String id, LocalDate fecha, Equipo local, Equipo visitante)
    {
        super(id, fecha);
        this.local = local;
        this.visitante = visitante;
    }

    public Equipo getLocal()
    {
        return local;
    }

    public Equipo getVisitante()
    {
        return visitante;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-05 - Registrar el partido y sus estadísticas
     *
     * En un partido interno juegan los inscritos de los dos equipos que no estén sancionados.
     *
     * Reglas: RN-12
     */
    @Override
    public boolean puedeParticipar(PerfilJugador jugador)
    {
        InscripcionEquipo inscripcion = jugador.buscarInscripcionEnDeporte(local.getDeporte());
        if (inscripcion == null)
        {
            return false;
        }
        Equipo equipo = inscripcion.getEquipo();
        return (equipo == local || equipo == visitante) && inscripcion.estaHabilitado();
    }

    /**
     * RF-E-06 - Registrar tarjetas y suspensiones
     *
     * Cuando termina un partido interno pasan dos cosas, en este orden. Primero, los sancionados de los
     * dos equipos cumplen un partido. Después, se registran las tarjetas de hoy. Si fuera al reves, una
     * roja de hoy se daria por cumplida en este mismo partido.
     *
     * Reglas: RN-11, RN-13
     */
    @Override
    protected void despuesDeCerrar()
    {
        local.descontarSancionesTrasPartido();
        visitante.descontarSancionesTrasPartido();
        for (EstadisticaPartido estadistica : getEstadisticas())
        {
            InscripcionEquipo inscripcion = estadistica.getJugador().buscarInscripcionEnDeporte(local.getDeporte());
            if (inscripcion != null)
            {
                estadistica.aplicarTarjetas(inscripcion);
            }
        }
    }
}
