package com.mycompany.alumnos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class java {
    private final Path filePath;

    public java(String fileName) {
        this.filePath = Path.of(fileName);
        inicializarArchivo();
    }

    // Verifica si el archivo existe; si no, lo crea automáticamente
    private void inicializarArchivo() {
        try {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            System.err.println("Error al inicializar el archivo: " + e.getMessage());
        }
    }

    // C: CREATE - Agregar alumno
    public boolean agregarAlumno(Alumno alumno) throws IOException {
        if (existeId(alumno.getId())) {
            return false; // El ID ya existe
        }
        String linea = alumno.toFileFormat() + System.lineSeparator();
        Files.writeString(filePath, linea, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        return true;
    }

    // R: READ ALL - Leer todos los alumnos
    public List<Alumno> obtenerTodos() throws IOException {
        List<String> lineas = Files.readAllLines(filePath);
        List<Alumno> alumnos = new ArrayList<>();

        for (String linea : lineas) {
            if (!linea.isBlank()) {
                Alumno alumno = Alumno.fromFileFormat(linea);
                if (alumno != null) {
                    alumnos.add(alumno);
                }
            }
        }
        return alumnos;
    }

    // R: READ BY ID - Buscar alumno por ID
    public Alumno buscarPorId(String id) throws IOException {
        List<Alumno> alumnos = obtenerTodos();
        for (Alumno alumno : alumnos) {
            if (alumno.getId().equalsIgnoreCase(id.trim())) {
                return alumno;
            }
        }
        return null;
    }

    // U: UPDATE - Actualizar nombre de alumno
    public boolean actualizarAlumno(String id, String nuevoNombre) throws IOException {
        List<Alumno> alumnos = obtenerTodos();
        boolean encontrado = false;

        List<String> nuevasLineas = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            if (alumno.getId().equalsIgnoreCase(id.trim())) {
                alumno.setNombre(nuevoNombre);
                encontrado = true;
            }
            nuevasLineas.add(alumno.toFileFormat());
        }

        if (encontrado) {
            Files.write(filePath, nuevasLineas);
        }
        return encontrado;
    }

    // D: DELETE - Eliminar alumno
    public boolean eliminarAlumno(String id) throws IOException {
        List<Alumno> alumnos = obtenerTodos();
        List<String> nuevasLineas = new ArrayList<>();
        boolean encontrado = false;

        for (Alumno alumno : alumnos) {
            if (alumno.getId().equalsIgnoreCase(id.trim())) {
                encontrado = true;
            } else {
                nuevasLineas.add(alumno.toFileFormat());
            }
        }

        if (encontrado) {
            Files.write(filePath, nuevasLineas);
        }
        return encontrado;
    }

    // Método auxiliar para validar duplicados
    public boolean existeId(String id) throws IOException {
        return buscarPorId(id) != null;
    }
}