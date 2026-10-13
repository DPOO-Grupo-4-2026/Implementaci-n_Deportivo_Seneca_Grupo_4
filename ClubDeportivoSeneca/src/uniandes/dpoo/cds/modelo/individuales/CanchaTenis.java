package uniandes.dpoo.cds.modelo.individuales;

/**
 * Cancha de tenis: 2 jugadores en sencillos o 4 en dobles.
 */
public class CanchaTenis extends Instalacion
{
    private boolean admiteSencillos;

    public CanchaTenis(String codigo, String nombre, int capacidadMaxima, boolean techada, boolean admiteSencillos)
    {
        super(codigo, nombre, capacidadMaxima, techada);
        this.admiteSencillos = admiteSencillos;
    }

    public boolean admiteSencillos()
    {
        return admiteSencillos;
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Sencillos con 2 si los admite, o dobles con 4.
     *
     * Reglas: RN-24
     */
    @Override
    public boolean admite(Modalidad modalidad, int numeroJugadores)
    {
        throw new UnsupportedOperationException("Pendiente [RN-24]: CanchaTenis.admite");
    }

    /**
     * El enunciado no restringe la cancha de tenis.
     *
     * Reglas: RN-32
     */
    @Override
    public boolean prohibeBebidaCaliente()
    {
        throw new UnsupportedOperationException("Pendiente [RN-32]: CanchaTenis.prohibeBebidaCaliente");
    }
}
