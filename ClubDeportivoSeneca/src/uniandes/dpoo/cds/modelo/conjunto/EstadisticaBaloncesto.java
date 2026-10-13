package uniandes.dpoo.cds.modelo.conjunto;

import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Estadisticas individuales de baloncesto.
 */
public class EstadisticaBaloncesto extends EstadisticaPartido
{
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
