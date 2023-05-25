package bankmanager.datamanagement;

import bankmanager.problemdomain.OperationBancaireApp;

import java.util.Date;
import java.util.Objects;

// Le nom des rubriques doit correspondre au nom des rubriques renvoyé par le JSON sinon jackson ne peut pas convertir
// le JSON en class d'OperationBancaire
public class OperationBancaire {
    private Integer idoperationBancaire;
    private Integer idcategorieOperation;
    private String libelle;
    private Date dateOperation;
    private double montantDebit;
    private double montantCredit;

    public OperationBancaire() {
        this.idoperationBancaire = null;
        this.idcategorieOperation = null;
        this.dateOperation = null;
        this.libelle = "";
        this.montantDebit = 0;
        this.montantCredit = 0;
    }
    public OperationBancaire(Integer idoperationBancaire, Integer idcategorieOperation, String libelle, Date dateOperation, double montantDebit, double montantCredit) {
        this.idoperationBancaire = idoperationBancaire;
        this.idcategorieOperation = idcategorieOperation;
        this.libelle = libelle;
        this.dateOperation = dateOperation;
        this.montantDebit = montantDebit;
        this.montantCredit = montantCredit;
    }

    public Integer getIdoperationBancaire() {
        return idoperationBancaire;
    }

    public Integer getIdcategorieOperation() {
        return idcategorieOperation;
    }

    public String getLibelle() {
        return libelle;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public double getMontantDebit() {
        return montantDebit;
    }

    public double getMontantCredit() {
        return montantCredit;
    }

    public void setIdoperationBancaire(Integer IDOperationBancaire) {this.idoperationBancaire = IDOperationBancaire;}
    public void setIdcategorieOperation(Integer IDCategorieOperation) {this.idcategorieOperation = IDCategorieOperation;}
    public void setDate(Date date) {
        this.dateOperation = date;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public void setDebit(double debit) {
        this.montantDebit = debit;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (OperationBancaire) obj;
        return Objects.equals(this.idoperationBancaire, that.idoperationBancaire) &&
                Objects.equals(this.idcategorieOperation, that.idcategorieOperation) &&
                Objects.equals(this.libelle, that.libelle) &&
                Objects.equals(this.dateOperation, that.dateOperation) &&
                Double.doubleToLongBits(this.montantDebit) == Double.doubleToLongBits(that.montantDebit) &&
                Double.doubleToLongBits(this.montantCredit) == Double.doubleToLongBits(that.montantCredit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idoperationBancaire, idcategorieOperation, libelle, dateOperation, montantDebit, montantCredit);
    }

    @Override
    public String toString() {
        return "OperationBancaire[" +
                "idoperationBancaire=" + idoperationBancaire + ", " +
                "idcategorieOperation=" + idcategorieOperation + ", " +
                "libelle=" + libelle + ", " +
                "dateOperation=" + dateOperation + ", " +
                "montantDebit=" + montantDebit + ", " +
                "montantCredit=" + montantCredit + ']';
    }
}
