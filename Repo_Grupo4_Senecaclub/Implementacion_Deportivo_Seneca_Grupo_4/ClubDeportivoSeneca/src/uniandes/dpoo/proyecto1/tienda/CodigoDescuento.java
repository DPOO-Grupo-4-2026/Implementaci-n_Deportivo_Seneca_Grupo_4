package uniandes.dpoo.proyecto1.tienda;

import java.io.Serializable;
import uniandes.dpoo.proyecto1.usuarios.Empleado;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Beneficio de un empleado: 15% para el y 8% para el socio con quién lo comparta.
 */
public class CodigoDescuento implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String codigo;
    private Empleado empleado;
    private double porcentajePropio;
    private double porcentajeCompartido;
    private boolean activo;

    public CodigoDescuento(String codigo, Empleado empleado)
    {
        this.codigo = codigo;
        this.empleado = empleado;
        this.porcentajePropio = 0.15;
        this.porcentajeCompartido = 0.08;
        this.activo = true;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public Empleado getEmpleado()
    {
        return empleado;
    }

    public double getPorcentajePropio()
    {
        return porcentajePropio;
    }

    public double getPorcentajeCompartido()
    {
        return porcentajeCompartido;
    }

    public boolean isActivo()
    {
        return activo;
    }

    public void setActivo(boolean activo)
    {
        this.activo = activo;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-12 - Comprar con código de descuento
     *
     * Si compra el mismo empleado tiene 15%; si comparte el código con un socio, el socio tiene 8%.
     *
     * Reglas: RN-30
     */
    public double porcentajePara(Usuario comprador)
    {
        if (comprador == empleado)
        {
            return porcentajePropio;
        }
        return porcentajeCompartido;
    }
}
