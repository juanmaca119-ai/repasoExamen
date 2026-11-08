package REPASO_EXAMEN_2;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** Apartado 8: XML con DOM (crear, leer, modificar, guardar). */
public class Ej07_XML {

    static void agregar(Document doc, Element raiz, String plataforma, String titulo, String anio) {
        Element v = doc.createElement("videojuego");
        v.setAttribute("anio", anio);                     // el año va como atributo

        Element p = doc.createElement("plataforma");
        p.setTextContent(plataforma);
        v.appendChild(p);

        Element t = doc.createElement("titulo");
        t.setTextContent(titulo);
        v.appendChild(t);

        raiz.appendChild(v);
    }

    // 8.5 Guardar un documento XML
    static void guardar(Document doc, File f) throws Exception {
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        tr.transform(new DOMSource(doc), new StreamResult(f));
    }

    // Al releer un XML con sangrías aparecen nodos de texto en blanco; se eliminan antes de guardar
    static void limpiar(Node n) {
        NodeList hijos = n.getChildNodes();
        for (int i = hijos.getLength() - 1; i >= 0; i--) {
            Node h = hijos.item(i);
            if (h.getNodeType() == Node.TEXT_NODE && h.getTextContent().isBlank()) {
                n.removeChild(h);
            } else {
                limpiar(h);
            }
        }
    }

    static void mostrar(Document doc) {
        NodeList lista = doc.getElementsByTagName("videojuego");
        for (int i = 0; i < lista.getLength(); i++) {
            Element v = (Element) lista.item(i);
            String plataforma = v.getElementsByTagName("plataforma").item(0).getTextContent();
            String titulo = v.getElementsByTagName("titulo").item(0).getTextContent();
            System.out.println(plataforma + " | " + v.getAttribute("anio") + " | " + titulo);
        }
    }

    public static void main(String[] args) throws Exception {
        File f = new File("videojuegos.xml");

        // 8.6 Crear un documento (ejercicio 1)
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element raiz = doc.createElement("videojuegos");
        doc.appendChild(raiz);
        agregar(doc, raiz, "PC", "Minecraft", "2011");
        agregar(doc, raiz, "PC", "Half-Life 2", "2004");
        agregar(doc, raiz, "PlayStation", "God of War", "2018");
        guardar(doc, f);

        // 8.2 y 8.3 Leer y recorrer el árbol (ejercicio 2)
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document leido = db.parse(f);
        leido.getDocumentElement().normalize();
        limpiar(leido);
        mostrar(leido);

        // 8.4 Modificar (ejercicio 3)
        NodeList lista = leido.getElementsByTagName("videojuego");
        for (int i = 0; i < lista.getLength(); i++) {
            Element v = (Element) lista.item(i);
            Element titulo = (Element) v.getElementsByTagName("titulo").item(0);
            if (titulo.getTextContent().equals("Half-Life 2")) {
                titulo.setTextContent("Half-Life 2: Episode One");
            }
        }

        // Añadir un nuevo nodo (ejercicio 4)
        agregar(leido, leido.getDocumentElement(), "Nintendo Switch", "Celeste", "2018");
        guardar(leido, f);

        System.out.println("--- Tras modificar y añadir ---");
        mostrar(db.parse(f));
    }
}
