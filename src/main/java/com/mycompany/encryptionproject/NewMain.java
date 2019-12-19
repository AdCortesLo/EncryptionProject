/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.encryptionproject;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Adrian cortes
 */
public class NewMain
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        //Generar clave AES
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();
        
        //Generar pareja claves RSA
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();

        PublicKey puKey = keyPair.getPublic();
        PrivateKey prKey = keyPair.getPrivate();


        
        String plainText = "hola";
        System.out.println("Mensaje: " + plainText);
        
        //Calcular hash del mensaje
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);
        System.out.println("Hash calculado inicialmente: " + encoded);
        
        //Cifrar hash con clave privada del emisor       
        Cipher aesCipher = Cipher.getInstance("RSA");
        aesCipher.init(Cipher.ENCRYPT_MODE, prKey);
        byte[] byteCipherText = aesCipher.doFinal(hash);

        //Encriptar hash encriptado-RSA con AES
        Cipher hashRSACipher = Cipher.getInstance("AES");
        hashRSACipher.init(Cipher.PUBLIC_KEY, secKey);
        byte[] encryptedHashRSA = hashRSACipher.doFinal(byteCipherText);

        //Encriptar AES con pública del receptor
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.PUBLIC_KEY, puKey);
        byte[] encryptedAESKey = cipher.doFinal(secKey.getEncoded());
        
        // Desencriptar clave AES con RSA privada del receptor
        cipher.init(Cipher.PRIVATE_KEY, prKey);
        byte[] decryptedAESKey = cipher.doFinal(encryptedAESKey);

        //Convert bytes to AES SecertKey
        SecretKey originalKey = new SecretKeySpec(decryptedAESKey, 0, decryptedAESKey.length, "AES");
        
        //Desencriptar hash-RSA del mensaje con AES
        Cipher aesCipherDecrypt = Cipher.getInstance("AES");
        aesCipherDecrypt.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] byteHashEncrypted = aesCipherDecrypt.doFinal(encryptedHashRSA);
        
        //Desencriptar hash con pública del emisor
        Cipher CipherDecrypt = Cipher.getInstance("RSA");
        CipherDecrypt.init(Cipher.DECRYPT_MODE, puKey);
        byte[] byteHashDecrypted = CipherDecrypt.doFinal(byteHashEncrypted);
        
        //Hash desencriptado
        String decoded = Base64.getEncoder().encodeToString(byteHashDecrypted);
        System.out.println("Hash después de desencriptar: " + decoded);
        
        System.out.println("");
        if (encoded.equals(decoded)) {
            System.out.println("Los hash coinciden");
        }
        else {
            System.out.println("Los hash no coinciden");
        }
        //String plainTextDecrypted = new String(textoSinHash);
        //System.out.println("Texto original: " + plainTextDecrypted);
    }
}
