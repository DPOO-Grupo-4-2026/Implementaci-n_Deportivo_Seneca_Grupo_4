package uniandes.dpoo.proyecto1.consola;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uniandes.dpoo.proyecto1.administracion.GeneradorReportes;
import uniandes.dpoo.proyecto1.administracion.SolicitudCambioTurno;
import uniandes.dpoo.proyecto1.administracion.Turno;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaFutbol;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.conjunto.PartidoInterno;
import uniandes.dpoo.proyecto1.entrenamiento.EstadoAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.individuales.InscripcionDisciplina;
import uniandes.dpoo.proyecto1.individuales.Modalidad;
import uniandes.dpoo.proyecto1.individuales.Nivel;
import uniandes.dpoo.proyecto1.individuales.Reserva;
import uniandes.dpoo.proyecto1.tienda.SugerenciaProducto;
import uniandes.dpoo.proyecto1.tienda.VentaTienda;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Socio;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Programa de prueba de los empleados (usuario operativo): entrenador y fisioterapeuta.
 */
public class ConsolaEmpleado
{
    public static void main(String[] args) throws ReglaDominioException
    {
        DatosDePrueba datos = new DatosDePrueba();
        System.out.println("PRUEBAS DEL EMPLEADO - " + datos.club.getNombre());

        autenticarse(datos);
        registrarPartidosYSanciones(datos);
        verificarMinimoDeHabilitados(datos);
        sesionesDeEntrenamiento(datos);
        jugarComoEntrenador(datos);
        turnosYSugerencias(datos);
        comprarConCodigo(datos);
    }

