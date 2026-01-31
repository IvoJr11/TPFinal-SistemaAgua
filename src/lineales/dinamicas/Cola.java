package lineales.dinamicas;

public class Cola {
    private Nodo frente;
    private Nodo fin;

    public Cola(){
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object nuevoElem) {
        Nodo nuevo = new Nodo(nuevoElem, null);
        if (this.fin == null) {
            this.frente = nuevo;
        } else {
            this.fin.setEnlace(nuevo);
        }
        this.fin = nuevo;
        return true;
    }

    public boolean sacar(){
        boolean sacado = false;
        if(this.frente!=null){
            this.frente = this.frente.getEnlace();
            if(this.frente == null){
                this.fin = null;
            }
            sacado = true;
        }
        return sacado;
    }

    public Object obtenerFrente(){
        return this.frente.getElemento();
    }

    public boolean esVacia(){
        return this.frente == null;
    }

    public void vaciar(){
        this.fin = null;
        this.frente = null;
    }

    public Cola clone() {
        Cola clon = new Cola(); // Paso 1: Nueva cola vacía
    
        if (this.frente != null) { // Si la cola original no está vacía
            // Paso 2: Copiar el primer nodo (frente)
            clon.frente = new Nodo(this.frente.getElemento(), null);
            Nodo nodoClon = clon.frente; // Puntero auxiliar para el clon
            Nodo nodoOriginal = this.frente.getEnlace(); // Puntero auxiliar para el original
    
            // Paso 3: Recorrer y copiar los nodos restantes
            while (nodoOriginal != null) {
                nodoClon.setEnlace(new Nodo(nodoOriginal.getElemento(), null)); // Copia el nodo
                nodoClon = nodoClon.getEnlace(); // Avanza en el clon
                nodoOriginal = nodoOriginal.getEnlace(); // Avanza en el original
            }
    
            // Paso 4: Actualizar 'fin' del clon
            clon.fin = nodoClon; // El último nodo procesado es el 'fin'
        }
    
        return clon;
    }

    @Override
    public String toString(){
        String texto = "[ ";
        if(this.frente==null){
            texto = "Cola vacia";
        } else{
            Nodo actual = this.frente;
            while(actual != null){
                texto += actual.getElemento() + (actual.getEnlace() == null ? "" : ", ");
                actual = actual.getEnlace();
            }
            texto += " ]";
        }
        return texto;
    }

}