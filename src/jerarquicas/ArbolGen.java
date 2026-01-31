package jerarquicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

public class ArbolGen {

    private NodoGen raiz;

    public ArbolGen() {
        this.raiz = null;
    }

    // --- INSERTAR (Hijo de un padre específico) ---
    public boolean insertar(Object elem, Object padre) {
        boolean exito = false;
        if (this.raiz == null) {
            this.raiz = new NodoGen(elem, null, null);
            exito = true;
        } else {
            NodoGen nodoPadre = buscarNodo(this.raiz, padre);
            if (nodoPadre != null) {
                // Se inserta como el hijo más a la izquierda
                NodoGen nuevoHijo = new NodoGen(elem, null, nodoPadre.getHijoIzquierdo());
                nodoPadre.setHijoIzquierdo(nuevoHijo);
                exito = true;
            }
        }
        return exito;
    }

    // --- INSERTAR POR POSICIÓN ---
    // Inserta elem como el n-ésimo hijo del padre indicado
    public boolean insertarPorPosicion(Object elem, Object padre, int pos) {
        boolean exito = false;
        if (this.raiz != null) {
            NodoGen nodoPadre = buscarNodo(this.raiz, padre);
            if (nodoPadre != null && pos >= 1) {
                if (pos == 1) {
                    nodoPadre.setHijoIzquierdo(new NodoGen(elem, null, nodoPadre.getHijoIzquierdo()));
                } else {
                    NodoGen aux = nodoPadre.getHijoIzquierdo();
                    int i = 1;
                    while (aux != null && i < pos - 1) {
                        aux = aux.getHermanoDerecho();
                        i++;
                    }
                    if (aux != null) {
                        aux.setHermanoDerecho(new NodoGen(elem, null, aux.getHermanoDerecho()));
                    }
                }
                exito = true;
            }
        }
        return exito;
    }

