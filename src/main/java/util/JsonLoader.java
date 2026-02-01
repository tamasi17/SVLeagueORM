package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Equipo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Clase que consume un json (en este caso svTeams.json)
 * @author mati
 */
public class JsonLoader {

    private static final Logger logger = LogManager.getLogger(JsonLoader.class);

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // forgiving

    public static List<Equipo> parseLeague(String path) {
        try {
            return MAPPER.readValue(new File(path), new TypeReference<List<Equipo>>() {});
        } catch (IOException e) {
            logger.error("Error parsing the league JSON file at path: {}", path, e);
            throw new RuntimeException("League data could not be initialized from " + path, e);
        }
    }
}