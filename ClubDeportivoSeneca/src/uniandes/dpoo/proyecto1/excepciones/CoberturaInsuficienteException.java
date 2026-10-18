package uniandes.dpoo.proyecto1.excepciones;

/**
 * El cambio dejaria una franja sin el personal mínimo.
 */
public class CoberturaInsuficienteException extends ReglaDominioException
{
    private static final long serialVersionUID = 1L;

    public CoberturaInsuficienteException(String regla, String mensaje)
    {
        super(regla, mensaje);
    }
}
