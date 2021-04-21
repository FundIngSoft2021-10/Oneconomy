package src.Model;

public class Categoria {

    int ID;
    String nombre;
    String descripcion;
    String perfilEmail = null;

    public Categoria(int ID, String nombre, String descripcion) {
        this.ID = ID;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPerfilEmail() {
        return perfilEmail;
    }

    public void setPerfilEmail(String perfilEmail) {
        this.perfilEmail = perfilEmail;
    }
}
