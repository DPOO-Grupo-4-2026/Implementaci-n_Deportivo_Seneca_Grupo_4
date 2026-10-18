package uniandes.dpoo.proyecto1.persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.administracion.Club;
import uniandes.dpoo.proyecto1.administracion.EstadoSolicitud;
import uniandes.dpoo.proyecto1.administracion.SolicitudCambioTurno;
import uniandes.dpoo.proyecto1.administracion.Turno;
import uniandes.dpoo.proyecto1.competencias.Competencia;
import uniandes.dpoo.proyecto1.competencias.CompetenciaLiga;
import uniandes.dpoo.proyecto1.competencias.CompetenciaPorFases;
import uniandes.dpoo.proyecto1.competencias.FilaTabla;
import uniandes.dpoo.proyecto1.competencias.InscripcionCompetencia;
import uniandes.dpoo.proyecto1.competencias.NominaCompetencia;
import uniandes.dpoo.proyecto1.competencias.PartidoCompetencia;
import uniandes.dpoo.proyecto1.competencias.RenglonNomina;
import uniandes.dpoo.proyecto1.competencias.TablaPosiciones;
import uniandes.dpoo.proyecto1.conjunto.Categoria;
import uniandes.dpoo.proyecto1.conjunto.DeporteConjunto;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaBaloncesto;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaFutbol;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaPartido;
import uniandes.dpoo.proyecto1.conjunto.EstadisticaVoleibol;
import uniandes.dpoo.proyecto1.conjunto.InscripcionEquipo;
import uniandes.dpoo.proyecto1.conjunto.MotivoSancion;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.conjunto.PartidoInterno;
import uniandes.dpoo.proyecto1.conjunto.Sancion;
import uniandes.dpoo.proyecto1.entrenamiento.EstadoAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.RegistroAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.individuales.AjusteRanking;
import uniandes.dpoo.proyecto1.individuales.CanchaPadel;
import uniandes.dpoo.proyecto1.individuales.CanchaTenis;
import uniandes.dpoo.proyecto1.individuales.DisciplinaIndividual;
import uniandes.dpoo.proyecto1.individuales.EstadisticaRaqueta;
import uniandes.dpoo.proyecto1.individuales.EstadisticaTenisMesa;
import uniandes.dpoo.proyecto1.individuales.InscripcionDisciplina;
import uniandes.dpoo.proyecto1.individuales.Instalacion;
import uniandes.dpoo.proyecto1.individuales.MesaTenisMesa;
import uniandes.dpoo.proyecto1.individuales.Modalidad;
import uniandes.dpoo.proyecto1.individuales.Nivel;
import uniandes.dpoo.proyecto1.individuales.PartidoPractica;
import uniandes.dpoo.proyecto1.individuales.Reserva;
import uniandes.dpoo.proyecto1.tienda.Alergeno;
import uniandes.dpoo.proyecto1.tienda.ArticuloDeportivo;
import uniandes.dpoo.proyecto1.tienda.Bebida;
import uniandes.dpoo.proyecto1.tienda.CodigoDescuento;
import uniandes.dpoo.proyecto1.tienda.EstadoSugerencia;
import uniandes.dpoo.proyecto1.tienda.Existencia;
import uniandes.dpoo.proyecto1.tienda.LineaVenta;
import uniandes.dpoo.proyecto1.tienda.MovimientoInventario;
import uniandes.dpoo.proyecto1.tienda.MovimientoPuntos;
import uniandes.dpoo.proyecto1.tienda.PagoMensualidad;
import uniandes.dpoo.proyecto1.tienda.Producto;
import uniandes.dpoo.proyecto1.tienda.Snack;
import uniandes.dpoo.proyecto1.tienda.SugerenciaProducto;
import uniandes.dpoo.proyecto1.tienda.TipoMovimiento;
import uniandes.dpoo.proyecto1.tienda.TipoPuntos;
import uniandes.dpoo.proyecto1.tienda.TipoUbicacion;
import uniandes.dpoo.proyecto1.tienda.UbicacionInventario;
import uniandes.dpoo.proyecto1.tienda.Venta;
import uniandes.dpoo.proyecto1.tienda.VentaCafeteria;
import uniandes.dpoo.proyecto1.tienda.VentaTienda;
import uniandes.dpoo.proyecto1.usuarios.Administrador;
import uniandes.dpoo.proyecto1.usuarios.Empleado;
import uniandes.dpoo.proyecto1.usuarios.Entrenador;
import uniandes.dpoo.proyecto1.usuarios.Fisioterapeuta;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;
import uniandes.dpoo.proyecto1.usuarios.Socio;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Guarda y carga todo el club en un archivo de texto plano.
 *
 * El archivo tiene una línea por objeto. La primera palabra dice qué es (USUARIO, EQUIPO, VENTA...) y los
 * demás campos van separados por punto y coma. Las relaciones se escriben con el nombre, el login o el
 * identificador del otro objeto; por ejemplo, una inscripción dice el nombre del equipo y el login del
 * jugador. Por eso las líneas se escriben en orden: primero lo que otros nombran (usuarios, equipos) y
 * después lo que los nombra a ellos (inscripciones, partidos).
 *
 * Al cargar no se revisan reglas: se recupera el club tal como estaba cuando se guardó, con los constructores,
 * los métodos agregar y los setters.
 */
public class PersistenciaClub
{
    private static final String SEPARADOR = ";";
    private static final String VACIO = "-";

    /**
     * El club que se está armando mientras se lee el archivo.
     */
    private Club club;

    // ==================================================================
    // GUARDAR
    // ==================================================================

