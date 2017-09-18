package com.job.moneyhelper.models;

public class Operacion {

    private double monto;
    private String tipo;
    private String cuenta;

    public Operacion() {
    }

    public Operacion(double monto, String tipo, String cuenta) {
        this.monto = monto;
        this.tipo = tipo;
        this.cuenta = cuenta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
