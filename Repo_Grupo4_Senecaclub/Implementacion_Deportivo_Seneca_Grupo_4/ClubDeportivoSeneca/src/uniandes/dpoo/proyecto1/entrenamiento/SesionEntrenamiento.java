package uniandes.dpoo.proyecto1.entrenamiento;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.JugadorNoHabilitadoException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.individuales.DisciplinaIndividual;
import uniandes.dpoo.proyecto1.individuales.InscripcionDisciplina;
import uniandes.dpoo.proyecto1.individuales.Instalacion;
import uniandes.dpoo.proyecto1.usuarios.Entrenador;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Sesión planeada por un entrenador en una instalación. Es de un equipo o de una disciplina: el otro
 * queda en null.
 */
public class SesionEntrenamiento implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String id;
    private Entrenador entrenador;
    private Instalacion instalacion;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private int duracionMinutos;
    private Equipo equipo;
    private DisciplinaIndividual disciplina;
    private boolean cerrada;
    private List<RegistroAsistencia> registros;

    public SesionEntrenamiento(String id, Entrenador entrenador, Instalacion instalacion, LocalDate fecha, LocalTime horaInicio, int duracionMinutos)
    {
        this.id = id;
        this.entrenador = entrenador;
        this.instalacion = instalacion;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.duracionMinutos = duracionMinutos;
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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-02 - Crear una sesión de entrenamiento
     *
     * El entrenador llama a un jugador a la sesión. El jugador tiene que ser del equipo o de la disciplina
     * de la sesión.
     *
     * Reglas: RN-27
     */
    public RegistroAsistencia convocar(PerfilJugador jugador) throws ReglaDominioException
    {
        if (cerrada)
        {
            throw new AccionNoPermitidaException("RN-27", "La sesion " + id + " ya esta cerrada");
        }
        for (RegistroAsistencia registro : registros)
        {
            if (registro.getJugador() == jugador)
            {
                throw new AccionNoPermitidaException("RN-27", jugador.getUsuario().getNombre() + " ya esta convocado");
            }
        }
        boolean esDeLaSesion = false;
        if (equipo != null)
        {
            InscripcionEquipo enEquipo = jugador.buscarInscripcionEnDeporte(equipo.getDeporte());
            esDeLaSesion = enEquipo != null && enEquipo.getEquipo() == equipo;
        }
        else
        {
            for (InscripcionDisciplina inscripcion : jugador.getInscripcionesDisciplina())
            {
                if (inscripcion.getDisciplina() == disciplina)
                {
                    esDeLaSesion = true;
                }
            }
        }
        if (!esDeLaSesion)
        {
            throw new JugadorNoHabilitadoException("RN-27", jugador.getUsuario().getNombre() + " no es del equipo o la disciplina de la sesion");
        }

        RegistroAsistencia registro = new RegistroAsistencia(jugador);
        registros.add(registro);
        return registro;
    }

    /**
     * RF-E-03, RF-E-04 - Registrar asistencia / Registrar la evaluación de desempeño
     *
     * El entrenador marca si el convocado asistió, faltó o llegó tarde, y si quiere le pone una nota de 1
     * a 5 y un comentario.
     *
     * Reglas: RN-27
     */
    public void registrarAsistencia(PerfilJugador jugador, EstadoAsistencia estado, Integer notaDesempeno, String comentario) throws ReglaDominioException
    {
        if (cerrada)
        {
            throw new AccionNoPermitidaException("RN-27", "La sesion " + id + " ya esta cerrada");
        }
        if (notaDesempeno != null && (notaDesempeno < 1 || notaDesempeno > 5))
        {
            throw new AccionNoPermitidaException("RN-27", "La nota tiene que estar entre 1 y 5");
        }
        RegistroAsistencia registro = null;
        for (RegistroAsistencia r : registros)
        {
            if (r.getJugador() == jugador)
            {
                registro = r;
            }
        }
        if (registro == null)
        {
            throw new AccionNoPermitidaException("RN-27", jugador.getUsuario().getNombre() + " no esta convocado a la sesion " + id);
        }
        registro.setEstado(estado);
        registro.setNotaDesempeno(notaDesempeno);
        registro.setComentario(comentario);
    }

    /**
     * RF-E-03 - Registrar asistencia
     *
     * Cierra la sesión cuando todos los convocados tienen asistencia. Después ya no se puede cambiar.
     *
     * Reglas: RN-27
     */
    public void cerrar() throws ReglaDominioException
    {
        if (cerrada)
        {
            throw new AccionNoPermitidaException("RN-27", "La sesion " + id + " ya esta cerrada");
        }
        for (RegistroAsistencia registro : registros)
        {
            if (registro.getEstado() == null)
            {
                throw new AccionNoPermitidaException("RN-27", "Falta la asistencia de " + registro.getJugador().getUsuario().getNombre());
            }
        }
        cerrada = true;
    }
}