    public void guardar(Club club, File archivo) throws IOException
    {
        if (archivo.getParentFile() != null)
        {
            archivo.getParentFile().mkdirs();
        }
        PrintWriter escritor = new PrintWriter(new FileWriter(archivo));
        try
        {
            escritor.println("# Club Deportivo Seneca. Una linea por objeto; los campos van separados por punto y coma.");
            escribir(escritor, "CLUB", club.getNombre(), club.getHoraApertura(), club.getHoraCierre());
            escritor.println("# USUARIOS Y TURNOS");
            guardarUsuariosYTurnos(escritor, club);
            escritor.println("# DEPORTES DE CONJUNTO");
            guardarDeportesDeConjunto(escritor, club);
            escritor.println("# DEPORTES INDIVIDUALES");
            guardarDeportesIndividuales(escritor, club);
            escritor.println("# COMPETENCIAS");
            guardarCompetencias(escritor, club);
            escritor.println("# ESTADISTICAS DE TODOS LOS PARTIDOS");
            guardarEstadisticas(escritor, club);
            escritor.println("# ENTRENAMIENTO");
            guardarEntrenamiento(escritor, club);
            escritor.println("# TIENDA Y CAFETERIA");
            guardarTienda(escritor, club);
        }
        finally
        {
            escritor.close();
        }
    }

    /**
     * Escribe una línea: el tipo y después los campos separados por punto y coma. Un campo nulo se escribe
     * como "-", y si un texto trae punto y coma se cambia por coma para no dañar la línea.
     */
    private void escribir(PrintWriter escritor, String tipo, Object... campos)
    {
        String linea = tipo;
        for (Object campo : campos)
        {
            String texto = VACIO;
            if (campo != null)
            {
                texto = campo.toString().replace(SEPARADOR, ",");
            }
            linea = linea + SEPARADOR + texto;
        }
        escritor.println(linea);
    }

    private void guardarUsuariosYTurnos(PrintWriter escritor, Club club)
    {
        for (Usuario usuario : club.getUsuarios())
        {
            escribir(escritor, "USUARIO", usuario.getRol(), usuario.getLogin(), usuario.getPassword(), usuario.getNombre(),
                    usuario.getFechaNacimiento(), usuario.isActivo(), usuario.getSaldoPuntosFidelidad());
        }
        for (Usuario usuario : club.getUsuarios())
        {
            if (usuario.getPerfilJugador() != null)
            {
                escribir(escritor, "PERFIL", usuario.getLogin(), usuario.getPerfilJugador().isActivo());
            }
            CodigoDescuento codigo = usuario.getCodigoDescuento();
            if (codigo != null)
            {
                escribir(escritor, "CODIGO", codigo.getCodigo(), usuario.getLogin(), codigo.isActivo());
            }
        }
        // Las solicitudes nombran el turno por su posición en la lista de turnos del club
        for (Turno turno : club.getTurnos())
        {
            escribir(escritor, "TURNO", turno.getEmpleado().getLogin(), turno.getDia(), turno.getHoraInicio(), turno.getHoraFin());
        }
        for (SolicitudCambioTurno solicitud : club.getSolicitudes())
        {
            escribir(escritor, "SOLICITUD", club.getTurnos().indexOf(solicitud.getTurno()), solicitud.getSolicitante().getLogin(),
                    solicitud.getReemplazo().getLogin(), solicitud.getFechaSolicitud(), solicitud.getMotivo(), solicitud.getEstado(),
                    login(solicitud.getResueltaPor()));
        }
    }

    private void guardarDeportesDeConjunto(PrintWriter escritor, Club club)
    {
        for (DeporteConjunto deporte : club.getDeportes())
        {
            escribir(escritor, "DEPORTE", deporte.getNombre(), deporte.getMinJugadoresHabilitados(), deporte.permiteEmpate(),
                    deporte.getPuntosVictoria(), deporte.getPuntosEmpate(), deporte.getPuntosDerrota());
        }
        for (Categoria categoria : club.getCategorias())
        {
            escribir(escritor, "CATEGORIA", categoria.getNombre(), categoria.getEdadMinima(), categoria.getEdadMaxima());
        }
        for (Equipo equipo : club.getEquipos())
        {
            escribir(escritor, "EQUIPO", equipo.getNombre(), equipo.getCupoMaximo(), equipo.getDeporte().getNombre(),
                    equipo.getCategoria().getNombre(), equipo.isActivo());
            for (InscripcionEquipo inscripcion : equipo.getInscripciones())
            {
                String jugador = inscripcion.getJugador().getUsuario().getLogin();
                escribir(escritor, "INSCRIPCION_EQUIPO", equipo.getNombre(), jugador, inscripcion.getPosicion(),
                        inscripcion.getNumeroCamiseta(), inscripcion.getAmarillasAcumuladas());
                for (Sancion sancion : inscripcion.getSanciones())
                {
                    escribir(escritor, "SANCION", equipo.getNombre(), jugador, sancion.getMotivo(), sancion.getPartidosPendientes(),
                            sancion.isCumplida());
                }
            }
        }
        for (PartidoInterno partido : club.getPartidos())
        {
            escribir(escritor, "PARTIDO_INTERNO", partido.getId(), partido.getFecha(), partido.getLocal().getNombre(),
                    partido.getVisitante().getNombre(), partido.getMarcadorLocal(), partido.getMarcadorVisitante(), partido.isJugado());
        }
    }

