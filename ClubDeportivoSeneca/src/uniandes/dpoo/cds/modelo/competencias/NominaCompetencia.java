package uniandes.dpoo.cds.modelo.competencias;

import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Jugadores que pueden disputar partidos oficiales. No es la plantilla del equipo.
 */
public class NominaCompetencia
{
    private List<RenglonNomina> renglones;

    public NominaCompetencia()
    {
        this.renglones = new ArrayList<RenglonNomina>();
    }

    public List<RenglonNomina> getRenglones()
    {
        return renglones;
    }

    public void agregarRenglon(RenglonNomina renglon)
    {
        renglones.add(renglon);
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Dice si el jugador tiene renglon en esta nomina.
     *
     * Reglas: RN-17
     */
    public boolean contiene(PerfilJugador jugador)
    {
        throw new UnsupportedOperationException("Pendiente [RN-17]: NominaCompetencia.contiene");
    }
}
