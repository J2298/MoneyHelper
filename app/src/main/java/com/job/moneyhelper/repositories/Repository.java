package com.job.moneyhelper.repositories;

import com.job.moneyhelper.models.Operacion;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository _INSTANCE = null;

    private Repository() {
    }

    public static Repository getInstance() {
        if (_INSTANCE == null)
            _INSTANCE = new Repository();
        return _INSTANCE;
    }

    private List<Operacion> operaciones = new ArrayList<>();

    public List<Operacion> getGastos() {
        return this.operaciones;
    }

    public void agregarOperacion(Operacion operacion) {
        this.operaciones.add(operacion);
    }
}

