package uniandes.dpoo.cds.modelo.tienda;

import java.time.LocalDateTime;
import java.util.List;
import uniandes.dpoo.cds.modelo.usuarios.Usuario;

/**
 * Venta de bebidas y snacks. Paga impuesto al consumo y admite propina.
 */
public class VentaCafeteria extends Venta
{
    public static final double TASA_CONSUMO = 0.08;

    private double porcentajePropina;

    public VentaCafeteria(String id, LocalDateTime fecha, Usuario comprador)
    {
        super(id, fecha, comprador);
        this.porcentajePropina = 0.10;
    }

    public double getPorcentajePropina()
    {
        return porcentajePropina;
    }

    public void setPorcentajePropina(double porcentajePropina)
    {
        this.porcentajePropina = porcentajePropina;
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Solo productos de cafeteria.
     *
     * Reglas: RN-29
     */
    @Override
    public boolean admiteProducto(Producto producto)
    {
        throw new UnsupportedOperationException("Pendiente [RN-29]: VentaCafeteria.admiteProducto");
    }

    /**
     * Impuesto al consumo del 8% sobre la base.
     *
     * Reglas: RN-29
     */
    @Override
    public double calcularImpuestos(double base)
    {
        throw new UnsupportedOperationException("Pendiente [RN-29]: VentaCafeteria.calcularImpuestos");
    }

    /**
     * Propina segun el porcentaje, que el socio puede cambiar.
     *
     * Reglas: RN-29
     */
    public double calcularPropina(double base)
    {
        throw new UnsupportedOperationException("Pendiente [RN-29]: VentaCafeteria.calcularPropina");
    }

    /**
     * En la cafeteria el cargo adicional es la propina.
     *
     * Reglas: RN-29
     */
    @Override
    protected double calcularCargosAdicionales(double base)
    {
        throw new UnsupportedOperationException("Pendiente [RN-29]: VentaCafeteria.calcularCargosAdicionales");
    }

    /**
     * Alergenos de todos los productos de la venta, sin repetir, para informarlos antes de cerrar.
     *
     * Reglas: RN-33
     */
    public List<Alergeno> consultarAlergenos()
    {
        throw new UnsupportedOperationException("Pendiente [RN-33]: VentaCafeteria.consultarAlergenos");
    }
}
