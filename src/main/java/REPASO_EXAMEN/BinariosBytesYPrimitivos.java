

/* 3. Ficheros binarios: Bytes y datos primitivos
(BinariosBytesYPrimitivos.java)
Aquí tienes la diferencia entre escribir bytes puros de
0 a 255 (FileOutputStream/FileInputStream) y escribir tipos
primitivos de Java como String, int o double
(DataOutputStream/DataInputStream), que siempre deben
leerse en el mismo orden en que se escribieron */


package REPASO_EXAMEN;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinariosBytesYPrimitivos {

    private static final String RUTA_FICHERO = "datos/";
    private static final String FICHERO_BYTES = "bytes.dat";
    private static final String FICHERO_PRIMITIVOS = "alumno.dat";

    public static void main(String[] args) {
        escribirYLeerBytes();
        escribirYLeerDatosPrimitivos();
    }

    // 1. Flujos de bytes básicos (valores entre 0 y 255)
    public static void escribirYLeerBytes() {
        // Escritura de bytes del 0 al 99
        try (FileOutputStream escribirFichero = new FileOutputStream(RUTA_FICHERO + FICHERO_BYTES)) {
            for (int i = 0; i < 100; i++) {
                escribirFichero.write(i);
            }
        } catch (IOException e) {
            System.out.println("Error al escribir bytes.");
        }

        // Lectura de bytes (read() devuelve -1 al llegar al final del fichero)
        try (FileInputStream leerFichero = new FileInputStream(RUTA_FICHERO + FICHERO_BYTES)) {
            int dato;
            while ((dato = leerFichero.read()) != -1) {
                System.out.print(dato + " ");
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error al leer bytes.");
        }
    }

    // 2. Flujos de datos primitivos (String, int, double, boolean, char)
    public static void escribirYLeerDatosPrimitivos() {
        // Escritura con DataOutputStream
        try (DataOutputStream escribirFichero = new DataOutputStream(
                new FileOutputStream(RUTA_FICHERO + FICHERO_PRIMITIVOS))) {

            // Escribimos en orden: 1º String (UTF), 2º int, 3º double
            escribirFichero.writeUTF("Ana");
            escribirFichero.writeInt(20);
            escribirFichero.writeDouble(8.75);

            System.out.println("Datos primitivos guardados.");
        } catch (IOException e) {
            System.out.println("Error al escribir datos primitivos.");
        }

        // Lectura con DataInputStream (OBLIGATORIO leer en el mismo orden exacto)
        try (DataInputStream leerFichero = new DataInputStream(
                new FileInputStream(RUTA_FICHERO + FICHERO_PRIMITIVOS))) {

            String nombre = leerFichero.readUTF();
            int edad = leerFichero.readInt();
            double nota = leerFichero.readDouble();

            System.out.println("Nombre: " + nombre + ", Edad: " + edad + ", Nota: " + nota);
        } catch (IOException e) {
            System.out.println("Error al leer datos primitivos.");
        }
    }
}