package uniandes.dpoo.proyecto1.individuales;

import uniandes.dpoo.proyecto1.conjunto.EstadisticaPartido;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Estadísticas individuales de tenis de mesa.
 */
public class EstadisticaTenisMesa extends EstadisticaPartido
{
    private int setsGanados;
    private int puntosTotales;

    public EstadisticaTenisMesa(PerfilJugador jugador, Partido partido, int setsGanados, int puntosTotales)
    {
        super(jugador, partido);
        this.setsGanados = setsGanados;
        this.puntosTotales = puntosTotales;
    }

    public int getSetsGanados()
    {
        return setsGanados;
    }

    public int getPuntosTotales()
    {
        return puntosTotales;
    }

    @Override
    public String describir()
    {
        return "sets ganados " + setsGanados + ", puntos " + puntosTotales;
    }
}
