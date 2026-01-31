package conjuntistas;

public class NodoAVL<T extends Comparable<T>> {
    private T elem;
    private int altura;
    private NodoAVL<T> izquierdo;
    private NodoAVL<T> derecho;

    public NodoAVL(T elemento, NodoAVL<T> izq, NodoAVL<T> der) {
        this.elem = elemento;
        this.izquierdo = izq;
        this.derecho = der;
        this.altura = 0; // Se inicializa como hoja
    }

    // Getters y Setters
    public T getElem() { return elem; }
    public void setElem(T elem) { this.elem = elem; }

    public int getAltura() { return altura; }
    public void setAltura(int altura) { this.altura = altura; }

    public NodoAVL<T> getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoAVL<T> izq) { this.izquierdo = izq; }

    public NodoAVL<T> getDerecho() { return derecho; }
    public void setDerecho(NodoAVL<T> der) { this.derecho = der; }
}