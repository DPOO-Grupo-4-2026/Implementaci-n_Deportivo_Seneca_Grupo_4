package uniandes.dpoo.proyecto1.excepciones;

/**
 * La reserva no cumple capacidad, cupo simultaneo o seguridad.
 */
public class ReservaRechazadaException extends ReglaDominioException
{
    private static final long serialVersionUID = 1L;

    public ReservaRechazadaException(String regla, String mensaje)
    {
        super(regla, mensaje);
    }
}
