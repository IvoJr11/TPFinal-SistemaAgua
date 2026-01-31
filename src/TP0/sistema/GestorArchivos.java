package TP0.sistema;

import java.io.*;
import TP0.modelo.Ciudad;
import TP0.modelo.Tuberia;

public class GestorArchivos {

    private static final String SEPARADOR = ";";

    public void cargarDatos(SistemaRedAgua sistema, String rutaCiudades, String rutaTuberias) {
        System.out.println("Iniciando carga masiva de datos...");
        
        cargarCiudades(sistema, rutaCiudades);
        cargarTuberias(sistema, rutaTuberias);

        System.out.println("Calculando historial de aprovisionamiento según red actual...");
        sistema.inicializarHistorialAprovisionamiento();
        
        System.out.println("Carga y cálculos finalizados.");
    }

    private void cargarCiudades(SistemaRedAgua sistema, String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int contador = 0;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARADOR);

                String nombre = normalizar(datos[0].trim());
                String codigo = datos[1].trim();
                double superficie = Double.parseDouble(datos[2].trim());
                double promConsumo = Double.parseDouble(datos[3].trim());

                Ciudad nuevaCiudad = new Ciudad(nombre, codigo, superficie, promConsumo);

                int indice = 4;
                for (int anio = 0; anio < Ciudad.CANT_ANIOS; anio++) {
                    for (int mes = 0; mes < Ciudad.CANT_MESES; mes++) {
                        if (indice < datos.length) {
                            int habit = Integer.parseInt(datos[indice].trim());
                            nuevaCiudad.setHabitantes(anio, mes, habit);
                            indice++;
                        }
                    }
                }

                sistema.agregarCiudad(nuevaCiudad);
                contador++;
            }
            System.out.println("-> Se cargaron " + contador + " ciudades.");

        } catch (IOException | NumberFormatException e) {
            System.err.println("ERROR CRÍTICO leyendo ciudades: " + e.getMessage());
        }
    }

    private void cargarTuberias(SistemaRedAgua sistema, String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int contador = 0;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(SEPARADOR);

                String origen = normalizar(datos[0].trim());
                String destino = normalizar(datos[1].trim());
                String codTubo = datos[2].trim();
                double cMin = Double.parseDouble(datos[3].trim());
                double cMax = Double.parseDouble(datos[4].trim());
                double diam = Double.parseDouble(datos[5].trim());
                String estado = datos[6].trim();

                Tuberia tubo = new Tuberia(codTubo, cMin, cMax, diam, estado);
                sistema.agregarTuberia(origen, destino, tubo);
                contador++;
            }
            System.out.println("-> Se cargaron " + contador + " tuberías.");

        } catch (IOException | NumberFormatException e) {
            System.err.println("ERROR CRÍTICO leyendo tuberías: " + e.getMessage());
        }
    }

    public void registrarLog(String mensaje) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sistema.log", true))) {
            java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter
                    .ofPattern("yyyy/MM/dd HH:mm:ss");
            bw.write("[" + dtf.format(java.time.LocalDateTime.now()) + "] " + mensaje);
            bw.newLine();
        } catch (IOException e) {
            // Fallo silencioso
        }
    }

    private String normalizar(String input) {
        String resultado = "";
        if (input != null) {
            String normalizado = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
            String sinTildes = normalizado.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            resultado = sinTildes.toUpperCase().trim();
        }
        return resultado;
    }
}