    private void guardarDeportesIndividuales(PrintWriter escritor, Club club)
    {
        for (DisciplinaIndividual disciplina : club.getDisciplinas())
        {
            escribir(escritor, "DISCIPLINA", disciplina.getNombre(), disciplina.admiteDobles(), disciplina.getMaxInstalacionesSimultaneas());
            for (Instalacion instalacion : disciplina.getInstalaciones())
            {
                // El nombre de la clase dice qué tipo de instalación es; cada tipo tiene un dato propio
                String tipo = instalacion.getClass().getSimpleName();
                Object propio = VACIO;
                if (tipo.equals("CanchaTenis"))
                {
                    propio = ((CanchaTenis) instalacion).admiteSencillos();
                }
                else if (tipo.equals("MesaTenisMesa"))
                {
                    propio = ((MesaTenisMesa) instalacion).admiteDobles();
                }
                escribir(escritor, "INSTALACION", disciplina.getNombre(), tipo, instalacion.getCodigo(), instalacion.getNombre(),
                        instalacion.getCapacidadMaxima(), instalacion.isTechada(), propio);
            }
            for (InscripcionDisciplina inscripcion : disciplina.getInscripciones())
            {
                escribir(escritor, "INSCRIPCION_DISCIPLINA", disciplina.getNombre(), inscripcion.getJugador().getUsuario().getLogin(),
                        inscripcion.getNivel(), inscripcion.getPuntajeRanking());
            }
            for (Reserva reserva : disciplina.getReservas())
            {
                escribir(escritor, "RESERVA", disciplina.getNombre(), reserva.getId(), reserva.getSolicitante().getLogin(),
                        reserva.getInstalacion().getCodigo(), reserva.getFecha(), reserva.getHoraInicio(), reserva.getHoraFin(),
                        reserva.getModalidad(), reserva.getNumeroJugadores());
                PartidoPractica partido = reserva.getPartido();
                if (partido != null)
                {
                    String jugadores = "";
                    for (InscripcionDisciplina participante : partido.getParticipantes())
                    {
                        if (!jugadores.isEmpty())
                        {
                            jugadores = jugadores + ",";
                        }
                        jugadores = jugadores + participante.getJugador().getUsuario().getLogin();
                    }
                    escribir(escritor, "PRACTICA", disciplina.getNombre(), reserva.getId(), partido.getId(), partido.getFecha(),
                            partido.getModalidad(), partido.getMarcadorLocal(), partido.getMarcadorVisitante(), partido.isJugado(), jugadores);
                }
            }
            for (InscripcionDisciplina inscripcion : disciplina.getInscripciones())
            {
                for (AjusteRanking ajuste : inscripcion.getAjustes())
                {
                    escribir(escritor, "AJUSTE", disciplina.getNombre(), inscripcion.getJugador().getUsuario().getLogin(), ajuste.getFecha(),
                            ajuste.getPuntajeAntes(), ajuste.getPuntajeDespues(), ajuste.getPartido().getId());
                }
            }
        }
    }

    private void guardarCompetencias(PrintWriter escritor, Club club)
    {
        for (Competencia competencia : club.getCompetencias())
        {
            escribir(escritor, "COMPETENCIA", competencia.getClass().getSimpleName(), competencia.getNombre(),
                    competencia.getEntidadOrganizadora(), competencia.getTemporada(), competencia.getNominaMinima(),
                    competencia.getDeporte().getNombre(), competencia.getCategoria().getNombre());
            for (InscripcionCompetencia inscripcion : competencia.getInscripciones())
            {
                String nomina = "";
                for (RenglonNomina renglon : inscripcion.getNomina().getRenglones())
                {
                    if (!nomina.isEmpty())
                    {
                        nomina = nomina + ",";
                    }
                    nomina = nomina + renglon.getJugador().getUsuario().getLogin();
                }
                escribir(escritor, "INSCRIPCION_COMPETENCIA", competencia.getNombre(), inscripcion.getEquipo().getNombre(), nomina);
            }
            for (PartidoCompetencia partido : competencia.getPartidos())
            {
                escribir(escritor, "PARTIDO_COMPETENCIA", competencia.getNombre(), partido.getId(), partido.getFecha(),
                        partido.getEquipo().getNombre(), partido.getInstitucionRival(), partido.getEscenario(), partido.getFase(),
                        partido.getMarcadorLocal(), partido.getMarcadorVisitante(), partido.isJugado());
            }
            for (TablaPosiciones tabla : competencia.getTablas())
            {
                for (FilaTabla fila : tabla.getFilas())
                {
                    escribir(escritor, "FILA", competencia.getNombre(), tabla.getNombre(), fila.getEquipo().getNombre(),
                            fila.getPartidosJugados(), fila.getGanados(), fila.getEmpatados(), fila.getPerdidos(), fila.getAFavor(),
                            fila.getEnContra(), fila.getPuntos());
                }
            }
        }
    }

    /**
     * Las estadísticas van al final de los deportes porque pueden ser de cualquier partido: interno, de
     * competencia o de práctica. Cada tipo de estadística guarda sus propios números.
     */
    private void guardarEstadisticas(PrintWriter escritor, Club club)
    {
        for (Partido partido : todosLosPartidos(club))
        {
            for (EstadisticaPartido estadistica : partido.getEstadisticas())
            {
                String tipo = estadistica.getClass().getSimpleName();
                String jugador = estadistica.getJugador().getUsuario().getLogin();
                if (tipo.equals("EstadisticaFutbol"))
                {
                    EstadisticaFutbol e = (EstadisticaFutbol) estadistica;
                    escribir(escritor, "ESTADISTICA", partido.getId(), jugador, tipo, e.getGoles(), e.getAsistencias(),
                            e.getTarjetasAmarillas(), e.getTarjetasRojas());
                }
                else if (tipo.equals("EstadisticaBaloncesto"))
                {
                    EstadisticaBaloncesto e = (EstadisticaBaloncesto) estadistica;
                    escribir(escritor, "ESTADISTICA", partido.getId(), jugador, tipo, e.getPuntos(), e.getRebotes());
                }
                else if (tipo.equals("EstadisticaVoleibol"))
                {
                    EstadisticaVoleibol e = (EstadisticaVoleibol) estadistica;
                    escribir(escritor, "ESTADISTICA", partido.getId(), jugador, tipo, e.getAces(), e.getBloqueos());
                }
                else if (tipo.equals("EstadisticaRaqueta"))
                {
                    EstadisticaRaqueta e = (EstadisticaRaqueta) estadistica;
                    escribir(escritor, "ESTADISTICA", partido.getId(), jugador, tipo, e.getSetsGanados(), e.getGamesGanados(),
                            e.getAces(), e.getErroresNoForzados());
                }
                else
                {
                    EstadisticaTenisMesa e = (EstadisticaTenisMesa) estadistica;
                    escribir(escritor, "ESTADISTICA", partido.getId(), jugador, tipo, e.getSetsGanados(), e.getPuntosTotales());
                }
            }
        }
    }

