# PENDIENTES

Tablero de trabajo. Cada fila es un método del modelo. Al terminar uno, marquen la casilla en el mismo commit.

| Bloque | Hechos | Total |
|---|---|---|
| Bloque A - Usuarios y deportes de conjunto | 26 | 26 |
| Bloque B - Competencias, individuales y entrenamiento | 0 | 31 |
| Bloque C - Tienda, cafeteria y administracion | 0 | 28 |
| Persistencia | 0 | 2 |

## BLOQUE A - USUARIOS Y DEPORTES DE CONJUNTO

| Hecho | Clase | Método | Reglas | Qué hace |
|---|---|---|---|---|
| [x] | `Usuario` | `validarPassword` | RN-01 | Dice si la clave digitada coincide con la del usuario. |
| [x] | `Usuario` | `calcularEdad` | RN-04 | Edad cumplida del usuario en la fecha dada. |
| [x] | `Usuario` | `crearPerfilJugador` | RN-38 | Crea el perfil de jugador del usuario y lo deja asociado. |
| [x] | `Administrador` | `crearPerfilJugador` | RN-38 | El administrador no puede jugar ni reservar: siempre rechaza. |
| [x] | `PerfilJugador` | `buscarInscripcionEnDeporte` | RN-05 | Devuelve la inscripcion del jugador en un equipo activo de ese deporte, o null si no tiene. |
| [x] | `Categoria` | `admiteEdad` | RN-04 | Dice si la edad cae dentro del rango de la categoria. |
| [x] | `Equipo` | `inscribirJugador` | RN-03, RN-04, RN-05, RN-06, RN-35 | Inscribe al jugador en el equipo y devuelve la inscripcion creada. |
| [x] | `Equipo` | `tieneCamiseta` | RN-06 | Dice si algun inscrito ya usa ese numero. |
| [x] | `Equipo` | `contarHabilitados` | RN-14 | Numero de inscritos sin sancion vigente. |
| [x] | `Equipo` | `puedeDisputarPartido` | RN-09 | Dice si el equipo esta activo y alcanza el minimo de habilitados de su deporte. |
| [x] | `Equipo` | `descontarSancionesTrasPartido` | RN-11, RN-12 | Tras jugar un partido, descuenta un partido a cada sancion vigente del equipo. |
| [x] | `InscripcionEquipo` | `estaHabilitado` | RN-12, RN-14 | Dice si el jugador no tiene ninguna sancion con partidos pendientes. |
| [x] | `InscripcionEquipo` | `registrarTarjetas` | RN-11, RN-13 | Acumula las tarjetas de un partido y, si corresponde, crea la sancion. Devuelve la sancion o null. |
| [x] | `Sancion` | `descontarPartido` | RN-11 | Resta un partido pendiente y marca la sancion como cumplida al llegar a cero. |
| [x] | `Partido` | `registrarEstadistica` | RN-10, RN-12 | Agrega la actuacion de un jugador en este partido. |
| [x] | `Partido` | `cerrar` | RN-19 | Registra el marcador final, marca el partido como jugado y llama a despuesDeCerrar. |
| [x] | `PartidoInterno` | `puedeParticipar` | RN-12 | Inscrito en alguno de los dos equipos y sin sancion vigente. |
| [x] | `PartidoInterno` | `despuesDeCerrar` | RN-11, RN-13 | Primero descuenta las sanciones que este partido cumplio; despues aplica las tarjetas de hoy. |
| [x] | `EstadisticaFutbol` | `aplicarTarjetas` | RN-11, RN-13 | Registra en la inscripcion las amarillas y rojas de este partido. |
| [x] | `Club` | `autenticar` | RN-01 | Devuelve el usuario si el login existe, esta activo y la clave coincide. |
| [x] | `Club` | `buscarUsuario` | RN-01 | Devuelve el usuario con ese login, o null. |
| [x] | `Club` | `crearEquipo` | RN-02, RN-03, RN-35 | Crea un equipo. Recibe un Administrador: el tipo del parametro es el que impide que otro rol lo haga. |
| [x] | `Club` | `darDeBajaEquipo` | RN-35 | Desactiva el equipo sin borrar su historia. |
| [x] | `Club` | `darDeBajaJugador` | RN-35 | Desactiva el perfil del jugador sin borrar su historia. |
| [x] | `Club` | `programarPartidoInterno` | RN-08, RN-09 | Crea un partido interno y lo deja en la lista de partidos. |
| [x] | `GeneradorReportes` | `resultadosDeEquipo` | RN-21 | Partidos ya jugados por el equipo, internos y de competencia. |

## BLOQUE B - COMPETENCIAS, INDIVIDUALES Y ENTRENAMIENTO

