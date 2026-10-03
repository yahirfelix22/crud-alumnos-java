# Sistema de Gestion de Alumnos (Java NIO)

Aplicacion de consola en Java para la gestion de alumnos (operaciones CRUD) con almacenamiento persistente en un archivo de texto plano (`alumnos.txt`) utilizando las clases del paquete `java.nio.file`.

## Requisitos y Tecnologias
* Java JDK 17 o superior
* Java NIO (`java.nio.file.Path`, `java.nio.file.Files`)
* Entorno de desarrollo (NetBeans, IntelliJ, Eclipse) o consola de comandos

## Funcionalidades del Sistema
* **Registrar alumno:** Permite ingresar ID y nombre completo. Valida que el ID no este duplicado antes de guardar.
* **Ver todos los alumnos:** Muestra la lista completa de registros guardados en `alumnos.txt`.
* **Buscar por ID:** Consulta y despliega la informacion de un alumno especifico.
* **Actualizar nombre:** Permite modificar el nombre de un alumno existente buscando por su ID.
* **Eliminar alumno:** Remueve la linea del registro del archivo `.txt` segun el ID especificado.
* **Manejo automatico de archivo:** Si `alumnos.txt` no existe al iniciar la aplicacion, se crea automaticamente.

## Formato del Archivo
Los registros se guardan en el archivo `alumnos.txt` respetando estrictamente la siguiente estructura:

```text
ID_Alumno - NombreAlumno
