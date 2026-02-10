package TP0.estructuras;

public class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    // Esto debería ser Tipo NodoAdy
    private Object primerAdy;

    public NodoVert(Object elem, NodoVert sigVertice) {
        this.elem = elem;
        this.sigVertice = sigVertice;
        this.primerAdy = null;
    }

    public Object getElem() {
        return elem;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public NodoVert getSigVertice() {
        return sigVertice;
    }

    public void setSigVertice(NodoVert sig) {
        this.sigVertice = sig;
    }

    public Object getPrimerAdy() {
        return primerAdy;
    }

    public void setPrimerAdy(Object ady) {
        this.primerAdy = ady;
    }
}