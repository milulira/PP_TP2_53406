package modelo.actividades;

    public class Charla extends Actividad {
        private static final long serialVersionUID = 1L;

        private String disertante;

        public Charla(int id, String titulo, int cupoMaximo) {
            super(id, titulo, cupoMaximo);
            this.disertante = disertante;
        }

        public String getDisertante() { return disertante; }

        @Override
        public double calcularCostoMateriales() {
            return 1500.0; // Costo estimado de folletos / minutas
        }

        @Override
        public String getTipo() {
            return "Charla";
        }
    }
