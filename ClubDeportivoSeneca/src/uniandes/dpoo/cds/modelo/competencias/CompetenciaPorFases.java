package uniandes.dpoo.cds.modelo.competencias;

import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.conjunto.Categoria;
import uniandes.dpoo.cds.modelo.conjunto.DeporteConjunto;

/**
 * Fase de grupos y luego eliminacion directa (ASCUN).
 */
public class CompetenciaPorFases extends Competencia
{
    public CompetenciaPorFases(String nombre, String entidadOrganizadora, String temporada, int nominaMinima, DeporteConjunto deporte, Categoria categoria)
    {
        super(nombre, entidadOrganizadora, temporada, nominaMinima, deporte, categoria);
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Si la fase empieza por Grupo, actualiza la tabla de ese grupo; en eliminacion solo cierra el partido.
     *
     * Que debe verificar:
     * - Que el partido sea de esta competencia.
     * - Que en eliminacion directa no haya empate.
     *
     * Reglas: RN-18, RN-20, RN-21
     */
    @Override
    public void registrarResultado(PartidoCompetencia partido, int marcadorClub, int marcadorRival) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-18, RN-20, RN-21]: CompetenciaPorFases.registrarResultado");
    }
}
