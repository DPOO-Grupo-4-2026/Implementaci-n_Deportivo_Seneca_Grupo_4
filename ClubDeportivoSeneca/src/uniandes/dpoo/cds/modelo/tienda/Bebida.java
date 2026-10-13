package uniandes.dpoo.cds.modelo.tienda;

/**
 * Bebida fria o caliente.
 */
public class Bebida extends ProductoCafeteria
{
    private Temperatura temperatura;

    public Bebida(String codigo, String nombre, double precioBase, Temperatura temperatura)
    {
        super(codigo, nombre, precioBase);
        this.temperatura = temperatura;
    }

    public Temperatura getTemperatura()
    {
        return temperatura;
    }

    // ------------------------------------------------------------------
    // Logica del dominio (los metodos que lanzan UnsupportedOperationException los escribe el grupo)
    // ------------------------------------------------------------------

    /**
     * Dice si la bebida es caliente.
     *
     * Reglas: RN-32
     */
    @Override
    public boolean esBebidaCaliente()
    {
        throw new UnsupportedOperationException("Pendiente [RN-32]: Bebida.esBebidaCaliente");
    }
}
