package uniandes.dpoo.cds.modelo.administracion;

import java.time.DayOfWeek;
import java.time.LocalTime;
import uniandes.dpoo.cds.modelo.usuarios.Empleado;

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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Dice si el turno incluye ese dia y esa hora: [inicio, fin).
     *
     * Reglas: RN-37
     */
    public boolean cubre(DayOfWeek dia, LocalTime hora)
    {
        throw new UnsupportedOperationException("Pendiente [RN-37]: Turno.cubre");
    }
}
