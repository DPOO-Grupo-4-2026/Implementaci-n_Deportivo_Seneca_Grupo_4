package uniandes.dpoo.proyecto1.individuales;

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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-08 - Reservar una instalación
     *
     * La cancha de tenis acepta sencillos con 2 o dobles con 4.
     *
     * Reglas: RN-24
     */
    @Override
    public boolean admite(Modalidad modalidad, int numeroJugadores)
    {
        if (modalidad == Modalidad.SENCILLOS)
        {
            return admiteSencillos && numeroJugadores == 2;
        }
        return numeroJugadores == 4;
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * En la cancha de tenis no hay restriccion de bebidas calientes.
     *
     * Reglas: RN-32
     */
    @Override
    public boolean prohibeBebidaCaliente()
    {
        return false;
    }
}
