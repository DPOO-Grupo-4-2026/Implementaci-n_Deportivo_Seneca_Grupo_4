package uniandes.dpoo.proyecto1.tienda;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Acumulación o redención de puntos de fidelidad.
 */
public class MovimientoPuntos implements Serializable
{
    private static final long serialVersionUID = 1L;

    private LocalDate fecha;
    private TipoPuntos tipo;
    private int cantidad;
    private Venta venta;

    public MovimientoPuntos(LocalDate fecha, TipoPuntos tipo, int cantidad, Venta venta)
    {
        this.fecha = fecha;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.venta = venta;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public TipoPuntos getTipo()
    {
        return tipo;
    }

    public int getCantidad()
    {
        return cantidad;
    }

    public Venta getVenta()
    {
        return venta;
    }
}
