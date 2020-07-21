package net.marco27.api.sling;

import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Read a json with keys and values and write a json with ordered keys */
public class OrderJsonKeys {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderJsonKeys.class);
    private static final String DOUBLE_QUOTE = "\"";

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong input!");
        }
        final String sourceJsonPath = args[0];
        final String targetJsonPath = args[1];
        LOGGER.info("Source JSON {}", sourceJsonPath);
        LOGGER.info("Target JSON {}", targetJsonPath);

        final List<String> slingKeysList = readSourceJson(sourceJsonPath);
        LOGGER.info("Found {} keys", slingKeysList.size());
        writeTargetJson(targetJsonPath, slingKeysList);
    }

    private static List<String> readSourceJson(final String sourceJsonPath) {
        LinkedList<String> result = new LinkedList<>();

        try (FileReader reader = new FileReader(sourceJsonPath)) {
            // parse json
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            // order json by key
            Set<Map.Entry<String, String>> entries = jsonObject.entrySet();
            TreeSet<String> treeSet = new TreeSet<>();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                treeSet.add(key);
            }
            // create result
            for (final String key : treeSet) {
                result.add(DOUBLE_QUOTE + key + DOUBLE_QUOTE + ": " + DOUBLE_QUOTE + jsonObject.get(key) + DOUBLE_QUOTE + ",\r");
            }
        } catch (Exception e) {
            LOGGER.error("Error!", e);
        }

        return result;
    }

    private static void writeTargetJson(final String targetJsonPath, final List<String> slingKeysList) {
        try {
            File file = new File(targetJsonPath);
            FileUtils.writeLines(file, slingKeysList, false);
            LOGGER.debug("" + slingKeysList);
        } catch (Exception e) {
            LOGGER.error("Error!", e);
        }
    }

}
