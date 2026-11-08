package REPASO_EXAMEN_2;

import java.io.*;
import java.util.Scanner;

/** Apartado 6: RandomAccessFile (registros de tamaño fijo). */
public class Ej06_Aleatorio {
    static final int LONG_NOMBRE = 20;
    // Registro: código (4 bytes) + nombre (20 chars x 2 bytes) + precio (8 bytes)
    static final int TAM = 4 + LONG_NOMBRE * 2 + 8;

    static void escribirProducto(RandomAccessFile raf, int codigo, String nombre, double precio)
            throws IOException {
        raf.writeInt(codigo);
        StringBuilder sb = new StringBuilder(nombre);
        sb.setLength(LONG_NOMBRE);              // rellena o recorta a 20 caracteres
        raf.writeChars(sb.toString());
        raf.writeDouble(precio);
    }

    static void mostrarProducto(RandomAccessFile raf) throws IOException {
        int codigo = raf.readInt();
        char[] c = new char[LONG_NOMBRE];
        for (int i = 0; i < LONG_NOMBRE; i++) {
            c[i] = raf.readChar();
        }
        String nombre = new String(c).replace('\0', ' ').trim();
        double precio = raf.readDouble();
        System.out.println(codigo + " | " + nombre + " | " + precio + " €");
    }

    // 6.6 ejercicio 4: comprobar que un código no existe ya
    static boolean existeCodigo(RandomAccessFile raf, int codigo) throws IOException {
        for (long pos = 0; pos < raf.length(); pos += TAM) {
            raf.seek(pos);
            if (raf.readInt() == codigo) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // Ejercicio 1 (6.2 y 6.3): números del 1 al 20
        try (RandomAccessFile raf = new RandomAccessFile("numeros.dat", "rw")) {
            raf.setLength(0);
            for (int i = 1; i <= 20; i++) {
                raf.writeInt(i);
            }
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                System.out.print(raf.readInt() + " ");
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Ejercicio 2: cinco productos y acceso directo por código
        try (RandomAccessFile raf = new RandomAccessFile("productos.dat", "rw")) {
            raf.setLength(0);
            escribirProducto(raf, 1, "Teclado", 19.99);
            escribirProducto(raf, 2, "Ratón", 12.5);
            escribirProducto(raf, 3, "Monitor", 149.0);
            escribirProducto(raf, 4, "Webcam", 35.5);
            escribirProducto(raf, 5, "Altavoces", 28.75);

            Scanner sc = new Scanner(System.in);
            System.out.print("Código a consultar (1-5): ");
            int codigo = sc.nextInt();
            raf.seek((long) (codigo - 1) * TAM);          // 6.4.1 acceso por posición
            mostrarProducto(raf);

            // 6.4.2 Modificar un registro: solo se sobrescribe el precio
            raf.seek((long) (codigo - 1) * TAM + 4 + LONG_NOMBRE * 2);
            raf.writeDouble(99.99);
            raf.seek((long) (codigo - 1) * TAM);
            System.out.print("Tras modificar: ");
            mostrarProducto(raf);

            // 6.4.3 Añadir un registro al final (comprobando que el código no exista)
            int nuevo = 6;
            if (existeCodigo(raf, nuevo)) {
                System.out.println("El código " + nuevo + " ya existe");
            } else {
                raf.seek(raf.length());
                escribirProducto(raf, nuevo, "Alfombrilla", 7.0);
                System.out.println("Producto añadido");
            }

            System.out.println("--- Listado completo ---");
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                mostrarProducto(raf);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
