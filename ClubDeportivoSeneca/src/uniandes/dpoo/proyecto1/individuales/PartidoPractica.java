package uniandes.dpoo.proyecto1.individuales;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Encuentro entre socios de una disciplina, sobre una reserva.
 */
public class PartidoPractica extends Partido
{
    /** Lo máximo que puede subir o bajar el ranking en un partido (la K del ELO). */
    public static final int PUNTOS_EN_JUEGO = 32;

    private Reserva reserva;
    private Modalidad modalidad;
    private List<InscripcionDisciplina> participantes;
    private List<AjusteRanking> ajustes;

    public PartidoPractica(String id, LocalDate fecha, Reserva reserva, Modalidad modalidad)
    {
        super(id, fecha);
        this.reserva = reserva;
        this.modalidad = modalidad;
        this.participantes = new ArrayList<InscripcionDisciplina>();
        this.ajustes = new ArrayList<AjusteRanking>();
    }

    public Reserva getReserva()
    {
        return reserva;
    }

    public Modalidad getModalidad()
    {
        return modalidad;
    }

    public List<InscripcionDisciplina> getParticipantes()
    {
        return participantes;
    }

    public void agregarParticipante(InscripcionDisciplina participante)
    {
        participantes.add(participante);
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
     * RF-S-09 - Programar un partido de práctica
     *
     * En un partido de práctica solo juegan los que están en el partido.
     *
     * Reglas: RN-23
     */
    @Override
    public boolean puedeParticipar(PerfilJugador jugador)
    {
        for (InscripcionDisciplina participante : participantes)
        {
            if (participante.getJugador() == jugador)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * RF-S-10 - Consultar mi ranking
     *
     * Se cierra el partido de práctica y a cada jugador se le ajusta el ranking según ganó o perdió.
     *
     * Reglas: RN-26
     */
    public void cerrarYActualizarRanking(int marcadorLocal, int marcadorVisitante) throws ReglaDominioException
    {
        cerrar(marcadorLocal, marcadorVisitante);

        // Los participantes en posición par (0, 2) son el lado local y los impares (1, 3) el visitante.
        // En dobles se usa el promedio de la pareja.
        double sumaLocal = 0;
        double sumaVisitante = 0;
        for (int i = 0; i < participantes.size(); i++)
        {
            if (i % 2 == 0)
            {
                sumaLocal = sumaLocal + participantes.get(i).getPuntajeRanking();
            }
            else
            {
                sumaVisitante = sumaVisitante + participantes.get(i).getPuntajeRanking();
            }
        }
        double jugadoresPorLado = participantes.size() / 2;
        double rankingLocal = sumaLocal / jugadoresPorLado;
        double rankingVisitante = sumaVisitante / jugadoresPorLado;

        // Fórmula del ELO: lo que se esperaba que sacara el local, entre 0 y 1
        double esperadoLocal = 1.0 / (1.0 + Math.pow(10, (rankingVisitante - rankingLocal) / 400.0));
        double resultadoLocal = 0.5;
        if (marcadorLocal > marcadorVisitante)
        {
            resultadoLocal = 1;
        }
        else if (marcadorLocal < marcadorVisitante)
        {
            resultadoLocal = 0;
        }
        int cambio = (int) Math.round(PUNTOS_EN_JUEGO * (resultadoLocal - esperadoLocal));

        for (int i = 0; i < participantes.size(); i++)
        {
            InscripcionDisciplina participante = participantes.get(i);
            if (i % 2 == 0)
            {
                participante.ajustarRanking(participante.getPuntajeRanking() + cambio, getFecha(), this);
            }
            else
            {
                participante.ajustarRanking(participante.getPuntajeRanking() - cambio, getFecha(), this);
            }
        }
    }

    @Override
    public String describir()
    {
        String nombres = "";
        for (int i = 0; i < participantes.size(); i++)
        {
            if (i > 0)
            {
                nombres = nombres + ", ";
            }
            nombres = nombres + participantes.get(i).getJugador().getUsuario().getNombre();
        }
        return "Practica de " + reserva.getInstalacion().getNombre() + ": " + nombres;
    }
}
