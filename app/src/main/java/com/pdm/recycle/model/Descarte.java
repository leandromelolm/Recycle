package com.pdm.recycle.model;

import java.util.ArrayList;
import java.util.Date;

public class Descarte {
    private int codDescarte;
    protected ArrayList<Residuo> residuos;
    private Date dataDescarte;
    private boolean status;

    public int getCodDescarte() {
        return codDescarte;
    }

    public void setCodDescarte(int codDescarte) {
        this.codDescarte = codDescarte;
    }

    public ArrayList<Residuo> getResiduos() {
        return residuos;
    }

    public void setResiduos(ArrayList<Residuo> residuos) {
        this.residuos = residuos;
    }

    public Date getDataDescarte() {
        return dataDescarte;
    }

    public void setDataDescarte(Date dataDescarte) {
        this.dataDescarte = dataDescarte;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
