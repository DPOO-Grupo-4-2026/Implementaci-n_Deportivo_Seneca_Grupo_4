package uniandes.dpoo.proyecto1.individuales;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Socio;

/**
 * Ocupación de una instalación por un socio en una franja.
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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-08 - Reservar una instalación
     *
     * Responde si esta reserva choca con otra franja el mismo día. Una reserva de 10 a 11 y otra de 11 a
     * 12 no chocan.
     *
     * Reglas: RN-25
     */
    public boolean seCruzaCon(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin)
    {
        return this.fecha.equals(fecha) && this.horaInicio.isBefore(horaFin) && horaInicio.isBefore(this.horaFin);
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * Responde si en ese momento la reserva está ocurriendo. Sirve para la regla de la bebida caliente.
     *
     * Reglas: RN-32
     */
    public boolean estaEnCurso(LocalDateTime momento)
    {
        LocalTime hora = momento.toLocalTime();
        return fecha.equals(momento.toLocalDate()) && !hora.isBefore(horaInicio) && hora.isBefore(horaFin);
    }
}
