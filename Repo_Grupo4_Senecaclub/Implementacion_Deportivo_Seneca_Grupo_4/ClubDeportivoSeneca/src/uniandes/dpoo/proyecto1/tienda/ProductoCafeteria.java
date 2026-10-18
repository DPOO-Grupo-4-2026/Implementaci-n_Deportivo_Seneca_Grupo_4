package uniandes.dpoo.proyecto1.tienda;

/**
 * Lo que vende la cafetería: bebidas y snacks.
 */
public abstract class ProductoCafeteria extends Producto
{
    private static final long serialVersionUID = 1L;

    public ProductoCafeteria(String codigo, String nombre, double precioBase)
    {
        super(codigo, nombre, precioBase);
    }

    @Override
    public boolean esDeCafeteria()
    {
        return true;
    }
}