    private void guardarEntrenamiento(PrintWriter escritor, Club club)
    {
        for (SesionEntrenamiento sesion : club.getSesiones())
        {
            String equipo = null;
            if (sesion.getEquipo() != null)
            {
                equipo = sesion.getEquipo().getNombre();
            }
            String disciplina = null;
            if (sesion.getDisciplina() != null)
            {
                disciplina = sesion.getDisciplina().getNombre();
            }
            escribir(escritor, "SESION", sesion.getId(), sesion.getEntrenador().getLogin(), sesion.getInstalacion().getCodigo(),
                    sesion.getFecha(), sesion.getHoraInicio(), sesion.getDuracionMinutos(), equipo, disciplina, sesion.isCerrada());
            for (RegistroAsistencia registro : sesion.getRegistros())
            {
                escribir(escritor, "REGISTRO", sesion.getId(), registro.getJugador().getUsuario().getLogin(), registro.getEstado(),
                        registro.getNotaDesempeno(), registro.getComentario());
            }
        }
    }

    private void guardarTienda(PrintWriter escritor, Club club)
    {
        for (Producto producto : club.getProductos())
        {
            // Cada tipo de producto tiene un dato propio: la categoría del artículo o si la bebida es caliente
            String tipo = producto.getClass().getSimpleName();
            Object propio = VACIO;
            if (tipo.equals("ArticuloDeportivo"))
            {
                propio = ((ArticuloDeportivo) producto).getCategoria();
            }
            else if (tipo.equals("Bebida"))
            {
                propio = ((Bebida) producto).isCaliente();
            }
            escribir(escritor, "PRODUCTO", tipo, producto.getCodigo(), producto.getNombre(), producto.getPrecioBase(), propio);
            if (tipo.equals("Snack"))
            {
                for (Alergeno alergeno : ((Snack) producto).getAlergenos())
                {
                    escribir(escritor, "ALERGENO", producto.getCodigo(), alergeno.getNombre());
                }
            }
        }
        for (UbicacionInventario ubicacion : club.getUbicaciones())
        {
            escribir(escritor, "UBICACION", ubicacion.getNombre(), ubicacion.getTipo());
            for (Existencia existencia : ubicacion.getExistencias())
            {
                escribir(escritor, "EXISTENCIA", ubicacion.getNombre(), existencia.getProducto().getCodigo(), existencia.getCantidadDisponible());
            }
        }
        for (Venta venta : club.getVentas())
        {
            String codigo = null;
            if (venta.getCodigoDescuento() != null)
            {
                codigo = venta.getCodigoDescuento().getCodigo();
            }
            // La propina solo existe en la cafetería
            double propina = 0;
            if (venta.getRubro().equals("Cafeteria"))
            {
                propina = ((VentaCafeteria) venta).getPorcentajePropina();
            }
            escribir(escritor, "VENTA", venta.getRubro(), venta.getId(), venta.getFecha(), venta.getComprador().getLogin(),
                    venta.getSubtotal(), venta.getTotalImpuestos(), venta.getTotal(), venta.getPuntosGenerados(),
                    venta.getPuntosRedimidos(), codigo, venta.isCerrada(), propina);
            for (LineaVenta linea : venta.getLineas())
            {
                escribir(escritor, "LINEA", venta.getId(), linea.getProducto().getCodigo(), linea.getCantidad(), linea.getPrecioUnitario());
            }
        }
        for (Usuario usuario : club.getUsuarios())
        {
            for (MovimientoPuntos movimiento : usuario.getMovimientosPuntos())
            {
                escribir(escritor, "PUNTOS", usuario.getLogin(), movimiento.getFecha(), movimiento.getTipo(), movimiento.getCantidad(),
                        movimiento.getVenta().getId());
            }
        }
        for (MovimientoInventario movimiento : club.getMovimientosInventario())
        {
            escribir(escritor, "MOVIMIENTO", movimiento.getFecha(), movimiento.getProducto().getCodigo(), movimiento.getCantidad(),
                    movimiento.getTipo(), nombre(movimiento.getOrigen()), nombre(movimiento.getDestino()));
        }
        for (SugerenciaProducto sugerencia : club.getSugerencias())
        {
            escribir(escritor, "SUGERENCIA", sugerencia.getDescripcion(), sugerencia.getFecha(), sugerencia.getAutor().getLogin(),
                    sugerencia.getEstado(), login(sugerencia.getResueltaPor()));
        }
        for (PagoMensualidad pago : club.getPagos())
        {
            escribir(escritor, "PAGO", pago.getSocio().getLogin(), pago.getPeriodo(), pago.getMonto(), pago.getFecha());
        }
    }

    private String login(Usuario usuario)
    {
        if (usuario == null)
        {
            return null;
        }
        return usuario.getLogin();
    }

    private String nombre(UbicacionInventario ubicacion)
    {
        if (ubicacion == null)
        {
            return null;
        }
        return ubicacion.getNombre();
    }

    // ==================================================================
    // CARGAR
    // ==================================================================

