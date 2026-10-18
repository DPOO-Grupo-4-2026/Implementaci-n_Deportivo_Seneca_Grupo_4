package uniandes.dpoo.proyecto1.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.administracion.Turno;
import uniandes.dpoo.proyecto1.tienda.CodigoDescuento;

/**
 * Personal del club. Agrupa lo común de entrenadores y fisioterapeutas.
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

    @Override
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
