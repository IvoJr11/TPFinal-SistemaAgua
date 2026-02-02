package TP0.interfaz;

import java.util.Scanner;
import TP0.sistema.SistemaRedAgua;
import TP0.sistema.GestorArchivos;
import TP0.modelo.Ciudad;
import TP0.estructuras.Lista;

// Clase encargada de generar el menú principal del sistema.

public class Menu {

    private SistemaRedAgua sistema;
    private Scanner scanner;
    private GestorArchivos gestorArchivos;

    public Menu(SistemaRedAgua sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
        this.gestorArchivos = new GestorArchivos();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            opcion = leerEntero("Ingrese una opción: ");
            procesarOpcion(opcion);
        } while (opcion != 0);
    }

    private void mostrarOpciones() {
        System.out.println("\n--- SISTEMA DE RED DE AGUA 2025 ---");
        System.out.println("1.  Cargar datos iniciales (Lote)");
        System.out.println("2.  Consultar una Ciudad (Habitantes y Agua)");
        System.out.println("3.  Mostrar estado del sistema (Debug)");
        System.out.println("4.  Ver Ranking de Consumo (Menor a Mayor)");
        System.out.println("5.  Consultar camino mínimo (Menos ciudades)");
        System.out.println("6.  Modificar estado de Tubería");
        System.out.println("7.  Dar de baja una Ciudad");
        System.out.println("8.  Consultar Ciudades por Rango");
        System.out.println("9.  Actualizar Habitantes");
        System.out.println("10. Modificar datos de Ciudad");
        System.out.println("11. Consultar camino por CAUDAL PLENO");
        System.out.println("12. Alta de nueva Ciudad (Manual)");
        System.out.println("13. Camino evitando una Ciudad");
        System.out.println("14. Todos los caminos filtrados por Caudal");
        System.out.println("15. Historial de ciudades");
        System.out.println("0.  Salir");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                ejecutarCargaInicial();
                break;
            case 2:
                ejecutarConsultaCiudad();
                break;
            case 3:
                System.out.println(sistema.toString());
                break;
            case 4:
                mostrarRanking();
                break;
            case 5:
                consultarCaminoMinimo();
                break;
            case 6:
                modificarTuberia();
                break;
            case 7:
                bajaCiudad();
                break;
            case 8:
                consultarCiudadPorRango();
                break;
            case 9:
                actualizarHabitantes();
                break;
            case 10:
                modificarCiudad();
                break;
            case 11:
                consultarCaminoCaudal();
                break;
            case 12:
                altaCiudadManual();
                break;
            case 13:
                consultarCaminoEvasivo();
                break;
            case 14:
                consultarTodosFiltro();
                break;
            case 15:
                imprimirAprovisionamientoHabitante();
                break;
            case 0:
                salirDelSistema();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    // --- MÉTODOS DE ACCIÓN ---

    // Método para cargar los datos iniciales
    private void ejecutarCargaInicial() {
        String rutaCiudades = "ciudades.txt";
        String rutaTuberias = "tuberias.txt";

        this.gestorArchivos.cargarDatos(sistema, rutaCiudades, rutaTuberias);

        gestorArchivos.registrarLog("Operación: Carga inicial de datos completada.");
        gestorArchivos.registrarLog("--- ESTADO INICIAL DEL SISTEMA ---");
        gestorArchivos.registrarLog(sistema.toString());
    }

    // Método para consultar una ciudad
    private void ejecutarConsultaCiudad() {
        System.out.println("--- CONSULTA DE CIUDAD ---");
        scanner.nextLine();

        System.out.print("Ingrese el nombre de la ciudad: ");
        String nombre = normalizar(scanner.nextLine());

        Ciudad c = sistema.buscarCiudadPorNombre(nombre);

        if (c != null) {
            int anio = leerEntero("Ingrese Año (0-9): ");
            int mes = leerEntero("Ingrese Mes (0-11): ");

            int poblacion = c.getHabitantes(anio, mes);
            double agua = c.getAprovisionamiento(anio, mes);
            boolean deficit = c.tuvoDeficit(anio, mes);

            System.out.println("\nResumen para " + nombre + " (Año " + anio + ", Mes " + mes + "):");
            System.out.println("- Población: " + poblacion);
            System.out.println("- Agua Disponible: " + agua);
            System.out.println("- Estado: " + (deficit ? "EN DÉFICIT" : "ABASTECIMIENTO OK"));
        } else {
            System.out.println("Error: Ciudad no encontrada.");
        }
    }

    // Método para mostrar el ranking de consumo
    private void mostrarRanking() {
        System.out.println("--- RANKING DE CONSUMO (Último Mes) ---");
        Lista ranking = sistema.generarRankingConsumo();
        int longitud = ranking.longitud();

        gestorArchivos.registrarLog("Operación: Generación de Ranking de Consumo.");

        for (int i = 1; i <= longitud; i++) {
            Ciudad c = (Ciudad) ranking.recuperar(i);
            // Cálculo para visualización
            int ultimoAnio = Ciudad.CANT_ANIOS - 1;
            int ultimoMes = Ciudad.CANT_MESES - 1;
            double demanda = c.getHabitantes(ultimoAnio, ultimoMes) * c.getPromConsumo() * 30;

            System.out.println(i + ". " + c.getNombre() + " - Consumo Est.: " + String.format("%.2f", demanda) + " m3");
        }
    }

    // Método para consultar camino mínimo de una ciudad a otra
    private void consultarCaminoMinimo() {
        scanner.nextLine();
        System.out.print("Ciudad Origen: ");
        String origen = normalizar(scanner.nextLine());
        System.out.print("Ciudad Destino: ");
        String destino = normalizar(scanner.nextLine());

        gestorArchivos.registrarLog("Operación: Consulta de camino mínimo entre " + origen + " y " + destino);
        System.out.println("\n--- RESULTADO ---");
        System.out.println(sistema.obtenerCaminoMinimo(origen, destino));
    }

    // Método para modificar estado de una tubería
    private void modificarTuberia() {
        scanner.nextLine();
        System.out.println("\n--- MODIFICAR ESTADO TUBERÍA ---");
        System.out.print("Ciudad Origen: ");
        String origen = normalizar(scanner.nextLine());
        System.out.print("Ciudad Destino: ");
        String destino = normalizar(scanner.nextLine());

        System.out.println("Estados: ACTIVO, EN REPARACION, EN DISENIO, INACTIVO");
        System.out.print("Nuevo Estado: ");
        String estado = scanner.nextLine();

        boolean exito = sistema.modificarEstadoTuberia(origen, destino, estado);

        if (exito) {
            System.out.println("-> Estado actualizado correctamente.");
            gestorArchivos.registrarLog("Modificación: Tubería " + origen + "-" + destino + " a " + estado);
        } else {
            System.out.println("-> Error: Tubería no encontrada o estado inválido.");
        }
    }

    // Método para dar de baja una ciudad
    private void bajaCiudad() {
        scanner.nextLine();
        System.out.println("\n--- BAJA DE CIUDAD ---");
        System.out.print("Nombre de la ciudad a eliminar: ");
        String nombre = normalizar(scanner.nextLine());

        System.out.print("¿Confirmar eliminación? (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            boolean exito = sistema.eliminarCiudad(nombre);
            if (exito) {
                System.out.println("-> Ciudad eliminada del sistema (AVL).");
                gestorArchivos.registrarLog("Baja: Se eliminó la ciudad " + nombre);
            } else {
                System.out.println("-> Error: La ciudad no existe.");
            }
        }
    }

    // Método para consultar ciudades por rango
    private void consultarCiudadPorRango() {
        scanner.nextLine();
        System.out.println("--- BÚSQUEDA POR RANGO ---");
        System.out.print("Nombre Mínimo: ");
        String minNom = normalizar(scanner.nextLine());
        System.out.print("Nombre Máximo: ");
        String maxNom = normalizar(scanner.nextLine());

        double minVol = leerDouble("Volumen Mínimo: ");
        double maxVol = leerDouble("Volumen Máximo: ");
        int anio = leerEntero("Año: ");
        int mes = leerEntero("Mes: ");

        Lista candidatos = sistema.listarCiudadesRango(minNom, maxNom);

        System.out.println("\nResultados:");
        int longi = candidatos.longitud();
        boolean hayResultados = false;

        for (int i = 1; i <= longi; i++) {
            Ciudad c = (Ciudad) candidatos.recuperar(i);
            double consumo = c.getHabitantes(anio, mes) * c.getPromConsumo() * 30;

            if (consumo >= minVol && consumo <= maxVol) {
                System.out.println("- " + c.getNombre() + " (Consumo: " + String.format("%.2f", consumo) + ")");
                hayResultados = true;
            }
        }
        if (!hayResultados)
            System.out.println("Ninguna ciudad cumple con los criterios.");
    }

    // Método para actualizar habitantes de una ciudad
    private void actualizarHabitantes() {
        scanner.nextLine();
        System.out.println("\n--- ACTUALIZAR POBLACIÓN ---");
        System.out.print("Ciudad: ");
        String nombre = normalizar(scanner.nextLine());

        Ciudad c = sistema.buscarCiudadPorNombre(nombre);
        if (c != null) {
            int anio = leerEntero("Año (0-9): ");
            int mes = leerEntero("Mes (0-11): ");
            int cantidad = leerEntero("Nueva cantidad de habitantes: ");

            boolean exito = c.setHabitantes(anio, mes, cantidad);
            if (exito) {
                System.out.println("-> Registro actualizado.");
                gestorArchivos.registrarLog("Actualización: Habitantes de " + nombre + " a " + cantidad);
            } else {
                System.out.println("-> Error: Fecha inválida.");
            }
        } else {
            System.out.println("-> Error: Ciudad no encontrada.");
        }
    }

    // Método para modificar datos de una ciudad
    private void modificarCiudad() {
        scanner.nextLine();
        System.out.println("\n--- MODIFICAR DATOS CIUDAD ---");
        System.out.print("Ciudad a modificar: ");
        String nombre = normalizar(scanner.nextLine());

        Ciudad c = sistema.buscarCiudadPorNombre(nombre);
        if (c != null) {
            System.out.println("Datos actuales - Consumo Prom: " + c.getPromConsumo());
            double nuevoConsumo = leerDouble("Ingrese nuevo Consumo Promedio: ");

            c.setPromConsumo(nuevoConsumo);
            System.out.println("-> Ciudad actualizada.");
            gestorArchivos.registrarLog("Modificación: Consumo de " + nombre + " cambiado a " + nuevoConsumo);
        } else {
            System.out.println("-> Error: Ciudad no encontrada.");
        }
    }

    // Método para consultar camino por caudal máximo
    private void consultarCaminoCaudal() {
        scanner.nextLine();
        System.out.println("\n--- CONSULTA DE CAMINO (OPTIMIZADO POR CAUDAL) ---");
        System.out.print("Ciudad Origen: ");
        String origen = normalizar(scanner.nextLine());
        System.out.print("Ciudad Destino: ");
        String destino = normalizar(scanner.nextLine());

        gestorArchivos.registrarLog("Consulta: Camino Caudal Pleno " + origen + " -> " + destino);
        System.out.println("\n--- RESULTADO ---");
        System.out.println(sistema.obtenerCaminoCaudalMaximo(origen, destino));
    }

    // Método para dar de alta una nueva ciudad
    private void altaCiudadManual() {
        scanner.nextLine();
        System.out.println("\n--- ALTA DE CIUDAD ---");
        System.out.print("Nombre: ");
        String nombre = normalizar(scanner.nextLine());

        if (sistema.buscarCiudadPorNombre(nombre) != null) {
            System.out.println("Error: Ya existe una ciudad con ese nombre.");
        } else {
            System.out.print("Código/Nomenclatura (Ej: N-01): ");
            String codigo = scanner.nextLine().toUpperCase();
            double superficie = leerDouble("Superficie: ");
            double consumo = leerDouble("Consumo Promedio: ");

            Ciudad nueva = new Ciudad(nombre, codigo, superficie, consumo);

            if (sistema.agregarCiudad(nueva)) {
                System.out.println("-> Ciudad agregada al sistema.");
                gestorArchivos.registrarLog("Alta Manual: Ciudad " + nombre);
            } else {
                System.out.println("-> Error al guardar.");
            }
        }
    }

    // Método para consultar camino evitando una ciudad
    private void consultarCaminoEvasivo() {
        scanner.nextLine();
        System.out.println("\n--- CAMINO EVADIENDO CIUDAD ---");
        System.out.print("Origen: ");
        String org = normalizar(scanner.nextLine());
        System.out.print("Destino: ");
        String des = normalizar(scanner.nextLine());
        System.out.print("Ciudad a EVITAR: ");
        String ev = normalizar(scanner.nextLine());

        gestorArchivos.registrarLog("Consulta: Camino " + org + " -> " + des + " evitando " + ev);
        System.out.println(sistema.obtenerCaminoEvitandoCiudad(org, des, ev));
    }

    // Método para consultar todos los caminos filtrados por caudal
    private void consultarTodosFiltro() {
        scanner.nextLine();
        System.out.println("\n--- FILTRO DE CAMINOS POR CAUDAL ---");
        System.out.print("Origen: ");
        String org = normalizar(scanner.nextLine());
        System.out.print("Destino: ");
        String des = normalizar(scanner.nextLine());
        double caudal = leerDouble("Caudal mínimo requerido: ");

        gestorArchivos.registrarLog("Consulta: Caminos " + org + " -> " + des + " con capacidad >= " + caudal);
        System.out.println(sistema.obtenerCaminosFiltrados(org, des, caudal));
    }

    private void salirDelSistema() {
        System.out.println("Guardando estado final en LOG...");
        gestorArchivos.registrarLog("Operación: Fin de ejecución.");
        gestorArchivos.registrarLog("--- ESTADO FINAL DEL SISTEMA ---");
        gestorArchivos.registrarLog(sistema.toString());
        System.out.println("Cerrando sistema...");
    }

    // Método para mostrar el aprovisionamiento y habitantes de todas las ciudades en mes/año
    private void imprimirAprovisionamientoHabitante() {
        System.out.println("\n--- CONSULTA DE DISTRIBUCIÓN MENSUAL ---");
    
        int anio = leerEntero("Ingrese año (0-9): ");
        int mes = leerEntero("Ingrese mes (0-11): ");

        if (anio >= 0 && anio < Ciudad.CANT_ANIOS && 
            mes >= 0 && mes < Ciudad.CANT_MESES) {

            Lista ciudades = sistema.getCiudades(); 
            int longitud = ciudades.longitud();

            System.out.println("Resultados para Año " + anio + " / Mes " + mes + ":");
        
            for (int i = 1; i <= longitud; i++) {
                Ciudad ciudad = (Ciudad) ciudades.recuperar(i);
                if (ciudad != null) {
                    double aprovisionamiento = ciudad.getAprovisionamiento(anio, mes);
                    int habitantes = ciudad.getHabitantes(anio, mes);
                    
                    System.out.println("Ciudad: " + String.format("%-15s", ciudad.getNombre()) + 
                                    " | Aprov: " + String.format("%10.2f", aprovisionamiento) + " m3" +
                                    " | Hab: " + habitantes);
                }
            }

        } else {
           System.out.println("Error: Fecha fuera de rango (10 años).");
        }
    }

    // --- MÉTODOS AUXILIARES (Validación y Entrada) ---

    private int leerEntero(String mensaje) {
        int valor = -1;
        boolean leido = false;
        while (!leido) {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(scanner.next());
                leido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número entero.");
            }
        }
        return valor;
    }

    private double leerDouble(String mensaje) {
        double valor = -1;
        boolean leido = false;
        while (!leido) {
            System.out.print(mensaje);
            try {
                valor = Double.parseDouble(scanner.next());
                leido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido.");
            }
        }
        return valor;
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