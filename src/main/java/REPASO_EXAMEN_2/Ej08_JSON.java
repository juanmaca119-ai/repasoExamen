package REPASO_EXAMEN_2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** Apartado 9: JSON con Gson (necesita la dependencia Gson, ver pom.xml). */
public class Ej08_JSON {

    static JsonObject libro(String titulo, String autor, int anio) {
        JsonObject o = new JsonObject();
        o.addProperty("titulo", titulo);
        o.addProperty("autor", autor);
        o.addProperty("anio", anio);
        return o;
    }

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // 9.4 Escritura: ejercicio 1 (cada libro es un objeto JSON dentro de un array)
        JsonArray libros = new JsonArray();
        libros.add(libro("El Quijote", "Miguel de Cervantes", 1605));
        libros.add(libro("La Regenta", "Leopoldo Alas \"Clarín\"", 1885));
        libros.add(libro("El nombre de la rosa", "Umberto Eco", 1980));
        try (FileWriter fw = new FileWriter("libros.json")) {
            gson.toJson(libros, fw);
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }

        // 9.3 Lectura: ejercicio 2
        try (FileReader fr = new FileReader("libros.json")) {
            JsonArray lista = JsonParser.parseReader(fr).getAsJsonArray();
            for (JsonElement el : lista) {
                JsonObject o = el.getAsJsonObject();
                System.out.println(o.get("titulo").getAsString() + " | "
                        + o.get("autor").getAsString() + " | "
                        + o.get("anio").getAsInt());
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }

        // Ejercicio 3: objeto Java -> JSON
        try (FileWriter fw = new FileWriter("configuracion.json")) {
            gson.toJson(new Configuracion("es", "oscuro", true), fw);
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }

        // Ejercicio 4: JSON -> objeto Java
        try (FileReader fr = new FileReader("configuracion.json")) {
            Configuracion c = gson.fromJson(fr, Configuracion.class);
            System.out.println("Idioma: " + c.getIdioma());
            System.out.println("Tema: " + c.getTema());
            System.out.println("Notificaciones: " + c.isNotificaciones());
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}

class Configuracion {
    private String idioma;
    private String tema;
    private boolean notificaciones;

    Configuracion(String idioma, String tema, boolean notificaciones) {
        this.idioma = idioma;
        this.tema = tema;
        this.notificaciones = notificaciones;
    }

    String getIdioma() { return idioma; }
    String getTema() { return tema; }
    boolean isNotificaciones() { return notificaciones; }
}
