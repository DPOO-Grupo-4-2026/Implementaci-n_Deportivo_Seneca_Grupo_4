package uniandes.dpoo.proyecto1.tienda;

import java.io.Serializable;

/**
 * Clase intermedia entre Producto y UbicacionInventario: cuánto hay de un producto en un lugar.
 */
public class Existencia implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Producto producto;
    private int cantidadDisponible;

    public Existencia(Producto producto, int cantidadDisponible)
    {
        this.producto = producto;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Producto getProducto()
    {
        return producto;
    }

    public int getCantidadDisponible()
    {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible)
    {
        this.cantidadDisponible = cantidadDisponible;
    }
}
