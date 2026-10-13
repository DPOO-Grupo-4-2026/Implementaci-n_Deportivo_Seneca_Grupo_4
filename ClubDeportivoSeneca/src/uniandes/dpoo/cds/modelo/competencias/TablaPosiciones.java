package uniandes.dpoo.cds.modelo.competencias;

import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.modelo.conjunto.Equipo;

/**
 * Clasificacion de una liga o de un grupo. Solo tiene filas de equipos del club (A-6).
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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Devuelve la fila de ese equipo, o null.
     *
     * Reglas: RN-21
     */
    public FilaTabla buscarFila(Equipo equipo)
    {
        throw new UnsupportedOperationException("Pendiente [RN-21]: TablaPosiciones.buscarFila");
    }
}
