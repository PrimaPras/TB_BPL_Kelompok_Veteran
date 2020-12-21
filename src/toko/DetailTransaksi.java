package toko;

public class DetailTransaksi {
    private String sku;
    private int jumlah;
    private int harga;

    public DetailTransaksi(String sku, int jumlah, int harga) {
        this.sku = sku;
        this.jumlah = jumlah;
        this.harga = harga;
    }

    public int getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getSku() {
        return sku;
    }

}
