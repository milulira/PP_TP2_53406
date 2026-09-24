package modelo.actividades;

import modelo.certificacion.Certificable;
import modelo.Estudiante;
import org.jetbrains.annotations.NotNull;

public class Curso extends Actividad implements Certificable {
        private static final long serialVersionUID = 1L;

        private int horas;

        public Curso(int id, String titulo, int cupoMaximo, int horas) {
            super(id, titulo, cupoMaximo);
            this.horas = horas;
        }

        public int getHoras() { return horas; }

        @Override
        public double calcularCostoMateriales() {
            return horas * 800.0;
        }

        @Override
        public String getTipo() {
            return "Curso";
        }

        @Override
        public String generarCertificado(@NotNull Estudiante estudiante) {
            return "CERTIFICADO DE APROBACIÓN | " + ENTIDAD_EMISORA + "\n" +
                    "Se otorga el presente certificado a: " + estudiante.getNombre() + " (Legajo: " + estudiante.getLegajo() + ")\n" +
                    "Por completar satisfactoriamente el Curso: '" + getTitulo() + "' (" + horas + " hs lectivas).";
        }
}
