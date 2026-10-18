package uniandes.dpoo.proyecto1.individuales;

/**
 * Cancha de pádel: siempre dobles, 4 jugadores.
 */
public class CanchaPadel extends Instalacion
{
    private static final long serialVersionUID = 1L;

    public CanchaPadel(String codigo, String nombre, int capacidadMaxima, boolean techada)
    {
        super(codigo, nombre, capacidadMaxima, techada);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-08 - Reservar una instalación
     *
     * El pádel siempre es dobles, con 4 jugadores.
     *
     * Reglas: RN-24
     */
    @Override
    public boolean admite(Modalidad modalidad, int numeroJugadores)
    {
        return modalidad == Modalidad.DOBLES && numeroJugadores == 4;
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * En la cancha de pádel no se permiten bebidas calientes.
     *
     * Reglas: RN-32
     */
    @Override
    public boolean prohibeBebidaCaliente()
    {
        return true;
    }
}
