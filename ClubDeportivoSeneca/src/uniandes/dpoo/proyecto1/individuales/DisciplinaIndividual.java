package uniandes.dpoo.proyecto1.individuales;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.JugadorNoHabilitadoException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.excepciones.ReservaRechazadaException;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Socio;

/**
 * Tenis, pádel o tenis de mesa. Conoce sus instalaciones, sus inscritos y sus reservas.
 */
public class DisciplinaIndividual
{
    private String nombre;
    private boolean admiteDobles;
    private int maxInstalacionesSimultaneas;
    private List<Instalacion> instalaciones;
    private List<InscripcionDisciplina> inscripciones;
    private List<Reserva> reservas;

    public DisciplinaIndividual(String nombre, boolean admiteDobles, int maxInstalacionesSimultaneas)
    {
        this.nombre = nombre;
        this.admiteDobles = admiteDobles;
        this.maxInstalacionesSimultaneas = maxInstalacionesSimultaneas;
        this.instalaciones = new ArrayList<Instalacion>();
        this.inscripciones = new ArrayList<InscripcionDisciplina>();
        this.reservas = new ArrayList<Reserva>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public boolean admiteDobles()
    {
        return admiteDobles;
    }

    public int getMaxInstalacionesSimultaneas()
    {
        return maxInstalacionesSimultaneas;
    }

    public List<Instalacion> getInstalaciones()
    {
        return instalaciones;
    }

    public void agregarInstalacion(Instalacion instalacion)
    {
        instalaciones.add(instalacion);
    }

    public List<InscripcionDisciplina> getInscripciones()
    {
        return inscripciones;
    }

    public void agregarInscripcion(InscripcionDisciplina inscripcion)
    {
        inscripciones.add(inscripcion);
    }

    public List<Reserva> getReservas()
    {
        return reservas;
    }

    public void agregarReserva(Reserva reserva)
    {
        reservas.add(reserva);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-07 - Inscribirse en una disciplina individual
     *
     * Un socio se inscribe en tenis, pádel o tenis de mesa con su nivel. Empieza con ranking de 1000.
     *
     * Reglas: RN-22
     */
    public InscripcionDisciplina inscribir(PerfilJugador jugador, Nivel nivel) throws ReglaDominioException
    {
        for (InscripcionDisciplina inscripcion : inscripciones)
        {
            if (inscripcion.getJugador() == jugador)
            {
                throw new AccionNoPermitidaException("RN-22", jugador.getUsuario().getNombre() + " ya esta inscrito en " + nombre);
            }
        }
        InscripcionDisciplina inscripcion = new InscripcionDisciplina(jugador, this, nivel, InscripcionDisciplina.RANKING_INICIAL);
        inscripciones.add(inscripcion);
        jugador.agregarInscripcionDisciplina(inscripcion);
        return inscripcion;
    }

    /**
     * RF-S-08, RF-A-12 - Reservar una instalación / Consultar las instalaciones
     *
     * Cuenta cuántas instalaciones de la disciplina están reservadas en esa franja.
     *
     * Reglas: RN-25
     */
    public int contarInstalacionesOcupadas(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin)
    {
        return consultarInstalacionesOcupadas(fecha, horaInicio, horaFin).size();
    }

    /**
     * RF-S-08 - Reservar una instalación
     *
     * Un socio reserva una cancha o mesa en un horario. La instalación tiene que aceptar la modalidad y el
     * número de jugadores, tiene que estar libre, y la disciplina no puede pasarse de su máximo de
     * instalaciones al tiempo.
     *
     * Reglas: RN-24, RN-25, RN-38
     */
    public Reserva reservar(String id, Socio solicitante, Instalacion instalacion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Modalidad modalidad, int numeroJugadores) throws ReglaDominioException
    {
        if (!horaInicio.isBefore(horaFin))
        {
            throw new ReservaRechazadaException("RN-24", "La hora de inicio tiene que ser antes de la hora de fin");
        }
        if (!instalaciones.contains(instalacion))
        {
            throw new ReservaRechazadaException("RN-24", instalacion.getNombre() + " no es de " + nombre);
        }
        if (!instalacion.admite(modalidad, numeroJugadores))
        {
            throw new ReservaRechazadaException("RN-24", instalacion.getNombre() + " no admite " + modalidad + " con " + numeroJugadores + " jugadores");
        }
        for (Reserva otra : reservas)
        {
            if (otra.getInstalacion() == instalacion && otra.seCruzaCon(fecha, horaInicio, horaFin))
            {
                throw new ReservaRechazadaException("RN-25", instalacion.getNombre() + " ya esta reservada en ese horario");
            }
        }
        if (contarInstalacionesOcupadas(fecha, horaInicio, horaFin) >= maxInstalacionesSimultaneas)
        {
            throw new ReservaRechazadaException("RN-25", nombre + " ya tiene " + maxInstalacionesSimultaneas + " instalacion(es) ocupada(s) en ese horario");
        }

        Reserva reserva = new Reserva(id, solicitante, instalacion, fecha, horaInicio, horaFin, modalidad, numeroJugadores);
        reservas.add(reserva);
        return reserva;
    }

    /**
     * RF-A-12 - Consultar las instalaciones
     *
     * El administrador quiere ver cómo están las instalaciones de la disciplina: cuáles están ocupadas en
     * una franja.
     *
     * Reglas: RN-24, RN-25
     */
    public List<Instalacion> consultarInstalacionesOcupadas(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin)
    {
        List<Instalacion> ocupadas = new ArrayList<Instalacion>();
        for (Reserva reserva : reservas)
        {
            if (reserva.seCruzaCon(fecha, horaInicio, horaFin) && !ocupadas.contains(reserva.getInstalacion()))
            {
                ocupadas.add(reserva.getInstalacion());
            }
        }
        return ocupadas;
    }

    /**
     * RF-S-09 - Programar un partido de práctica
     *
     * Sobre una reserva se arma un partido de práctica. Los niveles tienen que ser iguales o seguidos,
     * salvo que alguno sea entrenador.
     *
     * Reglas: RN-23, RN-24
     */
    public PartidoPractica programarPartidoPractica(String id, Reserva reserva, List<InscripcionDisciplina> participantes) throws ReglaDominioException
    {
        if (!reservas.contains(reserva))
        {
            throw new ReservaRechazadaException("RN-24", "La reserva " + reserva.getId() + " no es de " + nombre);
        }
        if (reserva.getPartido() != null)
        {
            throw new AccionNoPermitidaException("RN-24", "La reserva " + reserva.getId() + " ya tiene un partido");
        }
        if (participantes.size() != reserva.getNumeroJugadores())
        {
            throw new ReservaRechazadaException("RN-24", "La reserva es para " + reserva.getNumeroJugadores() + " jugadores y llegaron " + participantes.size());
        }
        boolean hayEntrenador = false;
        for (InscripcionDisciplina participante : participantes)
        {
            if (participante.getDisciplina() != this)
            {
                throw new JugadorNoHabilitadoException("RN-22", participante.getJugador().getUsuario().getNombre() + " no esta inscrito en " + nombre);
            }
            if (participante.getJugador().esEntrenador())
            {
                hayEntrenador = true;
            }
        }
        if (!hayEntrenador)
        {
            for (InscripcionDisciplina uno : participantes)
            {
                for (InscripcionDisciplina otro : participantes)
                {
                    if (!uno.getNivel().puedeJugarContra(otro.getNivel()))
                    {
                        throw new JugadorNoHabilitadoException("RN-23", uno.getNivel() + " no puede jugar contra " + otro.getNivel());
                    }
                }
            }
        }

        PartidoPractica partido = new PartidoPractica(id, reserva.getFecha(), reserva, reserva.getModalidad());
        for (InscripcionDisciplina participante : participantes)
        {
            partido.agregarParticipante(participante);
            reserva.agregarParticipante(participante.getJugador());
        }
        reserva.setPartido(partido);
        return partido;
    }
}
