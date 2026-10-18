package uniandes.dpoo.proyecto1.interfaz;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.administracion.Club;
import uniandes.dpoo.proyecto1.administracion.GeneradorReportes;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaPartido;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.conjunto.Sancion;
import uniandes.dpoo.proyecto1.entrenamiento.RegistroAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.individuales.AjusteRanking;
import uniandes.dpoo.proyecto1.individuales.DisciplinaIndividual;
import uniandes.dpoo.proyecto1.individuales.InscripcionDisciplina;
import uniandes.dpoo.proyecto1.individuales.Instalacion;
import uniandes.dpoo.proyecto1.individuales.Modalidad;
import uniandes.dpoo.proyecto1.individuales.Nivel;
import uniandes.dpoo.proyecto1.individuales.PartidoPractica;
import uniandes.dpoo.proyecto1.individuales.Reserva;
import uniandes.dpoo.proyecto1.tienda.MovimientoPuntos;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Socio;

/**
 * Lo que puede hacer un socio: inscribirse en equipos y disciplinas, ver sus partidos, estadísticas y
 * sanciones, reservar canchas, jugar partidos de práctica y comprar en la tienda y la cafetería.
 *
 * El menú no revisa reglas: le pide cada operación al modelo y, si el modelo la rechaza, muestra el mensaje
 * con el número de la regla.
 */
public class MenuSocio
{
    private Club club;
    private Socio socio;

    public MenuSocio(Club club, Socio socio)
    {
        this.club = club;
        this.socio = socio;
    }

    public void mostrar()
    {
        boolean seguir = true;
        while (seguir)
        {
            Entrada.titulo("Socio: " + socio.getNombre());
            int opcion = Entrada.menu(new String[] {
                "Ver mis equipos y disciplinas            (RF-S-02)",
                "Inscribirme en un equipo                 (RF-S-03)",
                "Ver mi calendario de partidos            (RF-S-04)",
                "Ver mis estadisticas                     (RF-S-05)",
                "Ver mis sanciones                        (RF-S-06)",
                "Inscribirme en una disciplina individual (RF-S-07)",
                "Reservar una instalacion                 (RF-S-08)",
                "Programar un partido de practica         (RF-S-09)",
                "Registrar el resultado de una practica   (RF-S-10)",
                "Ver mi ranking                           (RF-S-10)",
                "Ver mis convocatorias y asistencias      (RF-S-11)",
                "Comprar en la tienda                     (RF-S-12)",
                "Comprar en la cafeteria                  (RF-S-13)",
                "Ver mis puntos de fidelidad              (RF-S-14)" }, "Cerrar sesion");

            switch (opcion)
            {
                case 1: verEquiposYDisciplinas(); break;
                case 2: inscribirseEnEquipo(); break;
                case 3: verCalendario(); break;
                case 4: verEstadisticas(); break;
                case 5: verSanciones(); break;
                case 6: inscribirseEnDisciplina(); break;
                case 7: reservar(); break;
                case 8: programarPractica(); break;
                case 9: registrarResultadoPractica(); break;
                case 10: verRanking(); break;
                case 11: verConvocatorias(); break;
                case 12: OpcionesComunes.comprarEnTienda(club, socio); break;
                case 13: OpcionesComunes.comprarEnCafeteria(club, socio); break;
                case 14: verPuntos(); break;
                default: seguir = false;
            }
        }
    }

    /**
     * Para inscribirse en algo hace falta el perfil de jugador. Si el socio todavía no lo tiene, se le crea.
     */
    private PerfilJugador perfil()
    {
        if (socio.getPerfilJugador() == null)
        {
            try
            {
                socio.crearPerfilJugador();
            }
            catch (ReglaDominioException e)
            {
                Entrada.rechazo(e);
            }
        }
        return socio.getPerfilJugador();
    }

