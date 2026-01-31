package TP0.estructuras;

public class NodoAVL {

    private Comparable clave;
    private Object dato;
    private int altura;
    private NodoAVL izquierdo;
    private NodoAVL derecho;

    public NodoAVL(Comparable clave, Object dato, NodoAVL izq, NodoAVL der) {
        this.clave = clave;
        this.dato = dato;
        this.izquierdo = izq;
        this.derecho = der;
        this.altura = 0;
    }

    public Comparable getClave() {
        return clave;
    }

    public void setClave(Comparable clave) {
        this.clave = clave;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public NodoAVL getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVL izq) {
        this.izquierdo = izq;
    }

    public NodoAVL getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVL der) {
        this.derecho = der;
    }
}