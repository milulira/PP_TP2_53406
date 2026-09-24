package modelo.actividades;

import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

    public abstract class Actividad implements Serializable {
        private static final long serialVersionUID = 1L;

        protected int id;
        protected String titulo;
        protected int cupoMaximo;
        public static final int CUPO_MINIMO = 5;
        protected List<Inscripcion> inscripciones;

        public Actividad(int id, String titulo, int cupoMaximo) {
            this.id = id;
            this.titulo = titulo;
            this.cupoMaximo = cupoMaximo;
            this.inscripciones = new ArrayList<>();
        }

        public int getId() { return id; }
        public String getTitulo() { return titulo; }
        public int getCupoMaximo() { return cupoMaximo; }
        public List<Inscripcion> getInscripciones() { return Collections.unmodifiableList(inscripciones); }

        public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
            if (inscripciones.size() >= cupoMaximo) {
                throw new CupoExcedidoException(
                        "¡Cupo agotado! No es posible inscribir a " + estudiante.getNombre() +
                                " en la actividad '" + titulo + "' (Cupo Máximo: " + cupoMaximo + ")."
                );
            }
            Inscripcion inscripcion = new Inscripcion(LocalDate.now(), "CONFIRMADA", estudiante);
            inscripciones.add(inscripcion);
            return inscripcion;
        }

        public void mostrarInscripciones() {
            System.out.println("   Inscriptos en '" + titulo + "' (" + inscripciones.size() + "/" + cupoMaximo + "):");
            if (inscripciones.isEmpty()) {
                System.out.println("     - Sin inscripciones registradas.");
            } else {
                for (Inscripcion ins : inscripciones) {
                    System.out.println("     - " + ins.getEstudiante().getNombre() + " (Legajo: " + ins.getEstudiante().getLegajo() + ") - Estado: " + ins.getEstado());
                }
            }
        }

        public final void mostrarIdentificacion() {
            System.out.println("   [ID: " + id + "] " + getTipo() + " - " + titulo + " | Cupo Máximo: " + cupoMaximo);
        }

        public abstract double calcularCostoMateriales();
        public abstract String getTipo();
    }