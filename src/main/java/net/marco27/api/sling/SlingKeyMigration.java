package net.marco27.api.sling;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SlingKeyMigration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlingKeyMigration.class);

    public static void main(String[] args) {
        /*
         * if (args.length != 2) { throw new IllegalArgumentException("Wrong input!"); }
         * 
         * 
         * final String sourceXmlPath = args[0]; final String targetXmlPath = args[1]; LOGGER.info("Source XML {}", sourceXmlPath);
         * LOGGER.info("Target XML {}", targetXmlPath);
         */
        parseXml(
                "/Users/marco27/dev/PFNEO/pf-neo/pf-neo-components/pf-neo-components-package/src/main/jcr_root/apps/pfch/components/i18n/en.xml");
    }

    private static void parseXml(final String sourceXmlPath) {
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
                        LOGGER.info("key:   " + qName);
                        LOGGER.info("value: " + value);
                        LOGGER.info("\n");
                    }
                }

            };

            saxParser.parse(sourceXmlPath, handler);

        } catch (Exception e) {
            LOGGER.error("Error!", e);
        }

    }

}
