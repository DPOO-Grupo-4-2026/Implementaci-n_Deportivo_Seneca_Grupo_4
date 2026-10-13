package uniandes.dpoo.cds.modelo.competencias;

import uniandes.dpoo.cds.modelo.conjunto.Equipo;

/**
 * Clase intermedia entre Equipo y Competencia. De ella cuelga la nomina.
 */
public class InscripcionCompetencia
{
    private Equipo equipo;
    private Competencia competencia;
    private NominaCompetencia nomina;

    public InscripcionCompetencia(Equipo equipo, Competencia competencia, NominaCompetencia nomina)
    {
        this.equipo = equipo;
        this.competencia = competencia;
        this.nomina = nomina;
    }

    public Equipo getEquipo()
    {
        return equipo;
    }

    public Competencia getCompetencia()
    {
        return competencia;
    }

    public NominaCompetencia getNomina()
    {
        return nomina;
    }
}
