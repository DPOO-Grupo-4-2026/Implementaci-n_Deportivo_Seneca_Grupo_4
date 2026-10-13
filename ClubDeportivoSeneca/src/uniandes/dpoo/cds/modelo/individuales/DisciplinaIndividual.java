package uniandes.dpoo.cds.modelo.individuales;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;
import uniandes.dpoo.cds.modelo.usuarios.Socio;

/**
 * Tenis, padel o tenis de mesa. Conoce sus instalaciones, sus inscritos y sus reservas.
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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Inscribe al jugador en la disciplina con su nivel y ranking inicial 1000.
     *
     * Que debe verificar:
     * - Que el jugador no este ya inscrito en esta disciplina.
     *
     * Reglas: RN-22
     */
    public InscripcionDisciplina inscribir(PerfilJugador jugador, Nivel nivel) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-22]: DisciplinaIndividual.inscribir");
    }

    /**
     * Cuantas instalaciones distintas de la disciplina tienen una reserva que se cruza con esa franja.
     *
     * Reglas: RN-25
     */
    public int contarInstalacionesOcupadas(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin)
    {
        throw new UnsupportedOperationException("Pendiente [RN-25]: DisciplinaIndividual.contarInstalacionesOcupadas");
    }

    /**
     * Crea la reserva si cumple las reglas. El solicitante es Socio: un administrador no puede pasarse aqui.
     *
     * Que debe verificar:
     * - Que horaInicio sea antes de horaFin.
     * - Que la instalacion sea de esta disciplina y este libre en la franja.
     * - Que la instalacion admita la modalidad y el numero de jugadores.
     * - Que la disciplina no supere su maximo de instalaciones simultaneas.
     *
     * Reglas: RN-24, RN-25, RN-38
     */
    public Reserva reservar(String id, Socio solicitante, Instalacion instalacion, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, Modalidad modalidad, int numeroJugadores) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-24, RN-25, RN-38]: DisciplinaIndividual.reservar");
    }

    /**
     * Crea el partido de practica sobre una reserva.
     *
     * Que debe verificar:
     * - Que la reserva no tenga ya partido.
     * - Que el numero de participantes sea el de la reserva.
     * - Que todos esten inscritos en esta disciplina.
     * - Si ninguno es entrenador: el nivel mas bajo puedeJugarContra el mas alto.
     *
     * Reglas: RN-23, RN-24
     */
    public PartidoPractica programarPartidoPractica(String id, Reserva reserva, List<InscripcionDisciplina> participantes) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-23, RN-24]: DisciplinaIndividual.programarPartidoPractica");
    }
}
