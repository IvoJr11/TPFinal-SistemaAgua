package TP0.sistema;

import java.util.HashMap;
import TP0.modelo.Ciudad;
import TP0.modelo.Tuberia;
import TP0.modelo.ClaveDosStrings;
import TP0.estructuras.GrafoEtiquetado;
import TP0.estructuras.ArbolAVL;
import TP0.estructuras.Lista;

// Clase principal para manejar las estructuras de datos (Grafo, AVL y HashMap).

public class SistemaRedAgua {

    private GrafoEtiquetado grafo;
    private ArbolAVL ciudades;
    private HashMap<ClaveDosStrings, Tuberia> tuberias;

    public SistemaRedAgua() {
        this.grafo = new GrafoEtiquetado();
        this.ciudades = new ArbolAVL();
        this.tuberias = new HashMap<>();
    }

    public void inicializarHistorialAprovisionamiento() {
        Lista todasLasCiudades = this.ciudades.listar();
        int cant = todasLasCiudades.longitud();

        for (int i = 1; i <= cant; i++) {
            Ciudad cDestino = (Ciudad) todasLasCiudades.recuperar(i);
            String nombreDestino = cDestino.getNombre();

            double capacidadFisicaRed = 0;

            for (int j = 1; j <= cant; j++) {
                Ciudad cOrigen = (Ciudad) todasLasCiudades.recuperar(j);
                String nombreOrigen = cOrigen.getNombre();

                if (!nombreDestino.equals(nombreOrigen)) {

                    Lista camino = this.grafo.caminoMayorCaudal(nombreOrigen, nombreDestino);
                    
                    if (!camino.esVacia()) {
                        double capacidadCamino = calcularCapacidadCamino(camino);

                        if (capacidadCamino > capacidadFisicaRed) {
                            capacidadFisicaRed = capacidadCamino;
                        }
                    }
                }
            }

            double ofertaMensual = capacidadFisicaRed * 24 * 30;

            for (int anio = 0; anio < Ciudad.CANT_ANIOS; anio++) {
                for (int mes = 0; mes < Ciudad.CANT_MESES; mes++) {
                    int habitantes = cDestino.getHabitantes(anio, mes);
                    double demanda = habitantes * cDestino.getPromConsumo() * 30;

                    double real = Math.min(demanda, ofertaMensual);
                    
                    cDestino.setAprovisionamiento(anio, mes, real);
                }
            }
        }
    }

    // --- MÉTODOS ABM (Altas, Bajas, Modificaciones) ---

    public boolean agregarCiudad(Ciudad nuevaCiudad) {
        boolean exito = false;
        if (nuevaCiudad != null) {
            this.ciudades.insertar(nuevaCiudad.getNombre(), nuevaCiudad);
            this.grafo.insertarVertice(nuevaCiudad.getNombre());
            exito = true;
        }
        return exito;
    }

    public boolean agregarTuberia(String origen, String destino, Tuberia tubo) {
        boolean exito = false;
        if (origen != null && destino != null && tubo != null) {
            boolean grafoOk = this.grafo.insertarArco(origen, destino, tubo.getCaudalMax());
            if (grafoOk) {
                ClaveDosStrings clave = new ClaveDosStrings(origen, destino);
                this.tuberias.put(clave, tubo);
                exito = true;
            }
        }
        return exito;
    }

    public boolean modificarEstadoTuberia(String origen, String destino, String nuevoEstado) {
        ClaveDosStrings clave = new ClaveDosStrings(origen, destino);
        Tuberia tubo = this.tuberias.get(clave);

        if (tubo != null) {
            return tubo.setEstado(nuevoEstado);
        }
        return false;
    }

    public boolean eliminarCiudad(String nombre) {
        return this.ciudades.eliminar(nombre);
    }

    // --- MÉTODOS DE CONSULTA BÁSICA ---

    public Lista getCiudades() {
        return this.ciudades.listar();
    }

    public Ciudad buscarCiudadPorNombre(String nombre) {
        Ciudad encontrada = null;
        Object resultado = this.ciudades.recuperar(nombre);
        if (resultado instanceof Ciudad) {
            encontrada = (Ciudad) resultado;
        }
        return encontrada;
    }

