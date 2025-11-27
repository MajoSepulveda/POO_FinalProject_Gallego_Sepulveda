# POO_FinalProject_Gallego_Sepulveda
Proyecto Final Programación Orientada a Objetos

# Sistema de Gestión de Tienda de Videojuegos (Store Manager)

## 👤 Integrantes
* **[Tu Nombre Completo]** - [Tu Código]
* **[Nombre del Integrante 2 (si aplica)]** - [Código del Integrante 2]

---

## Descripción General del Sistema

### Objetivo y Alcance
El objetivo principal del proyecto es **simular un sistema de administración** para una tienda de videojuegos. Permite al usuario gestionar todas las operaciones esenciales de la tienda a través de una interfaz de consola interactiva.

El alcance del sistema está definido por las siguientes entidades clave, contenidas dentro del objeto principal **`Store`**:
* **`VideoGame`**: Gestión de inventario (título, género, precio, stock).
* **`Customer`**: Gestión de la base de clientes (ID, nombre, saldo).
* **`Sale`**: Registro de transacciones (ID, videojuego, cliente, monto, fecha).

### Funcionamiento
El programa funciona como una **aplicación de consola basada en menús**. Al iniciarse, intenta cargar el estado previo de la tienda desde un archivo serializado (`.ser`). Si este archivo no existe, la tienda se inicializa cargando datos desde los archivos `.csv` de la raíz.

El usuario interactúa seleccionando opciones numéricas para navegar entre los menús de gestión y realizar acciones como:
* Crear, modificar y eliminar videojuegos y clientes.
* Concretar nuevas ventas, ajustando automáticamente el *stock* y registrando la transacción.
* Generar reportes de ingresos de ventas dentro de un periodo de fechas específico.

### Persistencia de Datos
El estado de la aplicación se guarda mediante **serialización de objetos Java**. El objeto `Store` (que encapsula todas las listas de datos) se guarda en un archivo binario (`store.ser`). En caso de fallo de guardado, se intenta un respaldo (`store.backup`). Los archivos `.csv` se usan **solo** para la carga inicial de datos si no hay un estado guardado previamente.

---

## Requisitos e Instrucciones de Ejecución

### Requisitos del Sistema
El proyecto fue desarrollado y compilado con **Java Development Kit (JDK) versión 17 o superior**. No utiliza librerías externas o dependencias de terceros; solo requiere el entorno de ejecución estándar de Java.

### Estructura de Archivos
Asegúrese de que los archivos de datos iniciales (`VideogamesList.csv`, `CustomersList.csv`, `SalesList.csv`) se encuentren en la raíz del proyecto para la carga inicial.

### Ejecución del Programa
Para compilar y ejecutar el programa desde la línea de comandos, asumiendo que el archivo principal que contiene el método `main` se llama **`Main.java`**:

1.  **Compilar el proyecto:**
    ```bash
    javac -cp . src/**/*.java
    ```

2.  **Ejecutar la clase principal:**
    ```bash
    java Main
    ```
    (Si tu clase principal está dentro de un paquete, usa el nombre completo, ej: `java com.tienda.Main`)

---

## Ejemplo de Entrada/Salida

A continuación, se muestra un ejemplo de la interacción del usuario para agregar un nuevo cliente y generar un reporte:

// --- Inicio de la Aplicación --- Cargando datos desde store.ser... Éxito.

Gestión de Videojuegos

Gestión de Clientes

Gestión de Ventas

Generar Reporte de Ingresos

Salir

Seleccione una opción: 2 // --- Menú de Clientes ---

Agregar Cliente

Modificar Cliente ... Seleccione una opción: 1 ID del cliente: 1000000055 Nombre del cliente: Juan López Saldo inicial: 50.00 Cliente agregado correctamente.

// --- Volver al Menú Principal --- Seleccione una opción: 4 Fecha inicio (dd-MM-yyyy): 01-11-2025 Fecha fin (dd-MM-yyyy): 30-11-2025
REPORTE DE INGRESOS
======================================== Periodo: 01-11-2025 al 30-11-2025 Ventas registradas en el periodo: 9 Ingreso Total: $419.89

---

## 📄 Diagrama de Clases (Modelo de Datos)

El modelo de datos del sistema está representado en el siguiente diagrama de clases, que ilustra las relaciones y atributos de las entidades principales.

[Enlace al Diagrama de Clases (ModelDiagram.gif)](ModelDiagram.gif)