    /**
     * Lee el archivo línea por línea y arma el club. Las líneas vacías y las que empiezan con # son
     * comentarios y se saltan. Si una línea no se entiende, avisa en qué línea está el problema.
     */
    public Club cargar(File archivo) throws IOException
    {
        club = null;
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        try
        {
            String linea = lector.readLine();
            int numero = 1;
            while (linea != null)
            {
                if (!linea.trim().isEmpty() && !linea.startsWith("#"))
                {
                    try
                    {
                        leerLinea(linea.split(SEPARADOR, -1));
                    }
                    catch (Exception e)
                    {
                        throw new IOException("Linea " + numero + " de " + archivo.getName() + " no se pudo leer: " + linea);
                    }
                }
                linea = lector.readLine();
                numero++;
            }
        }
        finally
        {
            lector.close();
        }
        return club;
    }

    /**
     * Mira la primera palabra de la línea y arma el objeto que corresponde.
     */
    private void leerLinea(String[] c) throws ReglaDominioException
    {
        String tipo = c[0];
        if (tipo.equals("CLUB"))
        {
            club = new Club(c[1], LocalTime.parse(c[2]), LocalTime.parse(c[3]));
        }
        else if (tipo.equals("USUARIO"))
        {
            leerUsuario(c);
        }
        else if (tipo.equals("PERFIL"))
        {
            PerfilJugador perfil = usuario(c[1]).crearPerfilJugador();
            perfil.setActivo(Boolean.parseBoolean(c[2]));
        }
        else if (tipo.equals("CODIGO"))
        {
            Empleado empleado = (Empleado) usuario(c[2]);
            CodigoDescuento codigo = new CodigoDescuento(c[1], empleado);
            codigo.setActivo(Boolean.parseBoolean(c[3]));
            empleado.setCodigoDescuento(codigo);
        }
        else if (tipo.equals("TURNO"))
        {
            Empleado empleado = (Empleado) usuario(c[1]);
            Turno turno = new Turno(empleado, DayOfWeek.valueOf(c[2]), LocalTime.parse(c[3]), LocalTime.parse(c[4]));
            empleado.agregarTurno(turno);
            club.agregarTurno(turno);
        }
        else if (tipo.equals("SOLICITUD"))
        {
            Turno turno = club.getTurnos().get(Integer.parseInt(c[1]));
            SolicitudCambioTurno solicitud = new SolicitudCambioTurno(turno, (Empleado) usuario(c[2]), (Empleado) usuario(c[3]),
                    LocalDate.parse(c[4]), c[5]);
            solicitud.setEstado(EstadoSolicitud.valueOf(c[6]));
            solicitud.setResueltaPor((Administrador) usuario(c[7]));
            club.agregarSolicitud(solicitud);
        }
        else if (tipo.equals("DEPORTE"))
        {
            club.agregarDeporte(new DeporteConjunto(c[1], entero(c[2]), Boolean.parseBoolean(c[3]), entero(c[4]), entero(c[5]),
                    entero(c[6])));
        }
        else if (tipo.equals("CATEGORIA"))
        {
            club.agregarCategoria(new Categoria(c[1], entero(c[2]), entero(c[3])));
        }
        else if (tipo.equals("EQUIPO"))
        {
            Equipo equipo = new Equipo(c[1], entero(c[2]), deporte(c[3]), categoria(c[4]));
            equipo.setActivo(Boolean.parseBoolean(c[5]));
            club.agregarEquipo(equipo);
        }
        else if (tipo.equals("INSCRIPCION_EQUIPO"))
        {
            Equipo equipo = equipo(c[1]);
            PerfilJugador perfil = usuario(c[2]).getPerfilJugador();
            InscripcionEquipo inscripcion = new InscripcionEquipo(perfil, equipo, c[3], entero(c[4]));
            inscripcion.setAmarillasAcumuladas(entero(c[5]));
            equipo.agregarInscripcion(inscripcion);
            perfil.agregarInscripcionEquipo(inscripcion);
        }
        else if (tipo.equals("SANCION"))
        {
            Sancion sancion = new Sancion(MotivoSancion.valueOf(c[3]), entero(c[4]));
            sancion.setCumplida(Boolean.parseBoolean(c[5]));
            inscripcionEquipo(equipo(c[1]), c[2]).agregarSancion(sancion);
        }
        else if (tipo.equals("PARTIDO_INTERNO"))
        {
            PartidoInterno partido = new PartidoInterno(c[1], LocalDate.parse(c[2]), equipo(c[3]), equipo(c[4]));
            ponerMarcador(partido, c[5], c[6], c[7]);
            club.agregarPartido(partido);
        }
        else if (tipo.equals("DISCIPLINA"))
        {
            club.agregarDisciplina(new DisciplinaIndividual(c[1], Boolean.parseBoolean(c[2]), entero(c[3])));
        }
        else if (tipo.equals("INSTALACION"))
        {
            leerInstalacion(c);
        }
        else if (tipo.equals("INSCRIPCION_DISCIPLINA"))
        {
            DisciplinaIndividual disciplina = disciplina(c[1]);
            PerfilJugador perfil = usuario(c[2]).getPerfilJugador();
            InscripcionDisciplina inscripcion = new InscripcionDisciplina(perfil, disciplina, Nivel.valueOf(c[3]), entero(c[4]));
            disciplina.agregarInscripcion(inscripcion);
            perfil.agregarInscripcionDisciplina(inscripcion);
        }
        else if (tipo.equals("RESERVA"))
        {
            DisciplinaIndividual disciplina = disciplina(c[1]);
            Reserva reserva = new Reserva(c[2], (Socio) usuario(c[3]), instalacion(c[4]), LocalDate.parse(c[5]), LocalTime.parse(c[6]),
                    LocalTime.parse(c[7]), Modalidad.valueOf(c[8]), entero(c[9]));
            disciplina.agregarReserva(reserva);
        }
        else if (tipo.equals("PRACTICA"))
        {
            leerPractica(c);
        }
        else if (tipo.equals("AJUSTE"))
        {
            InscripcionDisciplina inscripcion = inscripcionDisciplina(disciplina(c[1]), c[2]);
            PartidoPractica partido = (PartidoPractica) partido(c[6]);
            AjusteRanking ajuste = new AjusteRanking(LocalDate.parse(c[3]), entero(c[4]), entero(c[5]), inscripcion, partido);
            inscripcion.agregarAjuste(ajuste);
            partido.agregarAjuste(ajuste);
        }
        else if (tipo.equals("COMPETENCIA"))
        {
            Competencia competencia;
            if (c[1].equals("CompetenciaLiga"))
            {
                competencia = new CompetenciaLiga(c[2], c[3], c[4], entero(c[5]), deporte(c[6]), categoria(c[7]));
            }
            else
            {
                competencia = new CompetenciaPorFases(c[2], c[3], c[4], entero(c[5]), deporte(c[6]), categoria(c[7]));
            }
            club.agregarCompetencia(competencia);
        }
        else if (tipo.equals("INSCRIPCION_COMPETENCIA"))
        {
            Competencia competencia = competencia(c[1]);
            NominaCompetencia nomina = new NominaCompetencia();
            if (!c[3].isEmpty())
            {
                for (String login : c[3].split(","))
                {
                    nomina.agregarRenglon(new RenglonNomina(usuario(login).getPerfilJugador()));
                }
            }
            competencia.agregarInscripcion(new InscripcionCompetencia(equipo(c[2]), competencia, nomina));
        }
        else if (tipo.equals("PARTIDO_COMPETENCIA"))
        {
            Competencia competencia = competencia(c[1]);
            PartidoCompetencia partido = new PartidoCompetencia(c[2], LocalDate.parse(c[3]), competencia, equipo(c[4]), c[5], c[6], c[7]);
            ponerMarcador(partido, c[8], c[9], c[10]);
            competencia.agregarPartido(partido);
        }
        else if (tipo.equals("FILA"))
        {
            FilaTabla fila = competencia(c[1]).obtenerFila(c[2], equipo(c[3]));
            fila.setPartidosJugados(entero(c[4]));
            fila.setGanados(entero(c[5]));
            fila.setEmpatados(entero(c[6]));
            fila.setPerdidos(entero(c[7]));
            fila.setAFavor(entero(c[8]));
            fila.setEnContra(entero(c[9]));
            fila.setPuntos(entero(c[10]));
        }
        else if (tipo.equals("ESTADISTICA"))
        {
            leerEstadistica(c);
        }
        else if (tipo.equals("SESION"))
        {
            SesionEntrenamiento sesion = new SesionEntrenamiento(c[1], (Entrenador) usuario(c[2]), instalacion(c[3]), LocalDate.parse(c[4]),
                    LocalTime.parse(c[5]), entero(c[6]));
            if (!c[7].equals(VACIO))
            {
                sesion.setEquipo(equipo(c[7]));
            }
            if (!c[8].equals(VACIO))
            {
                sesion.setDisciplina(disciplina(c[8]));
            }
            sesion.setCerrada(Boolean.parseBoolean(c[9]));
            club.agregarSesion(sesion);
        }
        else if (tipo.equals("REGISTRO"))
        {
            RegistroAsistencia registro = new RegistroAsistencia(usuario(c[2]).getPerfilJugador());
            if (!c[3].equals(VACIO))
            {
                registro.setEstado(EstadoAsistencia.valueOf(c[3]));
            }
            if (!c[4].equals(VACIO))
            {
                registro.setNotaDesempeno(entero(c[4]));
            }
            if (!c[5].equals(VACIO))
            {
                registro.setComentario(c[5]);
            }
            sesion(c[1]).agregarRegistro(registro);
        }
        else if (tipo.equals("PRODUCTO"))
        {
            leerProducto(c);
        }
        else if (tipo.equals("ALERGENO"))
        {
            ((Snack) producto(c[1])).agregarAlergeno(new Alergeno(c[2]));
        }
        else if (tipo.equals("UBICACION"))
        {
            club.agregarUbicacion(new UbicacionInventario(c[1], TipoUbicacion.valueOf(c[2])));
        }
        else if (tipo.equals("EXISTENCIA"))
        {
            ubicacion(c[1]).agregarExistencia(new Existencia(producto(c[2]), entero(c[3])));
        }
        else if (tipo.equals("VENTA"))
        {
            leerVenta(c);
        }
        else if (tipo.equals("LINEA"))
        {
            venta(c[1]).agregarLinea(new LineaVenta(producto(c[2]), entero(c[3]), Double.parseDouble(c[4])));
        }
        else if (tipo.equals("PUNTOS"))
        {
            usuario(c[1]).agregarMovimientoPuntos(new MovimientoPuntos(LocalDate.parse(c[2]), TipoPuntos.valueOf(c[3]), entero(c[4]),
                    venta(c[5])));
        }
        else if (tipo.equals("MOVIMIENTO"))
        {
            club.agregarMovimientoInventario(new MovimientoInventario(LocalDate.parse(c[1]), producto(c[2]), entero(c[3]),
                    TipoMovimiento.valueOf(c[4]), ubicacion(c[5]), ubicacion(c[6])));
        }
        else if (tipo.equals("SUGERENCIA"))
        {
            SugerenciaProducto sugerencia = new SugerenciaProducto(c[1], LocalDate.parse(c[2]), (Empleado) usuario(c[3]));
            sugerencia.setEstado(EstadoSugerencia.valueOf(c[4]));
            sugerencia.setResueltaPor((Administrador) usuario(c[5]));
            club.agregarSugerencia(sugerencia);
        }
        else if (tipo.equals("PAGO"))
        {
            club.agregarPago(new PagoMensualidad((Socio) usuario(c[1]), c[2], Double.parseDouble(c[3]), LocalDate.parse(c[4])));
        }
        else
        {
            throw new IllegalArgumentException("Tipo de linea desconocido: " + tipo);
        }
    }

