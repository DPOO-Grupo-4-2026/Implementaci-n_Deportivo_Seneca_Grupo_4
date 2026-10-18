package uniandes.dpoo.proyecto1.consola;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.administracion.GeneradorReportes;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaFutbol;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaPartido;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.conjunto.PartidoInterno;
import uniandes.dpoo.proyecto1.conjunto.Sancion;
import uniandes.dpoo.proyecto1.entrenamiento.EstadoAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.RegistroAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.individuales.AjusteRanking;
import uniandes.dpoo.proyecto1.individuales.InscripcionDisciplina;
import uniandes.dpoo.proyecto1.individuales.Modalidad;
import uniandes.dpoo.proyecto1.individuales.Nivel;
import uniandes.dpoo.proyecto1.individuales.PartidoPractica;
import uniandes.dpoo.proyecto1.individuales.Reserva;
import uniandes.dpoo.proyecto1.tienda.Alergeno;
import uniandes.dpoo.proyecto1.tienda.VentaCafeteria;
import uniandes.dpoo.proyecto1.tienda.VentaTienda;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Socio;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Programa de prueba del socio (usuario final). Cada método prueba uno o varios requerimientos funcionales del socio.
 */
public class ConsolaSocio
{
    public static void main(String[] args) throws ReglaDominioException
    {
        DatosDePrueba datos = new DatosDePrueba();
        System.out.println("PRUEBAS DEL SOCIO - " + datos.club.getNombre());

        autenticarse(datos);
        inscribirseEnEquipos(datos);
        consultarMisEquipos(datos);
        consultarSancionesCalendarioYEstadisticas(datos);
        deportesIndividuales(datos);
        consultarConvocatorias(datos);
        comprar(datos);
    }

