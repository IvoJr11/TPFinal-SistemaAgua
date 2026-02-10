package TP0.estructuras;

public class GrafoEtiquetado {
    private NodoVert inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    // --- MÉTODOS PÚBLICOS DE ESTRUCTURA ---

    public boolean insertarVertice(Object nuevoElem) {
        NodoVert aux = this.ubicarVertice(nuevoElem);
        boolean exito = false;
        if (aux == null) {
            this.inicio = new NodoVert(nuevoElem, this.inicio);
            exito = true;
        }
        return exito;
    }

    public boolean insertarArco(Object origen, Object destino, double etiqueta) {
        boolean exito = false;
        NodoVert vOrigen = ubicarVertice(origen);
        NodoVert vDestino = ubicarVertice(destino);

        if (vOrigen != null && vDestino != null) {
            NodoAdyEti actualPrimerAdy = (NodoAdyEti) vOrigen.getPrimerAdy();
            vOrigen.setPrimerAdy(new NodoAdyEti(vDestino, actualPrimerAdy, etiqueta));
            exito = true;
        }
        return exito;
    }

    @Override
    public String toString() {
        return toStringAux(this.inicio);
    }

    private String toStringAux(NodoVert auxV) {
        String resultado = "";

        if (auxV != null) {
            String salidaPosterior = toStringAux(auxV.getSigVertice());

            StringBuilder sbActual = new StringBuilder();
            sbActual.append(auxV.getElem()).append(" -> ");

            NodoAdyEti auxA = (NodoAdyEti) auxV.getPrimerAdy();
            while (auxA != null) {
                sbActual.append(auxA.getVerticeDestino().getElem())
                        .append(" (peso:").append(auxA.getEtiqueta()).append("), ");
                auxA = auxA.getSigAdyacente();
            }
            sbActual.append("\n");

            resultado = salidaPosterior + sbActual.toString();
        }

        return resultado;
    }

    // --- ALGORITMOS DE CAMINOS ---

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista caminoResultado = new Lista();
        NodoVert vOrigen = ubicarVertice(origen);
        NodoVert vDestino = ubicarVertice(destino);

