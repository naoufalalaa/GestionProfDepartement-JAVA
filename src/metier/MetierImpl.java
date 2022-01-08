package metier;

import DAO.SignletonConnectionDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MetierImpl implements IMetier{
    @Override
    public void addProfesseur(Professeur professeur) {
        Connection conn= SignletonConnectionDB.getConnection();
        try {
            if(!getProfesseur(professeur)) {
                PreparedStatement pstm = conn.prepareStatement("insert into Professeur(NOM,PRENOM,CIN,ADDRESS,TELEPHONE,EMAIL,ID_DEPART,DATERECRUTEMENT) values (?,?,?,?,?,?,?,?)");
                pstm.setString(1, professeur.getNom());
                pstm.setString(2, professeur.getPrenom());
                pstm.setString(3, professeur.getCin());
                pstm.setString(4, professeur.getAddress());
                pstm.setString(5, professeur.getTelephone());
                pstm.setString(6, professeur.getEmail());
                pstm.setInt(7, professeur.getDepartement().getId_depart());
                pstm.setDate(8, Date.valueOf(professeur.getDate_recrutement()));
                pstm.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteProfesseur(Professeur prof) {
        Connection conn=SignletonConnectionDB.getConnection();
        try{
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM Professeur WHERE ID_PROF = ?");
            pstm.setInt(1,prof.getId_prof());
            pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfesseur(Professeur prof, Professeur newData) {
        Connection conn=SignletonConnectionDB.getConnection();
        try{
            PreparedStatement pstm = conn.prepareStatement("UPDATE Professeur SET NOM = ? , PRENOM = ? , CIN = ? , ADDRESS = ? , TELEPHONE = ? , EMAIL = ? , DATERECRUTEMENT = ? WHERE ID_PROF = ?");
            pstm.setString(1,newData.getNom());
            pstm.setString(2,newData.getPrenom());
            pstm.setString(3,newData.getCin());
            pstm.setString(4,newData.getAddress());
            pstm.setString(5,newData.getTelephone());
            pstm.setString(6,newData.getEmail());
            pstm.setDate(7, Date.valueOf(newData.getDate_recrutement()));
            pstm.setInt(8,prof.getId_prof());
            pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void affectToDepart(Professeur professeur, Departement Depart) {
        Professeur newProf = professeur;
        newProf.setDepartement(Depart);
        updateProfesseur(professeur,newProf);
    }

    @Override
    public List<Professeur> getAllProfesseurs() {
        Connection conn=SignletonConnectionDB.getConnection();
        List<Professeur> professeurs=new ArrayList<>();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Professeur");
            ResultSet rs= pstm.executeQuery();
            while (rs.next()){
                PreparedStatement Dep=conn.prepareStatement("select * from Departement WHERE ID_DEPART = "+rs.getInt("ID_DEPART"));
                ResultSet depINFO = Dep.executeQuery();
                while(depINFO.next()){
                    Departement dept = new Departement(depINFO.getInt("ID_DEPART"),depINFO.getString("NOM"));
                    Professeur professeur=new Professeur(rs.getInt("ID_PROF"),rs.getString("NOM"),rs.getString("PRENOM"), rs.getString("CIN"),rs.getString("ADDRESS"),rs.getString("TELEPHONE"),rs.getString("EMAIL"),rs.getDate("DATERECRUTEMENT").toLocalDate(),dept);
                    professeurs.add(professeur);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return professeurs;
    }

    @Override
    public boolean getProfesseur(Professeur p) {
        Connection conn=SignletonConnectionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Professeur WHERE TELEPHONE = '"+p.getTelephone()+"' OR CIN = '"+p.getCin()+"' OR EMAIL = '"+p.getEmail()+"'");
            ResultSet rs= pstm.executeQuery();
            while(rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Professeur> getProfesseursByDepart(Departement departement) {
        Connection conn=SignletonConnectionDB.getConnection();
        List<Professeur> professeurs=new ArrayList<>();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Departement where ID_DEPART = ?");
            pstm.setInt(1,departement.getId_depart());
            ResultSet rs= pstm.executeQuery();
            while (rs.next()){
                PreparedStatement Dep=conn.prepareStatement("select * from Professeur WHERE ID_DEPART = "+rs.getInt("ID_DEPART"));
                ResultSet depINFO = Dep.executeQuery();
                while(depINFO.next()) {
                    Departement dept = new Departement(depINFO.getInt("ID_DEPART"), depINFO.getString("NOM"));
                    Professeur professeur = new Professeur(depINFO.getInt("ID_PROF"), depINFO.getString("NOM"), depINFO.getString("PRENOM"), depINFO.getString("CIN"), depINFO.getString("ADDRESS"), depINFO.getString("TELEPHONE"), depINFO.getString("EMAIL"), depINFO.getDate("DATERECRUTEMENT").toLocalDate(), dept);
                    professeurs.add(professeur);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return professeurs;
    }

    @Override
    public List<Professeur> getProfesseursByKeyWord(String motCle) {
        Connection conn=SignletonConnectionDB.getConnection();
        List<Professeur> professeurs=new ArrayList<>();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from professeur where NOM like ? OR PRENOM LIKE ? OR EMAIL LIKE ? OR CIN LIKE ? OR TELEPHONE LIKE ?");
            pstm.setString(1,"%"+motCle+"%");
            pstm.setString(2,"%"+motCle+"%");
            pstm.setString(3,"%"+motCle+"%");
            pstm.setString(4,"%"+motCle+"%");
            pstm.setString(5,"%"+motCle+"%");
            ResultSet rs= pstm.executeQuery();
            while (rs.next()){
                PreparedStatement Dep=conn.prepareStatement("select * from Departement WHERE ID_DEPART = "+rs.getInt("ID_DEPART"));
                ResultSet depINFO= Dep.executeQuery();
                while(depINFO.next()) {
                    Departement dept = new Departement(depINFO.getInt("ID_DEPART"), depINFO.getString("NOM"));
                    Professeur professeur = new Professeur(rs.getInt("ID_PROF"), rs.getString("NOM"), rs.getString("PRENOM"), rs.getString("CIN"), rs.getString("ADDRESS"), rs.getString("TELEPHONE"), rs.getString("EMAIL"), rs.getDate("DATERECRUTEMENT").toLocalDate(), dept);
                    professeurs.add(professeur);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return professeurs;
    }

    @Override
    public void addDepartement(Departement p) {
        Connection conn=SignletonConnectionDB.getConnection();
        try {
            if(!getDepartement(p.getNom())){
                PreparedStatement pstm=conn.prepareStatement("insert into Departement(NOM) values (?)");
                pstm.setString(1,p.getNom());
                pstm.executeUpdate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateDepartement(Departement depart, Departement newData) {
        Connection conn = SignletonConnectionDB.getConnection();
        try{
            PreparedStatement pstm = conn.prepareStatement("UPDATE Departement SET NOM = ? WHERE ID_DEPART = ?");
            pstm.setString(1,newData.getNom());
            pstm.setInt(2,depart.getId_depart());
            pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDepartement(Departement depart) {
        Connection conn = SignletonConnectionDB.getConnection();
        try{
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM Departement WHERE ID_DEPART = ?");
            pstm.setInt(1,depart.getId_depart());
            pstm.executeUpdate();
            PreparedStatement pt= conn.prepareStatement("DELETE FROM Professeur WHERE ID_DEPART = ?");
            pt.setInt(1,depart.getId_depart());
            pt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Departement> getAllDepartement() {
        Connection conn=SignletonConnectionDB.getConnection();
        List<Departement> departements=new ArrayList<>();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Departement");
            ResultSet rs= pstm.executeQuery();
            while (rs.next()){
                Departement p=new Departement(rs.getInt("ID_DEPART"),rs.getString("NOM"));
                departements.add(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  departements;
    }

    @Override
    public boolean getDepartement(String Nom) {
        Connection conn=SignletonConnectionDB.getConnection();
        Departement departement = null;
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Departement WHERE NOM = '"+Nom+"'");
            ResultSet rs= pstm.executeQuery();
            while(rs.next()){
                departement = new Departement(rs.getString("NOM"));
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Departement> getDepartementByName(String Nom) {
        Connection conn=SignletonConnectionDB.getConnection();
        List<Departement> departement = new ArrayList<>();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Departement WHERE NOM LIKE '%"+Nom+"%'");
            ResultSet rs= pstm.executeQuery();
            while(rs.next()){
                departement.add(new Departement(rs.getString("NOM")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return departement;
    }

    @Override
    public Departement getDepartementInfo(String Nom) {
        Connection conn=SignletonConnectionDB.getConnection();
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Departement WHERE NOM LIKE '"+Nom+"'");
            ResultSet rs= pstm.executeQuery();
            if(rs.next()){
                Departement departement = new Departement(rs.getInt("ID_DEPART"), rs.getString("NOM"));
                return departement;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Departement();
    }

    @Override
    public int getIDDepartement(String Nom) {
        Connection conn=SignletonConnectionDB.getConnection();
        Departement departement = null;
        try {
            PreparedStatement pstm=conn.prepareStatement("select * from Departement WHERE NOM = '"+Nom+"'");
            ResultSet rs= pstm.executeQuery();
            while(rs.next()){
                departement = new Departement(rs.getString("NOM"));
                return rs.getInt("ID_DEPART");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<String> getAllNomDepartement(){
        Connection conn=SignletonConnectionDB.getConnection();
        List<String> departements=new ArrayList<>();
        try {
            PreparedStatement pstm=conn.prepareStatement("select NOM from Departement");
            ResultSet rs= pstm.executeQuery();
            while (rs.next()){
                departements.add(rs.getString("NOM"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return departements;
    }
}
