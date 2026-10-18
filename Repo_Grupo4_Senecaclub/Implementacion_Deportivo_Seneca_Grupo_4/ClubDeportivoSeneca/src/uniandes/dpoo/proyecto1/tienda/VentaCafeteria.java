package uniandes.dpoo.proyecto1.tienda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Venta de bebidas y snacks. Paga impuesto al consumo y admite propina.
 */
public class VentaCafeteria extends Venta
{
    private static final long serialVersionUID = 1L;

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

    @Override
    public String getRubro()
    {
        return "Cafeteria";
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * La cafetería solo vende bebidas y snacks.
     *
     * Reglas: RN-29
     */
    @Override
    public boolean admiteProducto(Producto producto)
    {
        return producto.esDeCafeteria();
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * En la cafetería se paga impuesto al consumo del 8%.
     *
     * Reglas: RN-29
     */
    @Override
    public double calcularImpuestos(double base)
    {
        return base * TASA_CONSUMO;
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * La propina sugerida es 10%, y el socio la puede cambiar.
     *
     * Reglas: RN-29
     */
    public double calcularPropina(double base)
    {
        return base * porcentajePropina;
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * En la cafetería se suma la propina al total.
     *
     * Reglas: RN-29
     */
    @Override
    protected double calcularCargosAdicionales(double base)
    {
        return calcularPropina(base);
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * Antes de pagar, el socio ve los alérgenos de lo que va a comprar.
     *
     * Reglas: RN-33
     */
    public List<Alergeno> consultarAlergenos()
    {
        List<Alergeno> resultado = new ArrayList<Alergeno>();
        for (LineaVenta linea : getLineas())
        {
            for (Alergeno alergeno : linea.getProducto().getAlergenos())
            {
                boolean repetido = false;
                for (Alergeno yaEsta : resultado)
                {
                    if (yaEsta.getNombre().equals(alergeno.getNombre()))
                    {
                        repetido = true;
                    }
                }
                if (!repetido)
                {
                    resultado.add(alergeno);
                }
            }
        }
        return resultado;
    }
}
