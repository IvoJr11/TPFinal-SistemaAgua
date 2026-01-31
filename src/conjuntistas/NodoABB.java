package conjuntistas;

// T extiende a Comparable para asegurar que los elementos se puedan comparar entre sí
public class NodoABB<T extends Comparable<T>> {
    private T elem;
    private NodoABB<T> izquierdo;
    private NodoABB<T> derecho;

    public NodoABB(T unElem, NodoABB<T> unIzquierdo, NodoABB<T> unDerecho) {
        this.elem = unElem;
        this.izquierdo = unIzquierdo;
        this.derecho = unDerecho;
    }

    public void setElem(T unElem) {
        this.elem = unElem;
    }

    public T getElem() {
        return this.elem;
    }

    public void setIzquierdo(NodoABB<T> unIzquierdo) {
        this.izquierdo = unIzquierdo;
    }

    public NodoABB<T> getIzquierdo() {
        return this.izquierdo;
    }

    public void setDerecho(NodoABB<T> unDerecho) {
        this.derecho = unDerecho;
    }

    public NodoABB<T> getDerecho() {
        return this.derecho;
    }
}