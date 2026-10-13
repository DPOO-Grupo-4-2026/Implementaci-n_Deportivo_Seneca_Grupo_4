package uniandes.dpoo.cds.modelo.conjunto;

import java.time.LocalDate;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Partido de practica entre dos equipos del club.
 */
public class PartidoInterno extends Partido
{
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
    // Logica del dominio
    // ------------------------------------------------------------------

    /**
     * Inscrito en alguno de los dos equipos y sin sancion vigente.
     *
     * Reglas: RN-12
     *
     * Por que asi: Un jugador tiene a lo sumo un equipo por deporte (RN-05), asi que basta buscar esa inscripcion.
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
     * Primero descuenta las sanciones que este partido cumplio; despues aplica las tarjetas de hoy.
     *
     * Verifica:
     * - El orden importa: al reves, la roja de hoy se cumpliria en el mismo partido que la genero.
     *
     * Reglas: RN-11, RN-13
     *
     * Por que asi: Primero se descuenta y despues se sanciona: asi la sancion de hoy se cumple en el proximo partido.
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
