package uniandes.dpoo.cds.modelo.conjunto;

/**
 * Categoria de edad: Sub-13, Sub-15, Sub-17 o Mayores.
 */
public class Categoria
{
    private String nombre;
    private int edadMinima;
    private int edadMaxima;

    public Categoria(String nombre, int edadMinima, int edadMaxima)
    {
        this.nombre = nombre;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }

    public String getNombre()
    {
        return nombre;
    }

    public int getEdadMinima()
    {
        return edadMinima;
    }

    public int getEdadMaxima()
    {
        return edadMaxima;
    }

    // ------------------------------------------------------------------
    // Logica del dominio
    // ------------------------------------------------------------------

    /**
     * Dice si la edad cae dentro del rango de la categoria.
     *
     * Reglas: RN-04
     *
     * Por que asi: Experto en informacion: la categoria conoce su rango.
     */
    public boolean admiteEdad(int edad)
    {
        return edadMinima <= edad && edad <= edadMaxima;
    }
}
