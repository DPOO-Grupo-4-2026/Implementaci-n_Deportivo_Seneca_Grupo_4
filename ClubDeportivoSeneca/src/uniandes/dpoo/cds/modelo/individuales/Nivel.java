package uniandes.dpoo.cds.modelo.individuales;

/**
 * Nivel de juego. El orden de declaracion es el que permite calcular la adyacencia: no reordenar.
 */
public enum Nivel
{
    PRINCIPIANTE,
    INTERMEDIO,
    AVANZADO;

    /**
     * Dice si dos jugadores de estos niveles pueden programar un partido de practica.
     *
     * Que debe verificar:
     * - Decision A-1: diferencia de ordinal menor o igual a 1.
     *
     * Reglas: RN-23
     */
    public boolean puedeJugarContra(Nivel otro)
    {
        throw new UnsupportedOperationException("Pendiente [RN-23]: Nivel.puedeJugarContra");
    }
}
