package grafos;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

public class Grafo {
    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }

    // --- OPERACIONES DE VÉRTICES ---
    public boolean insertarVertice(Object nuevoElem) {
        boolean exito = false;
        NodoVert aux = ubicarVertice(nuevoElem);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoElem, this.inicio);
            exito = true;
        }
        return exito;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    // --- OPERACIONES DE ARCOS ---
    public boolean insertarArco(Object origen, Object destino) {
        boolean exito = false;
        NodoVert vOrigen = ubicarVertice(origen);
        NodoVert vDestino = ubicarVertice(destino);

        if (vOrigen != null && vDestino != null) {
            // Grafo no dirigido: insertamos en ambos sentidos
            vOrigen.setPrimerAdy(new NodoAdy(vDestino, (NodoAdy) vOrigen.getPrimerAdy()));
            vDestino.setPrimerAdy(new NodoAdy(vOrigen, (NodoAdy) vDestino.getPrimerAdy()));
            exito = true;
        }
        return exito;
    }

    // --- RECORRIDO EN PROFUNDIDAD (DFS) ---
    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert nodo, Lista visitados) {
        if (nodo != null) {
            visitados.insertar(nodo.getElem(), visitados.longitud() + 1);
            NodoAdy ady = (NodoAdy) nodo.getPrimerAdy();
            while (ady != null) {
                if (visitados.localizar(ady.getVerticeDestino().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVerticeDestino(), visitados);
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    // --- RECORRIDO EN ANCHURA (BFS) ---
    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                bfsAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void bfsAux(NodoVert nodoInicial, Lista visitados) {
        Cola q = new Cola();
        visitados.insertar(nodoInicial.getElem(), visitados.longitud() + 1);
        q.poner(nodoInicial);
        while (!q.esVacia()) {
            NodoVert u = (NodoVert) q.obtenerFrente();
            q.sacar();
            NodoAdy v = (NodoAdy) u.getPrimerAdy();
            while (v != null) {
                if (visitados.localizar(v.getVerticeDestino().getElem()) < 0) {
                    visitados.insertar(v.getVerticeDestino().getElem(), visitados.longitud() + 1);
                    q.poner(v.getVerticeDestino());
                }
                v = v.getSigAdyacente();
            }
        }
    }

    public boolean existePaso(Object origen, Object destino) {
        boolean existe = false;
        NodoVert vOrigen = ubicarVertice(origen);
        NodoVert vDestino = ubicarVertice(destino);
        if (vOrigen != null && vDestino != null) {
            existe = existePasoAux(vOrigen, destino, new Lista());
        }
        return existe;
    }

    private boolean existePasoAux(NodoVert n, Object destino, Lista visitados) {
        boolean encontrado = false;
        if (n != null) {
            if (n.getElem().equals(destino)) {
                encontrado = true;
            } else {
                visitados.insertar(n.getElem(), visitados.longitud() + 1);
                NodoAdy ady = (NodoAdy) n.getPrimerAdy();
                while (ady != null && !encontrado) {
                    if (visitados.localizar(ady.getVerticeDestino().getElem()) < 0) {
                        encontrado = existePasoAux(ady.getVerticeDestino(), destino, visitados);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return encontrado;
    }


    public Lista caminoMasCorto(Object origen, Object destino) {
    Lista camino = new Lista();
    NodoVert vOrigen = ubicarVertice(origen);
    NodoVert vDestino = ubicarVertice(destino);

    if (vOrigen != null && vDestino != null) {
        // Mapeo para guardar: [Clave: NodoActual, Valor: NodoPadre]
        // Usaremos dos listas paralelas para simular un Diccionario simple
        Lista nodosVisitados = new Lista();
        Lista padres = new Lista();
        Cola q = new Cola();

        boolean encontrado = false;
        q.poner(vOrigen);
        nodosVisitados.insertar(vOrigen, nodosVisitados.longitud() + 1);
        padres.insertar(null, padres.longitud() + 1); // El origen no tiene padre

        while (!q.esVacia() && !encontrado) {
            NodoVert u = (NodoVert) q.obtenerFrente();
            q.sacar();

            if (u.equals(vDestino)) {
                encontrado = true;
            } else {
                NodoAdy v = (NodoAdy) u.getPrimerAdy();
                while (v != null) {
                    NodoVert vertDest = v.getVerticeDestino();
                    if (nodosVisitados.localizar(vertDest) < 0) {
                        nodosVisitados.insertar(vertDest, nodosVisitados.longitud() + 1);
                        padres.insertar(u, padres.longitud() + 1);
                        q.poner(vertDest);
                    }
                    v = v.getSigAdyacente();
                }
            }
        }

        if (encontrado) {
            // Reconstruir el camino desde el destino al origen
            NodoVert actual = vDestino;
            while (actual != null) {
                camino.insertar(actual.getElem(), 1); // Insertar al inicio para que quede ordenado
                int pos = nodosVisitados.localizar(actual);
                actual = (NodoVert) padres.recuperar(pos);
            }
        }
    }
    return camino;
}
}