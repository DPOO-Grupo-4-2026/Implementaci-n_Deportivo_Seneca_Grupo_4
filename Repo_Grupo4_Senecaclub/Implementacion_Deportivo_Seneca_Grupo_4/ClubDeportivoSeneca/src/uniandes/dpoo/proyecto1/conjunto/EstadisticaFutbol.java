package uniandes.dpoo.proyecto1.conjunto;

import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Estadísticas individuales de fútbol.
 */
public class EstadisticaFutbol extends EstadisticaPartido
{
    private static final long serialVersionUID = 1L;

    private int goles;
    private int asistencias;
    private int tarjetasAmarillas;
    private int tarjetasRojas;

    public EstadisticaFutbol(PerfilJugador jugador, Partido partido, int goles, int asistencias, int tarjetasAmarillas, int tarjetasRojas)
    {
        super(jugador, partido);
        this.goles = goles;
        this.asistencias = asistencias;
        this.tarjetasAmarillas = tarjetasAmarillas;
        this.tarjetasRojas = tarjetasRojas;
    }

    public int getGoles()
    {
        return goles;
    }

    public int getAsistencias()
    {
        return asistencias;
    }

    public int getTarjetasAmarillas()
    {
        return tarjetasAmarillas;
    }

    public int getTarjetasRojas()
    {
        return tarjetasRojas;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-06 - Registrar tarjetas y suspensiones
     *
     * Solo el fútbol tiene tarjetas, así que solo esta estadística le pasa las amarillas y rojas a la
     * inscripción del jugador.
     *
     * Reglas: RN-11, RN-13
     */
    @Override
    public void aplicarTarjetas(InscripcionEquipo inscripcion)
    {
        inscripcion.registrarTarjetas(tarjetasAmarillas, tarjetasRojas);
    }
}
