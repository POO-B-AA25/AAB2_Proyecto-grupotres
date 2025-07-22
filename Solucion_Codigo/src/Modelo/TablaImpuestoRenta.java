package Modelo; // Este archivo está en el paquete Modelo

import java.io.Serializable; // Permite guardar/cargar objetos de esta clase en archivos
import java.util.ArrayList;  // Lista dinámica para guardar los rangos de la tabla

// Clase que representa la tabla oficial de impuesto a la renta de un año
public class TablaImpuestoRenta implements Serializable {
    private static final long serialVersionUID = 1L; // Versión para serialización

    private int anio; // Año fiscal de la tabla
    private ArrayList<RangoImpuesto> rangos; // Lista de rangos de impuesto

    // Constructor: recibe el año y crea la tabla con los rangos oficiales
    public TablaImpuestoRenta(int anio) {
        this.anio = anio;
        this.rangos = new ArrayList<>();
        inicializarTabla2023(); // Llena la tabla con los valores del año 2023
    }

    // Llena la tabla con los valores oficiales del SRI para 2023
    private void inicializarTabla2023() {
        // Cada rango: fracción básica, fracción excedente, impuesto básico, % impuesto excedente
        rangos.add(new RangoImpuesto(0.00, 11310.00, 0.00, 0.00));
        rangos.add(new RangoImpuesto(11310.01, 14410.00, 0.00, 0.05));
        rangos.add(new RangoImpuesto(14410.01, 18010.00, 155.00, 0.10));
        rangos.add(new RangoImpuesto(18010.01, 21630.00, 515.00, 0.12));
        rangos.add(new RangoImpuesto(21630.01, 31630.00, 949.40, 0.15));
        rangos.add(new RangoImpuesto(31630.01, 41630.00, 2449.40, 0.20));
        rangos.add(new RangoImpuesto(41630.01, 51630.00, 4449.40, 0.25));
        rangos.add(new RangoImpuesto(51630.01, 61630.00, 6949.40, 0.30));
        rangos.add(new RangoImpuesto(61630.01, 100000.00, 9949.40, 0.35));
        rangos.add(new RangoImpuesto(100000.01, Double.MAX_VALUE, 23378.90, 0.37));
    }

    // Calcula el impuesto a la renta según la base imponible usando la tabla
    public double calcularImpuesto(double baseImponible) {
        RangoImpuesto rango = buscarRango(baseImponible); // Busca el rango correspondiente

        if (rango == null) {
            return 0.0; // Si no encuentra rango, no hay impuesto
        }

        double excedente = baseImponible - rango.getFraccionBasica(); // Lo que excede la fracción básica
        double impuestoExcedente = excedente * rango.getPorcentajeFraccionExcedente(); // Impuesto por el excedente

        // Impuesto total = impuesto básico + impuesto por excedente
        return rango.getImpuestoFraccionBasica() + impuestoExcedente;
    }

    // Busca el rango de la tabla donde cae la base imponible
    public RangoImpuesto buscarRango(double baseImponible) {
        for (RangoImpuesto rango : rangos) {
            // Si está dentro del rango actual o es el último rango
            if (baseImponible >= rango.getFraccionBasica() &&
                    (baseImponible < rango.getFraccionExcedente() || rango.getFraccionExcedente() == Double.MAX_VALUE)) {
                return rango;
            }
        }
        return null; // No debería pasar si la tabla está bien definida
    }

    // Métodos para obtener los datos de la tabla
    public int getAnio() {
        return anio;
    }

    public ArrayList<RangoImpuesto> getRangos() {
        return rangos;
    }

    // Representación en texto de la tabla (útil para depuración)
    @Override
    public String toString() {
        return "TablaImpuestoRenta{" +
                "anio=" + anio +
                ", rangos=" + rangos.size() +
                '}';
    }
}