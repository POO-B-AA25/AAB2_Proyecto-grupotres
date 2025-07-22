package Controlador; // Este archivo está en el paquete Controlador (controla la lógica principal)

import Modelo.*; // Importa todos los modelos de datos (Contribuyente, Declaracion, etc.)
import java.io.*; // Para guardar y cargar archivos
import java.time.LocalDate; // Para manejar fechas
import java.util.Map; // Para manejar tablas de datos (gastos por categoría, límites, etc.)

// Clase principal que controla la lógica del sistema y conecta la vista con los modelos
public class SistemaController {
    private Contribuyente contribuyente; // Persona que declara impuestos
    private Declaracion declaracion;     // Declaración anual de impuestos

    // Constructor: inicia sin contribuyente ni declaración
    public SistemaController() {
        this.contribuyente = null;
        this.declaracion = null;
    }

    // Registra los datos del contribuyente y crea una nueva declaración
    public boolean registrarContribuyente(String nombre, String cedula, String direccion, String telefono, String email, String ocupacion) {
        // Validar cédula y email usando ValidadorDatos
        if (!ValidadorDatos.validarCedula(cedula)) {
            System.err.println("La cédula ingresada no es válida.");
            return false;
        }

        if (!ValidadorDatos.validarEmail(email)) {
            System.err.println("El correo electrónico ingresado no es válido.");
            return false;
        }

        try {
            // ver Si todo es válido, crea el contribuyente y la declaración
            this.contribuyente = new Contribuyente(nombre, cedula, direccion, telefono, email, ocupacion);
            this.declaracion = new Declaracion();
            return true;
        } catch (Exception e) {
            System.err.println("Error al registrar contribuyente: " + e.getMessage());
            return false;
        }
    }

    // Agrega o reemplaza el sueldo de un mes en la declaración
    public boolean agregarSueldo(int mes, double monto, double aporteIESS, double impuestoRetenido) {
        if (declaracion == null) {
            System.err.println("No hay una declaración activa. Registre un contribuyente primero.");
            return false;
        }

        if (mes < 1 || mes > 12) {
            System.err.println("El mes debe estar entre 1 y 12.");
            return false;
        }

        if (monto < 0 || aporteIESS < 0 || impuestoRetenido < 0) {
            System.err.println("Los valores monetarios no pueden ser negativos.");
            return false;
        }

        try {
            Sueldo sueldo = new Sueldo(mes, monto, aporteIESS, impuestoRetenido);
            declaracion.agregarSueldo(sueldo);
            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar sueldo: " + e.getMessage());
            return false;
        }
    }

    // Agrega cualquier gasto deducible usando polimorfismo
    public boolean agregarGastoDeducible(GastoDeducible gasto) {
        if (declaracion == null) {
            System.err.println("No hay una declaración activa. Registre un contribuyente primero.");
            return false;
        }
        try {
            declaracion.agregarGastoDeducible(gasto);
            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar gasto deducible: " + e.getMessage());
            return false;
        }
    }

    // El método agregarFactura ahora usa polimorfismo
    public boolean agregarFactura(String numero, String proveedor, LocalDate fecha, double monto, double iva, CategoriaGasto categoria) {
        if (declaracion == null) {
            System.err.println("No hay una declaración activa. Registre un contribuyente primero.");
            return false;
        }
        // Validar número de factura
        if (!ValidadorDatos.validarFactura(numero)) {
            System.err.println("El número de factura no es válido.");
            return false;
        }
        // Validar fecha (debe ser del año de la declaración)
        if (!ValidadorDatos.validarFecha(fecha, declaracion.getAnio())) {
            System.err.println("La fecha debe corresponder al año de la declaración.");
            return false;
        }
        if (monto <= 0 || iva < 0) {
            System.err.println("El monto debe ser positivo y el IVA no puede ser negativo.");
            return false;
        }
        try {
            GastoDeducible factura = new Factura(numero, proveedor, fecha, monto, iva, categoria);
            return agregarGastoDeducible(factura);
        } catch (Exception e) {
            System.err.println("Error al agregar factura: " + e.getMessage());
            return false;
        }
    }

    // Valida que la declaración tenga datos mínimos y que los gastos no excedan los límites legales
    public boolean validarDatosDeclaracion() {
        if (declaracion == null) {
            System.err.println("No hay una declaración activa. Registre un contribuyente primero.");
            return false;
        }

        // Verificar que haya al menos un sueldo registrado
        if (declaracion.getSueldosMensuales().isEmpty()) {
            System.err.println("Debe registrar al menos un sueldo mensual.");
            return false;
        }

        // Validar los límites de gastos deducibles por categoría
        return declaracion.validarLimitesDeducibles();
    }

    

    // Calcula el saldo a pagar o a favor en la declaración
    public double calcularDeclaracion() {
        if (declaracion == null) {
            System.err.println("No hay una declaración activa. Registre un contribuyente primero.");
            return 0.0;
        }

        // Realizar el cálculo completo de la declaración
        return declaracion.calcularSaldoAPagar();
    }

    // Devuelve los límites deducibles por categoría (para mostrar al usuario)
    public Map<CategoriaGasto, Double> obtenerLimitesDeducibles() {
        if (declaracion == null) {
            System.err.println("No hay una declaración activa. Registre un contribuyente primero.");
            return null;
        }

        return declaracion.getLimitesDeduciblesPorCategoria();
    }

    // Devuelve el total de gastos por cada categoría
    public Map<CategoriaGasto, Double> obtenerGastosPorCategoria() {
        if (declaracion == null) {
            System.err.println("No hay una declaración activa. Registre un contribuyente primero.");
            return null;
        }

        return declaracion.getTotalGastosPorCategoria();
    }

    // Devuelve un resumen de la declaración (ingresos, gastos, impuesto, etc.)
    public String[] obtenerResumenDeclaracion() {
        if (declaracion == null) {
            System.err.println("No hay una declaración activa. Registre un contribuyente primero.");
            return new String[]{"No hay declaración activa."};
        }

        return declaracion.getDatosResumen();
    }

    // Guarda los datos del contribuyente y la declaración en un archivo
    public boolean guardarDatos(String nombreArchivo) {
        if (contribuyente == null || declaracion == null) {
            System.err.println("No hay datos para guardar.");
            return false;
        }

        try (FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            // Crear un objeto que contenga tanto el contribuyente como la declaración
            Object[] datos = {contribuyente, declaracion};
            objectOut.writeObject(datos);

            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            return false;
        }
    }

    // Carga los datos del contribuyente y la declaración desde un archivo
    public boolean cargarDatos(String nombreArchivo) {
        try (FileInputStream fileIn = new FileInputStream(nombreArchivo);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            // Cargar el array de objetos
            Object[] datos = (Object[]) objectIn.readObject();

            // Extraer contribuyente y declaración
            this.contribuyente = (Contribuyente) datos[0];
            this.declaracion = (Declaracion) datos[1];

            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            return false;
        }
    }

    // Métodos para obtener los objetos principales (para la vista)
    public Contribuyente getContribuyente() {
        return contribuyente;
    }

    public Declaracion getDeclaracion() {
        return declaracion;
    }
}