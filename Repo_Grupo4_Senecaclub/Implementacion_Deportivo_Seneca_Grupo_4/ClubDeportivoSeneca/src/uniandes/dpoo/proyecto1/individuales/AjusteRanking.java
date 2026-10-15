package uniandes.dpoo.proyecto1.individuales;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Cambio de ranking producido por un partido. Guarda el histórico.
 */
public class AjusteRanking implements Serializable
{
    private static final long serialVersionUID = 1L;

    private LocalDate fecha;
    private int puntajeAntes;
    private int puntajeDespues;
    private InscripcionDisciplina inscripcion;
    private PartidoPractica partido;

    public AjusteRanking(LocalDate fecha, int puntajeAntes, int puntajeDespues, InscripcionDisciplina inscripcion, PartidoPractica partido)
    {
        this.fecha = fecha;
        this.puntajeAntes = puntajeAntes;
        this.puntajeDespues = puntajeDespues;
        this.inscripcion = inscripcion;
        this.partido = partido;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public int getPuntajeAntes()
    {
        return puntajeAntes;
    }

    public int getPuntajeDespues()
    {
        return puntajeDespues;
    }

    public InscripcionDisciplina getInscripcion()
    {
        return inscripcion;
    }

    public PartidoPractica getPartido()
    {
        return partido;
    }

    /** Atributo derivado: no se guarda, se calcula. */
    public int getDelta()
    {
        return puntajeDespues - puntajeAntes;
    }
}
