package bankmanager.userinterface.frame.listoperation.components;

import bankmanager.problemdomain.OperationBancaireApp;
import bankmanager.restservice.APIFunction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BankOperationTableModel extends AbstractTableModel {

    // Constant pour avoir l'index des colonnes
    static final int COLUMN_IDOPERATION = 0;
    static final int COLUMN_IDCATEGORIE = 1;
    static final int COLUMN_DATE        = 2;
    static final int COLUMN_LIBELLE     = 3;
    static final int COLUMN_CREDIT      = 4;
    static final int COLUMN_DEBIT       = 5;

    private static final String[] LIST_COLUMN_NAMES = {
            "IDOperationBancaire",
            "IDCategorieOperation",
            "Date",
            "Libellé",
            "Montant crédit",
            "Montant débit"
    };

    private String[] ListColumnNames;

    private List<OperationBancaireApp> listBankOperation;

    public BankOperationTableModel(List<OperationBancaireApp> listBankOperation) {
        this.listBankOperation = listBankOperation;
    }

    @Override
    public int getColumnCount() {
        return LIST_COLUMN_NAMES.length;
    }

    @Override
    public int getRowCount() {
        return listBankOperation.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return LIST_COLUMN_NAMES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case COLUMN_IDOPERATION -> listBankOperation.get(rowIndex).getIdoperationBancaire();
            case COLUMN_IDCATEGORIE -> listBankOperation.get(rowIndex).getIdcategorieOperation();
            case COLUMN_DATE        -> listBankOperation.get(rowIndex).getDate();
            case COLUMN_LIBELLE     -> listBankOperation.get(rowIndex).getLibelle();
            case COLUMN_CREDIT      -> listBankOperation.get(rowIndex).getCredit();
            case COLUMN_DEBIT       -> listBankOperation.get(rowIndex).getDebit();
            default -> "-";
        };
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        if (getValueAt(0, columnIndex) != null) {
            return getValueAt(0, columnIndex).getClass();
        } else {
            return Object.class;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(OperationBancaireApp value, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COLUMN_IDOPERATION -> listBankOperation.get(rowIndex).setIdoperationBancaire(value.getIdoperationBancaire());
            case COLUMN_IDCATEGORIE -> listBankOperation.get(rowIndex).setIdcategorieOperation(value.getIdcategorieOperation());
            case COLUMN_DATE        -> listBankOperation.get(rowIndex).setDate(value.getDate());
            case COLUMN_LIBELLE     -> listBankOperation.get(rowIndex).setLibelle(value.getLibelle());
            case COLUMN_CREDIT      -> listBankOperation.get(rowIndex).setCredit(value.getCredit());
            case COLUMN_DEBIT       -> listBankOperation.get(rowIndex).setDebit(value.getDebit());
            default -> JOptionPane.showMessageDialog(null, "L'index de la colonne n°" + columnIndex + " ne correspond à aucune colonne");
        };
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void addValueInTable(OperationBancaireApp newOperation) {

        listBankOperation.add(newOperation);
    }

    private void addDataToTable() {

        List<OperationBancaireApp> listBankOperation = new ArrayList<OperationBancaireApp>();

        clearTable();

        try {
            listBankOperation = APIFunction.getAllOperations();
        } catch (IOException exception) {
            System.out.println(exception.toString());
            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du chargement de la table des opérations.\n" + exception.toString());
            return ;
        }

        if (!listBankOperation.isEmpty()) {
            for (OperationBancaireApp actualOperation : listBankOperation) {
                addValueInTable(actualOperation);
            }
        }
    }


    public void clearTable() {
        int numberOfRow = getRowCount();
        fireTableRowsDeleted(0, numberOfRow);
    }

}
