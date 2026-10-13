package uniandes.dpoo.cds.persistencia;

import java.io.File;
import java.io.IOException;
import uniandes.dpoo.cds.modelo.administracion.Club;

/**
 * Guarda y carga todo el estado de la aplicacion. El modelo no sabe como se persiste: solo esta clase lo sabe.
 *
 * Decision A-2: serializacion de Java. Todo el modelo implementa Serializable y se guarda un solo archivo
 * datos/club.dat con el Club completo. La carpeta de datos esta fuera de src, como exige el enunciado.
 */
public class PersistenciaClub
{
    public static final String NOMBRE_ARCHIVO = "club.dat";

    /**
     * Escribe el estado completo del club en la carpeta indicada.
     */
    public void guardar(Club club, File carpeta) throws IOException
    {
        throw new UnsupportedOperationException("Pendiente [persistencia]: PersistenciaClub.guardar");
    }

    /**
     * Reconstruye el club a partir de los archivos de la carpeta indicada.
     */
    public Club cargar(File carpeta) throws IOException
    {
        throw new UnsupportedOperationException("Pendiente [persistencia]: PersistenciaClub.cargar");
    }
}
