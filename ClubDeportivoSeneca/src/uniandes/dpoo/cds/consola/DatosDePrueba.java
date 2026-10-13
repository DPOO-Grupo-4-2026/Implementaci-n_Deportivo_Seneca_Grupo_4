package uniandes.dpoo.cds.consola;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import uniandes.dpoo.cds.modelo.administracion.Club;
import uniandes.dpoo.cds.modelo.administracion.Turno;
import uniandes.dpoo.cds.modelo.competencias.CompetenciaLiga;
import uniandes.dpoo.cds.modelo.competencias.CompetenciaPorFases;
import uniandes.dpoo.cds.modelo.conjunto.Categoria;
import uniandes.dpoo.cds.modelo.conjunto.DeporteConjunto;
import uniandes.dpoo.cds.modelo.conjunto.Equipo;
import uniandes.dpoo.cds.modelo.individuales.CanchaPadel;
import uniandes.dpoo.cds.modelo.individuales.CanchaTenis;
import uniandes.dpoo.cds.modelo.individuales.DisciplinaIndividual;
import uniandes.dpoo.cds.modelo.individuales.Instalacion;
import uniandes.dpoo.cds.modelo.individuales.MesaTenisMesa;
import uniandes.dpoo.cds.modelo.tienda.Alergeno;
import uniandes.dpoo.cds.modelo.tienda.ArticuloDeportivo;
import uniandes.dpoo.cds.modelo.tienda.Bebida;
import uniandes.dpoo.cds.modelo.tienda.CodigoDescuento;
import uniandes.dpoo.cds.modelo.tienda.Existencia;
import uniandes.dpoo.cds.modelo.tienda.Producto;
import uniandes.dpoo.cds.modelo.tienda.Snack;
import uniandes.dpoo.cds.modelo.tienda.Temperatura;
import uniandes.dpoo.cds.modelo.tienda.TipoUbicacion;
import uniandes.dpoo.cds.modelo.tienda.UbicacionInventario;
import uniandes.dpoo.cds.modelo.usuarios.Administrador;
import uniandes.dpoo.cds.modelo.usuarios.Empleado;
import uniandes.dpoo.cds.modelo.usuarios.Entrenador;
import uniandes.dpoo.cds.modelo.usuarios.Fisioterapeuta;
import uniandes.dpoo.cds.modelo.usuarios.Socio;

/**
 * Arma en memoria un club pequeno para los programas de prueba, mientras no exista la persistencia.
 *
 * Los atributos son publicos a proposito: esta clase no es parte del modelo, es solo el punto de partida de las
 * pruebas. Solo usa constructores y metodos agregar: no depende de ninguna logica del dominio.
 */
public class DatosDePrueba
{
    public static final LocalDate HOY = LocalDate.of(2026, 10, 15);

    public Club club;

    public Administrador admin;
    public Entrenador entrenador1;
    public Entrenador entrenador2;
    public Entrenador entrenador3;
    public Fisioterapeuta fisio1;
    public Fisioterapeuta fisio2;
    public Socio sofia;
    public Socio mateo;
    public Socio valentina;
    public Socio andres;
    /** Socios mayores de edad sin equipo, para armar plantillas completas en las pruebas. */
    public List<Socio> plantillaA = new ArrayList<Socio>();
    public List<Socio> plantillaB = new ArrayList<Socio>();

    public DeporteConjunto futbol;
    public DeporteConjunto baloncesto;
    public DeporteConjunto voleibol;
    public Categoria sub13;
    public Categoria sub15;
    public Categoria sub17;
    public Categoria mayores;
    public Equipo futbolMayoresA;
    public Equipo futbolMayoresB;
    public Equipo futbolSub15;
    public Equipo baloncestoMayores;
    public Equipo voleibolCupoUno;
    public CompetenciaLiga torneoCerros;
    public CompetenciaPorFases ascun;

    public DisciplinaIndividual tenis;
    public DisciplinaIndividual padel;
    public DisciplinaIndividual tenisDeMesa;
    public CanchaTenis canchaTenis1;
    public CanchaTenis canchaTenis2;
    public CanchaPadel canchaPadel1;
    public MesaTenisMesa mesaTechada;
    public MesaTenisMesa mesaAireLibre;

    public ArticuloDeportivo raqueta;
    public ArticuloDeportivo balon;
    public Bebida cafe;
    public Bebida jugo;
    public Snack barraDeMani;
    public UbicacionInventario bodega;
    public UbicacionInventario tienda;
    public UbicacionInventario cafeteria;
    public CodigoDescuento codigoEntrenador1;

    public DatosDePrueba()
    {
        club = new Club("Club Deportivo Seneca", LocalTime.of(6, 0), LocalTime.of(22, 0));
        crearUsuarios();
        crearDeportesDeConjunto();
        crearDisciplinasIndividuales();
        crearTiendaYCafeteria();
        crearTurnos();
    }

