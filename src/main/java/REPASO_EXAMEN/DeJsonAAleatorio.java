

/* 3. Leer de un JSON y guardarlo en un fichero de Acceso Aleatorio
(DeJsonAAleatorio.java)
Esta combinación une el Apartado 9.3 (Lectura de un array JSON con fromJson)
con el Apartado 6.2 (Escritura de registros de tamaño fijo con RandomAccessFile).
Lee el archivo catalogo_completo.json que generamos en el ejemplo anterior y guarda
cada receta en recetas_aleatorio.dat fijando el tamaño de los textos con
StringBuffer para que todos los registros ocupen exactamente los mismos bytes */

/* (Nota: Recuerda añadir en tu clase
Receta.java un constructor vacío
public Receta() {} si vas a leer objetos
Receta con gson.fromJson(), tal como pide Gson
en la página 62 del PDF). */

package REPASO_EXAMEN;

import EJERCICIO_RECETAS.Receta;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DeJsonAAleatorio {

    private static final String RUTA_FICHERO =
            "C:\\Users\\JUAN MANUEL\\IdeaProjects\\GestionRecetas\\src\\main\\java\\EJERCICIO_RECETAS\\";
    private static final String FICHERO_JSON_ORIGEN = "catalogo_completo.json";
    private static final String FICHERO_ALEATORIO_DESTINO = "recetas_aleatorio.dat";

    /*
     * Cálculo del tamaño fijo de cada registro de Receta:
     * - id (int): 4 bytes
     * - nombre (20 caracteres x 2 bytes por char): 40 bytes
     * - categoria (15 caracteres x 2 bytes por char): 30 bytes
     * - tiempoPreparacion (int): 4 bytes
     * TOTAL TAM_REGISTRO = 4 + 40 + 30 + 4 = 78 bytes
     */
    private static final int TAM_REGISTRO = 78;

    public static void main(String[] args) {
        pasarDeJsonAAleatorio();
        comprobarLecturaAleatoria(2); // Probamos a buscar directamente la receta 2 con seek()
    }

    public static void pasarDeJsonAAleatorio() {
        File archivoJson = new File(RUTA_FICHERO + FICHERO_JSON_ORIGEN);
        if (!archivoJson.exists()) {
            System.out.println("El fichero JSON de origen no existe.");
            return;
        }

        Gson gson = new Gson();
        Receta[] recetas;

        // 1. Leemos el array de recetas desde el documento JSON
        try (FileReader lectorJson = new FileReader(archivoJson)) {
            recetas = gson.fromJson(lectorJson, Receta[].class);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero JSON.");
            return;
        }

        // 2. Escribimos cada receta como un registro de tamaño fijo (78 bytes) en RandomAccessFile
        try (RandomAccessFile ficheroAleatorio = new RandomAccessFile(
                RUTA_FICHERO + FICHERO_ALEATORIO_DESTINO, "rw")) {

            // Limpiamos el fichero por si tenía datos anteriores
            ficheroAleatorio.setLength(0);

            for (Receta r : recetas) {
                // Escribe ID (4 bytes)
                ficheroAleatorio.writeInt(r.getId());

                // Escribe Nombre fijado a 20 caracteres (40 bytes)
                StringBuffer bufferNombre = new StringBuffer(r.getNombre().trim());
                bufferNombre.setLength(20);
                ficheroAleatorio.writeChars(bufferNombre.toString());

                // Escribe Categoría fijada a 15 caracteres (30 bytes)
                StringBuffer bufferCategoria = new StringBuffer(r.getCategoria().trim());
                bufferCategoria.setLength(15);
                ficheroAleatorio.writeChars(bufferCategoria.toString());

                // Escribe Tiempo de preparación (4 bytes)
                ficheroAleatorio.writeInt(r.getTiempoPreparacion());
            }

            System.out.println("Datos pasados de JSON a fichero de acceso aleatorio correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el fichero de acceso aleatorio.");
        }
    }

    // Método para comprobar que podemos saltar directamente a una receta con seek()
    public static void comprobarLecturaAleatoria(int idBuscado) {
        try (RandomAccessFile fichero = new RandomAccessFile(
                RUTA_FICHERO + FICHERO_ALEATORIO_DESTINO, "r")) {

            long posicion = (long) (idBuscado - 1) * TAM_REGISTRO;

            if (posicion >= fichero.length() || posicion < 0) {
                System.out.println("La receta no existe en el fichero aleatorio.");
                return;
            }

            // Saltamos directamente a la posición de la receta
            fichero.seek(posicion);

            int id = fichero.readInt();

            char[] nombreChars = new char[20];
            for (int i = 0; i < nombreChars.length; i++) {
                nombreChars[i] = fichero.readChar();
            }

            char[] categoriaChars = new char[15];
            for (int i = 0; i < categoriaChars.length; i++) {
                categoriaChars[i] = fichero.readChar();
            }

            int tiempo = fichero.readInt();

            System.out.println("Lectura directa con seek() -> ID: " + id +
                    ", Nombre: " + new String(nombreChars).trim() +
                    ", Categoría: " + new String(categoriaChars).trim() +
                    ", Tiempo: " + tiempo + " min.");

        } catch (IOException e) {
            System.out.println("Error al leer el fichero aleatorio.");
        }
    }
}