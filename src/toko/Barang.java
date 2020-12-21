package toko;

public class Barang {
    private String sku;
    private String nama;
    private int stok;
    private int harga_beli;
    private int harga_jual;


    public Barang(String sku, String nama, int stok, int harga_beli, int harga_jual) {
        this.sku = sku;
        this.nama = nama;
        this.stok = stok;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public int getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(int harga_beli) {
        this.harga_beli = harga_beli;
    }

    public int getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(int harga_jual) {
        this.harga_jual = harga_jual;
    }
}