package uniandes.dpoo.cds.consola;

import java.time.LocalDateTime;
import java.time.LocalTime;
import uniandes.dpoo.cds.modelo.individuales.Modalidad;
import uniandes.dpoo.cds.modelo.tienda.VentaCafeteria;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Programa de prueba del socio (usuario final). Cada escenario dice que espera y muestra que obtuvo.
 */
public class ConsolaSocio
{
    public static void main(String[] args)
    {
        DatosDePrueba d = new DatosDePrueba();
        Escenario.titulo("PRUEBAS DEL SOCIO - " + d.club.getNombre());

        Escenario.probar("RF-S-01 Sofia entra con su clave", "OK -> Sofia Herrera",
                () -> d.club.autenticar("sofia", "sofia123").getNombre());

        Escenario.probar("RF-S-01 Sofia entra con una clave equivocada", "RECHAZADO RN-01",
                () -> d.club.autenticar("sofia", "otra").getNombre());

        Escenario.probar("RF-S-03 Sofia (22) se inscribe en Futbol Mayores A con la camiseta 9", "OK -> camiseta 9",
                () -> {
                    PerfilJugador perfil = Escenario.perfilDe(d.sofia);
                    return "camiseta " + d.futbolMayoresA.inscribirJugador(perfil, "Delantera", 9, DatosDePrueba.HOY).getNumeroCamiseta();
                });

        Escenario.probarAccion("RF-S-03 Sofia (22) intenta entrar a Futbol Sub-15", "RECHAZADO RN-04",
                () -> d.futbolSub15.inscribirJugador(Escenario.perfilDe(d.sofia), "Defensa", 4, DatosDePrueba.HOY));

        Escenario.probarAccion("RF-S-03 Sofia intenta un segundo equipo de futbol", "RECHAZADO RN-05",
                () -> d.futbolMayoresB.inscribirJugador(Escenario.perfilDe(d.sofia), "Delantera", 10, DatosDePrueba.HOY));

        Escenario.probarAccion("RF-S-03 Valentina pide la camiseta 9, que ya tiene Sofia", "RECHAZADO RN-06",
                () -> d.futbolMayoresA.inscribirJugador(Escenario.perfilDe(d.valentina), "Portera", 9, DatosDePrueba.HOY));

        Escenario.probar("RF-S-03 Sofia tambien juega baloncesto: otro deporte, otra camiseta", "OK -> camiseta 23",
                () -> "camiseta " + d.baloncestoMayores.inscribirJugador(Escenario.perfilDe(d.sofia), "Base", 23, DatosDePrueba.HOY)
                        .getNumeroCamiseta());

        Escenario.probar("RF-S-08 Sofia reserva la cancha de tenis 1 para dobles, 4 jugadores", "OK",
                () -> d.tenis.reservar("R-01", d.sofia, d.canchaTenis1, DatosDePrueba.HOY, LocalTime.of(10, 0), LocalTime.of(11, 0),
                        Modalidad.DOBLES, 4).getId());

        Escenario.probarAccion("RF-S-08 Sofia reserva la cancha de padel para 2 jugadores", "RECHAZADO RN-24",
                () -> d.padel.reservar("R-02", d.sofia, d.canchaPadel1, DatosDePrueba.HOY, LocalTime.of(10, 0), LocalTime.of(11, 0),
                        Modalidad.SENCILLOS, 2));

        Escenario.probar("RF-S-13 Sofia consulta los alergenos de una barra de mani antes de pagar", "OK, dos alergenos",
                () -> {
                    VentaCafeteria venta = new VentaCafeteria("V-01", LocalDateTime.of(2026, 10, 15, 9, 0), d.sofia);
                    venta.agregarLinea(d.barraDeMani, 1);
                    return venta.consultarAlergenos().size() + " alergenos";
                });

        Escenario.probarAccion("RF-S-13 Valentina pide un cafe mientras tiene reservada la cancha de padel", "RECHAZADO RN-32",
                () -> {
                    d.padel.reservar("R-03", d.valentina, d.canchaPadel1, DatosDePrueba.HOY, LocalTime.of(15, 0), LocalTime.of(16, 0),
                            Modalidad.DOBLES, 4);
                    VentaCafeteria venta = new VentaCafeteria("V-02", LocalDateTime.of(2026, 10, 15, 15, 30), d.valentina);
                    venta.agregarLinea(d.cafe, 1);
                    d.club.registrarVenta(venta, d.cafeteria, 0);
                });
    }
}