        if (vOrigen != null && vDestino != null) {
            Cola q = new Cola();
            Lista caminoInicial = new Lista();
            caminoInicial.insertar(origen, 1);
            q.poner(caminoInicial);
            Lista visitados = new Lista();
            caminoResultado = caminoResultadoAux(q, destino, visitados);
        }
        return caminoResultado;
    }

    public Lista caminoMayorCaudal(Object origen, Object destino) {
        Lista mejorCamino = new Lista();
        double mejorCaudal = -1;
        Lista todosLosCaminos = obtenerTodosLosCaminos(origen, destino);
        int cantCaminos = todosLosCaminos.longitud();

        /*
        No tiene sentido hacer el for, debería estar implementado junto con la recorrida de "obtenerTodosLosCaminos" 
        para mejorar la eficiencia del código.
        */

        for (int i = 1; i <= cantCaminos; i++) {
            Lista caminoCand = (Lista) todosLosCaminos.recuperar(i);
            double caudalPleno = calcularCaudalPleno(caminoCand);
            if (caudalPleno > mejorCaudal) {
                mejorCaudal = caudalPleno;
                mejorCamino = caminoCand;
            }
        }
        return mejorCamino;
    }

    public Lista caminoSinPasarPor(Object origen, Object destino, Object evitar) {
        Lista caminoResultado = new Lista();
        NodoVert vOrigen = ubicarVertice(origen);
        NodoVert vDestino = ubicarVertice(destino);

        if (vOrigen != null && vDestino != null) {
            Cola q = new Cola();
            Lista caminoInicial = new Lista();
            caminoInicial.insertar(origen, 1);
            q.poner(caminoInicial);

            Lista visitados = new Lista();
            visitados.insertar(evitar, 1); // Marcamos 'evitar' como visitado

            caminoResultado = caminoResultadoAux(q, destino, visitados);
        }
        return caminoResultado;
    }

    private Lista caminoResultadoAux(Cola cola, Object destino, Lista visitados) {
        boolean encontrado = false;
        Lista caminoResultado = new Lista();
        while (!cola.esVacia() && !encontrado) {
            Lista caminoActual = (Lista) cola.obtenerFrente();
            cola.sacar();

            Object verticeActualObj = caminoActual.recuperar(caminoActual.longitud());

            if (verticeActualObj.equals(destino)) {
                caminoResultado = caminoActual;
                encontrado = true;
            } else {
                NodoVert vActual = ubicarVertice(verticeActualObj);
                visitados.insertar(verticeActualObj, visitados.longitud() + 1);

                NodoAdyEti auxAdy = (NodoAdyEti) vActual.getPrimerAdy();
                while (auxAdy != null) {
                    Object vecino = auxAdy.getVerticeDestino().getElem();
                    if (visitados.localizar(vecino) < 0 && caminoActual.localizar(vecino) < 0) {
                        Lista nuevoCamino = caminoActual.clone();
                        nuevoCamino.insertar(vecino, nuevoCamino.longitud() + 1);
                        cola.poner(nuevoCamino);
                    }
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }
        }
        return caminoResultado;
    }

    public Lista obtenerTodosLosCaminos(Object origen, Object destino) {
        Lista todos = new Lista();
        Lista visitados = new Lista();
        Lista caminoActual = new Lista();

        NodoVert vOrigen = ubicarVertice(origen);
        NodoVert vDestino = ubicarVertice(destino);

        if (vOrigen != null && vDestino != null) {
            buscarCaminosAux(vOrigen, destino, visitados, caminoActual, todos);
        }
        return todos;
    }

    // --- MÉTODOS PRIVADOS AUXILIARES ---

    /* 
        REHACER para el Final
        Buscar variantes de estos métodos como el camino con la suma de etiquetas más grande, el peso mejor a x, etc.
        Se puede usar variantes para los demás recorridos (agregando controles) para optimizarlos.
    */
    private void buscarCaminosAux(NodoVert actual, Object destino, Lista visitados, Lista caminoActual, Lista todos) {
        Object elemActual = actual.getElem();

        visitados.insertar(elemActual, visitados.longitud() + 1);
        caminoActual.insertar(elemActual, caminoActual.longitud() + 1);

        if (elemActual.equals(destino)) {
            todos.insertar(caminoActual.clone(), todos.longitud() + 1);
        } else {
            NodoAdyEti ady = (NodoAdyEti) actual.getPrimerAdy();
            while (ady != null) {
                Object vecinoElem = ady.getVerticeDestino().getElem();
                if (visitados.localizar(vecinoElem) < 0) {
                    buscarCaminosAux(ady.getVerticeDestino(), destino, visitados, caminoActual, todos);
                }
                ady = ady.getSigAdyacente();
            }
        }

        // no tiene sentido tener ámbos caminos si tienen los mismos datos
        visitados.eliminar(visitados.longitud());
        caminoActual.eliminar(caminoActual.longitud());
    }

    private double calcularCaudalPleno(Lista camino) {
        double minCaudal = Double.MAX_VALUE;
        int longitud = camino.longitud();

        if (longitud < 2) {
            minCaudal = 0.0;
        } else {
            for (int i = 1; i < longitud; i++) {
                Object origen = camino.recuperar(i);
                Object destino = camino.recuperar(i + 1);
                double pesoArco = buscarPesoArco(origen, destino);

                if (pesoArco < minCaudal) {
                    minCaudal = pesoArco;
                }
            }

            if (minCaudal == Double.MAX_VALUE) {
                minCaudal = 0.0;
            }
        }
        return minCaudal;
    }

    private double buscarPesoArco(Object origen, Object destino) {
        double peso = 0.0;
        boolean encontrado = false;

        NodoVert vOrigen = ubicarVertice(origen);

        if (vOrigen != null) {
            NodoAdyEti ady = (NodoAdyEti) vOrigen.getPrimerAdy();
            while (ady != null && !encontrado) {
                if (ady.getVerticeDestino().getElem().equals(destino)) {
                    try {
                        peso = (Double) ady.getEtiqueta();
                        encontrado = true;
                    } catch (Exception e) {
                        peso = 0.0;
                        encontrado = true;
                    }
                }
                ady = ady.getSigAdyacente();
            }
        }
        return peso;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        NodoVert resultado = null;
        boolean encontrado = false;

        while (aux != null && !encontrado) {
            if (aux.getElem().equals(buscado)) {
                resultado = aux;
                encontrado = true;
            } else {
                aux = aux.getSigVertice();
            }
        }
        return resultado;
    }
}