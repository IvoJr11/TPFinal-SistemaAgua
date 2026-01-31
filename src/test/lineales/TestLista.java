package test.lineales;

import lineales.dinamicas.Lista;

public class TestLista {
    public static void main(String[] args) {
        Lista lista = new Lista();

        // Prueba de inserción
        System.out.println("Insertar elementos:");
        lista.insertar("A", 1);
        lista.insertar("B", 2);
        lista.insertar("C", 3);
        System.out.println("Lista después de inserciones: " + lista.toString());
        System.out.println();

        // Prueba de eliminación
        System.out.println("\nEliminar elemento en posición 2:");
        lista.eliminar(2);
        System.out.println("Lista después de eliminar: " + lista.toString());

        // Prueba de recuperar
        System.out.println("\nRecuperar elemento en posición 1:");
        System.out.println("Elemento recuperado: " + lista.recuperar(1));

        // Prueba de localizar
        System.out.println("\nLocalizar elemento 'C':");
        System.out.println("Posición de 'C': " + lista.localizar("C"));

        // Prueba de longitud
        System.out.println("\nLongitud de la lista:");
        System.out.println("Longitud: " + lista.longitud());

        // Prueba de clonar
        System.out.println("\nClonar la lista:");
        Lista clon = lista.clone();
        System.out.println("Clon: " + clon.toString());
    }
}