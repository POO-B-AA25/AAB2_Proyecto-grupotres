package Modelo; // Este archivo está en el paquete Modelo

import java.io.Serializable; // Permite guardar/cargar objetos de esta clase

// Clase que representa un rango de la tabla de impuesto a la renta
public class RangoImpuesto implements Serializable {
    private static final long serialVersionUID = 1L;

    private double fraccionBasica;               // Desde este valor empieza el rango
    private double fraccionExcedente;            // Hasta este valor termina el rango
    private double impuestoFraccionBasica;       // Impuesto fijo para la fracción básica
    private double porcentajeFraccionExcedente;  // Porcentaje para el excedente

    // Constructor con todos los datos
    public RangoImpuesto(double fraccionBasica, double fraccionExcedente,
                         double impuestoFraccionBasica, double porcentajeFraccionExcedente) {
        this.fraccionBasica = fraccionBasica;
        this.fraccionExcedente = fraccionExcedente;
        this.impuestoFraccionBasica = impuestoFraccionBasica;
        this.porcentajeFraccionExcedente = porcentajeFraccionExcedente;
    }

    // Métodos para obtener los datos
    public double getFraccionBasica() {
        return fraccionBasica;
    }

    public double getFraccionExcedente() {
        return fraccionExcedente;
    }

    public double getImpuestoFraccionBasica() {
        return impuestoFraccionBasica;
    }

    public double getPorcentajeFraccionExcedente() {
        return porcentajeFraccionExcedente;
    }

    // Representación en texto del rango (útil para depuración)
    @Override
    public String toString() {
        return "RangoImpuesto{" +
                "fraccionBasica=" + fraccionBasica +
                ", fraccionExcedente=" + fraccionExcedente +
                ", impuestoFraccionBasica=" + impuestoFraccionBasica +
                ", porcentajeFraccionExcedente=" + porcentajeFraccionExcedente +
                '}';
    }
}