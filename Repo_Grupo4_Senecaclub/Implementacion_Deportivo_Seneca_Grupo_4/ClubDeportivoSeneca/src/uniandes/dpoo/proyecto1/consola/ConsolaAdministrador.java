package uniandes.dpoo.proyecto1.consola;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uniandes.dpoo.proyecto1.administracion.Club;
import uniandes.dpoo.proyecto1.administracion.GeneradorReportes;
import uniandes.dpoo.proyecto1.administracion.SolicitudCambioTurno;
import uniandes.dpoo.proyecto1.competencias.FilaTabla;
import uniandes.dpoo.proyecto1.competencias.PartidoCompetencia;
import uniandes.dpoo.proyecto1.competencias.TablaPosiciones;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaFutbol;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.conjunto.PartidoInterno;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.individuales.Instalacion;
import uniandes.dpoo.proyecto1.individuales.Modalidad;
import uniandes.dpoo.proyecto1.persistencia.PersistenciaClub;
import uniandes.dpoo.proyecto1.tienda.Existencia;
import uniandes.dpoo.proyecto1.tienda.SugerenciaProducto;
import uniandes.dpoo.proyecto1.tienda.VentaCafeteria;
import uniandes.dpoo.proyecto1.tienda.VentaTienda;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Socio;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Programa de prueba del administrador.
 */
public class ConsolaAdministrador
{
    public static void main(String[] args) throws ReglaDominioException
    {
        DatosDePrueba datos = new DatosDePrueba();
        System.out.println("PRUEBAS DEL ADMINISTRADOR - " + datos.club.getNombre());

        autenticarse(datos);
        crearYDarDeBaja(datos);
        programarPartidos(datos);
        competencias(datos);
        inventarioYSugerencias(datos);
        turnos(datos);
        reportesEInstalaciones(datos);
        guardarYCargar(datos);
    }