    private void leerUsuario(String[] c)
    {
        Usuario usuario;
        LocalDate nacimiento = LocalDate.parse(c[5]);
        if (c[1].equals("Socio"))
        {
            usuario = new Socio(c[2], c[3], c[4], nacimiento);
        }
        else if (c[1].equals("Entrenador"))
        {
            usuario = new Entrenador(c[2], c[3], c[4], nacimiento);
        }
        else if (c[1].equals("Fisioterapeuta"))
        {
            usuario = new Fisioterapeuta(c[2], c[3], c[4], nacimiento);
        }
        else
        {
            usuario = new Administrador(c[2], c[3], c[4], nacimiento);
        }
        usuario.setActivo(Boolean.parseBoolean(c[6]));
        usuario.setSaldoPuntosFidelidad(entero(c[7]));
        club.agregarUsuario(usuario);
    }

    private void leerInstalacion(String[] c)
    {
        Instalacion instalacion;
        int capacidad = entero(c[5]);
        boolean techada = Boolean.parseBoolean(c[6]);
        if (c[2].equals("CanchaTenis"))
        {
            instalacion = new CanchaTenis(c[3], c[4], capacidad, techada, Boolean.parseBoolean(c[7]));
        }
        else if (c[2].equals("CanchaPadel"))
        {
            instalacion = new CanchaPadel(c[3], c[4], capacidad, techada);
        }
        else
        {
            instalacion = new MesaTenisMesa(c[3], c[4], capacidad, techada, Boolean.parseBoolean(c[7]));
        }
        disciplina(c[1]).agregarInstalacion(instalacion);
        club.agregarInstalacion(instalacion);
    }

