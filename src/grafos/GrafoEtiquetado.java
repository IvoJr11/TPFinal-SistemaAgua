package grafos;

public class GrafoEtiquetado {
    private NodoVert inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarArco(Object origen, Object destino, double etiqueta) {
        boolean exito = false;
        NodoVert vOrigen = ubicarVertice(origen);
        NodoVert vDestino = ubicarVertice(destino);

        if (vOrigen != null && vDestino != null) {
            // Casteamos el resultado de getPrimerAdy a NodoAdyEti
            NodoAdyEti actualPrimerAdy = (NodoAdyEti) vOrigen.getPrimerAdy();
            vOrigen.setPrimerAdy(new NodoAdyEti(vDestino, actualPrimerAdy, etiqueta));
            
            // Para grafo no dirigido:
            NodoAdyEti actualPrimerAdyDest = (NodoAdyEti) vDestino.getPrimerAdy();
            vDestino.setPrimerAdy(new NodoAdyEti(vOrigen, actualPrimerAdyDest, etiqueta));
            
            exito = true;
        }
        return exito;
    }

    public String toString() {
        String s = "";
        NodoVert auxV = this.inicio;
        while (auxV != null) {
            s += auxV.getElem() + " -> ";
            // Aquí también aplicamos el casteo para poder recorrer la lista de adyacentes
            NodoAdyEti auxA = (NodoAdyEti) auxV.getPrimerAdy();
            while (auxA != null) {
                s += auxA.getVerticeDestino().getElem() + " (peso:" + auxA.getEtiqueta() + "), ";
                auxA = auxA.getSigAdyacente();
            }
            s += "\n";
            auxV = auxV.getSigVertice();
        }
        return s;
    }
}