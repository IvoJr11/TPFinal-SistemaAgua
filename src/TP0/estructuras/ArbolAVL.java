package TP0.estructuras;

public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    // --- MÉTODOS PÚBLICOS ---

    public boolean insertar(Comparable clave, Object dato) {
        boolean[] exito = { false };
        this.raiz = insertarAux(this.raiz, clave, dato, exito);
        return exito[0];
    }

    public boolean eliminar(Comparable clave) {
        boolean[] exito = { false };
        this.raiz = eliminarAux(this.raiz, clave, exito);
        return exito[0];
    }

    public Object recuperar(Comparable clave) {
        return recuperarAux(this.raiz, clave);
    }

    public Lista listar() {
        Lista l = new Lista();
        listarAux(this.raiz, l);
        return l;
    }

    public Lista listarRango(Comparable min, Comparable max) {
        Lista resultado = new Lista();
        listarRangoAux(this.raiz, min, max, resultado);
        return resultado;
    }

    @Override
    public String toString() {
        if (this.raiz == null) {
            return "Árbol AVL Vacío";
        }
        return toStringAux(this.raiz);
    }

    // --- MÉTODOS PRIVADOS (Lógica Recursiva) ---

    private NodoAVL insertarAux(NodoAVL n, Comparable clave, Object dato, boolean[] exito) {
        NodoAVL resultado = n;

        if (n == null) {
            resultado = new NodoAVL(clave, dato, null, null);
            exito[0] = true;
        } else {
            int comp = clave.compareTo(n.getClave());

            if (comp < 0) {
                n.setIzquierdo(insertarAux(n.getIzquierdo(), clave, dato, exito));
            } else if (comp > 0) {
                n.setDerecho(insertarAux(n.getDerecho(), clave, dato, exito));
            } else {
                exito[0] = false; // Clave repetida
            }

            if (exito[0]) {
                actualizarAltura(n);
                resultado = balancear(n);
            }
        }
        return resultado;
    }

    private NodoAVL eliminarAux(NodoAVL n, Comparable clave, boolean[] exito) {
        NodoAVL res = n;

        if (n != null) {
            int comp = clave.compareTo(n.getClave());

            if (comp < 0) {
                n.setIzquierdo(eliminarAux(n.getIzquierdo(), clave, exito));
            } else if (comp > 0) {
                n.setDerecho(eliminarAux(n.getDerecho(), clave, exito));
            } else {
                exito[0] = true;
                if (n.getIzquierdo() == null || n.getDerecho() == null) {
                    res = (n.getIzquierdo() != null) ? n.getIzquierdo() : n.getDerecho();
                } else {
                    NodoAVL sucesor = buscarNodoMinimo(n.getDerecho());
                    n.setClave(sucesor.getClave());
                    n.setDato(sucesor.getDato());
                    n.setDerecho(eliminarAux(n.getDerecho(), sucesor.getClave(), exito));
                    res = n;
                }
            }

            if (res != null) {
                actualizarAltura(res);
                res = balancear(res);
            }
        }
        return res;
    }

    private Object recuperarAux(NodoAVL n, Comparable clave) {
        Object encontrado = null;
        if (n != null) {
            int comp = clave.compareTo(n.getClave());
            if (comp == 0) {
                encontrado = n.getDato();
            } else if (comp < 0) {
                encontrado = recuperarAux(n.getIzquierdo(), clave);
            } else {
                encontrado = recuperarAux(n.getDerecho(), clave);
            }
        }
        return encontrado;
    }

    private void listarAux(NodoAVL n, Lista l) {
        if (n != null) {
            listarAux(n.getIzquierdo(), l);
            l.insertar(n.getDato(), l.longitud() + 1);
            listarAux(n.getDerecho(), l);
        }
    }

    private void listarRangoAux(NodoAVL n, Comparable min, Comparable max, Lista ls) {
        if (n != null) {
            Comparable clave = n.getClave();
            if (clave.compareTo(min) > 0) {
                listarRangoAux(n.getIzquierdo(), min, max, ls);
            }
            if (clave.compareTo(min) >= 0 && clave.compareTo(max) <= 0) {
                ls.insertar(n.getDato(), ls.longitud() + 1);
            }
            if (clave.compareTo(max) < 0) {
                listarRangoAux(n.getDerecho(), min, max, ls);
            }
        }
    }

    private String toStringAux(NodoAVL n) {
        String s = "";
        if (n != null) {
            s += n.getClave().toString() + " (Alt: " + n.getAltura() + ")";
            Object hi = (n.getIzquierdo() != null) ? n.getIzquierdo().getClave() : "-";
            Object hd = (n.getDerecho() != null) ? n.getDerecho().getClave() : "-";

            s += " HI: " + hi + " HD: " + hd + "\n";
            s += toStringAux(n.getIzquierdo());
            s += toStringAux(n.getDerecho());
        }
        return s;
    }

    // --- MÉTODOS DE BALANCEO Y ALTURA ---

    private NodoAVL balancear(NodoAVL n) {
        int balance = obtenerBalance(n);
        NodoAVL nuevaRaiz = n;

        if (balance > 1) {
            if (obtenerBalance(n.getIzquierdo()) < 0) {
                n.setIzquierdo(rotarIzquierda(n.getIzquierdo()));
            }
            nuevaRaiz = rotarDerecha(n);
        } else if (balance < -1) {
            if (obtenerBalance(n.getDerecho()) > 0) {
                n.setDerecho(rotarDerecha(n.getDerecho()));
            }
            nuevaRaiz = rotarIzquierda(n);
        }
        return nuevaRaiz;
    }

    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.getIzquierdo();
        NodoAVL temp = x.getDerecho();

        x.setDerecho(y);
        y.setIzquierdo(temp);

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.getDerecho();
        NodoAVL temp = y.getIzquierdo();

        y.setIzquierdo(x);
        x.setDerecho(temp);

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    private void actualizarAltura(NodoAVL n) {
        if (n != null) {
            n.setAltura(1 + Math.max(getAlt(n.getIzquierdo()), getAlt(n.getDerecho())));
        }
    }

    private int getAlt(NodoAVL n) {
        return (n != null) ? n.getAltura() : -1;
    }

    private int obtenerBalance(NodoAVL n) {
        return (n != null) ? getAlt(n.getIzquierdo()) - getAlt(n.getDerecho()) : 0;
    }

    private NodoAVL buscarNodoMinimo(NodoAVL n) {
        while (n.getIzquierdo() != null) {
            n = n.getIzquierdo();
        }
        return n;
    }
}