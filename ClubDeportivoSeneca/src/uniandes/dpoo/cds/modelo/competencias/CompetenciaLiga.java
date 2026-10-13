package uniandes.dpoo.cds.modelo.competencias;

import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.conjunto.Categoria;
import uniandes.dpoo.cds.modelo.conjunto.DeporteConjunto;

/**
 * Todos contra todos, con una sola tabla de posiciones (Torneo de los Cerros).
 */
public class CompetenciaLiga extends Competencia
{
    public CompetenciaLiga(String nombre, String entidadOrganizadora, String temporada, int nominaMinima, DeporteConjunto deporte, Categoria categoria)
    {
        super(nombre, entidadOrganizadora, temporada, nominaMinima, deporte, categoria);
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Actualiza la tabla General con el puntaje del deporte.
     *
     * Que debe verificar:
     * - Que el partido sea de esta competencia.
     * - Que el deporte admita empate si el marcador quedo igualado.
     *
     * Reglas: RN-18, RN-19, RN-21
     */
    @Override
    public void registrarResultado(PartidoCompetencia partido, int marcadorClub, int marcadorRival) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-18, RN-19, RN-21]: CompetenciaLiga.registrarResultado");
    }
}
