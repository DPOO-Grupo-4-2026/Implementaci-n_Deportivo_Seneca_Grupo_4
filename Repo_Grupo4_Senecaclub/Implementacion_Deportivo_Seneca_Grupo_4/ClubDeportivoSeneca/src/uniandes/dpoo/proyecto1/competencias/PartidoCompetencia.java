package uniandes.dpoo.proyecto1.competencias;

import java.time.LocalDate;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaPartido;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Partido oficial contra otra institución. El club siempre es el local en el marcador.
 */
public class PartidoCompetencia extends Partido
{
    private static final long serialVersionUID = 1L;

    private Competencia competencia;
    private Equipo equipo;
    private String institucionRival;
    private String escenario;
    private String fase;

    public PartidoCompetencia(String id, LocalDate fecha, Competencia competencia, Equipo equipo, String institucionRival, String escenario, String fase)
    {
        super(id, fecha);
        this.competencia = competencia;
        this.equipo = equipo;
        this.institucionRival = institucionRival;
        this.escenario = escenario;
        this.fase = fase;
    }

    public Competencia getCompetencia()
    {
        return competencia;
    }

    public Equipo getEquipo()
    {
        return equipo;
    }

    public String getInstitucionRival()
    {
        return institucionRival;
    }

    public String getEscenario()
    {
        return escenario;
    }

    public String getFase()
    {
        return fase;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-05 - Registrar el partido y sus estadísticas
     *
     * En un partido oficial juega quién está en la nómina y no tiene sanción pendiente.
     *
     * Reglas: RN-12, RN-17
     */
    @Override
    public boolean puedeParticipar(PerfilJugador jugador)
    {
        InscripcionCompetencia inscripcion = competencia.buscarInscripcion(equipo);
        if (inscripcion == null || !inscripcion.getNomina().contiene(jugador))
        {
            return false;
        }
        InscripcionEquipo enEquipo = jugador.buscarInscripcionEnDeporte(equipo.getDeporte());
        return enEquipo != null && enEquipo.getEquipo() == equipo && enEquipo.estaHabilitado();
    }

    /**
     * RF-E-06 - Registrar tarjetas y suspensiones
     *
     * Igual que en el partido interno: primero se descuentan sanciones y después se registran las tarjetas
     * de hoy.
     *
     * Reglas: RN-11, RN-13
     */
    @Override
    protected void despuesDeCerrar()
    {
        equipo.descontarSancionesTrasPartido();
        for (EstadisticaPartido estadistica : getEstadisticas())
        {
            InscripcionEquipo inscripcion = estadistica.getJugador().buscarInscripcionEnDeporte(equipo.getDeporte());
            if (inscripcion != null)
            {
                estadistica.aplicarTarjetas(inscripcion);
            }
        }
    }
}
