package com.pdm.recycle.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.pdm.recycle.control.ConfiguracaoFirebase;

import java.util.ArrayList;
import java.util.Date;

public class Descarte {
    private String idDescarte;
    private Double latitude;
    private Double longitude;
    //protected ArrayList<Residuo> residuos;
    //private Date dataDescarte;
    //private boolean status;
    //protected  ArrayList<Usuario> usuarios;

    public Descarte(){
    }

    public void salvarDescarte(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabaseReference();
        DatabaseReference descarte =  firebaseRef.child("descartes").child(idDescarte);

        descarte.setValue(this);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Exclude
    public String getidDescarte() {
        return idDescarte;
    }


    public void setidDescarte(String idDescarte) {
        this.idDescarte = idDescarte;
    }
/*
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
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


 */
}
