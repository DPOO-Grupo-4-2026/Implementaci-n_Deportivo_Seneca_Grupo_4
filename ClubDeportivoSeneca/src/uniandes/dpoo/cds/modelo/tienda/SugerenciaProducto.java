package uniandes.dpoo.cds.modelo.tienda;

import java.time.LocalDate;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.usuarios.Administrador;
import uniandes.dpoo.cds.modelo.usuarios.Empleado;

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

    public Administrador getResueltaPor()
    {
        return resueltaPor;
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Deja la sugerencia aprobada y registra quien la resolvio.
     *
     * Que debe verificar:
     * - Que este pendiente.
     *
     * Reglas: RN-36
     */
    public void aprobar(Administrador administrador) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-36]: SugerenciaProducto.aprobar");
    }

    /**
     * Deja la sugerencia rechazada y registra quien la resolvio.
     *
     * Que debe verificar:
     * - Que este pendiente.
     *
     * Reglas: RN-36
     */
    public void rechazar(Administrador administrador) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-36]: SugerenciaProducto.rechazar");
    }
}
