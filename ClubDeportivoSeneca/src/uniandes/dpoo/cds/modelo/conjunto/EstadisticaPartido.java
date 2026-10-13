package uniandes.dpoo.cds.modelo.conjunto;

import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Clase intermedia entre PerfilJugador y Partido. Su existencia dice que el jugador participo.
 */
public abstract class EstadisticaPartido
{
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
     * Pasa las tarjetas de esta actuacion a la inscripcion del jugador. Por defecto, nada (D-17):
     * solo el futbol tiene tarjetas.
     */
    public void aplicarTarjetas(InscripcionEquipo inscripcion)
    {
    }
}
