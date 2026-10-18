package uniandes.dpoo.proyecto1.competencias;

import uniandes.dpoo.proyecto1.conjunto.Categoria;
import uniandes.dpoo.proyecto1.conjunto.DeporteConjunto;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;

/**
 * Fase de grupos y luego eliminación directa (ASCUN).
 */
public class CompetenciaPorFases extends Competencia
{
    public CompetenciaPorFases(String nombre, String entidadOrganizadora, String temporada, int nominaMinima, DeporteConjunto deporte, Categoria categoria)
    {
        super(nombre, entidadOrganizadora, temporada, nominaMinima, deporte, categoria);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-05, RF-A-03 - Registrar el partido y sus estadísticas / Crear competencias e inscribir equipos
     *
     * En la fase de grupos se suma en la tabla del grupo. En eliminación directa no hay tabla ni empate:
     * solo se cierra el partido.
     *
     * Reglas: RN-18, RN-20, RN-21
     */
    @Override
    public void registrarResultado(PartidoCompetencia partido, int marcadorClub, int marcadorRival) throws ReglaDominioException
    {
        if (partido.getCompetencia() != this)
        {
            throw new AccionNoPermitidaException("RN-20", "El partido " + partido.getId() + " no es de " + getNombre());
        }
        boolean esDeGrupos = partido.getFase().startsWith("Grupo");
        if (marcadorClub == marcadorRival)
        {
            if (!esDeGrupos)
            {
                throw new AccionNoPermitidaException("RN-20", "En eliminacion directa no puede haber empate");
            }
            if (!getDeporte().permiteEmpate())
            {
                throw new AccionNoPermitidaException("RN-18", getDeporte().getNombre() + " no admite empate");
            }
        }
        partido.cerrar(marcadorClub, marcadorRival);
        if (esDeGrupos)
        {
            obtenerFila(partido.getFase(), partido.getEquipo()).sumarResultado(marcadorClub, marcadorRival, getDeporte());
        }
    }
}
