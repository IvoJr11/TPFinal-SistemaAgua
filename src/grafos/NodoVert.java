package grafos;

public class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    private Object primerAdy; // Lo definimos como Object para que acepte NodoAdy o NodoAdyEti

    public NodoVert(Object elem, NodoVert sigVertice) {
        this.elem = elem;
        this.sigVertice = sigVertice;
        this.primerAdy = null;
    }

    public Object getElem() { return elem; }
    public void setElem(Object elem) { this.elem = elem; }

    public NodoVert getSigVertice() { return sigVertice; }
    public void setSigVertice(NodoVert sig) { this.sigVertice = sig; }

    // Estos métodos ahora manejan Object para ser compatibles con cualquier tipo de arco
    public Object getPrimerAdy() { return primerAdy; }
    public void setPrimerAdy(Object ady) { this.primerAdy = ady; }
}