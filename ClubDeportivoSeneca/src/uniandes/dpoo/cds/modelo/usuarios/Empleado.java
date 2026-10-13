package uniandes.dpoo.cds.modelo.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.modelo.administracion.Turno;
import uniandes.dpoo.cds.modelo.tienda.CodigoDescuento;

/**
 * Personal del club. Agrupa lo comun de entrenadores y fisioterapeutas.
 */
public abstract class Empleado extends Usuario
{
    private CodigoDescuento codigoDescuento;
    private List<Turno> turnos;

    public Empleado(String login, String password, String nombre, LocalDate fechaNacimiento)
    {
        super(login, password, nombre, fechaNacimiento);
        this.turnos = new ArrayList<Turno>();
    }

    public CodigoDescuento getCodigoDescuento()
    {
        return codigoDescuento;
    }

    public void setCodigoDescuento(CodigoDescuento codigoDescuento)
    {
        this.codigoDescuento = codigoDescuento;
    }

    public List<Turno> getTurnos()
    {
        return turnos;
    }

    public void agregarTurno(Turno turno)
    {
        turnos.add(turno);
    }
}
