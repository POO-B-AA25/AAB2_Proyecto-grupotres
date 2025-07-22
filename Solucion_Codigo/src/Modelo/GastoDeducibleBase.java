package Modelo;

// Clase abstracta para gastos deducibles
public abstract class GastoDeducibleBase implements GastoDeducible {
    private CategoriaGasto categoria;
    private boolean deducible;

    public GastoDeducibleBase(CategoriaGasto categoria, boolean deducible) {
        this.categoria = categoria;
        this.deducible = deducible;
    }

    @Override
    public CategoriaGasto getCategoria() {
        return categoria;
    }

    @Override
    public boolean esDeducible() {
        return deducible;
    }

    // Método abstracto para obtener el monto deducible
    @Override
    public abstract double getMontoDeducible();
}
