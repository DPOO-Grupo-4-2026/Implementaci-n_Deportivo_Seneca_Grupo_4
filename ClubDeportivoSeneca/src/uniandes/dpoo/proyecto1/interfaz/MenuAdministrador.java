package uniandes.dpoo.proyecto1.interfaz;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uniandes.dpoo.proyecto1.administracion.Club;
import uniandes.dpoo.proyecto1.administracion.EstadoSolicitud;
import uniandes.dpoo.proyecto1.administracion.GeneradorReportes;
import uniandes.dpoo.proyecto1.administracion.SolicitudCambioTurno;
import uniandes.dpoo.proyecto1.administracion.Turno;
import uniandes.dpoo.proyecto1.competencias.Competencia;
import uniandes.dpoo.proyecto1.competencias.FilaTabla;
import uniandes.dpoo.proyecto1.competencias.InscripcionCompetencia;
import uniandes.dpoo.proyecto1.competencias.PartidoCompetencia;
import uniandes.dpoo.proyecto1.competencias.TablaPosiciones;
import uniandes.dpoo.proyecto1.conjunto.Categoria;
import uniandes.dpoo.proyecto1.conjunto.DeporteConjunto;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.individuales.DisciplinaIndividual;
import uniandes.dpoo.proyecto1.individuales.Instalacion;
import uniandes.dpoo.proyecto1.tienda.EstadoSugerencia;
import uniandes.dpoo.proyecto1.tienda.Existencia;
import uniandes.dpoo.proyecto1.tienda.Producto;
import uniandes.dpoo.proyecto1.tienda.SugerenciaProducto;
import uniandes.dpoo.proyecto1.tienda.UbicacionInventario;
import uniandes.dpoo.proyecto1.usuarios.Administrador;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Lo que puede hacer el administrador: manejar equipos, jugadores y competencias, el inventario y las
 * sugerencias, los turnos del personal y los reportes.
 */
public class MenuAdministrador
{
    private Club club;
    private Administrador administrador;

    public MenuAdministrador(Club club, Administrador administrador)
    {
        this.club = club;
        this.administrador = administrador;
    }

    public void mostrar()
    {
        boolean seguir = true;
        while (seguir)
        {
            Entrada.titulo("Administrador: " + administrador.getNombre());
            int opcion = Entrada.menu(new String[] {
                "Equipos y jugadores                      (RF-A-02)",
                "Competencias                             (RF-A-03, RF-E-08)",
                "Inventario: consultar, mover, reabastecer (RF-A-04, RF-A-05)",
                "Sugerencias de productos                 (RF-A-06)",
                "Partidos: programar e historial          (RF-A-07)",
                "Turnos y cobertura                       (RF-A-08)",
                "Solicitudes de cambio de turno           (RF-A-09)",
                "Reporte de ventas por rubro              (RF-A-10)",
                "Reporte de asistencia de un jugador      (RF-A-11)",
                "Instalaciones ocupadas                   (RF-A-12)" }, "Cerrar sesion");

            switch (opcion)
            {
                case 1: equiposYJugadores(); break;
                case 2: competencias(); break;
                case 3: inventario(); break;
                case 4: sugerencias(); break;
                case 5: partidos(); break;
                case 6: turnos(); break;
                case 7: solicitudesDeCambio(); break;
                case 8: reporteDeVentas(); break;
                case 9: OpcionesComunes.consultarAsistencia(club); break;
                case 10: instalacionesOcupadas(); break;
                default: seguir = false;
            }
        }
    }

    // ------------------------------------------------------------------
    // Equipos y jugadores
    // ------------------------------------------------------------------

    /**
     * RF-A-02 - Crear y dar de baja equipos y jugadores
     */
    private void equiposYJugadores()
    {
        Entrada.subtitulo("Equipos del club");
        for (Equipo equipo : club.getEquipos())
        {
            Entrada.linea(OpcionesComunes.nombreEquipo(equipo));
        }
        int opcion = Entrada.menu(new String[] {
            "Crear un equipo",
            "Dar de baja un equipo",
            "Crear el perfil de jugador de un usuario",
            "Dar de baja un jugador" }, "Volver");
        if (opcion == 1)
        {
            crearEquipo();
        }
        else if (opcion == 2)
        {
            Equipo equipo = OpcionesComunes.escogerEquipo(club, "Que equipo da de baja?");
            if (equipo != null)
            {
                try
                {
                    club.darDeBajaEquipo(administrador, equipo);
                    Entrada.exito(equipo.getNombre() + " quedo dado de baja. Sus partidos se conservan.");
                }
                catch (ReglaDominioException e)
                {
                    Entrada.rechazo(e);
                }
                Entrada.pausa();
            }
        }
        else if (opcion == 3)
        {
            crearPerfil();
        }
        else if (opcion == 4)
        {
            darDeBajaJugador();
        }
    }

