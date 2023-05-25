package com.ebank.application.managedata;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class OperationBancaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer IDOperationBancaire;

    private Date DateOperation;

    private String Libelle;

    private double MontantDebit;

    private double MontantCredit;

    private Integer IDCategorieOperation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idutilisateur")
    private User user;

    public void setIDOperationBancaire(Integer IDOperationBancaire) {
        this.IDOperationBancaire = IDOperationBancaire;
    }

    public void setDateOperation(Date dateOperation) {
        DateOperation = dateOperation;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    public void setMontantDebit(double montantDebit) {
        MontantDebit = montantDebit;
    }

    public void setMontantCredit(double montantCredit) {
        MontantCredit = montantCredit;
    }

    public void setIDCategorieOperation(Integer IDCategorieOperation) {
        this.IDCategorieOperation = IDCategorieOperation;
    }

    public Integer getIDOperationBancaire() {
        return IDOperationBancaire;
    }

    public Date getDateOperation() {
        return DateOperation;
    }

    public String getLibelle() {
        return Libelle;
    }

    public double getMontantDebit() {
        return MontantDebit;
    }

    public double getMontantCredit() {
        return MontantCredit;
    }

    public Integer getIDCategorieOperation() {
        return IDCategorieOperation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
