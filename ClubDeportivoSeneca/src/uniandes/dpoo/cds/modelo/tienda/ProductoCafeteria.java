package uniandes.dpoo.cds.modelo.tienda;

/**
 * Lo que vende la cafeteria: bebidas y snacks.
 */
public abstract class ProductoCafeteria extends Producto
{
    public ProductoCafeteria(String codigo, String nombre, double precioBase)
    {
        super(codigo, nombre, precioBase);
    }
}
