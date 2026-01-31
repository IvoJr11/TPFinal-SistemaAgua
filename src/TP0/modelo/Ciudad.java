package TP0.modelo;

public class Ciudad {

    public static final int CANT_ANIOS = 10;
    public static final int CANT_MESES = 12;

    private String nombre;
    private String nomenclatura;
    private double superficie;
    private double promConsumo;

    // Fila = Año, Columna = Mes
    private int[][] habitantes;
    private double[][] aprovisionamiento;

    public Ciudad(String nombre, String nomenclatura, double superficie, double promConsumo) {
        this.nombre = nombre;
        this.nomenclatura = nomenclatura;
        this.superficie = superficie;
        this.promConsumo = promConsumo;
        this.habitantes = new int[CANT_ANIOS][CANT_MESES];
        this.aprovisionamiento = new double[CANT_ANIOS][CANT_MESES];
    }

    public boolean tuvoDeficit(int anio, int mes) {
        boolean deficit = false;
        int diasDelMes = 30;

        if (esFechaValida(anio, mes)) {
            double demandaTotal = this.habitantes[anio][mes] * this.promConsumo * diasDelMes;
            double ofertaAgua = this.aprovisionamiento[anio][mes];
            if (demandaTotal > ofertaAgua) {
                deficit = true;
            }
        }
        return deficit;
    }

    private boolean esFechaValida(int anio, int mes) {
        return anio >= 0 && anio < CANT_ANIOS && mes >= 0 && mes < CANT_MESES;
    }

    // --- SETTERS DE HISTORIAL ---

    public boolean setHabitantes(int anio, int mes, int cantidad) {
        boolean exito = false;
        if (esFechaValida(anio, mes)) {
            this.habitantes[anio][mes] = cantidad;
            exito = true;
        }
        return exito;
    }

    public boolean setAprovisionamiento(int anio, int mes, double cantidad) {
        boolean exito = false;
        if (esFechaValida(anio, mes)) {
            this.aprovisionamiento[anio][mes] = cantidad;
            exito = true;
        }
        return exito;
    }

    // --- GETTERS Y SETTERS BÁSICOS ---

    public int getHabitantes(int anio, int mes) {
        int resultado = 0;
        if (esFechaValida(anio, mes)) {
            resultado = this.habitantes[anio][mes];
        }
        return resultado;
    }

    public double getAprovisionamiento(int anio, int mes) {
        double resultado = 0.0;
        if (esFechaValida(anio, mes)) {
            resultado = this.aprovisionamiento[anio][mes];
        }
        return resultado;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNomenclatura() {
        return this.nomenclatura;
    }

    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public double getSuperficie() {
        return this.superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public double getPromConsumo() {
        return this.promConsumo;
    }

    public void setPromConsumo(double promConsumo) {
        this.promConsumo = promConsumo;
    }

    @Override
    public String toString() {
        return "Ciudad: " + this.nombre + " [" + this.nomenclatura + "] " +
                "(Sup: " + this.superficie + ", Consumo: " + this.promConsumo + ")";
    }
}