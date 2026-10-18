package uniandes.dpoo.proyecto1.competencias;

import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Presencia de un jugador en una nómina.
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
