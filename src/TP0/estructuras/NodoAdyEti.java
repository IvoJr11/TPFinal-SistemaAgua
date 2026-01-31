package TP0.estructuras;

public class NodoAdyEti {
    private NodoVert verticeDestino;
    private NodoAdyEti sigAdyacente;
    private Object etiqueta;

    public NodoAdyEti(NodoVert destino, NodoAdyEti sigAdy, Object etiqueta) {
        this.verticeDestino = destino;
        this.sigAdyacente = sigAdy;
        this.etiqueta = etiqueta;
    }

    public NodoVert getVerticeDestino() {
        return verticeDestino;
    }

    public NodoAdyEti getSigAdyacente() {
        return sigAdyacente;
    }

    public void setSigAdyacente(NodoAdyEti sig) {
        this.sigAdyacente = sig;
    }

    public Object getEtiqueta() {
        return etiqueta;
    }
}