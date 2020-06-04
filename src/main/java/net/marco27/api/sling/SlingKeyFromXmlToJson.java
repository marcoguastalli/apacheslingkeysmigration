package net.marco27.api.sling;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import net.marco27.api.sling.domain.SlingKeys;

/**
 * Convert this:
 *
 * 	<pfch.pages.homepage.description
 * 			jcr:primaryType="sling:MessageEntry"
 * 			sling:key="pfch.pages.homepage.description"
 * 			sling:message="Description Home Page"/>
 *
 * Into this:
 *
 *  {
 *   "pfch.pages.homepage.description": "Description Home Page"
 *  }
 *
 */
public class SlingKeyFromXmlToJson {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlingKeyFromXmlToJson.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong input!");
        }
        final String sourceXmlPath = args[0];
        final String targetJsonPath = args[1];
        LOGGER.info("Source XML {}", sourceXmlPath);
        LOGGER.info("Target JSON {}", targetJsonPath);

        final List<SlingKeys> slingKeysList = parseSourceXml(sourceXmlPath);
        LOGGER.info("Found {} keys", slingKeysList.size());
        writeJsonXml(targetJsonPath, slingKeysList);
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

    private static void writeJsonXml(final String targetJsonPath, final List<SlingKeys> slingKeysList) {
        try {
            JSONObject jsonObject = new JSONObject();
            for (final SlingKeys slingKeys : slingKeysList) {
                jsonObject.put(slingKeys.getKey(), slingKeys.getMessage());
            }
            FileWriter fileWriter = new FileWriter(targetJsonPath);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (Exception e) {
            LOGGER.error("Error!", e);
        }
    }

}
