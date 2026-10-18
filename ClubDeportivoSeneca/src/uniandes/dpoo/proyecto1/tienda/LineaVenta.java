package uniandes.dpoo.proyecto1.tienda;

/**
 * Renglón de una venta: que producto, cuántos y a que precio.
 */
public class LineaVenta
{
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public LineaVenta(Producto producto, int cantidad, double precioUnitario)
    {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto()
    {
        return producto;
    }

    public int getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(int cantidad)
    {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario()
    {
        return precioUnitario;
    }

    /** Atributo derivado: no se guarda, se calcula. */
    public double getSubtotal()
    {
        return cantidad * precioUnitario;
    }
}
