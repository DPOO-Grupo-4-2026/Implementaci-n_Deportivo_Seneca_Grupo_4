package uniandes.dpoo.cds.modelo.tienda;

import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;

/**
 * Bodega, tienda o cafeteria. Conoce sus existencias.
 */
public class UbicacionInventario
{
    private String nombre;
    private TipoUbicacion tipo;
    private List<Existencia> existencias;

    public UbicacionInventario(String nombre, TipoUbicacion tipo)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.existencias = new ArrayList<Existencia>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public TipoUbicacion getTipo()
    {
        return tipo;
    }

    public List<Existencia> getExistencias()
    {
        return existencias;
    }

    public void agregarExistencia(Existencia existencia)
    {
        existencias.add(existencia);
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Devuelve la existencia de ese producto aqui, o null.
     *
     * Reglas: RN-35
     */
    public Existencia buscarExistencia(Producto producto)
    {
        throw new UnsupportedOperationException("Pendiente [RN-35]: UbicacionInventario.buscarExistencia");
    }

    /**
     * Suma unidades; crea la existencia si no habia.
     *
     * Reglas: RN-35
     */
    public void ingresar(Producto producto, int cantidad)
    {
        throw new UnsupportedOperationException("Pendiente [RN-35]: UbicacionInventario.ingresar");
    }

    /**
     * Resta unidades de la existencia.
     *
     * Que debe verificar:
     * - Que haya suficientes unidades.
     *
     * Reglas: RN-35
     */
    public void retirar(Producto producto, int cantidad) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-35]: UbicacionInventario.retirar");
    }
}
