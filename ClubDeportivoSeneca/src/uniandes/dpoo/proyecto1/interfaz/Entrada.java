package uniandes.dpoo.proyecto1.interfaz;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;

/**
 * Todo lo que la aplicación le pregunta o le muestra al usuario pasa por aquí: títulos, menús, números,
 * fechas y mensajes. Así los tres menús se ven igual y ninguno tiene que preocuparse por lo que la persona
 * escriba mal: cada método vuelve a preguntar hasta recibir algo válido.
 */
public class Entrada
{
    private static final String LINEA_DOBLE = "==========================================================";
    private static final String LINEA = "----------------------------------------------------------";

    private static Scanner lector = new Scanner(System.in);

    // ------------------------------------------------------------------
    // Mostrar
    // ------------------------------------------------------------------

    public static void titulo(String texto)
    {
        System.out.println();
        System.out.println(LINEA_DOBLE);
        System.out.println("  " + texto.toUpperCase());
        System.out.println(LINEA_DOBLE);
    }

    public static void subtitulo(String texto)
    {
        System.out.println();
        System.out.println("  " + texto);
        System.out.println("  " + LINEA.substring(0, Math.min(LINEA.length(), texto.length())));
    }

    public static void linea(String texto)
    {
        System.out.println("  " + texto);
    }

    public static void exito(String texto)
    {
        System.out.println();
        System.out.println("  [OK] " + texto);
    }

    /**
     * Muestra por qué el modelo no dejó hacer algo. El mensaje de la excepción ya trae el número de la regla.
     */
    public static void rechazo(ReglaDominioException e)
    {
        System.out.println();
        System.out.println("  [NO SE PUDO] " + e.getMessage());
    }

    public static void aviso(String texto)
    {
        System.out.println();
        System.out.println("  " + texto);
    }

    public static void pausa()
    {
        System.out.println();
        System.out.print("  Presione Enter para continuar...");
        lector.nextLine();
    }

    // ------------------------------------------------------------------
    // Preguntar
    // ------------------------------------------------------------------

    /**
     * Muestra un menú numerado desde 1, con la opción 0 para volver, y devuelve lo que se escoja.
     */
    public static int menu(String[] opciones, String textoCero)
    {
        System.out.println();
        for (int i = 0; i < opciones.length; i++)
        {
            String numero = String.valueOf(i + 1);
            if (numero.length() == 1)
            {
                numero = " " + numero;
            }
            System.out.println("  " + numero + ". " + opciones[i]);
        }
        System.out.println("   0. " + textoCero);
        return entero("Escoja una opcion", 0, opciones.length);
    }

    /**
     * Muestra una lista numerada y devuelve la posición escogida (desde 0), o -1 si la persona escoge volver
     * o si la lista está vacía.
     */
    public static int escoger(String pregunta, List<String> nombres)
    {
        return escoger(pregunta, nombres, "Volver");
    }

    /**
     * Igual que el anterior, pero dice qué significa el 0: por ejemplo "Terminar" cuando se están agregando
     * productos a una compra.
     */
    public static int escoger(String pregunta, List<String> nombres, String textoCero)
    {
        if (nombres.isEmpty())
        {
            aviso("No hay nada para escoger.");
            return -1;
        }
        subtitulo(pregunta);
        for (int i = 0; i < nombres.size(); i++)
        {
            String numero = String.valueOf(i + 1);
            if (numero.length() == 1 && nombres.size() > 9)
            {
                numero = " " + numero;
            }
            System.out.println("  " + numero + ". " + nombres.get(i));
        }
        System.out.println("  0. " + textoCero);
        return entero("Escoja", 0, nombres.size()) - 1;
    }

    public static String texto(String pregunta)
    {
        System.out.print("  " + pregunta + ": ");
        return lector.nextLine().trim();
    }

    /**
     * Pide un número entero entre minimo y maximo. Si escriben otra cosa, lo vuelve a pedir.
     */
    public static int entero(String pregunta, int minimo, int maximo)
    {
        while (true)
        {
            String texto = texto(pregunta);
            try
            {
                int numero = Integer.parseInt(texto);
                if (numero >= minimo && numero <= maximo)
                {
                    return numero;
                }
            }
            catch (NumberFormatException e)
            {
                // No era un número; se avisa abajo y se vuelve a pedir
            }
            System.out.println("  Escriba un numero entre " + minimo + " y " + maximo + ".");
        }
    }

    /**
     * Pide un número que se puede dejar vacío. Si se deja vacío devuelve null; se usa, por ejemplo, para una
     * nota de desempeño que no es obligatoria.
     */
    public static Integer enteroOpcional(String pregunta, int minimo, int maximo)
    {
        while (true)
        {
            String texto = texto(pregunta + " (Enter para dejarlo vacio)");
            if (texto.isEmpty())
            {
                return null;
            }
            try
            {
                int numero = Integer.parseInt(texto);
                if (numero >= minimo && numero <= maximo)
                {
                    return numero;
                }
            }
            catch (NumberFormatException e)
            {
                // Se vuelve a pedir
            }
            System.out.println("  Escriba un numero entre " + minimo + " y " + maximo + ", o deje vacio.");
        }
    }

    /**
     * Pide una fecha en formato AAAA-MM-DD. Con Enter se toma la fecha de hoy.
     */
    public static LocalDate fecha(String pregunta)
    {
        while (true)
        {
            String texto = texto(pregunta + " (AAAA-MM-DD, Enter = hoy)");
            if (texto.isEmpty())
            {
                return hoy();
            }
            try
            {
                return LocalDate.parse(texto);
            }
            catch (DateTimeParseException e)
            {
                System.out.println("  Fecha no valida. Ejemplo: " + hoy());
            }
        }
    }

    /**
     * Pide una hora en formato HH:MM, por ejemplo 07:30.
     */
    public static LocalTime hora(String pregunta)
    {
        while (true)
        {
            String texto = texto(pregunta + " (HH:MM)");
            try
            {
                return LocalTime.parse(texto);
            }
            catch (DateTimeParseException e)
            {
                System.out.println("  Hora no valida. Ejemplo: 07:30");
            }
        }
    }

    public static boolean confirmar(String pregunta)
    {
        while (true)
        {
            String texto = texto(pregunta + " (s/n)").toLowerCase();
            if (texto.equals("s") || texto.equals("si"))
            {
                return true;
            }
            if (texto.equals("n") || texto.equals("no"))
            {
                return false;
            }
        }
    }

    public static LocalDate hoy()
    {
        return LocalDate.now();
    }

    private static final String[] DIAS = { "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" };

    /**
     * El nombre del día en español. DayOfWeek empieza en lunes = 1.
     */
    public static String dia(DayOfWeek dia)
    {
        return DIAS[dia.getValue() - 1];
    }

    /**
     * Un valor de un enum escrito para personas: LLEGO_TARDE queda "Llego tarde".
     */
    public static String bonito(Enum<?> valor)
    {
        String texto = valor.toString().replace('_', ' ').toLowerCase();
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }

    /**
     * Escribe un valor en pesos sin decimales, con punto de miles: 96.093
     */
    public static String pesos(double valor)
    {
        return "$" + String.format(Locale.US, "%,.0f", valor).replace(',', '.');
    }
}
