package conjuntistas;

import lineales.dinamicas.Lista;

public class ArbolABB<T extends Comparable<T>> {

    private NodoABB<T> raiz;

    public ArbolABB() {
        this.raiz = null;
    }

    public boolean insertar(T elemento) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoABB<>(elemento, null, null);
        } else {
            exito = insertarAux(this.raiz, elemento);
        }
        return exito;
    }

    private boolean insertarAux(NodoABB<T> nodo, T elemento) {
        boolean exito = true;
        int comp = elemento.compareTo(nodo.getElem());

        if (comp == 0) {
            exito = false;
        } else if (comp < 0) {
            if (nodo.getIzquierdo() != null) {
                exito = insertarAux(nodo.getIzquierdo(), elemento);
            } else {
                nodo.setIzquierdo(new NodoABB<>(elemento, null, null));
            }
        } else {
            if (nodo.getDerecho() != null) {
                exito = insertarAux(nodo.getDerecho(), elemento);
            } else {
                nodo.setDerecho(new NodoABB<>(elemento, null, null));
            }
        }
        return exito;
    }

    public boolean eliminar(T elemento) {
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarAux(this.raiz, elemento, null);
        }
        return exito;
    }

    private boolean eliminarAux(NodoABB<T> nodo, T elemento, NodoABB<T> padre) {
        boolean exito = false;
        if (nodo != null) {
            int comp = elemento.compareTo(nodo.getElem());
            if (comp < 0) {
                exito = eliminarAux(nodo.getIzquierdo(), elemento, nodo);
            } else if (comp > 0) {
                exito = eliminarAux(nodo.getDerecho(), elemento, nodo);
            } else {
                exito = true;
                if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                    quitarNodo(nodo, padre);
                } else if (nodo.getIzquierdo() == null || nodo.getDerecho() == null) {
                    quitarNodo(nodo, padre);
                } else {
                    // Caso 2 hijos: buscamos el mayor del subárbol izquierdo
                    T elemCandidato = buscarMaximo(nodo.getIzquierdo());
                    nodo.setElem(elemCandidato);
                    eliminarAux(nodo.getIzquierdo(), elemCandidato, nodo);
                }
            }
        }
        return exito;
    }

    private void quitarNodo(NodoABB<T> nodo, NodoABB<T> padre) {
        NodoABB<T> hijoValido = (nodo.getIzquierdo() != null) ? nodo.getIzquierdo() : nodo.getDerecho();
        if (padre == null) {
            this.raiz = hijoValido;
        } else {
            if (padre.getIzquierdo() == nodo) {
                padre.setIzquierdo(hijoValido);
            } else {
                padre.setDerecho(hijoValido);
            }
        }
    }

    public boolean pertenece(T elemento) {
        return perteneceAux(this.raiz, elemento);
    }

    private boolean perteneceAux(NodoABB<T> nodo, T elemento) {
        boolean encontrado = false;
        if (nodo != null) {
            int comp = elemento.compareTo(nodo.getElem());
            if (comp == 0) {
                encontrado = true;
            } else if (comp < 0) {
                encontrado = perteneceAux(nodo.getIzquierdo(), elemento);
            } else {
                encontrado = perteneceAux(nodo.getDerecho(), elemento);
            }
        }
        return encontrado;
    }

    public T minimoElem() {
        T min = null;
        if (this.raiz != null) {
            NodoABB<T> aux = this.raiz;
            while (aux.getIzquierdo() != null) {
                aux = aux.getIzquierdo();
            }
            min = aux.getElem();
        }
        return min;
    }

    public T maximoElem() {
        return (this.raiz == null) ? null : buscarMaximo(this.raiz);
    }

    private T buscarMaximo(NodoABB<T> nodo) {
        NodoABB<T> aux = nodo;
        while (aux.getDerecho() != null) {
            aux = aux.getDerecho();
        }
        return aux.getElem();
    }

    // --- LISTAR: Retorna tu clase Lista dinámica ---
    public Lista listar() {
        Lista l = new Lista();
        listarAux(this.raiz, l);
        return l;
    }

    private void listarAux(NodoABB<T> nodo, Lista l) {
        if (nodo != null) {
            listarAux(nodo.getIzquierdo(), l);
            l.insertar(nodo.getElem(), l.longitud() + 1);
            listarAux(nodo.getDerecho(), l);
        }
    }

    // --- LISTAR RANGO ---
    public Lista listarRango(T min, T max) {
        Lista l = new Lista();
        listarRangoAux(this.raiz, min, max, l);
        return l;
    }

    private void listarRangoAux(NodoABB<T> nodo, T min, T max, Lista l) {
        if (nodo != null) {
            T elem = nodo.getElem();
            if (elem.compareTo(min) > 0) {
                listarRangoAux(nodo.getIzquierdo(), min, max, l);
            }
            if (elem.compareTo(min) >= 0 && elem.compareTo(max) <= 0) {
                l.insertar(elem, l.longitud() + 1);
            }
            if (elem.compareTo(max) < 0) {
                listarRangoAux(nodo.getDerecho(), min, max, l);
            }
        }
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }
}