package bankmanager.restservice;

import bankmanager.datamanagement.OperationBancaire;
import bankmanager.datamanagement.User;
import bankmanager.problemdomain.OperationBancaireApp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class APIFunction {

    private static final String APIURL = "http://localhost:8080";
    private static final String OPERATION_URL = APIURL + "/OperationBancaire";
    private static final String CONNEXION_URL = APIURL + "/Connexion";

    public static List<OperationBancaireApp> getAllOperations() throws IOException {

        String apiUrlGetAllOperation =  OPERATION_URL + "/RecupererOperationsBancaire";

        // j'utilise listBankOperationData pour récupérer mes données et ListBankOperation pour avoir mes données dans
        // une classe
        List<OperationBancaire> listBankOperationData = new ArrayList<OperationBancaire>();;
        List<OperationBancaireApp> ListBankOperation = new ArrayList<OperationBancaireApp>();

        URL obj = new URL(apiUrlGetAllOperation);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success

            InputStream inputStream = connection.getInputStream();

            String bodyResponse = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            //listBankOperationData = JSONToObject.JSONToListOfOperation(bodyResponse);
            listBankOperationData = JsonConverter.JSONToListOfOperation(bodyResponse);

            // Je remplit ma list d'opération bancaire
            for(OperationBancaire anOperation : listBankOperationData) {

                OperationBancaireApp actualBankOperation = new OperationBancaireApp();

                actualBankOperation.setIdoperationBancaire(anOperation.getIdoperationBancaire());
                actualBankOperation.setIdcategorieOperation(anOperation.getIdcategorieOperation());
                actualBankOperation.setDate(anOperation.getDateOperation());
                actualBankOperation.setLibelle(anOperation.getLibelle());
                actualBankOperation.setCredit(anOperation.getMontantCredit());
                actualBankOperation.setDebit(anOperation.getMontantDebit());

                ListBankOperation.add(actualBankOperation);
            }

        } else {
            System.out.println("GET request did not work.");
        }

        return ListBankOperation;

    }

    public static void addListOfOperation(List<OperationBancaire> listBankOperation) throws IOException {

        String apiUrlAddOperation = OPERATION_URL + "/AjouterListeOperation";

        //String JSONListOfOperation = ObjectToJSON.ListOfOperationToJSON(listBankOperation);
        String JSONListOfOperation = JsonConverter.ListOfOperationToJSON(listBankOperation);

        URL obj = new URL(apiUrlAddOperation);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");

        // For POST only - START
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();

        // ATTENTION !!!! l'encodage est nécessaire sinon côté API lorsque je transforme de nouveau mon JSON en object,
        // j'obtiens une erreur de jackson car le JSON perd des balises fermantes apparemment
        // problème de décodage
        String encodedParameter = Base64.getEncoder().encodeToString(JSONListOfOperation.getBytes());

        String parameter = "JSONListOfOperation=" + encodedParameter;

        outputStream.write(parameter.getBytes());
        outputStream.flush();
        outputStream.close();
        // For POST only - END

        int responseCode = connection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request did not work.");
        }
    }

    // besoin de crypter et encoder car info sensible
    public static User signIn(String username, String hashPassword) throws IOException {

        String apiUrlSignIn = CONNEXION_URL + "/SignIn";

        URL obj = new URL(apiUrlSignIn);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("POST");

        // For POST only - START
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();

        String encryptUsername = EncodeDecodeEncryptDecrypt.encryptAndEncodeData(username);
        String encryptPassword = EncodeDecodeEncryptDecrypt.encryptAndEncodeData(hashPassword);

        String parameter = "nomUtilisateur=" + encryptUsername;
        parameter += "&password=" + encryptPassword;

        outputStream.write(parameter.getBytes());
        outputStream.flush();
        outputStream.close();
        // For POST only - END

        int responseCode = connection.getResponseCode();

        System.out.println("POST Response Code :: " + responseCode);

        boolean connectionWithSuccess = false;
        User userReturn = new User();

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            String JSONUser = EncodeDecodeEncryptDecrypt.decryptAndDecodeData(response.toString());

            userReturn = JsonConverter.JSONToUser(JSONUser);

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request did not work.");
        }

        return userReturn;
    }
/*
    private static String getEncodedParameter(String parameterToEncode) {

        String encodedParameter = Base64.getEncoder().encodeToString(parameterToEncode.getBytes());

        return encodedParameter;
    }

    public static String encryptAndEncodeData(String dataToEncrypt) {

        String encryptedAndEncodedData = "";

        try
        {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            SecretKey secretKey = getSecretKey();

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedData                = cipher.doFinal(dataToEncrypt.getBytes());

            encryptedAndEncodedData             = Base64.getEncoder().encodeToString(encryptedData);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return encryptedAndEncodedData;
    }

    public static String decryptAndDecodeData(String dataToDecrypt) {

        String decryptAndDecodeData = "";

        try
        {

            SecretKey secretKey = getSecretKey();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptData = cipher.doFinal(Base64.getDecoder().decode(dataToDecrypt));
            decryptAndDecodeData = decryptData.toString();

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return decryptAndDecodeData;
    }

    public static SecretKey getSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {

        String key = "EBANKCRYPT";

        SecretKeyFactory secretKeyFactory   = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec keySpec                     = new PBEKeySpec(key.toCharArray());
        SecretKey secretKey                 = new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(), "AES");

        return secretKey;
    }
*/
}
