package uniandes.dpoo.cds.modelo.tienda;

import java.time.LocalDate;

/**
 * Cuota de un socio. Es uno de los rubros del reporte de ventas.
 */
public class PagoMensualidad
{
    private String periodo;
    private double monto;
    private LocalDate fecha;

    public PagoMensualidad(String periodo, double monto, LocalDate fecha)
    {
        this.periodo = periodo;
        this.monto = monto;
        this.fecha = fecha;
    }

    public String getPeriodo()
    {
        return periodo;
    }

    public double getMonto()
    {
        return monto;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }
}
