package lineales.dinamicas;

public class Pila {
    private Nodo tope;

    public Pila (){
        this.tope=null;
    }

    public boolean apilar(Object unElemento){
        Nodo nuevo = new Nodo(unElemento, this.tope);
        this.tope = nuevo;
        return true;
    }

    public boolean desapilar(){
        boolean desapilado = false;
        if(this.tope != null){
            this.tope = this.tope.getEnlace();  
            desapilado = true;
        }
        return desapilado;
    }

    public Object obtenerTope(){
        return this.tope != null ? this.tope.getElemento() : null;
    }

    public boolean esVacia(){
        return this.tope==null;
    }

    public void vaciar(){
        this.tope=null;
    }

    public Pila clone() {
        Pila clon = new Pila();
        if (this.tope != null) {
            clon.tope = new Nodo(this.tope.getElemento(), null);
            Nodo auxClon = clon.tope;
            Nodo auxOriginal = this.tope.getEnlace();
            
            while (auxOriginal != null) {
                auxClon.setEnlace(new Nodo(auxOriginal.getElemento(), null));
                auxClon = auxClon.getEnlace();
                auxOriginal = auxOriginal.getEnlace();
            }
        }
        return clon;
    }

    @Override
    public String toString(){
        String texto = "";
        if(this.tope==null){
            texto = "Pila vacia";
        } else {
            Nodo aux = this.tope;
            texto = "[";
            while(aux != null){
                texto += aux.getElemento().toString();
                aux = aux.getEnlace();
                if(aux != null){
                    texto += ", ";
                }
            }
            texto += "]";
        }
        return texto;
    }

}
