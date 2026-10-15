package uniandes.dpoo.proyecto1.usuarios;

import java.time.LocalDate;

/**
 * Empleado responsable de la aptitud física de los jugadores.
 */
public class Fisioterapeuta extends Empleado
{
    private static final long serialVersionUID = 1L;

    public Fisioterapeuta(String login, String password, String nombre, LocalDate fechaNacimiento)
    {
        super(login, password, nombre, fechaNacimiento);
    }

    @Override
    public String getRol()
    {
        return "Fisioterapeuta";
    }
}
