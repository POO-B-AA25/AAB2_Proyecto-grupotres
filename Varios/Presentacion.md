# Sistema de declaración de impuestos anuales
Este proyecto implementamo un **Sistema de Declaración de Impuestos a la Renta** para personas naturales, siguiendo la normativa fiscal ecuatoriana y la tabla oficial de impuestos del año 2023. El sistema permite al usuario registrar sus sueldos mensuales y las facturas generadas en categorías como Vivienda, Educación, Alimentación, Vestimenta, Salud y Turismo, calculando automáticamente las deducciones permitidas y el impuesto anual a pagar o a favor.

#### ¿Cómo se cumplieron los requisitos?

- **Registro de información:**  
  El modelado de clases permite ingresar sueldos mensuales y facturas por categoría. Esto se gestiona en [`Declaracion.java`](../Solucion_Codigo/src/Modelo/Declaracion.java) y [`Factura.java`](../Solucion_Codigo/src/Modelo/Factura.java) 

- **Cálculo de impuestos:**  
  El sistema consulta la tabla oficial de impuestos a la renta 2023, implementada en [`TablaImpuestoRenta.java`](../Solucion_Codigo/src/Modelo/TablaImpuestoRenta.java)  y realiza el cálculo anual considerando deducciones y sueldos.

- **Generación de declaración:**  
  Se genera una declaración detallada con el cálculo de impuestos, deducciones y saldo a pagar o a favor, en [`Declaracion.java`](../Solucion_Codigo/src/Modelo/Declaracion.java) 

- **Validación de datos:**  
  Las validaciones de cédula, email, número de factura y límites de deducción están en [`ValidadorDatos.java`](../Solucion_Codigo/src/Modelo/ValidadorDatos.java) 

---

### Polimorfismo en el Proyecto

El eje principal del sistema es el **polimorfismo**, que permite tratar diferentes tipos de gastos deducibles de forma uniforme y extensible:

- **Interfaz y clase abstracta:**  
  La interfaz [`GastoDeducible.java`](../Solucion_Codigo/src/Modelo/GastoDeducible.java) y la clase abstracta [`GastoDeducibleBase.java`](../Solucion_Codigo/src/Modelo/GastoDeducibleBase.java) definen la estructura común para cualquier gasto deducible.

- **Colección polimórfica:**  
  En [`Declaracion.java`](../Solucion_Codigo/src/Modelo/Declaracion.java)  la lista `gastosDeducibles` almacena cualquier objeto que implemente la interfaz, permitiendo agregar nuevos tipos de gastos sin modificar la lógica principal.

- **Controlador polimórfico:**  
  El método `agregarGastoDeducible(GastoDeducible gasto)` en [`SistemaController.java`](../Solucion_Codigo/src/Controlador/SistemaController.java) acepta cualquier tipo de gasto deducible, no solo facturas.

- **Métodos polimórficos:**  
  Los métodos de cálculo y validación en [`Declaracion.java`](../Solucion_Codigo/src/Modelo/Declaracion.java) operan sobre la interfaz, haciendo el sistema flexible y abierto a extensión.

Este diseño permite agregar nuevas clases de gastos deducibles (por ejemplo, Donación, AporteVoluntario) sin modificar el controlador ni la declaración, cumpliendo el principio de **abierto/cerrado** (OCP).

---

### Principios SOLID Aplicados

- **S (Responsabilidad Única):**  
  Cada clase tiene una única responsabilidad, por ejemplo, [`Declaracion.java`](../Solucion_Codigo/src/Modelo/Declaracion.java) solo gestiona la lógica de la declaración.

- **O (Abierto/Cerrado):**  
  El sistema es abierto para extensión y cerrado para modificación, gracias al uso de la interfaz [`GastoDeducible.java`](../Solucion_Codigo/src/Modelo/GastoDeducible.java) y colecciones polimórficas.

- **L (Sustitución de Liskov):**  
  Cualquier clase que implemente la interfaz puede ser usada en la declaración sin romper la lógica, como [`Factura.java`](../Solucion_Codigo/src/Modelo/Factura.java) 

- **I (Segregación de Interfaces):**  
  La interfaz [`GastoDeducible.java`](../Solucion_Codigo/src/Modelo/GastoDeducible.java) es específica y no obliga a implementar métodos innecesarios.

- **D (Inversión de Dependencias):**  
  Las clases principales dependen de abstracciones, no de implementaciones concretas, como en [`Declaracion.java`](../Solucion_Codigo/src/Modelo/Declaracion.java) 

---

**En resumen:**  
El proyecto cumple con los requisitos funcionales y de diseño, usando polimorfismo y los principios SOLID para lograr un sistema extensible, mantenible y flexible. Puedes agregar nuevos tipos de gastos deducibles sin modificar la lógica principal, demostrando una correcta aplicación de la programación