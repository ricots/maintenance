package com.acer.e_maintenance.oop;

/**
 * Created by acer on 7/19/2017.
 */

public class Item_tindak_hard {
    String id_tindak_hard;
    String tanggal_tindak_hard;
    String id_kom_tindak;
    String nama_komponen_tindak;
    String ket_tindak_lanjut;
    String keterangan;
    String ket_maintenance_hard;

    public Item_tindak_hard(String id_kom_tindak, String nama_komponen_tindak, String ket_tindak_lanjut, String keterangan) {
        this.id_kom_tindak = id_kom_tindak;
        this.nama_komponen_tindak = nama_komponen_tindak;
        this.ket_tindak_lanjut = ket_tindak_lanjut;
        this.keterangan = keterangan;
    }

    public String getId_tindak_hard() {
        return id_tindak_hard;
    }

    public void setId_tindak_hard(String id_tindak_hard) {
        this.id_tindak_hard = id_tindak_hard;
    }

    public String getTanggal_tindak_hard() {
        return tanggal_tindak_hard;
    }

    public void setTanggal_tindak_hard(String tanggal_tindak_hard) {
        this.tanggal_tindak_hard = tanggal_tindak_hard;
    }

    public String getId_kom_tindak() {
        return id_kom_tindak;
    }

    public void setId_kom_tindak(String id_kom_tindak) {
        this.id_kom_tindak = id_kom_tindak;
    }

    public String getNama_komponen_tindak() {
        return nama_komponen_tindak;
    }

    public void setNama_komponen_tindak(String nama_komponen_tindak) {
        this.nama_komponen_tindak = nama_komponen_tindak;
    }

    public String getKet_tindak_lanjut() {
        return ket_tindak_lanjut;
    }

    public void setKet_tindak_lanjut(String ket_tindak_lanjut) {
        this.ket_tindak_lanjut = ket_tindak_lanjut;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKet_maintenance_hard() {
        return ket_maintenance_hard;
    }

    public void setKet_maintenance_hard(String ket_maintenance_hard) {
        this.ket_maintenance_hard = ket_maintenance_hard;
    }
}
