package especificos;

import lineales.dinamicas.Lista;

public class Mapeo {

    private static final int TAMANIO = 20; // Tamaño sugerido, idealmente un número primo
    private Lista[] tabla;
    private int cant; // Cantidad de entradas almacenadas

    public Mapeo() {
        this.tabla = new Lista[TAMANIO];
        for (int i = 0; i < TAMANIO; i++) {
            this.tabla[i] = new Lista();
        }
        this.cant = 0;
    }

    // --- FUNCIÓN HASH ---
    private int hash(Object clave) {
        // Math.abs asegura que el índice sea positivo
        return Math.abs(clave.hashCode() % TAMANIO);
    }

    // --- OPERACIÓN: ASOCIAR (Insertar o Actualizar) ---
    public boolean asociar(Object clave, Object valor) {
        boolean exito = true;
        int pos = hash(clave);
        Lista listaColision = this.tabla[pos];

        // Buscamos si la clave ya existe en la lista
        EntradaMapeo buscado = new EntradaMapeo(clave, null);
        int posEnLista = listaColision.localizar(buscado);

        if (posEnLista > 0) {
            // Si la clave ya existe, actualizamos su valor (Mapeo a Uno)
            EntradaMapeo entradaExistente = (EntradaMapeo) listaColision.recuperar(posEnLista);
            entradaExistente.setValor(valor);
        } else {
            // Si no existe, insertamos el nuevo par
            listaColision.insertar(new EntradaMapeo(clave, valor), 1);
            this.cant++;
        }
        return exito;
    }

    // --- OPERACIÓN: OBTENER VALOR ---
    public Object obtenerValor(Object clave) {
        Object valorRetorno = null;
        int pos = hash(clave);
        Lista listaColision = this.tabla[pos];

        // Buscamos la entrada
        EntradaMapeo buscado = new EntradaMapeo(clave, null);
        int posEnLista = listaColision.localizar(buscado);

        if (posEnLista > 0) {
            EntradaMapeo entrada = (EntradaMapeo) listaColision.recuperar(posEnLista);
            valorRetorno = entrada.getValor();
        }
        return valorRetorno;
    }

    // --- OPERACIÓN: DESASOCIAR (Eliminar) ---
    public boolean desasociar(Object clave) {
        boolean exito = false;
        int pos = hash(clave);
        Lista listaColision = this.tabla[pos];

        EntradaMapeo buscado = new EntradaMapeo(clave, null);
        int posEnLista = listaColision.localizar(buscado);

        if (posEnLista > 0) {
            listaColision.eliminar(posEnLista);
            this.cant--;
            exito = true;
        }
        return exito;
    }

    // --- OPERACIÓN: OBTENER CONJUNTO DOMINIO (Claves) ---
    public Lista obtenerConjuntoDominio() {
        Lista claves = new Lista();
        for (int i = 0; i < TAMANIO; i++) {
            if (!this.tabla[i].esVacia()) {
                // Recorremos la lista de cada celda
                Lista listaAux = this.tabla[i]; // Copiamos referencia para no dañar original
                int longitud = listaAux.longitud();
                for (int j = 1; j <= longitud; j++) {
                    EntradaMapeo entrada = (EntradaMapeo) listaAux.recuperar(j);
                    claves.insertar(entrada.getClave(), claves.longitud() + 1);
                }
            }
        }
        return claves;
    }

    // --- OPERACIÓN: OBTENER CONJUNTO RANGO (Valores) ---
    public Lista obtenerConjuntoRango() {
        Lista valores = new Lista();
        for (int i = 0; i < TAMANIO; i++) {
            if (!this.tabla[i].esVacia()) {
                Lista listaAux = this.tabla[i];
                int longitud = listaAux.longitud();
                for (int j = 1; j <= longitud; j++) {
                    EntradaMapeo entrada = (EntradaMapeo) listaAux.recuperar(j);
                    valores.insertar(entrada.getValor(), valores.longitud() + 1);
                }
            }
        }
        return valores;
    }

    public boolean esVacio() {
        return this.cant == 0;
    }

    public void vaciar() {
        for (int i = 0; i < TAMANIO; i++) {
            this.tabla[i].vaciar();
        }
        this.cant = 0;
    }

    @Override
    public String toString() {
        String s = "Mapeo: \n";
        for (int i = 0; i < TAMANIO; i++) {
            if (!this.tabla[i].esVacia()) {
                s += "Pos " + i + ": " + this.tabla[i].toString() + "\n";
            }
        }
        return s;
    }
}