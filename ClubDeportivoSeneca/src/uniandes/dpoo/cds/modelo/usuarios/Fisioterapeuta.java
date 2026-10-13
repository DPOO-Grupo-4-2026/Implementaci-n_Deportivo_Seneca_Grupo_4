package uniandes.dpoo.cds.modelo.usuarios;

import java.time.LocalDate;

/**
 * Empleado responsable de la aptitud fisica de los jugadores.
 */
public class Fisioterapeuta extends Empleado
{
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
