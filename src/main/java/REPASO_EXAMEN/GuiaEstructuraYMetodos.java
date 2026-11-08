

/* 7. Guía de sintaxis: Menús, Métodos
(con/sin parámetros y retorno), Bucles y Condicionales
(GuiaEstructuraYMetodos.java)
Esta clase aparte toma como base tu ejercicio de
GestionRecetas para explicarte cómo construir desde cero
un menú interactivo,
cuándo un método lleva parámetros
                                  entre paréntesis, cuándo devuelve un dato con return o es void,
                                  y cómo funcionan los distintos bucles y condicionales. */


package REPASO_EXAMEN;

import EJERCICIO_RECETAS.Receta;

import java.util.Scanner;

public class GuiaEstructuraYMetodos {

    public static void main(String[] args) {
        // Scanner permite leer lo que el usuario escribe por teclado
        Scanner sc = new Scanner(System.in);
        int opcionMenu;

        // Creamos un array de prueba en memoria para practicar con los métodos
        Receta[] catalogo = {
                new Receta(1, "Pasta carbonara", "Italiana", 20),
                new Receta(2, "Arroz con pollo", "Arroces", 40),
                new Receta(3, "Fajitas de pollo", "Mexicana", 25),
                new Receta(4, "Arepas rellenas", "Colombiana", 15)
        };

        // =========================================================================
        // 1. ESTRUCTURA DE UN MENÚ CON BUCLE DO-WHILE Y CONDICIONAL SWITCH
        // =========================================================================
        // Usamos do-while porque queremos que el menú se muestre AL MENOS UNA VEZ
        // y se repita mientras la opción elegida sea distinta de 0 (Salir).
        do {
            System.out.println("\n=== PLANTILLA DE MENÚ ===");
            System.out.println("1. Llamar a método SIN parámetros y SIN retorno (void)");
            System.out.println("2. Llamar a método CON parámetros y SIN retorno (void)");
            System.out.println("3. Llamar a método CON parámetros y CON retorno (devuelve int)");
            System.out.println("4. Ejemplo de los 3 tipos de bucles (for, for-each, while)");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcionMenu = sc.nextInt();
            // Limpiamos el Enter pendiente después de leer un número con nextInt()
            sc.nextLine();

            // switch evalúa el valor de 'opcionMenu' y salta al 'case' correspondiente
            switch (opcionMenu) {
                case 1:
                    // Llamada a un método que NO necesita datos entre paréntesis
                    mostrarMensajeBienvenida();
                    break; // El break evita que se ejecuten los case de abajo

                case 2:
                    System.out.print("Introduce el tiempo máximo en minutos: ");
                    int tiempoMaximo = sc.nextInt();
                    // Llamada a un método pasándole 2 argumentos entre paréntesis
                    filtrarRecetasRapidas(catalogo, tiempoMaximo);
                    break;

                case 3:
                    System.out.print("Introduce la categoría a contar (ej. Italiana): ");
                    String categoriaBuscar = sc.nextLine();
                    // Como este método DEVUELVE un int (return), guardamos su resultado en una variable
                    int cantidad = contarPorCategoria(catalogo, categoriaBuscar);
                    System.out.println("Total de recetas en esa categoría: " + cantidad);
                    break;

                case 4:
                    demostrarTiposDeBucles(catalogo);
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    // Se ejecuta si el usuario introduce un número que no está en los case
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcionMenu != 0); // Condición de repetición del menú

        sc.close();
    }

    // =========================================================================
    // 2. SINTAXIS DE MÉTODOS: ¿CUÁNDO USAR PARÁMETROS Y CUÁNDO USAR RETURN?
    // =========================================================================

    /*
     * A) MÉTODO SIN PARÁMETROS () Y SIN RETORNO (void)
     * - Lleva 'void' porque solo hace una acción (imprimir) y no devuelve ningún dato al main.
     * - Lleva paréntesis vacíos () porque no necesita ningún dato externo para funcionar.
     */
    public static void mostrarMensajeBienvenida() {
        System.out.println("¡Bienvenido al sistema de repaso de Gestión de Recetas!");
    }

    /*
     * B) MÉTODO CON PARÁMETROS (Receta[] lista, int limiteMinutos) Y SIN RETORNO (void)
     * - Recibe datos entre paréntesis que le envía el main para poder trabajar con ellos.
     * - Usa condicionales (if / else) y una bandera booleana para saber si encontró alguna.
     */
    public static void filtrarRecetasRapidas(Receta[] lista, int limiteMinutos) {
        boolean hayRecetasRapidas = false; // Bandera booleana inicializada en false

        for (int i = 0; i < lista.length; i++) {
            // Condicional IF: solo entra si el tiempo es menor o igual al límite recibido
            if (lista[i].getTiempoPreparacion() <= limiteMinutos) {
                System.out.println("Receta rápida encontrada: " + lista[i]);
                hayRecetasRapidas = true; // Cambiamos la bandera porque encontramos al menos una
            }
        }

        // Condicional al final del recorrido: si la bandera sigue en false (!hayRecetasRapidas)
        if (!hayRecetasRapidas) {
            System.out.println("No hay ninguna receta que tarde " + limiteMinutos + " minutos o menos.");
        }
    }

    /*
     * C) MÉTODO CON PARÁMETROS Y CON RETORNO (int)
     * - En lugar de 'void', pone 'int' porque promete devolver un número entero al terminar.
     * - Es OBLIGATORIO poner la instrucción 'return variable;' al final del método.
     */
    public static int contarPorCategoria(Receta[] lista, String categoriaBuscada) {
        int contador = 0;

        for (Receta receta : lista) {
            // Para comparar Strings NUNCA usamos ==, usamos .equals() o .equalsIgnoreCase()
            if (receta.getCategoria().trim().equalsIgnoreCase(categoriaBuscada)) {
                contador++;
            }
        }

        return contador; // Devuelve el número total calculado a quien llamó al método
    }

    // =========================================================================
    // 3. LOS 3 TIPOS DE BUCLES QUE APARECEN EN LOS APUNTES
    // =========================================================================
    public static void demostrarTiposDeBucles(Receta[] lista) {

        // 1. Bucle FOR tradicional: ideal cuando necesitas saber la posición [i] del array
        System.out.println("--- 1. Bucle FOR con índice i ---");
        for (int i = 0; i < lista.length; i++) {
            System.out.println("Posición " + i + ": " + lista[i].getNombre());
        }

        // 2. Bucle FOR-EACH: más corto y limpio para recorrer todos los objetos de un array
        System.out.println("\n--- 2. Bucle FOR-EACH ---");
        for (Receta r : lista) {
            System.out.println("Receta: " + r.getNombre());
        }

        // 3. Bucle WHILE: se repite mientras la condición entre paréntesis sea verdadera (true)
        System.out.println("\n--- 3. Bucle WHILE con condición ---");
        int indice = 0;
        while (indice < lista.length) {
            System.out.println("Leyendo con while: " + lista[indice].getNombre());
            indice++; // Avanzamos el índice para no crear un bucle infinito
        }
    }
}