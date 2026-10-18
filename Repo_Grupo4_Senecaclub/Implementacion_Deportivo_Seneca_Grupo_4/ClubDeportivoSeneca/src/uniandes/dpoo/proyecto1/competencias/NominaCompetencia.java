package uniandes.dpoo.proyecto1.competencias;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Jugadores que pueden disputar partidos oficiales. No es la plantilla del equipo.
 */
public class NominaCompetencia implements Serializable
{
    private static final long serialVersionUID = 1L;

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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-08 - Proponer la nómina de una competencia
     *
     * Responde si el jugador está en la nómina, es decir, si puede jugar partidos oficiales de esta
     * competencia.
     *
     * Reglas: RN-17
     */
    public boolean contiene(PerfilJugador jugador)
    {
        for (RenglonNomina renglon : renglones)
        {
            if (renglon.getJugador() == jugador)
            {
                return true;
            }
        }
        return false;
    }
}
