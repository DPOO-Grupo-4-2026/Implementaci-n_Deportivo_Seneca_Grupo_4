package uniandes.dpoo.cds.modelo.tienda;

import java.time.LocalDateTime;
import uniandes.dpoo.cds.modelo.usuarios.Usuario;

/**
 * Venta de articulos deportivos. Paga IVA.
 */
public class VentaTienda extends Venta
{
    public static final double TASA_IVA = 0.19;

    public VentaTienda(String id, LocalDateTime fecha, Usuario comprador)
    {
        super(id, fecha, comprador);
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Solo articulos deportivos.
     *
     * Reglas: RN-28
     */
    @Override
    public boolean admiteProducto(Producto producto)
    {
        throw new UnsupportedOperationException("Pendiente [RN-28]: VentaTienda.admiteProducto");
    }

    /**
     * IVA del 19% sobre la base.
     *
     * Reglas: RN-28
     */
    @Override
    public double calcularImpuestos(double base)
    {
        throw new UnsupportedOperationException("Pendiente [RN-28]: VentaTienda.calcularImpuestos");
    }
}
