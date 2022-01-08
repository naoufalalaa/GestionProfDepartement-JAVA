package presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import metier.Departement;
import metier.IMetier;
import metier.MetierImpl;

import java.net.URL;
import java.util.ResourceBundle;


public class UpdateDepartementController implements Initializable {
    private String Nom;
    @FXML
    private TextField nom;
    private IMetier metier;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        metier=new MetierImpl();
        this.Nom = (DepartementController.selectedItem).getNom();
        nom.setText(this.Nom);
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

    @FXML
    private Button Update;
    public void updateDepartement(ActionEvent actionEvent) {
        String Nom1 = capitalize(nom.getText());
        Departement d = new Departement(Nom1);
        try {
            metier.updateDepartement(metier.getDepartementInfo(this.Nom),d);
            DepartementController.liste.clear();
            DepartementController.liste.addAll(metier.getAllDepartement());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Modifié avec succès");
            alert.setContentText("Le Département "+Nom+" a été modifié avec succès!");
            alert.show();

            Stage curent = (Stage) Update.getScene().getWindow();
            curent.close();
        }catch(Exception e ){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Erreur");
            alert.setContentText("Le Département "+Nom+" n'a pas été modifié!");
            alert.show();
        }


    }
}
