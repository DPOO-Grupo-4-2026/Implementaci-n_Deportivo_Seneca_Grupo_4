package uniandes.dpoo.proyecto1.tienda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.proyecto1.excepciones.AccionNoPermitidaException;
import uniandes.dpoo.proyecto1.excepciones.ReglaDominioException;

/**
 * Bodega, tienda o cafetería. Conoce sus existencias.
 */
public class UbicacionInventario implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String nombre;
    private TipoUbicacion tipo;
    private List<Existencia> existencias;

    public UbicacionInventario(String nombre, TipoUbicacion tipo)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.existencias = new ArrayList<Existencia>();
    }

    public String getNombre()
    {
        return nombre;
    }

    public TipoUbicacion getTipo()
    {
        return tipo;
    }

    public List<Existencia> getExistencias()
    {
        return existencias;
    }

    public void agregarExistencia(Existencia existencia)
    {
        existencias.add(existencia);
    }

    // ------------------------------------------------------------------
    // Lógica del dominio
    // ------------------------------------------------------------------

    /**
     * RF-A-04 - Consultar el inventario
     *
     * Busca cuántas unidades hay de un producto en esta ubicación.
     *
     * Reglas: RN-35
     */
    public Existencia buscarExistencia(Producto producto)
    {
        for (Existencia existencia : existencias)
        {
            if (existencia.getProducto() == producto)
            {
                return existencia;
            }
        }
        return null;
    }

    /**
     * RF-A-05 - Mover y reabastecer artículos
     *
     * Suma unidades de un producto en esta ubicación.
     *
     * Reglas: RN-35
     */
    public void ingresar(Producto producto, int cantidad)
    {
        Existencia existencia = buscarExistencia(producto);
        if (existencia == null)
        {
            existencias.add(new Existencia(producto, cantidad));
        }
        else
        {
            existencia.setCantidadDisponible(existencia.getCantidadDisponible() + cantidad);
        }
    }

    /**
     * RF-A-05, RF-S-12 - Mover y reabastecer artículos / Comprar en la tienda
     *
     * Saca unidades de un producto. Si no hay suficientes, se rechaza.
     *
     * Reglas: RN-35
     */
    public void retirar(Producto producto, int cantidad) throws ReglaDominioException
    {
        Existencia existencia = buscarExistencia(producto);
        if (existencia == null || existencia.getCantidadDisponible() < cantidad)
        {
            int hay = 0;
            if (existencia != null)
            {
                hay = existencia.getCantidadDisponible();
            }
            throw new AccionNoPermitidaException("RN-35", "En " + nombre + " hay " + hay + " de " + producto.getNombre() + " y se piden " + cantidad);
        }
        existencia.setCantidadDisponible(existencia.getCantidadDisponible() - cantidad);
    }
}
