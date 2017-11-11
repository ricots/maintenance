package com.acer.e_maintenance.oop;

/**
 * Created by acer on 8/14/2017.
 */

public class Item_data_hardware {
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
    String kon_prosesor ,kon_hdd,kon_ram ,kon_vga ,kon_lan ,
            kon_sound ,kon_keyboard ,kon_mouse ,kon_power ,kon_monitor ,kon_casing;

    public String getPower_supply() {
        return power_supply;
    }

    public void setPower_supply(String power_supply) {
        this.power_supply = power_supply;
    }

    String id_asisten;
    String id_software;
    String nama_software,power_supply;

    public Item_data_hardware(String id_komputer, String prosesor, String hardisk, String memory, String vga,
                              String lan, String sondcard, String keyboard, String mouse, String casing, String monitor,
                              String power_supply,String kon_prosesor ,String kon_hdd,String kon_ram ,String kon_vga ,String kon_lan ,
                              String kon_sound ,String kon_keyboard ,String kon_mouse ,String kon_power ,String kon_monitor ,String kon_casing){
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
        this.kon_casing = kon_casing;
        this.kon_hdd = kon_hdd;
        this.kon_keyboard = kon_keyboard;
        this.kon_lan = kon_lan;
        this.kon_monitor = kon_monitor;
        this.kon_mouse = kon_mouse;
        this.kon_power = kon_power;
        this.kon_ram = kon_ram;
        this.kon_vga = kon_vga;
        this.kon_prosesor = kon_prosesor;
        this.kon_sound = kon_sound;
    }

    public String getKon_prosesor() {
        return kon_prosesor;
    }

    public void setKon_prosesor(String kon_prosesor) {
        this.kon_prosesor = kon_prosesor;
    }

    public String getKon_hdd() {
        return kon_hdd;
    }

    public void setKon_hdd(String kon_hdd) {
        this.kon_hdd = kon_hdd;
    }

    public String getKon_ram() {
        return kon_ram;
    }

    public void setKon_ram(String kon_ram) {
        this.kon_ram = kon_ram;
    }

    public String getKon_vga() {
        return kon_vga;
    }

    public void setKon_vga(String kon_vga) {
        this.kon_vga = kon_vga;
    }

    public String getKon_lan() {
        return kon_lan;
    }

    public void setKon_lan(String kon_lan) {
        this.kon_lan = kon_lan;
    }

    public String getKon_sound() {
        return kon_sound;
    }

    public void setKon_sound(String kon_sound) {
        this.kon_sound = kon_sound;
    }

    public String getKon_keyboard() {
        return kon_keyboard;
    }

    public void setKon_keyboard(String kon_keyboard) {
        this.kon_keyboard = kon_keyboard;
    }

    public String getKon_mouse() {
        return kon_mouse;
    }

    public void setKon_mouse(String kon_mouse) {
        this.kon_mouse = kon_mouse;
    }

    public String getKon_power() {
        return kon_power;
    }

    public void setKon_power(String kon_power) {
        this.kon_power = kon_power;
    }

    public String getKon_monitor() {
        return kon_monitor;
    }

    public void setKon_monitor(String kon_monitor) {
        this.kon_monitor = kon_monitor;
    }

    public String getKon_casing() {
        return kon_casing;
    }

    public void setKon_casing(String kon_casing) {
        this.kon_casing = kon_casing;
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
