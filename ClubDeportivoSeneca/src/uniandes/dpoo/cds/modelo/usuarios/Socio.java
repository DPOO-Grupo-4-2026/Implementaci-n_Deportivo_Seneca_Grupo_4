package uniandes.dpoo.cds.modelo.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.modelo.tienda.PagoMensualidad;

/**
 * Miembro del club: juega, reserva y compra.
 */
public class Socio extends Usuario
{
    private List<PagoMensualidad> pagos;

    public Socio(String login, String password, String nombre, LocalDate fechaNacimiento)
    {
        super(login, password, nombre, fechaNacimiento);
        this.pagos = new ArrayList<PagoMensualidad>();
    }

    public List<PagoMensualidad> getPagos()
    {
        return pagos;
    }

    public void agregarPago(PagoMensualidad pago)
    {
        pagos.add(pago);
    }

    @Override
    public String getRol()
    {
        return "Socio";
    }
}
