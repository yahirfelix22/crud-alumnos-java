package com.mycompany.alumnos;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que gestiona la interfaz de usuario mediante consola.
 * Controla el flujo del menu interactivo y las operaciones CRUD sobre los alumnos.
 */
public class Main {
    // Objeto Scanner para capturar los datos ingresados por el usuario por teclado
    private static final Scanner sc = new Scanner(System.in);

    // Instancia del repositorio que maneja la lectura y escritura con Java NIO
    private static final java repo = new java("alumnos.txt");

    public static void main(String[] args) {
        int op = 0; // Variable que almacenara la opcion seleccionada por el usuario

        // Ciclo do-while: Muestra el menu al menos una vez y se repite mientras op != 6
        do {
            // Despliegue del menu de opciones en consola
            System.out.println("\n=== MENU GESTION DE ALUMNOS (JAVA NIO) ===");
            System.out.println("1. Registrar nuevo alumno\n2. Ver todos los alumnos\n3. Buscar alumno por ID\n4. Actualizar nombre\n5. Eliminar alumno\n6. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                // Lee el texto del usuario, elimina espacios (.trim()) y lo convierte a numero entero
                op = Integer.parseInt(sc.nextLine().trim());
                System.out.println();

                // Evalua la opcion seleccionada y ejecuta el metodo correspondiente
                switch (op) {
                    case 1 -> registrar();
                    case 2 -> verTodos();
                    case 3 -> buscar();
                    case 4 -> actualizar();
                    case 5 -> eliminar();
                    case 6 -> System.out.println("Hasta luego! Programa finalizado.");
                    default -> System.out.println("Opcion invalida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                // Captura el error si el usuario escribe letras en lugar de numeros en el menu
                System.out.println("Error: Debe ingresar un numero entero valido.");
            } catch (IOException e) {
                // Captura fallos de lectura/escritura con la API de Java NIO
                System.out.println("Error en el archivo: " + e.getMessage());
            }
        } while (op != 6); // Evalua si la opcion elegida es diferente de 6 para volver a repetir
    }

    /**
     * Opcion 1: Solicita ID y Nombre para crear un nuevo registro en el archivo.
     */
    private static void registrar() throws IOException {
        System.out.println("--- Registrar Alumno ---");
        System.out.print("Ingrese ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Ingrese Nombre: ");
        String nombre = sc.nextLine().trim();

        // Validar que los campos no esten vacios
        if (id.isEmpty() || nombre.isEmpty()) {
            System.out.println("Error: El ID y el Nombre no pueden estar vacios.");
            return;
        }

        // Intenta agregar al alumno; el metodo devuelve true si no habia duplicado de ID
        if (repo.agregarAlumno(new Alumno(id, nombre))) {
            System.out.println("Alumno registrado con exito!");
        } else {
            System.out.println("Error: El ID '" + id + "' ya existe en el sistema.");
        }
    }

    /**
     * Opcion 2: Muestra la lista completa de alumnos almacenados en alumnos.txt.
     */
    private static void verTodos() throws IOException {
        System.out.println("--- Lista de Alumnos ---");
        List<Alumno> alumnos = repo.obtenerTodos();

        // Comprueba si la lista obtenida esta vacia
        if (alumnos.isEmpty()) {
            System.out.println("No hay registros en el archivo alumnos.txt.");
        } else {
            // Imprime cada alumno de la lista utilizando la referencia de metodo System.out::println
            alumnos.forEach(System.out::println);
        }
    }

    /**
     * Opcion 3: Busca y muestra un alumno especifico mediante su ID.
     */
    private static void buscar() throws IOException {
        System.out.println("--- Buscar Alumno ---");
        System.out.print("Ingrese ID a buscar: ");
        String id = sc.nextLine().trim();

        Alumno a = repo.buscarPorId(id);
        // Uso de operador ternario para imprimir la informacion si existe o un mensaje de error si no
        System.out.println(a != null ? "Alumno encontrado: " + a : "No se encontro ningun alumno con el ID: " + id);
    }

    /**
     * Opcion 4: Modifica el nombre de un alumno existente buscando su ID.
     */
    private static void actualizar() throws IOException {
        System.out.println("--- Actualizar Alumno ---");
        System.out.print("Ingrese ID a modificar: ");
        String id = sc.nextLine().trim();

        // Verifica si el alumno existe antes de pedir el nuevo nombre
        if (!repo.existeId(id)) {
            System.out.println("Error: No existe el alumno con ID '" + id + "'.");
            return;
        }

        System.out.print("Ingrese el nuevo Nombre: ");
        String nuevoNombre = sc.nextLine().trim();
        if (nuevoNombre.isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacio.");
            return;
        }

        repo.actualizarAlumno(id, nuevoNombre);
        System.out.println("Nombre actualizado correctamente!");
    }

    /**
     * Opcion 5: Elimina el registro de un alumno del archivo segun su ID.
     */
    private static void eliminar() throws IOException {
        System.out.println("--- Eliminar Alumno ---");
        System.out.print("Ingrese ID a eliminar: ");
        String id = sc.nextLine().trim();

        // Llama al repositorio para borrar la linea correspondiente
        if (repo.eliminarAlumno(id)) {
            System.out.println("Alumno eliminado con exito!");
        } else {
            System.out.println("Error: No existe ningun alumno con el ID '" + id + "'.");
        }
    }
}