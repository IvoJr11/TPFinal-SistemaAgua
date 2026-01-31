package lineales.estaticas;
public class Pila {
    private Object[] arreglo;
    private int tope;
    private static final int TAMANO = 20;

    public Pila(){
        this.arreglo = new Object[TAMANO];
        this.tope=-1;
    }

    public int getTAMANO(){
        return TAMANO;
    }

    public boolean apilar (Object nuevoElemento){
        boolean apilado=false;
        if (this.tope + 1 < this.getTAMANO()){
            this.tope++;
            this.arreglo[this.tope]=nuevoElemento;
            apilado=true;
        }
        return apilado;
    }

    public boolean desapilar(){
        boolean desapilado=false;
        if(!this.esVacia()){
            this.arreglo[this.tope] = null;
            this.tope--; 
            desapilado=true;
        }
        return desapilado;
    }

    public Object obtenerTope(){
        return this.tope >= 0 ? this.arreglo[this.tope] : null;
    }

    public boolean esVacia(){
        return this.tope == -1;
    }

    public void vaciar(){
        for (int i = 0; i <= this.tope; i++) {
            this.arreglo[i] = null;
        }
        this.tope = -1;
    }

    public Pila clone(){
        Pila copia = new Pila();
        for (int i = 0; i <= this.tope; i++) {
            copia.apilar(this.arreglo[i]);
            }
        return copia;
    }
    
    @Override
    public String toString(){
        String texto= "[ ";
        if(this.esVacia()){
            texto="Pila vacia";
        } else{
            for (int i = this.tope; i >= 0; i--){
                texto+=this.arreglo[i] + (i!=0 ?", ": " ");
            }
            texto+= "]";
        }
        return texto;
    }
}