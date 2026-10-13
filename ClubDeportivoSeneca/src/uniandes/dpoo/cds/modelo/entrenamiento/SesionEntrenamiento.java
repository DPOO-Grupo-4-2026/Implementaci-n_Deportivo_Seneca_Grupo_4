package uniandes.dpoo.cds.modelo.entrenamiento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.conjunto.Equipo;
import uniandes.dpoo.cds.modelo.individuales.DisciplinaIndividual;
import uniandes.dpoo.cds.modelo.individuales.Instalacion;
import uniandes.dpoo.cds.modelo.usuarios.Entrenador;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Sesion planeada por un entrenador en una instalacion.
 */
public class SesionEntrenamiento
{
    private String id;
    private Entrenador entrenador;
    private Instalacion instalacion;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private int duracionMinutos;
    private TipoSesion tipo;
    private Equipo equipo;
    private DisciplinaIndividual disciplina;
    private boolean cerrada;
    private List<RegistroAsistencia> registros;

    public SesionEntrenamiento(String id, Entrenador entrenador, Instalacion instalacion, LocalDate fecha, LocalTime horaInicio, int duracionMinutos, TipoSesion tipo)
    {
        this.id = id;
        this.entrenador = entrenador;
        this.instalacion = instalacion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.duracionMinutos = duracionMinutos;
        this.tipo = tipo;
        this.cerrada = false;
        this.registros = new ArrayList<RegistroAsistencia>();
    }

    public String getId()
    {
        return id;
    }

    public Entrenador getEntrenador()
    {
        return entrenador;
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

    public int getDuracionMinutos()
    {
        return duracionMinutos;
    }

    public TipoSesion getTipo()
    {
        return tipo;
    }

    public Equipo getEquipo()
    {
        return equipo;
    }

    public void setEquipo(Equipo equipo)
    {
        this.equipo = equipo;
    }

    public DisciplinaIndividual getDisciplina()
    {
        return disciplina;
    }

    public void setDisciplina(DisciplinaIndividual disciplina)
    {
        this.disciplina = disciplina;
    }

    public boolean isCerrada()
    {
        return cerrada;
    }

    public List<RegistroAsistencia> getRegistros()
    {
        return registros;
    }

    public void agregarRegistro(RegistroAsistencia registro)
    {
        registros.add(registro);
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Convoca al jugador: crea su registro, aun sin estado.
     *
     * Que debe verificar:
     * - Que la sesion no este cerrada.
     * - Que el jugador no este ya convocado.
     * - Que el jugador este inscrito en el equipo o la disciplina de la sesion.
     *
     * Reglas: RN-27
     */
    public RegistroAsistencia convocar(PerfilJugador jugador) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-27]: SesionEntrenamiento.convocar");
    }

    /**
     * Marca la asistencia de un convocado; la nota y el comentario son opcionales (null).
     *
     * Que debe verificar:
     * - Que el jugador este convocado.
     * - Que la sesion no este cerrada.
     * - Que la nota, si viene, este entre 1 y 5.
     *
     * Reglas: RN-27
     */
    public void registrarAsistencia(PerfilJugador jugador, EstadoAsistencia estado, Integer notaDesempeno, String comentario) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-27]: SesionEntrenamiento.registrarAsistencia");
    }

    /**
     * Cierra la sesion: despues no se puede cambiar la asistencia.
     *
     * Que debe verificar:
     * - Que todos los convocados tengan estado.
     *
     * Reglas: RN-27
     */
    public void cerrar() throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-27]: SesionEntrenamiento.cerrar");
    }
}
