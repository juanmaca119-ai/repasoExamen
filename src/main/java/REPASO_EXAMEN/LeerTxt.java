package REPASO_EXAMEN;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LeerTxt {

    private static final String RUTA_FICHERO = "datos/";
    private static final String FICHERO_ORIGEN = "notas.txt";
    private static final String FICHERO_DESTINO = "copia_mayusculas.txt";

    public static void main(String[] args) {
        leerCaracterACaracter();
        leerLineaALinea();
        copiarYTransformarTexto();
    }

    // 1. Leer carácter a carácter con FileReader
    public static void leerCaracterACaracter() {
        try (FileReader leerFichero = new FileReader(RUTA_FICHERO + FICHERO_ORIGEN)) {
            int caracter;
            // read() devuelve el código numérico del carácter o -1 al llegar al final
            while ((caracter = leerFichero.read()) != -1) {
                // Convertimos el int a (char) para mostrar la letra
                System.out.print((char) caracter);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo carácter a carácter.");
        }
    }

    // 2. Leer línea a línea con BufferedReader (más eficiente)
    public static void leerLineaALinea() {
        try (BufferedReader leerFichero = new BufferedReader(
                new FileReader(RUTA_FICHERO + FICHERO_ORIGEN))) {
            String linea;
            // readLine() devuelve una línea completa o null cuando llega al final
            while ((linea = leerFichero.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo línea a línea.");
        }
    }

    // 3. Abrir dos recursos a la vez en el try para leer, transformar y copiar
    public static void copiarYTransformarTexto() {
        try (BufferedReader leerFichero = new BufferedReader(
                new FileReader(RUTA_FICHERO + FICHERO_ORIGEN));
             BufferedWriter escribirFichero = new BufferedWriter(
                     new FileWriter(RUTA_FICHERO + FICHERO_DESTINO))) {

            String linea;
            int numeroLinea = 1;

            while ((linea = leerFichero.readLine()) != null) {
                // Numeramos cada línea y la pasamos a mayúsculas
                escribirFichero.write(numeroLinea + ". " + linea.toUpperCase());
                escribirFichero.newLine();
                numeroLinea++;
            }
            System.out.println("Archivo copiado y procesado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al copiar el archivo.");
        }
    }
}