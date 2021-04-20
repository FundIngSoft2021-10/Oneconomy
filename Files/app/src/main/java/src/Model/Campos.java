package src.Model;

public class Campos {

    Object campo1;
    Object campo2;
    Object campo3;
    Object campo4;


    public Campos(String campo1, String campo2, String campo3, String campo4) {
        this.campo1 = campo1;
        this.campo2 = campo2;
        this.campo3 = campo3;
        this.campo4 = campo4;
    }


    public Object getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public Object getCampo2() {
        return campo2;
    }

    public void setCampo2(Object campo2) {
        this.campo2 = campo2;
    }

    public Object getCampo3() {
        return campo3;
    }

    public void setCampo3(Object campo3) {
        this.campo3 = campo3;
    }

    public Object getCampo4() {
        return campo4;
    }

    public void setCampo4(Object campo4) {
        this.campo4 = campo4;
    }
}

