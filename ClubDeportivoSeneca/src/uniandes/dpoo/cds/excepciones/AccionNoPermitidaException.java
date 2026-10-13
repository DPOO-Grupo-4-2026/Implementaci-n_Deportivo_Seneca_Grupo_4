package uniandes.dpoo.cds.excepciones;

/**
 * El usuario no tiene permitido hacer esa operacion, o el objeto no esta en un estado que la admita.
 */
public class AccionNoPermitidaException extends ReglaDominioException
{
    private static final long serialVersionUID = 1L;

    public AccionNoPermitidaException(String regla, String mensaje)
    {
        super(regla, mensaje);
    }
}
