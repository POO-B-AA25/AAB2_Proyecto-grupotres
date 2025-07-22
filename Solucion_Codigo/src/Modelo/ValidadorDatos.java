package Modelo; // Este archivo está en el paquete Modelo (modelos de datos y lógica)

import java.time.LocalDate; // Importa clase para manejar fechas

// Clase con métodos estáticos para validar datos importantes del sistema
public class ValidadorDatos {

    // Válida si una cédula ecuatoriana es válida (solo formato y dígito verificador)
    public static boolean validarCedula(String cedula) {
        // Si la cédula es nula o no tiene 10 dígitos, es inválida
        if (cedula == null || cedula.length() != 10) {
            return false;
        }

        try {
            // Algoritmo oficial de validación de cédula ecuatoriana
            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2}; // Coeficientes para cada dígito
            int verificador = Character.getNumericValue(cedula.charAt(9)); // Último dígito (verificador)
            int suma = 0;
            int resultado;

            // Se multiplica cada dígito por su coeficiente y se suma
            for (int i = 0; i < coeficientes.length; i++) {
                resultado = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
                if (resultado > 9) {
                    resultado -= 9; // Si el resultado es mayor a 9, se resta 9
                }
                suma += resultado;
            }

            int ultimoDigito = suma % 10;
            // La cédula es válida si el dígito verificador coincide con el cálculo
            return (ultimoDigito == 0 && verificador == 0) || (10 - ultimoDigito == verificador);

        } catch (NumberFormatException e) {
            // Si hay un error al convertir caracteres a números, es inválida
            return false;
        }
    }

    // Valida si un email tiene formato correcto (básico)
    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Expresión regular para validar email (básica)
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    // Valida si el número de factura tiene el formato correcto (ej: 001-001-000000001)
    public static boolean validarFactura(String numeroFactura) {
        if (numeroFactura == null || numeroFactura.isEmpty()) {
            return false;
        }
        // Expresión regular para el formato de factura
        String regex = "^\\d{3}-\\d{3}-\\d{9}$";
        return numeroFactura.matches(regex);
    }

    // Valida que la fecha de la factura esté en el año de la declaración
    public static boolean validarFecha(LocalDate fecha, int anioDeclaracion) {
        if (fecha == null) {
            return false;
        }
        return fecha.getYear() == anioDeclaracion;
    }
}