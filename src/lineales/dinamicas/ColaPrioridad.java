package lineales.dinamicas;

import conjuntistas.ArbolHeap;

public class ColaPrioridad {
    private ArbolHeap heap;
    private int contadorOrden; // Contador global para marcar el orden de llegada

    public ColaPrioridad(int capacidad) {
        this.heap = new ArbolHeap(capacidad);
        this.contadorOrden = 0;
    }

    // --- OPERACIÓN: INSERTAR ---
    public boolean insertar(Object elemento, int prioridad) {
        // Incrementamos el contador para asegurar que cada elemento tenga un número único y creciente
        this.contadorOrden++;
        
        // Creamos el contenedor con el dato, la prioridad y su "turno"
        ElementoPrioridad nuevo = new ElementoPrioridad(elemento, prioridad, this.contadorOrden);
        
        return this.heap.insertar(nuevo);
    }

    // --- OPERACIÓN: ELIMINAR ---
    public boolean eliminarPrioridad() {
        // Elimina el elemento de la raíz (el de mayor prioridad y, en empate, el más antiguo)
        return this.heap.eliminarCima();
    }

    // --- OPERACIÓN: OBTENER FRENTE ---
    public Object obtenerPrioridad() {
        Object dato = null;
        if (!this.heap.esVacio()) {
            ElementoPrioridad cima = (ElementoPrioridad) this.heap.recuperarCima();
            dato = cima.getDato();
        }
        return dato;
    }

    public boolean esVacia() {
        return this.heap.esVacio();
    }

    public void vaciar() {
        this.heap.vaciar();
        this.contadorOrden = 0; // Reiniciamos el contador
    }

    @Override
    public String toString() {
        return this.heap.toString();
    }
}