    /** RF-E-01. */
    private static void autenticarse(DatosDePrueba datos)
    {
        Pantalla.seccion("RF-E-01 Autenticarse");

        Pantalla.prueba("El entrenador Carlos entra con su clave", "entra como Entrenador");
        try
        {
            Usuario usuario = datos.club.autenticar("ent1", "ent123");
            Pantalla.resultado("entra " + usuario.getNombre() + " como " + usuario.getRol());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /**
     * RF-E-05 y RF-E-06. Cuatro partidos seguidos entre Mayores A y Mayores B para ver una sanción de principio a fin.
     * Cada partido se programa con el club, asi que cada vez se revisa que los dos equipos tengan 7 habilitados.
     */
    private static void registrarPartidosYSanciones(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-E-05 y RF-E-06 Partidos, tarjetas y sanciones");
        PerfilJugador sofia = datos.sofia.crearPerfilJugador();
        PerfilJugador valentina = datos.valentina.crearPerfilJugador();
        PerfilJugador mateo = datos.mateo.crearPerfilJugador();
        InscripcionEquipo sofiaEnA = datos.futbolMayoresA.inscribirJugador(sofia, "Delantera", 9, DatosDePrueba.HOY);
        InscripcionEquipo valentinaEnB = datos.futbolMayoresB.inscribirJugador(valentina, "Portera", 1, DatosDePrueba.HOY);
        completarPlantillas(datos);

        PartidoInterno partido1 = datos.club.programarPartidoInterno("PI-01", datos.futbolMayoresA, datos.futbolMayoresB, DatosDePrueba.HOY);

        Pantalla.prueba("PI-01: Sofia hace un gol y ve roja; el partido termina 1-0", "Sofia queda suspendida");
        try
        {
            partido1.registrarEstadistica(new EstadisticaFutbol(sofia, partido1, 1, 0, 0, 1));
            partido1.cerrar(1, 0);
            Pantalla.resultado("Sofia habilitada: " + sofiaEnA.estaHabilitado());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        PartidoInterno partido2 = datos.club.programarPartidoInterno("PI-02", datos.futbolMayoresA, datos.futbolMayoresB, DatosDePrueba.HOY.plusDays(7));

        Pantalla.prueba("Sofia, suspendida, aparece en PI-02", "rechazo RN-12");
        try
        {
            partido2.registrarEstadistica(new EstadisticaFutbol(sofia, partido2, 0, 0, 0, 0));
            Pantalla.resultado("registrada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("PI-02 se juega sin Sofia", "Sofia cumple la sancion y vuelve a estar habilitada");
        try
        {
            partido2.cerrar(0, 0);
            Pantalla.resultado("Sofia habilitada: " + sofiaEnA.estaHabilitado());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Se intenta cerrar PI-01 otra vez", "rechazo RN-19");
        try
        {
            partido1.cerrar(2, 0);
            Pantalla.resultado("cerrado otra vez");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        PartidoInterno partido3 = datos.club.programarPartidoInterno("PI-03", datos.futbolMayoresA, datos.futbolMayoresB, DatosDePrueba.HOY.plusDays(14));

        Pantalla.prueba("PI-03: Valentina ve una amarilla", "sigue habilitada (es su primera amarilla)");
        try
        {
            partido3.registrarEstadistica(new EstadisticaFutbol(valentina, partido3, 0, 0, 1, 0));
            partido3.cerrar(0, 2);
            Pantalla.resultado("Valentina habilitada: " + valentinaEnB.estaHabilitado());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        PartidoInterno partido4 = datos.club.programarPartidoInterno("PI-04", datos.futbolMayoresA, datos.futbolMayoresB, DatosDePrueba.HOY.plusDays(21));

        Pantalla.prueba("Mateo, que no esta en ningun equipo, aparece en PI-04", "rechazo RN-12");
        try
        {
            partido4.registrarEstadistica(new EstadisticaFutbol(mateo, partido4, 0, 0, 0, 0));
            Pantalla.resultado("registrada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("PI-04: Valentina ve su segunda amarilla", "se registra");
        try
        {
            partido4.registrarEstadistica(new EstadisticaFutbol(valentina, partido4, 0, 0, 1, 0));
            Pantalla.resultado("registrada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Se registra otra vez la estadistica de Valentina en PI-04", "rechazo RN-10");
        try
        {
            partido4.registrarEstadistica(new EstadisticaFutbol(valentina, partido4, 0, 0, 0, 0));
            Pantalla.resultado("registrada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Termina PI-04", "Valentina queda suspendida por dos amarillas");
        try
        {
            partido4.cerrar(1, 1);
            Pantalla.resultado("Valentina habilitada: " + valentinaEnB.estaHabilitado());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** Inscribe 7 socios adultos en cada equipo de fútbol de mayores, el mínimo para poder programar partidos. */
    private static void completarPlantillas(DatosDePrueba datos) throws ReglaDominioException
    {
        for (int i = 0; i < 7; i++)
        {
            Socio jugadorA = datos.plantillaA.get(i);
            Socio jugadorB = datos.plantillaB.get(i);
            datos.futbolMayoresA.inscribirJugador(jugadorA.crearPerfilJugador(), "Campo", 11 + i, DatosDePrueba.HOY);
            datos.futbolMayoresB.inscribirJugador(jugadorB.crearPerfilJugador(), "Campo", 11 + i, DatosDePrueba.HOY);
        }
    }

    /** RF-E-07. */
    private static void verificarMinimoDeHabilitados(DatosDePrueba datos)
    {
        Pantalla.seccion("RF-E-07 Verificar el minimo de habilitados");

        Pantalla.prueba("El entrenador revisa si Mayores B puede jugar (necesita 7)", "si puede: 8 inscritos, 7 habilitados, Valentina suspendida");
        Pantalla.resultado("puede jugar: " + datos.futbolMayoresB.puedeDisputarPartido()
                + ", habilitados: " + datos.futbolMayoresB.contarHabilitados());
        for (InscripcionEquipo inscripcion : datos.futbolMayoresB.consultarNoHabilitados())
        {
            Pantalla.resultado("no habilitada: " + inscripcion.getJugador().getUsuario().getNombre());
        }
    }

    /** RF-E-02, RF-E-03, RF-E-04 y RF-E-09. */
    private static void sesionesDeEntrenamiento(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-E-02, RF-E-03 y RF-E-04 Sesiones de entrenamiento");
        PerfilJugador sofia = datos.sofia.getPerfilJugador();
        PerfilJugador valentina = datos.valentina.getPerfilJugador();
        PerfilJugador mateo = datos.mateo.getPerfilJugador();
        datos.tenis.inscribir(sofia, Nivel.AVANZADO);
        datos.tenis.inscribir(valentina, Nivel.PRINCIPIANTE);

        SesionEntrenamiento sesion = new SesionEntrenamiento("S-01", datos.entrenador2, datos.canchaTenis2, DatosDePrueba.HOY,
                LocalTime.of(7, 0), 60);
        sesion.setDisciplina(datos.tenis);
        datos.club.agregarSesion(sesion);

        Pantalla.prueba("Diana arma la sesion S-01 de la academia de tenis y convoca a Sofia y a Valentina", "las dos quedan convocadas");
        try
        {
            sesion.convocar(sofia);
            sesion.convocar(valentina);
            Pantalla.resultado(sesion.getRegistros().size() + " convocadas");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Diana convoca a Mateo, que no esta en tenis", "rechazo RN-27");
        try
        {
            sesion.convocar(mateo);
            Pantalla.resultado("convocado");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia asistio; Diana le pone nota 4", "se registra");
        try
        {
            sesion.registrarAsistencia(sofia, EstadoAsistencia.ASISTIO, 4, "Buen saque");
            Pantalla.resultado("registrada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Diana intenta poner nota 7", "rechazo RN-27, la nota va de 1 a 5");
        try
        {
            sesion.registrarAsistencia(sofia, EstadoAsistencia.ASISTIO, 7, null);
            Pantalla.resultado("registrada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Diana cierra la sesion sin marcar a Valentina", "rechazo RN-27");
        try
        {
            sesion.cerrar();
            Pantalla.resultado("cerrada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Valentina llego tarde, sin nota; Diana cierra la sesion", "se cierra");
        try
        {
            sesion.registrarAsistencia(valentina, EstadoAsistencia.LLEGO_TARDE, null, null);
            sesion.cerrar();
            Pantalla.resultado("cerrada: " + sesion.isCerrada());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Diana intenta cambiar la asistencia de Sofia despues de cerrar", "rechazo RN-27");
        try
        {
            sesion.registrarAsistencia(sofia, EstadoAsistencia.FALTO, null, null);
            Pantalla.resultado("cambiada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.seccion("RF-E-09 Consultar asistencia y desempeno");

        Pantalla.prueba("Diana consulta la asistencia de Valentina en octubre", "asistio 0, falto 0, llego tarde 1");
        Map<EstadoAsistencia, Integer> conteo = new GeneradorReportes(datos.club).asistenciaDeJugador(valentina,
                DatosDePrueba.HOY.withDayOfMonth(1), DatosDePrueba.HOY.withDayOfMonth(31));
        Pantalla.resultado("asistio " + conteo.get(EstadoAsistencia.ASISTIO) + ", falto " + conteo.get(EstadoAsistencia.FALTO)
                + ", llego tarde " + conteo.get(EstadoAsistencia.LLEGO_TARDE));
    }

    /** RN-23: un entrenador puede jugar contra cualquier nivel. */
    private static void jugarComoEntrenador(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RN-23 Un entrenador juega contra cualquier nivel");
        InscripcionDisciplina carlos = datos.tenis.inscribir(datos.entrenador1.crearPerfilJugador(), Nivel.PRINCIPIANTE);
        InscripcionDisciplina sofia = null;
        for (InscripcionDisciplina inscripcion : datos.tenis.getInscripciones())
        {
            if (inscripcion.getJugador() == datos.sofia.getPerfilJugador())
            {
                sofia = inscripcion;
            }
        }
        Reserva reserva = datos.tenis.reservar("R-01", datos.sofia, datos.canchaTenis1, DatosDePrueba.HOY, LocalTime.of(17, 0),
                LocalTime.of(18, 0), Modalidad.SENCILLOS, 2);

        Pantalla.prueba("Carlos (entrenador, principiante) juega contra Sofia (avanzada)", "se programa, porque Carlos es entrenador");
        try
        {
            List<InscripcionDisciplina> jugadores = new ArrayList<InscripcionDisciplina>();
            jugadores.add(carlos);
            jugadores.add(sofia);
            Pantalla.resultado("programado " + datos.tenis.programarPartidoPractica("PP-01", reserva, jugadores).getId());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** RF-E-10 y RF-E-11. */
    private static void turnosYSugerencias(DatosDePrueba datos)
    {
        Pantalla.seccion("RF-E-10 Solicitar un cambio de turno");
        // El primer turno de cada empleado es el del lunes
        Turno lunesDePaula = datos.fisio1.getTurnos().get(0);
        Turno lunesDeJorge = datos.fisio2.getTurnos().get(0);

        Pantalla.prueba("Paula (fisioterapeuta) pide ceder su turno del lunes a Felipe", "queda pendiente");
        try
        {
            SolicitudCambioTurno solicitud = datos.club.solicitarCambioTurno(datos.fisio1, lunesDePaula, datos.entrenador3,
                    DatosDePrueba.HOY, "Cita medica");
            Pantalla.resultado("solicitud " + solicitud.getEstado());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Paula pide ceder un turno que es de Jorge", "rechazo RN-37");
        try
        {
            datos.club.solicitarCambioTurno(datos.fisio1, lunesDeJorge, datos.entrenador3, DatosDePrueba.HOY, "Error");
            Pantalla.resultado("pendiente");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.seccion("RF-E-11 Sugerir productos");

        Pantalla.prueba("Diana sugiere vender una bebida hidratante", "queda pendiente");
        try
        {
            SugerenciaProducto sugerencia = datos.club.registrarSugerencia(datos.entrenador2, "Bebida hidratante", DatosDePrueba.HOY);
            Pantalla.resultado("sugerencia " + sugerencia.getEstado());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Diana manda una sugerencia vacia", "rechazo RN-36");
        try
        {
            datos.club.registrarSugerencia(datos.entrenador2, " ", DatosDePrueba.HOY);
            Pantalla.resultado("registrada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** RF-E-12. */
    private static void comprarConCodigo(DatosDePrueba datos)
    {
        Pantalla.seccion("RF-E-12 Comprar con codigo de descuento");

        Pantalla.prueba("Carlos compra un balon de 95.000 con su codigo", "15% menos: 80.750 + IVA = 96.092,5");
        try
        {
            VentaTienda venta = new VentaTienda("V-01", LocalDateTime.of(2026, 10, 15, 12, 0), datos.entrenador1);
            venta.agregarLinea(datos.balon, 1);
            venta.aplicarCodigoDescuento(datos.codigoEntrenador1);
            datos.club.registrarVenta(venta, datos.tienda, 0);
            Pantalla.resultado("total " + venta.getTotal());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia compra un balon con el codigo que le compartio Carlos", "8% menos: 87.400 + IVA = 104.006");
        try
        {
            VentaTienda venta = new VentaTienda("V-02", LocalDateTime.of(2026, 10, 15, 12, 10), datos.sofia);
            venta.agregarLinea(datos.balon, 1);
            venta.aplicarCodigoDescuento(datos.codigoEntrenador1);
            datos.club.registrarVenta(venta, datos.tienda, 0);
            Pantalla.resultado("total " + venta.getTotal());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Se intenta poner el codigo dos veces en la misma compra", "rechazo RN-30, no es acumulable");
        try
        {
            VentaTienda venta = new VentaTienda("V-03", LocalDateTime.of(2026, 10, 15, 12, 20), datos.sofia);
            venta.agregarLinea(datos.balon, 1);
            venta.aplicarCodigoDescuento(datos.codigoEntrenador1);
            venta.aplicarCodigoDescuento(datos.codigoEntrenador1);
            Pantalla.resultado("aplicado dos veces");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }
}
