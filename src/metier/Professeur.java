package metier;

import java.io.Serializable;
import java.time.LocalDate;

public class Professeur implements Serializable {
    private int id_prof;
    private String nom;
    private String prenom;
    private String cin;
    private String address;
    private String telephone;
    private String email;
    private LocalDate date_recrutement;
    private Departement departement;

    public Professeur(String nom, String prenom, String cin, String address, String telephone, String email, LocalDate date_recrutement, Departement departement) {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.date_recrutement = date_recrutement;
        this.departement = departement;
    }

    public Professeur(String nom, String prenom, String cin, String address, String telephone, String email, LocalDate date_recrutement) {
        this.id_prof = id_prof;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.date_recrutement = date_recrutement;
    }

    public Professeur() {
    }

    public Professeur(int id_prof, String nom, String prenom, String cin, String address, String telephone, String email, LocalDate date_recrutement, Departement departement) {
        this.id_prof = id_prof;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.date_recrutement = date_recrutement;
        this.departement = departement;
    }


    public int getId_prof() {
        return id_prof;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate_recrutement() {
        return date_recrutement;
    }

    public void setDate_recrutement(LocalDate date_recrutement) {
        this.date_recrutement = date_recrutement;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    @Override
    public String toString() {
        return "Professeur{" +
                "id_prof=" + id_prof +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", cin='" + cin + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", date_recrutement=" + date_recrutement +
                ", departement=" + departement.getId_depart() +
                '}';
    }
}
