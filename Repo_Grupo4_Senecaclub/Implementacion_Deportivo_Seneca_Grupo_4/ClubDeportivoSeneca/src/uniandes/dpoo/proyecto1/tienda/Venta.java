package uniandes.dpoo.proyecto1.tienda;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;
import uniandes.dpoo.proyecto1.excepciones.VentaRechazadaException;
import uniandes.dpoo.proyecto1.usuarios.Usuario;

/**
 * Transacción registrada. Guarda lo común de tienda y cafetería.
 */
public abstract class Venta implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** Cada venta da al comprador el 2% en puntos. */
    public static final double PORCENTAJE_PUNTOS = 0.02;

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

    /** Cargos que se suman después de impuestos. Por defecto ninguno; la cafetería suma la propina. */
    protected double calcularCargosAdicionales(double base)
    {
        return 0;
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-S-12, RF-S-13 - Comprar en la tienda / Comprar en la cafetería
     *
     * El comprador agrega un producto a la compra. La tienda solo vende artículos deportivos y la
     * cafetería solo bebidas y snacks.
     *
     * Reglas: RN-28, RN-29
     */
    public LineaVenta agregarLinea(Producto producto, int cantidad) throws ReglaDominioException
    {
        if (cerrada)
        {
            throw new VentaRechazadaException("RN-34", "La venta " + id + " ya esta cerrada");
        }
        if (cantidad <= 0)
        {
            throw new VentaRechazadaException("RN-34", "La cantidad tiene que ser mayor que cero");
        }
        if (!admiteProducto(producto))
        {
            throw new VentaRechazadaException("RN-34", producto.getNombre() + " no se vende en " + getRubro());
        }
        for (LineaVenta linea : lineas)
        {
            if (linea.getProducto() == producto)
            {
                linea.setCantidad(linea.getCantidad() + cantidad);
                return linea;
            }
        }
        LineaVenta linea = new LineaVenta(producto, cantidad, producto.getPrecioBase());
        lineas.add(linea);
        return linea;
    }

    /**
     * La tienda vende artículos deportivos; la cafetería, bebidas y snacks.
     *
     * Reglas: RN-28, RN-29
     */
    public abstract boolean admiteProducto(Producto producto);

    /**
     * RF-E-12 - Comprar con código de descuento
     *
     * Se usa el código de descuento de un empleado. Solo se puede uno por compra.
     *
     * Reglas: RN-30
     */
    public void aplicarCodigoDescuento(CodigoDescuento codigo) throws ReglaDominioException
    {
        if (cerrada)
        {
            throw new VentaRechazadaException("RN-34", "La venta " + id + " ya esta cerrada");
        }
        if (!codigo.isActivo())
        {
            throw new VentaRechazadaException("RN-30", "El codigo " + codigo.getCodigo() + " no esta activo");
        }
        if (codigoDescuento != null)
        {
            throw new VentaRechazadaException("RN-30", "La venta ya tiene un descuento y no se puede acumular otro");
        }
        codigoDescuento = codigo;
    }

    /**
     * Cada tipo de venta aplica su impuesto.
     *
     * Reglas: RN-28, RN-29
     */
    public abstract double calcularImpuestos(double base);

    /**
     * Tienda o Cafetería, para el reporte de ventas.
     *
     * Reglas: RN-34
     */
    public abstract String getRubro();

    /**
     * RF-S-12, RF-S-13, RF-S-14 - Comprar en la tienda / Comprar en la cafetería / Consultar y redimir puntos
     *
     * Se paga la compra: subtotal, descuento, impuesto, propina si es cafetería, y los puntos que se usan
     * y los que se ganan.
     *
     * Reglas: RN-30, RN-31, RN-34
     */
    public void cerrar(int puntosARedimir) throws ReglaDominioException
    {
        if (cerrada)
        {
            throw new VentaRechazadaException("RN-34", "La venta " + id + " ya esta cerrada");
        }
        if (lineas.isEmpty())
        {
            throw new VentaRechazadaException("RN-34", "La venta no tiene productos");
        }
        if (puntosARedimir < 0 || puntosARedimir > comprador.getSaldoPuntosFidelidad())
        {
            throw new VentaRechazadaException("RN-31", comprador.getNombre() + " tiene " + comprador.getSaldoPuntosFidelidad() + " puntos y quiere usar " + puntosARedimir);
        }

        // 1. Suma de las líneas
        double suma = 0;
        for (LineaVenta linea : lineas)
        {
            suma = suma + linea.getSubtotal();
        }
        // 2. Descuento: va antes del impuesto, porque el impuesto se cobra sobre lo que de verdad se paga
        double descuento = 0;
        if (codigoDescuento != null)
        {
            descuento = suma * codigoDescuento.porcentajePara(comprador);
        }
        double base = suma - descuento;
        // 3. Impuesto y propina, según el tipo de venta
        double impuestos = calcularImpuestos(base);
        double cargos = calcularCargosAdicionales(base);
        double totalAntesDePuntos = base + impuestos + cargos;
        if (puntosARedimir > totalAntesDePuntos)
        {
            throw new VentaRechazadaException("RN-31", "No se pueden usar mas puntos que el valor de la compra");
        }

        // 4. Se guardan los valores y se mueven los puntos (1 punto = 1 peso)
        subtotal = suma;
        totalImpuestos = impuestos;
        total = totalAntesDePuntos - puntosARedimir;
        puntosRedimidos = puntosARedimir;
        puntosGenerados = (int) ((base - puntosARedimir) * PORCENTAJE_PUNTOS);
        if (puntosGenerados < 0)
        {
            puntosGenerados = 0;
        }
        comprador.redimirPuntos(puntosRedimidos, this);
        comprador.acumularPuntos(puntosGenerados, this);
        cerrada = true;
    }
}
