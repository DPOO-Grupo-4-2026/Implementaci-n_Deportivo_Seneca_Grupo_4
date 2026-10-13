package uniandes.dpoo.cds.modelo.usuarios;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.tienda.MovimientoPuntos;
import uniandes.dpoo.cds.modelo.tienda.Venta;

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
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Dice si la clave digitada coincide con la del usuario.
     *
     * Reglas: RN-01
     *
     * Por que asi: equals compara el contenido; == compararia referencias y fallaria con claves leidas de archivo.
     */
    public boolean validarPassword(String intento)
    {
        return password != null && password.equals(intento);
    }

    /**
     * Edad cumplida del usuario en la fecha dada.
     *
     * Reglas: RN-04
     *
     * Por que asi: Period cuenta anios cumplidos; restar solo los anios fallaria antes del cumpleanios.
     */
    public int calcularEdad(LocalDate hoy)
    {
        return Period.between(fechaNacimiento, hoy).getYears();
    }

    /**
     * Crea el perfil de jugador del usuario y lo deja asociado.
     *
     * Verifica:
     * - Que el usuario no tenga ya un perfil.
     * - Administrador redefine este metodo para rechazarlo.
     *
     * Reglas: RN-38
     *
     * Por que asi: El perfil cuelga de Usuario y no de Socio porque un entrenador tambien juega (RN-23).
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
     * Suma puntos al saldo y deja el MovimientoPuntos de tipo ACUMULACION.
     *
     * Reglas: RN-31
     */
    public void acumularPuntos(int puntos, Venta venta)
    {
        throw new UnsupportedOperationException("Pendiente [RN-31]: Usuario.acumularPuntos");
    }

    /**
     * Resta puntos del saldo y deja el MovimientoPuntos de tipo REDENCION.
     *
     * Que debe verificar:
     * - Que el saldo alcance para la redencion.
     *
     * Reglas: RN-31
     */
    public void redimirPuntos(int puntos, Venta venta) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-31]: Usuario.redimirPuntos");
    }

    /**
     * Nombre del tipo de usuario, para mostrarlo en consola.
     */
    public abstract String getRol();
}
