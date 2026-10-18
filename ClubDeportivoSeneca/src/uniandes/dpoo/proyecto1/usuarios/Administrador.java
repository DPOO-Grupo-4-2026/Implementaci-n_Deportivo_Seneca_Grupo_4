package uniandes.dpoo.proyecto1.usuarios;

import java.time.LocalDate;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;

/**
 * Responsable de la operación administrativa y financiera.
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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-A-02 - Crear y dar de baja equipos y jugadores
     *
     * El administrador no puede jugar ni reservar (RN-38). En vez de preguntar el rol en cada operación,
     * Administrador cambia este método para que siempre diga que no.
     *
     * Reglas: RN-38
     */
    @Override
    public PerfilJugador crearPerfilJugador() throws ReglaDominioException
    {
        throw new AccionNoPermitidaException("RN-38", "El administrador no puede tener perfil de jugador");
    }
}
