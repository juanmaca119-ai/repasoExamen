package REPASO_EXAMEN_2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Apartado 2.9: API Path y Files. */
public class Ej02_PathFiles {
    public static void main(String[] args) throws IOException {
        Path dir = Paths.get("datos_path");
        Path fichero = dir.resolve("nota.txt");

        Files.createDirectories(dir);
        if (Files.notExists(fichero)) {
            Files.createFile(fichero);
        }

        Files.writeString(fichero, "Hola con Files\n");
        System.out.println("Contenido: " + Files.readString(fichero).trim());
        System.out.println("Tamaño: " + Files.size(fichero) + " bytes");
        System.out.println("Nombre: " + fichero.getFileName());
        System.out.println("Absoluta: " + fichero.toAbsolutePath());
        System.out.println("¿Existe? " + Files.exists(fichero));

        Files.deleteIfExists(fichero);
        Files.deleteIfExists(dir);
    }
}
