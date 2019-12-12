package pos;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import library.Transaksi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.stage.Window;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.annotation.Resources;
import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable{
    @FXML private TextField lbTotal;
    @FXML private TextField textDibayar;
    @FXML private Label lbKembalian;
    @FXML private Button btnDone;
    @FXML private GridPane gridContainer;

    @Override
    public void initialize(URL location, ResourceBundle resource){

    }

    @FXML
    private void calculateKembalian(){
        int uang = 0;
        int harga = 0;

        if(textDibayar.getText() != "" && lbTotal.getText() != ""){
            uang = Integer.parseInt(textDibayar.getText());
            harga = Integer.parseInt(lbTotal.getText());
        }

        if(uang > harga){
            lbKembalian.setText(Integer.toString(uang - harga));
            btnDone.setDisable(false);
        }else{
            lbKembalian.setText("-");
            btnDone.setDisable(true);
        }
    }

    @FXML
    private void closeDialog(){
        Stage stage = (Stage) btnDone.getScene().getWindow();
        stage.close();
    }
}
