package Modelo;

import java.io.Serializable;

// Interfaz para gastos deducibles
public interface GastoDeducible extends Serializable {
    double getMontoDeducible();
    CategoriaGasto getCategoria();
    boolean esDeducible();
}
