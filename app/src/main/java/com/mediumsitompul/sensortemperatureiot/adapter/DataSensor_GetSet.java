package com.mediumsitompul.sensortemperatureiot.adapter;

public class DataSensor_GetSet {
    private String id_witel;
    private String witel;
    private String id_datel;
    private String datel;
    private String sto;
    private String flagging;

    public DataSensor_GetSet(String id_witel, String witel) {
        this.id_witel = id_witel;
        this.witel = witel;
    }

    public DataSensor_GetSet(String id_witel, String witel, String id_datel, String datel) {
        this.id_witel = id_witel;
        this.witel = witel;
        this.id_datel = id_datel;
        this.datel = datel;
    }

    public DataSensor_GetSet(String id_witel, String witel, String id_datel, String datel, String sto, String flagging) {
        this.id_witel = id_witel;
        this.witel = witel;
        this.id_datel = id_datel;
        this.datel = datel;
        this.sto = sto;
        this.flagging = flagging;
    }

    public String getId_witel() {
        return id_witel;
    }

    public void setId_witel(String id_witel) {
        this.id_witel = id_witel;
    }

    public String getWitel() {
        return witel;
    }

    public void setWitel(String witel) {
        this.witel = witel;
    }

    public String getId_datel() {
        return id_datel;
    }

    public void setId_datel(String id_datel) {
        this.id_datel = id_datel;
    }

    public String getDatel() {
        return datel;
    }

    public void setDatel(String datel) {
        this.datel = datel;
    }

    public String getSto() {
        return sto;
    }

    public void setSto(String sto) {
        this.sto = sto;
    }

    public String getFlagging() {
        return flagging;
    }

    public void setFlagging(String flagging) {
        this.flagging = flagging;
    }
}
