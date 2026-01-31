package test.lineales;

public class Fecha {
    //atributos
    private int dia;
    private int mes;
    private int año;

    //constructores
    public Fecha (int unDia, int unMes, int unAño){
        this.dia=unDia;
        this.mes=unMes;
        this.año=unAño;
    }

    //visualizadoes
    public void setAño(int año) {
        this.año = año;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }

    //modificadores
    public int getAño() {
        return año;
    }
    public int getMes() {
        return mes;
    }
    public int getDia() {
        return dia;
    }

    //propias del tipo

    // Verifica año bisiesto
    public boolean esAñoBisiesto(){
        return (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);
    }

    public String cualEstacion() {
        String respuesta;
        if ((mes == 3 && dia >= 20) || (mes == 4) || (mes == 5) || (mes == 6 && dia < 21)) {
            respuesta = "Primavera";
        } else if ((mes == 6 && dia >= 21) || (mes == 7) || (mes == 8) || (mes == 9 && dia < 23)) {
            respuesta = "Verano";
        } else if ((mes == 9 && dia >= 23) || (mes == 10) || (mes == 11) || (mes == 12 && dia < 21)) {
            respuesta = "Otoño";
        } else {
            respuesta = "Invierno";
        }
        return respuesta;
    }

    public int diasFaltantes(Fecha unaFecha) {
        // Implementación más precisa
        int diasThis = this.dia + diasAcumuladosMes(this.mes, this.año) + this.año * 365;
        int diasUnaFecha = unaFecha.dia + diasAcumuladosMes(unaFecha.mes, unaFecha.año) + unaFecha.año * 365;
        
        // Ajuste para años bisiestos
        diasThis += contarBisiestos(this.año);
        diasUnaFecha += contarBisiestos(unaFecha.año);
        
        return Math.abs(diasThis - diasUnaFecha);
    }

    private int diasAcumuladosMes(int mes, int año) {
        int[] diasPorMes = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (esAñoBisiesto()) diasPorMes[2] = 29;
        
        int total = 0;
        for (int i = 1; i < mes; i++) {
            total += diasPorMes[i];
        }
        return total;
    }

    private int contarBisiestos(int año) {
        return año / 4 - año / 100 + año / 400;
    }

    public String toString(){
        return "dia:[ " + this.dia +" ]"+ " ,mes:[ " + this.mes +" ]" + " ,año :[ " + this.año +" ]";
    }
}