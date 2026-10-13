package uniandes.dpoo.cds.consola;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.administracion.SolicitudCambioTurno;
import uniandes.dpoo.cds.modelo.administracion.Turno;
import uniandes.dpoo.cds.modelo.conjunto.EstadisticaFutbol;
import uniandes.dpoo.cds.modelo.conjunto.PartidoInterno;
import uniandes.dpoo.cds.modelo.entrenamiento.EstadoAsistencia;
import uniandes.dpoo.cds.modelo.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.cds.modelo.entrenamiento.TipoSesion;
import uniandes.dpoo.cds.modelo.individuales.InscripcionDisciplina;
import uniandes.dpoo.cds.modelo.individuales.Modalidad;
import uniandes.dpoo.cds.modelo.individuales.Nivel;
import uniandes.dpoo.cds.modelo.individuales.Reserva;
import uniandes.dpoo.cds.modelo.tienda.VentaTienda;
import uniandes.dpoo.cds.modelo.usuarios.Usuario;

/**
 * Programa de prueba de los empleados (usuario operativo): entrenador y fisioterapeuta.
 */
public class ConsolaEmpleado
{
    public static void main(String[] args)
    {
        DatosDePrueba d = new DatosDePrueba();
        Escenario.titulo("PRUEBAS DEL EMPLEADO - " + d.club.getNombre());
        partidosYSanciones(d);
        entrenamientoYOtros(d);
    }

    /**
     * Bloque A. Los partidos se construyen directo, sin programarPartidoInterno, para no necesitar 7 habilitados:
     * aqui se prueba el ciclo de estadisticas, tarjetas y sanciones. La programacion se prueba en ConsolaAdministrador.
     */
    private static void partidosYSanciones(DatosDePrueba d)
    {
        Escenario.titulo("Partidos internos y sanciones");
        PartidoInterno p1 = new PartidoInterno("PI-01", DatosDePrueba.HOY, d.futbolMayoresA, d.futbolMayoresB);
        PartidoInterno p2 = new PartidoInterno("PI-02", DatosDePrueba.HOY.plusDays(7), d.futbolMayoresA, d.futbolMayoresB);
        PartidoInterno p3 = new PartidoInterno("PI-03", DatosDePrueba.HOY.plusDays(14), d.futbolMayoresA, d.futbolMayoresB);
        PartidoInterno p4 = new PartidoInterno("PI-04", DatosDePrueba.HOY.plusDays(21), d.futbolMayoresA, d.futbolMayoresB);

        Escenario.probar("RF-E-05 Sofia entra a Mayores A y Valentina a Mayores B", "OK -> 2 inscritas",
                () -> {
                    d.futbolMayoresA.inscribirJugador(Escenario.perfilDe(d.sofia), "Delantera", 9, DatosDePrueba.HOY);
                    d.futbolMayoresB.inscribirJugador(Escenario.perfilDe(d.valentina), "Portera", 1, DatosDePrueba.HOY);
                    return "2 inscritas";
                });

        Escenario.probar("RF-E-06 PI-01: Sofia hace un gol y ve roja; el partido termina 1-0", "OK -> habilitada: false",
                () -> {
                    p1.registrarEstadistica(new EstadisticaFutbol(Escenario.perfilDe(d.sofia), p1, 1, 0, 0, 1));
                    p1.cerrar(1, 0);
                    return "habilitada: " + habilitada(d.sofia, d);
                });

        Escenario.probarAccion("RF-E-06 Sofia, suspendida, intenta jugar PI-02", "RECHAZADO RN-12",
                () -> p2.registrarEstadistica(new EstadisticaFutbol(Escenario.perfilDe(d.sofia), p2, 0, 0, 0, 0)));

        Escenario.probar("RF-E-06 PI-02 se juega sin Sofia: cumple la sancion", "OK -> habilitada: true",
                () -> {
                    p2.cerrar(0, 0);
                    return "habilitada: " + habilitada(d.sofia, d);
                });

        Escenario.probarAccion("RF-E-06 Se intenta cerrar PI-01 otra vez", "RECHAZADO RN-19",
                () -> p1.cerrar(2, 0));

        Escenario.probarAccion("RF-E-06 PI-03: Valentina ve una amarilla", "OK",
                () -> p3.registrarEstadistica(new EstadisticaFutbol(Escenario.perfilDe(d.valentina), p3, 0, 0, 1, 0)));

        Escenario.probarAccion("RF-E-06 Se registra otra vez la estadistica de Valentina en PI-03", "RECHAZADO RN-10",
                () -> p3.registrarEstadistica(new EstadisticaFutbol(Escenario.perfilDe(d.valentina), p3, 0, 0, 0, 0)));

        Escenario.probar("RF-E-06 PI-03 termina: con una amarilla sigue habilitada", "OK -> habilitada: true",
                () -> {
                    p3.cerrar(0, 2);
                    return "habilitada: " + habilitada(d.valentina, d);
                });

        Escenario.probarAccion("RF-E-06 Mateo, sin equipo, aparece en PI-04", "RECHAZADO RN-12",
                () -> p4.registrarEstadistica(new EstadisticaFutbol(Escenario.perfilDe(d.mateo), p4, 0, 0, 0, 0)));

        Escenario.probar("RF-E-06 PI-04: segunda amarilla de Valentina; queda suspendida", "OK -> habilitada: false",
                () -> {
                    p4.registrarEstadistica(new EstadisticaFutbol(Escenario.perfilDe(d.valentina), p4, 0, 0, 1, 0));
                    p4.cerrar(1, 1);
                    return "habilitada: " + habilitada(d.valentina, d);
                });
    }

