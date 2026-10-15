package uniandes.dpoo.proyecto1.tienda;

import java.io.Serializable;

/**
 * Maní, gluten, lácteos.
 */
public class Alergeno implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String nombre;

    public Alergeno(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }
}
