package net.marco27.api.sling;

import net.marco27.api.sling.domain.SlingKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SlingKeyMigration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlingKeyMigration.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong input!");
        }
        final String sourceXmlPath = args[0];
        final String targetXmlPath = args[1];
        LOGGER.info("Source XML {}", sourceXmlPath);
        LOGGER.info("Target XML {}", targetXmlPath);

        final List<SlingKeys> slingKeysList = parseSourceXml(sourceXmlPath);
        LOGGER.info("Found {} keys", slingKeysList.size());

        writeTargetXml(targetXmlPath, slingKeysList);
    }

    private static List<SlingKeys> parseSourceXml(final String sourceXmlPath) {
        List<SlingKeys> result = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                boolean isKey = false;
                String value = "";

                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.startsWith("pfch.")) {
                        isKey = true;
                        value = attributes.getValue("sling:message");
                    }
                }

                public void endElement(String uri, String localName, String qName) {
                    if (isKey) {
                        LOGGER.debug("key:   " + qName);
                        LOGGER.debug("value: " + value);
                        LOGGER.debug("\n");
                        result.add(new SlingKeys(qName, value));
                    }
                }
            };
            saxParser.parse(sourceXmlPath, handler);
        } catch (Exception e) {
            LOGGER.error("Error!", e);
        }
        return result;
    }

    private static void writeTargetXml(final String targetXmlPath, final List<SlingKeys> slingKeysList) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = docBuilder.newDocument();
            Element rootElement = document.createElement("jcr:root");
            rootElement.setAttribute("xmlns:sling", "http://sling.apache.org/jcr/sling/1.0");
            rootElement.setAttribute("xmlns:mix", "http://www.jcp.org/jcr/mix/1.0");
            rootElement.setAttribute("xmlns:jcr", "http://www.jcp.org/jcr/1.0");
            rootElement.setAttribute("xmlns:nt", "http://www.jcp.org/jcr/nt/1.0");
            rootElement.setAttribute("jcr:language", "en");
            rootElement.setAttribute("jcr:mixinTypes", "[mix:language]");
            rootElement.setAttribute("jcr:primaryType", "nt:folder");
            document.appendChild(rootElement);

            for (final SlingKeys slingKeys : slingKeysList) {
                Element element = document.createElement(slingKeys.getKey());
                element.setAttribute("jcr:primaryType", slingKeys.getPrimaryType());
                element.setAttribute("sling:key", slingKeys.getKey());
                element.setAttribute("sling:message", slingKeys.getMessage());
                rootElement.appendChild(element);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(targetXmlPath));
            transformer.transform(domSource, streamResult);

        } catch (Exception e) {
            LOGGER.error("Error!", e);
        }
    }

}
