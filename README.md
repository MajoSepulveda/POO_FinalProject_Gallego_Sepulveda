# Sistema de Gestión de Tienda de Videojuegos (Store Manager)
## Proyecto Final Programación Orientada a Objetos

## Integrantes
* **Jeróniom Gallego Restrepo** - 60880
* **Maria José Sepúlveda Montes** - 59886

---

## Descripción General del Sistema

### Objetivo y Alcance
El objetivo principal del proyecto es **simular un sistema de administración** para una tienda de videojuegos, permitiendo gestionar todas las operaciones esenciales a través de una interfaz de consola interactiva.

El alcance del sistema está definido por las siguientes entidades clave, contenidas dentro del objeto principal **`Store`**:
* **`VideoGame`**: Gestión de inventario (ID, título, género, precio, stock).
* **`Customer`**: Gestión de la base de clientes (ID, nombre, saldo).
* **`Sale`**: Registro de transacciones (ID, videojuego, cliente, monto, fecha).

### Funcionamiento
El programa funciona como una **aplicación de consola basada en menús**. Al iniciarse, intenta cargar el estado previo de la tienda desde un archivo serializado (`.ser`). Si este archivo no existe, la tienda se inicializa cargando datos desde los archivos `.csv` (que actúan como listas iniciales).

El usuario interactúa seleccionando opciones numéricas para navegar entre los menús y realizar acciones como crear, modificar y eliminar entidades, concretar ventas y generar reportes de ingresos.

### Persistencia de Datos
El estado de la aplicación se guarda mediante **serialización de objetos Java**. El objeto `Store` se guarda en un archivo binario (`store_data.ser`). En caso de fallo de guardado, se intenta un respaldo en (`store_data_backup.ser`). Los archivos `.csv` se usan **solo** para la carga inicial de datos.

---

## Requisitos e Instrucciones de Ejecución

### Requisitos del Sistema
El proyecto fue desarrollado y compilado con **Java Development Kit (JDK) versión 17 o superior**. No utiliza librerías externas.

### Estructura de Archivos
Asegúrese de que los archivos de datos iniciales (`VideogamesList.csv`, `CustomersList.csv`, `SalesList.csv`) se encuentren en la carpeta **`Data`** del proyecto para la carga inicial.

### Ejecución del Programa
Para compilar y ejecutar el programa desde la línea de comandos:

1.  **Compilar el proyecto:**
    ```bash
    javac -cp . src/**/*.java
    ```

2.  **Ejecutar la clase principal:**
    ```bash
    java com.ui.Main
    ```

---

## Ejemplo de Entrada/Salida

A continuación, se muestra el flujo de interacción del usuario para agregar un nuevo cliente y salir de la aplicación:

```
Datos de la tienda cargados con éxito desde src/data/store_data.ser

Iniciando programa...
BIENVENIDO/A A LA TIENDA DE JUEGOS
========================================

Entrar a la tienda.

Salir.

Seleccione una opción: 1 ----------- TIENDA -----------

Mostrar catálogo completo.

Buscar juego.

Administrar tienda.

Volver al menú principal.

Seleccione una opción: 3 --- ADMINISTRACIÓN DE LA TIENDA ---

Gestionar clientes.

Gestionar videojuegos.

Gestionar ventas.

Generar reporte.

Volver al menú principal.

Seleccione una opción: 1 ----------- GESTIÓN DE CLIENTES  -----------

Agregar cliente.

Eliminar cliente.

Modificar cliente.

Lista de clientes.

Volver.

Seleccione una opción: 1 Nombre del cliente: Maria  ID: 5374820592 Saldo inicial: 2000 Cliente agregado: ID = 5374820592 Nombre = Maria ----------- GESTIÓN DE CLIENTES  -----------

Agregar cliente.

Eliminar cliente.

Modificar cliente.

Lista de clientes.

Volver.

Seleccione una opción: 5

Volviendo... --- ADMINISTRACIÓN DE LA TIENDA ---

Gestionar clientes.

Gestionar videojuegos.

Gestionar ventas.

Generar reporte.

Volver al menú principal.

Seleccione una opción: 5

Volviendo... ----------- TIENDA -----------

Mostrar catálogo completo.

Buscar juego.

Administrar tienda.

Volver al menú principal.

Seleccione una opción: 4

Volviendo al menú principal...
BIENVENIDO/A A LA TIENDA DE JUEGOS
========================================

Entrar a la tienda.

Salir.

Seleccione una opción: 2

Gracias por visitar la tienda. Saliendo... Guardando cambios de la sesión...

Datos de la tienda guardados con éxito en src/data/store_data.ser
```
---


## Diagrama de Clases (Modelo de Datos)

El modelo de datos del sistema está representado en el siguiente diagrama de clases, que ilustra las relaciones y atributos de las entidades principales.

[Enlace al Diagrama de Clases (ModelDiagram.gif)](ModelDiagram.gif)
