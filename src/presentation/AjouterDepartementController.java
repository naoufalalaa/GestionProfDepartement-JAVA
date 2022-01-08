package presentation;

import DAO.SignletonConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import metier.Departement;
import metier.IMetier;
import metier.MetierImpl;
import metier.Professeur;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class AjouterDepartementController implements Initializable {
    @FXML
    private TextField nom;
    private IMetier metier;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        metier=new MetierImpl();

    }
    public String capitalize(String str) {
        String st = "";
        String[] a = str.split(" ");
        if(a.length>1) {
            for (int i = 0; i < a.length; i++) {
                a[i] = (a[i].toLowerCase()).replace((a[i].charAt(0)), (a[i].toUpperCase().charAt(0)));
                st += a[i] + " ";
            }
            return st.substring(0,st.length()-1);
        }
        else{
            a=str.split("");
            a[0]=a[0].toUpperCase();
            for (int i=0;i < a.length; i++){
                st+=a[i];
            }
        }

        return st;
    }


    public void ajouterDepartement(ActionEvent actionEvent) {
        String Nom = nom.getText().toUpperCase();

        Departement d = new Departement(Nom);
        try {
            metier.addDepartement(d);
            DepartementController.liste.clear();
            DepartementController.liste.addAll(metier.getAllDepartement());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Ajouté avec succès");
            alert.setContentText("Le Département "+Nom+" a été ajouté avec succès!");
            alert.show();
        }catch(Exception e ){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Erreur");
            alert.setContentText("Le Département "+Nom+" n'a pas été ajouté!");
            alert.show();
        }


    }
}
