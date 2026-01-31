package grafos;

public class NodoAdyEti {
    private NodoVert verticeDestino;
    private NodoAdyEti sigAdyacente;
    private double etiqueta;

    public NodoAdyEti(NodoVert destino, NodoAdyEti sigAdy, double etiqueta) {
        this.verticeDestino = destino;
        this.sigAdyacente = sigAdy;
        this.etiqueta = etiqueta;
    }

    public NodoVert getVerticeDestino() { return verticeDestino; }
    public NodoAdyEti getSigAdyacente() { return sigAdyacente; }
    public void setSigAdyacente(NodoAdyEti sig) { this.sigAdyacente = sig; }
    public double getEtiqueta() { return etiqueta; }
}