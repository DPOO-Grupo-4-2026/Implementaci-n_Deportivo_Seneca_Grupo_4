package uniandes.dpoo.cds.modelo.administracion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uniandes.dpoo.cds.modelo.competencias.Competencia;
import uniandes.dpoo.cds.modelo.competencias.PartidoCompetencia;
import uniandes.dpoo.cds.modelo.conjunto.Equipo;
import uniandes.dpoo.cds.modelo.conjunto.Partido;
import uniandes.dpoo.cds.modelo.conjunto.PartidoInterno;
import uniandes.dpoo.cds.modelo.entrenamiento.EstadoAsistencia;
import uniandes.dpoo.cds.modelo.individuales.AjusteRanking;
import uniandes.dpoo.cds.modelo.individuales.InscripcionDisciplina;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Total de tienda, cafeteria y mensualidades en el periodo.
     *
     * Reglas: RN-34
     */
    public Map<String, Double> ventasPorRubro(LocalDate desde, LocalDate hasta)
    {
        throw new UnsupportedOperationException("Pendiente [RN-34]: GeneradorReportes.ventasPorRubro");
    }

    /**
     * Cuantas veces asistio, falto o llego tarde el jugador en el periodo.
     *
     * Reglas: RN-27
     */
    public Map<EstadoAsistencia, Integer> asistenciaDeJugador(PerfilJugador jugador, LocalDate desde, LocalDate hasta)
    {
        throw new UnsupportedOperationException("Pendiente [RN-27]: GeneradorReportes.asistenciaDeJugador");
    }

    /**
     * Partidos ya jugados por el equipo, internos y de competencia.
     *
     * Reglas: RN-21
     *
     * Por que asi: Es una consulta, no un comportamiento del modelo: aqui instanceof es aceptable.
     */
    public List<Partido> resultadosDeEquipo(Equipo equipo)
    {
        List<Partido> resultado = new ArrayList<Partido>();
        for (Partido partido : club.getPartidos())
        {
            if (partido.isJugado() && partido instanceof PartidoInterno)
            {
                PartidoInterno interno = (PartidoInterno) partido;
                if (interno.getLocal() == equipo || interno.getVisitante() == equipo)
                {
                    resultado.add(partido);
                }
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
     * Historico de ajustes de ranking, en orden de fecha.
     *
     * Reglas: RN-26
     */
    public List<AjusteRanking> evolucionDeRanking(InscripcionDisciplina inscripcion)
    {
        throw new UnsupportedOperationException("Pendiente [RN-26]: GeneradorReportes.evolucionDeRanking");
    }
}
