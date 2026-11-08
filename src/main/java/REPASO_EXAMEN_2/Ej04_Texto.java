import java.io.*;
import java.util.Scanner;

/** Apartado 4: archivos de texto (lectura, escritura, copia). Ejercicios 4.6. */
public class Ej04_Texto {
    public static void main(String[] args) {
        // 4.3 Escritura: se crea datos.txt para poder trabajar con él
        try (PrintWriter pw = new PrintWriter(new FileWriter("datos.txt"))) {
            pw.println("Java es un lenguaje de programación");
            pw.println();
            pw.println("Los ficheros de texto se leen línea a línea");
            pw.println("JAVA también sirve para acceder a ficheros");
            pw.println();
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }

        // 4.3 Escritura añadiendo al final (segundo parámetro true = append)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("datos.txt", true))) {
            bw.write("Última línea añadida");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al añadir: " + e.getMessage());
        }

        // Ejercicio 1 (4.2 Lectura): contar líneas
        int lineas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"))) {
            while (br.readLine() != null) {
                lineas++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        System.out.println("Total de líneas: " + lineas);

        // Ejercicio 2: líneas que contienen una palabra (sin distinguir mayúsculas)
        Scanner sc = new Scanner(System.in);
        System.out.print("Palabra a buscar: ");
        String palabra = sc.nextLine().toLowerCase();
        int coincidencias = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.toLowerCase().contains(palabra)) {
                    coincidencias++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        System.out.println("Líneas con \"" + palabra + "\": " + coincidencias);

        // Ejercicio 3 (4.4 Copia): copiar datos.txt en copia.txt
        // Ejercicio 4: omitir las líneas vacías
        try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("copia.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.isBlank()) {          // quitar este if = ejercicio 3
                    bw.write(linea);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al copiar: " + e.getMessage());
        }
        System.out.println("Copia realizada en copia.txt");
    }
}
