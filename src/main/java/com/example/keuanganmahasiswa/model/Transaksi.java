package com.example.keuanganmahasiswa.model;

public class Transaksi {
    private int id,user_id,nominal;
    private String kategori,tanggal;
    private String jenis_transaksi;

    public Transaksi(int id, int user_id, int nominal, String kategori, String tanggal, String jenis_transaksi) {
        this.id = id;
        this.user_id = user_id;
        this.nominal = nominal;
        this.kategori = kategori;
        this.tanggal = tanggal;
        this.jenis_transaksi = jenis_transaksi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Transaksi(int id, int user_id, int nominal, String jenis_transaksi) {
        this.id = id;
        this.user_id = user_id;
        this.nominal = nominal;
        this.jenis_transaksi = jenis_transaksi;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getJenis_transaksi() {
        return jenis_transaksi;
    }

    public void setJenis_transaksi(String jenis_transaksi) {
        this.jenis_transaksi = jenis_transaksi;
    }
}
