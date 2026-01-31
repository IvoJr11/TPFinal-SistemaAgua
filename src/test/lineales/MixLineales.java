package test.lineales;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Nodo;
import lineales.dinamicas.Pila;
public class MixLineales {
    public static void main(String[] args) {
        Cola c1 = new Cola();
        c1.poner('A');
        c1.poner('B');
        c1.poner('$');
        c1.poner('C');
        c1.poner('$');
        c1.poner('D');
        c1.poner('E');
        c1.poner('F');
        
        System.out.println("Cola original: " + c1);
        Cola resultado = MixLineales.generarOtraCola(c1);
        System.out.println("Cola generada: " + resultado);
        // Esperado: [A,B,B,A,$,C,C,$,D,E,F,F,E,D]
    }

    public static Cola generarOtraCola(Cola c1) {
        Cola c2 = new Cola();
        Pila pilaAux = new Pila();
        Cola colaAux = new Cola();

        while (!c1.esVacia()) {
            char elem = (char) c1.obtenerFrente();
            if (elem != '$') {
                // Apilar y encolar para invertir después
                pilaAux.apilar(elem);
                colaAux.poner(elem);
                c1.sacar();
            } else {
                // Procesar la secuencia antes del $
                while (!pilaAux.esVacia()) {
                    colaAux.poner(pilaAux.obtenerTope());
                    pilaAux.desapilar();
                }
                colaAux.poner('$'); // Agregar el separador
                c1.sacar(); // Sacar el $ de c1
            }
        }

        // Procesar la última secuencia (si no termina en $)
        while (!pilaAux.esVacia()) {
            colaAux.poner(pilaAux.obtenerTope());
            pilaAux.desapilar();
        }

        // Pasar los elementos de colaAux a c2
        while (!colaAux.esVacia()) {
            c2.poner(colaAux.obtenerFrente());
            colaAux.sacar();
        }

        return c2;
    }
    
}