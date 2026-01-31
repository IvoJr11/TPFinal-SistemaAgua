package test.lineales;

import lineales.estaticas.Pila;

public class TestPila {
    public static void main(String[] args) {
        // probar metodos de pila
        Pila pila = new Pila();
        System.out.println("Apilando elementos...");
        for(int i=0;i<=5;i++){
            pila.apilar(i);
        }
        System.out.println("El tope es: " + pila.obtenerTope());
        System.out.println("La es pila: " + pila.toString());
        System.out.println("Desapilando el primer elemnto...");
        pila.desapilar();
        System.out.println("El tope después de desapilar es: " + pila.obtenerTope());
        Pila nuevaPila = pila.clone();
        System.out.println("Hacer copia de la pila y vaciar la original...");
        pila.vaciar();
        System.out.println("La pila original esta vacia? " + pila.esVacia());
        System.out.println("Primera pila: " + pila.toString());
        System.out.println("Segunda pila: " + nuevaPila.toString());

        //probar si es capicua(nuevaPila)
        System.out.println("¿Es la pila original capicua? " + esCapicua(nuevaPila));

        //probar si funciona con String
        Pila pilaString = new Pila();
        pilaString.apilar("hola");
        pilaString.apilar("mundo");
        System.out.println("Primera pila de String: " + pilaString.toString());

        //probar si funciona con una clase como objeto
        Pila pilaClass = new Pila();
        pilaClass.apilar(new Alumno("FAI-[5383]"));
        pilaClass.apilar(new Alumno("FAI-[7777]"));
        System.out.println("Primera pila de clase: " + pilaClass.toString());

    }

    //metodo que dice si es o no capicua
    public static boolean esCapicua(Pila unaPila){
        Pila aux = new Pila();
        Pila clon = unaPila.clone();
        boolean capicua=true;
        while (!unaPila.esVacia()) {
            aux.apilar(unaPila.obtenerTope());
            unaPila.desapilar();
        }
        while (!clon.esVacia() && capicua) {
            if (clon.obtenerTope() != aux.obtenerTope()) {
                capicua = false;
            }
            clon.desapilar();
            aux.desapilar();
        }
        return capicua;
    }

}