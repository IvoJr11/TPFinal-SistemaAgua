package lineales.dinamicas;

public class Nodo {
    private Object elemento;
    private Nodo enlace;

    public Nodo(Object unElemento, Nodo unEnlace){
        this.elemento = unElemento;
        this.enlace = unEnlace;
    }

    public Object getElemento(){
        return this.elemento;
    }

    public void setElemento(Object unElemento){
        this.elemento = unElemento;
    }

    public Nodo getEnlace(){
        return this.enlace;
    }

    public void setEnlace(Nodo unEnlace) {
        this.enlace = unEnlace; 
    }
}