    public Lista listarCiudadesRango(String minNombre, String maxNombre) {
        return this.ciudades.listarRango(minNombre, maxNombre);
    }

    public Lista generarRankingConsumo() {
        Lista todas = this.ciudades.listar();
        int cant = todas.longitud();
        Ciudad[] arreglo = new Ciudad[cant];

        for (int i = 0; i < cant; i++) {
            arreglo[i] = (Ciudad) todas.recuperar(i + 1);
        }

        int ultimoAnio = Ciudad.CANT_ANIOS - 1;
        int ultimoMes = Ciudad.CANT_MESES - 1;

        for (int i = 0; i < cant - 1; i++) {
            for (int j = 0; j < cant - 1 - i; j++) {
                double demandaA = arreglo[j].getHabitantes(ultimoAnio, ultimoMes) * arreglo[j].getPromConsumo();
                double demandaB = arreglo[j + 1].getHabitantes(ultimoAnio, ultimoMes) * arreglo[j + 1].getPromConsumo();

                if (demandaA < demandaB) {
                    Ciudad temp = arreglo[j];
                    arreglo[j] = arreglo[j + 1];
                    arreglo[j + 1] = temp;
                }
            }
        }

        Lista ranking = new Lista();
        for (Ciudad c : arreglo) {
            ranking.insertar(c, ranking.longitud() + 1);
        }
        return ranking;
    }

    // --- ALGORITMOS DE CAMINOS Y ANÁLISIS ---

    public String obtenerCaminoMinimo(String origen, String destino) {
        String resultado;
        if (this.ciudades.recuperar(origen) == null) {
            resultado = "ERROR: La ciudad de origen '" + origen + "' NO EXISTE.";
        } else if (this.ciudades.recuperar(destino) == null) {
            resultado = "ERROR: La ciudad de destino '" + destino + "' NO EXISTE.";
        } else {
            Lista camino = this.grafo.caminoMasCorto(origen, destino);
            if (camino.esVacia()) {
                resultado = "No existe camino físico conectado.";
            } else {
                resultado = analizarEstadoDelCamino(camino);
            }
        }
        return resultado;
    }

    public String obtenerCaminoCaudalMaximo(String origen, String destino) {
        String resultado;
        if (this.ciudades.recuperar(origen) == null || this.ciudades.recuperar(destino) == null) {
            resultado = "ERROR: Ciudades no existentes.";
        } else {
            Lista mejorCamino = this.grafo.caminoMayorCaudal(origen, destino);
            if (mejorCamino.esVacia()) {
                resultado = "No existe camino físico conectado.";
            } else {
                resultado = "--- CAMINO DE MÁXIMO CAUDAL ---\n" + analizarEstadoDelCamino(mejorCamino);
            }
        }
        return resultado;
    }

    public String obtenerCaminoEvitandoCiudad(String origen, String destino, String evitar) {
        String resultado;
        if (this.ciudades.recuperar(origen) == null ||
                this.ciudades.recuperar(destino) == null ||
                this.ciudades.recuperar(evitar) == null) {
            resultado = "Error: Alguna de las ciudades no existe.";
        } else {
            Lista camino = this.grafo.caminoSinPasarPor(origen, destino, evitar);
            if (camino.esVacia()) {
                resultado = "No hay camino posible sin pasar por " + evitar;
            } else {
                resultado = "--- CAMINO EVASIVO (Evita " + evitar + ") ---\n" + analizarEstadoDelCamino(camino);
            }
        }
        return resultado;
    }

    public String obtenerCaminosFiltrados(String origen, String destino, double caudalRequerido) {
        String reporte;
        if (this.ciudades.recuperar(origen) == null || this.ciudades.recuperar(destino) == null) {
            reporte = "Error: Ciudades no válidas.";
        } else {
            Lista todos = this.grafo.obtenerTodosLosCaminos(origen, destino);
            if (todos.esVacia()) {
                reporte = "No existen caminos entre " + origen + " y " + destino;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("--- CAMINOS CAPACES DE TRANSPORTAR ").append(caudalRequerido).append(" m3 ---\n");

                boolean hayAlguno = false;
                int cant = todos.longitud();

                for (int i = 1; i <= cant; i++) {
                    Lista camino = (Lista) todos.recuperar(i);
                    double capacidadReal = calcularCapacidadCamino(camino);

                    if (capacidadReal >= caudalRequerido) {
                        sb.append("Camino ").append(i).append(": ")
                                .append(camino.toString())
                                .append(" (Capacidad: ").append(capacidadReal).append(")\n");
                        hayAlguno = true;
                    }
                }
                if (!hayAlguno)
                    sb.append("Ningún camino soporta el caudal solicitado.");
                reporte = sb.toString();
            }
        }
        return reporte;
    }