    /**
     * RF-S-02 - Consultar mis equipos y disciplinas
     */
    private void verEquiposYDisciplinas()
    {
        PerfilJugador perfil = socio.getPerfilJugador();
        if (perfil == null)
        {
            Entrada.aviso("Todavia no esta inscrito en ningun equipo ni disciplina.");
            Entrada.pausa();
            return;
        }
        Entrada.subtitulo("Mis equipos");
        if (perfil.consultarEquipos().isEmpty())
        {
            Entrada.linea("Ninguno.");
        }
        for (InscripcionEquipo inscripcion : perfil.consultarEquipos())
        {
            String estado = "habilitado";
            if (!inscripcion.estaHabilitado())
            {
                estado = "NO habilitado";
            }
            Entrada.linea(inscripcion.getEquipo().getNombre() + " | " + inscripcion.getPosicion() + " | camiseta "
                    + inscripcion.getNumeroCamiseta() + " | " + estado);
        }
        Entrada.subtitulo("Mis disciplinas");
        if (perfil.getInscripcionesDisciplina().isEmpty())
        {
            Entrada.linea("Ninguna.");
        }
        for (InscripcionDisciplina inscripcion : perfil.getInscripcionesDisciplina())
        {
            Entrada.linea(inscripcion.getDisciplina().getNombre() + " | nivel " + Entrada.bonito(inscripcion.getNivel()) + " | ranking "
                    + inscripcion.getPuntajeRanking());
        }
        Entrada.pausa();
    }

