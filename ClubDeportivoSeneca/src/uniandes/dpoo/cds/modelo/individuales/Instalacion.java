package uniandes.dpoo.cds.modelo.individuales;

/**
 * Espacio reservable del club. Cada tipo fija su propia capacidad.
 */
public abstract class Instalacion
{
    private String codigo;
    private String nombre;
    private int capacidadMaxima;
    private boolean techada;

    public Instalacion(String codigo, String nombre, int capacidadMaxima, boolean techada)
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidadMaxima = capacidadMaxima;
        this.techada = techada;
    }

    public String getCodigo()
    {
        return codigo;
    }

    public String getNombre()
    {
        return nombre;
    }

    public int getCapacidadMaxima()
    {
        return capacidadMaxima;
    }

    public boolean isTechada()
    {
        return techada;
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Cada tipo de instalacion decide que modalidades y cuantos jugadores acepta.
     *
     * Reglas: RN-24
     */
    public abstract boolean admite(Modalidad modalidad, int numeroJugadores);

    /**
     * Dice si en esta instalacion no se puede tener una bebida caliente.
     *
     * Reglas: RN-32
     */
    public abstract boolean prohibeBebidaCaliente();
}
