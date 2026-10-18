package uniandes.dpoo.proyecto1.tienda;

import java.util.ArrayList;
import java.util.List;

/**
 * Alimento con sus alérgenos declarados.
 */
public class Snack extends ProductoCafeteria
{
    private List<Alergeno> alergenos;

    public Snack(String codigo, String nombre, double precioBase)
    {
        super(codigo, nombre, precioBase);
        this.alergenos = new ArrayList<Alergeno>();
    }

    @Override
    public List<Alergeno> getAlergenos()
    {
        return alergenos;
    }

    public void agregarAlergeno(Alergeno alergeno)
    {
        alergenos.add(alergeno);
    }
}
