import modelo.actividades.*;
import modelo.certificacion.Certificable;
import excepciones.CupoExcedidoException;
import modelo.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE EVENTOS UNIVERSITARIOS   ");
        System.out.println("==================================================\n");

        // -------------------------------------------------------------
        // A. Creación de Estudiantes
        // -------------------------------------------------------------
        Estudiante est1 = new Estudiante("E101", "Ana Gómez");
        Estudiante est2 = new Estudiante("E102", "Carlos Pérez");
        Estudiante est3 = new Estudiante("E103", "Lucía Fernández");

        // -------------------------------------------------------------
        // B. Creación de Evento, Sala y Actividades (Charlas, Talleres, Cursos)
        // -------------------------------------------------------------
        EventoUniversitario evento = new EventoUniversitario("EVT-01", "Jornadas de Tecnología UTN 2026", 15000.0, false);
        Sala salaMagna = new Sala(1, "Aula Magna - Bloque A");
        evento.asignarSala(salaMagna);

        // Actividades con cupos reducidos para testear control de cupo
        Charla charlaIA = new Charla(1, "Inteligencia Artificial Aplicada", 2);
        Taller tallerGit = new Taller(2, "Taller Práctico de Git y GitHub", 1, true);
        Curso cursoJava = new Curso(3, "Curso Avanzado de Java POO", 2, 20);

        evento.agregarActividad(charlaIA);
        evento.agregarActividad(tallerGit);
        evento.agregarActividad(cursoJava);

        // -------------------------------------------------------------
        // EJERCICIO 1: Manejo de Excepciones de Cupo y Try-Catch-Finally
        // -------------------------------------------------------------
        System.out.println("--- [EJERCICIO 1] Inscripciones y Manejo de Excepciones ---");

        // Caso Exitoso
        try {
            System.out.println("Intentando inscribir a Ana Gómez en Taller Git...");
            tallerGit.inscribir(est1);
            System.out.println("-> Inscripción exitosa.");
        } catch (CupoExcedidoException e) {
            System.err.println("-> ERROR: " + e.getMessage());
        }

        // Caso Fallido (Cupo Excedido)
        try {
            System.out.println("Intentando inscribir a Carlos Pérez en Taller Git (Cupo máximo: 1)...");
            tallerGit.inscribir(est2); // Debe lanzar CupoExcedidoException
        } catch (CupoExcedidoException e) {
            System.out.println("-> EXCEPCIÓN CAPTURADA: " + e.getMessage());
        } finally {
            System.out.println("-> Bloque 'finally' ejecutado tras proceso de inscripción.\n");
        }

        // Inscribimos en otras actividades para completar el escenario
        try {
            charlaIA.inscribir(est1);
            charlaIA.inscribir(est2);
            cursoJava.inscribir(est2);
            cursoJava.inscribir(est3);
        } catch (CupoExcedidoException e) {
            System.out.println("-> " + e.getMessage());
        }

        // -------------------------------------------------------------
        // EJERCICIO 1: Persistencia (Serialización/Deserialización Granular)
        // -------------------------------------------------------------
        System.out.println("--- [EJERCICIO 1] Persistencia y Deserialización de Objetos ---");
        try {
            System.out.println("Persistiendo evento '" + evento.getTitulo() + "'...");
            evento.persistirEvento();
            System.out.println("-> Evento guardado correctamente en archivo.");

            System.out.println("Recuperando evento persistido desde archivo...");
            EventoUniversitario eventoRecuperado = EventoUniversitario.recuperarEvento("EVT-01");
            System.out.println("-> Evento recuperado con éxito: " + eventoRecuperado.getTitulo());
        } catch (FileNotFoundException e) {
            System.err.println("-> ERROR: No se encontró el archivo de persistencia: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("-> ERROR de Entrada/Salida en persistencia: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("-> ERROR: Clase no encontrada al deserializar objeto: " + e.getMessage());
        } finally {
            System.out.println("-> Flujo de persistencia completado.\n");
        }

        // -------------------------------------------------------------
        // EJERCICIO 2: Emisión de Certificados e Interfaces (Certificable)
        // -------------------------------------------------------------
        System.out.println("--- [EJERCICIO 2] Emisión de Certificados (Solo Talleres y Cursos) ---");
        for (Actividad act : evento.getActividades()) {
            if (act instanceof Certificable) {
                Certificable certificable = (Certificable) act;
                System.out.println("\nEmitiendo certificados para actividad certificable: '" + act.getTitulo() + "'");
                for (Inscripcion ins : act.getInscripciones()) {
                    String certificado = certificable.generarCertificado(ins.getEstudiante());
                    System.out.println("--------------------------------------------------");
                    System.out.println(certificado);
                    System.out.println("--------------------------------------------------");
                }
            } else {
                System.out.println("\nNota: La actividad '" + act.getTitulo() + "' (" + act.getTipo() + ") NO es certificable.");
            }
        }
        System.out.println();

        // -------------------------------------------------------------
        // EJERCICIO 3: Métodos Genericos Acotados y Wildcards
        // -------------------------------------------------------------
        System.out.println("--- [EJERCICIO 3] Generics, Filtrado por Tipo y Wildcards ---");

        // A. Listas fuertemente tipadas utilizando el método genérico
        List<Charla> listaCharlas = evento.filtrarActividadesPorTipo(Charla.class);
        List<Taller> listaTalleres = evento.filtrarActividadesPorTipo(Taller.class);
        List<Curso> listaCursos = evento.filtrarActividadesPorTipo(Curso.class);

        // B. Cantidad de actividades de cada tipo
        System.out.println("Cantidad de Charlas:  " + listaCharlas.size());
        System.out.println("Cantidad de Talleres: " + listaTalleres.size());
        System.out.println("Cantidad de Cursos:   " + listaCursos.size());
        System.out.println();

        // C. Cálculo de costo de materiales por tipo utilizando wildcard (List<? extends Actividad>)
        double costoMaterialesCharlas  = evento.calcularCostoMateriales(listaCharlas);
        double costoMaterialesTalleres = evento.calcularCostoMateriales(listaTalleres);
        double costoMaterialesCursos   = evento.calcularCostoMateriales(listaCursos);
        double costoMaterialesTotales  = evento.calcularCostoMateriales(evento.getActividades());

        System.out.println("Costo total de materiales (Charlas):  $" + costoMaterialesCharlas);
        System.out.println("Costo total de materiales (Talleres): $" + costoMaterialesTalleres);
        System.out.println("Costo total de materiales (Cursos):   $" + costoMaterialesCursos);
        System.out.println("Costo total de materiales (Todas):    $" + costoMaterialesTotales);
        System.out.println();

        // -------------------------------------------------------------
        // Mostrar datos completos del evento
        // -------------------------------------------------------------
        evento.mostrarDatos();
    }
}