package TP0.sistema;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class GeneradorDeLote {

    public static void main(String[] args) {
        generarCiudades();
        generarTuberias();
    }

    private static void generarCiudades() {

        String[] nombres = {
            "Neufuén", "Rionegra", "Solandina", 
            "Terraviva", "Bosquemonte", "Cupral-Có", 
            "Maravélica", "Piedraluna", "Valleverde",
            "Monteaurora", "Fuenteluz", "Ventisquero Alto", "Cieloflor"
        };
        
        String[] codigos = {
            "NE3001", "RI3009", "SO3004", "TE3005", "BO3013", 
            "CC3002", "MA3003", "PI3006", "VA3007", 
            "MO3008", "FU3010", "VE3011", "CI3012"
        };

        try (PrintWriter pw = new PrintWriter(new FileWriter("ciudades.txt"))) {
            Random rand = new Random();

            for (int i = 0; i < nombres.length; i++) {
                StringBuilder sb = new StringBuilder();

                sb.append(nombres[i]).append(";");
                sb.append(codigos[i]).append(";");
                sb.append((500000 + rand.nextInt(1000000))).append(";");
                double r = rand.nextDouble(0.1, 1.0);
                sb.append(r + ";");

                for (int j = 0; j < 120; j++) {

                    sb.append(10000 + rand.nextInt(40000)).append(";");
                }

                pw.println(sb.toString());
            }
            System.out.println("Archivo 'ciudades.txt' generado con éxito (Solo Habitantes).");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generarTuberias() {
        String[] tuberias = {
            "Neufuén;Rionegra;T-01;100;274;500;ACTIVO",
            "Neufuén;Solandina;T-02;150;387;600;ACTIVO",
            "Neufuén;Terraviva;T-03;200;443;700;ACTIVO",
            "Neufuén;Bosquemonte;T-04;50;148;300;EN REPARACIÓN",
            "Cupral-Có;Piedraluna;T-05;100;312;400;ACTIVO",
            "Cupral-Có;Terraviva;T-06;150;431;550;ACTIVO",
            "Cupral-Có;Valleverde;T-07;120;268;450;EN DISEÑO",
            "Maravélica;Terraviva;T-08;180;456;600;ACTIVO",
            "Maravélica;Bosquemonte;T-09;60;164;350;ACTIVO",
            "Maravélica;Monteaurora;T-10;200;460;700;INACTIVO",
            "Rionegra;Ventisquero Alto;T-11;100;300;400;ACTIVO",
            "Solandina;Monteaurora;T-12;110;290;420;ACTIVO",
            "Cieloflor;Neufuén;T-13;150;400;500;ACTIVO",
            "Ventisquero Alto;Cieloflor;T-14;100;250;400;ACTIVO",
            "Piedraluna;Maravélica;T-15;120;320;450;ACTIVO"
        };

        try (PrintWriter pw = new PrintWriter(new FileWriter("tuberias.txt"))) {
            for (String linea : tuberias) {
                pw.println(linea);
            }
            System.out.println("Archivo 'tuberias.txt' generado con éxito.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}