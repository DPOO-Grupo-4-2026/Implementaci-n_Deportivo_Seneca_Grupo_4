package uniandes.dpoo.proyecto1.individuales;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Clase intermedia entre PerfilJugador y DisciplinaIndividual: el nivel y el ranking son del par.
 */
public class InscripcionDisciplina
{
    /** Ranking con el que empieza todo jugador. */
    public static final int RANKING_INICIAL = 1000;

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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-10 - Consultar mi ranking
     *
     * Cambia el ranking del jugador y guarda el antes y el después, para que se pueda ver como ha
     * evolucionado.
     *
     * Reglas: RN-26
     */
    public AjusteRanking ajustarRanking(int nuevoPuntaje, LocalDate fecha, PartidoPractica partido)
    {
        AjusteRanking ajuste = new AjusteRanking(fecha, puntajeRanking, nuevoPuntaje, this, partido);
        ajustes.add(ajuste);
        partido.agregarAjuste(ajuste);
        puntajeRanking = nuevoPuntaje;
        return ajuste;
    }
}
