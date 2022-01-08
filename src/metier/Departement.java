package metier;

import java.io.Serializable;

public class Departement implements Serializable {
    private int id_depart;
    private String nom;

    public Departement(int id_depart, String nom) {
        this.id_depart = id_depart;
        this.nom = nom;
    }

    public Departement(String nom) {
        this.nom = nom;
    }
    public Departement(){
    }

    public int getId_depart() {
        return id_depart;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom ;
    }
}
