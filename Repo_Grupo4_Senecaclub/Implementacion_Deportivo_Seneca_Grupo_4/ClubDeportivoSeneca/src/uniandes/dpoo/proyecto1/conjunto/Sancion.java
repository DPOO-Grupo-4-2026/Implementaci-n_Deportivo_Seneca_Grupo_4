package uniandes.dpoo.proyecto1.conjunto;

import java.io.Serializable;

/**
 * Suspension de un jugador en un equipo. Entidad y no booleano: tiene motivo y duracion.
 */
public class Sancion implements Serializable
{
    private static final long serialVersionUID = 1L;

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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-06 - Registrar tarjetas y suspensiones
     *
     * Resta un partido pendiente. Cuando llega a cero, la sanción queda cumplida y el jugador vuelve a
     * estar habilitado.
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
