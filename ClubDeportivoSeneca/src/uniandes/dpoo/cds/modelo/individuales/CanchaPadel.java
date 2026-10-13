package uniandes.dpoo.cds.modelo.individuales;

/**
 * Cancha de padel: siempre dobles, 4 jugadores.
 */
public class CanchaPadel extends Instalacion
{
    public CanchaPadel(String codigo, String nombre, int capacidadMaxima, boolean techada)
    {
        super(codigo, nombre, capacidadMaxima, techada);
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Solo dobles con 4.
     *
     * Reglas: RN-24
     */
    @Override
    public boolean admite(Modalidad modalidad, int numeroJugadores)
    {
        throw new UnsupportedOperationException("Pendiente [RN-24]: CanchaPadel.admite");
    }

    /**
     * En cancha de padel nunca se permite.
     *
     * Reglas: RN-32
     */
    @Override
    public boolean prohibeBebidaCaliente()
    {
        throw new UnsupportedOperationException("Pendiente [RN-32]: CanchaPadel.prohibeBebidaCaliente");
    }
}
