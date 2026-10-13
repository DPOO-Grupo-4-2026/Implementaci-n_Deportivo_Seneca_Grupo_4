package uniandes.dpoo.cds.modelo.tienda;

import uniandes.dpoo.cds.modelo.usuarios.Empleado;
import uniandes.dpoo.cds.modelo.usuarios.Usuario;

/**
 * Beneficio de un empleado: 15% para el y 8% para el socio con quien lo comparta.
 */
public class CodigoDescuento
{
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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * 15% si compra el dueno del codigo; 8% si compra otro.
     *
     * Reglas: RN-30
     */
    public double porcentajePara(Usuario comprador)
    {
        throw new UnsupportedOperationException("Pendiente [RN-30]: CodigoDescuento.porcentajePara");
    }
}
