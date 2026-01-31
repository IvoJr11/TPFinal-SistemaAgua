package TP0.modelo;

public class Tuberia {

    public static final String ESTADO_ACTIVO = "ACTIVO";
    public static final String ESTADO_REPARACION = "EN REPARACIÓN";
    public static final String ESTADO_DISENIO = "EN DISEÑO";
    public static final String ESTADO_INACTIVO = "INACTIVO";

    private String nomenclatura;
    private double caudalMin;
    private double caudalMax;
    private double diametro;
    private String estado;

    public Tuberia(String nomenclatura, double caudalMin, double caudalMax, double diametro, String estado) {
        this.nomenclatura = nomenclatura;
        this.caudalMin = caudalMin;
        this.caudalMax = caudalMax;
        this.diametro = diametro;

        if (!setEstado(estado)) {
            this.estado = ESTADO_INACTIVO;
        }
    }

    // --- GETTERS ---
    public String getNomenclatura() {
        return this.nomenclatura;
    }

    public double getCaudalMin() {
        return this.caudalMin;
    }

    public double getCaudalMax() {
        return this.caudalMax;
    }

    public double getDiametro() {
        return this.diametro;
    }

    public String getEstado() {
        return this.estado;
    }

    // --- SETTERS ---
    public void setNomenclatura(String nomenclatura) {
        this.nomenclatura = nomenclatura;
    }

    public boolean setCaudalMin(double caudalMin) {
        boolean exito = false;
        if (caudalMin >= 0) {
            this.caudalMin = caudalMin;
            exito = true;
        }
        return exito;
    }

    public boolean setCaudalMax(double caudalMax) {
        boolean exito = false;
        if (caudalMax >= this.caudalMin) {
            this.caudalMax = caudalMax;
            exito = true;
        }
        return exito;
    }

    public boolean setDiametro(double diametro) {
        boolean exito = false;
        if (diametro > 0) {
            this.diametro = diametro;
            exito = true;
        }
        return exito;
    }

    public boolean setEstado(String estado) {
        boolean exito = false;
        if (estado != null) {
            String estadoNormalizado = estado.toUpperCase();
            if (estadoNormalizado.equals(ESTADO_ACTIVO) ||
                    estadoNormalizado.equals(ESTADO_REPARACION) ||
                    estadoNormalizado.equals(ESTADO_DISENIO) ||
                    estadoNormalizado.equals(ESTADO_INACTIVO)) {

                this.estado = estadoNormalizado;
                exito = true;
            }
        }
        return exito;
    }

    @Override
    public String toString() {
        return "Tuberia [" + this.nomenclatura + "] " +
                "Estado: " + this.estado + ", " +
                "Caudal: " + this.caudalMin + "-" + this.caudalMax + ", " +
                "Diametro: " + this.diametro;
    }
}