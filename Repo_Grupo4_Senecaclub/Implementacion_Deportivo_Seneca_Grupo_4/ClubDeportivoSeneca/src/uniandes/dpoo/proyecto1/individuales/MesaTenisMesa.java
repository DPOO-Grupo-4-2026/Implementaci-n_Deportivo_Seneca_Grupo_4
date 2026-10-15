package uniandes.dpoo.proyecto1.individuales;

/**
 * Mesa de tenis de mesa: 2 o 4 jugadores.
 */
public class MesaTenisMesa extends Instalacion
{
    private static final long serialVersionUID = 1L;

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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-08 - Reservar una instalación
     *
     * La mesa acepta sencillos con 2, o dobles con 4 si la mesa lo permite.
     *
     * Reglas: RN-24
     */
    @Override
    public boolean admite(Modalidad modalidad, int numeroJugadores)
    {
        if (modalidad == Modalidad.SENCILLOS)
        {
            return numeroJugadores == 2;
        }
        return admiteDobles && numeroJugadores == 4;
    }

    /**
     * RF-S-13 - Comprar en la cafetería
     *
     * En la mesa de tenis de mesa no se permiten si la mesa es techada.
     *
     * Reglas: RN-32
     */
    @Override
    public boolean prohibeBebidaCaliente()
    {
        return isTechada();
    }
}
