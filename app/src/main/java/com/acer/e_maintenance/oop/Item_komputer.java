package com.acer.e_maintenance.oop;

/**
 * Created by acer on 8/14/2017.
 */

public class Item_komputer {
    String id_komputer,id_asisten;

    public Item_komputer(String id_komputer){
        this.id_komputer = id_komputer;
    }

    public String getId_komputer() {
        return id_komputer;
    }

    public void setId_komputer(String id_komputer) {
        this.id_komputer = id_komputer;
    }

    public String getId_asisten() {
        return id_asisten;
    }

    public void setId_asisten(String id_asisten) {
        this.id_asisten = id_asisten;
    }
}
