package src.Model;

public class DatoGrafica {

    private String nombre;
    private float ingreso;
    private float egreso;

    public DatoGrafica() {
    }

    public DatoGrafica(String nombre, float ingreso, float egreso) {
        this.nombre = nombre;
        this.ingreso = ingreso;
        this.egreso = egreso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getIngreso() {
        return ingreso;
    }

    public void setIngreso(float ingreso) {
        this.ingreso = ingreso;
    }

    public float getEgreso() {
        return egreso;
    }

    public void setEgreso(float egreso) {
        this.egreso = egreso;
    }

}
