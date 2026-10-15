package uniandes.dpoo.proyecto1.usuarios;

import java.time.LocalDate;

/**
 * Miembro del club: juega, reserva y compra.
 */
public class Socio extends Usuario
{
    private static final long serialVersionUID = 1L;

    public Socio(String login, String password, String nombre, LocalDate fechaNacimiento)
    {
        super(login, password, nombre, fechaNacimiento);
    }

    @Override
    public String getRol()
    {
        return "Socio";
    }
}
