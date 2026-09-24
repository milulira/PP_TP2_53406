package modelo;
import excepciones.CupoExcedidoException;
import modelo.actividades.Actividad;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventoUniversitario implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos = 0;

    private Sala sala;
    private List<Actividad> actividades;


    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    // Constructor de copia
    public EventoUniversitario(EventoUniversitario otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        this.sala = otro.sala;
        this.actividades = new ArrayList<>(otro.actividades);
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public double getCostoBase() { return costoBase; }
    public boolean isGratuito() { return gratuito; }
    public Sala getSala() { return sala; }
    public List<Actividad> getActividades() { return actividades; }

    public static int getCantidadEventos() { return cantidadEventos; }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void agregarActividad(Actividad actividad) {
        this.actividades.add(actividad);
    }

    public double calcularCostoEstimado() {
        double costoTotal = gratuito ? 0.0 : costoBase;
        for (Actividad act : actividades) {
            costoTotal += act.calcularCostoMateriales();
        }
        return costoTotal;
    }


    //Método Parametrizado Acotado
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Actividad act : actividades) {
            if (tipo.isInstance(act)) {
                resultado.add(tipo.cast(act));
            }
        }
        return resultado;
    }

    // Wildcards
    public double calcularCostoMateriales(List<? extends Actividad> listaActividades) {
        double total = 0.0;
        for (Actividad act : listaActividades) {
            total += act.calcularCostoMateriales();
        }
        return total;
    }

    public void mostrarDatos() {
        System.out.println("==================================================");
        System.out.println(" EVENTO: " + titulo + " [ID: " + id + "]");
        System.out.println(" Costo Base: $" + costoBase + " | Es Gratuito: " + (gratuito ? "Sí" : "No"));
        System.out.println(" Sala Asignada: " + (sala != null ? sala.getNombre() : "Sin Sala"));
        System.out.println(" Cantidad de Actividades: " + actividades.size());
        System.out.println(" Costo Estimado Total: $" + calcularCostoEstimado());
        System.out.println("--------------------------------------------------");
        System.out.println(" Detalle de Actividades:");
        for (Actividad act : actividades) {
            act.mostrarIdentificacion();
            act.mostrarInscripciones();
        }
        System.out.println("==================================================");
    }

    // Persistencia (Serialización)
    public boolean persistirEvento() throws IOException {
        String filename = "evento_" + id + ".ser";
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            return true;
        }
    }

    public static EventoUniversitario recuperarEvento(String id) throws IOException, ClassNotFoundException {
        String filename = "evento_" + id + ".ser";
        try (FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (EventoUniversitario) ois.readObject();
        }
    }
}