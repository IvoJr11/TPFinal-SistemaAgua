package lineales.dinamicas;

public class ElementoPrioridad implements Comparable<ElementoPrioridad> {
    private Object dato;
    private int prioridad;
    private int ordenLlegada; // Nuevo atributo para el desempate

    public ElementoPrioridad(Object dato, int prioridad, int ordenLlegada) {
        this.dato = dato;
        this.prioridad = prioridad;
        this.ordenLlegada = ordenLlegada;
    }

    public Object getDato() { return dato; }
    public int getPrioridad() { return prioridad; }
    public int getOrdenLlegada() { return ordenLlegada; }

    @Override
    public int compareTo(ElementoPrioridad otro) {
        int res;
        // 1. Primer criterio: Prioridad (menor valor = mayor prioridad)
        res = Integer.compare(this.prioridad, otro.prioridad);
        
        // 2. Segundo criterio: Si hay empate, usamos el orden de llegada
        if (res == 0) {
            // Menor orden de llegada significa que llegó antes, por lo tanto "es menor" 
            // y debe subir en el Heap Mínimo.
            res = Integer.compare(this.ordenLlegada, otro.ordenLlegada);
        }
        return res;
    }
    
    @Override
    public String toString() {
        return "[" + dato + ", P:" + prioridad + "]";
    }
}