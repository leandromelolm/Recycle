package com.pdm.recycle.model;

import com.google.firebase.database.DatabaseReference;
import com.pdm.recycle.control.ConfiguracaoFirebase;

import java.util.Collection;

public class Coleta {
    private String idDescarte;
    private Double latitude;
    private Double longitude;
    private String tipoResiduo;
    private Collection<String> residuos;
    private String status;
    private String userEmail;
    private String dataDescarte;
    private String dataColeta;

    public Coleta(){
    }

    public void salvarColeta(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabaseReference();
        DatabaseReference coleta =  firebaseRef.child("coletas");

        //coleta.setValue(this);
        coleta.push().setValue(this);  // push() gera identificador automáticamente
    }

   public String getIdDescarte() {
        return idDescarte;
    }

    public void setIdDescarte(String idDescarte) {
        this.idDescarte = idDescarte;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDescarte() {
        return dataDescarte;
    }

    public void setDataDescarte(String data) {
        this.dataDescarte = data;
    }

    public String getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(String dataColeta) {
        this.dataColeta = dataColeta;
    }
}
