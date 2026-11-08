import java.io.*;

/** Apartado 3: throws, try-catch, finally y try-with-resources. */
public class Ej03_Excepciones {

    // 3.2 throws: el método no gestiona el error, lo propaga a quien lo llama
    static String leerPrimeraLinea(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String linea = br.readLine();
        br.close();
        return linea;
    }

    public static void main(String[] args) {
        // 3.3 try-catch (de la excepción más concreta a la más general)
        try {
            System.out.println(leerPrimeraLinea("no_existe.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de entrada/salida: " + e.getMessage());
        }

        // 3.3.1 finally: se ejecuta siempre, ideal para cerrar recursos
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("no_existe.txt"));
            System.out.println(br.readLine());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                System.out.println("No se pudo cerrar: " + e.getMessage());
            }
            System.out.println("Bloque finally ejecutado");
        }

        // try-with-resources: cierra el recurso automáticamente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("prueba.txt"))) {
            bw.write("Hola");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
        try (BufferedReader r = new BufferedReader(new FileReader("prueba.txt"))) {
            System.out.println("Leído: " + r.readLine());
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        new File("prueba.txt").delete();
    }
}
