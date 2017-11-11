package com.acer.e_maintenance.koneksi;

import com.acer.e_maintenance.oop.Item_komputer;

/**
 * Created by ACER on 2/28/2017.
 */

public class config {
    public static final String DAFTAR_HARDWARE =  "http://mydeveloper.id/maintenance/list_detail_hardware.php?id_asisten=";
    public static final String ADD_HARDWARE = "http://mydeveloper.id/maintenance/add_hardware.php";
    public static final String LOGIN = "http://mydeveloper.id/maintenance/login_asisten.php";
    public static final String UPDATE_HARDWARE = "http://mydeveloper.id/maintenance/update_hardware.php";
    public static final String DELETE_HARDWARE = "http://mydeveloper.id/maintenance/delete_hardware.php?id_komputer=";
    public static final String DAFTAR_SOFTWARE = "http://mydeveloper.id/maintenance/daftar_software.php";
    public static final String ADD_SOFTWARE = "http://mydeveloper.id/maintenance/add_software.php";
    public static final String ADD_SOFTWARE_new = "http://mydeveloper.id/maintenance/add_software_new.php";
    public static final String DELETE_SOFT = "http://mydeveloper.id/maintenance/delete_software.php?id_software=";
    public static final String DELETE_KOM_SOFT = "http://mydeveloper.id/maintenance/delete_kom_software.php?id_software=";
    public static final String MAINTENANCE_HARD = "http://mydeveloper.id/maintenance/maintenance_hardware.php";
    public static final String DETAIL_SOFTWARE_RUANG = "http://mydeveloper.id/maintenance/detail_software_peruang.php?id_komputer=";
    public static final String MAINTENANCE_SOFT = "http://mydeveloper.id/maintenance/maintenance_soft.php";
    public static final String MAINTENANCE_TINDAK_HARD = "http://mydeveloper.id/maintenance/tindak_lanjut_hard.php";
    public static final String MAINTENANCE_TINDAK_SOFT = "http://mydeveloper.id/maintenance/tindak_lanjut_soft.php";
    public static final String LIST_TINDAK_HARD = "http://mydeveloper.id/maintenance/list_tindak_lanjut_hard.php?tanggal_tindak_hard=";
    public static final String LIST_TINDAK_SOFT = "http://mydeveloper.id/maintenance/list_tindak_lanjut_soft.php?tanggal_tindak_soft=";
    public static final String LIST_DATA_HARD = "http://mydeveloper.id/maintenance/list_data_maintenance_hard.php?tanggal=";
    public static final String DATA_TANGGAL_SOFT = "http://mydeveloper.id/maintenance/data_soft_tiap_kom.php?tanggal_soft=";
    public static final String DATA_SOFTWARE = "http://mydeveloper.id/maintenance/data_software.php?tanggal_soft=";
    public static final String UPDATE_TOKEN = "http://mydeveloper.id/maintenance/update_token.php";

    public static final String KEY_TOKEN = "token";
    public static final String KEY_PROSESOR = "prosesor";
    public static final String KEY_HDD = "hard_disk";
    public static final String KEY_RAM = "memory";
    public static final String KEY_LAN = "nic_lan";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ID_asis = "id_asisten";
    public static final String KEY_SOUNDCARD = "sound_card";
    public static final String KEY_KEYBOARD = "keyboard";
    public static final String KEY_MOUSE = "mouse";
    public static final String KEY_POWER = "power_supply";
    public static final String KEY_MONITOR = "monitor";
    public static final String KEY_CASING = "casing";
    public static final String KEY_VGA = "vga";
    public static final String KEY_ID_KOM = "id_komputer";
    public static final String KEY_SOFT = "nama_software";
    public static final String KEY_id_SOft = "id_software";
    public static final String JSON_ARRAY = "list_software";
    public static final String KEY_KONDISI_SOUNDCARD = "kondisi_soundcard";
    public static final String KEY_KONDISI_KEYBOARD = "kondisi_keyboard";
    public static final String KEY_KONDISI_MOUSE = "kondisi_mouse";
    public static final String KEY_KONDISI_POWER = "kondisi_power_supply";
    public static final String KEY_KONDISI_MONITOR = "kondisi_monitor";
    public static final String KEY_KONDISI_CASING = "kondisi_casing";
    public static final String KEY_KONDISI_VGA = "kondisi_vga";
    public static final String KEY_KONDISI_PROSESOR = "kondisi_prosesor";
    public static final String KEY_KONDISI_HDD = "kondisi_hdd";
    public static final String KEY_KONDISI_RAM = "kondisi_ram";
    public static final String KEY_KONDISI_LAN = "kondisi_lan";
    public static final String KEY_KONDISI_SOFT = "kondisi_soft";

    public static final String KEY_ID_TINDAKHARD = "id_tindak_hard";
    public static final String KEY_TGL_TINDAK = "tanggal_tindak_hard";
    public static final String KEY_ID_KOM_TINDAK = "id_kom_tindak";
    public static final String KEY_NAMA_KOMPONEN = "nama_komponen_tindak";
    public static final String KEY_KET_TINDAK = "ket_tindak_lanjut";
    public static final String KEY_KET = "keterangan";
    public static final String KEY_MAINTENANCE_HARD = "ket_maintenance_hard";
    public static final String KEY_ID_KOM_SOFT = "id_kom_soft";
    public static final String KEY_NAMA_SOFT = "nama_soft_tindak";
    public static final String KEY_KETERANGAN_TINDAK = "keterangan_tindak_soft";
    public static final String KEY_KET_SOFT = "ket_soft";

    public static final String LOGIN_SUCCESS = "success";
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String EMAIL_SHARED_PREF = "npm";
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}
