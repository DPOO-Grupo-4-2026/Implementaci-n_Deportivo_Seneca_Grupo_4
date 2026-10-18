package uniandes.dpoo.proyecto1.individuales;

import uniandes.dpoo.proyecto1.conjunto.EstadisticaPartido;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Estadísticas individuales de tenis y pádel.
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

    @Override
    public String describir()
    {
        return "sets ganados " + setsGanados + ", games ganados " + gamesGanados + ", aces " + aces + ", errores no forzados " + erroresNoForzados;
    }
}
