package uniandes.dpoo.cds.modelo.usuarios;

import java.time.LocalDate;
import uniandes.dpoo.cds.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;

/**
 * Responsable de la operacion administrativa y financiera.
 */
public class Administrador extends Usuario
{
    public Administrador(String login, String password, String nombre, LocalDate fechaNacimiento)
    {
        super(login, password, nombre, fechaNacimiento);
    }

    @Override
    public String getRol()
    {
        return "Administrador";
    }

    // ------------------------------------------------------------------
    // Logica del dominio
    // ------------------------------------------------------------------

    /**
     * El administrador no puede jugar ni reservar: siempre rechaza.
     *
     * Reglas: RN-38
     *
     * Por que asi: La regla la impone la jerarquia: se redefine una vez en lugar de preguntar el rol en cada operacion.
     */
    @Override
    public PerfilJugador crearPerfilJugador() throws ReglaDominioException
    {
        throw new AccionNoPermitidaException("RN-38", "El administrador no puede tener perfil de jugador");
    }
}
