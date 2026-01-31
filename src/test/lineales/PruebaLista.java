package test.lineales;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Pila;
import lineales.dinamicas.Cola;

public class PruebaLista {
    // a) Concatenar dos listas
    public static void main(String[]args){
        
    }
    public static Lista concatenar(Lista L1, Lista L2) {
        Lista resultado = new Lista();
        for (int i = 1; i <= L1.longitud(); i++) {
            resultado.insertar(L1.recuperar(i), resultado.longitud() + 1);
        }
        for (int i = 1; i <= L2.longitud(); i++) {
            resultado.insertar(L2.recuperar(i), resultado.longitud() + 1);
        }
        return resultado;
    }

    // b) Comprobar formato cadena@cadena@cadena*
    public static boolean comprobar(Lista L1) {
        boolean cumple = false;
        Cola cola = new Cola();
        Pila pila = new Pila();
        int posCero = L1.localizar(0); // Buscar el primer 0
        if (posCero != -1) {
            // Llenar cola y pila con los elementos antes del 0
            for (int i = 1; i < posCero; i++) {
                Object elem = L1.recuperar(i);
                cola.poner(elem);
                pila.apilar(elem);
            }
            // Verificar si los elementos después del 0 coinciden con la pila
            cumple = true;
            int j = posCero + 1;
            while (cumple && !pila.esVacia() && j <= L1.longitud()) {
                Object elemCola = cola.obtenerFrente();
                Object elemPila = pila.obtenerTope();
                Object elemLista = L1.recuperar(j);
                if (!elemLista.equals(elemPila)) {
                    cumple = false;
                }
                cola.sacar();
                pila.desapilar();
                j++;
            }
        }
        return cumple;
    }

    // c) Invertir una lista
    public static Lista invertir(Lista L) {
        Lista invertida = new Lista();
        for (int i = 1; i <= L.longitud(); i++) {
            invertida.insertar(L.recuperar(i), 1); // Inserta al principio
        }
        return invertida;
    }
}