

/* 5. Archivos XML con la API DOM
(GestionXmlDomCompleta.java)
En el ejercicio de recetas hicimos la creación de un XML,
pero en el examen también puede entrarte leer/recorrer un
XML con NodeList, modificar el texto de una etiqueta o
añadir un nodo nuevo a un XML que ya existe.
Esta clase tiene las 4 operaciones juntas */



package REPASO_EXAMEN;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class GestionXmlDomCompleta {

    private static final String RUTA_FICHERO = "datos/";
    private static final String NOMBRE_FICHERO = "peliculas.xml";

    public static void main(String[] args) {
        crearXmlDesdeCero();
        leerYRecorrerXml();
        modificarElementoXml("Gladiator", "Gladiator (Edición Especial)");
        anadirNodoAXmlExistente("2019", "Joker", "Todd Phillips");
    }

    // 1. Crear un documento XML nuevo desde cero
    public static void crearXmlDesdeCero() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Crea el documento vacío en memoria
            Document documento = builder.newDocument();

            // Elemento raíz <peliculas> colgado de documento
            Element raizPeliculas = documento.createElement("peliculas");
            documento.appendChild(raizPeliculas);

            // Elemento <catalogo> colgado de <peliculas>
            Element catalogo = documento.createElement("catalogo");
            raizPeliculas.appendChild(catalogo);

            // Elemento <pelicula anio="2000"> colgado de <catalogo>
            Element pelicula = documento.createElement("pelicula");
            pelicula.setAttribute("anio", "2000");
            catalogo.appendChild(pelicula);

            // Hijos <titulo> y <director> colgados de <pelicula>
            Element titulo = documento.createElement("titulo");
            titulo.setTextContent("Gladiator");
            pelicula.appendChild(titulo);

            Element director = documento.createElement("director");
            director.setTextContent("Ridley Scott");
            pelicula.appendChild(director);

            // Guardar el árbol DOM en el fichero físico
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(
                    new DOMSource(documento),
                    new StreamResult(new File(RUTA_FICHERO + NOMBRE_FICHERO))
            );
            System.out.println("Documento XML creado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al crear el XML.");
        }
    }

    // 2. Abrir y recorrer un documento XML existente
    public static void leerYRecorrerXml() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Opciones para ignorar comentarios y espacios vacíos del XML
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            // parse() carga el fichero existente en memoria como un árbol DOM
            Document documento = builder.parse(new File(RUTA_FICHERO + NOMBRE_FICHERO));

            // Obtenemos una lista con todas las etiquetas <pelicula>
            NodeList listaPeliculas = documento.getElementsByTagName("pelicula");

            for (int i = 0; i < listaPeliculas.getLength(); i++) {
                // Convertimos cada nodo a Element mediante casting
                Element pelicula = (Element) listaPeliculas.item(i);

                // Leemos el atributo "anio" de la etiqueta <pelicula>
                String anio = pelicula.getAttribute("anio");

                // Leemos el texto interno de sus etiquetas hijas <titulo> y <director>
                String titulo = pelicula.getElementsByTagName("titulo").item(0).getTextContent();
                String director = pelicula.getElementsByTagName("director").item(0).getTextContent();

                System.out.println("Año: " + anio + " | Título: " + titulo + " | Director: " + director);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el XML.");
        }
    }

    // 3. Buscar y modificar el contenido de una etiqueta en un XML existente
    public static void modificarElementoXml(String tituloBuscado, String nuevoTitulo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(new File(RUTA_FICHERO + NOMBRE_FICHERO));

            NodeList listaPeliculas = documento.getElementsByTagName("pelicula");
            boolean encontrada = false;

            for (int i = 0; i < listaPeliculas.getLength(); i++) {
                Element pelicula = (Element) listaPeliculas.item(i);
                Element elementoTitulo = (Element) pelicula.getElementsByTagName("titulo").item(0);

                if (elementoTitulo.getTextContent().equalsIgnoreCase(tituloBuscado)) {
                    // Cambiamos el texto en memoria con setTextContent()
                    elementoTitulo.setTextContent(nuevoTitulo);
                    encontrada = true;
                    break;
                }
            }

            if (!encontrada) {
                System.out.println("No se encontró la película.");
                return;
            }

            // ¡MUY IMPORTANTE! Si no usamos Transformer al final, el cambio no se guarda en el archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(
                    new DOMSource(documento),
                    new StreamResult(new File(RUTA_FICHERO + NOMBRE_FICHERO))
            );
            System.out.println("Título modificado en el XML.");
        } catch (Exception e) {
            System.out.println("Error al modificar el XML.");
        }
    }

    // 4. Añadir un nuevo elemento a un XML que ya existe
    public static void anadirNodoAXmlExistente(String anio, String titulo, String director) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(new File(RUTA_FICHERO + NOMBRE_FICHERO));

            // Buscamos la etiqueta padre <catalogo> donde cuelgan las películas
            Element catalogo = (Element) documento.getElementsByTagName("catalogo").item(0);

            // Creamos la nueva película y sus hijos
            Element nuevaPelicula = documento.createElement("pelicula");
            nuevaPelicula.setAttribute("anio", anio);

            Element elemTitulo = documento.createElement("titulo");
            elemTitulo.setTextContent(titulo);
            nuevaPelicula.appendChild(elemTitulo);

            Element elemDirector = documento.createElement("director");
            elemDirector.setTextContent(director);
            nuevaPelicula.appendChild(elemDirector);

            // Enganchamos la nueva película al <catalogo>
            catalogo.appendChild(nuevaPelicula);

            // Guardamos los cambios con Transformer
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(
                    new DOMSource(documento),
                    new StreamResult(new File(RUTA_FICHERO + NOMBRE_FICHERO))
            );
            System.out.println("Nueva película añadida al XML.");
        } catch (Exception e) {
            System.out.println("Error al añadir al XML.");
        }
    }
}