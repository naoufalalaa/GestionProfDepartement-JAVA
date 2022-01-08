package presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import metier.Departement;
import metier.IMetier;
import metier.MetierImpl;
import metier.Professeur;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

import static presentation.ProfesseurController.liste;

public class HomeController implements Initializable {
    @FXML
    private TableView<Professeur> PROFESSEURS;
    @FXML
    private TableColumn<Professeur,Integer> ID_PROF;
    @FXML
    private TableColumn<Professeur,String> NOM;
    @FXML
    private TableColumn<Professeur,String> PRENOM;
    @FXML
    private TableColumn<Professeur,String> CIN;
    @FXML
    private TableColumn<Professeur,String> TELEPHONE;
    @FXML
    private TableColumn<Professeur,String> EMAIL;
    @FXML
    private TableColumn<Professeur, Date> DATERECRUTEMENT;
    @FXML
    private TableColumn<Professeur, Departement> DEPARTEMENT;
    //ObservableList<Professeur> liste= FXCollections.observableArrayList();
    private IMetier metier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ID_PROF.setCellValueFactory(new PropertyValueFactory<>("id_prof"));
        NOM.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PRENOM.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        CIN.setCellValueFactory(new PropertyValueFactory<>("cin"));
        TELEPHONE.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        EMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        DATERECRUTEMENT.setCellValueFactory(new PropertyValueFactory<>("date_recrutement"));
        DEPARTEMENT.setCellValueFactory(new PropertyValueFactory<>("departement"));

        metier=new MetierImpl();
        liste.addAll(metier.getAllProfesseurs());
        PROFESSEURS.setItems(liste);
    }
    @FXML
    private TextField search;

    public void Search(){
        String element = search.getText();
        liste.clear();
        liste.addAll(metier.getProfesseursByKeyWord(element));
        PROFESSEURS.setItems(liste);
    }
    @FXML
    private Button EXIT;
    public void exit(ActionEvent actionEvent) throws IOException {
        try {
            Stage curent = (Stage) EXIT.getScene().getWindow();
            curent.close();
        }catch (Exception er){
            System.out.println(er.getCause());
        }
    }

    @FXML
    private Button Departement;
    public void goToDeparement(ActionEvent actionEvent) {
        try{
            BorderPane root = FXMLLoader.load(getClass().getResource("DepartementView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Départements - Gestion");
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private Button Professeur;
    public void goToProfesseur(ActionEvent actionEvent) {
        try{
            BorderPane root = FXMLLoader.load(getClass().getResource("ProfesseursView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Professeurs - Gestion");
            stage.setResizable(false);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
