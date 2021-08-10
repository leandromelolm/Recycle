package com.pdm.recycle.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.pdm.recycle.control.ConfiguracaoFirebase;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class Descarte {
    private String idDescarte;
    private Double latitude;
    private Double longitude;
    private String tipoResiduo;
    private Collection<String> residuos;

    private boolean coletado;
    private Usuario usuario;
    private Date data;

    public Descarte(){
    }

    public void salvarDescarte(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabaseReference();
        DatabaseReference descarte =  firebaseRef.child("descartes").child(idDescarte);

        descarte.setValue(this);

        // método push() gera identificador único
        //descarte.push().setValue(descarte);
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

    public String getTipoResiduo() {
        return tipoResiduo;
    }

    public void setTipoResiduo(String tipoResiduo) {
        this.tipoResiduo = tipoResiduo;
    }

    public Collection<String> getResiduos() {
        return residuos;
    }

    public void setResiduos(Collection<String> residuos) {
        this.residuos = residuos;
    }

    public void addTipoResiduo(String tipoResiduo) {
        residuos.add(tipoResiduo);
    }

    public boolean isColetado() {
        return coletado;
    }

    public void setColetado(boolean coletado) {
        this.coletado = coletado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
