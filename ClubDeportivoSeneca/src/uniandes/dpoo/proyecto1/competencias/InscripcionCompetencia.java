package uniandes.dpoo.proyecto1.competencias;

import uniandes.dpoo.proyecto1.conjunto.Equipo;

/**
 * Clase intermedia entre Equipo y Competencia. De ella cuelga la nómina.
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
