package uniandes.dpoo.proyecto1.competencias;

import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.conjunto.Equipo;

/**
 * Clasificación de una liga o de un grupo. Solo tiene filas de equipos del club, porque los rivales no
 * son clases del modelo.
 */
public class TablaPosiciones
{
    private String nombre;
    private List<FilaTabla> filas;

    public TablaPosiciones(String nombre)
    {
        this.nombre = nombre;
        this.filas = new ArrayList<FilaTabla>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public List<FilaTabla> getFilas()
    {
        return filas;
    }

    public void agregarFila(FilaTabla fila)
    {
        filas.add(fila);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-A-03 - Crear competencias e inscribir equipos
     *
     * Busca la fila de un equipo en la tabla.
     *
     * Reglas: RN-21
     */
    public FilaTabla buscarFila(Equipo equipo)
    {
        for (FilaTabla fila : filas)
        {
            if (fila.getEquipo() == equipo)
            {
                return fila;
            }
        }
        return null;
    }
}
