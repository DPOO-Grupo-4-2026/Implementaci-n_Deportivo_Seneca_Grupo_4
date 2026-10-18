package uniandes.dpoo.proyecto1.competencias;

import uniandes.dpoo.proyecto1.conjunto.Categoria;
import uniandes.dpoo.proyecto1.conjunto.DeporteConjunto;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;

/**
 * Todos contra todos, con una sola tabla de posiciones (Torneo de los Cerros).
 */
public class CompetenciaLiga extends Competencia
{
    private static final long serialVersionUID = 1L;

    public CompetenciaLiga(String nombre, String entidadOrganizadora, String temporada, int nominaMinima, DeporteConjunto deporte, Categoria categoria)
    {
        super(nombre, entidadOrganizadora, temporada, nominaMinima, deporte, categoria);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-05, RF-A-03 - Registrar el partido y sus estadísticas / Crear competencias e inscribir equipos
     *
     * En la liga todos juegan contra todos y hay una sola tabla. Cerramos el partido y sumamos los puntos
     * que da el deporte.
     *
     * Reglas: RN-18, RN-19, RN-21
     */
    @Override
    public void registrarResultado(PartidoCompetencia partido, int marcadorClub, int marcadorRival) throws ReglaDominioException
    {
        if (partido.getCompetencia() != this)
        {
            throw new AccionNoPermitidaException("RN-18", "El partido " + partido.getId() + " no es de " + getNombre());
        }
        if (marcadorClub == marcadorRival && !getDeporte().permiteEmpate())
        {
            throw new AccionNoPermitidaException("RN-18", getDeporte().getNombre() + " no admite empate");
        }
        partido.cerrar(marcadorClub, marcadorRival);
        obtenerFila("General", partido.getEquipo()).sumarResultado(marcadorClub, marcadorRival, getDeporte());
    }
}
