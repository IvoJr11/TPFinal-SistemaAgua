package TP0.modelo;

import java.util.Objects;

/**
 * Clase que representa una clave compuesta por dos Strings.
 * Utilizada para el HashMap de Tuberías.
 */
public class ClaveDosStrings {

    private String nom1;
    private String nom2;

    public ClaveDosStrings(String nom1, String nom2) {
        this.nom1 = nom1;
        this.nom2 = nom2;
    }

    @Override
    public boolean equals(Object obj) {
        boolean sonIguales = false;
        if (this == obj) {
            sonIguales = true;
        } else {
            if (obj != null && this.getClass() == obj.getClass()) {
                ClaveDosStrings otraClave = (ClaveDosStrings) obj;
                sonIguales = Objects.equals(this.nom1, otraClave.nom1) &&
                        Objects.equals(this.nom2, otraClave.nom2);
            }
        }
        return sonIguales;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.nom1, this.nom2);
    }

    @Override
    public String toString() {
        return "(" + this.nom1 + " -> " + this.nom2 + ")";
    }
}