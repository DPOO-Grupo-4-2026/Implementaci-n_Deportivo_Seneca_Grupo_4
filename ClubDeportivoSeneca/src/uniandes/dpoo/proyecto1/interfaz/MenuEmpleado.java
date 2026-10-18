package uniandes.dpoo.proyecto1.interfaz;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.administracion.Club;
import uniandes.dpoo.proyecto1.administracion.SolicitudCambioTurno;
import uniandes.dpoo.proyecto1.administracion.Turno;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaBaloncesto;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaFutbol;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaPartido;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaVoleibol;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.conjunto.PartidoInterno;
import uniandes.dpoo.proyecto1.entrenamiento.EstadoAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.RegistroAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.individuales.DisciplinaIndividual;
import uniandes.dpoo.proyecto1.individuales.InscripcionDisciplina;
import uniandes.dpoo.proyecto1.individuales.Instalacion;
import uniandes.dpoo.proyecto1.tienda.SugerenciaProducto;
import uniandes.dpoo.proyecto1.usuarios.Empleado;
import uniandes.dpoo.proyecto1.usuarios.Entrenador;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Lo que puede hacer un empleado (entrenador o fisioterapeuta): registrar los partidos internos con sus
 * estadísticas y tarjetas, revisar quién está habilitado, pedir cambios de turno, sugerir productos y
 * comprar con su código de descuento. El entrenador, además, arma sesiones de entrenamiento y registra la
 * asistencia.
 */
public class MenuEmpleado
{
    private Club club;
    private Empleado empleado;

    public MenuEmpleado(Club club, Empleado empleado)
    {
        this.club = club;
        this.empleado = empleado;
    }

    private boolean esEntrenador()
    {
        return empleado.getRol().equals("Entrenador");
    }

    public void mostrar()
    {
        boolean seguir = true;
        while (seguir)
        {
            Entrada.titulo(empleado.getRol() + ": " + empleado.getNombre());
            List<String> opciones = new ArrayList<String>();
            opciones.add("Programar un partido interno              (RF-E-05)");
            opciones.add("Registrar estadisticas de un partido      (RF-E-05)");
            opciones.add("Cerrar un partido y aplicar tarjetas      (RF-E-06)");
            opciones.add("Revisar los habilitados de un equipo      (RF-E-07)");
            opciones.add("Consultar asistencia de un jugador        (RF-E-09)");
            opciones.add("Ver mis turnos y pedir un cambio          (RF-E-10)");
            opciones.add("Sugerir un producto o snack               (RF-E-11)");
            opciones.add("Comprar en la tienda con mi codigo        (RF-E-12)");
            opciones.add("Comprar en la cafeteria con mi codigo     (RF-E-12)");
            if (esEntrenador())
            {
                opciones.add("Crear una sesion y convocar jugadores     (RF-E-02)");
                opciones.add("Registrar asistencia y evaluacion         (RF-E-03, RF-E-04)");
            }
            int opcion = Entrada.menu(opciones.toArray(new String[0]), "Cerrar sesion");

            switch (opcion)
            {
                case 1: OpcionesComunes.programarPartidoInterno(club); Entrada.pausa(); break;
                case 2: registrarEstadisticas(); break;
                case 3: cerrarPartido(); break;
                case 4: revisarHabilitados(); break;
                case 5: OpcionesComunes.consultarAsistencia(club); break;
                case 6: turnos(); break;
                case 7: sugerirProducto(); break;
                case 8: mostrarCodigo(); OpcionesComunes.comprarEnTienda(club, empleado); break;
                case 9: mostrarCodigo(); OpcionesComunes.comprarEnCafeteria(club, empleado); break;
                case 10: crearSesion(); break;
                case 11: registrarAsistencia(); break;
                default: seguir = false;
            }
        }
    }

    // ------------------------------------------------------------------
    // Partidos
    // ------------------------------------------------------------------

    private PartidoInterno escogerPartidoPorJugar(String pregunta)
    {
        List<PartidoInterno> porJugar = new ArrayList<PartidoInterno>();
        List<String> nombres = new ArrayList<String>();
        for (PartidoInterno partido : club.getPartidos())
        {
            if (!partido.isJugado())
            {
                porJugar.add(partido);
                nombres.add(OpcionesComunes.describirPartido(partido));
            }
        }
        int posicion = Entrada.escoger(pregunta, nombres);
        if (posicion < 0)
        {
            return null;
        }
        return porJugar.get(posicion);
    }

