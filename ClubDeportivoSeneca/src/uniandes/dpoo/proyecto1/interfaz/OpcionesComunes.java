package uniandes.dpoo.proyecto1.interfaz;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uniandes.dpoo.proyecto1.administracion.Club;
import uniandes.dpoo.proyecto1.administracion.GeneradorReportes;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.conjunto.PartidoInterno;
import uniandes.dpoo.proyecto1.entrenamiento.EstadoAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.RegistroAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.tienda.Alergeno;
import uniandes.dpoo.proyecto1.tienda.CodigoDescuento;
import uniandes.dpoo.proyecto1.tienda.Existencia;
import uniandes.dpoo.proyecto1.tienda.LineaVenta;
import uniandes.dpoo.proyecto1.tienda.TipoUbicacion;
import uniandes.dpoo.proyecto1.tienda.UbicacionInventario;
import uniandes.dpoo.proyecto1.tienda.Venta;
import uniandes.dpoo.proyecto1.tienda.VentaCafeteria;
import uniandes.dpoo.proyecto1.tienda.VentaTienda;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Lo que hacen igual dos o tres menús: comprar, escoger un equipo, programar un partido, ver un historial y
 * consultar la asistencia de un jugador.
 * Está aquí una sola vez para no repetirlo en cada menú.
 */
public class OpcionesComunes
{
    // ------------------------------------------------------------------
    // Equipos y partidos
    // ------------------------------------------------------------------

    public static String nombreEquipo(Equipo equipo)
    {
        String texto = equipo.getNombre() + " - " + equipo.getDeporte().getNombre() + " " + equipo.getCategoria().getNombre()
                + " (" + equipo.getInscripciones().size() + "/" + equipo.getCupoMaximo() + ")";
        if (!equipo.isActivo())
        {
            texto = texto + " [dado de baja]";
        }
        return texto;
    }

    /**
     * Muestra los equipos activos del club y devuelve el escogido, o null si la persona vuelve.
     */
    public static Equipo escogerEquipo(Club club, String pregunta)
    {
        List<Equipo> activos = new ArrayList<Equipo>();
        List<String> nombres = new ArrayList<String>();
        for (Equipo equipo : club.getEquipos())
        {
            if (equipo.isActivo())
            {
                activos.add(equipo);
                nombres.add(nombreEquipo(equipo));
            }
        }
        int posicion = Entrada.escoger(pregunta, nombres);
        if (posicion < 0)
        {
            return null;
        }
        return activos.get(posicion);
    }

    /**
     * Una línea con todo lo de un partido: identificador, fecha, quiénes juegan y el marcador si ya se jugó.
     */
    public static String describirPartido(Partido partido)
    {
        String marcador = "por jugar";
        if (partido.isJugado())
        {
            marcador = partido.getMarcadorLocal() + " - " + partido.getMarcadorVisitante();
        }
        return partido.getId() + " | " + partido.getFecha() + " | " + partido.describir() + " | " + marcador;
    }

