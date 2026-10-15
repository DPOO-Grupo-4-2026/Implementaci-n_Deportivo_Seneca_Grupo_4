package uniandes.dpoo.proyecto1.tienda;

/**
 * Bebida fria o caliente.
 */
public class Bebida extends ProductoCafeteria
{
    private static final long serialVersionUID = 1L;

    private boolean caliente;

    public Bebida(String codigo, String nombre, double precioBase, boolean caliente)
    {
        super(codigo, nombre, precioBase);
        this.caliente = caliente;
    }

    public boolean isCaliente()
    {
        return caliente;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * Responde si la bebida es caliente.
     *
     * Reglas: RN-32
     */
    @Override
    public boolean esBebidaCaliente()
    {
        return caliente;
    }
}
