package uniandes.dpoo.proyecto1.excepciones;

/**
 * La venta no cumple existencias, descuentos o seguridad.
 */
public class VentaRechazadaException extends ReglaDominioException
{
    private static final long serialVersionUID = 1L;

    public VentaRechazadaException(String regla, String mensaje)
    {
        super(regla, mensaje);
    }
}
