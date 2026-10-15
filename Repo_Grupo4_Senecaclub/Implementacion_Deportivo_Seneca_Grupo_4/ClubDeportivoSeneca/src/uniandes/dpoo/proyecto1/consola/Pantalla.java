package uniandes.dpoo.proyecto1.consola;

import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;

/**
 * Métodos pequeños para que las tres consolas impriman sus pruebas igual.
 *
 * Cada prueba muestra que hace, que se espera y que paso. Asi, al correr una consola, se puede comparar
 * línea por línea lo esperado con lo obtenido.
 */
public class Pantalla
{
    private static int numero = 0;

    public static void seccion(String titulo)
    {
        System.out.println();
        System.out.println("==== " + titulo + " ====");
    }

    public static void prueba(String queSeHace, String queSeEspera)
    {
        numero++;
        System.out.println();
        System.out.println(numero + ". " + queSeHace);
        System.out.println("   Se espera: " + queSeEspera);
    }

    public static void resultado(String texto)
    {
        System.out.println("   Resultado: " + texto);
    }

    public static void rechazo(ReglaDominioException e)
    {
        System.out.println("   Resultado: rechazado " + e.getMessage());
    }
}
