package uniandes.dpoo.proyecto1.conjunto;

import java.io.Serializable;

/**
 * Categoria de edad: Sub-13, Sub-15, Sub-17 o Mayores.
 */
public class Categoria implements Serializable
{
    private static final long serialVersionUID = 1L;

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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-03 - Inscribirse en un equipo
     *
     * Cada categoría tiene un rango de edad (Sub-13, Sub-15...). Le preguntamos a la categoría si la edad
     * cabe.
     *
     * Reglas: RN-04
     */
    public boolean admiteEdad(int edad)
    {
        return edadMinima <= edad && edad <= edadMaxima;
    }
}
