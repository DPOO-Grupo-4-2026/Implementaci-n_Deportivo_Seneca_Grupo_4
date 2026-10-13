package uniandes.dpoo.cds.modelo.conjunto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.cds.excepciones.CupoExcedidoException;
import uniandes.dpoo.cds.excepciones.JugadorNoHabilitadoException;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.usuarios.PerfilJugador;

/**
 * Conjunto de jugadores de un unico deporte y una unica categoria.
 */
public class Equipo
{
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
    // Logica del dominio
    // ------------------------------------------------------------------

    /**
     * Inscribe al jugador en el equipo y devuelve la inscripcion creada.
     *
     * Verifica:
     * - Que el equipo y el jugador esten activos.
     * - Que quede cupo.
     * - Que la edad del jugador este en el rango de la categoria.
     * - Que el jugador no tenga ya un equipo en este deporte.
     * - Que el numero de camiseta este libre en el equipo.
     * - Al final: la inscripcion queda en la lista del equipo y en la del jugador.
     *
     * Reglas: RN-03, RN-04, RN-05, RN-06, RN-35
     *
     * Por que asi: Todas las validaciones van antes de crear nada: un rechazo no deja la relacion a medias. El metodo que crea la inscripcion la agrega en las dos puntas.
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

        InscripcionEquipo inscripcion = new InscripcionEquipo(jugador, this, posicion, numeroCamiseta);
        agregarInscripcion(inscripcion);
        jugador.agregarInscripcionEquipo(inscripcion);
        return inscripcion;
    }

    /**
     * Dice si algun inscrito ya usa ese numero.
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
     * Numero de inscritos sin sancion vigente.
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
     * Dice si el equipo esta activo y alcanza el minimo de habilitados de su deporte.
     *
     * Reglas: RN-09
     *
     * Por que asi: El minimo es del deporte, no del equipo: el equipo se lo pregunta a su deporte.
     */
    public boolean puedeDisputarPartido()
    {
        return activo && contarHabilitados() >= deporte.getMinJugadoresHabilitados();
    }

    /**
     * Tras jugar un partido, descuenta un partido a cada sancion vigente del equipo.
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
