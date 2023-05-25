package bankmanager.problemdomain;

import java.util.Date;

public class OperationBancaireApp {

    private Integer idoperationBancaire;
    private Integer idcategorieOperation;
    private Date dateOperation;
    private String libelle;
    private double montantDebit;
    private double montantCredit;

    public OperationBancaireApp() {
        this.idoperationBancaire = null;
        this.idcategorieOperation = null;
        this.dateOperation = null;
        this.libelle = "";
        this.montantDebit = 0;
        this.montantCredit = 0;
    }

    public OperationBancaireApp(Integer IDOperationBancaire, Integer IDCategorieOperation, Date date, String libelle, double debit, double credit) {
        this.idoperationBancaire = IDOperationBancaire;
        this.idcategorieOperation = IDCategorieOperation;
        this.dateOperation = date;
        this.libelle = libelle;
        this.montantDebit = debit;
        this.montantCredit = credit;
    }
    public Integer getIdoperationBancaire() {return idoperationBancaire;}
    public void setIdoperationBancaire(Integer IDOperationBancaire) {this.idoperationBancaire = IDOperationBancaire;}
    public Integer getIdcategorieOperation() {return idcategorieOperation;}
    public void setIdcategorieOperation(Integer IDCategorieOperation) {this.idcategorieOperation = IDCategorieOperation;}
    public Date getDate() {
        return dateOperation;
    }
    public void setDate(Date date) {
        this.dateOperation = date;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public double getDebit() {
        return montantDebit;
    }
    public void setDebit(double debit) {
        this.montantDebit = debit;
    }
    public double getCredit() {
        return montantCredit;
    }
    public void setCredit(double credit) {
        this.montantCredit = credit;
    }

    public boolean estUnDebit(OperationBancaireApp uneOperation) {
        if (uneOperation.getDebit() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
