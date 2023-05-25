package bankmanager.userinterface.frame;

import bankmanager.GetBankTransaction;
import bankmanager.datamanagement.OperationBancaire;
import bankmanager.restservice.APIFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileChooserFrame extends JFrame {

    private JPanel PanelMain = new JPanel();
    private JButton buttonRetrieveFile, buttonProcessFile;
    private JTextArea textAreaFileName, textAreaOperationsInfo;

    private JFileChooser fileChooser;
    private String filePath;
    private String fileName;

    public FileChooserFrame() {
        super("Traitement des fichiers");

        // La zone de texte doit être créer en premier car l'action listeners
        // en a besoin de s'en référer pour afficher le nom du fichier
        textAreaFileName = new JTextArea();
        textAreaFileName.setMargin(new Insets(5,5,5,5));
        textAreaFileName.setEditable(false);
        // Je veux par défaut afficher l'état actuel soit aucun fichier en cours
        textAreaFileName.setText("Aucun fichier");

        textAreaOperationsInfo = new JTextArea();
        textAreaOperationsInfo.setMargin(new Insets(5,5,5,5));
        textAreaOperationsInfo.setEditable(false);
        JScrollPane infoOpScrollPane = new JScrollPane(textAreaOperationsInfo);

        //Create a file chooser
        fileChooser = new JFileChooser();

        //Uncomment one of the following lines to try a different
        //file selection mode.  The first allows just directories
        //to be selected (and, at least in the Java look and feel,
        //shown).  The second allows both files and directories
        //to be selected.  If you leave these lines commented out,
        //then the default mode (FILES_ONLY) will be used.
        //
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        //Create the open button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        buttonRetrieveFile = new JButton("Récupérer historique des opérations");
        buttonRetrieveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int returnVal = fileChooser.showOpenDialog(FileChooserFrame.this);

                textAreaFileName.setText("");

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    //This is where a real application would open the file.
                    textAreaFileName.append(file.getName());

                    fileName = file.getName();
                    filePath = file.getAbsolutePath();
                } else {
                    //Dans ce cas l'utilisateur a annulé la récupération du fichier (clique sur croix)
                    // donc j'affiche que je n'ai aps de fichier
                    textAreaFileName.append("Aucun fichier");
                }
                //textAreaNomFichier.setCaretPosition(textAreaNomFichier.getDocument().getLength());
            }
        });

        buttonProcessFile = new JButton("Traiter le fichier");
        buttonProcessFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetBankTransaction getOperationController = new GetBankTransaction(filePath, fileName);
                List<OperationBancaire> listOperationBancaire = new ArrayList<OperationBancaire>();
                listOperationBancaire = getOperationController.GetListOfTransaction();

                textAreaOperationsInfo.setText("");

                try {
                    APIFunction.addListOfOperation(listOperationBancaire);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                /*
                for (OperationBancaire operationEnCours : listOperationBancaire) {
                    DateFormat formatFrancaisDate = new SimpleDateFormat("dd/MM/yyyy");
                    textAreaOperationsInfo.append(formatFrancaisDate.format(operationEnCours.getDate()) + "\t\t");
                    textAreaOperationsInfo.append(operationEnCours.getLibelle() + "\t\t");
                    if (operationEnCours.getDebit() != 0) {
                        textAreaOperationsInfo.append(operationEnCours.getDebit() + "\n");
                    } else {
                        textAreaOperationsInfo.append(operationEnCours.getCredit() + "\n");
                    }
                }
                */
            }
        });

        //For layout purposes, put the buttons in a separate panel
        PanelMain = new JPanel();
        JPanel contentPanel = new JPanel();

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        buttonRetrieveFile.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(buttonRetrieveFile);

        textAreaFileName.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(textAreaFileName);

        buttonProcessFile.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(buttonProcessFile);
        PanelMain.add(contentPanel);
        //Add the buttons and the log to this panel.
        //add(buttonPanel, BorderLayout.PAGE_START);
        //add(PanelMain, BorderLayout.CENTER);
        //add(infoOpScrollPane, BorderLayout.CENTER);


//        //For layout purposes, put the buttons in a separate panel
//        JPanel buttonPanel = new JPanel(); //use FlowLayout
//        buttonPanel.add(openButton);
//        buttonPanel.add(saveButton);
//
//        //Add the buttons and the log to this panel.
//        add(buttonPanel, BorderLayout.PAGE_START);
//        add(logScrollPane, BorderLayout.CENTER);
    }

    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        //Create and set up the window.
        FileChooserFrame frame = new FileChooserFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(frame.PanelMain);
        //Display the window.
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