    /** RF-A-01. */
    private static void autenticarse(DatosDePrueba datos)
    {
        Pantalla.seccion("RF-A-01 Autenticarse");

        Pantalla.prueba("Laura, la administradora, entra con su clave", "entra como Administrador");
        try
        {
            Usuario usuario = datos.club.autenticar("admin", "admin123");
            Pantalla.resultado("entra " + usuario.getNombre() + " como " + usuario.getRol());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** RF-A-02. */
    private static void crearYDarDeBaja(DatosDePrueba datos)
    {
        Pantalla.seccion("RF-A-02 Crear y dar de baja equipos y jugadores");

        Pantalla.prueba("Crea el equipo Seneca Voleibol Sub-17", "se crea");
        try
        {
            Equipo equipo = datos.club.crearEquipo(datos.admin, "Seneca Voleibol Sub-17", 14, datos.voleibol, datos.sub17);
            Pantalla.resultado("creado " + equipo.getNombre());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Crea otro equipo llamado Seneca Futbol Mayores A", "rechazo RN-02, el nombre ya existe");
        try
        {
            datos.club.crearEquipo(datos.admin, "Seneca Futbol Mayores A", 20, datos.futbol, datos.mayores);
            Pantalla.resultado("creado");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Le crea el perfil de jugador a Andres", "se crea");
        try
        {
            PerfilJugador perfil = datos.club.crearPerfilJugador(datos.admin, datos.andres);
            Pantalla.resultado("perfil creado para " + perfil.getUsuario().getNombre());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Intenta crearse un perfil de jugador para ella misma", "rechazo RN-38");
        try
        {
            datos.club.crearPerfilJugador(datos.admin, datos.admin);
            Pantalla.resultado("perfil creado");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Da de baja Futbol Sub-15 y Mateo (14 anios) intenta inscribirse", "rechazo RN-35");
        try
        {
            datos.club.darDeBajaEquipo(datos.admin, datos.futbolSub15);
            PerfilJugador mateo = datos.mateo.crearPerfilJugador();
            datos.futbolSub15.inscribirJugador(mateo, "Volante", 8, DatosDePrueba.HOY);
            Pantalla.resultado("inscrito");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Valentina ocupa el unico cupo de Voleibol Mayores y despues Sofia intenta entrar", "rechazo RN-03 para Sofia");
        try
        {
            datos.voleibolCupoUno.inscribirJugador(datos.valentina.crearPerfilJugador(), "Libero", 5, DatosDePrueba.HOY);
            datos.voleibolCupoUno.inscribirJugador(datos.sofia.crearPerfilJugador(), "Opuesta", 7, DatosDePrueba.HOY);
            Pantalla.resultado("las dos inscritas");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** Programar partidos internos (RN-08, RN-09) y consultar el historial (RF-A-07). */
    private static void programarPartidos(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("Partidos internos y RF-A-07 Consultar el historial");

        Pantalla.prueba("Programa Mayores A contra Mayores B sin jugadores", "rechazo RN-09");
        try
        {
            datos.club.programarPartidoInterno("PI-10", datos.futbolMayoresA, datos.futbolMayoresB, DatosDePrueba.HOY);
            Pantalla.resultado("programado");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Programa futbol contra baloncesto", "rechazo RN-08");
        try
        {
            datos.club.programarPartidoInterno("PI-10", datos.futbolMayoresA, datos.baloncestoMayores, DatosDePrueba.HOY);
            Pantalla.resultado("programado");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        // Siete jugadores por equipo, el mínimo del fútbol
        for (int i = 0; i < 7; i++)
        {
            Socio jugadorA = datos.plantillaA.get(i);
            Socio jugadorB = datos.plantillaB.get(i);
            datos.futbolMayoresA.inscribirJugador(jugadorA.crearPerfilJugador(), "Campo", i + 1, DatosDePrueba.HOY);
            datos.futbolMayoresB.inscribirJugador(jugadorB.crearPerfilJugador(), "Campo", i + 1, DatosDePrueba.HOY);
        }

        Pantalla.prueba("Con 7 jugadores por equipo, programa Mayores A contra Mayores B", "se programa PI-10");
        PartidoInterno partido = datos.club.programarPartidoInterno("PI-10", datos.futbolMayoresA, datos.futbolMayoresB, DatosDePrueba.HOY);
        Pantalla.resultado("programado " + partido.getId());

        Pantalla.prueba("Se juega PI-10 (3-2) y se consulta el historial de Mayores A", "1 partido, 3-2");
        partido.cerrar(3, 2);
        List<Partido> jugados = new GeneradorReportes(datos.club).resultadosDeEquipo(datos.futbolMayoresA);
        for (Partido jugado : jugados)
        {
            Pantalla.resultado(jugado.getId() + " " + jugado.getMarcadorLocal() + "-" + jugado.getMarcadorVisitante());
        }

        Pantalla.prueba("Da de baja al jugador A1", "Mayores A queda con 6 habilitados y ya no puede jugar");
        try
        {
            datos.club.darDeBajaJugador(datos.admin, datos.plantillaA.get(0).getPerfilJugador());
            Pantalla.resultado("habilitados: " + datos.futbolMayoresA.contarHabilitados()
                    + ", puede jugar: " + datos.futbolMayoresA.puedeDisputarPartido());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** RF-A-03 (y RF-E-08, la nómina). Se usa Mayores B, que ya tiene 7 jugadores. */
    private static void competencias(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-A-03 Competencias");
        for (int i = 7; i < 12; i++)
        {
            datos.futbolMayoresB.inscribirJugador(datos.plantillaB.get(i).crearPerfilJugador(), "Campo", i + 1, DatosDePrueba.HOY);
        }
        List<PerfilJugador> nominaCorta = new ArrayList<PerfilJugador>();
        List<PerfilJugador> nomina = new ArrayList<PerfilJugador>();
        for (int i = 0; i < 11; i++)
        {
            nomina.add(datos.plantillaB.get(i).getPerfilJugador());
            if (i < 8)
            {
                nominaCorta.add(datos.plantillaB.get(i).getPerfilJugador());
            }
        }

        Pantalla.prueba("Inscribe Mayores B en el Torneo de los Cerros con 8 jugadores en la nomina", "rechazo RN-16, el minimo es 11");
        try
        {
            datos.torneoCerros.inscribirEquipo(datos.futbolMayoresB, nominaCorta);
            Pantalla.resultado("inscrito");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Inscribe Mayores B con 11 jugadores en la nomina", "se inscribe");
        try
        {
            datos.torneoCerros.inscribirEquipo(datos.futbolMayoresB, nomina);
            Pantalla.resultado("inscrito con " + datos.torneoCerros.buscarInscripcion(datos.futbolMayoresB).getNomina().getRenglones().size() + " jugadores");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Inscribe Baloncesto Mayores en el Torneo de los Cerros, que es de futbol", "rechazo RN-15");
        try
        {
            datos.torneoCerros.inscribirEquipo(datos.baloncestoMayores, new ArrayList<PerfilJugador>());
            Pantalla.resultado("inscrito");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        PartidoCompetencia liga = datos.torneoCerros.programarPartido("TC-01", DatosDePrueba.HOY.plusDays(2), datos.futbolMayoresB,
                "Universidad del Bosque", "Estadio El Campin", "Liga");

        Pantalla.prueba("B12 esta en el equipo pero no en la nomina, y aparece en TC-01", "rechazo, no puede jugar ese partido (RN-17)");
        try
        {
            liga.registrarEstadistica(new EstadisticaFutbol(datos.plantillaB.get(11).getPerfilJugador(), liga, 0, 0, 0, 0));
            Pantalla.resultado("registrada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Mayores B gana TC-01 2-1", "en la tabla: 1 jugado, 1 ganado, 3 puntos");
        try
        {
            datos.torneoCerros.registrarResultado(liga, 2, 1);
            imprimirTablas(datos.torneoCerros.getTablas());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        datos.ascun.inscribirEquipo(datos.futbolMayoresB, nomina);
        PartidoCompetencia grupos = datos.ascun.programarPartido("AS-01", DatosDePrueba.HOY.plusDays(4), datos.futbolMayoresB,
                "Universidad Nacional", "Coliseo", "Grupo A");
        PartidoCompetencia cuartos = datos.ascun.programarPartido("AS-02", DatosDePrueba.HOY.plusDays(9), datos.futbolMayoresB,
                "Universidad Javeriana", "Coliseo", "Cuartos de final");

        Pantalla.prueba("En ASCUN, Mayores B empata 1-1 en el Grupo A", "en la tabla del Grupo A: 1 empatado, 1 punto");
        try
        {
            datos.ascun.registrarResultado(grupos, 1, 1);
            imprimirTablas(datos.ascun.getTablas());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("En cuartos de final se intenta registrar un empate 1-1", "rechazo RN-20");
        try
        {
            datos.ascun.registrarResultado(cuartos, 1, 1);
            Pantalla.resultado("registrado");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    private static void imprimirTablas(List<TablaPosiciones> tablas)
    {
        for (TablaPosiciones tabla : tablas)
        {
            for (FilaTabla fila : tabla.getFilas())
            {
                Pantalla.resultado(tabla.getNombre() + ": " + fila.getEquipo().getNombre() + ", jugados " + fila.getPartidosJugados()
                        + ", ganados " + fila.getGanados() + ", empatados " + fila.getEmpatados() + ", puntos " + fila.getPuntos());
            }
        }
    }

    /** RF-A-04, RF-A-05 y RF-A-06. */
    private static void inventarioYSugerencias(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-A-04 Consultar el inventario");

        Pantalla.prueba("Laura mira el inventario de la tienda", "2 raquetas y 5 balones");
        for (Existencia existencia : datos.club.consultarInventario(datos.tienda))
        {
            Pantalla.resultado(existencia.getProducto().getNombre() + ": " + existencia.getCantidadDisponible());
        }

        Pantalla.seccion("RF-A-05 Mover y reabastecer articulos");

        Pantalla.prueba("Mueve 3 raquetas de la bodega a la tienda", "la tienda queda con 5 y la bodega con 7");
        try
        {
            datos.club.moverProducto(datos.admin, datos.raqueta, datos.bodega, datos.tienda, 3, DatosDePrueba.HOY);
            Pantalla.resultado("tienda " + datos.tienda.buscarExistencia(datos.raqueta).getCantidadDisponible()
                    + ", bodega " + datos.bodega.buscarExistencia(datos.raqueta).getCantidadDisponible());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Mueve 500 balones, pero en la bodega hay 25", "rechazo RN-35");
        try
        {
            datos.club.moverProducto(datos.admin, datos.balon, datos.bodega, datos.tienda, 500, DatosDePrueba.HOY);
            Pantalla.resultado("movidos");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Llegan 10 cafes nuevos a la cafeteria", "la cafeteria queda con 60");
        try
        {
            datos.club.reabastecer(datos.admin, datos.cafe, datos.cafeteria, 10, DatosDePrueba.HOY);
            Pantalla.resultado("cafeteria " + datos.cafeteria.buscarExistencia(datos.cafe).getCantidadDisponible());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.seccion("RF-A-06 Aprobar o rechazar sugerencias");
        SugerenciaProducto sugerencia = datos.club.registrarSugerencia(datos.entrenador2, "Bebida hidratante", DatosDePrueba.HOY);

        Pantalla.prueba("Laura aprueba la sugerencia de Diana", "APROBADA");
        try
        {
            sugerencia.aprobar(datos.admin);
            Pantalla.resultado(sugerencia.getEstado() + " por " + sugerencia.getResueltaPor().getNombre());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Laura intenta rechazar la misma sugerencia", "rechazo RN-36, ya estaba resuelta");
        try
        {
            sugerencia.rechazar(datos.admin);
            Pantalla.resultado("rechazada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** RF-A-08 y RF-A-09. */
    private static void turnos(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-A-08 Administrar turnos");

        Pantalla.prueba("Laura revisa la cobertura del lunes a las 10:00", "si hay: 2 entrenadores y 1 fisioterapeuta");
        Pantalla.resultado("hay cobertura: " + datos.club.hayCobertura(DayOfWeek.MONDAY, LocalTime.of(10, 0)));

        Pantalla.seccion("RF-A-09 Aprobar o rechazar cambios de turno");
        // El primer turno de cada empleado es el del lunes
        SolicitudCambioTurno dePaula = datos.club.solicitarCambioTurno(datos.fisio1, datos.fisio1.getTurnos().get(0), datos.entrenador3,
                DatosDePrueba.HOY, "Cita medica");
        SolicitudCambioTurno deCarlos = datos.club.solicitarCambioTurno(datos.entrenador1, datos.entrenador1.getTurnos().get(0),
                datos.entrenador3, DatosDePrueba.HOY, "Viaje");

        Pantalla.prueba("Paula, la unica fisioterapeuta de la manana, cede su lunes a Felipe (entrenador)", "rechazo RN-37, el lunes en la manana no quedaria fisioterapeuta");
        try
        {
            datos.club.resolverCambioTurno(datos.admin, dePaula, true);
            Pantalla.resultado(dePaula.getEstado().toString());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Carlos cede su lunes en la manana a Felipe, que trabaja el lunes en la tarde", "APROBADA, sigue habiendo 2 entrenadores");
        try
        {
            datos.club.resolverCambioTurno(datos.admin, deCarlos, true);
            Pantalla.resultado(deCarlos.getEstado() + ", el turno ahora es de " + deCarlos.getTurno().getEmpleado().getNombre());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** RF-A-10, RF-A-11 y RF-A-12. */
    private static void reportesEInstalaciones(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-A-10 Reporte de ventas");
        VentaTienda raqueta = new VentaTienda("V-01", LocalDateTime.of(2026, 10, 15, 12, 0), datos.sofia);
        raqueta.agregarLinea(datos.raqueta, 1);
        datos.club.registrarVenta(raqueta, datos.tienda, 0);
        VentaCafeteria jugo = new VentaCafeteria("V-02", LocalDateTime.of(2026, 10, 15, 13, 0), datos.valentina);
        jugo.agregarLinea(datos.jugo, 1);
        datos.club.registrarVenta(jugo, datos.cafeteria, 0);

        Pantalla.prueba("Ventas de octubre por rubro", "Tienda 380.800, Cafeteria 7.080, Mensualidades 300.000");
        Map<String, Double> totales = new GeneradorReportes(datos.club).ventasPorRubro(DatosDePrueba.HOY.withDayOfMonth(1),
                DatosDePrueba.HOY.withDayOfMonth(31));
        Pantalla.resultado("Tienda " + totales.get("Tienda") + ", Cafeteria " + totales.get("Cafeteria")
                + ", Mensualidades " + totales.get("Mensualidades"));

        Pantalla.seccion("RF-A-12 Consultar las instalaciones");
        datos.tenis.reservar("R-01", datos.sofia, datos.canchaTenis1, DatosDePrueba.HOY, LocalTime.of(10, 0), LocalTime.of(11, 0),
                Modalidad.DOBLES, 4);

        Pantalla.prueba("Que canchas de tenis estan ocupadas de 10:30 a 10:45", "solo la cancha de tenis 1");
        for (Instalacion instalacion : datos.tenis.consultarInstalacionesOcupadas(DatosDePrueba.HOY, LocalTime.of(10, 30), LocalTime.of(10, 45)))
        {
            Pantalla.resultado(instalacion.getNombre());
        }
    }

    /** Persistencia: el club se guarda en datos/ y se vuelve a cargar. */
    private static void guardarYCargar(DatosDePrueba datos)
    {
        Pantalla.seccion("Persistencia");
        PersistenciaClub persistencia = new PersistenciaClub();
        File carpeta = new File("datos");

        Pantalla.prueba("Se guarda el club en datos/club.dat y se vuelve a cargar", "6 equipos, 2 ventas y 7 inscritos en Mayores A");
        try
        {
            persistencia.guardar(datos.club, carpeta);
            Club cargado = persistencia.cargar(carpeta);
            Equipo mayoresA = null;
            for (Equipo equipo : cargado.getEquipos())
            {
                if (equipo.getNombre().equals("Seneca Futbol Mayores A"))
                {
                    mayoresA = equipo;
                }
            }
            Pantalla.resultado(cargado.getEquipos().size() + " equipos, " + cargado.getVentas().size() + " ventas, "
                    + mayoresA.getInscripciones().size() + " inscritos en Mayores A");
        }
        catch (IOException e)
        {
            Pantalla.resultado("error al guardar o cargar: " + e.getMessage());
        }
    }
}
