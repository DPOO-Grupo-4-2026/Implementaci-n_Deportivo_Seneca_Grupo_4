# Club Deportivo Séneca · Grupo 4

Proyecto 1, Entrega 2 de ISIS-1226 (DPOO). Proyecto de Eclipse en `ClubDeportivoSeneca/`.

## Estado

| Parte | Estado |
|---|---|
| Modelo: clases, atributos, constructores, getters | Completo |
| Bloque A: usuarios y deportes de conjunto | **Implementado**: 26 métodos, 30 escenarios de prueba en verde |
| Bloque B: competencias, individuales y entrenamiento | Pendiente |
| Bloque C: tienda, cafetería y administración | Pendiente |
| Persistencia | Pendiente (al final) |

El detalle por método está en [`docs/PENDIENTES.md`](docs/PENDIENTES.md).

## Abrir en Eclipse

1. Clonar el repositorio.
2. **File → Import → General → Existing Projects into Workspace**, escoger la carpeta `ClubDeportivoSeneca` del repositorio clonado. **No marcar "Copy projects into workspace".**
3. Correr `ConsolaSocio`, `ConsolaEmpleado` o `ConsolaAdministrador` (paquete `uniandes.dpoo.cds.consola`) con **Run As → Java Application**.

Cada escenario imprime lo esperado y lo obtenido. Lo que aún no está implementado dice `Pendiente [RN-xx]: Clase.metodo`.

## Estructura

```
ClubDeportivoSeneca/src/uniandes/dpoo/cds/
├── modelo/usuarios/         Usuario, Socio, Empleado, Entrenador, Fisioterapeuta, Administrador, PerfilJugador
├── modelo/conjunto/         Equipo, Categoria, InscripcionEquipo, Sancion, Partido, PartidoInterno, estadísticas
├── modelo/competencias/     Competencia (Liga, PorFases), nómina, tabla de posiciones
├── modelo/individuales/     Disciplinas, instalaciones, reservas, partidos de práctica, ranking
├── modelo/entrenamiento/    Sesiones y asistencia
├── modelo/tienda/           Productos, inventario, ventas, puntos, descuentos
├── modelo/administracion/   Club (raíz del estado), turnos, reportes
├── excepciones/             ReglaDominioException y sus subclases
├── persistencia/            PersistenciaClub
└── consola/                 Programas de prueba y datos de prueba
```

## Cómo trabajamos

- Rama por bloque: `bloque-a`, `bloque-b`, `bloque-c`. Merge a `main` por pull request.
- Un commit por método, con la regla en el mensaje: `RN-05 Equipo.inscribirJugador: un equipo por deporte`.
- Quien revisa el PR debe poder explicar el método.
- Nadie cambia la firma de un método de otro bloque sin abrir un issue.
- No aplicar Format (Ctrl+Shift+F) a archivos de otro bloque.
- Las decisiones nuevas van en [`docs/DECISIONES.md`](docs/DECISIONES.md).

## Uso de IA

El esqueleto del modelo, los datos y programas de prueba, y la implementación del Bloque A se hicieron con IA a partir del análisis del grupo. Va declarado con los prompts en el anexo de IAG del documento de diseño.
