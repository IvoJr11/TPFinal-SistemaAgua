package TP0.interfaz;

import TP0.sistema.SistemaRedAgua;

public class Main {

    public static void main(String[] args) {
        SistemaRedAgua miSistema = new SistemaRedAgua();
        Menu menuPrincipal = new Menu(miSistema);
        menuPrincipal.iniciar();
    }
}