    private static boolean habilitada(Usuario usuario, DatosDePrueba d) throws ReglaDominioException
    {
        return Escenario.perfilDe(usuario).buscarInscripcionEnDeporte(d.futbol).estaHabilitado();
    }

    private static void entrenamientoYOtros(DatosDePrueba d)
    {
        Escenario.titulo("Entrenamiento, individuales, tienda y turnos");
        SesionEntrenamiento sesion = new SesionEntrenamiento("S-01", d.entrenador1, d.canchaTenis1, DatosDePrueba.HOY,
                LocalTime.of(8, 0), 90, TipoSesion.EQUIPO);
        sesion.setEquipo(d.futbolMayoresA);
        d.club.agregarSesion(sesion);

        Escenario.probarAccion("RF-E-02 El entrenador convoca a Sofia a la sesion", "OK",
                () -> sesion.convocar(Escenario.perfilDe(d.sofia)));

        Escenario.probarAccion("RF-E-03 Registra que Sofia asistio, con nota 4", "OK",
                () -> sesion.registrarAsistencia(Escenario.perfilDe(d.sofia), EstadoAsistencia.ASISTIO, 4, "Buen trabajo de pases"));

        Escenario.probarAccion("RF-E-03 Registra asistencia de Valentina, que no estaba convocada", "RECHAZADO RN-27",
                () -> sesion.registrarAsistencia(Escenario.perfilDe(d.valentina), EstadoAsistencia.FALTO, null, null));

        Escenario.probarAccion("RF-E-04 Pone una nota de desempeno de 7", "RECHAZADO RN-27",
                () -> sesion.registrarAsistencia(Escenario.perfilDe(d.sofia), EstadoAsistencia.ASISTIO, 7, null));

        Escenario.probar("RF-E-07 Revisa si Futbol Mayores A puede jugar (necesita 7 habilitados)", "OK -> false",
                () -> d.futbolMayoresA.puedeDisputarPartido());

        Escenario.probar("RN-23 El entrenador 1 (Principiante) juega tenis contra Sofia (Avanzado): se permite por ser entrenador", "OK",
                () -> {
                    List<InscripcionDisciplina> participantes = new ArrayList<InscripcionDisciplina>();
                    participantes.add(d.tenis.inscribir(Escenario.perfilDe(d.entrenador1), Nivel.PRINCIPIANTE));
                    participantes.add(d.tenis.inscribir(Escenario.perfilDe(d.sofia), Nivel.AVANZADO));
                    Reserva reserva = d.tenis.reservar("R-10", d.sofia, d.canchaTenis2, DatosDePrueba.HOY, LocalTime.of(17, 0),
                            LocalTime.of(18, 0), Modalidad.SENCILLOS, 2);
                    return d.tenis.programarPartidoPractica("PP-01", reserva, participantes).getId();
                });

        Escenario.probar("RF-E-12 El entrenador 1 compra un balon con su codigo: 15% de descuento", "OK",
                () -> {
                    VentaTienda venta = new VentaTienda("V-10", LocalDateTime.of(2026, 10, 15, 12, 0), d.entrenador1);
                    venta.agregarLinea(d.balon, 1);
                    venta.aplicarCodigoDescuento(d.codigoEntrenador1);
                    d.club.registrarVenta(venta, d.tienda, 0);
                    return "total " + venta.getTotal();
                });

        Escenario.probar("RF-E-10 El fisioterapeuta de la manana cede su turno del lunes al entrenador 3", "RECHAZADO RN-37",
                () -> {
                    Turno turnoLunes = d.fisio1.getTurnos().get(DayOfWeek.MONDAY.getValue() - 1);
                    SolicitudCambioTurno solicitud = new SolicitudCambioTurno(turnoLunes, d.fisio1, d.entrenador3,
                            DatosDePrueba.HOY, "Cita medica");
                    d.club.agregarSolicitud(solicitud);
                    d.club.resolverCambioTurno(d.admin, solicitud, true);
                    return solicitud.getEstado();
                });
    }
}
