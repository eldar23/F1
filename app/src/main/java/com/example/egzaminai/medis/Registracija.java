package com.example.egzaminai.medis;

import java.io.Serializable;

/**
 * Created by mokytojas on 2017-07-27.
 */

@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class Registracija implements Serializable {
    private String id;
    private String data;
    private String vartotojas;
    private String lenktynininkas;
    private String trasa;
    private String laikas;
    private String komandos;
    private String treneris;

    public Registracija(String vartotojas, String lenktynininkas, String trasa, String laikas, String komandos, String treneris) {
        this.id = id;
        this.data = data;
        this.vartotojas = vartotojas;
        this.lenktynininkas = lenktynininkas;
        this.trasa = trasa;
        this.laikas = laikas;
        this.komandos = komandos;
        this.treneris = treneris;
    }

    public Registracija(String id, String data, String vartotojas, String lenktynininkas, String trasa, String laikas, String komandos, String treneris) {
        this.id = id;
        this.data = data;
        this.vartotojas = vartotojas;
        this.lenktynininkas = lenktynininkas;
        this.trasa = trasa;
        this.laikas = laikas;
        this.komandos = komandos;
        this.treneris = treneris;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getVartotojas() {
        return vartotojas;
    }

    public void setVartotojas(String vartotojas) {
        this.vartotojas = vartotojas;
    }

    public String getLenktynininkas() {
        return lenktynininkas;
    }

    public void setLenktynininkas(String lenktynininkas) {
        this.lenktynininkas = lenktynininkas;
    }

    public String getTrasa() {
        return trasa;
    }

    public void setTrasa(String trasa) {
        this.trasa = trasa;
    }

    public String getLaikas() {
        return laikas;
    }

    public void setLaikas(String laikas) {
        this.laikas = laikas;
    }

    public String getKomandos() {
        return komandos;
    }

    public void setKomandos(String komandos) {
        this.komandos = komandos;
    }

    public String getTreneris() {
        return treneris;
    }

    public void setTreneris(String treneris) {
        this.treneris = treneris;
    }
}
