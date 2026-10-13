package uniandes.dpoo.cds.modelo.competencias;

import uniandes.dpoo.cds.modelo.conjunto.DeporteConjunto;
import uniandes.dpoo.cds.modelo.conjunto.Equipo;

/**
 * Registro de un equipo del club dentro de una tabla.
 */
public class FilaTabla
{
    private Equipo equipo;
    private int partidosJugados;
    private int ganados;
    private int empatados;
    private int perdidos;
    private int aFavor;
    private int enContra;
    private int puntos;

    public FilaTabla(Equipo equipo)
    {
        this.equipo = equipo;
        this.partidosJugados = 0;
        this.ganados = 0;
        this.empatados = 0;
        this.perdidos = 0;
        this.aFavor = 0;
        this.enContra = 0;
        this.puntos = 0;
    }

    public Equipo getEquipo()
    {
        return equipo;
    }

    public int getPartidosJugados()
    {
        return partidosJugados;
    }

    public int getGanados()
    {
        return ganados;
    }

    public int getEmpatados()
    {
        return empatados;
    }

    public int getPerdidos()
    {
        return perdidos;
    }

    public int getAFavor()
    {
        return aFavor;
    }

    public int getEnContra()
    {
        return enContra;
    }

    public int getPuntos()
    {
        return puntos;
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Actualiza jugados, ganados, empatados, perdidos, marcador y puntos segun el puntaje del deporte.
     *
     * Reglas: RN-18
     */
    public void sumarResultado(int aFavor, int enContra, DeporteConjunto deporte)
    {
        throw new UnsupportedOperationException("Pendiente [RN-18]: FilaTabla.sumarResultado");
    }
}
