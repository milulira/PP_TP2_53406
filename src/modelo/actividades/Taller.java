package modelo.actividades;

import modelo.certificacion.Certificable;
import modelo.Estudiante;


public class Taller extends Actividad implements Certificable {
        private static final long serialVersionUID = 1L;

        private boolean requiereNotebook;

        public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook) {
            super(id, titulo, cupoMaximo);
            this.requiereNotebook = requiereNotebook;
        }

        public boolean isRequiereNotebook() { return requiereNotebook; }

        @Override
        public double calcularCostoMateriales() {
            return requiereNotebook ? 4500.0 : 3000.0;
        }

        @Override
        public String getTipo() {
            return "Taller";
        }

        @Override
        public String generarCertificado(Estudiante estudiante) {
            return "CERTIFICADO DE ASISTENCIA | " + ENTIDAD_EMISORA + "\n" +
                    "Se otorga el presente certificado a: " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() + ")\n" +
                    "Por su participación en el Taller: '" + getTitulo() + "'.";
        }
}
