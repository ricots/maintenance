package com.acer.e_maintenance.oop;

/**
 * Created by acer on 8/14/2017.
 */

public class Item_hardware {
    String id_komputer;
    String id_ruang;
    String prosesor;
    String hardisk;
    String memory;
    String vga;
    String lan;
    String sondcard;
    String keyboard;
    String mouse;
    String monitor;
    String casing;

    public String getPower_supply() {
        return power_supply;
    }

    public void setPower_supply(String power_supply) {
        this.power_supply = power_supply;
    }

    String id_asisten;
    String id_software;
    String nama_software,power_supply;

    public Item_hardware(String id_komputer, String prosesor, String hardisk, String memory, String vga,
                         String lan, String sondcard, String keyboard, String mouse, String casing, String monitor,
                            String power_supply){
        this.id_komputer = id_komputer;
        this.prosesor = prosesor;
        this.hardisk = hardisk;
        this.memory = memory;
        this.vga = vga;
        this.lan = lan;
        this.sondcard = sondcard;
        this.keyboard = keyboard;
        this.mouse = mouse;
        this.casing = casing;
        this.monitor = monitor;
        this.power_supply = power_supply;

    }

    public String getId_komputer() {
        return id_komputer;
    }

    public void setId_komputer(String id_komputer) {
        this.id_komputer = id_komputer;
    }

    public String getId_ruang() {
        return id_ruang;
    }

    public void setId_ruang(String id_ruang) {
        this.id_ruang = id_ruang;
    }

    public String getProsesor() {
        return prosesor;
    }

    public void setProsesor(String prosesor) {
        this.prosesor = prosesor;
    }

    public String getHardisk() {
        return hardisk;
    }

    public void setHardisk(String hardisk) {
        this.hardisk = hardisk;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getSondcard() {
        return sondcard;
    }

    public void setSondcard(String sondcard) {
        this.sondcard = sondcard;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getCasing() {
        return casing;
    }

    public void setCasing(String casing) {
        this.casing = casing;
    }

    public String getId_asisten() {
        return id_asisten;
    }

    public void setId_asisten(String id_asisten) {
        this.id_asisten = id_asisten;
    }

    public String getId_software() {
        return id_software;
    }

    public void setId_software(String id_software) {
        this.id_software = id_software;
    }

    public String getNama_software() {
        return nama_software;
    }

    public void setNama_software(String nama_software) {
        this.nama_software = nama_software;
    }
}
