package uniandes.dpoo.proyecto1.administracion;

import java.io.Serializable;
import java.time.LocalDate;
import uniandes.dpoo.proyecto1.usuarios.Administrador;
import uniandes.dpoo.proyecto1.usuarios.Empleado;

/**
 * Petición de un empleado para ceder un turno, sujeta a aprobacion.
 */
public class SolicitudCambioTurno implements Serializable
{
    private static final long serialVersionUID = 1L;

    private Turno turno;
    private Empleado solicitante;
    private Empleado reemplazo;
    private LocalDate fechaSolicitud;
    private String motivo;
    private EstadoSolicitud estado;
    private Administrador resueltaPor;

    public SolicitudCambioTurno(Turno turno, Empleado solicitante, Empleado reemplazo, LocalDate fechaSolicitud, String motivo)
    {
        this.turno = turno;
        this.solicitante = solicitante;
        this.reemplazo = reemplazo;
        this.fechaSolicitud = fechaSolicitud;
        this.motivo = motivo;
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public Turno getTurno()
    {
        return turno;
    }

    public Empleado getSolicitante()
    {
        return solicitante;
    }

    public Empleado getReemplazo()
    {
        return reemplazo;
    }

    public LocalDate getFechaSolicitud()
    {
        return fechaSolicitud;
    }

    public String getMotivo()
    {
        return motivo;
    }

    public EstadoSolicitud getEstado()
    {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado)
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
}
