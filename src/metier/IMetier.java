package metier;

import java.util.List;

public interface IMetier {
    //Professeur
    List<Professeur> getAllProfesseurs();
    List<Professeur> getProfesseursByKeyWord(String keyword);
    boolean getProfesseur(Professeur p);
    void addProfesseur(Professeur prof);
    void deleteProfesseur(Professeur prof);
    void updateProfesseur(Professeur prof, Professeur newData);
    void affectToDepart(Professeur professeur, Departement Depart);

    /* Departement */
    List<Departement> getAllDepartement();
    boolean getDepartement(String Nom);
    List<Departement> getDepartementByName(String Nom);


    List<Professeur> getProfesseursByDepart(Departement departement);

    void addDepartement(Departement depart);
    void updateDepartement(Departement depart, Departement newData);
    void deleteDepartement(Departement depart);

    Departement getDepartementInfo(String Nom);

    int getIDDepartement(String Nom);

    List<String> getAllNomDepartement();
}
