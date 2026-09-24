Trabajo Práctico N° 2 - Programación Orientada a Objetos en Java
--------------------------------------------------

- Asignatura: Paradigmas de Programación
- Institución: Universidad Tecnológica Nacional – Facultad Regional Mendoza (UTN-FRM)
- Lenguaje: Java 17+
- Entorno de Desarrollo: IntelliJ IDEA

*Descripción del Proyecto*
--------------------------------------------------

Este proyecto consiste en el desarrollo e implementación del Sistema de Gestión de Eventos Universitarios, escalando su arquitectura mediante conceptos avanzados de Orientación a Objetos en Java.
El objetivo principal es responder de forma sólida a las necesidades del dominio, aplicando:
Modularización y Encapsulamiento mediante la estructuración clara en paquetes (packages).
Tolerancia a Fallos mediante el manejo robusto y granular de Excepciones personalizadas y chequeadas.
Persistencia de Datos mediante Serialización y Deserialización de objetos.
Abstracción y Polimorfismo mediante Interfaces para actividades certificables.
Tipado Avanzado y Reutilización mediante Métodos Genéricos Acotados y Comodines (Wildcards).
 
 *Estructura del Proyecto y Paquetes*
 --------------------------------------------------
 
El código fuente está organizado en los siguientes paquetes según el diagrama de clases:

src/

├── actividades/

│   ├── Actividad.java (Clase abstracta base para actividades)

│   ├── Charla.java             (Subclase de Actividad)

│   ├── Taller.java             (Subclase de Actividad, implementa Certificable)

│   └── Curso.java              (Subclase de Actividad, implementa Certificable)

├── certificacion/

│   └── Certificable.java       (Interfaz para actividades que emiten certificado)

├── excepciones/

│   └── CupoExcedidoException.java (Excepción personalizada chequeada)

├── modelo/

│   ├── Sala.java               (Representa la sala asignada al evento)

│   ├── Estudiante.java         (Representa a los alumnos)

│   ├── Inscripcion.java        (Registra la vinculación estudiante-actividad)

│   └── EventoUniversitario.java (Clase principal del dominio y gestor de persistencia)

└── App.java                    (Clase ejecutable principal)




 *Funcionalidades e Hitos Desarrollados*
 --------------------------------------------------
 
- Ejercicio 1: Encapsulamiento, Excepciones y Persistencia
Excepción Personalizada: Implementación de CupoExcedidoException lanzada al intentar inscribir estudiantes más allá del cupoMaximo definido para una actividad.
Persistencia por Serialización: Implementación de los métodos persistirEvento() y recuperarEvento() en EventoUniversitario utilizando ObjectOutputStream y ObjectInputStream.
Manejo Granular de Excepciones: Flujo try-catch-finally en App.java capturando excepciones específicas como FileNotFoundException, IOException y ClassNotFoundException.
- Ejercicio 2: Polimorfismo e Interfaces (Certificable)
Incorporación del tipo Curso: Nueva subclase de Actividad.
Interfaz Certificable: Define la constante ENTIDAD_EMISORA y el método generarCertificado(Estudiante estudiante).
Lógica de Emisión: Implementada en Taller y Curso. La clase Charla no implementa la interfaz ya que no otorga certificados.
- Ejercicio 3: Métodos Genéricos y Wildcards
Filtrado Tipado: Implementación del método genérico acotado en EventoUniversitario:
public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo)
Permite obtener listas fuertemente tipadas como List<Charla>, List<Taller> o List<Curso>.
Cálculo de Costos con Wildcards: Uso de comodines de tipo (upper bounded wildcards):
public double calcularCostoMateriales(List<? extends Actividad> actividades)
Permite procesar listas de Actividad o de cualquiera de sus subclases de forma polimórfica.
 
 *Instrucciones de Ejecución*
 --------------------------------------------------
 

1. Clonar el repositorio:
git clone https://github.com/milulira/PP_TP2_53406.git

2. Abrir en IntelliJ IDEA:
Abrir IntelliJ IDEA y seleccionar Open.
3. Navegar hasta la carpeta del proyecto clonado y seleccionar el archivo pom.xml o la carpeta raíz del proyecto.
4. Compilar y Ejecutar:
Navegar hasta la clase App.java (src/App.java).
5. Ejecutar la clase haciendo clic en Run 'App.main()'.
   
 Salida por Consola (Ejemplo)
 --------------------------------------------------
    
    ==================================================
     SISTEMA DE GESTIÓN DE EVENTOS UNIVERSITARIOS 
    ================================================== 
    --- [EJERCICIO 1] Inscripciones y Manejo de Excepciones ---
    Intentando inscribir a Ana Gómez en Taller Git... 
    -> Inscripción exitosa.
    Intentando inscribir a Carlos Pérez en Taller Git (Cupo máximo: 1)...
    -> EXCEPCIÓN CAPTURADA: ¡Cupo agotado! No es posible inscribir a Carlos Pérez en la actividad 'Taller Práctico de Git y GitHub' (Cupo Máximo: 1).
    -> Bloque 'finally' ejecutado tras proceso de inscripción.
    
    ----[EJERCICIO 1] Persistencia y Deserialización de Objetos ---
    Persistiendo evento 'Jornadas de Tecnología UTN 2026'...
    -> Evento guardado correctamente en archivo.
    Recuperando evento persistido desde archivo...
    -> Evento recuperado con éxito: Jornadas de Tecnología UTN 2026
    -> Flujo de persistencia completado.
    
    --- [EJERCICIO 2] Emisión de Certificados (Solo Talleres y Cursos) ---
    Nota: La actividad 'Inteligencia Artificial Aplicada' (Charla) NO es certificable.
    Emitiendo certificados para actividad certificable: 'Taller Práctico de Git y GitHub'
   
    --------------------------------------------------
    CERTIFICADO DE ASISTENCIA | UTN - Facultad Regional Mendoza
    Se otorga el presente certificado a: Ana Gómez (Legajo: E101)
    Por su participación en el Taller: 'Taller Práctico de Git y GitHub'.
    --------------------------------------------------
   
    Emitiendo certificados para actividad certificable: 'Curso Avanzado de Java POO'
    
    --------------------------------------------------
    CERTIFICADO DE APROBACIÓN | UTN - Facultad Regional Mendoza
    Se otorga el presente certificado a: Carlos Pérez (Legajo: E102)
    Por completar satisfactoriamente el Curso: 'Curso Avanzado de Java POO' (20 hs lectivas).
    --------------------------------------------------
    
    --- [EJERCICIO 3] Generics, Filtrado por Tipo y Wildcards ---
    Cantidad de Charlas:  1
    Cantidad de Talleres: 1
    Cantidad de Cursos:   1
    
    Costo total de materiales (Charlas):  $1500.0
    Costo total de materiales (Talleres): $4500.0
    Costo total de materiales (Cursos):   $16000.0
    Costo total de materiales (Todas):    $22000.0
