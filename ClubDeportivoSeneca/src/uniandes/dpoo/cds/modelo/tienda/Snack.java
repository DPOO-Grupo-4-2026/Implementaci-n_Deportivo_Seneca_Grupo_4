package uniandes.dpoo.cds.modelo.tienda;

import java.util.ArrayList;
import java.util.List;

/**
 * Alimento con sus alergenos declarados.
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
