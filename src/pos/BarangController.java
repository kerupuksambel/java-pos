package pos;

import library.Barang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.Window;

public class BarangController {
    @FXML
    private TextField textNamaBarang;

    @FXML
    private TextField textHargaBarang;

    @FXML
    private Button btnTambahBarang;

    @FXML
    private Button btnUbahBarang;

    @FXML
    private Button btnHapusBarang;

    @FXML
    private TableView<Barang> tbBarang;

    @FXML
    private TableColumn<Barang, Integer> tbBarangID;

    @FXML
    private TableColumn<Barang, String> tbBarangNama;

    @FXML
    private TableColumn<Barang, Integer> tbBarangHarga;
}
