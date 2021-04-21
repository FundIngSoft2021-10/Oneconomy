package src.Model;

import java.util.Date;

public class Movimiento {


    int idMovimiento;
    int Value;
    Date fecha_Movimiento;
    String descripcion;
    int idCategoria;
    int IdMetodo_pago;
    String perfilEmail;
    int idExtracto = -1;

    public Date getFecha_Movimiento() {
        return fecha_Movimiento;
    }

    public void setFecha_Movimiento(Date fecha_Movimiento) {
        this.fecha_Movimiento = fecha_Movimiento;
    }

    public Movimiento(int idMovimiento, int value, Date fecha_Movimiento, String descripcion, int idCategoria, int idMetodo_pago, String perfilEmail) {
        this.idMovimiento = idMovimiento;
        Value = value;
        this.fecha_Movimiento = fecha_Movimiento;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        IdMetodo_pago = idMetodo_pago;
        this.perfilEmail = perfilEmail;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdMetodo_pago() {
        return IdMetodo_pago;
    }

    public void setIdMetodo_pago(int idMetodo_pago) {
        IdMetodo_pago = idMetodo_pago;
    }

    public String getPerfilEmail() {
        return perfilEmail;
    }

    public void setPerfilEmail(String perfilEmail) {
        this.perfilEmail = perfilEmail;
    }

    public int getIdExtracto() {
        return idExtracto;
    }

    public void setIdExtracto(int idExtracto) {
        this.idExtracto = idExtracto;
    }
}
