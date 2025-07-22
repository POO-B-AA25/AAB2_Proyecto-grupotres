package Modelo; // Este archivo está en el paquete Modelo

import java.io.Serial;
import java.io.Serializable; // Permite guardar/cargar objetos de esta clase

// Clase que representa a la persona que declara impuestos
public class Contribuyente implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; //define un ID único para serialización

    // Atributos del contribuyente

    private String nombre;      // Nombre completo
    private String cedula;      // Cédula de identidad
    private String direccion;   // Dirección de domicilio
    private String telefono;    // Teléfono de contacto
    private String email;       // Correo electrónico
    private String ocupacion;   // Ocupación o profesión

    // Constructor vacío (por defecto)
    public Contribuyente() {
        this.nombre = "";
        this.cedula = "";
        this.direccion = "";
        this.telefono = "";
        this.email = "";
        this.ocupacion = "";
    }

    // Constructor con todos los datos del contribuyente
    public Contribuyente(String nombre, String cedula, String direccion, String telefono, String email, String ocupacion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.ocupacion = ocupacion;
    }

    // Métodos para obtener y modificar los datos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    // Devuelve los datos en un arreglo de Strings
    public String[] getDatos() {
        return new String[]{nombre, cedula, direccion, telefono, email, ocupacion};
    }

    // Permite modificar todos los datos de una sola vez
    public void setDatos(String nombre, String cedula, String direccion, String telefono, String email, String ocupacion) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.ocupacion = ocupacion;
    }

    // Representación en texto del contribuyente (útil para depuración)
    @Override
    public String toString() {
        return "Contribuyente{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", ocupacion='" + ocupacion + '\'' +
                '}';
    }
}