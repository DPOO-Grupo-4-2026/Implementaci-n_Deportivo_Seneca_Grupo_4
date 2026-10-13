package uniandes.dpoo.cds.modelo.individuales;

/**
 * Mesa de tenis de mesa: 2 o 4 jugadores.
 */
public class MesaTenisMesa extends Instalacion
{
    private boolean admiteDobles;

    public MesaTenisMesa(String codigo, String nombre, int capacidadMaxima, boolean techada, boolean admiteDobles)
    {
        super(codigo, nombre, capacidadMaxima, techada);
        this.admiteDobles = admiteDobles;
    }

    public boolean admiteDobles()
    {
        return admiteDobles;
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Sencillos con 2, o dobles con 4 si la mesa lo admite.
     *
     * Reglas: RN-24
     */
    @Override
    public boolean admite(Modalidad modalidad, int numeroJugadores)
    {
        throw new UnsupportedOperationException("Pendiente [RN-24]: MesaTenisMesa.admite");
    }

    /**
     * Solo si la mesa es techada.
     *
     * Reglas: RN-32
     */
    @Override
    public boolean prohibeBebidaCaliente()
    {
        throw new UnsupportedOperationException("Pendiente [RN-32]: MesaTenisMesa.prohibeBebidaCaliente");
    }
}
