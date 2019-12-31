/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.encryptionproject;

import static com.mycompany.encryptionproject.Encriptar.getPrivate;
import static com.mycompany.encryptionproject.Encriptar.getPublic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Adrian
 */
public class Desencriptar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, Exception {
        // TODO code application logic here

        Cipher cipher = Cipher.getInstance("RSA");
        PrivateKey prKeyRe = getPrivate("PrivateKeyRECEPTOR.txt");
        cipher.init(Cipher.PRIVATE_KEY, prKeyRe);

        byte[] decryptedAESKey = cipher.doFinal(getFileInBytes(new File("AESEncriptada.txt")));

        //Convert bytes to AES SecertKey
        SecretKey AESKey = new SecretKeySpec(decryptedAESKey, 0, decryptedAESKey.length, "AES");

        //Desencriptar hash-RSA del mensaje con AES
        Cipher aesCipherDecrypt = Cipher.getInstance("AES");
        aesCipherDecrypt.init(Cipher.DECRYPT_MODE, AESKey);
        byte[] byteHashEncrypted = aesCipherDecrypt.doFinal(getFileInBytes(new File("hashCifrado.txt")));

        //Desencriptar hash con pública del EMISOR
        PublicKey puKeyEm = getPublic("PublicKeyEMISOR.txt");
        Cipher CipherDecrypt = Cipher.getInstance("RSA");
        CipherDecrypt.init(Cipher.DECRYPT_MODE, puKeyEm);
        byte[] byteHashDecrypted = CipherDecrypt.doFinal(byteHashEncrypted);

        //Hash desencriptado
        String decoded = Base64.getEncoder().encodeToString(byteHashDecrypted);
        System.out.println("Hash después de desencriptar: " + decoded);

        aesCipherDecrypt = Cipher.getInstance("AES");
        aesCipherDecrypt.init(Cipher.DECRYPT_MODE, AESKey);
        byte[] mensajeDesencriptado = aesCipherDecrypt.doFinal(getFileInBytes(new File("mensajeEncriptado.txt")));
        
        String mensaje = new String(mensajeDesencriptado);
        System.out.println("Mensaje original: " + mensaje);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(mensaje.getBytes(StandardCharsets.UTF_8));

        String hashDelOriginal = Base64.getEncoder().encodeToString(hash);
        System.out.println("Hash calculado inicialmente: " + hashDelOriginal);
        
        if (decoded.equals(hashDelOriginal))
            System.out.println("Los hash coinciden.");
        else
            System.out.println("ERROR: Los hash NO coinciden");
        
        //String plainTextDecrypted = new String(textoSinHash);
        //System.out.println("Texto original: " + plainTextDecrypted);
    }
    
    public static String readFile(String path, Charset encoding) throws Exception {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static byte[] getFileInBytes(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        byte[] fbytes = new byte[(int) f.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }

    public static PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

}
