package com.mycompany.alumnos;

public class Alumno {
    private String id;
    private String nombre;

    public Alumno(String id, String nombre) {
        this.id = id.trim();
        this.nombre = nombre.trim();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre.trim();
    }

    // Convierte el objeto al formato requerido para el archivo txt
    public String toFileFormat() {
        return id + " - " + nombre;
    }

    // Crea un objeto Alumno a partir de una línea del archivo
    public static Alumno fromFileFormat(String line) {
        if (line == null || !line.contains(" - ")) {
            return null;
        }
        String[] partes = line.split(" - ", 2);
        return new Alumno(partes[0].trim(), partes[1].trim());
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre;
    }
}