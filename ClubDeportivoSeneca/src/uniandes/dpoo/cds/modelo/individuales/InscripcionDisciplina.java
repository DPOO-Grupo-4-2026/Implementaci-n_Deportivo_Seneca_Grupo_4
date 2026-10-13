package uniandes.dpoo.cds.modelo.individuales;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Clase intermedia entre PerfilJugador y DisciplinaIndividual: el nivel y el ranking son del par.
 */
public class InscripcionDisciplina
{
    private PerfilJugador jugador;
    private DisciplinaIndividual disciplina;
    private Nivel nivel;
    private int puntajeRanking;
    private List<AjusteRanking> ajustes;

    public InscripcionDisciplina(PerfilJugador jugador, DisciplinaIndividual disciplina, Nivel nivel, int puntajeRanking)
    {
        this.jugador = jugador;
        this.disciplina = disciplina;
        this.nivel = nivel;
        this.puntajeRanking = puntajeRanking;
        this.ajustes = new ArrayList<AjusteRanking>();
    }

    public PerfilJugador getJugador()
    {
        return jugador;
    }

    public DisciplinaIndividual getDisciplina()
    {
        return disciplina;
    }

    public Nivel getNivel()
    {
        return nivel;
    }

    public void setNivel(Nivel nivel)
    {
        this.nivel = nivel;
    }

    public int getPuntajeRanking()
    {
        return puntajeRanking;
    }

    public List<AjusteRanking> getAjustes()
    {
        return ajustes;
    }

    public void agregarAjuste(AjusteRanking ajuste)
    {
        ajustes.add(ajuste);
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Cambia el puntaje y deja el AjusteRanking con el antes y el despues.
     *
     * Reglas: RN-26
     */
    public AjusteRanking ajustarRanking(int nuevoPuntaje, LocalDate fecha, PartidoPractica partido)
    {
        throw new UnsupportedOperationException("Pendiente [RN-26]: InscripcionDisciplina.ajustarRanking");
    }
}
