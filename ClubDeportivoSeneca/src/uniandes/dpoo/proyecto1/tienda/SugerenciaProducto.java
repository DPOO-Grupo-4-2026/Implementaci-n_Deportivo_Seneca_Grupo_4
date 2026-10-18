package uniandes.dpoo.proyecto1.tienda;

import java.time.LocalDate;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.usuarios.Administrador;
import uniandes.dpoo.proyecto1.usuarios.Empleado;

/**
 * Propuesta de un empleado, sujeta a aprobacion del administrador.
 */
public class SugerenciaProducto
{
    private String descripcion;
    private LocalDate fecha;
    private Empleado autor;
    private EstadoSugerencia estado;
    private Administrador resueltaPor;

    public SugerenciaProducto(String descripcion, LocalDate fecha, Empleado autor)
    {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.autor = autor;
        this.estado = EstadoSugerencia.PENDIENTE;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public LocalDate getFecha()
    {
        return fecha;
    }

    public Empleado getAutor()
    {
        return autor;
    }

    public EstadoSugerencia getEstado()
    {
        return estado;
    }

    public void setEstado(EstadoSugerencia estado)
    {
        this.estado = estado;
    }

    public Administrador getResueltaPor()
    {
        return resueltaPor;
    }

    public void setResueltaPor(Administrador resueltaPor)
    {
        this.resueltaPor = resueltaPor;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-A-06 - Aprobar o rechazar sugerencias
     *
     * El administrador acepta la sugerencia de un empleado.
     *
     * Reglas: RN-36
     */
    public void aprobar(Administrador administrador) throws ReglaDominioException
    {
        if (estado != EstadoSugerencia.PENDIENTE)
        {
            throw new AccionNoPermitidaException("RN-36", "La sugerencia ya fue resuelta");
        }
        estado = EstadoSugerencia.APROBADA;
        resueltaPor = administrador;
    }

    /**
     * RF-A-06 - Aprobar o rechazar sugerencias
     *
     * El administrador rechaza la sugerencia de un empleado.
     *
     * Reglas: RN-36
     */
    public void rechazar(Administrador administrador) throws ReglaDominioException
    {
        if (estado != EstadoSugerencia.PENDIENTE)
        {
            throw new AccionNoPermitidaException("RN-36", "La sugerencia ya fue resuelta");
        }
        estado = EstadoSugerencia.RECHAZADA;
        resueltaPor = administrador;
    }
}
