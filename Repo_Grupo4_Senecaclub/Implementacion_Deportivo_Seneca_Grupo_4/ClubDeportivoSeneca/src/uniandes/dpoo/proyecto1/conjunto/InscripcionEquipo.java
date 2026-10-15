package uniandes.dpoo.proyecto1.conjunto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Clase intermedia entre PerfilJugador y Equipo: la posición y la camiseta son del par.
 */
public class InscripcionEquipo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** Partidos que dura una suspensión. El enunciado no da otro valor: es supuesto del grupo. */
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
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-E-07, RF-S-06 - Verificar el mínimo de habilitados / Consultar mis sanciones
     *
     * Un jugador puede jugar si no está dado de baja y no tiene sanciones pendientes en este equipo. No
     * guardamos un booleano: lo calculamos cada vez a partir de las sanciones.
     *
     * Reglas: RN-12, RN-14
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
     * RF-E-06 - Registrar tarjetas y suspensiones
     *
     * Después de un partido de fútbol, llegan las tarjetas del jugador. Una roja lo suspende de una vez.
     * Las amarillas se van sumando, y al llegar a dos se crea la sanción y el conteo vuelve a cero. Las
     * amarillas se cuentan en la inscripción del jugador a su equipo, sin separar por competencia.
     *
     * Reglas: RN-11, RN-13
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
