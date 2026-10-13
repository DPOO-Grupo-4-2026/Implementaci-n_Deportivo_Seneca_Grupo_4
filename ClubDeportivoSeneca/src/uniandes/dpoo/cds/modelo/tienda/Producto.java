package uniandes.dpoo.cds.modelo.tienda;

import java.util.ArrayList;
import java.util.List;

/**
 * Articulo vendible.
 */
public abstract class Producto
{
    private String codigo;
    private String nombre;
    private double precioBase;

    public Producto(String codigo, String nombre, double precioBase)
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public double getPrecioBase()
    {
        return precioBase;
    }

    public void setPrecioBase(double precioBase)
    {
        this.precioBase = precioBase;
    }

    /** Por defecto un producto no es bebida caliente; Bebida lo redefine (D-20). */
    public boolean esBebidaCaliente()
    {
        return false;
    }

    /** Por defecto un producto no declara alergenos; Snack lo redefine con su lista (D-20). */
    public List<Alergeno> getAlergenos()
    {
        return new ArrayList<Alergeno>();
    }
}