    private void crearUsuarios()
    {
        admin = new Administrador("admin", "admin123", "Laura Rojas", LocalDate.of(1985, 3, 10));
        entrenador1 = new Entrenador("ent1", "ent123", "Carlos Pineda", LocalDate.of(1990, 5, 2));
        entrenador2 = new Entrenador("ent2", "ent123", "Diana Mora", LocalDate.of(1988, 11, 20));
        entrenador3 = new Entrenador("ent3", "ent123", "Felipe Arango", LocalDate.of(1993, 1, 15));
        fisio1 = new Fisioterapeuta("fis1", "fis123", "Paula Nieto", LocalDate.of(1991, 7, 8));
        fisio2 = new Fisioterapeuta("fis2", "fis123", "Jorge Salas", LocalDate.of(1989, 9, 30));
        // Edades al 15 de octubre de 2026: Sofia 22, Mateo 14, Valentina 20, Andres 16
        sofia = new Socio("sofia", "sofia123", "Sofia Herrera", LocalDate.of(2004, 2, 14));
        mateo = new Socio("mateo", "mateo123", "Mateo Ruiz", LocalDate.of(2012, 6, 1));
        valentina = new Socio("valentina", "vale123", "Valentina Cruz", LocalDate.of(2006, 4, 25));
        andres = new Socio("andres", "andres123", "Andres Lozano", LocalDate.of(2010, 8, 12));

        club.agregarUsuario(admin);
        club.agregarUsuario(entrenador1);
        club.agregarUsuario(entrenador2);
        club.agregarUsuario(entrenador3);
        club.agregarUsuario(fisio1);
        club.agregarUsuario(fisio2);
        club.agregarUsuario(sofia);
        club.agregarUsuario(mateo);
        club.agregarUsuario(valentina);
        club.agregarUsuario(andres);

        // Siete socios por plantilla: el minimo de habilitados del futbol
        for (int i = 1; i <= 7; i++)
        {
            Socio a = new Socio("jugA" + i, "clave", "Jugador A" + i, LocalDate.of(1998, 1, i));
            Socio b = new Socio("jugB" + i, "clave", "Jugador B" + i, LocalDate.of(1999, 1, i));
            plantillaA.add(a);
            plantillaB.add(b);
            club.agregarUsuario(a);
            club.agregarUsuario(b);
        }
    }

    private void crearDeportesDeConjunto()
    {
        // Orden de los tres ultimos datos: puntos por victoria, por empate y por derrota.
        // Futbol 3-1-0 lo dice el enunciado. Baloncesto y voleibol 2-0-1: supuesto A-3.
        futbol = new DeporteConjunto("Futbol", 7, true, 3, 1, 0);
        baloncesto = new DeporteConjunto("Baloncesto", 5, false, 2, 0, 1);
        voleibol = new DeporteConjunto("Voleibol", 6, false, 2, 0, 1);
        club.agregarDeporte(futbol);
        club.agregarDeporte(baloncesto);
        club.agregarDeporte(voleibol);

        // Rangos de edad: supuesto A-3.
        sub13 = new Categoria("Sub-13", 8, 12);
        sub15 = new Categoria("Sub-15", 13, 14);
        sub17 = new Categoria("Sub-17", 15, 16);
        mayores = new Categoria("Mayores", 17, 99);
        club.agregarCategoria(sub13);
        club.agregarCategoria(sub15);
        club.agregarCategoria(sub17);
        club.agregarCategoria(mayores);

        futbolMayoresA = new Equipo("Seneca Futbol Mayores A", 20, futbol, mayores);
        futbolMayoresB = new Equipo("Seneca Futbol Mayores B", 20, futbol, mayores);
        futbolSub15 = new Equipo("Seneca Futbol Sub-15", 18, futbol, sub15);
        baloncestoMayores = new Equipo("Seneca Baloncesto Mayores", 12, baloncesto, mayores);
        // Equipo con cupo 1, solo para poder probar el rechazo por cupo lleno con pocos pasos
        voleibolCupoUno = new Equipo("Seneca Voleibol Mayores (cupo 1)", 1, voleibol, mayores);
        club.agregarEquipo(futbolMayoresA);
        club.agregarEquipo(futbolMayoresB);
        club.agregarEquipo(futbolSub15);
        club.agregarEquipo(baloncestoMayores);
        club.agregarEquipo(voleibolCupoUno);

        torneoCerros = new CompetenciaLiga("Torneo de los Cerros", "Universidades de la ciudad", "2026", 11, futbol, mayores);
        ascun = new CompetenciaPorFases("Torneo ASCUN", "ASCUN", "2026", 11, futbol, mayores);
        club.agregarCompetencia(torneoCerros);
        club.agregarCompetencia(ascun);
    }

