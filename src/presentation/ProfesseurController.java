package presentation;

import DAO.SignletonConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import metier.Departement;
import metier.IMetier;
import metier.MetierImpl;
import metier.Professeur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

public class ProfesseurController implements Initializable {
    static Professeur selectedItem;
    @FXML
    private TableView<metier.Professeur> PROFESSEURS;
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
    private TableColumn<Professeur, metier.Departement> DEPARTEMENT;
    @FXML
    private ComboBox<Departement> Depart;
    static ObservableList<Professeur> liste= FXCollections.observableArrayList();
    private IMetier metier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        metier=new MetierImpl();
        Connection conn = SignletonConnectionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Departement");
            ResultSet rs= pstm.executeQuery();
            while(rs.next()){
                Depart.getItems().add(new Departement(rs.getInt("ID_DEPART"), rs.getString("NOM")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        NOM.setCellValueFactory(new PropertyValueFactory<>("nom"));
        PRENOM.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        CIN.setCellValueFactory(new PropertyValueFactory<>("cin"));
        TELEPHONE.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        EMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        DATERECRUTEMENT.setCellValueFactory(new PropertyValueFactory<>("date_recrutement"));
        DEPARTEMENT.setCellValueFactory(new PropertyValueFactory<>("departement"));

        metier=new MetierImpl();
        liste.clear();
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

    public void goToDeparement(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("DepartementView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Département - Gestion");
            stage.setResizable(false);
            stage.show();
        }catch (Exception er){
            er.printStackTrace();
        }
    }

    public void ajouterProfesseur(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AjouerProfesseurView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter Professeur");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)actionEvent.getSource()).getScene().getWindow() );
            stage.setResizable(false);
            stage.show();
        }catch (Exception er){
            er.printStackTrace();
        }
    }

    public void updateProfesseur(ActionEvent actionEvent) {
        try {
            this.selectedItem = (Professeur) PROFESSEURS.getSelectionModel().getSelectedItem();
            int index = PROFESSEURS.getSelectionModel().getSelectedIndex();
            if(selectedItem!=null) {
                Parent root = FXMLLoader.load(getClass().getResource("UpdateProfesseurView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Modifier Professeur");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                stage.setResizable(false);
                stage.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Aucun élément selectioné");
                alert.setContentText("Veuillez selectioner un élément");
                alert.show();
            }
        }catch (Exception er){
            er.printStackTrace();
        }
    }

    public void deleteProfesseur(){
        try {
            Professeur p = (Professeur) PROFESSEURS.getSelectionModel().getSelectedItem();
            if(p!=null) {
                metier.deleteProfesseur(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Modifié avec succès");
                alert.setContentText("Le professeur "+p.getNom()+" "+p.getPrenom()+" a été supprimé avec succès!");
                alert.show();
                liste.clear();
                liste.addAll(metier.getAllProfesseurs());
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Aucun élément selectioné");
                alert.setContentText("Veuillez selectioner un élément");
                alert.show();
            }

        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Erreur");
            alert.show();
        }
    }
    public void getByDepart(ActionEvent actionEvent) {
        liste.clear();
        liste.addAll(metier.getProfesseursByDepart(Depart.getValue()));
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

}