    /**
     * RF-S-03 - Inscribirse en un equipo
     *
     * El equipo revisa el cupo, la edad, que no esté ya en otro equipo del mismo deporte y la camiseta.
     */
    private void inscribirseEnEquipo()
    {
        Equipo equipo = OpcionesComunes.escogerEquipo(club, "En que equipo se quiere inscribir?");
        if (equipo == null)
        {
            return;
        }
        String posicion = Entrada.texto("Posicion (por ejemplo Delantera, Base, Libero)");
        int camiseta = Entrada.entero("Numero de camiseta", 1, 99);
        PerfilJugador perfil = perfil();
        if (perfil == null)
        {
            return;
        }
        try
        {
            equipo.inscribirJugador(perfil, posicion, camiseta, Entrada.hoy());
            Entrada.exito("Quedo inscrito en " + equipo.getNombre() + " con la camiseta " + camiseta + ".");
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    /**
     * RF-S-04 - Consultar el calendario de partidos
     */
    private void verCalendario()
    {
        Entrada.subtitulo("Partidos de mis equipos");
        List<Partido> partidos = new ArrayList<Partido>();
        if (socio.getPerfilJugador() != null)
        {
            partidos = club.consultarCalendario(socio.getPerfilJugador());
        }
        if (partidos.isEmpty())
        {
            Entrada.linea("No hay partidos de sus equipos.");
        }
        for (Partido partido : partidos)
        {
            Entrada.linea(OpcionesComunes.describirPartido(partido));
        }
        Entrada.pausa();
    }

    /**
     * RF-S-05 - Consultar mis estadísticas
     */
    private void verEstadisticas()
    {
        Entrada.subtitulo("Mis estadisticas");
        List<EstadisticaPartido> estadisticas = new ArrayList<EstadisticaPartido>();
        if (socio.getPerfilJugador() != null)
        {
            estadisticas = club.consultarEstadisticas(socio.getPerfilJugador());
        }
        if (estadisticas.isEmpty())
        {
            Entrada.linea("Todavia no tiene estadisticas.");
        }
        for (EstadisticaPartido estadistica : estadisticas)
        {
            Partido partido = estadistica.getPartido();
            Entrada.linea(partido.getId() + " | " + partido.getFecha() + " | " + estadistica.describir());
        }
        Entrada.pausa();
    }

    /**
     * RF-S-06 - Consultar mis sanciones
     */
    private void verSanciones()
    {
        Entrada.subtitulo("Mis sanciones vigentes");
        List<Sancion> sanciones = new ArrayList<Sancion>();
        if (socio.getPerfilJugador() != null)
        {
            sanciones = socio.getPerfilJugador().consultarSancionesVigentes();
        }
        if (sanciones.isEmpty())
        {
            Entrada.linea("No tiene sanciones. Puede jugar.");
        }
        for (Sancion sancion : sanciones)
        {
            Entrada.linea(Entrada.bonito(sancion.getMotivo()) + " | le faltan " + sancion.getPartidosPendientes() + " partido(s) por cumplir");
        }
        Entrada.pausa();
    }

    private DisciplinaIndividual escogerDisciplina(String pregunta)
    {
        List<String> nombres = new ArrayList<String>();
        for (DisciplinaIndividual disciplina : club.getDisciplinas())
        {
            nombres.add(disciplina.getNombre());
        }
        int posicion = Entrada.escoger(pregunta, nombres);
        if (posicion < 0)
        {
            return null;
        }
        return club.getDisciplinas().get(posicion);
    }

    /**
     * Busca la inscripción del socio en una disciplina, o null si no está inscrito.
     */
    private InscripcionDisciplina miInscripcion(DisciplinaIndividual disciplina)
    {
        if (socio.getPerfilJugador() == null)
        {
            return null;
        }
        for (InscripcionDisciplina inscripcion : socio.getPerfilJugador().getInscripcionesDisciplina())
        {
            if (inscripcion.getDisciplina() == disciplina)
            {
                return inscripcion;
            }
        }
        return null;
    }

    /**
     * RF-S-07 - Inscribirse en una disciplina individual
     */
    private void inscribirseEnDisciplina()
    {
        DisciplinaIndividual disciplina = escogerDisciplina("En que disciplina se quiere inscribir?");
        if (disciplina == null)
        {
            return;
        }
        Nivel[] niveles = Nivel.values();
        List<String> nombres = new ArrayList<String>();
        for (Nivel nivel : niveles)
        {
            nombres.add(Entrada.bonito(nivel));
        }
        int posicion = Entrada.escoger("Cual es su nivel?", nombres);
        if (posicion < 0)
        {
            return;
        }
        PerfilJugador perfil = perfil();
        if (perfil == null)
        {
            return;
        }
        try
        {
            InscripcionDisciplina inscripcion = disciplina.inscribir(perfil, niveles[posicion]);
            Entrada.exito("Quedo inscrito en " + disciplina.getNombre() + ". Su ranking inicial es " + inscripcion.getPuntajeRanking() + ".");
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    /**
     * RF-S-08 - Reservar una instalación
     *
     * La disciplina revisa que la instalación esté libre, que acepte la modalidad y el número de jugadores, y
     * que no se pase del máximo de instalaciones ocupadas al tiempo.
     */
    private void reservar()
    {
        DisciplinaIndividual disciplina = escogerDisciplina("Para que disciplina es la reserva?");
        if (disciplina == null)
        {
            return;
        }
        List<String> nombres = new ArrayList<String>();
        for (Instalacion instalacion : disciplina.getInstalaciones())
        {
            String techo = "al aire libre";
            if (instalacion.isTechada())
            {
                techo = "techada";
            }
            nombres.add(instalacion.getNombre() + " (" + techo + ", hasta " + instalacion.getCapacidadMaxima() + " jugadores)");
        }
        int posicion = Entrada.escoger("Que instalacion?", nombres);
        if (posicion < 0)
        {
            return;
        }
        Instalacion instalacion = disciplina.getInstalaciones().get(posicion);
        LocalDate fecha = Entrada.fecha("Fecha");
        LocalTime inicio = Entrada.hora("Hora de inicio");
        LocalTime fin = Entrada.hora("Hora de fin");
        Modalidad modalidad = Modalidad.SENCILLOS;
        if (Entrada.entero("Modalidad: 1 = sencillos, 2 = dobles", 1, 2) == 2)
        {
            modalidad = Modalidad.DOBLES;
        }
        int jugadores = 2;
        if (modalidad == Modalidad.DOBLES)
        {
            jugadores = 4;
        }

        String id = "R-" + (contarReservas() + 1);
        try
        {
            disciplina.reservar(id, socio, instalacion, fecha, inicio, fin, modalidad, jugadores);
            Entrada.exito("Reserva " + id + ": " + instalacion.getNombre() + ", " + fecha + " de " + inicio + " a " + fin + ".");
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    private int contarReservas()
    {
        int total = 0;
        for (DisciplinaIndividual disciplina : club.getDisciplinas())
        {
            total = total + disciplina.getReservas().size();
        }
        return total;
    }

    /**
     * RF-S-09 - Programar un partido de práctica
     *
     * Sobre una reserva del socio que todavía no tenga partido. En sencillos se escoge un rival; en dobles,
     * un compañero y dos rivales. La disciplina revisa que todos estén inscritos y que los niveles puedan
     * jugar entre sí (RN-22, RN-23).
     */
    private void programarPractica()
    {
        List<Reserva> libres = new ArrayList<Reserva>();
        List<DisciplinaIndividual> deCual = new ArrayList<DisciplinaIndividual>();
        List<String> nombres = new ArrayList<String>();
        for (DisciplinaIndividual disciplina : club.getDisciplinas())
        {
            for (Reserva reserva : disciplina.getReservas())
            {
                if (reserva.getSolicitante() == socio && reserva.getPartido() == null)
                {
                    libres.add(reserva);
                    deCual.add(disciplina);
                    nombres.add(reserva.getId() + " | " + disciplina.getNombre() + " | " + reserva.getInstalacion().getNombre() + " | "
                            + reserva.getFecha() + " " + reserva.getHoraInicio() + " | " + Entrada.bonito(reserva.getModalidad()));
                }
            }
        }
        int posicion = Entrada.escoger("Sobre cual de sus reservas?", nombres);
        if (posicion < 0)
        {
            return;
        }
        Reserva reserva = libres.get(posicion);
        DisciplinaIndividual disciplina = deCual.get(posicion);
        InscripcionDisciplina yo = miInscripcion(disciplina);
        if (yo == null)
        {
            Entrada.aviso("Primero tiene que inscribirse en " + disciplina.getNombre() + ".");
            Entrada.pausa();
            return;
        }

        // El primero y el tercero de la lista juegan de un lado; el segundo y el cuarto, del otro
        List<InscripcionDisciplina> participantes = new ArrayList<InscripcionDisciplina>();
        participantes.add(yo);
        if (reserva.getModalidad() == Modalidad.SENCILLOS)
        {
            InscripcionDisciplina rival = escogerJugador(disciplina, participantes, "Contra quien juega?");
            if (rival == null)
            {
                return;
            }
            participantes.add(rival);
        }
        else
        {
            InscripcionDisciplina rival1 = escogerJugador(disciplina, participantes, "Primer rival");
            if (rival1 == null)
            {
                return;
            }
            participantes.add(rival1);
            InscripcionDisciplina companero = escogerJugador(disciplina, participantes, "Su companero de dobles");
            if (companero == null)
            {
                return;
            }
            participantes.add(companero);
            InscripcionDisciplina rival2 = escogerJugador(disciplina, participantes, "Segundo rival");
            if (rival2 == null)
            {
                return;
            }
            participantes.add(rival2);
        }

        String id = "PP-" + (contarPracticas() + 1);
        try
        {
            PartidoPractica partido = disciplina.programarPartidoPractica(id, reserva, participantes);
            Entrada.exito("Partido programado: " + partido.describir());
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    /**
     * Muestra los inscritos de la disciplina que todavía no están en la lista y devuelve el escogido.
     */
    private InscripcionDisciplina escogerJugador(DisciplinaIndividual disciplina, List<InscripcionDisciplina> yaEstan, String pregunta)
    {
        List<InscripcionDisciplina> opciones = new ArrayList<InscripcionDisciplina>();
        List<String> nombres = new ArrayList<String>();
        for (InscripcionDisciplina inscripcion : disciplina.getInscripciones())
        {
            if (!yaEstan.contains(inscripcion))
            {
                opciones.add(inscripcion);
                nombres.add(inscripcion.getJugador().getUsuario().getNombre() + " (" + Entrada.bonito(inscripcion.getNivel()) + ", ranking "
                        + inscripcion.getPuntajeRanking() + ")");
            }
        }
        int posicion = Entrada.escoger(pregunta, nombres);
        if (posicion < 0)
        {
            return null;
        }
        return opciones.get(posicion);
    }

    private int contarPracticas()
    {
        int total = 0;
        for (DisciplinaIndividual disciplina : club.getDisciplinas())
        {
            for (Reserva reserva : disciplina.getReservas())
            {
                if (reserva.getPartido() != null)
                {
                    total++;
                }
            }
        }
        return total;
    }

    /**
     * RF-S-10 - Consultar mi ranking
     *
     * Se registra el marcador de una práctica en la que jugó el socio. Al cerrarla, el partido le ajusta el
     * ranking a cada participante.
     */
    private void registrarResultadoPractica()
    {
        List<PartidoPractica> pendientes = new ArrayList<PartidoPractica>();
        List<String> nombres = new ArrayList<String>();
        PerfilJugador perfil = socio.getPerfilJugador();
        for (DisciplinaIndividual disciplina : club.getDisciplinas())
        {
            for (Reserva reserva : disciplina.getReservas())
            {
                PartidoPractica partido = reserva.getPartido();
                if (partido != null && !partido.isJugado() && reserva.getParticipantes().contains(perfil))
                {
                    pendientes.add(partido);
                    nombres.add(OpcionesComunes.describirPartido(partido));
                }
            }
        }
        int posicion = Entrada.escoger("Que partido termino?", nombres);
        if (posicion < 0)
        {
            return;
        }
        PartidoPractica partido = pendientes.get(posicion);
        List<InscripcionDisciplina> jugadores = partido.getParticipantes();
        String ladoUno = jugadores.get(0).getJugador().getUsuario().getNombre();
        String ladoDos = jugadores.get(1).getJugador().getUsuario().getNombre();
        if (jugadores.size() == 4)
        {
            ladoUno = ladoUno + " y " + jugadores.get(2).getJugador().getUsuario().getNombre();
            ladoDos = ladoDos + " y " + jugadores.get(3).getJugador().getUsuario().getNombre();
        }
        int marcadorUno = Entrada.entero("Sets o puntos de " + ladoUno, 0, 99);
        int marcadorDos = Entrada.entero("Sets o puntos de " + ladoDos, 0, 99);
        try
        {
            partido.cerrarYActualizarRanking(marcadorUno, marcadorDos);
            Entrada.exito("Resultado registrado. Rankings nuevos:");
            for (InscripcionDisciplina jugador : jugadores)
            {
                Entrada.linea(jugador.getJugador().getUsuario().getNombre() + ": " + jugador.getPuntajeRanking());
            }
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    /**
     * RF-S-10 - Consultar mi ranking y su evolución
     */
    private void verRanking()
    {
        Entrada.subtitulo("Mi ranking");
        if (socio.getPerfilJugador() == null || socio.getPerfilJugador().getInscripcionesDisciplina().isEmpty())
        {
            Entrada.linea("No esta inscrito en ninguna disciplina individual.");
        }
        else
        {
            for (InscripcionDisciplina inscripcion : socio.getPerfilJugador().getInscripcionesDisciplina())
            {
                Entrada.linea(inscripcion.getDisciplina().getNombre() + ": " + inscripcion.getPuntajeRanking() + " puntos (nivel "
                        + Entrada.bonito(inscripcion.getNivel()) + ")");
                for (AjusteRanking ajuste : new GeneradorReportes(club).evolucionDeRanking(inscripcion))
                {
                    Entrada.linea("   " + ajuste.getFecha() + ": " + ajuste.getPuntajeAntes() + " -> " + ajuste.getPuntajeDespues()
                            + " (partido " + ajuste.getPartido().getId() + ")");
                }
            }
        }
        Entrada.pausa();
    }

    /**
     * RF-S-11 - Consultar mis convocatorias y asistencias
     */
    private void verConvocatorias()
    {
        Entrada.subtitulo("Mis convocatorias");
        PerfilJugador perfil = socio.getPerfilJugador();
        boolean alguna = false;
        for (SesionEntrenamiento sesion : club.getSesiones())
        {
            for (RegistroAsistencia registro : sesion.getRegistros())
            {
                if (perfil != null && registro.getJugador() == perfil)
                {
                    alguna = true;
                    String estado = "pendiente";
                    if (registro.getEstado() != null)
                    {
                        estado = Entrada.bonito(registro.getEstado());
                    }
                    String nota = "";
                    if (registro.getNotaDesempeno() != null)
                    {
                        nota = " | nota " + registro.getNotaDesempeno();
                    }
                    Entrada.linea(sesion.getId() + " | " + sesion.getFecha() + " " + sesion.getHoraInicio() + " | "
                            + sesion.getInstalacion().getNombre() + " | con " + sesion.getEntrenador().getNombre() + " | " + estado + nota);
                }
            }
        }
        if (!alguna)
        {
            Entrada.linea("No lo han convocado a ninguna sesion.");
        }
        Entrada.pausa();
    }

    /**
     * RF-S-14 - Consultar y redimir puntos
     *
     * Los puntos se usan al pagar (en la tienda o la cafetería). Aquí se ve el saldo y de dónde salió.
     */
    private void verPuntos()
    {
        Entrada.subtitulo("Mis puntos de fidelidad");
        Entrada.linea("Saldo: " + socio.getSaldoPuntosFidelidad() + " puntos (1 punto = $1 al pagar)");
        for (MovimientoPuntos movimiento : socio.getMovimientosPuntos())
        {
            Entrada.linea(movimiento.getFecha() + " | " + Entrada.bonito(movimiento.getTipo()) + " | " + movimiento.getCantidad() + " | compra "
                    + movimiento.getVenta().getId());
        }
        Entrada.pausa();
    }
}
