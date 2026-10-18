package uniandes.dpoo.proyecto1.administracion;

import java.time.DayOfWeek;
import java.time.LocalTime;
import uniandes.dpoo.proyecto1.usuarios.Empleado;

/**
 * Franja laboral semanal de un empleado.
 */
public class Turno
{
    private Empleado empleado;
    private DayOfWeek dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Turno(Empleado empleado, DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin)
    {
        this.empleado = empleado;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Empleado getEmpleado()
    {
        return empleado;
    }

    public void setEmpleado(Empleado empleado)
    {
        this.empleado = empleado;
    }

    public DayOfWeek getDia()
    {
        return dia;
    }

    public LocalTime getHoraInicio()
    {
        return horaInicio;
    }

    public LocalTime getHoraFin()
    {
        return horaFin;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-A-08 - Administrar turnos
     *
     * Responde si el turno incluye ese día y esa hora.
     *
     * Reglas: RN-37
     */
    public boolean cubre(DayOfWeek dia, LocalTime hora)
    {
        return this.dia == dia && !hora.isBefore(horaInicio) && hora.isBefore(horaFin);
    }
}
