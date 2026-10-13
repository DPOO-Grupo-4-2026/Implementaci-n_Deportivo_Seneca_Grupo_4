package uniandes.dpoo.cds.modelo.conjunto;

import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Estadisticas individuales de futbol.
 */
public class EstadisticaFutbol extends EstadisticaPartido
{
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
    // Logica del dominio
    // ------------------------------------------------------------------

    /**
     * Registra en la inscripcion las amarillas y rojas de este partido.
     *
     * Reglas: RN-11, RN-13
     *
     * Por que asi: Solo el futbol tiene tarjetas: el resto de estadisticas hereda la version vacia y no hace falta preguntar el deporte.
     */
    @Override
    public void aplicarTarjetas(InscripcionEquipo inscripcion)
    {
        inscripcion.registrarTarjetas(tarjetasAmarillas, tarjetasRojas);
    }
}
