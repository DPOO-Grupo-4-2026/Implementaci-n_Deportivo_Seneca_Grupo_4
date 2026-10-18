package uniandes.dpoo.proyecto1.excepciones;

/**
 * Se supero un cupo o una capacidad.
 */
public class CupoExcedidoException extends ReglaDominioException
{
    private static final long serialVersionUID = 1L;

    public CupoExcedidoException(String regla, String mensaje)
    {
        super(regla, mensaje);
    }
}
