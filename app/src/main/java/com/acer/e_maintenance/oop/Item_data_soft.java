package com.acer.e_maintenance.oop;

/**
 * Created by acer on 7/19/2017.
 */

public class Item_data_soft {
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
    String id_asisten;
    String id_software;
    String nama_software,kondisi_soft;

    public Item_data_soft(String id_komputer, String id_software, String nama_software, String kondisi_soft) {
        this.kondisi_soft = kondisi_soft;
        this.id_komputer = id_komputer;
        this.id_software = id_software;
        this.nama_software = nama_software;
    }

    public String getKondisi_soft() {
        return kondisi_soft;
    }

    public void setKondisi_soft(String kondisi_soft) {
        this.kondisi_soft = kondisi_soft;
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

    public String getId_asisten() {
        return id_asisten;
    }

    public void setId_asisten(String id_asisten) {
        this.id_asisten = id_asisten;
    }

    public String getPower_suply() {
        return power_suply;
    }

    public void setPower_suply(String power_suply) {
        this.power_suply = power_suply;
    }

    String power_suply;

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
}
