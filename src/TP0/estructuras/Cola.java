package TP0.estructuras;

public class Cola {
    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object nuevoElem) {
        Nodo nuevo = new Nodo(nuevoElem, null);
        if (this.fin == null) {
            this.frente = nuevo;
        } else {
            this.fin.setEnlace(nuevo);
        }
        this.fin = nuevo;
        return true;
    }

    public boolean sacar() {
        boolean sacado = false;
        if (this.frente != null) {
            this.frente = this.frente.getEnlace();
            if (this.frente == null) {
                this.fin = null;
            }
            sacado = true;
        }
        return sacado;
    }

    public Object obtenerFrente() {
        Object elemento = null;
        if (this.frente != null) {
            elemento = this.frente.getElemento();
        }
        return elemento;
    }

    public boolean esVacia() {
        return this.frente == null;
    }

    public void vaciar() {
        this.fin = null;
        this.frente = null;
    }

    public Cola clone() {
        Cola clon = new Cola();
        if (this.frente != null) {
            clon.frente = new Nodo(this.frente.getElemento(), null);
            Nodo nodoClon = clon.frente;
            Nodo nodoOriginal = this.frente.getEnlace();

            while (nodoOriginal != null) {
                nodoClon.setEnlace(new Nodo(nodoOriginal.getElemento(), null));
                nodoClon = nodoClon.getEnlace();
                nodoOriginal = nodoOriginal.getEnlace();
            }
            clon.fin = nodoClon;
        }
        return clon;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        if (this.frente == null) {
            sb.append("Cola vacia");
        } else {
            Nodo actual = this.frente;
            while (actual != null) {
                sb.append(actual.getElemento());
                if (actual.getEnlace() != null) {
                    sb.append(", ");
                }
                actual = actual.getEnlace();
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}