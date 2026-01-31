package grafos;

public class NodoAdy {
    private NodoVert verticeDestino;
    private NodoAdy sigAdyacente;

    public NodoAdy(NodoVert destino, NodoAdy sigAdy) {
        this.verticeDestino = destino;
        this.sigAdyacente = sigAdy;
    }

    // Getters y Setters
    public NodoVert getVerticeDestino() { return verticeDestino; }
    public void setVerticeDestino(NodoVert v) { this.verticeDestino = v; }
    public NodoAdy getSigAdyacente() { return sigAdyacente; }
    public void setSigAdyacente(NodoAdy sig) { this.sigAdyacente = sig; }
}