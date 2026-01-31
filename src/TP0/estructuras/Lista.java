package TP0.estructuras;

public class Lista {
    private Nodo cabecera;

    public Lista() {
        this.cabecera = null;
    }

    public boolean insertar(Object unElemento, int unaPos) {
        boolean insertado = false;
        if (1 <= unaPos && unaPos <= (this.longitud() + 1)) {
            if (unaPos == 1) {
                this.cabecera = new Nodo(unElemento, this.cabecera);
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < unaPos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(unElemento, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            insertado = true;
        }
        return insertado;
    }

    public boolean eliminar(int unaPos) {
        boolean eliminado = false;
        if (this.cabecera != null && 1 <= unaPos && unaPos <= this.longitud()) {
            if (unaPos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < unaPos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            eliminado = true;
        }
        return eliminado;
    }

    public Object recuperar(int unaPos) {
        Object elemento = null;
        if (1 <= unaPos && unaPos <= this.longitud()) {
            Nodo auxNodo = this.cabecera;
            int i = 1;
            while (i < unaPos) {
                i++;
                auxNodo = auxNodo.getEnlace();
            }
            elemento = auxNodo.getElemento();
        }
        return elemento;
    }

    public int localizar(Object unElemento) {
        int posicion = -1;
        int i = 1;
        Nodo auxNodo = this.cabecera;
        boolean encontrado = false;

        while (auxNodo != null && !encontrado) {
            if (auxNodo.getElemento().equals(unElemento)) {
                posicion = i;
                encontrado = true;
            } else {
                auxNodo = auxNodo.getEnlace();
                i++;
            }
        }
        return posicion;
    }

    public void vaciar() {
        this.cabecera = null;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public int longitud() {
        int tamano = 0;
        Nodo indice = this.cabecera;
        while (indice != null) {
            tamano++;
            indice = indice.getEnlace();
        }
        return tamano;
    }

    public Lista clone() {
        Lista clonLista = new Lista();
        if (this.cabecera != null) {
            clonLista.cabecera = new Nodo(this.cabecera.getElemento(), null);
            Nodo auxClon = clonLista.cabecera;
            Nodo auxOriginal = this.cabecera.getEnlace();
            while (auxOriginal != null) {
                auxClon.setEnlace(new Nodo(auxOriginal.getElemento(), null));
                auxClon = auxClon.getEnlace();
                auxOriginal = auxOriginal.getEnlace();
            }
        }
        return clonLista;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        if (this.cabecera == null) {
            sb.append("Lista vacia");
        } else {
            Nodo auxNodo = this.cabecera;
            while (auxNodo != null) {
                sb.append(auxNodo.getElemento());
                if (auxNodo.getEnlace() != null) {
                    sb.append(", ");
                }
                auxNodo = auxNodo.getEnlace();
            }
        }
        sb.append(" ]");
        return sb.toString();
    }
}