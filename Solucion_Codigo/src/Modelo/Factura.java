package Modelo; // Este archivo está en el paquete Modelo

import java.time.LocalDate;  // Para manejar fechas
import java.time.format.DateTimeFormatter; // Para mostrar fechas en texto

// Clase que representa una factura de gasto deducible
public class Factura implements GastoDeducible {
    private String numero;              // Número de la factura (ej: 001-001-000000001)
    private String proveedor;           // Nombre del proveedor
    private LocalDate fecha;            // Fecha de la factura
    private double monto;               // Monto sin IVA
    private double iva;                 // Valor del IVA
    private CategoriaGasto categoria;   // Categoría del gasto (vivienda, salud, etc.)
    private boolean deducible;          // Si la factura es deducible o no

    // Constructor vacío (por defecto)
    public Factura() {
        this.numero = "";
        this.proveedor = "";
        this.fecha = LocalDate.now();
        this.monto = 0.0;
        this.iva = 0.0;
        this.categoria = null;
        this.deducible = false;
    }

    // Constructor con todos los datos
    public Factura(String numero, String proveedor, LocalDate fecha, double monto, double iva, CategoriaGasto categoria) {
        this.numero = numero;
        this.proveedor = proveedor;
        this.fecha = fecha;
        this.monto = monto;
        this.iva = iva;
        this.categoria = categoria;
        this.deducible = true;  // Por defecto, la factura es deducible
    }

    // Métodos para obtener y modificar los datos
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public CategoriaGasto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaGasto categoria) {
        this.categoria = categoria;
    }

    public boolean isDeducible() {
        return deducible;
    }

    public void setDeducible(boolean deducible) {
        this.deducible = deducible;
    }

    // Devuelve el monto deducible (si la factura es deducible, devuelve el monto; si no, 0)
    @Override
    public double getMontoDeducible() {
        return deducible ? monto : 0.0;
    }

    @Override
    public boolean esDeducible() {
        return deducible;
    }

    // Representación en texto de la factura (útil para depuración)
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Factura{" +
                "numero='" + numero + '\'' +
                ", proveedor='" + proveedor + '\'' +
                ", fecha=" + fecha.format(formatter) +
                ", monto=" + monto +
                ", iva=" + iva +
                ", categoria=" + categoria +
                ", deducible=" + deducible +
                '}';
    }
}