    /**
     * Un partido de práctica vive en su reserva. Sus participantes son las inscripciones de esos jugadores en
     * la disciplina, en el orden en que juegan: los de posición par de un lado y los de posición impar del otro.
     */
    private void leerPractica(String[] c)
    {
        DisciplinaIndividual disciplina = disciplina(c[1]);
        Reserva reserva = reserva(disciplina, c[2]);
        PartidoPractica partido = new PartidoPractica(c[3], LocalDate.parse(c[4]), reserva, Modalidad.valueOf(c[5]));
        ponerMarcador(partido, c[6], c[7], c[8]);
        for (String login : c[9].split(","))
        {
            InscripcionDisciplina participante = inscripcionDisciplina(disciplina, login);
            partido.agregarParticipante(participante);
            reserva.agregarParticipante(participante.getJugador());
        }
        reserva.setPartido(partido);
    }

    private void leerEstadistica(String[] c)
    {
        Partido partido = partido(c[1]);
        PerfilJugador jugador = usuario(c[2]).getPerfilJugador();
        String tipo = c[3];
        EstadisticaPartido estadistica;
        if (tipo.equals("EstadisticaFutbol"))
        {
            estadistica = new EstadisticaFutbol(jugador, partido, entero(c[4]), entero(c[5]), entero(c[6]), entero(c[7]));
        }
        else if (tipo.equals("EstadisticaBaloncesto"))
        {
            estadistica = new EstadisticaBaloncesto(jugador, partido, entero(c[4]), entero(c[5]));
        }
        else if (tipo.equals("EstadisticaVoleibol"))
        {
            estadistica = new EstadisticaVoleibol(jugador, partido, entero(c[4]), entero(c[5]));
        }
        else if (tipo.equals("EstadisticaRaqueta"))
        {
            estadistica = new EstadisticaRaqueta(jugador, partido, entero(c[4]), entero(c[5]), entero(c[6]), entero(c[7]));
        }
        else
        {
            estadistica = new EstadisticaTenisMesa(jugador, partido, entero(c[4]), entero(c[5]));
        }
        partido.agregarEstadistica(estadistica);
    }

    private void leerProducto(String[] c)
    {
        Producto producto;
        double precio = Double.parseDouble(c[4]);
        if (c[1].equals("ArticuloDeportivo"))
        {
            producto = new ArticuloDeportivo(c[2], c[3], precio, c[5]);
        }
        else if (c[1].equals("Bebida"))
        {
            producto = new Bebida(c[2], c[3], precio, Boolean.parseBoolean(c[5]));
        }
        else
        {
            producto = new Snack(c[2], c[3], precio);
        }
        club.agregarProducto(producto);
    }

    private void leerVenta(String[] c)
    {
        Venta venta;
        LocalDateTime fecha = LocalDateTime.parse(c[3]);
        Usuario comprador = usuario(c[4]);
        if (c[1].equals("Cafeteria"))
        {
            VentaCafeteria cafeteria = new VentaCafeteria(c[2], fecha, comprador);
            cafeteria.setPorcentajePropina(Double.parseDouble(c[12]));
            venta = cafeteria;
        }
        else
        {
            venta = new VentaTienda(c[2], fecha, comprador);
        }
        venta.setSubtotal(Double.parseDouble(c[5]));
        venta.setTotalImpuestos(Double.parseDouble(c[6]));
        venta.setTotal(Double.parseDouble(c[7]));
        venta.setPuntosGenerados(entero(c[8]));
        venta.setPuntosRedimidos(entero(c[9]));
        if (!c[10].equals(VACIO))
        {
            venta.setCodigoDescuento(club.buscarCodigoDescuento(c[10]));
        }
        venta.setCerrada(Boolean.parseBoolean(c[11]));
        club.agregarVenta(venta);
    }