    /**
     * RF-E-05 - Registrar el partido y sus estadísticas
     *
     * Se escoge un jugador de cualquiera de los dos equipos y se escriben sus números. Cada deporte tiene su
     * clase de estadística; como aquí se crea el objeto a partir de lo que escribe la persona, hay que decidir
     * cuál crear según el deporte del partido. El partido revisa que el jugador pueda jugar (RN-12) y que no
     * tenga ya estadística (RN-10).
     */
    private void registrarEstadisticas()
    {
        PartidoInterno partido = escogerPartidoPorJugar("De que partido?");
        if (partido == null)
        {
            return;
        }
        List<PerfilJugador> jugadores = new ArrayList<PerfilJugador>();
        List<String> nombres = new ArrayList<String>();
        agregarJugadores(partido.getLocal(), jugadores, nombres);
        agregarJugadores(partido.getVisitante(), jugadores, nombres);
        int posicion = Entrada.escoger("Que jugador?", nombres);
        if (posicion < 0)
        {
            return;
        }
        PerfilJugador jugador = jugadores.get(posicion);

        String deporte = partido.getLocal().getDeporte().getNombre();
        EstadisticaPartido estadistica;
        if (deporte.equals("Futbol"))
        {
            int goles = Entrada.entero("Goles", 0, 30);
            int asistencias = Entrada.entero("Asistencias", 0, 30);
            int amarillas = Entrada.entero("Tarjetas amarillas", 0, 2);
            int rojas = Entrada.entero("Tarjetas rojas", 0, 1);
            estadistica = new EstadisticaFutbol(jugador, partido, goles, asistencias, amarillas, rojas);
        }
        else if (deporte.equals("Baloncesto"))
        {
            int puntos = Entrada.entero("Puntos", 0, 200);
            int rebotes = Entrada.entero("Rebotes", 0, 100);
            estadistica = new EstadisticaBaloncesto(jugador, partido, puntos, rebotes);
        }
        else
        {
            int aces = Entrada.entero("Aces", 0, 100);
            int bloqueos = Entrada.entero("Bloqueos", 0, 100);
            estadistica = new EstadisticaVoleibol(jugador, partido, aces, bloqueos);
        }

        try
        {
            partido.registrarEstadistica(estadistica);
            Entrada.exito("Estadistica registrada: " + jugador.getUsuario().getNombre() + ", " + estadistica.describir());
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    private void agregarJugadores(Equipo equipo, List<PerfilJugador> jugadores, List<String> nombres)
    {
        for (InscripcionEquipo inscripcion : equipo.getInscripciones())
        {
            String estado = "";
            if (!inscripcion.estaHabilitado())
            {
                estado = " [NO habilitado]";
            }
            jugadores.add(inscripcion.getJugador());
            nombres.add(inscripcion.getJugador().getUsuario().getNombre() + " - " + equipo.getNombre() + " #"
                    + inscripcion.getNumeroCamiseta() + estado);
        }
    }

    /**
     * RF-E-06 - Registrar tarjetas y suspensiones
     *
     * Al cerrar el partido, primero los sancionados de los dos equipos cumplen un partido y después las
     * tarjetas de hoy se vuelven sanciones. Al final se muestra quién quedó sin poder jugar.
     */
    private void cerrarPartido()
    {
        PartidoInterno partido = escogerPartidoPorJugar("Que partido termino?");
        if (partido == null)
        {
            return;
        }
        int local = Entrada.entero("Goles o puntos de " + partido.getLocal().getNombre(), 0, 300);
        int visitante = Entrada.entero("Goles o puntos de " + partido.getVisitante().getNombre(), 0, 300);
        try
        {
            partido.cerrar(local, visitante);
            Entrada.exito("Partido cerrado: " + OpcionesComunes.describirPartido(partido));
            mostrarNoHabilitados(partido.getLocal());
            mostrarNoHabilitados(partido.getVisitante());
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    private void mostrarNoHabilitados(Equipo equipo)
    {
        List<InscripcionEquipo> noHabilitados = equipo.consultarNoHabilitados();
        if (noHabilitados.isEmpty())
        {
            Entrada.linea(equipo.getNombre() + ": todos habilitados.");
        }
        for (InscripcionEquipo inscripcion : noHabilitados)
        {
            Entrada.linea(equipo.getNombre() + ": " + inscripcion.getJugador().getUsuario().getNombre() + " queda suspendido.");
        }
    }

    /**
     * RF-E-07 - Verificar el mínimo de habilitados
     */
    private void revisarHabilitados()
    {
        Equipo equipo = OpcionesComunes.escogerEquipo(club, "Que equipo quiere revisar?");
        if (equipo == null)
        {
            return;
        }
        int minimo = equipo.getDeporte().getMinJugadoresHabilitados();
        Entrada.subtitulo(equipo.getNombre());
        Entrada.linea("Inscritos: " + equipo.getInscripciones().size());
        Entrada.linea("Habilitados: " + equipo.contarHabilitados() + " (minimo para jugar: " + minimo + ")");
        if (equipo.puedeDisputarPartido())
        {
            Entrada.linea("Puede jugar.");
        }
        else
        {
            Entrada.linea("NO puede jugar: le faltan jugadores habilitados.");
        }
        for (InscripcionEquipo inscripcion : equipo.consultarNoHabilitados())
        {
            Entrada.linea("No habilitado: " + inscripcion.getJugador().getUsuario().getNombre());
        }
        Entrada.pausa();
    }

    // ------------------------------------------------------------------
    // Entrenamiento
    // ------------------------------------------------------------------

    /**
     * RF-E-02 - Crear una sesión de entrenamiento y convocar
     *
     * La sesión es de un equipo o de una disciplina. Después se convocan jugadores uno por uno; la sesión
     * revisa que sean de ese equipo o disciplina (RN-27).
     */
    private void crearSesion()
    {
        List<String> nombres = new ArrayList<String>();
        for (Instalacion instalacion : club.getInstalaciones())
        {
            nombres.add(instalacion.getNombre());
        }
        int posicion = Entrada.escoger("En que instalacion?", nombres);
        if (posicion < 0)
        {
            return;
        }
        Instalacion instalacion = club.getInstalaciones().get(posicion);
        LocalDate fecha = Entrada.fecha("Fecha");
        LocalTime hora = Entrada.hora("Hora de inicio");
        int duracion = Entrada.entero("Duracion en minutos", 30, 240);
        String id = "S-" + (club.getSesiones().size() + 1);
        SesionEntrenamiento sesion = new SesionEntrenamiento(id, (Entrenador) empleado, instalacion, fecha, hora, duracion);

        // Los candidatos a convocar salen del equipo o de la disciplina de la sesión
        List<PerfilJugador> candidatos = new ArrayList<PerfilJugador>();
        if (Entrada.entero("La sesion es de: 1 = un equipo, 2 = una disciplina individual", 1, 2) == 1)
        {
            Equipo equipo = OpcionesComunes.escogerEquipo(club, "De que equipo?");
            if (equipo == null)
            {
                return;
            }
            sesion.setEquipo(equipo);
            for (InscripcionEquipo inscripcion : equipo.getInscripciones())
            {
                candidatos.add(inscripcion.getJugador());
            }
        }
        else
        {
            List<String> disciplinas = new ArrayList<String>();
            for (DisciplinaIndividual disciplina : club.getDisciplinas())
            {
                disciplinas.add(disciplina.getNombre());
            }
            int cual = Entrada.escoger("De que disciplina?", disciplinas);
            if (cual < 0)
            {
                return;
            }
            DisciplinaIndividual disciplina = club.getDisciplinas().get(cual);
            sesion.setDisciplina(disciplina);
            for (InscripcionDisciplina inscripcion : disciplina.getInscripciones())
            {
                candidatos.add(inscripcion.getJugador());
            }
        }
        club.agregarSesion(sesion);
        Entrada.exito("Sesion " + id + " creada. Ahora convoque a los jugadores.");

        boolean seguir = true;
        while (seguir)
        {
            List<String> nombresCandidatos = new ArrayList<String>();
            for (PerfilJugador jugador : candidatos)
            {
                nombresCandidatos.add(jugador.getUsuario().getNombre());
            }
            int escogido = Entrada.escoger("A quien convoca?", nombresCandidatos, "Terminar de convocar");
            if (escogido < 0)
            {
                seguir = false;
            }
            else
            {
                try
                {
                    sesion.convocar(candidatos.get(escogido));
                    Entrada.exito(candidatos.get(escogido).getUsuario().getNombre() + " convocado.");
                    candidatos.remove(escogido);
                }
                catch (ReglaDominioException e)
                {
                    Entrada.rechazo(e);
                }
            }
        }
        Entrada.aviso("La sesion " + id + " tiene " + sesion.getRegistros().size() + " convocados.");
        Entrada.pausa();
    }

    /**
     * RF-E-03, RF-E-04 - Registrar asistencia y evaluación
     *
     * Para cada convocado se marca si asistió, faltó o llegó tarde, con una nota opcional de 1 a 5. Al
     * final se puede cerrar la sesión; la sesión no deja cerrarse si falta marcar a alguien (RN-27).
     */
    private void registrarAsistencia()
    {
        List<SesionEntrenamiento> abiertas = new ArrayList<SesionEntrenamiento>();
        List<String> nombres = new ArrayList<String>();
        for (SesionEntrenamiento sesion : club.getSesiones())
        {
            if (sesion.getEntrenador() == empleado && !sesion.isCerrada())
            {
                abiertas.add(sesion);
                nombres.add(sesion.getId() + " | " + sesion.getFecha() + " " + sesion.getHoraInicio() + " | "
                        + sesion.getInstalacion().getNombre() + " | " + sesion.getRegistros().size() + " convocados");
            }
        }
        int posicion = Entrada.escoger("Que sesion?", nombres);
        if (posicion < 0)
        {
            return;
        }
        SesionEntrenamiento sesion = abiertas.get(posicion);
        EstadoAsistencia[] estados = EstadoAsistencia.values();
        for (RegistroAsistencia registro : sesion.getRegistros())
        {
            PerfilJugador jugador = registro.getJugador();
            Entrada.subtitulo(jugador.getUsuario().getNombre());
            int estado = Entrada.entero("1 = asistio, 2 = falto, 3 = llego tarde", 1, 3);
            Integer nota = Entrada.enteroOpcional("Nota de 1 a 5", 1, 5);
            String comentario = Entrada.texto("Comentario (Enter si no hay)");
            try
            {
                sesion.registrarAsistencia(jugador, estados[estado - 1], nota, comentario);
            }
            catch (ReglaDominioException e)
            {
                Entrada.rechazo(e);
            }
        }
        if (Entrada.confirmar("Cerrar la sesion?"))
        {
            try
            {
                sesion.cerrar();
                Entrada.exito("Sesion " + sesion.getId() + " cerrada.");
            }
            catch (ReglaDominioException e)
            {
                Entrada.rechazo(e);
            }
        }
        Entrada.pausa();
    }

    // ------------------------------------------------------------------
    // Turnos, sugerencias y compras
    // ------------------------------------------------------------------

    /**
     * RF-E-10 - Solicitar un cambio de turno
     *
     * Se escoge uno de los turnos propios y a quién se le quiere ceder. La solicitud queda pendiente hasta
     * que el administrador la apruebe o la rechace.
     */
    private void turnos()
    {
        List<String> nombres = new ArrayList<String>();
        for (Turno turno : empleado.getTurnos())
        {
            nombres.add(Entrada.dia(turno.getDia()) + " de " + turno.getHoraInicio() + " a " + turno.getHoraFin());
        }
        int posicion = Entrada.escoger("Sus turnos. Cual quiere ceder?", nombres);
        if (posicion < 0)
        {
            return;
        }
        Turno turno = empleado.getTurnos().get(posicion);

        // Los empleados son los usuarios con rol de entrenador o fisioterapeuta
        List<Empleado> otros = new ArrayList<Empleado>();
        List<String> nombresOtros = new ArrayList<String>();
        for (Usuario usuario : club.getUsuarios())
        {
            String rol = usuario.getRol();
            if ((rol.equals("Entrenador") || rol.equals("Fisioterapeuta")) && usuario != empleado)
            {
                otros.add((Empleado) usuario);
                nombresOtros.add(usuario.getNombre() + " (" + rol + ")");
            }
        }
        int cual = Entrada.escoger("A quien se lo cede?", nombresOtros);
        if (cual < 0)
        {
            return;
        }
        String motivo = Entrada.texto("Motivo");
        try
        {
            SolicitudCambioTurno solicitud = club.solicitarCambioTurno(empleado, turno, otros.get(cual), Entrada.hoy(), motivo);
            Entrada.exito("Solicitud enviada. Estado: " + Entrada.bonito(solicitud.getEstado()));
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    /**
     * RF-E-11 - Sugerir productos
     */
    private void sugerirProducto()
    {
        String descripcion = Entrada.texto("Que producto o snack sugiere?");
        try
        {
            SugerenciaProducto sugerencia = club.registrarSugerencia(empleado, descripcion, Entrada.hoy());
            Entrada.exito("Sugerencia registrada. Estado: " + Entrada.bonito(sugerencia.getEstado()));
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    /**
     * RF-E-12 - Comprar con código de descuento o compartirlo
     *
     * Al pagar se escribe el código. Con su propio código el empleado tiene 15%; si se lo comparte a un
     * socio, el socio tiene 8% (RN-30).
     */
    private void mostrarCodigo()
    {
        if (empleado.getCodigoDescuento() != null)
        {
            Entrada.aviso("Su codigo de descuento es " + empleado.getCodigoDescuento().getCodigo()
                    + ". Escribalo al pagar, o compartalo con un socio.");
        }
    }
}
