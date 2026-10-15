package uniandes.dpoo.proyecto1.individuales;

/**
 * Nivel de juego de un jugador en una disciplina.
 */
public enum Nivel
{
    PRINCIPIANTE,
    INTERMEDIO,
    AVANZADO;

    /**
     * RF-S-09 - Programar un partido de práctica
     *
     * Dos jugadores pueden programar un partido de práctica si tienen el mismo nivel o niveles seguidos.
     *
     * Reglas: RN-23
     */
    public boolean puedeJugarContra(Nivel otro)
    {
        if (this == otro)
        {
            return true;
        }
        // Intermedio está al lado de los otros dos; principiante y avanzado no están seguidos
        return this == INTERMEDIO || otro == INTERMEDIO;
    }
}
