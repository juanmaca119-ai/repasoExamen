

/* 1. Gestión de directorios y propiedades de ficheros
(GestionArchivosFile.java)
Esta clase reúne la creación de carpetas y archivos,
la consulta de propiedades, el listado de un directorio
y el borrado mediante la clase File y la API Path/Files */



package REPASO_EXAMEN;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GestionArchivosFile {

    // Declaración de constantes para rutas y nombres de ficheros
    private static final String RUTA_FICHERO = "datos/";
    private static final String NOMBRE_FICHERO = "recetas.txt";
    private static final String NOMBRE_RENOMBRADO = "recetas_nuevo.txt";

    public static void main(String[] args) {
        crearDirectorioYFichero();
        consultarPropiedades();
        listarContenidoDirectorio();
        comprobarConPathYFiles();
        renombrarYEliminar();
    }

    // 1. Crear directorios y archivos vacíos
    public static void crearDirectorioYFichero() {
        File directorio = new File(RUTA_FICHERO);

        // mkdir() crea un directorio; mkdirs() crea también los intermedios si faltan
        if (directorio.mkdirs()) {
            System.out.println("Directorio creado correctamente.");
        } else {
            System.out.println("El directorio ya existe o no se pudo crear.");
        }

        // Creamos el objeto File indicando el directorio padre y el nombre
        File fichero = new File(directorio, NOMBRE_FICHERO);

        try {
            // createNewFile() devuelve true si lo crea, o false si ya existía
            if (fichero.createNewFile()) {
                System.out.println("Se ha creado el fichero: " + NOMBRE_FICHERO);
            } else {
                System.out.println("El fichero ya existe.");
            }
        } catch (IOException e) {
            System.out.println("Error al crear el fichero.");
        }
    }

    // 2. Consultar propiedades o metadatos de un fichero
    public static void consultarPropiedades() {
        File fichero = new File(RUTA_FICHERO + NOMBRE_FICHERO);

        System.out.println("\n*** INFORMACIÓN DEL FICHERO ***");
        System.out.println("Nombre: " + fichero.getName());
        System.out.println("Ruta relativa: " + fichero.getPath());
        System.out.println("Ruta absoluta: " + fichero.getAbsolutePath());
        System.out.println("Existe: " + fichero.exists());
        System.out.println("Se puede leer: " + fichero.canRead());
        System.out.println("Se puede escribir: " + fichero.canWrite());
        System.out.println("Tamaño: " + fichero.length() + " bytes");
        System.out.println("Es un directorio: " + fichero.isDirectory());
        System.out.println("Es un fichero: " + fichero.isFile());
        System.out.println("Directorio padre: " + fichero.getParent());
    }

    // 3. Listar todos los elementos de un directorio
    public static void listarContenidoDirectorio() {
        File directorio = new File(RUTA_FICHERO);
        // list() devuelve un array de String con los nombres de los elementos
        String[] elementos = directorio.list();

        if (elementos != null) {
            for (String nombre : elementos) {
                File elemento = new File(directorio, nombre);
                if (elemento.isDirectory()) {
                    System.out.println("Directorio: " + nombre);
                } else {
                    System.out.println("Fichero: " + nombre);
                }
            }
        }
    }

    // 4. Comprobar existencia con la API moderna Path y Files
    public static void comprobarConPathYFiles() {
        Path ruta = Path.of(RUTA_FICHERO + NOMBRE_FICHERO);
        if (Files.exists(ruta)) {
            System.out.println("Confirmado con Files: el fichero existe.");
        } else {
            System.out.println("Confirmado con Files: el fichero no existe.");
        }
    }

    // 5. Renombrar y eliminar un archivo
    public static void renombrarYEliminar() {
        File ficheroOriginal = new File(RUTA_FICHERO + NOMBRE_FICHERO);
        File ficheroNuevo = new File(RUTA_FICHERO + NOMBRE_RENOMBRADO);

        // Renombrar archivo
        if (ficheroOriginal.renameTo(ficheroNuevo)) {
            System.out.println("Fichero renombrado correctamente.");
        }

        // Eliminar archivo (un directorio solo se borra con delete() si está vacío)
        if (ficheroNuevo.delete()) {
            System.out.println("Fichero eliminado correctamente.");
        } else {
            System.out.println("No se ha podido eliminar el fichero.");
        }
    }
}