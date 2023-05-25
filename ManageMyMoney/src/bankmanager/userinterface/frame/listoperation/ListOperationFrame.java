package bankmanager.userinterface.frame.listoperation;

import bankmanager.problemdomain.OperationBancaireApp;
import bankmanager.restservice.APIFunction;
import bankmanager.userinterface.frame.FileChooserFrame;
import bankmanager.userinterface.frame.listoperation.components.BankOperationTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListOperationFrame extends JFrame {

    private List<OperationBancaireApp> listBankOperation;
    private boolean DEBUG = false;
    private boolean ALLOW_COLUMN_SELECTION = false;
    private boolean ALLOW_ROW_SELECTION = true;
    private JTable bankOperationTable;
    private JScrollPane scrollPane;
    private JPanel panelMain;
    public ListOperationFrame() {
        super("Opération bancaires");
        //super(new GridLayout(1,0));

        final OperationBancaireApp[][] rowData;

        listBankOperation = getListBankOperation();

        BankOperationTableModel bankOperationModel = new BankOperationTableModel(listBankOperation);

        bankOperationTable = new JTable();

        bankOperationTable.setModel(bankOperationModel);

        bankOperationTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        bankOperationTable.setFillsViewportHeight(true);

        bankOperationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (ALLOW_ROW_SELECTION) { // true by default
            ListSelectionModel rowSM = bankOperationTable.getSelectionModel();
            rowSM.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    //Ignore extra messages.
                    if (e.getValueIsAdjusting()) return;

                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        System.out.println("No rows are selected.");
                    } else {
                        int selectedRow = lsm.getMinSelectionIndex();
                        System.out.println("Row " + selectedRow
                                + " is now selected.");
                    }
                }
            });
        } else {
            bankOperationTable.setRowSelectionAllowed(false);
        }

        if (ALLOW_COLUMN_SELECTION) { // false by default
            if (ALLOW_ROW_SELECTION) {
                //We allow both row and column selection, which
                //implies that we *really* want to allow individual
                //cell selection.
                bankOperationTable.setCellSelectionEnabled(true);
            }
            bankOperationTable.setColumnSelectionAllowed(true);
            ListSelectionModel colSM =
                    bankOperationTable.getColumnModel().getSelectionModel();
            colSM.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    //Ignore extra messages.
                    if (e.getValueIsAdjusting()) return;

                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        System.out.println("No columns are selected.");
                    } else {
                        int selectedCol = lsm.getMinSelectionIndex();
                        System.out.println("Column " + selectedCol
                                + " is now selected.");
                    }
                }
            });
        }

        if (DEBUG) {
            bankOperationTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(bankOperationTable);
                }
            });
        }
        panelMain = new JPanel();

        //Create the scroll pane and add the table to it.
        scrollPane = new JScrollPane(bankOperationTable);

        //Add the scroll pane to this panel.
        panelMain.add(scrollPane);
    }

    private List<OperationBancaireApp> getListBankOperation() {
        List<OperationBancaireApp> listBankOperation = new ArrayList<>();

        try {
            APIFunction.getAllOperations();
        } catch(IOException ioException) {
            System.out.println(ioException.toString());
            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la récupération des opérations bancaires.\n" + ioException.toString());
        }

        return listBankOperation;
    }
    private void printDebugData(JTable table) {

        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ouverture de la fenêtre pour lister les opérations.\n" + e.toString());
        }

        //Create and set up the window.
        ListOperationFrame frame = new ListOperationFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(frame.panelMain);
        //Display the window.
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
