package uniandes.dpoo.cds.modelo.tienda;

/**
 * Raquetas, balones, uniformes.
 */
public class ArticuloDeportivo extends Producto
{
    private String categoria;

    public ArticuloDeportivo(String codigo, String nombre, double precioBase, String categoria)
    {
        super(codigo, nombre, precioBase);
        this.categoria = categoria;
    }

    public String getCategoria()
    {
        return categoria;
    }
}
