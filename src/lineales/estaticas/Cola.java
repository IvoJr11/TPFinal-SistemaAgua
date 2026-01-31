package lineales.estaticas;

public class Cola {
    private final int TAMANIO = 10;
    private Object[] arreglo;
    private int frente;
    private int fin;

    public Cola (){
        this.arreglo = new Object[this.TAMANIO];
        this.frente = 0;
        this.fin = 0;
    }

    public boolean poner(Object unElemento){
        boolean ponido = false;
        if((this.fin+1) % this.TAMANIO != this.frente){
            arreglo[this.fin]=unElemento;
            this.fin = (this.fin+1) % this.TAMANIO;
            ponido=true;
        }
        return ponido;
    }

    public boolean sacar(){
        boolean sacado = false;
        if (this.frente!=this.fin){
            this.arreglo[this.frente]=null;
            this.frente = (this.frente+1) % this.TAMANIO;
            sacado=true;
        }
        return sacado;
    }

    public Object obtenerFrente(){
        return arreglo[this.frente];
    }

    public boolean esVacia(){
        return this.frente==this.fin;
    }

    public void vaciar(){
        this.frente = this.fin = 0;
    }

    public Cola clone() {
        Cola clon = new Cola();
        clon.frente = this.frente;
        clon.fin = this.fin;
    
        int i = this.frente;
        while (i != this.fin) {
            clon.arreglo[i] = this.arreglo[i];
            i = (i + 1) % this.TAMANIO;
        }
    
        return clon;
    }

    public String toString(){
        String texto = "[ ";
        if(this.frente == this.fin){
            texto = "Cola vacia";
        } else {
            int i = this.frente;
            while (i != this.fin) {
                texto += this.arreglo[i] + (i+1!=this.fin?", ":"");
                i = (i + 1) % this.TAMANIO;
            }
            texto += " ]";
        }
        return texto;
    }
}