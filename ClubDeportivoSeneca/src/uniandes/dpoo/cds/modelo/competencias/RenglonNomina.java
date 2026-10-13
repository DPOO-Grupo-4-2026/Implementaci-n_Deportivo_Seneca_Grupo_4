package uniandes.dpoo.cds.modelo.competencias;

import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Presencia de un jugador en una nomina.
 */
public class RenglonNomina
{
    private PerfilJugador jugador;

    public RenglonNomina(PerfilJugador jugador)
    {
        this.jugador = jugador;
    }

    public PerfilJugador getJugador()
    {
        return jugador;
    }
}
