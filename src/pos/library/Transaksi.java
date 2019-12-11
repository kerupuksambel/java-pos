package library;

public class Transaksi {
    private String id;
    private String nama;
    private int harga;
    private String jumlah;
    private int subtotal;

    public Transaksi(){
        this.id = "";
        this.nama = "";
        this.harga = 0;
        this.jumlah = "";
        this.subtotal = 0;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public String getJumlah() {
        return jumlah;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
