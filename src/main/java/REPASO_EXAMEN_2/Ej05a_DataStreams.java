package REPASO_EXAMEN_2;

import java.io.*;

/** Apartados 5.3 y 5.4: flujos de datos primitivos (DataOutputStream / DataInputStream). */
public class Ej05a_DataStreams {
    public static void main(String[] args) {
        // Ejercicio 1: enteros del 1 al 50
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("numeros.bin"))) {
            for (int i = 1; i <= 50; i++) {
                out.writeInt(i);
            }
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }

        try (DataInputStream in = new DataInputStream(new FileInputStream("numeros.bin"))) {
            while (true) {
                System.out.print(in.readInt() + " ");
            }
        } catch (EOFException e) {
            System.out.println("\n(fin del fichero)");   // así se detecta el final
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }

        // Ejercicio 2: cinco alumnos (nombre y nota media)
        String[] nombres = {"Ana", "Luis", "Marta", "Pablo", "Lucía"};
        double[] notas = {8.5, 6.0, 9.25, 5.5, 7.75};

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream("alumnos.bin"))) {
            for (int i = 0; i < nombres.length; i++) {
                out.writeUTF(nombres[i]);
                out.writeDouble(notas[i]);
            }
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }

        // Se lee en el mismo orden y con los mismos tipos con que se escribió
        try (DataInputStream in = new DataInputStream(new FileInputStream("alumnos.bin"))) {
            for (int i = 0; i < 5; i++) {
                String nombre = in.readUTF();
                double nota = in.readDouble();
                System.out.println(nombre + " - " + nota);
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}
