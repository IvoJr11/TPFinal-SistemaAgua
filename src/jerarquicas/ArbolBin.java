package jerarquicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

public class ArbolBin {
    private NodoArbol raiz;

    // Constructor
    public ArbolBin() {
        this.raiz = null;
    }

    // Método para insertar un elemento dado su padre y la posición (I/D)
    public boolean insertar(Object elemNuevo, Object elemPadre, char posicion) {
        boolean exito = true;
        if (this.raiz == null) {
            // Árbol vacío: elemNuevo es la raíz
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            // Buscar el nodo padre
            NodoArbol nodoPadre = obtenerNodo(this.raiz, elemPadre);
            if (nodoPadre != null) {
                if (posicion == 'I' && nodoPadre.getIzquierdo() == null) {
                    nodoPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else if (posicion == 'D' && nodoPadre.getDerecho() == null) {
                    nodoPadre.setDerecho(new NodoArbol(elemNuevo, null, null));
                } else {
                    exito = false; // Posición ocupada o inválida
                }
            } else {
                exito = false; // Padre no encontrado
            }
        }
        return exito;
    }

    // Método auxiliar para buscar un nodo por su elemento
    private NodoArbol obtenerNodo(NodoArbol nodo, Object elem) {
        NodoArbol resultado = null;
        if (nodo != null) {
            if (nodo.getElem().equals(elem)) {
                resultado = nodo;
            } else {
                resultado = obtenerNodo(nodo.getIzquierdo(), elem);
                if (resultado == null) {
                    resultado = obtenerNodo(nodo.getDerecho(), elem);
                }
            }
        }
        return resultado;
    }

    // Método para verificar si el árbol está vacío
    public boolean esVacio() {
        return this.raiz == null;
    }

    // Método para obtener el padre de un elemento
    public Object padre(Object elem) {
        Object padre = null;
        if (this.raiz != null && !this.raiz.getElem().equals(elem)) {
            padre = padreAux(this.raiz, elem);
        }
        return padre;
    }

    private Object padreAux(NodoArbol nodo, Object elem) {
        Object resultado = null;
        if (nodo != null) {
            // Verificar si alguno de los hijos es el elemento buscado
            if ((nodo.getIzquierdo() != null && nodo.getIzquierdo().getElem().equals(elem)) ||
                (nodo.getDerecho() != null && nodo.getDerecho().getElem().equals(elem))) {
                resultado = nodo.getElem();
            } else {
                resultado = padreAux(nodo.getIzquierdo(), elem);
                if (resultado == null) {
                    resultado = padreAux(nodo.getDerecho(), elem);
                }
            }
        }
        return resultado;
    }

    // Método para calcular la altura del árbol
    public int altura() {
        return alturaAux(this.raiz);
    }

    private int alturaAux(NodoArbol nodo) {
        int altura = -1;
        if (nodo != null) {
            altura = Math.max(alturaAux(nodo.getIzquierdo()), alturaAux(nodo.getDerecho())) + 1;
        }
        return altura;
    }

    // Método para obtener el nivel de un elemento
    public int nivel(Object elem) {
        return nivelAux(this.raiz, elem, 0);
    }

    private int nivelAux(NodoArbol nodo, Object elem, int nivelActual) {
        int nivel = -1;
        if (nodo != null) {
            if (nodo.getElem().equals(elem)) {
                nivel = nivelActual;
            } else {
                nivel = nivelAux(nodo.getIzquierdo(), elem, nivelActual + 1);
                if (nivel == -1) {
                    nivel = nivelAux(nodo.getDerecho(), elem, nivelActual + 1);
                }
            }
        }
        return nivel;
    }

    // Método para vaciar el árbol
    public void vaciar() {
        this.raiz = null;
    }

    // Método para clonar el árbol
    public ArbolBin clonar() {
        ArbolBin clon = new ArbolBin();
        clon.raiz = clonarAux(this.raiz);
        return clon;
    }

    private NodoArbol clonarAux(NodoArbol nodo) {
        NodoArbol nuevoNodo = null;
        if (nodo != null) {
            nuevoNodo = new NodoArbol(nodo.getElem(), clonarAux(nodo.getIzquierdo()), clonarAux(nodo.getDerecho()));
        }
        return nuevoNodo;
    }

    // Método para listar en preorden
    public Lista listarPreorden() {
        Lista lista = new Lista();
        listarPreordenAux(this.raiz, lista);
        return lista;
    }

    private void listarPreordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            listarPreordenAux(nodo.getIzquierdo(), lista);
            listarPreordenAux(nodo.getDerecho(), lista);
        }
    }

    // Método para listar en inorden
    public Lista listarInorden() {
        Lista lista = new Lista();
        listarInordenAux(this.raiz, lista);
        return lista;
    }

    private void listarInordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            listarInordenAux(nodo.getIzquierdo(), lista);
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
            listarInordenAux(nodo.getDerecho(), lista);
        }
    }

    // Método para listar en posorden
    public Lista listarPosorden() {
        Lista lista = new Lista();
        listarPosordenAux(this.raiz, lista);
        return lista;
    }

    private void listarPosordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            listarPosordenAux(nodo.getIzquierdo(), lista);
            listarPosordenAux(nodo.getDerecho(), lista);
            lista.insertar(nodo.getElem(), lista.longitud() + 1);
        }
    }

    // Método para listar por niveles (BFS)
    public Lista listarPorNiveles() {
        Lista lista = new Lista();
        if (this.raiz != null) {
            Cola cola = new Cola();
            cola.poner(this.raiz);
            while (!cola.esVacia()) {
                NodoArbol nodoActual = (NodoArbol) cola.obtenerFrente();
                cola.sacar();
                lista.insertar(nodoActual.getElem(), lista.longitud() + 1);
                if (nodoActual.getIzquierdo() != null) {
                    cola.poner(nodoActual.getIzquierdo());
                }
                if (nodoActual.getDerecho() != null) {
                    cola.poner(nodoActual.getDerecho());
                }
            }
        }
        return lista;
    }

    // Método toString para representar el árbol
    @Override
    public String toString() {
        return toStringAux(this.raiz, 0);
    }

    private String toStringAux(NodoArbol nodo, int nivel) {
        String s = "";
        if (nodo != null) {
            // Agregar sangría según el nivel
            for (int i = 0; i < nivel; i++) {
                s += "  ";
            }
            s += nodo.getElem().toString() + "\n";
            s += toStringAux(nodo.getIzquierdo(), nivel + 1);
            s += toStringAux(nodo.getDerecho(), nivel + 1);
        }
        return s;
    }

    public Lista obtenerDescendientes(Object elem) {
        Lista descendientes = new Lista();
        NodoArbol nodo = obtenerNodo(this.raiz, elem);
        if (nodo != null) {
            obtenerDescendientesAux(nodo, descendientes);
        }
        return descendientes;
    }
    
    private void obtenerDescendientesAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            // Agregar hijos (excluyendo el nodo actual)
            if (nodo.getIzquierdo() != null) {
                lista.insertar(nodo.getIzquierdo().getElem(), lista.longitud() + 1);
                obtenerDescendientesAux(nodo.getIzquierdo(), lista);
            }
            if (nodo.getDerecho() != null) {
                lista.insertar(nodo.getDerecho().getElem(), lista.longitud() + 1);
                obtenerDescendientesAux(nodo.getDerecho(), lista);
            }
        }
    }

    public Lista obtenerAncestros(Object elem) {
        Lista ancestros = new Lista();
        obtenerAncestrosAux(this.raiz, elem, ancestros);
        return ancestros;
    }
    
    private boolean obtenerAncestrosAux(NodoArbol nodo, Object elem, Lista lista) {
        if (nodo == null) {
            return false;
        }
        if (nodo.getElem().equals(elem)) {
            return true; // Elemento encontrado
        }
        // Buscar en los subárboles
        if (obtenerAncestrosAux(nodo.getIzquierdo(), elem, lista) || 
            obtenerAncestrosAux(nodo.getDerecho(), elem, lista)) {
            lista.insertar(nodo.getElem(), 1); // Insertar al inicio (para mantener orden raíz -> elem)
            return true;
        }
        return false;
    }

    public Lista frontera() {
        Lista listaHojas = new Lista();
        fronteraAux(this.raiz, listaHojas);
        return listaHojas;
    }
    
    private void fronteraAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
                // Es una hoja: agregar a la lista
                lista.insertar(nodo.getElem(), lista.longitud() + 1);
            } else {
                // Recorrer hijos
                fronteraAux(nodo.getIzquierdo(), lista);
                fronteraAux(nodo.getDerecho(), lista);
            }
        }
    }
}