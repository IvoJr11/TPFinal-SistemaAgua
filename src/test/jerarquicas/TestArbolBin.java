package test.jerarquicas;

import jerarquicas.ArbolBin;
import lineales.dinamicas.Lista;

public class TestArbolBin {
    public static void main(String[] args) {
        ArbolBin arbol = new ArbolBin();

        // Insertar elementos
        arbol.insertar(1, null, 'I'); // Raíz
        arbol.insertar(2, 1, 'I');    // Hijo izquierdo de 1
        arbol.insertar(3, 1, 'D');    // Hijo derecho de 1
        arbol.insertar(4, 2, 'I');    // Hijo izquierdo de 2
        arbol.insertar(5, 2, 'D');    // Hijo derecho de 2

        // Probar frontera
        System.out.println("Hojas del árbol (frontera): " + arbol.frontera().toString());

        // Probar ancestros
        System.out.println("Ancestros de 5: " + arbol.obtenerAncestros(5).toString());

        // Probar descendientes
        System.out.println("Descendientes de 2: " + arbol.obtenerDescendientes(2).toString());
    }
}