package uniandes.dpoo.cds.modelo.conjunto;

import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Clase intermedia entre PerfilJugador y Equipo: la posicion y la camiseta son del par.
 */
public class InscripcionEquipo
{
    /** Partidos que dura una suspension. El enunciado no da otro valor: es supuesto del grupo. */
    public static final int PARTIDOS_POR_SANCION = 1;

    private PerfilJugador jugador;
    private Equipo equipo;
    private String posicion;
    private int numeroCamiseta;
    private int amarillasAcumuladas;
    private List<Sancion> sanciones;

    public InscripcionEquipo(PerfilJugador jugador, Equipo equipo, String posicion, int numeroCamiseta)
    {
        this.jugador = jugador;
        this.equipo = equipo;
        this.posicion = posicion;
        this.numeroCamiseta = numeroCamiseta;
        this.amarillasAcumuladas = 0;
        this.sanciones = new ArrayList<Sancion>();
    }

    public PerfilJugador getJugador()
    {
        return jugador;
    }

    public Equipo getEquipo()
    {
        return equipo;
    }

    public String getPosicion()
    {
        return posicion;
    }

    public int getNumeroCamiseta()
    {
        return numeroCamiseta;
    }

    public int getAmarillasAcumuladas()
    {
        return amarillasAcumuladas;
    }

    public List<Sancion> getSanciones()
    {
        return sanciones;
    }

    public void agregarSancion(Sancion sancion)
    {
        sanciones.add(sancion);
    }

    // ------------------------------------------------------------------
    // Logica del dominio
    // ------------------------------------------------------------------

    /**
     * Dice si el jugador no tiene ninguna sancion con partidos pendientes.
     *
     * Reglas: RN-12, RN-14
     *
     * Por que asi: La sancion es una entidad con motivo y duracion; un booleano no guardaria por que ni por cuanto. Un jugador dado de baja tampoco cuenta como habilitado.
     */
    public boolean estaHabilitado()
    {
        if (!jugador.isActivo())
        {
            return false;
        }
        for (Sancion sancion : sanciones)
        {
            if (!sancion.isCumplida())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Acumula las tarjetas de un partido y, si corresponde, crea la sancion. Devuelve la sancion o null.
     *
     * Verifica:
     * - Una roja suspende de inmediato.
     * - Dos amarillas acumuladas suspenden y reinician el contador.
     *
     * Reglas: RN-11, RN-13
     *
     * Por que asi: La sancion cuelga de la inscripcion: aplica a cualquier partido de ese equipo (A-7).
     */
    public Sancion registrarTarjetas(int amarillas, int rojas)
    {
        Sancion sancion = null;
        if (rojas > 0)
        {
            sancion = new Sancion(MotivoSancion.TARJETA_ROJA, PARTIDOS_POR_SANCION);
        }
        else
        {
            amarillasAcumuladas += amarillas;
            if (amarillasAcumuladas >= 2)
            {
                amarillasAcumuladas = 0;
                sancion = new Sancion(MotivoSancion.DOS_AMARILLAS, PARTIDOS_POR_SANCION);
            }
        }
        if (sancion != null)
        {
            agregarSancion(sancion);
        }
        return sancion;
    }
}
