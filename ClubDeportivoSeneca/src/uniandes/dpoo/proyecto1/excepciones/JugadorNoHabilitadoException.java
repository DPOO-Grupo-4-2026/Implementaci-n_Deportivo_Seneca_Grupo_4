package uniandes.dpoo.proyecto1.excepciones;

/**
 * El jugador no puede participar: sanción, nómina, edad o nivel.
 */
public class JugadorNoHabilitadoException extends ReglaDominioException
{
    private static final long serialVersionUID = 1L;

    public JugadorNoHabilitadoException(String regla, String mensaje)
    {
        super(regla, mensaje);
    }
}