| Hecho | Clase | Método | Reglas | Qué hace |
|---|---|---|---|---|
| [ ] | `PerfilJugador` | `esEntrenador` | RN-23 | Dice si el usuario detras del perfil es un entrenador. |
| [ ] | `Competencia` | `inscribirEquipo` | RN-15, RN-16 | Inscribe un equipo del club con su nomina y devuelve la inscripcion. |
| [ ] | `Competencia` | `buscarInscripcion` | RN-17 | Devuelve la inscripcion de ese equipo, o null. |
| [ ] | `Competencia` | `obtenerFila` | RN-21 | Busca la tabla por nombre y la fila del equipo, y crea la que falte (D-18). |
| [ ] | `CompetenciaLiga` | `registrarResultado` | RN-18, RN-19, RN-21 | Actualiza la tabla General con el puntaje del deporte. |
| [ ] | `CompetenciaPorFases` | `registrarResultado` | RN-18, RN-20, RN-21 | Si la fase empieza por Grupo, actualiza la tabla de ese grupo; en eliminacion solo cierra el partido. |
| [ ] | `NominaCompetencia` | `contiene` | RN-17 | Dice si el jugador tiene renglon en esta nomina. |
| [ ] | `PartidoCompetencia` | `puedeParticipar` | RN-12, RN-17 | En la nomina de la competencia y sin sancion vigente. |
| [ ] | `PartidoCompetencia` | `despuesDeCerrar` | RN-11, RN-13 | Igual que en PartidoInterno, pero con el unico equipo del club. |
| [ ] | `TablaPosiciones` | `buscarFila` | RN-21 | Devuelve la fila de ese equipo, o null. |
| [ ] | `FilaTabla` | `sumarResultado` | RN-18 | Actualiza jugados, ganados, empatados, perdidos, marcador y puntos segun el puntaje del deporte. |
| [ ] | `Nivel` | `puedeJugarContra` | RN-23 | Dice si dos jugadores de estos niveles pueden programar un partido de practica. |
| [ ] | `DisciplinaIndividual` | `inscribir` | RN-22 | Inscribe al jugador en la disciplina con su nivel y ranking inicial 1000. |
| [ ] | `DisciplinaIndividual` | `contarInstalacionesOcupadas` | RN-25 | Cuantas instalaciones distintas de la disciplina tienen una reserva que se cruza con esa franja. |
| [ ] | `DisciplinaIndividual` | `reservar` | RN-24, RN-25, RN-38 | Crea la reserva si cumple las reglas. El solicitante es Socio: un administrador no puede pasarse aqui. |
| [ ] | `DisciplinaIndividual` | `programarPartidoPractica` | RN-23, RN-24 | Crea el partido de practica sobre una reserva. |
| [ ] | `InscripcionDisciplina` | `ajustarRanking` | RN-26 | Cambia el puntaje y deja el AjusteRanking con el antes y el despues. |
| [ ] | `CanchaTenis` | `admite` | RN-24 | Sencillos con 2 si los admite, o dobles con 4. |
| [ ] | `CanchaTenis` | `prohibeBebidaCaliente` | RN-32 | El enunciado no restringe la cancha de tenis. |
| [ ] | `CanchaPadel` | `admite` | RN-24 | Solo dobles con 4. |
| [ ] | `CanchaPadel` | `prohibeBebidaCaliente` | RN-32 | En cancha de padel nunca se permite. |
| [ ] | `MesaTenisMesa` | `admite` | RN-24 | Sencillos con 2, o dobles con 4 si la mesa lo admite. |
| [ ] | `MesaTenisMesa` | `prohibeBebidaCaliente` | RN-32 | Solo si la mesa es techada. |
| [ ] | `Reserva` | `seCruzaCon` | RN-25 | Dice si la reserva ocupa algun momento de esa franja. Intervalos semiabiertos: 10-11 y 11-12 no se cruzan. |
| [ ] | `PartidoPractica` | `puedeParticipar` | RN-23 | Solo quienes estan entre los participantes del partido. |
| [ ] | `PartidoPractica` | `cerrarYActualizarRanking` | RN-26 | Cierra el partido y ajusta el ranking de cada participante con ELO (A-4). |
| [ ] | `SesionEntrenamiento` | `convocar` | RN-27 | Convoca al jugador: crea su registro, aun sin estado. |
| [ ] | `SesionEntrenamiento` | `registrarAsistencia` | RN-27 | Marca la asistencia de un convocado; la nota y el comentario son opcionales (null). |
| [ ] | `SesionEntrenamiento` | `cerrar` | RN-27 | Cierra la sesion: despues no se puede cambiar la asistencia. |
| [ ] | `GeneradorReportes` | `asistenciaDeJugador` | RN-27 | Cuantas veces asistio, falto o llego tarde el jugador en el periodo. |
| [ ] | `GeneradorReportes` | `evolucionDeRanking` | RN-26 | Historico de ajustes de ranking, en orden de fecha. |

