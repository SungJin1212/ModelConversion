package CodeGeneration.Parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class PreprocessParser { // transform whole xml file -> sub models
    public ArrayList<String> getXMLData(String url) {

        ArrayList<String> generatedNames = new ArrayList<>(0);

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(url);

            Element rootElement = document.getDocumentElement();
            NodeList instanceNodes = rootElement.getElementsByTagName("MODEL");
            //System.out.println(instanceNodes.getLength());

            for(int i=0; i<instanceNodes.getLength(); i++) {
                Element e = (Element) instanceNodes.item(i);
                String modelType = e.getAttributes().getNamedItem("modeltype").getNodeValue();

                try {

                    String fileName = e.getAttributes().getNamedItem("name").getNodeValue();
                    fileName += ".xml";
                    Document target = builder.newDocument();
                    Node copied = target.importNode(e, true);
                    target.appendChild(copied);

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource domSource = new DOMSource(target);
                    StreamResult streamResult = new StreamResult(new File(fileName));
                    transformer.transform(domSource, streamResult);
                    generatedNames.add(fileName);
                } catch (TransformerException tfe) {
                    tfe.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return generatedNames;

    }
}
