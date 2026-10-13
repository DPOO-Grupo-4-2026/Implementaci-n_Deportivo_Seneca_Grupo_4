package uniandes.dpoo.cds.modelo.conjunto;

/**
 * Futbol, baloncesto o voleibol, con sus reglas propias.
 */
public class DeporteConjunto
{
    private String nombre;
    private int minJugadoresHabilitados;
    private boolean permiteEmpate;
    private int puntosVictoria;
    private int puntosEmpate;
    private int puntosDerrota;

    public DeporteConjunto(String nombre, int minJugadoresHabilitados, boolean permiteEmpate, int puntosVictoria, int puntosEmpate, int puntosDerrota)
    {
        this.nombre = nombre;
        this.minJugadoresHabilitados = minJugadoresHabilitados;
        this.permiteEmpate = permiteEmpate;
        this.puntosVictoria = puntosVictoria;
        this.puntosEmpate = puntosEmpate;
        this.puntosDerrota = puntosDerrota;
    }

    public String getNombre()
    {
        return nombre;
    }

    public int getMinJugadoresHabilitados()
    {
        return minJugadoresHabilitados;
    }

    public boolean permiteEmpate()
    {
        return permiteEmpate;
    }

    public int getPuntosVictoria()
    {
        return puntosVictoria;
    }

    public int getPuntosEmpate()
    {
        return puntosEmpate;
    }

    public int getPuntosDerrota()
    {
        return puntosDerrota;
    }
}