    /**
     * RF-E-05 - Registrar el partido
     *
     * Se escogen los dos equipos y la fecha. El club revisa que sean del mismo deporte y categoría y que cada
     * uno tenga el mínimo de jugadores habilitados.
     */
    public static void programarPartidoInterno(Club club)
    {
        Entrada.subtitulo("Programar un partido interno");
        Equipo local = escogerEquipo(club, "Equipo local");
        if (local == null)
        {
            return;
        }
        Equipo visitante = escogerEquipo(club, "Equipo visitante");
        if (visitante == null)
        {
            return;
        }
        LocalDate fecha = Entrada.fecha("Fecha del partido");
        String id = "PI-" + (club.getPartidos().size() + 1);
        try
        {
            PartidoInterno partido = club.programarPartidoInterno(id, local, visitante, fecha);
            Entrada.exito("Partido programado: " + describirPartido(partido));
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
    }

    /**
     * RF-A-07, RF-S-04 - Consultar el historial de partidos
     *
     * Los partidos internos y de competencia de un equipo, jugados o por jugar.
     */
    public static void historialDeEquipo(Club club)
    {
        Equipo equipo = escogerEquipo(club, "De que equipo quiere ver los partidos?");
        if (equipo == null)
        {
            return;
        }
        List<Partido> partidos = new GeneradorReportes(club).resultadosDeEquipo(equipo);
        Entrada.subtitulo("Partidos de " + equipo.getNombre());
        if (partidos.isEmpty())
        {
            Entrada.linea("Todavia no tiene partidos.");
        }
        for (Partido partido : partidos)
        {
            Entrada.linea(describirPartido(partido));
        }
        Entrada.pausa();
    }

    /**
     * RF-E-09, RF-A-11 - Consultar asistencia y desempeño acumulado
     *
     * Se escoge un jugador que haya sido convocado alguna vez y un periodo; el reporte cuenta cuántas veces
     * asistió, faltó o llegó tarde.
     */
    public static void consultarAsistencia(Club club)
    {
        List<PerfilJugador> convocados = new ArrayList<PerfilJugador>();
        List<String> nombres = new ArrayList<String>();
        for (SesionEntrenamiento sesion : club.getSesiones())
        {
            for (RegistroAsistencia registro : sesion.getRegistros())
            {
                if (!convocados.contains(registro.getJugador()))
                {
                    convocados.add(registro.getJugador());
                    nombres.add(registro.getJugador().getUsuario().getNombre());
                }
            }
        }
        int posicion = Entrada.escoger("De que jugador?", nombres);
        if (posicion < 0)
        {
            return;
        }
        PerfilJugador jugador = convocados.get(posicion);
        LocalDate desde = Entrada.fecha("Desde");
        LocalDate hasta = Entrada.fecha("Hasta");
        Map<EstadoAsistencia, Integer> conteo = new GeneradorReportes(club).asistenciaDeJugador(jugador, desde, hasta);
        Entrada.subtitulo("Asistencia de " + jugador.getUsuario().getNombre());
        for (EstadoAsistencia estado : EstadoAsistencia.values())
        {
            Integer veces = conteo.get(estado);
            if (veces == null)
            {
                veces = 0;
            }
            Entrada.linea(Entrada.bonito(estado) + ": " + veces);
        }
        for (SesionEntrenamiento sesion : club.getSesiones())
        {
            for (RegistroAsistencia registro : sesion.getRegistros())
            {
                if (registro.getJugador() == jugador && registro.getNotaDesempeno() != null)
                {
                    Entrada.linea("Nota en " + sesion.getId() + " (" + sesion.getFecha() + "): " + registro.getNotaDesempeno());
                }
            }
        }
        Entrada.pausa();
    }

    // ------------------------------------------------------------------
    // Compras
    // ------------------------------------------------------------------

    public static UbicacionInventario buscarUbicacion(Club club, TipoUbicacion tipo)
    {
        for (UbicacionInventario ubicacion : club.getUbicaciones())
        {
            if (ubicacion.getTipo() == tipo)
            {
                return ubicacion;
            }
        }
        return null;
    }

    /**
     * RF-S-12, RF-E-12 - Comprar en la tienda
     *
     * Artículos deportivos. Se paga IVA.
     */
    public static void comprarEnTienda(Club club, Usuario comprador)
    {
        UbicacionInventario tienda = buscarUbicacion(club, TipoUbicacion.TIENDA);
        Entrada.subtitulo("Compra en la tienda");
        VentaTienda venta = new VentaTienda(nuevoIdVenta(club), LocalDateTime.now().withNano(0), comprador);
        if (escogerProductos(club, venta, tienda))
        {
            terminarCompra(club, venta, tienda, comprador);
        }
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * Bebidas y snacks. Antes de pagar se muestran los alérgenos de lo que se va a comprar y se pregunta por
     * la propina. Si el comprador está en una cancha donde no se permite bebida caliente, el club no deja
     * venderle café (RN-32).
     */
    public static void comprarEnCafeteria(Club club, Usuario comprador)
    {
        UbicacionInventario cafeteria = buscarUbicacion(club, TipoUbicacion.CAFETERIA);
        Entrada.subtitulo("Compra en la cafeteria");
        VentaCafeteria venta = new VentaCafeteria(nuevoIdVenta(club), LocalDateTime.now().withNano(0), comprador);
        if (!escogerProductos(club, venta, cafeteria))
        {
            return;
        }

        List<Alergeno> alergenos = venta.consultarAlergenos();
        if (!alergenos.isEmpty())
        {
            String lista = "";
            for (Alergeno alergeno : alergenos)
            {
                lista = lista + alergeno.getNombre() + " ";
            }
            Entrada.aviso("Atencion, su compra contiene: " + lista);
        }
        int propina = Entrada.entero("Propina en porcentaje (0, 5 o 10)", 0, 10);
        venta.setPorcentajePropina(propina / 100.0);
        terminarCompra(club, venta, cafeteria, comprador);
    }

    private static String nuevoIdVenta(Club club)
    {
        return "V-" + (club.getVentas().size() + 1);
    }

    /**
     * Muestra lo que hay en el punto de venta y deja agregar productos hasta que la persona termine.
     * Devuelve false si al final no se agregó nada.
     */
    private static boolean escogerProductos(Club club, Venta venta, UbicacionInventario ubicacion)
    {
        boolean seguir = true;
        while (seguir)
        {
            List<Existencia> disponibles = new ArrayList<Existencia>();
            List<String> nombres = new ArrayList<String>();
            for (Existencia existencia : club.consultarInventario(ubicacion))
            {
                if (existencia.getCantidadDisponible() > 0)
                {
                    disponibles.add(existencia);
                    nombres.add(existencia.getProducto().getNombre() + " - " + Entrada.pesos(existencia.getProducto().getPrecioBase())
                            + " (quedan " + existencia.getCantidadDisponible() + ")");
                }
            }
            mostrarCarrito(venta);
            int posicion = Entrada.escoger("Que quiere agregar?", nombres, "Terminar la compra");
            if (posicion < 0)
            {
                seguir = false;
            }
            else
            {
                Existencia escogida = disponibles.get(posicion);
                int cantidad = Entrada.entero("Cuantas unidades", 1, escogida.getCantidadDisponible());
                try
                {
                    venta.agregarLinea(escogida.getProducto(), cantidad);
                }
                catch (ReglaDominioException e)
                {
                    Entrada.rechazo(e);
                }
            }
        }
        if (venta.getLineas().isEmpty())
        {
            Entrada.aviso("No agrego nada: no se hizo la compra.");
            return false;
        }
        return true;
    }

    private static void mostrarCarrito(Venta venta)
    {
        if (venta.getLineas().isEmpty())
        {
            return;
        }
        Entrada.subtitulo("Lleva hasta ahora");
        for (LineaVenta linea : venta.getLineas())
        {
            Entrada.linea(linea.getCantidad() + " x " + linea.getProducto().getNombre() + " = " + Entrada.pesos(linea.getSubtotal()));
        }
    }

    /**
     * El final de cualquier compra: código de descuento, puntos y registro. El club revisa existencias y
     * bebida caliente; la venta calcula descuento, impuesto, propina y puntos (RN-29 a RN-34).
     */
    private static void terminarCompra(Club club, Venta venta, UbicacionInventario ubicacion, Usuario comprador)
    {
        String codigo = Entrada.texto("Codigo de descuento (Enter si no tiene)");
        if (!codigo.isEmpty())
        {
            CodigoDescuento descuento = club.buscarCodigoDescuento(codigo);
            if (descuento == null)
            {
                Entrada.aviso("Ese codigo no existe; se sigue sin descuento.");
            }
            else
            {
                try
                {
                    venta.aplicarCodigoDescuento(descuento);
                    Entrada.linea("Descuento aplicado: " + Math.round(descuento.porcentajePara(comprador) * 100) + "%");
                }
                catch (ReglaDominioException e)
                {
                    Entrada.rechazo(e);
                }
            }
        }

        int puntos = 0;
        if (comprador.getSaldoPuntosFidelidad() > 0)
        {
            Entrada.linea("Tiene " + comprador.getSaldoPuntosFidelidad() + " puntos (1 punto = $1).");
            puntos = Entrada.entero("Cuantos puntos quiere usar", 0, comprador.getSaldoPuntosFidelidad());
        }

        try
        {
            club.registrarVenta(venta, ubicacion, puntos);
            Entrada.exito("Compra " + venta.getId() + " registrada.");
            Entrada.linea("Subtotal:          " + Entrada.pesos(venta.getSubtotal()));
            Entrada.linea("Impuestos:         " + Entrada.pesos(venta.getTotalImpuestos()));
            Entrada.linea("Puntos usados:     " + venta.getPuntosRedimidos());
            Entrada.linea("TOTAL A PAGAR:     " + Entrada.pesos(venta.getTotal()));
            Entrada.linea("Puntos ganados:    " + venta.getPuntosGenerados() + " (ahora tiene " + comprador.getSaldoPuntosFidelidad() + ")");
        }
        catch (ReglaDominioException e)
        {
            Entrada.rechazo(e);
        }
        Entrada.pausa();
    }
}
