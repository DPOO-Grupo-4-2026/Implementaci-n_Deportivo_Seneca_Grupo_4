package uniandes.dpoo.proyecto1.usuarios;

import java.time.LocalDate;

/**
 * Empleado que planea sesiones y dirige equipos.
 */
public class Entrenador extends Empleado
{
    private static final long serialVersionUID = 1L;

    public Entrenador(String login, String password, String nombre, LocalDate fechaNacimiento)
    {
        super(login, password, nombre, fechaNacimiento);
    }

    @Override
    public String getRol()
    {
        return "Entrenador";
    }
}
