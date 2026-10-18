package uniandes.dpoo.proyecto1.entrenamiento;

import uniandes.dpoo.proyecto1.usuarios.PerfilJugador;

/**
 * Clase intermedia entre SesionEntrenamiento y PerfilJugador: asistencia y evaluación de esa sesión.
 */
public class RegistroAsistencia
{
    private PerfilJugador jugador;
    private EstadoAsistencia estado;
    private Integer notaDesempeno;
    private String comentario;

    public RegistroAsistencia(PerfilJugador jugador)
    {
        this.jugador = jugador;
    }

    public PerfilJugador getJugador()
    {
        return jugador;
    }

    public EstadoAsistencia getEstado()
    {
        return estado;
    }

    public void setEstado(EstadoAsistencia estado)
    {
        this.estado = estado;
    }

    public Integer getNotaDesempeno()
    {
        return notaDesempeno;
    }

    public void setNotaDesempeno(Integer notaDesempeno)
    {
        this.notaDesempeno = notaDesempeno;
    }

    public String getComentario()
    {
        return comentario;
    }

    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }
}
