

/* 2. Escritura y lectura de ficheros de texto
(EscribirTxt.java y LeerTxt.java)
Estas dos clases muestran cómo escribir y leer archivos .txt
tanto carácter a carácter (FileWriter/FileReader) como línea
a línea usando un búfer (BufferedWriter/BufferedReader),
además de copiar y transformar un archivo */


package REPASO_EXAMEN;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EscribirTxt {

    private static final String RUTA_FICHERO = "datos/";
    private static final String NOMBRE_FICHERO = "notas.txt";

    public static void main(String[] args) {
        escribirCaracteres();
        escribirLineasConBuffer();
    }

    // 1. Escribir texto directamente con FileWriter
    public static void escribirCaracteres() {
        // try-with-resources cierra automáticamente el FileWriter al terminar
        try (FileWriter escribirFichero = new FileWriter(RUTA_FICHERO + NOMBRE_FICHERO)) {
            escribirFichero.write("Acceso a datos");
            System.out.println("Texto escrito con FileWriter.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }

    // 2. Escribir varias líneas de texto con BufferedWriter
    public static void escribirLineasConBuffer() {
        // Envolvemos el FileWriter dentro de un BufferedWriter para usar newLine()
        try (BufferedWriter escribirFichero = new BufferedWriter(
                new FileWriter(RUTA_FICHERO + NOMBRE_FICHERO))) {

            escribirFichero.write("Primera línea de receta");
            escribirFichero.newLine(); // Inserta un salto de línea
            escribirFichero.write("Segunda línea de receta");
            escribirFichero.newLine();
            escribirFichero.write("Tercera línea de receta");

            System.out.println("Líneas escritas con BufferedWriter.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }
}