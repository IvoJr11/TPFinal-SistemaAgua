package especificos;

public class NodoAVLDicc {
    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;

    public NodoAVLDicc(Comparable clave, Object dato, NodoAVLDicc izq, NodoAVLDicc der) {
        this.clave = clave;
        this.dato = dato;
        this.altura = 0; // Altura de hoja es 0
        this.izquierdo = izq;
        this.derecho = der;
    }

    // Getters y Setters
    public Comparable getClave() { return clave; }
    public void setClave(Comparable clave) { this.clave = clave; }
    
    public Object getDato() { return dato; }
    public void setDato(Object dato) { this.dato = dato; }
    
    public int getAltura() { return altura; }
    public void setAltura(int altura) { this.altura = altura; }
    
    public NodoAVLDicc getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoAVLDicc izquierdo) { this.izquierdo = izquierdo; }
    
    public NodoAVLDicc getDerecho() { return derecho; }
    public void setDerecho(NodoAVLDicc derecho) { this.derecho = derecho; }
}