    /** RF-S-01. */
    private static void autenticarse(DatosDePrueba datos)
    {
        Pantalla.seccion("RF-S-01 Autenticarse");

        Pantalla.prueba("Sofia entra con su clave", "entra");
        try
        {
            Usuario usuario = datos.club.autenticar("sofia", "sofia123");
            Pantalla.resultado("entra " + usuario.getNombre() + " como " + usuario.getRol());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia escribe mal la clave", "rechazo RN-01");
        try
        {
            datos.club.autenticar("sofia", "otraClave");
            Pantalla.resultado("entra");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** RF-S-03. */
    private static void inscribirseEnEquipos(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-S-03 Inscribirse en un equipo");
        PerfilJugador sofia = datos.sofia.crearPerfilJugador();
        PerfilJugador valentina = datos.valentina.crearPerfilJugador();

        Pantalla.prueba("Sofia (22 anios) entra a Futbol Mayores A con la camiseta 9", "se acepta");
        try
        {
            InscripcionEquipo inscripcion = datos.futbolMayoresA.inscribirJugador(sofia, "Delantera", 9, DatosDePrueba.HOY);
            Pantalla.resultado("aceptada en " + inscripcion.getEquipo().getNombre() + " con la camiseta " + inscripcion.getNumeroCamiseta());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia intenta entrar a Futbol Sub-15", "rechazo RN-04 por la edad");
        try
        {
            datos.futbolSub15.inscribirJugador(sofia, "Defensa", 4, DatosDePrueba.HOY);
            Pantalla.resultado("aceptada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia intenta un segundo equipo de futbol (Mayores B)", "rechazo RN-05");
        try
        {
            datos.futbolMayoresB.inscribirJugador(sofia, "Delantera", 10, DatosDePrueba.HOY);
            Pantalla.resultado("aceptada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Valentina pide la camiseta 9 en Mayores A, que ya es de Sofia", "rechazo RN-06");
        try
        {
            datos.futbolMayoresA.inscribirJugador(valentina, "Portera", 9, DatosDePrueba.HOY);
            Pantalla.resultado("aceptada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Valentina se inscribe sin posicion", "rechazo RN-07");
        try
        {
            datos.futbolMayoresA.inscribirJugador(valentina, "", 1, DatosDePrueba.HOY);
            Pantalla.resultado("aceptada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia tambien entra a Baloncesto Mayores: otro deporte, otra camiseta", "se acepta");
        try
        {
            InscripcionEquipo inscripcion = datos.baloncestoMayores.inscribirJugador(sofia, "Base", 23, DatosDePrueba.HOY);
            Pantalla.resultado("aceptada con la camiseta " + inscripcion.getNumeroCamiseta());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }

    /** RF-S-02. */
    private static void consultarMisEquipos(DatosDePrueba datos)
    {
        Pantalla.seccion("RF-S-02 Consultar mis equipos");

        Pantalla.prueba("Sofia mira sus equipos", "Futbol Mayores A y Baloncesto Mayores");
        for (InscripcionEquipo inscripcion : datos.sofia.getPerfilJugador().consultarEquipos())
        {
            Pantalla.resultado(inscripcion.getEquipo().getNombre() + ", " + inscripcion.getPosicion() + ", camiseta " + inscripcion.getNumeroCamiseta());
        }
    }

    /** RF-S-06, RF-S-04 y RF-S-05. */
    private static void consultarSancionesCalendarioYEstadisticas(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-S-06, RF-S-04 y RF-S-05 Sanciones, calendario y estadisticas");
        PerfilJugador sofia = datos.sofia.getPerfilJugador();

        // Para que Sofia tenga historia se juega un partido entre A y B. Primero se completan los dos equipos
        // con 7 socios cada uno, porque el club no programa un partido sin el mínimo de habilitados.
        for (int i = 0; i < 7; i++)
        {
            Socio jugadorA = datos.plantillaA.get(i);
            Socio jugadorB = datos.plantillaB.get(i);
            datos.futbolMayoresA.inscribirJugador(jugadorA.crearPerfilJugador(), "Campo", 11 + i, DatosDePrueba.HOY);
            datos.futbolMayoresB.inscribirJugador(jugadorB.crearPerfilJugador(), "Campo", 11 + i, DatosDePrueba.HOY);
        }
        PartidoInterno partido = datos.club.programarPartidoInterno("PI-01", datos.futbolMayoresA, datos.futbolMayoresB, DatosDePrueba.HOY);
        partido.registrarEstadistica(new EstadisticaFutbol(sofia, partido, 2, 1, 0, 1));
        partido.cerrar(2, 0);

        Pantalla.prueba("Sofia hizo 2 goles y vio roja en PI-01; consulta sus sanciones", "1 sancion, 1 partido pendiente");
        for (Sancion sancion : sofia.consultarSancionesVigentes())
        {
            Pantalla.resultado(sancion.getMotivo() + ", partidos pendientes: " + sancion.getPartidosPendientes());
        }

        Pantalla.prueba("Sofia consulta el calendario de sus equipos", "1 partido: PI-01");
        Pantalla.resultado(datos.club.consultarCalendario(sofia).size() + " partido(s)");

        Pantalla.prueba("Sofia consulta sus estadisticas", "1 estadistica, la de PI-01");
        List<EstadisticaPartido> estadisticas = datos.club.consultarEstadisticas(sofia);
        for (EstadisticaPartido estadistica : estadisticas)
        {
            Pantalla.resultado("estadistica en el partido " + estadistica.getPartido().getId());
        }
    }

    /** RF-S-07, RF-S-08, RF-S-09 y RF-S-10. */
    private static void deportesIndividuales(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-S-07 Inscribirse en una disciplina individual");
        PerfilJugador sofia = datos.sofia.getPerfilJugador();
        PerfilJugador valentina = datos.valentina.getPerfilJugador();
        PerfilJugador andres = datos.andres.crearPerfilJugador();

        Pantalla.prueba("Sofia se inscribe en tenis como avanzada", "se acepta con ranking 1000");
        InscripcionDisciplina sofiaEnTenis = null;
        try
        {
            sofiaEnTenis = datos.tenis.inscribir(sofia, Nivel.AVANZADO);
            Pantalla.resultado("inscrita, nivel " + sofiaEnTenis.getNivel() + ", ranking " + sofiaEnTenis.getPuntajeRanking());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia se inscribe otra vez en tenis", "rechazo RN-22");
        try
        {
            datos.tenis.inscribir(sofia, Nivel.PRINCIPIANTE);
            Pantalla.resultado("inscrita");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
        InscripcionDisciplina valentinaEnTenis = datos.tenis.inscribir(valentina, Nivel.PRINCIPIANTE);
        InscripcionDisciplina andresEnTenis = datos.tenis.inscribir(andres, Nivel.INTERMEDIO);

        Pantalla.seccion("RF-S-08 Reservar una instalacion");

        Pantalla.prueba("Sofia reserva la cancha de tenis 1 de 10 a 11 para dobles, 4 jugadores", "se acepta");
        try
        {
            Reserva reserva = datos.tenis.reservar("R-01", datos.sofia, datos.canchaTenis1, DatosDePrueba.HOY, LocalTime.of(10, 0),
                    LocalTime.of(11, 0), Modalidad.DOBLES, 4);
            Pantalla.resultado("reserva " + reserva.getId() + " en " + reserva.getInstalacion().getNombre());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Valentina pide la misma cancha de 10:30 a 11:30", "rechazo RN-25, ya esta reservada");
        try
        {
            datos.tenis.reservar("R-02", datos.valentina, datos.canchaTenis1, DatosDePrueba.HOY, LocalTime.of(10, 30),
                    LocalTime.of(11, 30), Modalidad.SENCILLOS, 2);
            Pantalla.resultado("reservada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia reserva la cancha de padel para sencillos, 2 jugadores", "rechazo RN-24, el padel es siempre dobles");
        try
        {
            datos.padel.reservar("R-03", datos.sofia, datos.canchaPadel1, DatosDePrueba.HOY, LocalTime.of(10, 0),
                    LocalTime.of(11, 0), Modalidad.SENCILLOS, 2);
            Pantalla.resultado("reservada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Tenis de mesa permite 1 mesa a la vez: Sofia reserva la techada y Valentina la otra a la misma hora",
                "la primera se acepta y la segunda rechazo RN-25");
        try
        {
            datos.tenisDeMesa.reservar("R-04", datos.sofia, datos.mesaTechada, DatosDePrueba.HOY, LocalTime.of(10, 0),
                    LocalTime.of(11, 0), Modalidad.SENCILLOS, 2);
            Pantalla.resultado("R-04 aceptada");
            datos.tenisDeMesa.reservar("R-05", datos.valentina, datos.mesaAireLibre, DatosDePrueba.HOY, LocalTime.of(10, 30),
                    LocalTime.of(11, 30), Modalidad.SENCILLOS, 2);
            Pantalla.resultado("R-05 aceptada");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.seccion("RF-S-09 Programar un partido de practica");
        Reserva reserva = datos.tenis.reservar("R-06", datos.sofia, datos.canchaTenis2, DatosDePrueba.HOY, LocalTime.of(17, 0),
                LocalTime.of(18, 0), Modalidad.SENCILLOS, 2);

        Pantalla.prueba("Sofia (avanzada) contra Valentina (principiante)", "rechazo RN-23, los niveles no son seguidos");
        try
        {
            List<InscripcionDisciplina> jugadores = new ArrayList<InscripcionDisciplina>();
            jugadores.add(sofiaEnTenis);
            jugadores.add(valentinaEnTenis);
            datos.tenis.programarPartidoPractica("PP-01", reserva, jugadores);
            Pantalla.resultado("programado");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia (avanzada) contra Andres (intermedio)", "se programa PP-02");
        PartidoPractica partido = null;
        try
        {
            List<InscripcionDisciplina> jugadores = new ArrayList<InscripcionDisciplina>();
            jugadores.add(sofiaEnTenis);
            jugadores.add(andresEnTenis);
            partido = datos.tenis.programarPartidoPractica("PP-02", reserva, jugadores);
            Pantalla.resultado("programado " + partido.getId());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.seccion("RF-S-10 Consultar mi ranking");

        Pantalla.prueba("Sofia le gana a Andres 6-3; los dos tenian 1000", "Sofia sube a 1016 y Andres baja a 984");
        try
        {
            partido.cerrarYActualizarRanking(6, 3);
            Pantalla.resultado("Sofia " + sofiaEnTenis.getPuntajeRanking() + ", Andres " + andresEnTenis.getPuntajeRanking());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia consulta la evolucion de su ranking", "un cambio: de 1000 a 1016");
        for (AjusteRanking ajuste : sofiaEnTenis.getAjustes())
        {
            Pantalla.resultado(ajuste.getFecha() + ": de " + ajuste.getPuntajeAntes() + " a " + ajuste.getPuntajeDespues());
        }
    }

    /** RF-S-11. */
    private static void consultarConvocatorias(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-S-11 Consultar mis convocatorias y asistencias");
        PerfilJugador sofia = datos.sofia.getPerfilJugador();

        // El entrenador arma una sesión de la academia de tenis y registra la asistencia de Sofia
        SesionEntrenamiento sesion = new SesionEntrenamiento("S-01", datos.entrenador2, datos.canchaTenis2, DatosDePrueba.HOY,
                LocalTime.of(7, 0), 60);
        sesion.setDisciplina(datos.tenis);
        datos.club.agregarSesion(sesion);
        sesion.convocar(sofia);
        sesion.registrarAsistencia(sofia, EstadoAsistencia.ASISTIO, 4, "Buen saque");

        Pantalla.prueba("Sofia consulta sus convocatorias", "1 convocatoria: asistio, nota 4");
        for (RegistroAsistencia registro : new GeneradorReportes(datos.club).convocatoriasDeJugador(sofia))
        {
            Pantalla.resultado(registro.getEstado() + ", nota " + registro.getNotaDesempeno() + ", \"" + registro.getComentario() + "\"");
        }
    }

    /** RF-S-12, RF-S-13 y RF-S-14. */
    private static void comprar(DatosDePrueba datos) throws ReglaDominioException
    {
        Pantalla.seccion("RF-S-12 Comprar en la tienda");

        Pantalla.prueba("Sofia compra una raqueta de 320.000", "total 380.800 (con IVA del 19%) y gana 6.400 puntos");
        try
        {
            VentaTienda venta = new VentaTienda("V-01", LocalDateTime.of(2026, 10, 15, 12, 0), datos.sofia);
            venta.agregarLinea(datos.raqueta, 1);
            datos.club.registrarVenta(venta, datos.tienda, 0);
            Pantalla.resultado("total " + venta.getTotal() + ", puntos ganados " + venta.getPuntosGenerados());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia intenta comprar un cafe en la tienda", "rechazo RN-34, la tienda no vende cafe");
        try
        {
            VentaTienda venta = new VentaTienda("V-02", LocalDateTime.of(2026, 10, 15, 12, 5), datos.sofia);
            venta.agregarLinea(datos.cafe, 1);
            Pantalla.resultado("agregado");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.seccion("RF-S-13 Comprar en la cafeteria");

        VentaCafeteria ventaSnack = new VentaCafeteria("V-03", LocalDateTime.of(2026, 10, 15, 9, 0), datos.sofia);
        ventaSnack.agregarLinea(datos.barraDeMani, 1);

        Pantalla.prueba("Antes de pagar una barra de mani, Sofia consulta sus alergenos", "Mani y Gluten");
        for (Alergeno alergeno : ventaSnack.consultarAlergenos())
        {
            Pantalla.resultado(alergeno.getNombre());
        }

        Pantalla.prueba("Sofia baja la propina al 5% y paga la barra de 5.500", "total 6.215 (5.500 + 440 de consumo + 275 de propina)");
        try
        {
            ventaSnack.setPorcentajePropina(0.05);
            datos.club.registrarVenta(ventaSnack, datos.cafeteria, 0);
            Pantalla.resultado("total " + ventaSnack.getTotal());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        datos.padel.reservar("R-10", datos.valentina, datos.canchaPadel1, DatosDePrueba.HOY, LocalTime.of(15, 0), LocalTime.of(16, 0),
                Modalidad.DOBLES, 4);

        Pantalla.prueba("Valentina pide un cafe a las 15:30, mientras tiene reservada la cancha de padel", "rechazo RN-32");
        try
        {
            VentaCafeteria venta = new VentaCafeteria("V-04", LocalDateTime.of(2026, 10, 15, 15, 30), datos.valentina);
            venta.agregarLinea(datos.cafe, 1);
            datos.club.registrarVenta(venta, datos.cafeteria, 0);
            Pantalla.resultado("vendido");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Valentina pide el mismo cafe a las 17:00, cuando ya termino su reserva", "se vende: total 5.310 (4.500 + 360 + 450 de propina)");
        try
        {
            VentaCafeteria venta = new VentaCafeteria("V-05", LocalDateTime.of(2026, 10, 15, 17, 0), datos.valentina);
            venta.agregarLinea(datos.cafe, 1);
            datos.club.registrarVenta(venta, datos.cafeteria, 0);
            Pantalla.resultado("vendido, total " + venta.getTotal());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.seccion("RF-S-14 Consultar y redimir puntos");

        Pantalla.prueba("Sofia consulta sus puntos", "6.510 (6.400 de la raqueta y 110 de la barra)");
        Pantalla.resultado(datos.sofia.getSaldoPuntosFidelidad() + " puntos");

        Pantalla.prueba("Sofia paga un jugo de 6.000 usando 1.000 puntos",
                "total 6.080 (6.000 + 480 + 600 de propina - 1.000) y queda con 5.610 puntos");
        try
        {
            VentaCafeteria venta = new VentaCafeteria("V-06", LocalDateTime.of(2026, 10, 15, 18, 0), datos.sofia);
            venta.agregarLinea(datos.jugo, 1);
            datos.club.registrarVenta(venta, datos.cafeteria, 1000);
            Pantalla.resultado("total " + venta.getTotal() + ", puntos ahora " + datos.sofia.getSaldoPuntosFidelidad());
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }

        Pantalla.prueba("Sofia intenta usar 999.999 puntos", "rechazo RN-31");
        try
        {
            VentaCafeteria venta = new VentaCafeteria("V-07", LocalDateTime.of(2026, 10, 15, 18, 5), datos.sofia);
            venta.agregarLinea(datos.jugo, 1);
            datos.club.registrarVenta(venta, datos.cafeteria, 999999);
            Pantalla.resultado("vendido");
        }
        catch (ReglaDominioException e)
        {
            Pantalla.rechazo(e);
        }
    }
}
