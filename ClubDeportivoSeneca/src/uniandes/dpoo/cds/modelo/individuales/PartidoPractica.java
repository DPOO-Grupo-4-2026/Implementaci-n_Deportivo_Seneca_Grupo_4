package uniandes.dpoo.cds.modelo.individuales;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.conjunto.Partido;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Encuentro entre socios de una disciplina, sobre una reserva.
 */
public class PartidoPractica extends Partido
{
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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Solo quienes estan entre los participantes del partido.
     *
     * Reglas: RN-23
     */
    @Override
    public boolean puedeParticipar(PerfilJugador jugador)
    {
        throw new UnsupportedOperationException("Pendiente [RN-23]: PartidoPractica.puedeParticipar");
    }

    /**
     * Cierra el partido y ajusta el ranking de cada participante con ELO (A-4).
     *
     * Que debe verificar:
     * - Participantes en posicion par = lado local; impar = visitante.
     * - K = 32; en dobles, promedio de cada lado.
     *
     * Reglas: RN-26
     */
    public void cerrarYActualizarRanking(int marcadorLocal, int marcadorVisitante) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-26]: PartidoPractica.cerrarYActualizarRanking");
    }
}
