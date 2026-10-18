package uniandes.dpoo.proyecto1.conjunto;

import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

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
     * Pasa las tarjetas de esta actuación a la inscripción del jugador. Por defecto, nada:
     * solo el fútbol tiene tarjetas.
     */
    public void aplicarTarjetas(InscripcionEquipo inscripcion)
    {
    }

    /**
     * RF-S-05 - Consultar mis estadísticas
     *
     * Devuelve en una línea lo que hizo el jugador. Cada deporte mide cosas distintas, así que cada
     * subclase lo escribe a su manera.
     */
    public abstract String describir();
}
