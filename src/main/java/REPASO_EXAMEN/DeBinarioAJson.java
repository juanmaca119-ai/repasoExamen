

/* 2. Leer de un fichero binario de objetos y
pasarlo a JSON (DeBinarioAJson.java)
Este ejemplo combina el Apartado 5.6/5.7
(Deserialización de objetos con ObjectInputStream)
con el Apartado 9.4/9.5 (Escritura de un array de objetos
en JSON con Gson). Lee todos los objetos Receta almacenados
en recetas.dat, los guarda en un array Receta[] y los
convierte automáticamente en una lista JSON dentro de
catalogo_completo.json */

/* Nota para el examen: Si tu profesora te pide el
proceso inverso (leer de un JSON que tiene varias recetas
y pasarlo a un fichero binario o a un XML), recuerda que
para leer un array desde JSON con Gson debes poner los
corchetes Receta[].class en el fromJson:
Receta[] recetasLeidas = gson.fromJson(lectorJson, Receta[].class); */


package REPASO_EXAMEN;

import EJERCICIO_RECETAS.Receta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeBinarioAJson {

    private static final String RUTA_FICHERO =
            "C:\\Users\\JUAN MANUEL\\IdeaProjects\\GestionRecetas\\src\\main\\java\\EJERCICIO_RECETAS\\";
    private static final String FICHERO_BINARIO_ORIGEN = "recetas.dat";
    private static final String FICHERO_JSON_DESTINO = "catalogo_completo.json";

    public static void main(String[] args) {
        exportarBinarioAJson();
    }

    // Método auxiliar para contar cuántos objetos Receta hay en el fichero binario
    public static int contarRecetasBinario() {
        int contador = 0;
        try (ObjectInputStream contar = new ObjectInputStream(
                new FileInputStream(RUTA_FICHERO + FICHERO_BINARIO_ORIGEN))) {
            while (true) {
                contar.readObject();
                contador++;
            }
        } catch (EOFException e) {
            // Fin del fichero alcanzado con normalidad
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al contar las recetas.");
        }
        return contador;
    }

    public static void exportarBinarioAJson() {
        File archivoBinario = new File(RUTA_FICHERO + FICHERO_BINARIO_ORIGEN);
        if (!archivoBinario.exists()) {
            System.out.println("El fichero binario no existe.");
            return;
        }

        // 1. Contamos cuántas recetas hay y creamos el array con el tamaño exacto
        int total = contarRecetasBinario();
        Receta[] listaRecetas = new Receta[total];

        // 2. Leemos todos los objetos del fichero binario (.dat) y llenamos el array
        try (ObjectInputStream leerBinario = new ObjectInputStream(
                new FileInputStream(archivoBinario))) {
            for (int i = 0; i < total; i++) {
                listaRecetas[i] = (Receta) leerBinario.readObject();
            }
        } catch (EOFException e) {
            // Fin de lectura
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el fichero binario.");
            return;
        }

        // 3. Escribimos el array completo de recetas en el fichero .json usando Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter escribirJson = new FileWriter(RUTA_FICHERO + FICHERO_JSON_DESTINO)) {
            // toJson convierte todo el array Receta[] en un array JSON [...]
            gson.toJson(listaRecetas, escribirJson);
            System.out.println("Catálogo exportado de binario (.dat) a JSON correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el documento JSON.");
        }
    }
}