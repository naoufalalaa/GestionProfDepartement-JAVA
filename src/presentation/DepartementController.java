package presentation;

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
import javafx.stage.Modality;
import javafx.stage.Stage;
import metier.Departement;
import metier.IMetier;
import metier.MetierImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DepartementController implements Initializable {
    static Departement selectedItem;
    @FXML
    private TableView<Departement> DEPARTEMENT;

    @FXML
    private TableColumn<Departement,String> NOM;
    static ObservableList<Departement> liste= FXCollections.observableArrayList();
    private IMetier metier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NOM.setCellValueFactory(new PropertyValueFactory<>("nom"));

        metier=new MetierImpl();
        liste.clear();
        liste.addAll(metier.getAllDepartement());
        DEPARTEMENT.setItems(liste);
    }
    @FXML
    private TextField search;

    public void Search(){
        String element = search.getText();
        liste.clear();
        liste.addAll(metier.getDepartementByName(element));
        DEPARTEMENT.setItems(liste);
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

    public void goToProfesseur(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ProfesseursView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Professeurs - Gestion");
            stage.setResizable(false);
            stage.show();
        }catch (Exception er){
            er.printStackTrace();
        }
    }

    public void updateDepartement(ActionEvent actionEvent){
        try {
            int index = DEPARTEMENT.getSelectionModel().getSelectedIndex();
            this.selectedItem = (Departement) DEPARTEMENT.getSelectionModel().getSelectedItem();
            if(selectedItem != null) {
                System.out.println(selectedItem);
                Parent root = FXMLLoader.load(getClass().getResource("UpdateDepartementView.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Modifier Département");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
                stage.setResizable(false);
                stage.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Aucun élément selectioné");
                alert.setContentText("Veuillez selectioner un élément");
                alert.show();
            }
        }catch (Exception er){
            er.printStackTrace();
        }
    }

    public void ajouterDepartement(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AjouerDepartementView.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter Département");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)actionEvent.getSource()).getScene().getWindow() );
            stage.setResizable(false);
            stage.show();
        }catch (Exception er){
            er.printStackTrace();
        }
    }

    public void deleteDepartement(){
        try{
            int index = DEPARTEMENT.getSelectionModel().getSelectedIndex();
            Departement select = (Departement) DEPARTEMENT.getSelectionModel().getSelectedItem();
            if(select!=null) {
                metier.deleteDepartement(select);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Élément supprimé avec succès");
                alert.setContentText("Vous avez supprimé un élément, l'opération est terminée avec succès...");
                alert.show();
                liste.clear();
                liste.addAll(metier.getAllDepartement());
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Aucun élément selectioné");
                alert.setContentText("Veuillez selectioner un élément");
                alert.show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
