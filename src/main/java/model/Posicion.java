package model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enum que define las posiciones posibles para los jugadores
 * @author mati
 */
public enum Posicion {

    @JsonProperty("S") SETTER("Colocador", "S"),
    @JsonProperty("OH") OUTSIDE_HITTER("Atacante Izquierda", "OH"),
    @JsonProperty("OP") OPPOSITE_HITTER("Atacante Derecha", "OP"),
    @JsonProperty("MB") MIDDLE_BLOCKER("Central", "MB"),
    @JsonProperty("L") LIBERO("Líbero", "L");

    private final String nombreCastellano;
    private final String abreviatura;

    // Constructor del Enum
    Posicion(String nombreCastellano, String abreviatura) {
        this.nombreCastellano = nombreCastellano;
        this.abreviatura = abreviatura;
    }

    // Getters por si necesitas mostrarlos en la UI o consola
    public String getNombreCastellano() {
        return nombreCastellano;
    }

    public String getAbreviatura() {
        return abreviatura;
    }


}
