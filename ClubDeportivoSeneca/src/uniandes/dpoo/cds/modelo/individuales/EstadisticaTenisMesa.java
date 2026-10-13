package uniandes.dpoo.cds.modelo.individuales;

import uniandes.dpoo.cds.modelo.conjunto.EstadisticaPartido;
import uniandes.dpoo.cds.modelo.conjunto.Partido;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Estadisticas individuales de tenis de mesa.
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
}