    // --- MÉTODOS PRIVADOS AUXILIARES ---

    private String analizarEstadoDelCamino(Lista camino) {
        String estadoGlobal = Tuberia.ESTADO_ACTIVO;
        String detalleCausa = "Todo operativo.";

        boolean hayDisenio = false;
        boolean hayInactivo = false;
        boolean hayReparacion = false;

        String causaNomenclatura = "";
        String causaTramo = "";

        int longitud = camino.longitud();
        for (int i = 1; i < longitud; i++) {
            String ciudadA = (String) camino.recuperar(i);
            String ciudadB = (String) camino.recuperar(i + 1);

            ClaveDosStrings clave = new ClaveDosStrings(ciudadA, ciudadB);
            Tuberia tubo = this.tuberias.get(clave);

            if (tubo != null) {
                String estadoTubo = tubo.getEstado();
                if (estadoTubo.equals(Tuberia.ESTADO_DISENIO)) {
                    hayDisenio = true;
                    causaNomenclatura = tubo.getNomenclatura();
                    causaTramo = ciudadA + " y " + ciudadB;
                } else if (estadoTubo.equals(Tuberia.ESTADO_INACTIVO)) {
                    hayInactivo = true;
                    if (!hayDisenio) {
                        causaNomenclatura = tubo.getNomenclatura();
                        causaTramo = ciudadA + " y " + ciudadB;
                    }
                } else if (estadoTubo.equals(Tuberia.ESTADO_REPARACION)) {
                    hayReparacion = true;
                    if (!hayDisenio && !hayInactivo) {
                        causaNomenclatura = tubo.getNomenclatura();
                        causaTramo = ciudadA + " y " + ciudadB;
                    }
                }
            }
        }

        if (hayDisenio) {
            estadoGlobal = Tuberia.ESTADO_DISENIO;
            detalleCausa = "Corte por Obra: Tubería " + causaNomenclatura + " entre " + causaTramo;
        } else if (hayInactivo) {
            estadoGlobal = Tuberia.ESTADO_INACTIVO;
            detalleCausa = "Corte Total: Tubería " + causaNomenclatura + " entre " + causaTramo;
        } else if (hayReparacion) {
            estadoGlobal = Tuberia.ESTADO_REPARACION;
            detalleCausa = "Mantenimiento: Tubería " + causaNomenclatura + " entre " + causaTramo;
        }

        return "Camino hallado: " + camino.toString() + "\n" +
                "Estado Global: " + estadoGlobal + "\n" +
                "Detalle: " + detalleCausa;
    }

    private double calcularCapacidadCamino(Lista camino) {
        double capacidadMinima = Double.MAX_VALUE;
        int longi = camino.longitud();
        boolean caminoRoto = false;
        int i = 1;

        while (i < longi && !caminoRoto) {
            String c1 = (String) camino.recuperar(i);
            String c2 = (String) camino.recuperar(i + 1);
            ClaveDosStrings k = new ClaveDosStrings(c1, c2);
            Tuberia t = this.tuberias.get(k);

            if (t != null) {
                if (t.getCaudalMax() < capacidadMinima) {
                    capacidadMinima = t.getCaudalMax();
                }
            } else {
                caminoRoto = true;
                capacidadMinima = 0.0;
            }
            i++;
        }
        if (capacidadMinima == Double.MAX_VALUE)
            capacidadMinima = 0.0;
        return capacidadMinima;
    }

    @Override
    public String toString() {
        return "=== ESTADO DEL SISTEMA ===\n" +
                "1. CIUDADES (AVL):\n" + this.ciudades.toString() + "\n" +
                "2. RED (GRAFO):\n" + this.grafo.toString() + "\n" +
                "3. TUBERIAS (DETALLE):\n" + this.tuberias.toString();
    }
}