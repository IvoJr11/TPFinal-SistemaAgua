package conjuntistas;

public class ArbolHeap {

    private static final int TAMANIO_POR_DEFECTO = 20;
    private Comparable[] heap;
    private int ultimo; // Indica la última posición ocupada

    public ArbolHeap() {
        this.heap = new Comparable[TAMANIO_POR_DEFECTO];
        this.ultimo = 0;
    }

    public ArbolHeap(int capacidad) {
        this.heap = new Comparable[capacidad + 1];
        this.ultimo = 0;
    }

    // --- OPERACIÓN: INSERTAR ---
    public boolean insertar(Comparable elemento) {
        boolean exito = false;
        if (this.ultimo < this.heap.length - 1) {
            this.ultimo++;
            this.heap[this.ultimo] = elemento;
            hacerSubir(this.ultimo);
            exito = true;
        }
        return exito;
    }

    private void hacerSubir(int posHijo) {
        int posPadre;
        Comparable temp = this.heap[posHijo];
        boolean salir = false;

        while (!salir && posHijo > 1) {
            posPadre = posHijo / 2;
            // Si el hijo es menor que el padre, deben intercambiar (Heap Mínimo)
            if (temp.compareTo(this.heap[posPadre]) < 0) {
                this.heap[posHijo] = this.heap[posPadre];
                posHijo = posPadre;
            } else {
                salir = true;
            }
        }
        this.heap[posHijo] = temp;
    }

    // --- OPERACIÓN: ELIMINAR CIMA (MÍNIMO) ---
    public boolean eliminarCima() {
        boolean exito = false;
        if (this.ultimo > 0) {
            // Reemplazamos la raíz con el último elemento
            this.heap[1] = this.heap[this.ultimo];
            this.heap[this.ultimo] = null;
            this.ultimo--;
            if (this.ultimo > 1) {
                hacerBajar(1);
            }
            exito = true;
        }
        return exito;
    }

    private void hacerBajar(int posPadre) {
        int posHijo;
        Comparable temp = this.heap[posPadre];
        boolean salir = false;

        while (!salir && (posPadre * 2) <= this.ultimo) {
            posHijo = posPadre * 2; // Por defecto hijo izquierdo

            // Si tiene hijo derecho, comparamos cuál es el menor de los dos
            if (posHijo < this.ultimo) {
                if (this.heap[posHijo + 1].compareTo(this.heap[posHijo]) < 0) {
                    posHijo++;
                }
            }

            // Si el menor de los hijos es menor que el padre, intercambiamos
            if (this.heap[posHijo].compareTo(temp) < 0) {
                this.heap[posPadre] = this.heap[posHijo];
                posPadre = posHijo;
            } else {
                salir = true;
            }
        }
        this.heap[posPadre] = temp;
    }

    // --- OPERACIONES COMPLEMENTARIAS ---
    public Object recuperarCima() {
        Object cima = null;
        if (this.ultimo > 0) {
            cima = this.heap[1];
        }
        return cima;
    }

    public boolean esVacio() {
        return this.ultimo == 0;
    }

    public void vaciar() {
        for (int i = 1; i <= this.ultimo; i++) {
            this.heap[i] = null;
        }
        this.ultimo = 0;
    }

    public String toString() {
        String s = "[ ";
        for (int i = 1; i <= this.ultimo; i++) {
            s += this.heap[i] + (i == this.ultimo ? "" : ", ");
        }
        s += " ]";
        return s;
    }

    public ArbolHeap clone() {
        ArbolHeap clon = new ArbolHeap(this.heap.length - 1);
        for (int i = 1; i <= this.ultimo; i++) {
            clon.heap[i] = this.heap[i];
        }
        clon.ultimo = this.ultimo;
        return clon;
    }
}