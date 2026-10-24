TíoTec Preventivo

Aplicación académica Android de evaluación y orientación preventiva en seguridad digital. Proyecto de DSY1105, Desarrollo de Aplicaciones Móviles, Evaluación Parcial 2.

## Autor

- Nombre y apellido: COMPLETAR ANTES DE ENTREGAR.
- Número de equipo, si corresponde: COMPLETAR.
- GitHub: StatiX5893.
- Desarrollo individual.

## Objetivo

Ayudar a personas no especialistas a revisar hábitos de respaldo, actualizaciones, autenticación de dos factores y recuperación de cuentas, mediante perfiles y dispositivos ficticios.

La orientación se basa en respuestas del usuario. No se analiza automáticamente el dispositivo ni se realiza un diagnóstico de seguridad. No ingresar contraseñas, códigos, información personal ni incidentes reales.

## Funcionalidades implementadas

- Inicio con acceso al flujo de evaluación, plan e historial.
- Perfil ficticio con alias, nivel de experiencia y confirmación de datos ficticios.
- Registro de dispositivo de prueba con nombre, tipo y sistema operativo.
- Validaciones gestionadas desde ViewModels y mensajes visuales.
- Cuestionario de cuatro preguntas Sí/No con validación de respuestas completas.
- Resultados con cantidad de medidas activas y recomendaciones para respuestas No.
- Checklist preventivo con acciones pendientes/completadas y contador.
- Persistencia del plan y sus marcas mediante DataStore Preferences.
- Historial persistente con alias ficticio, dispositivo, fecha y resultado.
- Navegación mediante Navigation Compose y botones de regreso.
- AnimatedVisibility para mostrar y ocultar el error del cuestionario.
- Notificación preventiva inmediata de prueba, canal y solicitud de permiso en Android 13 o superior. Al tocarla, abre la app.
- Captura opcional de foto con la cámara del sistema y visualización dentro del formulario mediante FileProvider.

## Regla de orientación

Cada respuesta Sí suma una medida activa, de un total de cuatro:

| Medidas activas | Orientación |
| --- | --- |
| 4 | Mantén tus hábitos preventivos |
| 2 o 3 | Hay medidas por mejorar |
| 0 o 1 | Prioriza las medidas pendientes |

Cada respuesta No genera una recomendación. Es una regla simplificada del prototipo académico, no una escala de riesgo validada.

## Tecnologías y arquitectura

Kotlin, Android Studio, Jetpack Compose, Material 3, MVVM, StateFlow, corrutinas, Navigation Compose y DataStore Preferences con serialización JSON. Recursos nativos mediante Activity Result API, FileProvider y NotificationCompat.

- Android mínimo configurado: API 24.
- SDK de compilación y destino: API 37.
- Pruebas de desarrollo en emulador API 37.

Paquetes bajo `app/src/main/java/com/example/tiotecpreventivo/`:

```text
model/          Datos de formularios, errores, acciones y evaluaciones
viewmodel/      Estado, validaciones y coordinación con repositorios
repository/     Persistencia del plan y del historial
ui/screens/     Pantallas Compose
ui/theme/       Tema, colores y tipografía
navigation/     Destinos y navegación
notifications/  Canal y aviso preventivo
camera/         Preparación del archivo y Uri de la foto
MainActivity.kt Punto de entrada y creación de ViewModels
```

La pantalla observa el estado y envía acciones al ViewModel. Los repositorios encapsulan el almacenamiento local.

## Cómo ejecutar

1. Clonar o descargar https://github.com/StatiX5893/TioTecPreventivo.
2. Abrir en Android Studio la carpeta que contiene `settings.gradle.kts` y `gradlew`.
3. Instalar los componentes de SDK solicitados y sincronizar Gradle. La descarga inicial de dependencias requiere internet.
4. Seleccionar un emulador o dispositivo compatible.
5. Ejecutar la configuración `app` con Run.

Las funciones implementadas no necesitan servidor ni inicio de sesión.

Para compilar desde PowerShell en la raíz del proyecto:

```powershell
.\gradlew.bat assembleDebug
```

El APK de depuración se genera normalmente en `app/build/outputs/apk/debug/app-debug.apk`.

## Recorrido de prueba

1. Pulsar Comenzar evaluación.
2. Ingresar un alias ficticio como UsuarioDemo, seleccionar nivel y confirmar uso de datos ficticios.
3. Registrar EquipoDemo, tipo y sistema operativo. La foto es opcional.
4. Responder el cuestionario y abrir el resultado.
5. Regresar al inicio y abrir el plan. Marcar acciones y observar el contador.
6. Consultar el historial.
7. Detener y volver a ejecutar la aplicación sin desinstalarla. Comprobar que el plan y el historial permanecen.
8. Desde el plan, probar el aviso preventivo y revisar el panel de notificaciones.
9. En el dispositivo, tomar y aceptar una foto de prueba; comprobar su visualización.

Cada pulsación válida de Ver resultado añade un registro al historial, incluso con respuestas repetidas.

## Recursos nativos y permisos

- POST_NOTIFICATIONS: autorización solicitada cuando corresponde en Android 13 o superior. El usuario puede rechazarla y seguir usando el plan.
- La cámara se abre mediante una aplicación externa del sistema; no se solicita CAMERA porque no hay acceso directo al sensor.
- FileProvider comparte la Uri del archivo y restringe las rutas a la subcarpeta privada `fotos/`.
- La fotografía es opcional y no se incorpora al historial.
- La notificación implementada es inmediata y manual; no es un recordatorio programado.
- INTERNET está declarado como preparación para la integración de API pendiente.

## Comprobaciones manuales realizadas

- Compilación y ejecución en emulador.
- Mensajes de error del perfil y dispositivo.
- Navegación y conservación de estado durante el recorrido.
- Cuatro respuestas Sí: 4 de 4 medidas, sin recomendaciones pendientes.
- Cuatro respuestas No: 0 de 4 medidas, con cuatro recomendaciones.
- Casillas y contador del plan.
- Recuperación del plan e historial tras detener y ejecutar nuevamente.
- Mensaje animado de validación del cuestionario.
- Envío y visualización de notificación.
- Captura y visualización de foto dentro de la app.

Estas comprobaciones son manuales; no constituyen una suite automatizada.

## Limitaciones y pendientes

- API REST todavía no consumida desde la app. Se preparó un recurso de consejos educativos en MockAPI, pero su integración no está terminada.
- Pantalla de contenido educativo remoto y solicitudes de asistencia simuladas pendientes.
- Adaptabilidad con Window Size Classes pendiente. El desplazamiento vertical de formularios no sustituye diseños adaptativos.
- Recordatorios programados pendientes; existe un aviso inmediato de prueba.
- Perfil y formulario del dispositivo no tienen persistencia independiente. El historial conserva el alias y nombre del dispositivo de cada evaluación.
- El plan corresponde a la última evaluación, sin separación por dispositivo. Conserva marcas de acciones con el mismo identificador al regenerarse.
- La foto no está integrada al historial ni a un catálogo persistente de dispositivos.
- El cuestionario usa un error general; no todas las validaciones tienen iconos por campo.
- Roles de administración y supervisión de contenidos no implementados.
- Pendiente ampliar pruebas, manejo de fallos y accesibilidad.

## Repositorio y planificación

- GitHub: https://github.com/StatiX5893/TioTecPreventivo
- Trello: (https://trello.com/b/s5sFOZag/tiotec-preventivo)

Los commits y el tablero registran avances reales. Las tareas pendientes no se presentan como funcionalidades terminadas.
