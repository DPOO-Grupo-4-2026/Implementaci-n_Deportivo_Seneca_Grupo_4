package uniandes.dpoo.proyecto1.competencias;

import java.io.Serializable;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Presencia de un jugador en una nómina.
 */
public class RenglonNomina implements Serializable
{
    private static final long serialVersionUID = 1L;

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
