package pos;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import library.Transaksi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.annotation.Resources;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{
    @FXML private TableView tableTrx;
    @FXML private TableColumn colID;
    @FXML private TableColumn colNama;
    @FXML private TableColumn colHarga;
    @FXML private TableColumn colJumlah;
    @FXML private TableColumn colSubTotal;
    @FXML private Label lbTotal;

    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/java_pos","root", "");
            return conn;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;

        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resource){
        setTable();
    }

    private void setTable(){
        colID.setCellValueFactory(new PropertyValueFactory<Transaksi, TextField>("id"));
        colID.setCellFactory(TextFieldTableCell.forTableColumn());
        colID.setEditable(true);
        colID.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent t) {
                ((Transaksi) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setId(t.getNewValue().toString());
                handleTransaksi();
            }
        });
        colNama.setCellValueFactory(new PropertyValueFactory<Transaksi, String>("nama"));
        colHarga.setCellValueFactory(new PropertyValueFactory<Transaksi, Integer>("harga"));
        colJumlah.setCellValueFactory(new PropertyValueFactory<Transaksi, TextField>("jumlah"));
        colJumlah.setCellFactory(TextFieldTableCell.forTableColumn());
        colJumlah.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent t) {
                ((Transaksi) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setJumlah(t.getNewValue().toString());
                hitungSubTotal();
            }
        });

        colSubTotal.setCellValueFactory(new PropertyValueFactory<Transaksi, Integer>("subtotal"));


        ObservableList<Transaksi> transaksiList = FXCollections.observableArrayList();

        for (int i = 0; i < 100; i++){
            transaksiList.add(new Transaksi());
        }

        tableTrx.setItems(transaksiList);
    }

    @FXML
    private void handleTransaksi(){
        //Dapatkan index item terpilih
        int trxIndex = tableTrx.getSelectionModel().getSelectedIndex();
        //Dapatkan seluruh item di table
        ObservableList <Transaksi> trxData = tableTrx.getItems();
        //Dapatkan data ID dari Transaksi item terpilih
        String trxID = trxData.get(trxIndex).getId();
        //Buat obj Transaksi baru

        Transaksi trxNew = cekBarang(trxID);
        trxData.set(trxIndex, trxNew);
    }

    private void hitungSubTotal(){
        //Dapatkan index item terpilih
        int trxIndex = tableTrx.getSelectionModel().getSelectedIndex();
        //Dapatkan seluruh item di table
        ObservableList <Transaksi> trxData = tableTrx.getItems();
        //Dapatkan data dari Transaksi item terpilih
        int trxHarga = trxData.get(trxIndex).getHarga();
        String jml = trxData.get(trxIndex).getJumlah();
        int trxJumlah = Integer.parseInt(jml);
        //Buat Transaksi baru
        Transaksi trxNew = trxData.get(trxIndex);
        trxNew.setSubtotal(trxHarga * trxJumlah);
        trxData.set(trxIndex, trxNew);
        refreshTotal();
    }

    private void refreshTotal(){
        int total = 0;
        ObservableList <Transaksi> trxData = tableTrx.getItems();
        for(Transaksi t : trxData){
            total = total + t.getSubtotal();
        }
        lbTotal.setText(Integer.toString(total));
    }

    private Transaksi cekBarang(String ID){
        ObservableList <Transaksi> barang = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM barang WHERE id = "+ ID;
        Statement state;
        ResultSet result;

        try{
            state = connection.createStatement();
            result = state.executeQuery(query);
            if(result != null){
                result.last();
                Transaksi item = new Transaksi();
                item.setId(result.getString("id"));
                item.setNama(result.getString("nama"));
                item.setHarga(result.getInt("harga"));
                barang.add(item);
            }
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("ID Barang yang Anda masukkan tidak valid!");
            alert.show();
        }
        return barang.get(0);
    }
}
