package com.mediumsitompul.sensortemperatureiot.adapter;

import java.util.HashMap;

public class MainTable_GetSet {
    private String no;
    private String date;
    private String flagging;
    private String suhu;
    private HashMap<String,String> datatables;

    public MainTable_GetSet(String no, String date, String flagging, String suhu, HashMap<String,String> datatables) {
        this.no = no;
        this.date = date;
        this.flagging = flagging;
        this.suhu = suhu;
        this.datatables = datatables;
    }

    public String getNo() {
        return no;
    }

    public String getDate() {
        return date;
    }

    public String getFlagging() {
        return flagging;
    }

    public String getSuhu() {
        return suhu;
    }

    public HashMap<String, String> getDatatables() {
        return datatables;
    }
}
