package uniandes.dpoo.proyecto1.tienda;

import java.time.LocalDateTime;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Venta de artículos deportivos. Paga IVA.
 */
public class VentaTienda extends Venta
{
    private static final long serialVersionUID = 1L;

    public static final double TASA_IVA = 0.19;

    public VentaTienda(String id, LocalDateTime fecha, Usuario comprador)
    {
        super(id, fecha, comprador);
    }

    @Override
    public String getRubro()
    {
        return "Tienda";
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-12 - Comprar en la tienda
     *
     * La tienda solo vende artículos deportivos.
     *
     * Reglas: RN-28
     */
    @Override
    public boolean admiteProducto(Producto producto)
    {
        return !producto.esDeCafeteria();
    }

    /**
     * RF-S-12 - Comprar en la tienda
     *
     * En la tienda se paga IVA del 19%.
     *
     * Reglas: RN-28
     */
    @Override
    public double calcularImpuestos(double base)
    {
        return base * TASA_IVA;
    }
}
