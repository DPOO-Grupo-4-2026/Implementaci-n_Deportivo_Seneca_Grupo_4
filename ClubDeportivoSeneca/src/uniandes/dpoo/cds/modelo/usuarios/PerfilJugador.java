package uniandes.dpoo.cds.modelo.usuarios;

import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.modelo.conjunto.DeporteConjunto;
import uniandes.dpoo.cds.modelo.conjunto.Equipo;
import uniandes.dpoo.cds.modelo.conjunto.InscripcionEquipo;
import uniandes.dpoo.cds.modelo.individuales.InscripcionDisciplina;

/**
 * Participacion de un usuario como jugador. Se asocia a Usuario y no hereda de Socio.
 */
public class PerfilJugador
{
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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Devuelve la inscripcion del jugador en un equipo activo de ese deporte, o null si no tiene.
     *
     * Reglas: RN-05
     *
     * Por que asi: Solo cuenta equipos activos: un equipo dado de baja no debe impedir que el jugador entre a otro.
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
     * Dice si el usuario detras del perfil es un entrenador.
     *
     * Reglas: RN-23
     */
    public boolean esEntrenador()
    {
        throw new UnsupportedOperationException("Pendiente [RN-23]: PerfilJugador.esEntrenador");
    }
}