    private void crearEquipo()
    {
        String nombre = Entrada.texto("Nombre del equipo");
        int cupo = Entrada.entero("Cupo maximo de jugadores", 0, 60);
        List<String> deportes = new ArrayList<String>();
        for (DeporteConjunto deporte : club.getDeportes())
        {
            deportes.add(deporte.getNombre());
        }
        int cualDeporte = Entrada.escoger("Deporte", deportes);
        if (cualDeporte < 0)
        {
            return;
        }
        List<String> categorias = new ArrayList<String>();
        for (Categoria categoria : club.getCategorias())
        {
            categorias.add(categoria.getNombre() + " (" + categoria.getEdadMinima() + " a " + categoria.getEdadMaxima() + " anos)");
        }
        int cualCategoria = Entrada.escoger("Categoria", categorias);
        if (cualCategoria < 0)
        {
            return;
        }
        try
        {
            Equipo equipo = club.crearEquipo(administrador, nombre, cupo, club.getDeportes().get(cualDeporte),
                    club.getCategorias().get(cualCategoria));
            Entrada.exito("Equipo creado: " + OpcionesComunes.nombreEquipo(equipo));
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    private void crearPerfil()
    {
        List<Usuario> sinPerfil = new ArrayList<Usuario>();
        List<String> nombres = new ArrayList<String>();
        for (Usuario usuario : club.getUsuarios())
        {
            if (usuario.getPerfilJugador() == null)
            {
                sinPerfil.add(usuario);
                nombres.add(usuario.getNombre() + " (" + usuario.getRol() + ")");
            }
        }
        int posicion = Entrada.escoger("A quien le crea el perfil de jugador?", nombres);
        if (posicion < 0)
        {
            return;
        }
        try
        {
            club.crearPerfilJugador(administrador, sinPerfil.get(posicion));
            Entrada.exito(sinPerfil.get(posicion).getNombre() + " ya puede inscribirse como jugador.");
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    private void darDeBajaJugador()
    {
        List<PerfilJugador> activos = new ArrayList<PerfilJugador>();
        List<String> nombres = new ArrayList<String>();
        for (Usuario usuario : club.getUsuarios())
        {
            PerfilJugador perfil = usuario.getPerfilJugador();
            if (perfil != null && perfil.isActivo())
            {
                activos.add(perfil);
                nombres.add(usuario.getNombre());
            }
        }
        int posicion = Entrada.escoger("A que jugador da de baja?", nombres);
        if (posicion < 0)
        {
            return;
        }
        try
        {
            club.darDeBajaJugador(administrador, activos.get(posicion));
            Entrada.exito(nombres.get(posicion) + " quedo dado de baja. Su historia se conserva.");
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    // ------------------------------------------------------------------
    // Competencias
    // ------------------------------------------------------------------

    /**
     * RF-A-03, RF-E-08 - Crear competencias, inscribir equipos con su nómina y registrar resultados
     */
    private void competencias()
    {
        List<String> nombres = new ArrayList<String>();
        for (Competencia competencia : club.getCompetencias())
        {
            nombres.add(competencia.getNombre() + " (" + competencia.getDeporte().getNombre() + " " + competencia.getCategoria().getNombre()
                    + ", nomina minima " + competencia.getNominaMinima() + ")");
        }
        int posicion = Entrada.escoger("Que competencia?", nombres);
        if (posicion < 0)
        {
            return;
        }
        Competencia competencia = club.getCompetencias().get(posicion);
        boolean seguir = true;
        while (seguir)
        {
            Entrada.titulo(competencia.getNombre());
            int opcion = Entrada.menu(new String[] {
                "Ver equipos inscritos, partidos y tablas",
                "Inscribir un equipo con su nomina",
                "Programar un partido",
                "Registrar el resultado de un partido" }, "Volver");
            switch (opcion)
            {
                case 1: verCompetencia(competencia); break;
                case 2: inscribirEnCompetencia(competencia); break;
                case 3: programarPartidoCompetencia(competencia); break;
                case 4: registrarResultado(competencia); break;
                default: seguir = false;
            }
        }
    }

    private void verCompetencia(Competencia competencia)
    {
        Entrada.subtitulo("Equipos inscritos");
        for (InscripcionCompetencia inscripcion : competencia.getInscripciones())
        {
            Entrada.linea(inscripcion.getEquipo().getNombre() + " (nomina de " + inscripcion.getNomina().getRenglones().size() + ")");
        }
        Entrada.subtitulo("Partidos");
        for (PartidoCompetencia partido : competencia.getPartidos())
        {
            Entrada.linea(OpcionesComunes.describirPartido(partido));
        }
        for (TablaPosiciones tabla : competencia.getTablas())
        {
            Entrada.subtitulo("Tabla: " + tabla.getNombre());
            Entrada.linea("Equipo                              PJ  G  E  P  GF GC PTS");
            for (FilaTabla fila : tabla.getFilas())
            {
                Entrada.linea(String.format("%-34s %3d %2d %2d %2d %3d %2d %3d", fila.getEquipo().getNombre(), fila.getPartidosJugados(),
                        fila.getGanados(), fila.getEmpatados(), fila.getPerdidos(), fila.getAFavor(), fila.getEnContra(), fila.getPuntos()));
            }
        }
        Entrada.pausa();
    }

    /**
     * La nómina son los jugadores del equipo que pueden jugar ese torneo. Se proponen todos los habilitados;
     * la competencia revisa que alcancen la nómina mínima (RN-16).
     */
    private void inscribirEnCompetencia(Competencia competencia)
    {
        Equipo equipo = OpcionesComunes.escogerEquipo(club, "Que equipo inscribe?");
        if (equipo == null)
        {
            return;
        }
        List<PerfilJugador> nomina = new ArrayList<PerfilJugador>();
        for (InscripcionEquipo inscripcion : equipo.getInscripciones())
        {
            if (inscripcion.estaHabilitado())
            {
                nomina.add(inscripcion.getJugador());
            }
        }
        Entrada.linea("Se propone una nomina con los " + nomina.size() + " jugadores habilitados del equipo.");
        try
        {
            competencia.inscribirEquipo(equipo, nomina);
            Entrada.exito(equipo.getNombre() + " quedo inscrito en " + competencia.getNombre() + ".");
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    private void programarPartidoCompetencia(Competencia competencia)
    {
        Equipo equipo = OpcionesComunes.escogerEquipo(club, "Que equipo del club juega?");
        if (equipo == null)
        {
            return;
        }
        String rival = Entrada.texto("Institucion rival");
        String escenario = Entrada.texto("Escenario");
        String fase = Entrada.texto("Fase (por ejemplo Liga, Grupo A, Cuartos de final)");
        LocalDate fecha = Entrada.fecha("Fecha");
        String id = "C-" + (competencia.getPartidos().size() + 1);
        try
        {
            PartidoCompetencia partido = competencia.programarPartido(id, fecha, equipo, rival, escenario, fase);
            Entrada.exito("Partido programado: " + OpcionesComunes.describirPartido(partido));
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    /**
     * La liga y la competencia por fases registran el resultado cada una a su manera (por ejemplo, en
     * eliminación directa no se acepta empate).
     */
    private void registrarResultado(Competencia competencia)
    {
        List<PartidoCompetencia> porJugar = new ArrayList<PartidoCompetencia>();
        List<String> nombres = new ArrayList<String>();
        for (PartidoCompetencia partido : competencia.getPartidos())
        {
            if (!partido.isJugado())
            {
                porJugar.add(partido);
                nombres.add(OpcionesComunes.describirPartido(partido));
            }
        }
        int posicion = Entrada.escoger("Que partido?", nombres);
        if (posicion < 0)
        {
            return;
        }
        PartidoCompetencia partido = porJugar.get(posicion);
        int nuestro = Entrada.entero("Marcador de " + partido.getEquipo().getNombre(), 0, 300);
        int rival = Entrada.entero("Marcador de " + partido.getInstitucionRival(), 0, 300);
        try
        {
            competencia.registrarResultado(partido, nuestro, rival);
            Entrada.exito("Resultado registrado: " + OpcionesComunes.describirPartido(partido));
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    // ------------------------------------------------------------------
    // Inventario y sugerencias
    // ------------------------------------------------------------------

    private UbicacionInventario escogerUbicacion(String pregunta)
    {
        List<String> nombres = new ArrayList<String>();
        for (UbicacionInventario ubicacion : club.getUbicaciones())
        {
            nombres.add(ubicacion.getNombre());
        }
        int posicion = Entrada.escoger(pregunta, nombres);
        if (posicion < 0)
        {
            return null;
        }
        return club.getUbicaciones().get(posicion);
    }

    private Producto escogerProducto(String pregunta)
    {
        List<String> nombres = new ArrayList<String>();
        for (Producto producto : club.getProductos())
        {
            nombres.add(producto.getCodigo() + " " + producto.getNombre());
        }
        int posicion = Entrada.escoger(pregunta, nombres);
        if (posicion < 0)
        {
            return null;
        }
        return club.getProductos().get(posicion);
    }

    /**
     * RF-A-04, RF-A-05 - Consultar el inventario / Mover y reabastecer artículos
     */
    private void inventario()
    {
        for (UbicacionInventario ubicacion : club.getUbicaciones())
        {
            Entrada.subtitulo(ubicacion.getNombre());
            for (Existencia existencia : club.consultarInventario(ubicacion))
            {
                Entrada.linea(existencia.getProducto().getNombre() + ": " + existencia.getCantidadDisponible());
            }
        }
        int opcion = Entrada.menu(new String[] {
            "Mover productos de un lugar a otro",
            "Reabastecer (llego mercancia nueva)" }, "Volver");
        if (opcion == 0)
        {
            return;
        }
        Producto producto = escogerProducto("Que producto?");
        if (producto == null)
        {
            return;
        }
        try
        {
            if (opcion == 1)
            {
                UbicacionInventario origen = escogerUbicacion("Desde donde?");
                UbicacionInventario destino = escogerUbicacion("Hacia donde?");
                if (origen == null || destino == null)
                {
                    return;
                }
                int cantidad = Entrada.entero("Cuantas unidades", 0, 1000);
                club.moverProducto(administrador, producto, origen, destino, cantidad, Entrada.hoy());
                Entrada.exito("Se movieron " + cantidad + " de " + producto.getNombre() + " de " + origen.getNombre() + " a "
                        + destino.getNombre() + ".");
            }
            else
            {
                UbicacionInventario destino = escogerUbicacion("A donde llega?");
                if (destino == null)
                {
                    return;
                }
                int cantidad = Entrada.entero("Cuantas unidades", 0, 1000);
                club.reabastecer(administrador, producto, destino, cantidad, Entrada.hoy());
                Entrada.exito("Se ingresaron " + cantidad + " de " + producto.getNombre() + " en " + destino.getNombre() + ".");
            }
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    /**
     * RF-A-06 - Aprobar o rechazar sugerencias
     */
    private void sugerencias()
    {
        List<SugerenciaProducto> pendientes = new ArrayList<SugerenciaProducto>();
        List<String> nombres = new ArrayList<String>();
        for (SugerenciaProducto sugerencia : club.getSugerencias())
        {
            if (sugerencia.getEstado() == EstadoSugerencia.PENDIENTE)
            {
                pendientes.add(sugerencia);
                nombres.add(sugerencia.getDescripcion() + " (de " + sugerencia.getAutor().getNombre() + ", " + sugerencia.getFecha() + ")");
            }
        }
        int posicion = Entrada.escoger("Sugerencias pendientes", nombres);
        if (posicion < 0)
        {
            Entrada.pausa();
            return;
        }
        SugerenciaProducto sugerencia = pendientes.get(posicion);
        try
        {
            if (Entrada.entero("1 = aprobar, 2 = rechazar", 1, 2) == 1)
            {
                sugerencia.aprobar(administrador);
            }
            else
            {
                sugerencia.rechazar(administrador);
            }
            Entrada.exito("La sugerencia quedo " + Entrada.bonito(sugerencia.getEstado()).toLowerCase() + ".");
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    // ------------------------------------------------------------------
    // Partidos, turnos y reportes
    // ------------------------------------------------------------------

    /**
     * RF-A-07 - Consultar el historial de partidos
     */
    private void partidos()
    {
        int opcion = Entrada.menu(new String[] {
            "Programar un partido interno",
            "Ver el historial de un equipo" }, "Volver");
        if (opcion == 1)
        {
            OpcionesComunes.programarPartidoInterno(club);
            Entrada.pausa();
        }
        else if (opcion == 2)
        {
            OpcionesComunes.historialDeEquipo(club);
        }
    }

    /**
     * RF-A-08 - Administrar los turnos
     *
     * Muestra los turnos de un día y revisa, hora por hora, si hay el personal mínimo (RN-37).
     */
    private void turnos()
    {
        DayOfWeek[] dias = DayOfWeek.values();
        List<String> nombres = new ArrayList<String>();
        for (DayOfWeek dia : dias)
        {
            nombres.add(Entrada.dia(dia));
        }
        int posicion = Entrada.escoger("Que dia?", nombres);
        if (posicion < 0)
        {
            return;
        }
        DayOfWeek dia = dias[posicion];
        Entrada.subtitulo("Turnos del " + Entrada.dia(dia).toLowerCase());
        for (Turno turno : club.getTurnos())
        {
            if (turno.getDia() == dia)
            {
                Entrada.linea(turno.getHoraInicio() + " a " + turno.getHoraFin() + "  " + turno.getEmpleado().getNombre() + " ("
                        + turno.getEmpleado().getRol() + ")");
            }
        }
        Entrada.subtitulo("Cobertura (2 entrenadores y 1 fisioterapeuta)");
        LocalTime hora = club.getHoraApertura();
        boolean todoBien = true;
        while (hora.isBefore(club.getHoraCierre()))
        {
            if (!club.hayCobertura(dia, hora))
            {
                Entrada.linea("A las " + hora + " NO hay el personal minimo.");
                todoBien = false;
            }
            hora = hora.plusHours(1);
        }
        if (todoBien)
        {
            Entrada.linea("Todas las horas tienen el personal minimo.");
        }
        Entrada.pausa();
    }

    /**
     * RF-A-09 - Aprobar o rechazar cambios de turno
     *
     * Antes de aprobar, el club revisa que el reemplazo no tenga otro turno a esa hora y que no se pierda la
     * cobertura mínima (RN-37).
     */
    private void solicitudesDeCambio()
    {
        List<SolicitudCambioTurno> pendientes = new ArrayList<SolicitudCambioTurno>();
        List<String> nombres = new ArrayList<String>();
        for (SolicitudCambioTurno solicitud : club.getSolicitudes())
        {
            if (solicitud.getEstado() == EstadoSolicitud.PENDIENTE)
            {
                Turno turno = solicitud.getTurno();
                pendientes.add(solicitud);
                nombres.add(solicitud.getSolicitante().getNombre() + " cede " + Entrada.dia(turno.getDia()) + " " + turno.getHoraInicio() + "-"
                        + turno.getHoraFin() + " a " + solicitud.getReemplazo().getNombre() + " (" + solicitud.getMotivo() + ")");
            }
        }
        int posicion = Entrada.escoger("Solicitudes pendientes", nombres);
        if (posicion < 0)
        {
            Entrada.pausa();
            return;
        }
        SolicitudCambioTurno solicitud = pendientes.get(posicion);
        boolean aprobar = Entrada.entero("1 = aprobar, 2 = rechazar", 1, 2) == 1;
        try
        {
            club.resolverCambioTurno(administrador, solicitud, aprobar);
            Entrada.exito("La solicitud quedo " + Entrada.bonito(solicitud.getEstado()).toLowerCase() + ".");
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }

    /**
     * RF-A-10 - Generar reportes de ventas por rubro y periodo
     */
    private void reporteDeVentas()
    {
        LocalDate desde = Entrada.fecha("Desde");
        LocalDate hasta = Entrada.fecha("Hasta");
        Map<String, Double> totales = new GeneradorReportes(club).ventasPorRubro(desde, hasta);
        Entrada.subtitulo("Ventas del " + desde + " al " + hasta);
        double total = 0;
        for (String rubro : totales.keySet())
        {
            Entrada.linea(String.format("%-15s %s", rubro, Entrada.pesos(totales.get(rubro))));
            total = total + totales.get(rubro);
        }
        Entrada.linea(String.format("%-15s %s", "TOTAL", Entrada.pesos(total)));
        Entrada.pausa();
    }

    /**
     * RF-A-12 - Consultar el estado de las instalaciones
     */
    private void instalacionesOcupadas()
    {
        LocalDate fecha = Entrada.fecha("Fecha");
        LocalTime inicio = Entrada.hora("Desde la hora");
        LocalTime fin = Entrada.hora("Hasta la hora");
        Entrada.subtitulo("Instalaciones el " + fecha + " de " + inicio + " a " + fin);
        for (DisciplinaIndividual disciplina : club.getDisciplinas())
        {
            List<Instalacion> ocupadas = disciplina.consultarInstalacionesOcupadas(fecha, inicio, fin);
            for (Instalacion instalacion : disciplina.getInstalaciones())
            {
                String estado = "libre";
                if (ocupadas.contains(instalacion))
                {
                    estado = "OCUPADA";
                }
                Entrada.linea(disciplina.getNombre() + " | " + instalacion.getNombre() + " | " + estado);
            }
        }
        Entrada.pausa();
    }
}
