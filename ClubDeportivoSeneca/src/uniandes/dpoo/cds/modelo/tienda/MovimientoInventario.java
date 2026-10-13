package uniandes.dpoo.cds.modelo.tienda;

import java.time.LocalDate;

/**
 * Traslado, reabastecimiento o salida por venta. Deja el rastro del inventario.
 */
public class MovimientoInventario
{
    private LocalDate fecha;
    private Producto producto;
    private int cantidad;
    private TipoMovimiento tipo;
    private UbicacionInventario origen;
    private UbicacionInventario destino;

    public MovimientoInventario(LocalDate fecha, Producto producto, int cantidad, TipoMovimiento tipo, UbicacionInventario origen, UbicacionInventario destino)
    {
        this.fecha = fecha;
        this.producto = producto;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.origen = origen;
        this.destino = destino;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public Producto getProducto()
    {
        return producto;
    }

    public int getCantidad()
    {
        return cantidad;
    }

    public TipoMovimiento getTipo()
    {
        return tipo;
    }

    public UbicacionInventario getOrigen()
    {
        return origen;
    }

    public UbicacionInventario getDestino()
    {
        return destino;
    }
}
