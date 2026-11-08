package REPASO_EXAMEN_2;

import java.io.File;
import java.io.IOException;

/** Apartados 2.4 a 2.8: clase File (propiedades, crear, listar y eliminar). */
public class Ej01_Ficheros {
    public static void main(String[] args) throws IOException {
        // 2.6 Crear directorio
        File dir = new File("copias");
        if (dir.exists()) {
            System.out.println("El directorio ya existe");
        } else if (dir.mkdir()) {
            System.out.println("Directorio creado");
        }

        // 2.6 Crear fichero dentro del directorio
        File f = new File(dir, "config.txt");
        if (f.exists()) {
            System.out.println("El fichero ya existe");
        } else if (f.createNewFile()) {
            System.out.println("Fichero creado");
        }

        // 2.5 Consulta de propiedades
        System.out.println("Nombre: " + f.getName());
        System.out.println("Ruta relativa: " + f.getPath());
        System.out.println("Ruta absoluta: " + f.getAbsolutePath());
        System.out.println("Tamaño: " + f.length() + " bytes");
        System.out.println("Es fichero: " + f.isFile() + " / Es directorio: " + f.isDirectory());
        System.out.println("Lectura: " + f.canRead() + " / Escritura: " + f.canWrite());

        // 2.7 Listado del contenido
        for (File e : dir.listFiles()) {
            System.out.println((e.isDirectory() ? "[DIR]  " : "[FILE] ") + e.getName());
        }

        // 2.8 Eliminación: un directorio con contenido NO se puede borrar
        System.out.println("¿Borrado con contenido? " + dir.delete());   // false
        f.delete();
        System.out.println("¿Borrado vacío? " + dir.delete());           // true
    }
}
