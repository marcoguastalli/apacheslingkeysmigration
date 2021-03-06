package net.marco27.api.sling;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class SlingKeyMigrationTest {

    private static final String SOURCE_XML_PATH = "/en.xml";
    private static final String TARGET_XML_PATH = "/out.xml";

    @Test
    public void testMain() {
        final String sourceXmlPath = new File(getClass().getResource(SOURCE_XML_PATH).getFile()).getAbsolutePath();
        final String path = StringUtils.removeEnd(sourceXmlPath, SOURCE_XML_PATH);
        final String targetXmlPath = new File(path + TARGET_XML_PATH).getAbsolutePath();
        final String[] args = new String[] { sourceXmlPath, targetXmlPath };
        SlingKeyMigration.main(args);
    }
}
