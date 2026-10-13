package uniandes.dpoo.cds.consola;

import java.time.DayOfWeek;
import java.time.LocalTime;
import uniandes.dpoo.cds.modelo.administracion.GeneradorReportes;
import uniandes.dpoo.cds.modelo.conjunto.PartidoInterno;
import uniandes.dpoo.cds.modelo.tienda.SugerenciaProducto;
import uniandes.dpoo.cds.modelo.usuarios.Socio;

/**
 * Programa de prueba del administrador.
 */
public class ConsolaAdministrador
{
    public static void main(String[] args)
    {
        DatosDePrueba d = new DatosDePrueba();
        Escenario.titulo("PRUEBAS DEL ADMINISTRADOR - " + d.club.getNombre());
        equiposYPartidos(d);
        operacion(d);
    }

    /** Bloque A. */
    private static void equiposYPartidos(DatosDePrueba d)
    {
        Escenario.titulo("Equipos y partidos internos");

        Escenario.probar("RF-A-01 El administrador entra", "OK -> Administrador",
                () -> d.club.autenticar("admin", "admin123").getRol());

        Escenario.probar("RF-A-02 Crea el equipo Seneca Voleibol Sub-17", "OK -> Seneca Voleibol Sub-17",
                () -> d.club.crearEquipo(d.admin, "Seneca Voleibol Sub-17", 14, d.voleibol, d.sub17).getNombre());

        Escenario.probarAccion("RF-A-02 Crea otro equipo con un nombre que ya existe", "RECHAZADO RN-02",
                () -> d.club.crearEquipo(d.admin, "Seneca Futbol Mayores A", 20, d.futbol, d.mayores));

        Escenario.probarAccion("RN-38 El administrador intenta crear su perfil de jugador", "RECHAZADO RN-38",
                () -> d.admin.crearPerfilJugador());

        Escenario.probarAccion("RF-A-03 Programa Mayores A contra Mayores B sin jugadores", "RECHAZADO RN-09",
                () -> d.club.programarPartidoInterno("PI-10", d.futbolMayoresA, d.futbolMayoresB, DatosDePrueba.HOY));

        Escenario.probarAccion("RF-A-03 Programa futbol contra baloncesto", "RECHAZADO RN-08",
                () -> d.club.programarPartidoInterno("PI-10", d.futbolMayoresA, d.baloncestoMayores, DatosDePrueba.HOY));

        Escenario.probar("RF-A-03 Inscribe 7 jugadores por equipo y programa el partido", "OK -> PI-10",
                () -> {
                    for (int i = 0; i < 7; i++)
                    {
                        Socio a = d.plantillaA.get(i);
                        Socio b = d.plantillaB.get(i);
                        d.futbolMayoresA.inscribirJugador(Escenario.perfilDe(a), "Campo", i + 1, DatosDePrueba.HOY);
                        d.futbolMayoresB.inscribirJugador(Escenario.perfilDe(b), "Campo", i + 1, DatosDePrueba.HOY);
                    }
                    return d.club.programarPartidoInterno("PI-10", d.futbolMayoresA, d.futbolMayoresB, DatosDePrueba.HOY).getId();
                });

        Escenario.probar("RF-A-04 Se juega PI-10 (3-2) y se consultan los resultados de Mayores A", "OK -> 1 partido, 3-2",
                () -> {
                    PartidoInterno partido = (PartidoInterno) d.club.getPartidos().get(0);
                    partido.cerrar(3, 2);
                    GeneradorReportes reportes = new GeneradorReportes(d.club);
                    return reportes.resultadosDeEquipo(d.futbolMayoresA).size() + " partido, "
                            + partido.getMarcadorLocal() + "-" + partido.getMarcadorVisitante();
                });

        Escenario.probar("RN-35 Da de baja al jugador A1: Mayores A queda con 6 habilitados", "OK -> puede jugar: false",
                () -> {
                    d.club.darDeBajaJugador(d.admin, Escenario.perfilDe(d.plantillaA.get(0)));
                    return "puede jugar: " + d.futbolMayoresA.puedeDisputarPartido();
                });

        Escenario.probar("RN-03 Valentina ocupa el unico cupo de Voleibol Mayores", "OK",
                () -> d.voleibolCupoUno.inscribirJugador(Escenario.perfilDe(d.valentina), "Libero", 5, DatosDePrueba.HOY)
                        .getEquipo().getNombre());

        Escenario.probarAccion("RN-03 Sofia intenta entrar al mismo equipo", "RECHAZADO RN-03",
                () -> d.voleibolCupoUno.inscribirJugador(Escenario.perfilDe(d.sofia), "Opuesta", 7, DatosDePrueba.HOY));

        Escenario.probarAccion("RN-35 Da de baja Futbol Sub-15 y Mateo (14) intenta inscribirse", "RECHAZADO RN-35",
                () -> {
                    d.club.darDeBajaEquipo(d.admin, d.futbolSub15);
                    d.futbolSub15.inscribirJugador(Escenario.perfilDe(d.mateo), "Volante", 8, DatosDePrueba.HOY);
                });
    }

    private static void operacion(DatosDePrueba d)
    {
        Escenario.titulo("Inventario, sugerencias, turnos y reportes");

        Escenario.probar("RF-A-05 Mueve 3 raquetas de la bodega a la tienda", "OK -> 5 en tienda",
                () -> {
                    d.club.moverProducto(d.admin, d.raqueta, d.bodega, d.tienda, 3);
                    return d.tienda.buscarExistencia(d.raqueta).getCantidadDisponible() + " en tienda";
                });

        Escenario.probarAccion("RF-A-05 Mueve 500 balones que no hay en la bodega", "RECHAZADO RN-35",
                () -> d.club.moverProducto(d.admin, d.balon, d.bodega, d.tienda, 500));

        Escenario.probar("RF-A-06 Aprueba la sugerencia de un entrenador", "OK -> APROBADA",
                () -> {
                    SugerenciaProducto sugerencia = new SugerenciaProducto("Bebida hidratante", DatosDePrueba.HOY, d.entrenador2);
                    d.club.agregarSugerencia(sugerencia);
                    sugerencia.aprobar(d.admin);
                    return sugerencia.getEstado();
                });

        Escenario.probar("RF-A-08 Revisa la cobertura del lunes a las 10:00", "OK -> true",
                () -> d.club.hayCobertura(DayOfWeek.MONDAY, LocalTime.of(10, 0)));

        Escenario.probar("RF-A-10 Reporte de ventas por rubro de octubre", "OK",
                () -> new GeneradorReportes(d.club).ventasPorRubro(DatosDePrueba.HOY.withDayOfMonth(1), DatosDePrueba.HOY));
    }
}
