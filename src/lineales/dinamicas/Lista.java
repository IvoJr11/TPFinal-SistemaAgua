package lineales.dinamicas;

public class Lista {
    private Nodo cabecera;

    public Lista(){
        this.cabecera = null;
    }

    public boolean insertar(Object unElemento,int unaPos){
        boolean insertado = false;
        if(1 <= unaPos && unaPos <= (this.longitud() + 1)){
            if (unaPos == 1) {
                this.cabecera = new Nodo(unElemento, this.cabecera);
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < unaPos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(unElemento, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
        }
        return insertado;
    }

    public boolean eliminar(int unaPos){
        boolean eliminado = false;
        if (this.cabecera != null && 1 <= unaPos && unaPos <= this.longitud()){
            if(unaPos == 1){
                this.cabecera = this.cabecera.getEnlace();
            } else {
                int i = 1;
                Nodo aux = this.cabecera;
                while(i<unaPos - 1){
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
        }
        return eliminado;
    }

    public Object recuperar (int unaPos){
        Object elemento = null;
        if(1 <= unaPos && unaPos <= this.longitud()){
            Nodo auxNodo = this.cabecera;
            int i = 1;
            while(i<unaPos){
                i++;
                auxNodo = auxNodo.getEnlace();
            }
            elemento = auxNodo.getElemento();
        }
        return elemento;
    }

    public int localizar (Object unElemento){
        int posicion = -1;
        if(this.cabecera != null){
            Nodo auxNodo =this.cabecera;
            int i = 1;
            while (auxNodo.getElemento() != unElemento && i<=this.longitud()) {
                i++;
                auxNodo = auxNodo.getEnlace(); 
            }
            if(auxNodo.getElemento().equals(unElemento)){
                posicion = i;
            }
        }
        return posicion;
    }

    public void vaciar(){
        this.cabecera = null;
    }

    public boolean esVacia(){
        return this.cabecera == null;
    }

    public Lista clone(){
        Lista clonLista = new Lista();
        if(this.cabecera != null){
            clonLista.cabecera = new Nodo (this.cabecera.getElemento(),null);
            Nodo auxClon = clonLista.cabecera;
            Nodo auxOriginal = this.cabecera.getEnlace();
        while(auxOriginal != null){
            auxClon.setEnlace(new Nodo(auxOriginal.getElemento(),null));
            auxClon = auxClon.getEnlace();
            auxOriginal = auxOriginal.getEnlace();
        }
        
        }
        return clonLista;
    }

    public int longitud(){
        int tamano = 0;
        Nodo indice =  this.cabecera;
        while(indice != null){
            tamano++;
            indice = indice.getEnlace();
        }
        return tamano;
    }

    public String toString(){
        String texto = "[ ";
        if(this.cabecera == null){
            texto = "Fila vacia";
        } else {
            Nodo auxNodo = this.cabecera;
            while(auxNodo != null){
                texto += auxNodo.getElemento() + (auxNodo.getEnlace() == null ? "": ", ");
                auxNodo = auxNodo.getEnlace();
            }
            texto += " ]";
        }
        return texto;
    }


    // Método para invertir la lista (sin estructuras auxiliares)
public void invertir() {
    Nodo anterior = null;
    Nodo actual = this.cabecera;
    Nodo siguiente = null;
    while (actual != null) {
        siguiente = actual.getEnlace();
        actual.setEnlace(anterior);
        anterior = actual;
        actual = siguiente;
    }
    this.cabecera = anterior;
}

// Método para eliminar todas las apariciones de un elemento
public void eliminarApariciones(Object x) {
    while (this.cabecera != null && this.cabecera.getElemento().equals(x)) {
        this.cabecera = this.cabecera.getEnlace();
    }
    if (this.cabecera != null) {
        Nodo aux = this.cabecera;
        while (aux.getEnlace() != null) {
            if (aux.getEnlace().getElemento().equals(x)) {
                aux.setEnlace(aux.getEnlace().getEnlace());
            } else {
                aux = aux.getEnlace();
            }
        }
    }
}



}