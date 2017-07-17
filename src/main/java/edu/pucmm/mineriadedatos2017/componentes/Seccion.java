package edu.pucmm.mineriadedatos2017.componentes;

public class Seccion {
    private int x;
    private int y;
    private int w;
    private int h;
    private boolean isActivo;

    public Seccion(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.isActivo = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public void setActivo(boolean activo) {
        isActivo = activo;
    }

    public boolean isActivo() {
        return isActivo;
    }
}
