package uniandes.dpoo.proyecto1.interfaz;

import java.io.File;
import java.io.IOException;
import uniandes.dpoo.proyecto1.administracion.Club;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.persistencia.PersistenciaClub;
import uniandes.dpoo.proyecto1.usuarios.Administrador;
import uniandes.dpoo.proyecto1.usuarios.Empleado;
import uniandes.dpoo.proyecto1.usuarios.Socio;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * La aplicación del Club Deportivo Séneca. Aquí empieza todo.
 *
 * Al arrancar carga el club de datos/club.txt. Si ese archivo todavía no existe, carga el club de ejemplo de
 * datos/club_ejemplo.txt. Luego da la bienvenida y pide el login. Según el rol de quien entra, abre el menú del
 * socio, del empleado o del administrador. Cada vez que alguien cierra sesión, el club se guarda en
 * datos/club.txt, así que lo que se haga queda para la próxima vez.
 */
public class AplicacionClub
{
    private static final File ARCHIVO = new File("datos/club.txt");
    private static final File ARCHIVO_EJEMPLO = new File("datos/club_ejemplo.txt");

    private Club club;
    private PersistenciaClub persistencia;

    public AplicacionClub()
    {
        persistencia = new PersistenciaClub();
    }

    public static void main(String[] args)
    {
        AplicacionClub aplicacion = new AplicacionClub();
        if (aplicacion.cargarClub())
        {
            aplicacion.iniciar();
        }
    }

    /**
     * Carga el club guardado, o el de ejemplo si todavía no hay nada guardado. Devuelve false si no pudo
     * cargar ninguno de los dos.
     */
    private boolean cargarClub()
    {
        File archivo = ARCHIVO;
        if (!archivo.exists())
        {
            archivo = ARCHIVO_EJEMPLO;
        }
        try
        {
            club = persistencia.cargar(archivo);
            Entrada.aviso("Se cargo el club de " + archivo.getPath() + ".");
            return true;
        }
        catch (IOException e)
        {
            Entrada.aviso("No se pudo cargar el club: " + e.getMessage());
            return false;
        }
    }

    private void guardarClub()
    {
        try
        {
            persistencia.guardar(club, ARCHIVO);
            Entrada.aviso("Cambios guardados en " + ARCHIVO.getPath() + ".");
        }
        catch (IOException e)
        {
            Entrada.aviso("No se pudo guardar el club: " + e.getMessage());
        }
    }

    private void iniciar()
    {
        boolean seguir = true;
        while (seguir)
        {
            Entrada.titulo("Bienvenido al " + club.getNombre());
            Entrada.linea("Horario: " + club.getHoraApertura() + " a " + club.getHoraCierre());
            int opcion = Entrada.menu(new String[] {
                "Iniciar sesion",
                "Ver los usuarios de ejemplo",
                "Volver a empezar con el club de ejemplo" }, "Salir");

            if (opcion == 1)
            {
                iniciarSesion();
            }
            else if (opcion == 2)
            {
                mostrarUsuariosDeEjemplo();
            }
            else if (opcion == 3)
            {
                reiniciar();
            }
            else
            {
                seguir = false;
            }
        }
        guardarClub();
        Entrada.aviso("Hasta pronto.");
    }

    /**
     * RF-S-01, RF-E-01, RF-A-01 - Autenticarse
     *
     * Pide login y clave. Si el club los acepta, abre el menú que le toca según su rol. El rol lo dice el
     * mismo usuario con getRol(), así que aquí solo se decide qué menú abrir.
     */
    private void iniciarSesion()
    {
        Entrada.subtitulo("Iniciar sesion");
        String login = Entrada.texto("Login");
        String clave = Entrada.texto("Clave");
        Usuario usuario;
        try
        {
            usuario = club.autenticar(login, clave);
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
            Entrada.pausa();
            return;
        }

        Entrada.exito("Hola, " + usuario.getNombre() + ". Entraste como " + usuario.getRol() + ".");
        String rol = usuario.getRol();
        if (rol.equals("Socio"))
        {
            new MenuSocio(club, (Socio) usuario).mostrar();
        }
        else if (rol.equals("Administrador"))
        {
            new MenuAdministrador(club, (Administrador) usuario).mostrar();
        }
        else
        {
            // Entrenador o fisioterapeuta: los dos son empleados
            new MenuEmpleado(club, (Empleado) usuario).mostrar();
        }
        guardarClub();
    }

    private void mostrarUsuariosDeEjemplo()
    {
        Entrada.subtitulo("Usuarios de ejemplo (login / clave)");
        Entrada.linea("Socios:          sofia / sofia123      valentina / vale123");
        Entrada.linea("                 mateo / mateo123      andres / andres123");
        Entrada.linea("Entrenadores:    ent1 / ent123 (Carlos)   ent2 / ent123 (Diana)   ent3 / ent123 (Felipe)");
        Entrada.linea("Fisioterapeutas: fis1 / fis123 (Paula)   fis2 / fis123 (Jorge)");
        Entrada.linea("Administradora:  admin / admin123 (Laura)");
        Entrada.linea("Jugadores de los equipos de futbol: jugA1 ... jugA12 y jugB1 ... jugB12, clave: clave");
        Entrada.pausa();
    }

    /**
     * Vuelve al club de ejemplo y lo guarda encima de lo que había.
     */
    private void reiniciar()
    {
        if (Entrada.confirmar("Se pierde todo lo que se ha hecho y se vuelve al club de ejemplo. Seguro?"))
        {
            try
            {
                club = persistencia.cargar(ARCHIVO_EJEMPLO);
                guardarClub();
            }
            catch (IOException e)
            {
                Entrada.aviso("No se pudo cargar el club de ejemplo: " + e.getMessage());
            }
        }
    }
}
