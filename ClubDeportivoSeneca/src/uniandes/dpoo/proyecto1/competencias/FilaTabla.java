package uniandes.dpoo.proyecto1.competencias;

import uniandes.dpoo.proyecto1.conjunto.DeporteConjunto;
import uniandes.dpoo.proyecto1.conjunto.Equipo;

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

    public void setPartidosJugados(int partidosJugados)
    {
        this.partidosJugados = partidosJugados;
    }

    public int getGanados()
    {
        return ganados;
    }

    public void setGanados(int ganados)
    {
        this.ganados = ganados;
    }

    public int getEmpatados()
    {
        return empatados;
    }

    public void setEmpatados(int empatados)
    {
        this.empatados = empatados;
    }

    public int getPerdidos()
    {
        return perdidos;
    }

    public void setPerdidos(int perdidos)
    {
        this.perdidos = perdidos;
    }

    public int getAFavor()
    {
        return aFavor;
    }

    public void setAFavor(int aFavor)
    {
        this.aFavor = aFavor;
    }

    public int getEnContra()
    {
        return enContra;
    }

    public void setEnContra(int enContra)
    {
        this.enContra = enContra;
    }

    public int getPuntos()
    {
        return puntos;
    }

    public void setPuntos(int puntos)
    {
        this.puntos = puntos;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-A-03 - Crear competencias e inscribir equipos
     *
     * Suma un partido a la fila: jugados, goles a favor y en contra, y si ganó, empató o perdió con los
     * puntos que da el deporte.
     *
     * Reglas: RN-18
     */
    public void sumarResultado(int aFavor, int enContra, DeporteConjunto deporte)
    {
        partidosJugados++;
        this.aFavor = this.aFavor + aFavor;
        this.enContra = this.enContra + enContra;
        if (aFavor > enContra)
        {
            ganados++;
            puntos = puntos + deporte.getPuntosVictoria();
        }
        else if (aFavor == enContra)
        {
            empatados++;
            puntos = puntos + deporte.getPuntosEmpate();
        }
        else
        {
            perdidos++;
            puntos = puntos + deporte.getPuntosDerrota();
        }
    }
}
