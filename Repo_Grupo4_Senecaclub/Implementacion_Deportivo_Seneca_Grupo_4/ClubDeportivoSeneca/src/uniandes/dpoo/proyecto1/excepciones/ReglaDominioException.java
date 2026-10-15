package uniandes.dpoo.proyecto1.excepciones;

/**
 * Se lanza cuando una operación rompe una regla de dominio. Guarda el código de la regla (RN-xx) para
 * mostrarlo en consola.
 */
public class ReglaDominioException extends Exception
{
    private static final long serialVersionUID = 1L;

    private String regla;

    public ReglaDominioException(String regla, String mensaje)
    {
        super("[" + regla + "] " + mensaje);
        this.regla = regla;
    }

    public String getRegla()
    {
        return regla;
    }
}