    private void crearDisciplinasIndividuales()
    {
        tenis = new DisciplinaIndividual("Tenis", true, 2);
        padel = new DisciplinaIndividual("Padel", true, 1);
        tenisDeMesa = new DisciplinaIndividual("Tenis de mesa", true, 1);
        club.agregarDisciplina(tenis);
        club.agregarDisciplina(padel);
        club.agregarDisciplina(tenisDeMesa);

        canchaTenis1 = new CanchaTenis("T1", "Cancha de tenis 1", 4, false, true);
        canchaTenis2 = new CanchaTenis("T2", "Cancha de tenis 2", 4, false, true);
        canchaPadel1 = new CanchaPadel("P1", "Cancha de padel 1", 4, true);
        mesaTechada = new MesaTenisMesa("M1", "Mesa techada", 4, true, true);
        mesaAireLibre = new MesaTenisMesa("M2", "Mesa al aire libre", 2, false, false);
        registrarInstalacion(tenis, canchaTenis1);
        registrarInstalacion(tenis, canchaTenis2);
        registrarInstalacion(padel, canchaPadel1);
        registrarInstalacion(tenisDeMesa, mesaTechada);
        registrarInstalacion(tenisDeMesa, mesaAireLibre);
    }

    private void registrarInstalacion(DisciplinaIndividual disciplina, Instalacion instalacion)
    {
        disciplina.agregarInstalacion(instalacion);
        club.agregarInstalacion(instalacion);
    }

    private void crearTiendaYCafeteria()
    {
        raqueta = new ArticuloDeportivo("A-01", "Raqueta de tenis", 320000, "Raquetas");
        balon = new ArticuloDeportivo("A-02", "Balon de futbol", 95000, "Balones");
        cafe = new Bebida("B-01", "Cafe", 4500, Temperatura.CALIENTE);
        jugo = new Bebida("B-02", "Jugo de naranja", 6000, Temperatura.FRIA);
        barraDeMani = new Snack("S-01", "Barra de mani", 5500);
        barraDeMani.agregarAlergeno(new Alergeno("Mani"));
        barraDeMani.agregarAlergeno(new Alergeno("Gluten"));
        club.agregarProducto(raqueta);
        club.agregarProducto(balon);
        club.agregarProducto(cafe);
        club.agregarProducto(jugo);
        club.agregarProducto(barraDeMani);

        bodega = new UbicacionInventario("Bodega", TipoUbicacion.BODEGA);
        tienda = new UbicacionInventario("Tienda", TipoUbicacion.TIENDA);
        cafeteria = new UbicacionInventario("Cafeteria", TipoUbicacion.CAFETERIA);
        club.agregarUbicacion(bodega);
        club.agregarUbicacion(tienda);
        club.agregarUbicacion(cafeteria);

        dejarExistencia(bodega, raqueta, 10);
        dejarExistencia(bodega, balon, 25);
        dejarExistencia(tienda, raqueta, 2);
        dejarExistencia(tienda, balon, 5);
        dejarExistencia(cafeteria, cafe, 50);
        dejarExistencia(cafeteria, jugo, 30);
        dejarExistencia(cafeteria, barraDeMani, 20);

        codigoEntrenador1 = new CodigoDescuento("DESC-ENT1", entrenador1);
        entrenador1.setCodigoDescuento(codigoEntrenador1);
    }

    private void dejarExistencia(UbicacionInventario ubicacion, Producto producto, int cantidad)
    {
        ubicacion.agregarExistencia(new Existencia(producto, cantidad));
    }

    private void crearTurnos()
    {
        // Todos los dias: entrenador 1 en la manana, entrenador 3 en la tarde y entrenador 2 todo el dia.
        // Asi siempre hay 2 entrenadores. Un fisioterapeuta en la manana y otro en la tarde.
        LocalTime apertura = LocalTime.of(6, 0);
        LocalTime mediodia = LocalTime.of(14, 0);
        LocalTime cierre = LocalTime.of(22, 0);
        for (DayOfWeek dia : DayOfWeek.values())
        {
            asignarTurno(entrenador1, dia, apertura, mediodia);
            asignarTurno(entrenador2, dia, apertura, cierre);
            asignarTurno(entrenador3, dia, mediodia, cierre);
            asignarTurno(fisio1, dia, apertura, mediodia);
            asignarTurno(fisio2, dia, mediodia, cierre);
        }
    }

    private void asignarTurno(Empleado empleado, DayOfWeek dia, LocalTime inicio, LocalTime fin)
    {
        Turno turno = new Turno(empleado, dia, inicio, fin);
        empleado.agregarTurno(turno);
        club.agregarTurno(turno);
    }
}
