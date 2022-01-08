package presentation;

import DAO.SignletonConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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


public class AjouterProfesseurController implements Initializable {
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField address;
    @FXML
    private TextField phone;
    @FXML
    private TextField cin;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dateR;
    @FXML
    private ComboBox<Departement> departement;

    private IMetier metier;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        metier=new MetierImpl();
        Connection conn = SignletonConnectionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Departement");
            ResultSet rs= pstm.executeQuery();
            while(rs.next()){
                departement.getItems().add(new Departement(rs.getInt("ID_DEPART"), rs.getString("NOM")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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


    public void ajouterProfesseur(ActionEvent actionEvent) {
        String Nom = nom.getText().toUpperCase();
        String Prenom = capitalize(prenom.getText());
        String Cin = cin.getText().toUpperCase();
        String Address = address.getText();
        String Email = email.getText().toLowerCase();
        String Phone = phone.getText();
        LocalDate date = dateR.getValue();

        Departement d = (Departement) departement.getValue();

        Professeur p = new Professeur(Nom,Prenom,Cin,Address,Phone,Email,date,d);
        try {
            metier.addProfesseur(p);
            ProfesseurController.liste.clear();
            ProfesseurController.liste.addAll(metier.getAllProfesseurs());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Ajouté avec succès");
            alert.setContentText("Le professeur "+Nom+" "+Prenom+" a été ajouté avec succès!");
            alert.show();
            nom.setText("");
            prenom.setText("");
            cin.setText("");
            address.setText("");
            email.setText("");
            phone.setText("");
            dateR.setValue(LocalDate.of(2000,01,01));
        }catch(Exception e ){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Erreur");
            alert.setContentText("Le professeur "+Nom+" "+Prenom+" n'a pas été ajouté!");
            alert.show();
        }


    }
}
