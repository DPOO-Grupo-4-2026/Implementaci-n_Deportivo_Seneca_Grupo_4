package uniandes.dpoo.cds.consola;

import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;
import uniandes.dpoo.cds.modelo.usuarios.Usuario;

/**
 * Utilidad de los programas de prueba: corre un escenario y muestra en consola como termino.
 *
 * Cada escenario termina de una de tres formas:
 * - OK: la operacion se hizo. Si devuelve algo, se muestra.
 * - RECHAZADO: el sistema lanzo una ReglaDominioException. Se muestra la regla que lo impidio.
 * - PENDIENTE: el metodo todavia no esta implementado.
 */
public class Escenario
{
    /**
     * Operacion que se quiere probar. Tiene un solo metodo, asi que se puede escribir como lambda:
     * () -> equipo.contarHabilitados() es lo mismo que una clase anonima que implementa ejecutar().
     */
    public interface Accion
    {
        Object ejecutar() throws ReglaDominioException;
    }

    /** Operacion que no devuelve nada. */
    public interface AccionSinResultado
    {
        void ejecutar() throws ReglaDominioException;
    }

    public static void titulo(String texto)
    {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }

    public static void probar(String descripcion, String esperado, Accion accion)
    {
        System.out.println();
        System.out.println(descripcion);
        System.out.println("  esperado : " + esperado);
        try
        {
            Object resultado = accion.ejecutar();
            System.out.println("  obtenido : OK" + (resultado != null ? " -> " + resultado : ""));
        }
        catch (ReglaDominioException e)
        {
            System.out.println("  obtenido : RECHAZADO " + e.getMessage());
        }
        catch (UnsupportedOperationException e)
        {
            System.out.println("  obtenido : " + e.getMessage());
        }
    }

    public static void probarAccion(String descripcion, String esperado, AccionSinResultado accion)
    {
        probar(descripcion, esperado, () -> {
            accion.ejecutar();
            return null;
        });
    }

    /** Devuelve el perfil de jugador del usuario; si no tiene, lo crea. */
    public static PerfilJugador perfilDe(Usuario usuario) throws ReglaDominioException
    {
        if (usuario.getPerfilJugador() != null)
        {
            return usuario.getPerfilJugador();
        }
        return usuario.crearPerfilJugador();
    }
}
