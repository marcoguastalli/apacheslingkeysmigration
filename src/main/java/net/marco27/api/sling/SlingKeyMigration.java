package net.marco27.api.sling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlingKeyMigration {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlingKeyMigration.class);

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong input!");
        }
        LOGGER.info("Source XML {}", args[0]);
        LOGGER.info("Target XML {}", args[1]);
    }

}
