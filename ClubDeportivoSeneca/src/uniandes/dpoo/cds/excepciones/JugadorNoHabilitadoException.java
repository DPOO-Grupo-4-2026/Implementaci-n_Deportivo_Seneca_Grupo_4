package uniandes.dpoo.cds.excepciones;

/**
 * El jugador no puede participar: sancion, nomina, edad o nivel.
 */
public class JugadorNoHabilitadoException extends ReglaDominioException
{
    private static final long serialVersionUID = 1L;

    public JugadorNoHabilitadoException(String regla, String mensaje)
    {
        super(regla, mensaje);
    }
}
