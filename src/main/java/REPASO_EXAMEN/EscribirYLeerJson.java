

/* 6. Lectura y escritura de JSON con Gson
(EscribirYLeerJson.java)
En el examen pueden pedirte guardar o leer un único objeto
(como hicimos con Configuracion.class) o guardar y leer
un array de varios objetos (como Receta[].class en el
ejemplo de Pelicula[].class y Videojuego[].class del PDF).
Aquí tienes ambos casos usando tus clases Configuracion y
Receta: */


package REPASO_EXAMEN;

import EJERCICIO_RECETAS.Configuracion;
import EJERCICIO_RECETAS.Receta;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EscribirYLeerJson {

    private static final String RUTA_FICHERO = "src/main/resources/";
    private static final String JSON_UN_OBJETO = "configuracion.json";
    private static final String JSON_ARRAY_OBJETOS = "catalogo_recetas.json";

    public static void main(String[] args) {
        guardarUnObjetoJson();
        leerUnObjetoJson();

        guardarArrayObjetosJson();
        leerArrayObjetosJson();
    }

    // 1A. Guardar UN SOLO objeto en JSON
    public static void guardarUnObjetoJson() {
        Configuracion config = new Configuracion(2, 4);
        // GsonBuilder con setPrettyPrinting() genera el JSON indentado y legible
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter escritor = new FileWriter(RUTA_FICHERO + JSON_UN_OBJETO)) {
            gson.toJson(config, escritor);
            System.out.println("Objeto único guardado en JSON.");
        } catch (IOException e) {
            System.out.println("Error al escribir JSON.");
        }
    }

    // 1B. Leer UN SOLO objeto desde JSON (se usa NombreClase.class sin corchetes)
    public static void leerUnObjetoJson() {
        Gson gson = new Gson();
        try (FileReader lector = new FileReader(RUTA_FICHERO + JSON_UN_OBJETO)) {
            Configuracion configLeida = gson.fromJson(lector, Configuracion.class);
            System.out.println("Configuración leída: " + configLeida);
        } catch (IOException e) {
            System.out.println("Error al leer JSON.");
        }
    }

    // 2A. Guardar un ARRAY DE OBJETOS en JSON (genera una lista [...] en el JSON)
    public static void guardarArrayObjetosJson() {
        Receta[] listaRecetas = {
                new Receta(1, "Pasta carbonara", "Italiana", 20),
                new Receta(2, "Arepas rellenas", "Colombiana", 20)
        };

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter escritor = new FileWriter(RUTA_FICHERO + JSON_ARRAY_OBJETOS)) {
            gson.toJson(listaRecetas, escritor);
            System.out.println("Array de recetas guardado en JSON.");
        } catch (IOException e) {
            System.out.println("Error al escribir el array en JSON.");
        }
    }

    // 2B. Leer un ARRAY DE OBJETOS desde JSON (¡OJO! Se usa Receta[].class con corchetes)
    public static void leerArrayObjetosJson() {
        Gson gson = new Gson();
        try (FileReader lector = new FileReader(RUTA_FICHERO + JSON_ARRAY_OBJETOS)) {
            // Convertimos el JSON directamente en un array de Receta[]
            Receta[] recetasLeidas = gson.fromJson(lector, Receta[].class);

            // Recorremos el array con un bucle for-each
            for (Receta r : recetasLeidas) {
                System.out.println(r);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el array JSON.");
        }
    }
}