    private NodoGen buscarNodo(NodoGen nodo, Object buscado) {
        NodoGen resultado = null;
        if (nodo != null) {
            if (nodo.getElem().equals(buscado)) {
                resultado = nodo;
            } else {
                resultado = buscarNodo(nodo.getHijoIzquierdo(), buscado);
                if (resultado == null) {
                    resultado = buscarNodo(nodo.getHermanoDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    // --- PERTENECE, VACÍO, VACIAR ---
    public boolean pertenece(Object elem) {
        return (buscarNodo(this.raiz, elem) != null);
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

    // --- PADRE ---
    public Object padre(Object elem) {
        Object resultado = null;
        if (this.raiz != null && !this.raiz.getElem().equals(elem)) {
            resultado = padreAux(this.raiz, elem);
        }
        return resultado;
    }

    private Object padreAux(NodoGen nodo, Object buscado) {
        Object res = null;
        if (nodo != null) {
            NodoGen hijo = nodo.getHijoIzquierdo();
            boolean encontrado = false;
            while (hijo != null && !encontrado) {
                if (hijo.getElem().equals(buscado)) {
                    encontrado = true;
                    res = nodo.getElem();
                }
                hijo = hijo.getHermanoDerecho();
            }
            if (!encontrado) {
                res = padreAux(nodo.getHijoIzquierdo(), buscado);
                if (res == null) {
                    res = padreAux(nodo.getHermanoDerecho(), buscado);
                }
            }
        }
        return res;
    }

    // --- ALTURA Y NIVEL ---
    public int altura() {
        return alturaAux(this.raiz);
    }

    private int alturaAux(NodoGen nodo) {
        int alt = -1;
        if (nodo != null) {
            alt = Math.max(alturaAux(nodo.getHijoIzquierdo()) + 1, alturaAux(nodo.getHermanoDerecho()));
        }
        return alt;
    }

    public int nivel(Object elem) {
        return nivelAux(this.raiz, elem, 0);
    }

    private int nivelAux(NodoGen nodo, Object buscado, int nivActual) {
        int res = -1;
        if (nodo != null) {
            if (nodo.getElem().equals(buscado)) {
                res = nivActual;
            } else {
                res = nivelAux(nodo.getHijoIzquierdo(), buscado, nivActual + 1);
                if (res == -1) {
                    res = nivelAux(nodo.getHermanoDerecho(), buscado, nivActual);
                }
            }
        }
        return res;
    }

    // --- ANCESTROS ---
    public Lista ancestros(Object elem) {
        Lista l = new Lista();
        ancestrosAux(this.raiz, elem, l);
        return l;
    }

    private boolean ancestrosAux(NodoGen nodo, Object buscado, Lista l) {
        boolean encontrado = false;
        if (nodo != null) {
            if (nodo.getElem().equals(buscado)) {
                encontrado = true;
            } else {
                encontrado = ancestrosAux(nodo.getHijoIzquierdo(), buscado, l);
                if (encontrado) {
                    l.insertar(nodo.getElem(), l.longitud() + 1);
                } else {
                    encontrado = ancestrosAux(nodo.getHermanoDerecho(), buscado, l);
                }
            }
        }
        return encontrado;
    }

    // --- RECORRIDOS ---
    public Lista listarPreorden() {
        Lista l = new Lista();
        preordenAux(this.raiz, l);
        return l;
    }

    private void preordenAux(NodoGen nodo, Lista l) {
        if (nodo != null) {
            l.insertar(nodo.getElem(), l.longitud() + 1);
            preordenAux(nodo.getHijoIzquierdo(), l);
            preordenAux(nodo.getHermanoDerecho(), l);
        }
    }

    public Lista listarInorden() {
        Lista l = new Lista();
        inordenAux(this.raiz, l);
        return l;
    }

    private void inordenAux(NodoGen nodo, Lista l) {
        if (nodo != null) {
            inordenAux(nodo.getHijoIzquierdo(), l);
            l.insertar(nodo.getElem(), l.longitud() + 1);
            if (nodo.getHijoIzquierdo() != null) {
                NodoGen aux = nodo.getHijoIzquierdo().getHermanoDerecho();
                while (aux != null) {
                    inordenAux(aux, l);
                    aux = aux.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarPosorden() {
        Lista l = new Lista();
        posordenAux(this.raiz, l);
        return l;
    }

    private void posordenAux(NodoGen nodo, Lista l) {
        if (nodo != null) {
            posordenAux(nodo.getHijoIzquierdo(), l);
            l.insertar(nodo.getElem(), l.longitud() + 1);
            posordenAux(nodo.getHermanoDerecho(), l);
        }
    }

    public Lista listarPorNiveles() {
        Lista l = new Lista();
        if (this.raiz != null) {
            Cola q = new Cola();
            q.poner(this.raiz);
            while (!q.esVacia()) {
                NodoGen nodo = (NodoGen) q.obtenerFrente();
                q.sacar();
                l.insertar(nodo.getElem(), l.longitud() + 1);
                NodoGen hijo = nodo.getHijoIzquierdo();
                while (hijo != null) {
                    q.poner(hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return l;
    }

    // --- CLONE Y TOSTRING ---
    public ArbolGen clone() {
        ArbolGen clon = new ArbolGen();
        clon.raiz = cloneAux(this.raiz);
        return clon;
    }

    private NodoGen cloneAux(NodoGen nodo) {
        return (nodo == null) ? null : new NodoGen(nodo.getElem(), cloneAux(nodo.getHijoIzquierdo()), cloneAux(nodo.getHermanoDerecho()));
    }

    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen nodo) {
        String s = "";
        if (nodo != null) {
            s += nodo.getElem().toString() + " -> ";
            NodoGen hijo = nodo.getHijoIzquierdo();
            while (hijo != null) {
                s += hijo.getElem().toString() + (hijo.getHermanoDerecho() != null ? ", " : "");
                hijo = hijo.getHermanoDerecho();
            }
            s += "\n" + toStringAux(nodo.getHijoIzquierdo()) + toStringAux(nodo.getHermanoDerecho());
        }
        return s;
    }
}