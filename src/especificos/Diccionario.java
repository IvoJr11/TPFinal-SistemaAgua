package especificos;

import lineales.dinamicas.Lista;

public class Diccionario {

    private NodoAVLDicc raiz;

    public Diccionario() {
        this.raiz = null;
    }

    // --- OPERACIÓN: INSERTAR ---
    public boolean insertar(Comparable clave, Object dato) {
        boolean[] exito = {false};
        this.raiz = insertarAux(this.raiz, clave, dato, exito);
        return exito[0];
    }

    private NodoAVLDicc insertarAux(NodoAVLDicc n, Comparable clave, Object dato, boolean[] exito) {
        if (n == null) {
            n = new NodoAVLDicc(clave, dato, null, null);
            exito[0] = true;
        } else {
            int comp = clave.compareTo(n.getClave());
            if (comp < 0) {
                n.setIzquierdo(insertarAux(n.getIzquierdo(), clave, dato, exito));
            } else if (comp > 0) {
                n.setDerecho(insertarAux(n.getDerecho(), clave, dato, exito));
            } else {
                // Clave repetida: En Diccionario estricto falla, en Mapeo actualiza.
                // Aquí asumimos que NO permite repetidos.
                exito[0] = false; 
            }
        }
        
        if (exito[0]) {
            actualizarAltura(n);
            n = balancear(n);
        }
        return n;
    }

    // --- OPERACIÓN: ELIMINAR ---
    public boolean eliminar(Comparable clave) {
        boolean[] exito = {false};
        this.raiz = eliminarAux(this.raiz, clave, exito);
        return exito[0];
    }

    private NodoAVLDicc eliminarAux(NodoAVLDicc n, Comparable clave, boolean[] exito) {
        NodoAVLDicc res = n;
        if (n != null) {
            int comp = clave.compareTo(n.getClave());
            if (comp < 0) {
                n.setIzquierdo(eliminarAux(n.getIzquierdo(), clave, exito));
            } else if (comp > 0) {
                n.setDerecho(eliminarAux(n.getDerecho(), clave, exito));
            } else {
                // Encontrado
                exito[0] = true;
                if (n.getIzquierdo() == null || n.getDerecho() == null) {
                    // Caso 1 o 2 hijos: retorna el que no es nulo (o null si es hoja)
                    res = (n.getIzquierdo() != null) ? n.getIzquierdo() : n.getDerecho();
                } else {
                    // Caso 2 hijos: buscar candidato (mínimo del derecho)
                    NodoAVLDicc candidato = buscarMinimo(n.getDerecho());
                    // Copiamos los datos del candidato
                    n.setClave(candidato.getClave());
                    n.setDato(candidato.getDato());
                    // Eliminamos fisicamente al candidato
                    n.setDerecho(eliminarAux(n.getDerecho(), candidato.getClave(), exito));
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

    // --- OPERACIÓN: RECUPERAR ---
    public Object recuperarElemento(Comparable clave) {
        return recuperarAux(this.raiz, clave);
    }

    private Object recuperarAux(NodoAVLDicc n, Comparable clave) {
        Object resultado = null;
        if (n != null) {
            int comp = clave.compareTo(n.getClave());
            if (comp == 0) {
                resultado = n.getDato();
            } else if (comp < 0) {
                resultado = recuperarAux(n.getIzquierdo(), clave);
            } else {
                resultado = recuperarAux(n.getDerecho(), clave);
            }
        }
        return resultado;
    }

    public boolean existeClave(Comparable clave) {
        return recuperarElemento(clave) != null;
    }

    // --- MÉTODOS DE BALANCEO (Idénticos a ArbolAVL pero con NodoAVLDicc) ---
    
    private NodoAVLDicc balancear(NodoAVLDicc n) {
        int balance = obtenerBalance(n);
        if (balance > 1) { // Desbalanceado a la izquierda
            if (obtenerBalance(n.getIzquierdo()) < 0) {
                n.setIzquierdo(rotarIzquierda(n.getIzquierdo()));
            }
            n = rotarDerecha(n);
        } else if (balance < -1) { // Desbalanceado a la derecha
            if (obtenerBalance(n.getDerecho()) > 0) {
                n.setDerecho(rotarDerecha(n.getDerecho()));
            }
            n = rotarIzquierda(n);
        }
        return n;
    }

    private int obtenerBalance(NodoAVLDicc n) {
        return (n == null) ? 0 : getAlt(n.getIzquierdo()) - getAlt(n.getDerecho());
    }

    private void actualizarAltura(NodoAVLDicc n) {
        if (n != null) {
            n.setAltura(1 + Math.max(getAlt(n.getIzquierdo()), getAlt(n.getDerecho())));
        }
    }

    private int getAlt(NodoAVLDicc n) {
        return (n == null) ? -1 : n.getAltura();
    }

    // Rotaciones
    private NodoAVLDicc rotarDerecha(NodoAVLDicc y) {
        NodoAVLDicc x = y.getIzquierdo();
        NodoAVLDicc temp = x.getDerecho();
        x.setDerecho(y);
        y.setIzquierdo(temp);
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private NodoAVLDicc rotarIzquierda(NodoAVLDicc x) {
        NodoAVLDicc y = x.getDerecho();
        NodoAVLDicc temp = y.getIzquierdo();
        y.setIzquierdo(x);
        x.setDerecho(temp);
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }
    
    private NodoAVLDicc buscarMinimo(NodoAVLDicc n) {
        while (n.getIzquierdo() != null) n = n.getIzquierdo();
        return n;
    }

    // --- LISTADOS ---
    public Lista listarClaves() {
        Lista l = new Lista();
        listarClavesAux(this.raiz, l);
        return l;
    }

    private void listarClavesAux(NodoAVLDicc n, Lista l) {
        if (n != null) {
            listarClavesAux(n.getIzquierdo(), l);
            l.insertar(n.getClave(), l.longitud() + 1);
            listarClavesAux(n.getDerecho(), l);
        }
    }

    public Lista listarDatos() {
        Lista l = new Lista();
        listarDatosAux(this.raiz, l);
        return l;
    }

    private void listarDatosAux(NodoAVLDicc n, Lista l) {
        if (n != null) {
            listarDatosAux(n.getIzquierdo(), l);
            l.insertar(n.getDato(), l.longitud() + 1);
            listarDatosAux(n.getDerecho(), l);
        }
    }
    
    public boolean esVacio() { return this.raiz == null; }
    public void vaciar() { this.raiz = null; }
}