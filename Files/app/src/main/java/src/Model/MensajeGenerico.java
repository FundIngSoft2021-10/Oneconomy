    package src.Model;

    public class MensajeGenerico {

        String ID ;
        String campoMensaje;

        public MensajeGenerico(String ID, String campoMensaje) {
            this.ID = ID;
            this.campoMensaje = campoMensaje;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getCampoMensaje() {
            return campoMensaje;
        }

        public void setCampoMensaje(String campoMensaje) {
            this.campoMensaje = campoMensaje;
        }
    }
