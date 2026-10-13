package uniandes.dpoo.cds.modelo.individuales;

import uniandes.dpoo.cds.modelo.conjunto.EstadisticaPartido;
import uniandes.dpoo.cds.modelo.conjunto.Partido;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Estadisticas individuales de tenis y padel.
 */
public class EstadisticaRaqueta extends EstadisticaPartido
{
    private int setsGanados;
    private int gamesGanados;
    private int aces;
    private int erroresNoForzados;

    public EstadisticaRaqueta(PerfilJugador jugador, Partido partido, int setsGanados, int gamesGanados, int aces, int erroresNoForzados)
    {
        super(jugador, partido);
        this.setsGanados = setsGanados;
        this.gamesGanados = gamesGanados;
        this.aces = aces;
        this.erroresNoForzados = erroresNoForzados;
    }

    public int getSetsGanados()
    {
        return setsGanados;
    }

    public int getGamesGanados()
    {
        return gamesGanados;
    }

    public int getAces()
    {
        return aces;
    }

    public int getErroresNoForzados()
    {
        return erroresNoForzados;
    }
}
