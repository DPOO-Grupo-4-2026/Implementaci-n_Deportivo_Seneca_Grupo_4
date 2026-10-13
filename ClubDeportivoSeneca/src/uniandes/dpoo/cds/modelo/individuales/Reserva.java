package uniandes.dpoo.cds.modelo.individuales;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;
import uniandes.dpoo.cds.modelo.usuarios.Socio;

/**
 * Ocupacion de una instalacion por un socio en una franja.
 */
public class Reserva
{
    private String id;
    private Socio solicitante;
    private Instalacion instalacion;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Modalidad modalidad;
    private int numeroJugadores;
    private PartidoPractica partido;
    private List<PerfilJugador> participantes;

    public Reserva(String id, Socio solicitante, Instalacion instalacion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Modalidad modalidad, int numeroJugadores)
    {
        this.id = id;
        this.solicitante = solicitante;
        this.instalacion = instalacion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.modalidad = modalidad;
        this.numeroJugadores = numeroJugadores;
        this.participantes = new ArrayList<PerfilJugador>();
    }

    public String getId()
    {
        return id;
    }

    public Socio getSolicitante()
    {
        return solicitante;
    }

    public Instalacion getInstalacion()
    {
        return instalacion;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public LocalTime getHoraInicio()
    {
        return horaInicio;
    }

    public LocalTime getHoraFin()
    {
        return horaFin;
    }

    public Modalidad getModalidad()
    {
        return modalidad;
    }

    public int getNumeroJugadores()
    {
        return numeroJugadores;
    }

    public PartidoPractica getPartido()
    {
        return partido;
    }

    public void setPartido(PartidoPractica partido)
    {
        this.partido = partido;
    }

    public List<PerfilJugador> getParticipantes()
    {
        return participantes;
    }

    public void agregarParticipante(PerfilJugador participante)
    {
        participantes.add(participante);
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Dice si la reserva ocupa algun momento de esa franja. Intervalos semiabiertos: 10-11 y 11-12 no se cruzan.
     *
     * Reglas: RN-25
     */
    public boolean seCruzaCon(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin)
    {
        throw new UnsupportedOperationException("Pendiente [RN-25]: Reserva.seCruzaCon");
    }

    /**
     * Dice si el momento cae dentro de la reserva.
     *
     * Reglas: RN-32
     */
    public boolean estaEnCurso(LocalDateTime momento)
    {
        throw new UnsupportedOperationException("Pendiente [RN-32]: Reserva.estaEnCurso");
    }
}
