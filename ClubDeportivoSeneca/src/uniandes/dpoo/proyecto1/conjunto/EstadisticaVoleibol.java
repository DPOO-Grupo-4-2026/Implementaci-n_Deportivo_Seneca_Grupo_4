package uniandes.dpoo.proyecto1.conjunto;

import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Estadísticas individuales de voleibol.
 */
public class EstadisticaVoleibol extends EstadisticaPartido
{
    private int aces;
    private int bloqueos;

    public EstadisticaVoleibol(PerfilJugador jugador, Partido partido, int aces, int bloqueos)
    {
        super(jugador, partido);
        this.aces = aces;
        this.bloqueos = bloqueos;
    }

    public int getAces()
    {
        return aces;
    }

    public int getBloqueos()
    {
        return bloqueos;
    }

    @Override
    public String describir()
    {
        return "aces " + aces + ", bloqueos " + bloqueos;
    }
}
