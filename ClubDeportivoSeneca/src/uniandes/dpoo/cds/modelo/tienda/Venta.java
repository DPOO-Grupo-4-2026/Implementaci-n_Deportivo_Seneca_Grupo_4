package uniandes.dpoo.cds.modelo.tienda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.excepciones.ReglaDominioException;
import uniandes.dpoo.cds.modelo.usuarios.Usuario;

/**
 * Transaccion registrada. Guarda lo comun de tienda y cafeteria.
 */
public abstract class Venta
{
    private String id;
    private LocalDateTime fecha;
    private Usuario comprador;
    private double subtotal;
    private double totalImpuestos;
    private double total;
    private int puntosGenerados;
    private int puntosRedimidos;
    private CodigoDescuento codigoDescuento;
    private boolean cerrada;
    private List<LineaVenta> lineas;

    public Venta(String id, LocalDateTime fecha, Usuario comprador)
    {
        this.id = id;
        this.fecha = fecha;
        this.comprador = comprador;
        this.subtotal = 0;
        this.totalImpuestos = 0;
        this.total = 0;
        this.puntosGenerados = 0;
        this.puntosRedimidos = 0;
        this.cerrada = false;
        this.lineas = new ArrayList<LineaVenta>();
    }

    public String getId()
    {
        return id;
    }

    public LocalDateTime getFecha()
    {
        return fecha;
    }

    public Usuario getComprador()
    {
        return comprador;
    }

    public double getSubtotal()
    {
        return subtotal;
    }

    public double getTotalImpuestos()
    {
        return totalImpuestos;
    }

    public double getTotal()
    {
        return total;
    }

    public int getPuntosGenerados()
    {
        return puntosGenerados;
    }

    public int getPuntosRedimidos()
    {
        return puntosRedimidos;
    }

    public CodigoDescuento getCodigoDescuento()
    {
        return codigoDescuento;
    }

    public boolean isCerrada()
    {
        return cerrada;
    }

    public List<LineaVenta> getLineas()
    {
        return lineas;
    }

    public void agregarLinea(LineaVenta linea)
    {
        lineas.add(linea);
    }

    /** Cargos que se suman despues de impuestos. Por defecto ninguno; la cafeteria suma la propina (D-19). */
    protected double calcularCargosAdicionales(double base)
    {
        return 0;
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Agrega un renglon con el precio actual del producto.
     *
     * Que debe verificar:
     * - Que la venta no este cerrada y la cantidad sea positiva.
     * - Que el producto se pueda vender en este tipo de venta (ver admiteProducto).
     * - Si ya hay linea de ese producto, suma la cantidad.
     *
     * Reglas: RN-28, RN-29
     */
    public LineaVenta agregarLinea(Producto producto, int cantidad) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-28, RN-29]: Venta.agregarLinea");
    }

    /**
     * La tienda vende articulos deportivos; la cafeteria, bebidas y snacks.
     *
     * Reglas: RN-28, RN-29
     */
    public abstract boolean admiteProducto(Producto producto);

    /**
     * Asocia el codigo de descuento a la venta.
     *
     * Que debe verificar:
     * - Que el codigo este activo.
     * - Que la venta no tenga ya otro descuento: no es acumulable.
     *
     * Reglas: RN-30
     */
    public void aplicarCodigoDescuento(CodigoDescuento codigo) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-30]: Venta.aplicarCodigoDescuento");
    }

    /**
     * Cada tipo de venta aplica su impuesto.
     *
     * Reglas: RN-28, RN-29
     */
    public abstract double calcularImpuestos(double base);

    /**
     * Calcula subtotal, descuento, impuestos y total; redime y acumula puntos; deja la venta cerrada.
     *
     * Que debe verificar:
     * - Validar todo primero: lineas, no cerrada, saldo y puntos menores o iguales al total.
     * - Orden: subtotal, descuento, base, impuestos, cargos adicionales, redencion (1 punto = 1 peso).
     * - Puntos generados: 2% de (base - puntos redimidos), truncado.
     *
     * Reglas: RN-30, RN-31, RN-34
     */
    public void cerrar(int puntosARedimir) throws ReglaDominioException
    {
        throw new UnsupportedOperationException("Pendiente [RN-30, RN-31, RN-34]: Venta.cerrar");
    }
}
