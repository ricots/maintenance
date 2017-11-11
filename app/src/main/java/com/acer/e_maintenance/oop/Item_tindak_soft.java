package com.acer.e_maintenance.oop;

/**
 * Created by acer on 7/19/2017.
 */

public class Item_tindak_soft {
    String id_tindak_soft;
    String tanggal_tindak_soft;
    String id_kom_soft;
    String nama_soft_tindak;
    String keterangan_tindak_soft;
    String ket_soft;
    String ket_soft_maintenance;

    public Item_tindak_soft(String id_kom_soft, String nama_soft_tindak, String keterangan_tindak_soft, String ket_soft) {
        this.id_kom_soft = id_kom_soft;
        this.nama_soft_tindak = nama_soft_tindak;
        this.keterangan_tindak_soft = keterangan_tindak_soft;
        this.ket_soft = ket_soft;
    }

    public String getId_tindak_soft() {
        return id_tindak_soft;
    }

    public void setId_tindak_soft(String id_tindak_soft) {
        this.id_tindak_soft = id_tindak_soft;
    }

    public String getTanggal_tindak_soft() {
        return tanggal_tindak_soft;
    }

    public void setTanggal_tindak_soft(String tanggal_tindak_soft) {
        this.tanggal_tindak_soft = tanggal_tindak_soft;
    }

    public String getId_kom_soft() {
        return id_kom_soft;
    }

    public void setId_kom_soft(String id_kom_soft) {
        this.id_kom_soft = id_kom_soft;
    }

    public String getNama_soft_tindak() {
        return nama_soft_tindak;
    }

    public void setNama_soft_tindak(String nama_soft_tindak) {
        this.nama_soft_tindak = nama_soft_tindak;
    }

    public String getKeterangan_tindak_soft() {
        return keterangan_tindak_soft;
    }

    public void setKeterangan_tindak_soft(String keterangan_tindak_soft) {
        this.keterangan_tindak_soft = keterangan_tindak_soft;
    }

    public String getKet_soft() {
        return ket_soft;
    }

    public void setKet_soft(String ket_soft) {
        this.ket_soft = ket_soft;
    }

    public String getKet_soft_maintenance() {
        return ket_soft_maintenance;
    }

    public void setKet_soft_maintenance(String ket_soft_maintenance) {
        this.ket_soft_maintenance = ket_soft_maintenance;
    }
}
