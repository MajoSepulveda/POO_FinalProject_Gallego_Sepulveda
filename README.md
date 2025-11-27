# Sistema de Gestión de Tienda de Videojuegos (Store Manager)
## Proyecto Final Programación Orientada a Objetos

## Integrantes
* **Jerónimo Gallego Restrepo** - 60880
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
El proyecto fue desarrollado y compilado con **Java Development Kit (JDK) versión 17 o superior**.

### Estructura de Archivos
Asegúrese de que los archivos de datos iniciales (`VideogamesList.csv`, `CustomersList.csv`, `SalesList.csv`) se encuentren en la carpeta **`Data`** del proyecto para la carga inicial.

---

##     Ejecución del Programa (Método Recomendado)

Para una puesta en marcha rápida y sin problemas de *shell* o compilación manual, la forma más sencilla de ejecutar el proyecto es a través de un Entorno de Desarrollo Integrado (**IDE**).

### 1. Ubicación del Archivo Principal
El archivo de inicio que contiene el método **`main`** se encuentra en la ruta:

### 2. Pasos para Ejecutar en IDE

1.  **Abrir Proyecto:** Use la opción **"Open"** o **"Import Project"** de su IDE (IntelliJ IDEA, Eclipse, VS Code) y seleccione la carpeta principal de este repositorio.
2.  **Ejecutar Main:** Localice el archivo `Main.java` ubicado en la carpeta `ui`y haga clic derecho sobre él.
3.  Seleccione la opción **"Run 'Main.main()'"** o su equivalente.

El IDE se encargará de la compilación y de configurar el *classpath* automáticamente.

---

## Ejemplo de Entrada/Salida

A continuación, se muestra el flujo de interacción del usuario para agregar un nuevo cliente y salir de la aplicación:

```
Datos de la tienda cargados con éxito desde src/data/store_data.ser
Iniciando programa...

BIENVENIDO/A A LA TIENDA DE JUEGOS
========================================
1) Entrar a la tienda.
2) Salir.

Seleccione una opción: 1

----------- TIENDA -----------
1) Mostrar catálogo completo.
2) Buscar juego.
3) Administrar tienda.
4) Volver al menú principal.

Seleccione una opción: 3

--- ADMINISTRACIÓN DE LA TIENDA ---
1) Gestionar clientes.
2) Gestionar videojuegos.
3) Gestionar ventas.
4) Generar reporte.
5) Volver al menú principal.

Seleccione una opción: 1

----------- GESTIÓN DE CLIENTES  -----------
1) Agregar cliente.
2) Eliminar cliente.
3) Modificar cliente.
4) Lista de clientes.
5) Volver.

Seleccione una opción: 1

Nombre del cliente: Maria
ID: 5374820592
Saldo inicial: 2000
Cliente agregado: ID = 5374820592 Nombre = Maria

----------- GESTIÓN DE CLIENTES  -----------
1) Agregar cliente.
2) Eliminar cliente.
3) Modificar cliente.
4) Lista de clientes.
5) Volver.

Seleccione una opción: 5

Volviendo...

--- ADMINISTRACIÓN DE LA TIENDA ---
1) Gestionar clientes.
2) Gestionar videojuegos.
3) Gestionar ventas.
4) Generar reporte.
5) Volver al menú principal.

Seleccione una opción: 5

Volviendo...

----------- TIENDA -----------
1) Mostrar catálogo completo.
2) Buscar juego.
3) Administrar tienda.
4) Volver al menú principal.

Seleccione una opción: 4

Volviendo al menú principal...

========================================
BIENVENIDO/A A LA TIENDA DE JUEGOS
========================================
1) Entrar a la tienda.
2) Salir.

Seleccione una opción: 2

Gracias por visitar la tienda. Saliendo...
Guardando cambios de la sesión...

Datos de la tienda guardados con éxito en src/data/store_data.ser
```
---


## 📄 Diagrama de Clases (Modelo de Datos)

El modelo de datos del sistema está representado en el siguiente diagrama:

[Diagrama de Clases UML](ModelDiagram.png)
