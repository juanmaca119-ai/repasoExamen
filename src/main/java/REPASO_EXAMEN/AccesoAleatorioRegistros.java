

/* 4. Ficheros de acceso aleatorio
(AccesoAleatorioRegistros.java)
Esta clase es clave para el examen porque suele caer el
cálculo de bytes de un registro fijo y el uso del puntero
con seek().
int ocupa 4 bytes.
double ocupa 8 bytes.
char ocupa 2 bytes (por tanto, un texto fijado
con StringBuffer a 10 caracteres ocupa
$10 \times 2 = 20\text{ bytes}$) */




package REPASO_EXAMEN;

import java.io.IOException;
import java.io.RandomAccessFile;

public class AccesoAleatorioRegistros {

    private static final String RUTA_FICHERO = "datos/";
    private static final String NOMBRE_FICHERO = "empleados.dat";

    // Cálculo del tamaño: Id (4) + Apellido 10 chars (20) + Dept (4) + Salario (8) = 36 bytes
    private static final int TAM_REGISTRO = 36;
    // Posición donde empieza el campo Salario dentro de un registro: 4 + 20 + 4 = 28
    private static final int POS_SALARIO = 28;

    public static void main(String[] args) {
        escribirRegistrosIniciales();
        leerTodosLosRegistros();
        buscarEmpleadoPorId(2);
        modificarSalarioDirecto(2, 2500.75);
        anadirEmpleadoAlFinal(5, "Martínez", 40, 3000.00);
    }

    // 1. Escribir registros de tamaño fijo en modo "rw" (lectura y escritura)
    public static void escribirRegistrosIniciales() {
        int[] ids = {1, 2, 3, 4};
        String[] apellidos = {"Fernandez", "Lopez", "Garcia", "Sanchez"};
        int[] departamentos = {10, 20, 10, 30};
        double[] salarios = {1800.50, 2100.00, 1950.75, 2300.25};

        try (RandomAccessFile fichero = new RandomAccessFile(RUTA_FICHERO + NOMBRE_FICHERO, "rw")) {
            // Vacía el fichero si ya tenía datos previos
            fichero.setLength(0);

            for (int i = 0; i < ids.length; i++) {
                fichero.writeInt(ids[i]); // 4 bytes

                // Fijamos el apellido exactamente a 10 caracteres (20 bytes)
                StringBuffer bufferApellido = new StringBuffer(apellidos[i]);
                bufferApellido.setLength(10);
                fichero.writeChars(bufferApellido.toString());

                fichero.writeInt(departamentos[i]); // 4 bytes
                fichero.writeDouble(salarios[i]);   // 8 bytes
            }
            System.out.println("Registros aleatorios escritos correctamente.");
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero aleatorio.");
        }
    }

    // 2. Leer todos los registros secuencialmente en modo "r" (solo lectura)
    public static void leerTodosLosRegistros() {
        try (RandomAccessFile fichero = new RandomAccessFile(RUTA_FICHERO + NOMBRE_FICHERO, "r")) {
            // Mientras el puntero actual sea menor que el tamaño total del archivo
            while (fichero.getFilePointer() < fichero.length()) {
                int id = fichero.readInt();

                // Para leer el String de 10 caracteres, leemos 10 veces readChar()
                char[] apellidoChars = new char[10];
                for (int i = 0; i < apellidoChars.length; i++) {
                    apellidoChars[i] = fichero.readChar();
                }
                // Convertimos el char[] a String y quitamos espacios sobrantes con trim()
                String apellido = new String(apellidoChars).trim();

                int departamento = fichero.readInt();
                double salario = fichero.readDouble();

                System.out.println("ID: " + id + " | Apellido: " + apellido +
                        " | Dept: " + departamento + " | Salario: " + salario);
            }
        } catch (IOException e) {
            System.out.println("Error al leer los registros.");
        }
    }

    // 3. Buscar un registro directamente calculando su posición en bytes
    public static void buscarEmpleadoPorId(int idBuscado) {
        try (RandomAccessFile fichero = new RandomAccessFile(RUTA_FICHERO + NOMBRE_FICHERO, "r")) {
            // Fórmula de posición: (id - 1) * tamaño del registro
            long posicion = (long) (idBuscado - 1) * TAM_REGISTRO;

            // Si la posición supera o iguala el tamaño del archivo, no existe
            if (posicion >= fichero.length() || posicion < 0) {
                System.out.println("El empleado no existe.");
                return;
            }

            // Movemos el puntero directamente al inicio del registro buscado
            fichero.seek(posicion);

            int id = fichero.readInt();
            char[] apellidoChars = new char[10];
            for (int i = 0; i < apellidoChars.length; i++) {
                apellidoChars[i] = fichero.readChar();
            }
            int departamento = fichero.readInt();
            double salario = fichero.readDouble();

            System.out.println("Encontrado -> ID: " + id + ", Apellido: " + new String(apellidoChars).trim() +
                    ", Salario: " + salario);
        } catch (IOException e) {
            System.out.println("Error al buscar el empleado.");
        }
    }

    // 4. Modificar únicamente un campo sin reescribir el resto del fichero
    public static void modificarSalarioDirecto(int idEmpleado, double nuevoSalario) {
        try (RandomAccessFile fichero = new RandomAccessFile(RUTA_FICHERO + NOMBRE_FICHERO, "rw")) {
            long posicionRegistro = (long) (idEmpleado - 1) * TAM_REGISTRO;

            if (posicionRegistro >= fichero.length() || posicionRegistro < 0) {
                System.out.println("El empleado no existe.");
                return;
            }

            // Situamos el puntero al inicio del registro + los bytes hasta llegar a salario (28)
            fichero.seek(posicionRegistro + POS_SALARIO);
            fichero.writeDouble(nuevoSalario);
            System.out.println("Salario actualizado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al modificar el salario.");
        }
    }

    // 5. Añadir un nuevo registro al final comprobando antes que no exista el ID
    public static void anadirEmpleadoAlFinal(int nuevoId, String apellido, int dept, double salario) {
        try (RandomAccessFile fichero = new RandomAccessFile(RUTA_FICHERO + NOMBRE_FICHERO, "rw")) {
            boolean existe = false;

            while (fichero.getFilePointer() < fichero.length()) {
                int idLeido = fichero.readInt();
                if (idLeido == nuevoId) {
                    existe = true;
                    break;
                }
                // Saltamos el resto de campos de este registro (20 + 4 + 8 = 32 bytes)
                fichero.seek(fichero.getFilePointer() + 20 + 4 + 8);
            }

            if (existe) {
                System.out.println("Ya existe un empleado con ese ID.");
                return;
            }

            // Colocamos el puntero justo al final del fichero con fichero.length()
            fichero.seek(fichero.length());

            fichero.writeInt(nuevoId);
            StringBuffer bufferApellido = new StringBuffer(apellido);
            bufferApellido.setLength(10);
            fichero.writeChars(bufferApellido.toString());
            fichero.writeInt(dept);
            fichero.writeDouble(salario);

            System.out.println("Empleado añadido al final correctamente.");
        } catch (IOException e) {
            System.out.println("Error al añadir el empleado.");
        }
    }
}