## BLOQUE C - TIENDA, CAFETERIA Y ADMINISTRACION

| Hecho | Clase | Método | Reglas | Qué hace |
|---|---|---|---|---|
| [ ] | `Usuario` | `acumularPuntos` | RN-31 | Suma puntos al saldo y deja el MovimientoPuntos de tipo ACUMULACION. |
| [ ] | `Usuario` | `redimirPuntos` | RN-31 | Resta puntos del saldo y deja el MovimientoPuntos de tipo REDENCION. |
| [ ] | `Reserva` | `estaEnCurso` | RN-32 | Dice si el momento cae dentro de la reserva. |
| [ ] | `Bebida` | `esBebidaCaliente` | RN-32 | Dice si la bebida es caliente. |
| [ ] | `UbicacionInventario` | `buscarExistencia` | RN-35 | Devuelve la existencia de ese producto aqui, o null. |
| [ ] | `UbicacionInventario` | `ingresar` | RN-35 | Suma unidades; crea la existencia si no habia. |
| [ ] | `UbicacionInventario` | `retirar` | RN-35 | Resta unidades de la existencia. |
| [ ] | `Venta` | `agregarLinea` | RN-28, RN-29 | Agrega un renglon con el precio actual del producto. |
| [ ] | `Venta` | `aplicarCodigoDescuento` | RN-30 | Asocia el codigo de descuento a la venta. |
| [ ] | `Venta` | `cerrar` | RN-30, RN-31, RN-34 | Calcula subtotal, descuento, impuestos y total; redime y acumula puntos; deja la venta cerrada. |
| [ ] | `VentaTienda` | `admiteProducto` | RN-28 | Solo articulos deportivos. |
| [ ] | `VentaTienda` | `calcularImpuestos` | RN-28 | IVA del 19% sobre la base. |
| [ ] | `VentaCafeteria` | `admiteProducto` | RN-29 | Solo productos de cafeteria. |
| [ ] | `VentaCafeteria` | `calcularImpuestos` | RN-29 | Impuesto al consumo del 8% sobre la base. |
| [ ] | `VentaCafeteria` | `calcularPropina` | RN-29 | Propina segun el porcentaje, que el socio puede cambiar. |
| [ ] | `VentaCafeteria` | `calcularCargosAdicionales` | RN-29 | En la cafeteria el cargo adicional es la propina. |
| [ ] | `VentaCafeteria` | `consultarAlergenos` | RN-33 | Alergenos de todos los productos de la venta, sin repetir, para informarlos antes de cerrar. |
| [ ] | `CodigoDescuento` | `porcentajePara` | RN-30 | 15% si compra el dueno del codigo; 8% si compra otro. |
| [ ] | `SugerenciaProducto` | `aprobar` | RN-36 | Deja la sugerencia aprobada y registra quien la resolvio. |
| [ ] | `SugerenciaProducto` | `rechazar` | RN-36 | Deja la sugerencia rechazada y registra quien la resolvio. |
| [ ] | `Turno` | `cubre` | RN-37 | Dice si el turno incluye ese dia y esa hora: [inicio, fin). |
| [ ] | `Club` | `puedeRecibirBebidaCaliente` | RN-32 | Dice si el usuario no tiene en ese momento una reserva en una instalacion que prohibe bebida caliente. |
| [ ] | `Club` | `registrarVenta` | RN-32, RN-34 | Cierra la venta, descuenta el inventario de la ubicacion y la deja en el historial. |
| [ ] | `Club` | `moverProducto` | RN-35 | Traslada unidades entre ubicaciones y deja el MovimientoInventario. |
| [ ] | `Club` | `reabastecer` | RN-35 | Ingresa unidades nuevas a una ubicacion y deja el MovimientoInventario. |
| [ ] | `Club` | `hayCobertura` | RN-37 | Dice si a esa hora hay al menos el minimo de entrenadores y de fisioterapeutas con turno. |
| [ ] | `Club` | `resolverCambioTurno` | RN-37 | Aprueba o rechaza la solicitud. Si aprueba, el turno pasa al reemplazo. |
| [ ] | `GeneradorReportes` | `ventasPorRubro` | RN-34 | Total de tienda, cafeteria y mensualidades en el periodo. |

## PERSISTENCIA (LOS TRES, AL FINAL)

| Hecho | Clase | Método | Qué hace |
|---|---|---|---|
| [ ] | `PersistenciaClub` | `guardar` | Serializa el `Club` en `datos/club.dat` |
| [ ] | `PersistenciaClub` | `cargar` | Reconstruye el `Club` desde `datos/club.dat` |
