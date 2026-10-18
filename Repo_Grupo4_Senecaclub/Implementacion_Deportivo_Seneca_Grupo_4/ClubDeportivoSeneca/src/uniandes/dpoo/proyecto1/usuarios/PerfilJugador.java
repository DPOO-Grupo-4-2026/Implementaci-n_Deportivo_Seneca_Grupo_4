package uniandes.dpoo.proyecto1.usuarios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.conjunto.DeporteConjunto;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.conjunto.Sancion;
import uniandes.dpoo.proyecto1.individuales.InscripcionDisciplina;

/**
 * Participación de un usuario como jugador. Se asocia a Usuario y no hereda de Socio.
 */
public class PerfilJugador implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Usuario usuario;
    private boolean activo;
    private List<InscripcionEquipo> inscripcionesEquipo;
    private List<InscripcionDisciplina> inscripcionesDisciplina;

    public PerfilJugador(Usuario usuario)
    {
        this.usuario = usuario;
        this.activo = true;
        this.inscripcionesEquipo = new ArrayList<InscripcionEquipo>();
        this.inscripcionesDisciplina = new ArrayList<InscripcionDisciplina>();
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public boolean isActivo()
    {
        return activo;
    }

    public void setActivo(boolean activo)
    {
        this.activo = activo;
    }

    public List<InscripcionEquipo> getInscripcionesEquipo()
    {
        return inscripcionesEquipo;
    }

    public void agregarInscripcionEquipo(InscripcionEquipo inscripcionEquipo)
    {
        inscripcionesEquipo.add(inscripcionEquipo);
    }

    public List<InscripcionDisciplina> getInscripcionesDisciplina()
    {
        return inscripcionesDisciplina;
    }

    public void agregarInscripcionDisciplina(InscripcionDisciplina inscripcionDisciplina)
    {
        inscripcionesDisciplina.add(inscripcionDisciplina);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-03, RF-E-05 - Inscribirse en un equipo / Registrar el partido y sus estadísticas
     *
     * Un jugador solo puede tener un equipo por deporte. Este método busca en qué equipo de ese deporte
     * está. Solo mira equipos activos: si su equipo se dio de baja, queda libre para entrar a otro.
     *
     * Reglas: RN-05
     */
    public InscripcionEquipo buscarInscripcionEnDeporte(DeporteConjunto deporte)
    {
        for (InscripcionEquipo inscripcion : inscripcionesEquipo)
        {
            Equipo equipo = inscripcion.getEquipo();
            if (equipo.getDeporte() == deporte && equipo.isActivo())
            {
                return inscripcion;
            }
        }
        return null;
    }

    /**
     * RF-S-02 - Consultar mis equipos y disciplinas
     *
     * El socio quiere ver en qué equipos está inscrito. Devolvemos sus inscripciones en equipos que siguen
     * activos; cada inscripción trae el equipo, la posición y la camiseta.
     *
     * Reglas: RN-05
     */
    public List<InscripcionEquipo> consultarEquipos()
    {
        List<InscripcionEquipo> activas = new ArrayList<InscripcionEquipo>();
        for (InscripcionEquipo inscripcion : inscripcionesEquipo)
        {
            if (inscripcion.getEquipo().isActivo())
            {
                activas.add(inscripcion);
            }
        }
        return activas;
    }

    /**
     * RF-S-06 - Consultar mis sanciones
     *
     * El socio quiere saber si está suspendido y por cuántos partidos. Recorremos sus equipos y juntamos
     * las sanciones que todavía no ha cumplido.
     *
     * Reglas: RN-11, RN-12
     */
    public List<Sancion> consultarSancionesVigentes()
    {
        List<Sancion> vigentes = new ArrayList<Sancion>();
        for (InscripcionEquipo inscripcion : inscripcionesEquipo)
        {
            for (Sancion sancion : inscripcion.getSanciones())
            {
                if (!sancion.isCumplida())
                {
                    vigentes.add(sancion);
                }
            }
        }
        return vigentes;
    }

    /**
     * RF-S-09 - Programar un partido de práctica
     *
     * Los partidos de práctica tienen una excepción: si uno de los jugadores es entrenador, no importa el
     * nivel. Este método responde si la persona detrás del perfil es entrenador.
     *
     * Reglas: RN-23
     */
    public boolean esEntrenador()
    {
        return usuario.getRol().equals("Entrenador");
    }
}
