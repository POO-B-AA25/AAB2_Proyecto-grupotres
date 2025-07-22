package Modelo; // Este archivo está en el paquete Modelo

import java.io.Serial;
import java.io.Serializable; // Permite guardar/cargar objetos de esta clase
import java.util.ArrayList;  // Lista dinámica para sueldos y facturas
import java.util.HashMap;    // Tabla para gastos por categoría
import java.util.Map;        // Tabla para límites y gastos

// Clase que representa toda la declaración anual de impuestos de una persona
public class Declaracion implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; // Define un ID único para serialización

    private int anio;                                   // Año fiscal de la declaración
    private String estado;                              // Estado de la declaración (pendiente, por pagar, saldo a favor, etc.)
    private ArrayList<Sueldo> sueldosMensuales;         // Lista de sueldos de cada mes
    private ArrayList<GastoDeducible> gastosDeducibles; // Lista de gastos deducibles (polimórfica)
    private double ingresoAnual;                        // Suma de todos los sueldos
    private double baseImponible;                       // Ingresos - gastos deducibles - aportes IESS
    private double impuestoCausado;                     // Impuesto calculado según la tabla
    private double impuestoRetenido;                    // Impuesto ya retenido por el empleador
    private double saldoAPagar;                         // Diferencia entre impuesto causado y retenido
    private TablaImpuestoRenta tablaImpuesto;           // Tabla oficial de impuesto a la renta

    // Constructor por defecto (usa el año actual)
    public Declaracion() {
        this.anio = java.time.Year.now().getValue();
        this.estado = "Pendiente";
        this.sueldosMensuales = new ArrayList<>();
        this.gastosDeducibles = new ArrayList<>();
        this.ingresoAnual = 0.0;
        this.baseImponible = 0.0;
        this.impuestoCausado = 0.0;
        this.impuestoRetenido = 0.0;
        this.saldoAPagar = 0.0;
        this.tablaImpuesto = new TablaImpuestoRenta(anio);
    }

    // Constructor con el año específico
    public Declaracion(int anio) {
        this.anio = anio;
        this.estado = "Pendiente";
        this.sueldosMensuales = new ArrayList<>();
        this.gastosDeducibles = new ArrayList<>();
        this.ingresoAnual = 0.0;
        this.baseImponible = 0.0;
        this.impuestoCausado = 0.0;
        this.impuestoRetenido = 0.0;
        this.saldoAPagar = 0.0;
        this.tablaImpuesto = new TablaImpuestoRenta(anio);
    }

    // Métodos para obtener y modificar los datos principales
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Agrega o reemplaza el sueldo de un mes
    public void agregarSueldo(Sueldo sueldo) {
        // Si ya existe un sueldo para ese mes, lo reemplaza
        for (int i = 0; i < sueldosMensuales.size(); i++) {
            if (sueldosMensuales.get(i).getMes() == sueldo.getMes()) {
                sueldosMensuales.set(i, sueldo);
                return;
            }
        }
        // Si no existe, lo agrega
        sueldosMensuales.add(sueldo);
    }

    // Agrega un gasto deducible (polimórfico)
    public void agregarGastoDeducible(GastoDeducible gasto) {
        gastosDeducibles.add(gasto);
    }

    // Calcula la suma de todos los sueldos y el impuesto retenido
    public double calcularIngresoAnual() {
        ingresoAnual = 0.0;
        impuestoRetenido = 0.0;

        for (Sueldo sueldo : sueldosMensuales) {
            ingresoAnual += sueldo.getMonto();
            impuestoRetenido += sueldo.getImpuestoRetenido();
        }

        return ingresoAnual;
    }

    // Calcula la suma de todos los gastos deducibles (respetando los límites por categoría)
    public double calcularGastosDeducibles() {
        double total = 0.0;
        for (GastoDeducible gasto : gastosDeducibles) {
            if (gasto.esDeducible()) {
                total += gasto.getMontoDeducible();
            }
        }
        return total;
    }

    // Calcula la base imponible (ingresos - gastos deducibles - aportes IESS)
    public double calcularBaseImponible() {
        calcularIngresoAnual();
        calcularGastosDeducibles();

        double totalAportesIESS = 0.0;
        for (Sueldo sueldo : sueldosMensuales) {
            totalAportesIESS += sueldo.getAporteIESS();
        }

        baseImponible = ingresoAnual - calcularGastosDeducibles() - totalAportesIESS;
        if (baseImponible < 0) {
            baseImponible = 0;
        }

        return baseImponible;
    }

    // Calcula el impuesto causado usando la tabla oficial
    public double calcularImpuestoCausado() {
        calcularBaseImponible();
        impuestoCausado = tablaImpuesto.calcularImpuesto(baseImponible);
        return impuestoCausado;
    }

    // Calcula el saldo a pagar (impuesto causado - impuesto retenido)
    public double calcularSaldoAPagar() {
        calcularImpuestoCausado();
        saldoAPagar = impuestoCausado - impuestoRetenido;
        if (saldoAPagar < 0) {
            estado = "Saldo a favor";
        } else if (saldoAPagar > 0) {
            estado = "Por pagar";
        } else {
            estado = "Declaración en cero";
        }
        return saldoAPagar;
    }

    // Devuelve un resumen de la declaración en forma de texto
    public String[] getDatosResumen() {
        calcularSaldoAPagar();

        String[] resumen = new String[10];
        resumen[0] = "Año fiscal: " + anio;
        resumen[1] = "Estado: " + estado;
        resumen[2] = "Ingresos anuales: $" + String.format("%.2f", ingresoAnual);
        resumen[3] = "Aportes IESS: $" + String.format("%.2f", calcularTotalAportesIESS());
        resumen[4] = "Gastos deducibles: $" + String.format("%.2f", calcularGastosDeducibles());
        resumen[5] = "Base imponible: $" + String.format("%.2f", baseImponible);
        resumen[6] = "Impuesto causado: $" + String.format("%.2f", impuestoCausado);
        resumen[7] = "Impuesto retenido: $" + String.format("%.2f", impuestoRetenido);
        resumen[8] = "Saldo a pagar: $" + String.format("%.2f", saldoAPagar);
        resumen[9] = "Total facturas registradas: " + (gastosDeducibles.size()+1);

        return resumen;
    }

    // Calcula el total de aportes al IESS de todos los sueldos
    private double calcularTotalAportesIESS() {
        double total = 0.0;
        for (Sueldo sueldo : sueldosMensuales) {
            total += sueldo.getAporteIESS();
        }
        return total;
    }

    // Métodos para obtener las listas de sueldos y facturas
    public ArrayList<Sueldo> getSueldosMensuales() {
        return sueldosMensuales;
    }

    public ArrayList<GastoDeducible> getGastosDeducibles() {
        return gastosDeducibles;
    }

    // Devuelve el total de gastos por cada categoría
    public Map<CategoriaGasto, Double> getTotalGastosPorCategoria() {
        Map<CategoriaGasto, Double> gastosPorCategoria = new HashMap<>();
        for (GastoDeducible gasto : gastosDeducibles) {
            if (gasto.esDeducible()) {
                CategoriaGasto categoria = gasto.getCategoria();
                double monto = gasto.getMontoDeducible();
                gastosPorCategoria.put(categoria, gastosPorCategoria.getOrDefault(categoria, 0.0) + monto);
            }
        }
        return gastosPorCategoria;
    }

    // Devuelve los límites deducibles por categoría según el ingreso anual
    public Map<CategoriaGasto, Double> getLimitesDeduciblesPorCategoria() {
        Map<CategoriaGasto, Double> limites = new HashMap<>();
        double ingreso = calcularIngresoAnual();
        for (CategoriaGasto categoria : CategoriaGasto.values()) {
            limites.put(categoria, categoria.getLimiteMaximoDeducible(ingreso));
        }
        return limites;
    }

    // Valida que ningún gasto por categoría exceda el límite legal
    public boolean validarLimitesDeducibles() {
        Map<CategoriaGasto, Double> gastosPorCategoria = getTotalGastosPorCategoria();
        Map<CategoriaGasto, Double> limites = getLimitesDeduciblesPorCategoria();
        for (CategoriaGasto categoria : gastosPorCategoria.keySet()) {
            if (gastosPorCategoria.get(categoria) > limites.get(categoria)) {
                return false;
            }
        }
        return true;
    }

    // Representación en texto de la declaración (útil para depuración)
    @Override
    public String toString() {
        return "Declaracion{" +
                "anio=" + anio +
                ", estado='" + estado + '\'' +
                ", sueldosMensuales=" + sueldosMensuales.size() +
                ", gastosDeducibles=" + gastosDeducibles.size() +
                ", ingresoAnual=" + ingresoAnual +
                ", baseImponible=" + baseImponible +
                ", impuestoCausado=" + impuestoCausado +
                ", impuestoRetenido=" + impuestoRetenido +
                ", saldoAPagar=" + saldoAPagar +
                '}';
    }
}