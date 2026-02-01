package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Equipo;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Clase que consume un json (en este caso svTeams.json)
 * @author mati
 */
public class JsonLoader {

    public static List<Equipo> parseLeague(String path) {
        ObjectMapper mapper = new ObjectMapper();
        // para gestionar fechas
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.readValue(new File(path), new TypeReference<List<Equipo>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse JSON league file", e);
        }
    }
}