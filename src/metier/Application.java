package metier;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        IMetier metier=new MetierImpl();
        Departement d1 = new Departement("Math Info");
        Departement d2 = new Departement("Electrique");
        Departement d3 = new Departement("Economique");
        Departement d4 = new Departement("Mecanique");
        Departement d5 = new Departement("Mecanique");
        metier.addDepartement(d1);
        metier.addDepartement(d2);
        metier.addDepartement(d3);
        metier.addDepartement(d4);
        metier.addDepartement(d5);
        List<Departement> departements=metier.getAllDepartement();
        for(Departement d : departements){
            System.out.println(d.getId_depart()+" "+d.getNom());
        }


        try {
            Date date = Date.valueOf(LocalDate.of(1990,01,19));
            Date date1 = Date.valueOf(LocalDate.of(1970,11,19));

            Professeur p1 = new Professeur("ALAA", "Noureddine", "000", "Marrakech", "062254", "n.ala@uca.ac.ma", date.toLocalDate(), d1);
            Professeur p2 = new Professeur("QBADOU", "Mohamed", "JK12k452", "Mohammedia", "061793210", "q.mohnamed@enset-media.ac.ma", date1.toLocalDate(), d2);
            metier.addProfesseur(p1);
            metier.addProfesseur(p2);
            List<Professeur> professeurs = metier.getAllProfesseurs();
            for (Professeur p : professeurs) {
                System.out.println(p.getId_prof() + " " + p.getCin());
            }
            for (Departement d : departements){
                System.out.println(d.getId_depart()+ " " + d.getNom());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
