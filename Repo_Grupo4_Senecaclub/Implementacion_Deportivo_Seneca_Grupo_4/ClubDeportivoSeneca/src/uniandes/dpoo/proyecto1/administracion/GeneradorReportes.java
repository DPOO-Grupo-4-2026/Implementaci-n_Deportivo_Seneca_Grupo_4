package uniandes.dpoo.proyecto1.administracion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uniandes.dpoo.proyecto1.competencias.Competencia;
import uniandes.dpoo.proyecto1.competencias.PartidoCompetencia;
import uniandes.dpoo.proyecto1.conjunto.Equipo;
import uniandes.dpoo.proyecto1.conjunto.Partido;
import uniandes.dpoo.proyecto1.conjunto.PartidoInterno;
import uniandes.dpoo.proyecto1.entrenamiento.EstadoAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.RegistroAsistencia;
import uniandes.dpoo.proyecto1.entrenamiento.SesionEntrenamiento;
import uniandes.dpoo.proyecto1.individuales.AjusteRanking;
import uniandes.dpoo.proyecto1.individuales.InscripcionDisciplina;
import uniandes.dpoo.proyecto1.tienda.PagoMensualidad;
import uniandes.dpoo.proyecto1.tienda.Venta;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Consultas del administrador sobre el estado del club. Los reportes no son entidades: se calculan.
 */
public class GeneradorReportes
{
    private Club club;

    public GeneradorReportes(Club club)
    {
        this.club = club;
    }

    public Club getClub()
    {
        return club;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-A-10 - Reporte de ventas
     *
     * Cuánto se vendio en tienda, cafetería y mensualidades en un periodo.
     *
     * Reglas: RN-34
     */
    public Map<String, Double> ventasPorRubro(LocalDate desde, LocalDate hasta)
    {
        Map<String, Double> totales = new HashMap<String, Double>();
        totales.put("Tienda", 0.0);
        totales.put("Cafeteria", 0.0);
        totales.put("Mensualidades", 0.0);
        for (Venta venta : club.getVentas())
        {
            LocalDate dia = venta.getFecha().toLocalDate();
            if (venta.isCerrada() && !dia.isBefore(desde) && !dia.isAfter(hasta))
            {
                totales.put(venta.getRubro(), totales.get(venta.getRubro()) + venta.getTotal());
            }
        }
        for (PagoMensualidad pago : club.getPagos())
        {
            if (!pago.getFecha().isBefore(desde) && !pago.getFecha().isAfter(hasta))
            {
                totales.put("Mensualidades", totales.get("Mensualidades") + pago.getMonto());
            }
        }
        return totales;
    }

    /**
     * RF-E-09, RF-A-11 - Consultar asistencia y desempeño / Reportes deportivos
     *
     * Cuántas veces el jugador asistió, faltó o llegó tarde en un periodo.
     *
     * Reglas: RN-27
     */
    public Map<EstadoAsistencia, Integer> asistenciaDeJugador(PerfilJugador jugador, LocalDate desde, LocalDate hasta)
    {
        Map<EstadoAsistencia, Integer> conteo = new HashMap<EstadoAsistencia, Integer>();
        conteo.put(EstadoAsistencia.ASISTIO, 0);
        conteo.put(EstadoAsistencia.FALTO, 0);
        conteo.put(EstadoAsistencia.LLEGO_TARDE, 0);
        for (SesionEntrenamiento sesion : club.getSesiones())
        {
            if (!sesion.getFecha().isBefore(desde) && !sesion.getFecha().isAfter(hasta))
            {
                for (RegistroAsistencia registro : sesion.getRegistros())
                {
                    if (registro.getJugador() == jugador && registro.getEstado() != null)
                    {
                        conteo.put(registro.getEstado(), conteo.get(registro.getEstado()) + 1);
                    }
                }
            }
        }
        return conteo;
    }

    /**
     * RF-S-11 - Consultar mis convocatorias y asistencias
     *
     * Las sesiones a las que el jugador fue convocado, con su asistencia.
     *
     * Reglas: RN-27
     */
    public List<RegistroAsistencia> convocatoriasDeJugador(PerfilJugador jugador)
    {
        List<RegistroAsistencia> convocatorias = new ArrayList<RegistroAsistencia>();
        for (SesionEntrenamiento sesion : club.getSesiones())
        {
            for (RegistroAsistencia registro : sesion.getRegistros())
            {
                if (registro.getJugador() == jugador)
                {
                    convocatorias.add(registro);
                }
            }
        }
        return convocatorias;
    }

    /**
     * RF-A-07, RF-S-04 - Consultar el historial / Consultar el calendario de partidos
     *
     * Los partidos que ya jugó un equipo, internos y de competencia.
     *
     * Reglas: RN-21
     */
    public List<Partido> resultadosDeEquipo(Equipo equipo)
    {
        List<Partido> resultado = new ArrayList<Partido>();
        for (PartidoInterno partido : club.getPartidos())
        {
            if (partido.isJugado() && (partido.getLocal() == equipo || partido.getVisitante() == equipo))
            {
                resultado.add(partido);
            }
        }
        for (Competencia competencia : club.getCompetencias())
        {
            for (PartidoCompetencia partido : competencia.getPartidos())
            {
                if (partido.isJugado() && partido.getEquipo() == equipo && !resultado.contains(partido))
                {
                    resultado.add(partido);
                }
            }
        }
        return resultado;
    }

    /**
     * RF-S-10 - Consultar mi ranking
     *
     * Cómo ha cambiado el ranking del jugador, partido a partido.
     *
     * Reglas: RN-26
     */
    public List<AjusteRanking> evolucionDeRanking(InscripcionDisciplina inscripcion)
    {
        // Los ajustes se guardan en el orden en que se jugaron los partidos
        return new ArrayList<AjusteRanking>(inscripcion.getAjustes());
    }
}
