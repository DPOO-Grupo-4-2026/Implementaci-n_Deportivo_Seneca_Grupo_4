package uniandes.dpoo.cds.modelo.competencias;

import java.time.LocalDate;
import uniandes.dpoo.cds.modelo.conjunto.Equipo;
import uniandes.dpoo.cds.modelo.conjunto.Partido;
import uniandes.dpoo.cds.modelo.conjunto.PartidoInterno;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Partido oficial contra otra institucion. El club siempre es el local en el marcador.
 */
public class PartidoCompetencia extends Partido
{
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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * En la nomina de la competencia y sin sancion vigente.
     *
     * Reglas: RN-12, RN-17
     */
    @Override
    public boolean puedeParticipar(PerfilJugador jugador)
    {
        throw new UnsupportedOperationException("Pendiente [RN-12, RN-17]: PartidoCompetencia.puedeParticipar");
    }

    /**
     * Igual que en PartidoInterno, pero con el unico equipo del club.
     *
     * Reglas: RN-11, RN-13
     */
    @Override
    protected void despuesDeCerrar()
    {
        throw new UnsupportedOperationException("Pendiente [RN-11, RN-13]: PartidoCompetencia.despuesDeCerrar");
    }
}
