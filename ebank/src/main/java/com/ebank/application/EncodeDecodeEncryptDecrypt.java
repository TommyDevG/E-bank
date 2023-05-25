package com.ebank.application;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class EncodeDecodeEncryptDecrypt {

    public static String encodeData(String parameterToEncode) {

        String encodedParameter = Base64.getEncoder().encodeToString(parameterToEncode.getBytes(StandardCharsets.UTF_8));

        return encodedParameter;
    }

    // ma fonction encryptAndEncodeData a besoin d'encoder des bytes et pas une chaine
    private static String encodeData(byte[] parameterToEncode) {

        String encodedParameter = Base64.getEncoder().encodeToString(parameterToEncode);

        return encodedParameter;
    }

    public static String decodeData(String parameterToEncode) {
        // PAS de toString car le décodage ne fonctionne pas
        String decodeData = new String(Base64.getDecoder().decode(parameterToEncode));

        return decodeData;
    }

    private static byte[] decodeDataToByte(String parameterToEncode) {
        byte[] decodeData = Base64.getDecoder().decode(parameterToEncode);

        return decodeData;
    }

    public static String encryptAndEncodeData(String dataToEncrypt) {

        String encryptedAndEncodedData = "";

        try
        {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            SecretKey secretKey = getSecretKey();

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, generateIv());

            encryptedAndEncodedData = encodeData(cipher.doFinal(dataToEncrypt.getBytes(StandardCharsets.UTF_8)));

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
            cipher.init(Cipher.DECRYPT_MODE, secretKey, generateIv());
            byte[] decryptData = cipher.doFinal(Base64.getUrlDecoder().decode(dataToDecrypt));
            decryptAndDecodeData = new String(decryptData);

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        return decryptAndDecodeData;
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        // pas de random sinon manque la moitié des infos
        // Avant : thomasguillier@hotmail.fr
        // Après : ���P�ZF�C�E�|���otmail.fr
        //new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    public static SecretKey getSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {

        String key = "EBANKCRYPTAVEC16";

        SecretKeyFactory secretKeyFactory   = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];

        // Pas de random car je veux la même clé dans l'API !!!
        //random.nextBytes(salt);
        // obligé d'avoir un salage pour l'algorithme PBKDF2WithHmacSHA256
        KeySpec keySpec                     = new PBEKeySpec(key.toCharArray(), salt, 65536,256);
        SecretKey secretKey                 = new SecretKeySpec(secretKeyFactory.generateSecret(keySpec).getEncoded(), "AES");

        return secretKey;
    }
}
