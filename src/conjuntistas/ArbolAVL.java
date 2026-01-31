package conjuntistas;

import lineales.dinamicas.Lista;

public class ArbolAVL<T extends Comparable<T>> {

    private NodoAVL<T> raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    // --- OPERACIÓN: INSERTAR ---
    public boolean insertar(T elem) {
        boolean[] exito = {false};
        this.raiz = insertarAux(this.raiz, elem, exito);
        return exito[0];
    }

    private NodoAVL<T> insertarAux(NodoAVL<T> nodo, T elem, boolean[] exito) {
        NodoAVL<T> nuevoNodo = nodo;

        if (nodo == null) {
            nuevoNodo = new NodoAVL<>(elem, null, null);
            exito[0] = true;
        } else {
            int comp = elem.compareTo(nodo.getElem());
            if (comp < 0) {
                nodo.setIzquierdo(insertarAux(nodo.getIzquierdo(), elem, exito));
            } else if (comp > 0) {
                nodo.setDerecho(insertarAux(nodo.getDerecho(), elem, exito));
            }
        }

        if (exito[0]) {
            actualizarAltura(nuevoNodo);
            nuevoNodo = balancear(nuevoNodo);
        }
        return nuevoNodo;
    }

    // --- MÉTODOS DE BALANCEO ---
    private NodoAVL<T> balancear(NodoAVL<T> n) {
        int balance = obtenerBalance(n);

        // Caso Izquierda-Izquierda o Izquierda-Derecha
        if (balance > 1) {
            if (obtenerBalance(n.getIzquierdo()) < 0) {
                n.setIzquierdo(rotarIzquierda(n.getIzquierdo())); // Rotación Doble
            }
            n = rotarDerecha(n);
        }
        // Caso Derecha-Derecha o Derecha-Izquierda
        else if (balance < -1) {
            if (obtenerBalance(n.getDerecho()) > 0) {
                n.setDerecho(rotarDerecha(n.getDerecho())); // Rotación Doble
            }
            n = rotarIzquierda(n);
        }
        return n;
    }

    private NodoAVL<T> rotarDerecha(NodoAVL<T> y) {
        NodoAVL<T> x = y.getIzquierdo();
        NodoAVL<T> temp = x.getDerecho();
        x.setDerecho(y);
        y.setIzquierdo(temp);
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private NodoAVL<T> rotarIzquierda(NodoAVL<T> x) {
        NodoAVL<T> y = x.getDerecho();
        NodoAVL<T> temp = y.getIzquierdo();
        y.setIzquierdo(x);
        x.setDerecho(temp);
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    // --- MÉTODOS AUXILIARES DE ALTURA ---
    private void actualizarAltura(NodoAVL<T> n) {
        n.setAltura(1 + Math.max(getAlt(n.getIzquierdo()), getAlt(n.getDerecho())));
    }

    private int getAlt(NodoAVL<T> n) {
        return (n == null) ? -1 : n.getAltura();
    }

    private int obtenerBalance(NodoAVL<T> n) {
        return (n == null) ? 0 : getAlt(n.getIzquierdo()) - getAlt(n.getDerecho());
    }

    // --- OPERACIÓN: ELIMINAR ---
    public boolean eliminar(T elem) {
        boolean[] exito = {false};
        this.raiz = eliminarAux(this.raiz, elem, exito);
        return exito[0];
    }

    private NodoAVL<T> eliminarAux(NodoAVL<T> n, T elem, boolean[] exito) {
        NodoAVL<T> res = n;
        if (n != null) {
            int comp = elem.compareTo(n.getElem());
            if (comp < 0) {
                n.setIzquierdo(eliminarAux(n.getIzquierdo(), elem, exito));
            } else if (comp > 0) {
                n.setDerecho(eliminarAux(n.getDerecho(), elem, exito));
            } else {
                exito[0] = true;
                if (n.getIzquierdo() == null || n.getDerecho() == null) {
                    res = (n.getIzquierdo() != null) ? n.getIzquierdo() : n.getDerecho();
                } else {
                    T sucesor = buscarMinimo(n.getDerecho());
                    n.setElem(sucesor);
                    n.setDerecho(eliminarAux(n.getDerecho(), sucesor, exito));
                    res = n;
                }
            }
        }
        if (res != null && exito[0]) {
            actualizarAltura(res);
            res = balancear(res);
        }
        return res;
    }

    private T buscarMinimo(NodoAVL<T> n) {
        while (n.getIzquierdo() != null) n = n.getIzquierdo();
        return n.getElem();
    }

    // --- RECORRIDO Y OTROS ---
    public Lista listar() {
        Lista l = new Lista();
        listarAux(this.raiz, l);
        return l;
    }

    private void listarAux(NodoAVL<T> n, Lista l) {
        if (n != null) {
            listarAux(n.getIzquierdo(), l);
            l.insertar(n.getElem(), l.longitud() + 1);
            listarAux(n.getDerecho(), l);
        }
    }

    public boolean esVacio() { return this.raiz == null; }
}