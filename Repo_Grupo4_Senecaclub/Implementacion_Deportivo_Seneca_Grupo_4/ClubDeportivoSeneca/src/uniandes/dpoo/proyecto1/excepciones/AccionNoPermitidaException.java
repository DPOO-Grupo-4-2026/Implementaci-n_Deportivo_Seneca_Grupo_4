package uniandes.dpoo.proyecto1.excepciones;

/**
 * El usuario no tiene permitido hacer esa operación, o el objeto no esta en un estado que la admita.
 */
public class AccionNoPermitidaException extends ReglaDominioException
{
    private static final long serialVersionUID = 1L;

    public AccionNoPermitidaException(String regla, String mensaje)
    {
        super(regla, mensaje);
    }
}
