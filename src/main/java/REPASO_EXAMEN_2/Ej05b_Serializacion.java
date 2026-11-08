package REPASO_EXAMEN_2;

import java.io.*;

/** Apartados 5.5 y 5.6: serialización de objetos. */
public class Ej05b_Serializacion {
    public static void main(String[] args) {
        // Ejercicio 3: un objeto
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("producto.bin"))) {
            out.writeObject(new Producto("Ratón", 12.5, 40));
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("producto.bin"))) {
            Producto p = (Producto) in.readObject();
            System.out.println(p);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }

        // Ejercicio 4: tres objetos en el mismo fichero
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("productos.bin"))) {
            out.writeObject(new Producto("Teclado", 19.99, 25));
            out.writeObject(new Producto("Monitor", 149.0, 10));
            out.writeObject(new Producto("Webcam", 35.5, 18));
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("productos.bin"))) {
            while (true) {
                System.out.println((Producto) in.readObject());
            }
        } catch (EOFException e) {
            System.out.println("(fin del fichero)");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}

class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    String nombre;
    double precio;
    int stock;

    Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return nombre + " - " + precio + " € - stock: " + stock;
    }
}
