package model;

/**
 * Enum que define las posiciones posibles para los jugadores
 * @author mati
 */
public enum Posicion {

    SETTER("Colocador", "S"),
    OUTSIDE_HITTER("Atacante Izquierda", "OH"),
    OPPOSITE_HITTER("Atacante Derecha", "OP"),
    MIDDLE_BLOCKER("Central", "MB"),
    LIBERO("Líbero", "L");

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
