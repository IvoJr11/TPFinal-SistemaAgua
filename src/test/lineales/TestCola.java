package test.lineales;

import lineales.dinamicas.Cola;
public class TestCola {
    public static void main(String[] args) {
        //probar metodos de cola
        Cola cola = new Cola();
        System.out.println("Poner elementos en la cola...");
        for(int i=0;i<=6;i++){
            cola.poner(i);
        }
        System.out.println("Cola resultante: "+ cola.toString());
        System.out.println("Se pudo sacar un elemento del frente?" + cola.sacar());
        System.out.println("Nuevo frente: " + cola.obtenerFrente());
        System.out.println("Es la cola vacia?" + cola.esVacia());
        System.out.println("Clonar y vaciar cola...");
        Cola clon = cola.clone();
        cola.vaciar();
        System.out.println("Cola original: "+ cola.toString());
        System.out.println("Clon: " + clon.toString());

        //probar con tda fecha
        Cola colaFechas = new Cola();
        colaFechas.poner(new Fecha(26,3,2010));
        colaFechas.poner(new Fecha(1,6,1990));
        System.out.println("La cola de fechas es: " + colaFechas.toString());
    }
}