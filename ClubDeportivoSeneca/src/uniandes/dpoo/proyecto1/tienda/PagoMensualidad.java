package uniandes.dpoo.proyecto1.tienda;

import java.time.LocalDate;
import uniandes.dpoo.proyecto1.usuarios.Socio;

/**
 * Cuota de un socio. Es uno de los rubros del reporte de ventas.
 */
public class PagoMensualidad
{
    private Socio socio;
    private String periodo;
    private double monto;
    private LocalDate fecha;

    public PagoMensualidad(Socio socio, String periodo, double monto, LocalDate fecha)
    {
        this.socio = socio;
        this.periodo = periodo;
        this.monto = monto;
        this.fecha = fecha;
    }

    public Socio getSocio()
    {
        return socio;
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
