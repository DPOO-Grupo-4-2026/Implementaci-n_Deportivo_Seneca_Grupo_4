package uniandes.dpoo.proyecto1.conjunto;

import java.io.Serializable;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Clase intermedia entre PerfilJugador y Partido. Su existencia dice que el jugador participo.
 */
public abstract class EstadisticaPartido implements Serializable
{
    private static final long serialVersionUID = 1L;

    private PerfilJugador jugador;
    private Partido partido;

    public EstadisticaPartido(PerfilJugador jugador, Partido partido)
    {
        this.jugador = jugador;
        this.partido = partido;
    }

    public PerfilJugador getJugador()
    {
        return jugador;
    }

    public Partido getPartido()
    {
        return partido;
    }

    /**
     * Pasa las tarjetas de esta actuación a la inscripción del jugador. Por defecto, nada:
     * solo el fútbol tiene tarjetas.
     */
    public void aplicarTarjetas(InscripcionEquipo inscripcion)
    {
    }
}
