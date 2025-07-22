package Modelo;

import java.io.Serializable;

// Interfaz para gastos deducibles
public interface GastoDeducible extends Serializable {
    CategoriaGasto getCategoria();
    double getMontoDeducible();
    boolean esDeducible();
}
