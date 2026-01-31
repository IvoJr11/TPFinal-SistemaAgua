package especificos;

public class EntradaMapeo {
    private Object clave;
    private Object valor;

    public EntradaMapeo(Object clave, Object valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public Object getClave() {
        return clave;
    }

    public void setClave(Object clave) {
        this.clave = clave;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "(" + clave + "->" + valor + ")";
    }

    @Override
    public boolean equals(Object otro) {
        // Dos entradas son iguales si tienen la misma clave
        if (this == otro)
            return true;
        if (otro == null || getClass() != otro.getClass())
            return false;
        EntradaMapeo that = (EntradaMapeo) otro;
        return clave.equals(that.clave);
    }
}