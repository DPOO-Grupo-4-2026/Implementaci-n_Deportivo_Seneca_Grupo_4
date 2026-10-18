package uniandes.dpoo.proyecto1.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import uniandes.dpoo.proyecto1.administracion.Club;

/**
 * Guarda y carga todo el club en un archivo. Usamos serialización de Java: como Club tiene las listas de todo,
 * al guardar el Club se guardan también todos los objetos que cuelgan de el, con sus relaciones.
 *
 * El archivo va en la carpeta datos/, que está fuera de src como pide el enunciado.
 */
public class PersistenciaClub
{
    public static final String NOMBRE_ARCHIVO = "club.dat";

    /**
     * Escribe el club completo en carpeta/club.dat. Si la carpeta no existe, la crea.
     */
    public void guardar(Club club, File carpeta) throws IOException
    {
        if (!carpeta.exists())
        {
            carpeta.mkdirs();
        }
        File archivo = new File(carpeta, NOMBRE_ARCHIVO);
        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo));
        try
        {
            salida.writeObject(club);
        }
        finally
        {
            salida.close();
        }
    }

    /**
     * Lee el club desde carpeta/club.dat.
     */
    public Club cargar(File carpeta) throws IOException
    {
        File archivo = new File(carpeta, NOMBRE_ARCHIVO);
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo));
        try
        {
            return (Club) entrada.readObject();
        }
        catch (ClassNotFoundException e)
        {
            throw new IOException("El archivo " + archivo.getName() + " no tiene un club guardado");
        }
        finally
        {
            entrada.close();
        }
    }
}
