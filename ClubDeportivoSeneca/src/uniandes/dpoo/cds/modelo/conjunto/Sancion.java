package uniandes.dpoo.cds.modelo.conjunto;

/**
 * Suspension de un jugador en un equipo. Entidad y no booleano: tiene motivo y duracion.
 */
public class Sancion
{
    private MotivoSancion motivo;
    private int partidosPendientes;
    private boolean cumplida;

    public Sancion(MotivoSancion motivo, int partidosPendientes)
    {
        this.motivo = motivo;
        this.partidosPendientes = partidosPendientes;
        this.cumplida = false;
    }

    public MotivoSancion getMotivo()
    {
        return motivo;
    }

    public int getPartidosPendientes()
    {
        return partidosPendientes;
    }

    public boolean isCumplida()
    {
        return cumplida;
    }

    // ------------------------------------------------------------------
    // Logica del dominio
    // ------------------------------------------------------------------

    /**
     * Resta un partido pendiente y marca la sancion como cumplida al llegar a cero.
     *
     * Reglas: RN-11
     */
    public void descontarPartido()
    {
        if (cumplida)
        {
            return;
        }
        partidosPendientes--;
        if (partidosPendientes <= 0)
        {
            partidosPendientes = 0;
            cumplida = true;
        }
    }
}
