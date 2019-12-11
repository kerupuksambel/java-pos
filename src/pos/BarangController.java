package pos;

import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Barang;

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

public class BarangController implements Initializable {
    @FXML private TextField textIDBarang;
    @FXML private TextField textNamaBarang;
    @FXML private TextField textHargaBarang;
    @FXML private TextField textCariBarang;
    @FXML private Button btnTambahBarang;
    @FXML private Button btnUbahBarang;
    @FXML private Button btnHapusBarang;
    @FXML private TableView<Barang> tbBarang;
    @FXML private TableColumn<Barang, Integer> tbBarangID;
    @FXML private TableColumn<Barang, String> tbBarangNama;
    @FXML private TableColumn<Barang, Integer> tbBarangHarga;

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

    @Override public void initialize(URL location, ResourceBundle resources){
        updateTable();
    }

    @FXML private void tambahBarang(){
        String query = "INSERT INTO barang (id, nama, harga) VALUES (NULL, '"+textNamaBarang.getText()+"',"+textHargaBarang.getText()+")";
        executeQuery(query);
        updateTable();
    }

    @FXML private void ubahBarang(){
        String query = "UPDATE barang SET nama = '"+textNamaBarang.getText()+"', harga = "+textHargaBarang.getText()+" WHERE id = "+textIDBarang.getText()+"";
        executeQuery(query);
        updateTable();
    }

    @FXML private void hapusBarang(){
        String query = "DELETE FROM barang WHERE id = "+textIDBarang.getText()+"";
        executeQuery(query);
        updateTable();
    }

    private ObservableList getTableContent(){
        ObservableList<Barang> itemList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM barang";
        Statement state;
        ResultSet result;

        try{
            state = connection.createStatement();
            result = state.executeQuery(query);
            while(result.next()) {
                Barang item = new Barang();
                item.setId(result.getInt("id"));
                item.setNama(result.getString("nama"));
                item.setHarga(result.getInt("harga"));
                itemList.add(item);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return itemList;
    }

    @FXML
    private void getTableRow(){
        if(tbBarang.getSelectionModel().getSelectedItem() != null){
            Barang selected = tbBarang.getSelectionModel().getSelectedItem();
            textIDBarang.setText(Integer.toString(selected.getId()));
            textNamaBarang.setText(selected.getNama());
            textHargaBarang.setText(Integer.toString(selected.getHarga()));
        }
    }

    private void updateTable(){
        ObservableList <Barang> barangList = getTableContent();

        tbBarangID.setCellValueFactory(new PropertyValueFactory<Barang, Integer>("id"));
        tbBarangNama.setCellValueFactory(new PropertyValueFactory<Barang, String>("nama"));
        tbBarangHarga.setCellValueFactory(new PropertyValueFactory<Barang, Integer>("harga"));

        tbBarang.setItems(barangList);
    }

    @FXML
    private void cariBarang(){
        ObservableList<Barang> itemList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM barang WHERE LOWER(nama) LIKE LOWER('%"+textCariBarang.getText()+"%')";
        Statement state;
        ResultSet result;

        try{
            state = connection.createStatement();
            result = state.executeQuery(query);
            while(result.next()) {
                Barang item = new Barang();
                item.setId(result.getInt("id"));
                item.setNama(result.getString("nama"));
                item.setHarga(result.getInt("harga"));
                itemList.add(item);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        tbBarangID.setCellValueFactory(new PropertyValueFactory<Barang, Integer>("id"));
        tbBarangNama.setCellValueFactory(new PropertyValueFactory<Barang, String>("nama"));
        tbBarangHarga.setCellValueFactory(new PropertyValueFactory<Barang, Integer>("harga"));

        tbBarang.setItems(itemList);
    }
}
