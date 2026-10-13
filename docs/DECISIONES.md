# DECISIONES DE DISEÑO

Materia prima de la sección de justificaciones del documento de diseño. Cada uno debe poder defender cualquiera.

## CAMBIOS FRENTE AL MODELO DE ANÁLISIS

| # | Qué cambia | Por qué |
|---|---|---|
| D-01 | `Club` es la raíz del estado | Sin raíz no hay qué guardar ni qué cargar |
| D-02 | `Usuario` gana `nombre` y `fechaNacimiento`; los puntos viven solo en `Usuario` | RN-04 necesita la edad; compran socios y empleados |
| D-03 | `local` y `visitante` bajan a `PartidoInterno` | En competencia y práctica serían campos vacíos |
| D-04 | `Partido.puedeParticipar` es abstracto | Cada tipo de partido decide quién juega |
| D-05 | `Competencia.registrarResultado` es abstracto | Justifica las dos subclases: liga y por fases |
| D-06 | `jugado` en `Partido`; `cerrada` en `SesionEntrenamiento` y `Venta` | Rechazar cambios sobre algo cerrado |
| D-07 | `InscripcionEquipo.amarillasAcumuladas` | Las amarillas se acumulan entre partidos |
| D-08 | Una instalación puede tener muchas sesiones | En el análisis la relación estaba 1 a 1 |
| D-09 | `Venta.fecha` con hora | RN-32 cruza la hora de la venta con la reserva |
| D-10 | `Club` gana horario de atención | RN-37 exige cobertura en ese horario |
| D-11 | El actor entra por tipo: `crearEquipo(Administrador, ...)` | El compilador verifica el permiso |
| D-12 | `Instalacion.admite` y `prohibeBebidaCaliente` abstractos | RN-24 y RN-32 sin `if` por tipo |
| D-13 | `ReglaDominioException` lleva el código de la regla | La consola muestra qué regla rechazó |
| D-14 | `getDelta` y `getSubtotal` se calculan | Datos derivados no se guardan |
| D-15 | Reportes en `GeneradorReportes` | Son consultas, no entidades |
| D-16 | Nombres corregidos frente a los diagramas | En Java el nombre queda para siempre |
| D-17 | `Partido.despuesDeCerrar` y `EstadisticaPartido.aplicarTarjetas` | Al cerrar: primero se descuentan sanciones, después se aplican tarjetas; si no, la roja de hoy se cumple hoy |
| D-18 | `Competencia.obtenerFila` crea tabla y fila al primer resultado | Nadie precrea tablas; la búsqueda no se repite |
| D-19 | `Venta.calcularCargosAdicionales` (0); la cafetería suma propina | `cerrar` vive en una sola clase |
| D-20 | `Producto.esBebidaCaliente` y `getAlergenos` con valor por defecto | Ventas y alérgenos sin `instanceof` |

## SUPUESTOS DECLARADOS

| # | Tema | Decisión |
|---|---|---|
| A-1 | Niveles adyacentes | Diferencia de `ordinal()` ≤ 1; si alguno es entrenador, cualquier combinación. El ejemplo del enunciado no es simétrico: preguntar en el foro |
| A-2 | Persistencia | Serialización: un archivo `datos/club.dat` con el `Club` |
| A-3 | Puntos y categorías | Fútbol 3-1-0; baloncesto y voleibol 2 por victoria, 1 por derrota. Sub-13: 8-12, Sub-15: 13-14, Sub-17: 15-16, Mayores: 17+ |
| A-4 | Ranking | ELO, K = 32, inicial 1000; en dobles, promedio por lado |
| A-5 | Bebida caliente | Solo se controla el despacho en la cafetería |
| A-6 | Tabla de posiciones | Solo filas de equipos del club |
| A-7 | Sanción | Vive en `InscripcionEquipo`; se cumple en cualquier partido de ese equipo, de 1 partido (`PARTIDOS_POR_SANCION`) |
| A-8 | Partidos de competencia | El club siempre es el local en el marcador |