    private void ponerMarcador(Partido partido, String local, String visitante, String jugado)
    {
        partido.setMarcadorLocal(entero(local));
        partido.setMarcadorVisitante(entero(visitante));
        partido.setJugado(Boolean.parseBoolean(jugado));
    }

    private int entero(String texto)
    {
        return Integer.parseInt(texto);
    }

    // ------------------------------------------------------------------
    // Búsquedas: de un nombre, login o id del archivo al objeto ya cargado
    // ------------------------------------------------------------------

    private Usuario usuario(String login)
    {
        if (login.equals(VACIO))
        {
            return null;
        }
        Usuario usuario = club.buscarUsuario(login);
        if (usuario == null)
        {
            throw new IllegalArgumentException("No existe el usuario " + login);
        }
        return usuario;
    }

    private DeporteConjunto deporte(String nombre)
    {
        for (DeporteConjunto deporte : club.getDeportes())
        {
            if (deporte.getNombre().equals(nombre))
            {
                return deporte;
            }
        }
        throw new IllegalArgumentException("No existe el deporte " + nombre);
    }

    private Categoria categoria(String nombre)
    {
        for (Categoria categoria : club.getCategorias())
        {
            if (categoria.getNombre().equals(nombre))
            {
                return categoria;
            }
        }
        throw new IllegalArgumentException("No existe la categoria " + nombre);
    }

    private Equipo equipo(String nombre)
    {
        for (Equipo equipo : club.getEquipos())
        {
            if (equipo.getNombre().equals(nombre))
            {
                return equipo;
            }
        }
        throw new IllegalArgumentException("No existe el equipo " + nombre);
    }

    private InscripcionEquipo inscripcionEquipo(Equipo equipo, String login)
    {
        for (InscripcionEquipo inscripcion : equipo.getInscripciones())
        {
            if (inscripcion.getJugador().getUsuario().getLogin().equals(login))
            {
                return inscripcion;
            }
        }
        throw new IllegalArgumentException(login + " no esta en " + equipo.getNombre());
    }

    private DisciplinaIndividual disciplina(String nombre)
    {
        for (DisciplinaIndividual disciplina : club.getDisciplinas())
        {
            if (disciplina.getNombre().equals(nombre))
            {
                return disciplina;
            }
        }
        throw new IllegalArgumentException("No existe la disciplina " + nombre);
    }

    private InscripcionDisciplina inscripcionDisciplina(DisciplinaIndividual disciplina, String login)
    {
        for (InscripcionDisciplina inscripcion : disciplina.getInscripciones())
        {
            if (inscripcion.getJugador().getUsuario().getLogin().equals(login))
            {
                return inscripcion;
            }
        }
        throw new IllegalArgumentException(login + " no esta en " + disciplina.getNombre());
    }

    private Instalacion instalacion(String codigo)
    {
        for (Instalacion instalacion : club.getInstalaciones())
        {
            if (instalacion.getCodigo().equals(codigo))
            {
                return instalacion;
            }
        }
        throw new IllegalArgumentException("No existe la instalacion " + codigo);
    }

    private Reserva reserva(DisciplinaIndividual disciplina, String id)
    {
        for (Reserva reserva : disciplina.getReservas())
        {
            if (reserva.getId().equals(id))
            {
                return reserva;
            }
        }
        throw new IllegalArgumentException("No existe la reserva " + id);
    }

    private Competencia competencia(String nombre)
    {
        for (Competencia competencia : club.getCompetencias())
        {
            if (competencia.getNombre().equals(nombre))
            {
                return competencia;
            }
        }
        throw new IllegalArgumentException("No existe la competencia " + nombre);
    }

    /**
     * Los partidos internos están en el club, los de competencia en su competencia y los de práctica en su
     * reserva. Este método los junta en una sola lista.
     */
    private List<Partido> todosLosPartidos(Club club)
    {
        List<Partido> todos = new ArrayList<Partido>();
        todos.addAll(club.getPartidos());
        for (Competencia competencia : club.getCompetencias())
        {
            todos.addAll(competencia.getPartidos());
        }
        for (DisciplinaIndividual disciplina : club.getDisciplinas())
        {
            for (Reserva reserva : disciplina.getReservas())
            {
                if (reserva.getPartido() != null)
                {
                    todos.add(reserva.getPartido());
                }
            }
        }
        return todos;
    }

    private Partido partido(String id)
    {
        for (Partido partido : todosLosPartidos(club))
        {
            if (partido.getId().equals(id))
            {
                return partido;
            }
        }
        throw new IllegalArgumentException("No existe el partido " + id);
    }

    private SesionEntrenamiento sesion(String id)
    {
        for (SesionEntrenamiento sesion : club.getSesiones())
        {
            if (sesion.getId().equals(id))
            {
                return sesion;
            }
        }
        throw new IllegalArgumentException("No existe la sesion " + id);
    }

    private Producto producto(String codigo)
    {
        for (Producto producto : club.getProductos())
        {
            if (producto.getCodigo().equals(codigo))
            {
                return producto;
            }
        }
        throw new IllegalArgumentException("No existe el producto " + codigo);
    }

    private UbicacionInventario ubicacion(String nombre)
    {
        if (nombre.equals(VACIO))
        {
            return null;
        }
        for (UbicacionInventario ubicacion : club.getUbicaciones())
        {
            if (ubicacion.getNombre().equals(nombre))
            {
                return ubicacion;
            }
        }
        throw new IllegalArgumentException("No existe la ubicacion " + nombre);
    }

    private Venta venta(String id)
    {
        for (Venta venta : club.getVentas())
        {
            if (venta.getId().equals(id))
            {
                return venta;
            }
        }
        throw new IllegalArgumentException("No existe la venta " + id);
    }
}
