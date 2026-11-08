

/* 1. Leer de un documento XML y pasarlo a un
fichero de texto (DeXmlATxt.java)
Este ejercicio mezcla el Apartado 8.3
(Recorrido de un árbol DOM) con el Apartado 4.3
(Escritura de líneas de texto con BufferedWriter).
Abre el archivo recetas.xml que exportaste en tu práctica,
extrae los datos de cada etiqueta <receta> y genera un
resumen legible línea a línea en resumen_recetas.txt. */



package REPASO_EXAMEN;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DeXmlATxt {

    // Declaración de rutas y nombres de los ficheros
    private static final String RUTA_FICHERO =
            "C:\\Users\\JUAN MANUEL\\IdeaProjects\\GestionRecetas\\src\\main\\java\\EJERCICIO_RECETAS\\";
    private static final String FICHERO_XML_ORIGEN = "recetas.xml";
    private static final String FICHERO_TXT_DESTINO = "resumen_recetas.txt";

    public static void main(String[] args) {
        convertirXmlATexto();
    }

    public static void convertirXmlATexto() {
        File archivoXml = new File(RUTA_FICHERO + FICHERO_XML_ORIGEN);

        // 1. Comprobamos siempre que el fichero XML de origen exista antes de leerlo
        if (!archivoXml.exists()) {
            System.out.println("El fichero XML no existe. Expórtalo primero desde GestionRecetas.");
            return;
        }

        try {
            // 2. Cargamos el documento XML en memoria mediante el árbol DOM
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(archivoXml);

            // Obtenemos la lista de todos los nodos <receta>
            NodeList listaRecetas = documento.getElementsByTagName("receta");

            // 3. Abrimos BufferedWriter con try-with-resources para escribir el fichero .txt
            try (BufferedWriter escribirTxt = new BufferedWriter(
                    new FileWriter(RUTA_FICHERO + FICHERO_TXT_DESTINO))) {

                escribirTxt.write("=== LISTADO DE RECETAS EXPORTADAS DESDE XML ===");
                escribirTxt.newLine();

                // 4. Recorremos cada nodo <receta> del XML y lo escribimos como una línea de texto
                for (int i = 0; i < listaRecetas.getLength(); i++) {
                    // Convertimos el nodo actual a Element mediante casting
                    Element recetaElem = (Element) listaRecetas.item(i);

                    // Obtenemos el atributo "id" y el texto de las etiquetas hijas
                    String id = recetaElem.getAttribute("id");
                    String nombre = recetaElem.getElementsByTagName("nombre").item(0).getTextContent();
                    String categoria = recetaElem.getElementsByTagName("categoria").item(0).getTextContent();
                    String tiempo = recetaElem.getElementsByTagName("tiempoPreparacion").item(0).getTextContent();

                    // Formateamos la línea y la escribimos en el archivo .txt
                    String lineaTexto = "Receta #" + id + ": " + nombre.trim() +
                            " (" + categoria.trim() + ") - " + tiempo + " min.";
                    escribirTxt.write(lineaTexto);
                    escribirTxt.newLine(); // Salto de línea para la siguiente receta
                }

                System.out.println("Fichero de texto generado correctamente desde el XML.");
            } catch (IOException e) {
                System.out.println("Error al escribir el fichero de texto.");
            }

        } catch (Exception e) {
            System.out.println("Error al procesar el documento XML.");
        }
    }
}