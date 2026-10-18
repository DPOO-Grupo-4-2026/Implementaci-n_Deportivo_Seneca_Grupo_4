package uniandes.dpoo.proyecto1.usuarios;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.excepciones.VentaRechazadaException;
import uniandes.dpoo.proyecto1.tienda.CodigoDescuento;
import uniandes.dpoo.proyecto1.tienda.MovimientoPuntos;
import uniandes.dpoo.proyecto1.tienda.TipoPuntos;
import uniandes.dpoo.proyecto1.tienda.Venta;

/**
 * Cualquier persona que puede autenticarse en el sistema.
 */
public abstract class Usuario
{
    private String login;
    private String password;
    private String nombre;
    private LocalDate fechaNacimiento;
    private boolean activo;
    private int saldoPuntosFidelidad;
    private PerfilJugador perfilJugador;
    private List<MovimientoPuntos> movimientosPuntos;

    public Usuario(String login, String password, String nombre, LocalDate fechaNacimiento)
    {
        this.login = login;
        this.password = password;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = true;
        this.saldoPuntosFidelidad = 0;
        this.movimientosPuntos = new ArrayList<MovimientoPuntos>();
    }

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public String getNombre()
    {
        return nombre;
    }

    public LocalDate getFechaNacimiento()
    {
        return fechaNacimiento;
    }

    public boolean isActivo()
    {
        return activo;
    }

    public void setActivo(boolean activo)
    {
        this.activo = activo;
    }

    public int getSaldoPuntosFidelidad()
    {
        return saldoPuntosFidelidad;
    }

    public void setSaldoPuntosFidelidad(int saldoPuntosFidelidad)
    {
        this.saldoPuntosFidelidad = saldoPuntosFidelidad;
    }

    public PerfilJugador getPerfilJugador()
    {
        return perfilJugador;
    }

    public List<MovimientoPuntos> getMovimientosPuntos()
    {
        return movimientosPuntos;
    }

    public void agregarMovimientoPuntos(MovimientoPuntos movimientoPuntos)
    {
        movimientosPuntos.add(movimientoPuntos);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-01, RF-E-01, RF-A-01 - Autenticarse
     *
     * Cuando alguien intenta entrar, Club busca su login y le pregunta a ese usuario si la clave que
     * escribió es la suya. Comparamos con equals porque lo que importa es que el texto sea igual.
     *
     * Reglas: RN-01
     */
    public boolean validarPassword(String intento)
    {
        return password != null && password.equals(intento);
    }

    /**
     * RF-S-03 - Inscribirse en un equipo
     *
     * Para inscribirse en un equipo hay que saber cuántos años tiene el jugador hoy. Usamos Period porque
     * cuenta los años cumplidos: alguien que cumple en diciembre todavía no los tiene en octubre.
     *
     * Reglas: RN-04
     */
    public int calcularEdad(LocalDate hoy)
    {
        return Period.between(fechaNacimiento, hoy).getYears();
    }

    /**
     * RF-S-03, RF-S-07 - Inscribirse en un equipo / Inscribirse en una disciplina individual
     *
     * Antes de inscribirse en un equipo o una disciplina, el usuario necesita su perfil de jugador. Lo
     * tiene Usuario y no Socio porque un entrenador también puede jugar partidos de práctica. Si ya tenía
     * perfil, no se crea otro.
     *
     * Reglas: RN-38
     */
    public PerfilJugador crearPerfilJugador() throws ReglaDominioException
    {
        if (perfilJugador != null)
        {
            throw new AccionNoPermitidaException("RN-38", nombre + " ya tiene perfil de jugador");
        }
        perfilJugador = new PerfilJugador(this);
        return perfilJugador;
    }

    /**
     * RF-S-14 - Consultar y redimir puntos
     *
     * Cada compra le da al comprador puntos de fidelidad. Aquí se suman al saldo y queda el movimiento
     * guardado, para que el usuario pueda ver de dónde salió cada punto.
     *
     * Reglas: RN-31
     */
    public void acumularPuntos(int puntos, Venta venta)
    {
        if (puntos <= 0)
        {
            return;
        }
        saldoPuntosFidelidad = saldoPuntosFidelidad + puntos;
        movimientosPuntos.add(new MovimientoPuntos(venta.getFecha().toLocalDate(), TipoPuntos.ACUMULACION, puntos, venta));
    }

    /**
     * RF-S-14 - Consultar y redimir puntos
     *
     * El comprador usa puntos para pagar parte de una venta. Si no le alcanza el saldo se rechaza; si
     * alcanza, se descuentan y queda el movimiento de redención.
     *
     * Reglas: RN-31
     */
    public void redimirPuntos(int puntos, Venta venta) throws ReglaDominioException
    {
        if (puntos <= 0)
        {
            return;
        }
        if (puntos > saldoPuntosFidelidad)
        {
            throw new VentaRechazadaException("RN-31", nombre + " tiene " + saldoPuntosFidelidad + " puntos y quiere usar " + puntos);
        }
        saldoPuntosFidelidad = saldoPuntosFidelidad - puntos;
        movimientosPuntos.add(new MovimientoPuntos(venta.getFecha().toLocalDate(), TipoPuntos.REDENCION, puntos, venta));
    }

    /**
     * Nombre del tipo de usuario, para mostrarlo en consola.
     */
    public abstract String getRol();

    /**
     * RF-E-12 - Comprar con código de descuento
     *
     * Solo los empleados tienen código de descuento. Para el resto de usuarios no hay código.
     */
    public CodigoDescuento getCodigoDescuento()
    {
        return null;
    }
}
