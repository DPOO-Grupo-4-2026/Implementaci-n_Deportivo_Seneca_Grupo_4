package uniandes.dpoo.proyecto1.conjunto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.CupoExcedidoException;
import uniandes.dpoo.proyecto1.excepciones.JugadorNoHabilitadoException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Conjunto de jugadores de un único deporte y una única categoría.
 */
public class Equipo implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int cupoMaximo;
    private DeporteConjunto deporte;
    private Categoria categoria;
    private boolean activo;
    private List<InscripcionEquipo> inscripciones;

    public Equipo(String nombre, int cupoMaximo, DeporteConjunto deporte, Categoria categoria)
    {
        this.nombre = nombre;
        this.cupoMaximo = cupoMaximo;
        this.deporte = deporte;
        this.categoria = categoria;
        this.activo = true;
        this.inscripciones = new ArrayList<InscripcionEquipo>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public int getCupoMaximo()
    {
        return cupoMaximo;
    }

    public DeporteConjunto getDeporte()
    {
        return deporte;
    }

    public Categoria getCategoria()
    {
        return categoria;
    }

    public boolean isActivo()
    {
        return activo;
    }

    public void setActivo(boolean activo)
    {
        this.activo = activo;
    }

    public List<InscripcionEquipo> getInscripciones()
    {
        return inscripciones;
    }

    public void agregarInscripcion(InscripcionEquipo inscripcion)
    {
        inscripciones.add(inscripcion);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-03 - Inscribirse en un equipo
     *
     * Un socio quiere entrar a este equipo. Primero revisamos todo lo que podría impedirlo: que el equipo
     * y el jugador estén activos, que quede cupo, que la edad corresponda a la categoría, que no tenga ya
     * un equipo de este deporte que la camiseta esté libre y que tenga una posición. Si algo falla,
     * lanzamos la excepción con la regla y no se cambia nada. Si todo está bien, creamos la inscripción y
     * la guardamos en el equipo y en el perfil del jugador, para que los dos sepan de la relación.
     *
     * Reglas: RN-03, RN-04, RN-05, RN-06, RN-07, RN-35
     */
    public InscripcionEquipo inscribirJugador(PerfilJugador jugador, String posicion, int numeroCamiseta, LocalDate hoy) throws ReglaDominioException
    {
        if (!activo)
        {
            throw new AccionNoPermitidaException("RN-35", "El equipo " + nombre + " esta dado de baja");
        }
        if (!jugador.isActivo())
        {
            throw new JugadorNoHabilitadoException("RN-35", "El jugador " + jugador.getUsuario().getNombre() + " esta dado de baja");
        }
        if (inscripciones.size() >= cupoMaximo)
        {
            throw new CupoExcedidoException("RN-03", "El equipo " + nombre + " ya tiene " + cupoMaximo + " inscritos");
        }
        int edad = jugador.getUsuario().calcularEdad(hoy);
        if (!categoria.admiteEdad(edad))
        {
            throw new JugadorNoHabilitadoException("RN-04", "Edad " + edad + " fuera del rango de " + categoria.getNombre());
        }
        if (jugador.buscarInscripcionEnDeporte(deporte) != null)
        {
            throw new JugadorNoHabilitadoException("RN-05", "Ya esta inscrito en un equipo de " + deporte.getNombre());
        }
        if (tieneCamiseta(numeroCamiseta))
        {
            throw new AccionNoPermitidaException("RN-06", "La camiseta " + numeroCamiseta + " ya esta ocupada en " + nombre);
        }
        if (posicion == null || posicion.trim().isEmpty())
        {
            throw new AccionNoPermitidaException("RN-07", "El jugador tiene que tener una posicion");
        }

        InscripcionEquipo inscripcion = new InscripcionEquipo(jugador, this, posicion, numeroCamiseta);
        agregarInscripcion(inscripcion);
        jugador.agregarInscripcionEquipo(inscripcion);
        return inscripcion;
    }

    /**
     * RF-S-03 - Inscribirse en un equipo
     *
     * Revisa si algun inscrito del equipo ya usa ese número de camiseta.
     *
     * Reglas: RN-06
     */
    public boolean tieneCamiseta(int numeroCamiseta)
    {
        for (InscripcionEquipo inscripcion : inscripciones)
        {
            if (inscripcion.getNumeroCamiseta() == numeroCamiseta)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * RF-E-07 - Verificar el mínimo de habilitados
     *
     * Cuenta cuántos inscritos pueden jugar hoy, es decir, los que no tienen una sanción pendiente.
     *
     * Reglas: RN-14
     */
    public int contarHabilitados()
    {
        int habilitados = 0;
        for (InscripcionEquipo inscripcion : inscripciones)
        {
            if (inscripcion.estaHabilitado())
            {
                habilitados++;
            }
        }
        return habilitados;
    }

    /**
     * RF-E-07 - Verificar el mínimo de habilitados
     *
     * Si el equipo no alcanza el mínimo, el entrenador necesita saber quienes no pueden jugar. Devolvemos
     * las inscripciones de los jugadores sancionados o dados de baja.
     *
     * Reglas: RN-12, RN-14
     */
    public List<InscripcionEquipo> consultarNoHabilitados()
    {
        List<InscripcionEquipo> noHabilitados = new ArrayList<InscripcionEquipo>();
        for (InscripcionEquipo inscripcion : inscripciones)
        {
            if (!inscripcion.estaHabilitado())
            {
                noHabilitados.add(inscripcion);
            }
        }
        return noHabilitados;
    }

    /**
     * RF-E-07 - Verificar el mínimo de habilitados
     *
     * El entrenador quiere saber si el equipo puede jugar. Cada deporte exige un mínimo de habilitados (7
     * en fútbol, 5 en baloncesto, 6 en voleibol), así que comparamos con el mínimo del deporte del equipo.
     *
     * Reglas: RN-09
     */
    public boolean puedeDisputarPartido()
    {
        return activo && contarHabilitados() >= deporte.getMinJugadoresHabilitados();
    }

    /**
     * RF-E-06 - Registrar tarjetas y suspensiones
     *
     * Cuando el equipo juega un partido, los sancionados cumplen un partido de su sanción. Recorremos las
     * inscripciones y le restamos uno a cada sanción pendiente.
     *
     * Reglas: RN-11, RN-12
     */
    public void descontarSancionesTrasPartido()
    {
        for (InscripcionEquipo inscripcion : inscripciones)
        {
            for (Sancion sancion : inscripcion.getSanciones())
            {
                if (!sancion.isCumplida())
                {
                    sancion.descontarPartido();
                }
            }
        }
    }
}
