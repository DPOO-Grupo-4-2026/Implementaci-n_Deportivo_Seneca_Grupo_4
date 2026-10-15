package uniandes.dpoo.proyecto1.conjunto;

import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Estadísticas individuales de baloncesto.
 */
public class EstadisticaBaloncesto extends EstadisticaPartido
{
    private static final long serialVersionUID = 1L;

    private int puntos;
    private int rebotes;

    public EstadisticaBaloncesto(PerfilJugador jugador, Partido partido, int puntos, int rebotes)
    {
        super(jugador, partido);
        this.puntos = puntos;
        this.rebotes = rebotes;
    }

    public int getPuntos()
    {
        return puntos;
    }

    public int getRebotes()
    {
        return rebotes;
    }
}
