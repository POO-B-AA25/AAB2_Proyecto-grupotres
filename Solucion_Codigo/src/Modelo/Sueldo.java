package Modelo; // Este archivo está en el paquete Modelo

import java.io.Serializable; // Permite guardar/cargar objetos de esta clase

// Clase que representa el sueldo de un mes
public class Sueldo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int mes;                // Mes del sueldo (1-12)
    private double monto;           // Monto bruto del sueldo
    private double aporteIESS;      // Aporte al IESS (seguridad social)
    private double impuestoRetenido;// Impuesto a la renta retenido por el empleador

    // Constructor vacío (por defecto)
    public Sueldo() {
        this.mes = 0;
        this.monto = 0.0;
        this.aporteIESS = 0.0;
        this.impuestoRetenido = 0.0;
    }

    // Constructor con todos los datos
    public Sueldo(int mes, double monto, double aporteIESS, double impuestoRetenido) {
        this.mes = mes;
        this.monto = monto;
        this.aporteIESS = aporteIESS;
        this.impuestoRetenido = impuestoRetenido;
    }

    // Métodos para obtener y modificar los datos
    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getAporteIESS() {
        return aporteIESS;
    }

    public void setAporteIESS(double aporteIESS) {
        this.aporteIESS = aporteIESS;
    }

    public double getImpuestoRetenido() {
        return impuestoRetenido;
    }

    public void setImpuestoRetenido(double impuestoRetenido) {
        this.impuestoRetenido = impuestoRetenido;
    }

    // Calcula el monto neto (lo que realmente recibe el empleado)
    public double getMontoNeto() {
        return monto - aporteIESS - impuestoRetenido;
    }

    // Representación en texto del sueldo (útil para depuración)
    @Override
    public String toString() {
        return "Sueldo{" +
                "mes=" + mes +
                ", monto=" + monto +
                ", aporteIESS=" + aporteIESS +
                ", impuestoRetenido=" + impuestoRetenido +
                ", montoNeto=" + getMontoNeto() +
                '}';
    }
}