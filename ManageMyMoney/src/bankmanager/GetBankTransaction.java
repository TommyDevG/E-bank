package bankmanager;

import bankmanager.datamanagement.OperationBancaire;
import bankmanager.problemdomain.OperationBancaireApp;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GetBankTransaction {

    private String filePath;
    private String fileName;

    public GetBankTransaction(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public List<OperationBancaire> GetListOfTransaction() {

        List<OperationBancaire> listOperationBancaire = new ArrayList<OperationBancaire>();

        boolean isFileValid = isValidFile("xlsx");

        if (isFileValid) {
            try
            {
                //File file = new File("C:\\Users\\thoma\\Documents\\CA20230404_193746.xlsx");   //creating a new file instance
                File file = new File(this.filePath);   //creating a new file instance
                FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file
                //creating Workbook instance that refers to .xlsx file
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object
                Iterator<Row> iterateur = sheet.iterator();    //iterating over excel file

                while (iterateur.hasNext())
                {
                    Row ligne = iterateur.next();

                    // les opérations commencent à partir de la ligne 10 (Attention dans un xslx la première ligne à pour indice : 0)
                    if (ligne.getRowNum() < 10) {
                        continue;
                    }

                    OperationBancaireApp actualBankOperation = new OperationBancaireApp();

                    Iterator<Cell> celluleIterateur = ligne.cellIterator();   //iterating over each column

                    // Je veux travailler avec le format anglais qui est souvent plus utilisé dans la programmation
                    SimpleDateFormat englishFormat = new SimpleDateFormat("yyyyMMdd");

                    while (celluleIterateur.hasNext())
                    {
                        Cell cellule = celluleIterateur.next();


                        switch (cellule.getColumnIndex()) {
                            case 0: // Date opération
                                String dateWithGoodFormat = englishFormat.format(cellule.getDateCellValue());
                                Date dateOperation = englishFormat.parse(dateWithGoodFormat);
                                actualBankOperation.setDate(dateOperation);
                                break;
                            case 1: // Libellé opération
                                actualBankOperation.setLibelle(cellule.getStringCellValue());
                                break;
                            case 2: // débit opération
                                actualBankOperation.setDebit(cellule.getNumericCellValue());
                                break;
                            case 3: // crédit opération
                                actualBankOperation.setCredit(cellule.getNumericCellValue());
                                break;
                            default:
                        }
                    }

                    // Je veux récupérer une date car elle va être transformer en String (convertisseur Object to JSON)
                    // et ne pas avoir le bon format
                    Date dateOperation = actualBankOperation.getDate();
                    String libelle = actualBankOperation.getLibelle();
                    double credit = actualBankOperation.getCredit();
                    double debit = actualBankOperation.getDebit();

                    OperationBancaire bankOperationData = new OperationBancaire(0, 0, libelle, dateOperation, debit, credit);

                    listOperationBancaire.add(bankOperationData);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return listOperationBancaire;
    }

    public String getExtension(String fileName) {
        String extension = "";

        if (fileName != "" && fileName.contains(".")) {
            int indexOfExtension = fileName.indexOf(".");
            int fileNameLength = fileName.length();

            // + 1 pour ne pas avoir le point
            extension = fileName.substring(indexOfExtension + 1, fileNameLength);
        } else {
            // Je print le fait que le fichier ne soit pas valable
            if (fileName == "") {
                System.out.println("Le nom du fichier est vide");
            } else {
                System.out.println("Le nom du fichier n'a pas le bon format" + fileName);
            }
        }

        return extension;
    }

    public boolean isValidFile(String validExtension) {
        String extension = getExtension(fileName);

        // --> extension == validExtension --> renvoi faux même si c'est la même chaine...
        //if (extension == validExtension) {
        if (extension.equals(validExtension)) {
            return true;
        } else {
            return false;
        }
    }

}
