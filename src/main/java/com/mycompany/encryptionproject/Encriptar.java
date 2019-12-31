
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.encryptionproject;

import static com.mycompany.encryptionproject.Desencriptar.getFileInBytes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
public class Encriptar
{
    /**
     * @param args the command line arguments
     */
    public static SecretKey secKey;

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException, IOException, Exception
    {
        //Generar clave AES
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits
        secKey = generator.generateKey();
        try (FileOutputStream stream = new FileOutputStream("AESKey.txt"))
        {
            stream.write(secKey.getEncoded());
        }

        System.out.println("Benvingut.\n"
                + "Hi ha dues parelles de claus generades per defecte, un parell pel receptor i un parell per l'emisor."
                + "\nSi vols utilitzar les teves claus, hauràs de copiar el arxius amb extensió .txt a la carpeta del projecte,\na la mateixa altura que les carpetes src i target.\n"
                + "Amb els noms:\n"
                + "PublicKeyEMISOR.txt, PrivateKeyEMISOR.txt, PublicKeyRECEPTOR.txt, PrivateKeyRECEPTOR.txt\n");
        
        int opcio;
        do
        {
            opcio = menu();

            switch (opcio)
            {
                case 1:
                    generarClaus();
                    break;
                case 2:
                    encriptar();
                    break;
                case 3:
                    desencriptar();
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("No és una opció.");
                    break;
            }
        } while (opcio != 4);

    }

    public static int menu()
    {

        System.out.println("1. Generar dos parells de claus (Es sobreescriuran les que existeixen)\n"
                + "2. Encriptar un missatge\n"
                + "3. Desencriptar un missatge\n"
                + "4. Sortir\n\n"
                + "Selecciona una opció.");

        int opcio = Util.demanarInt("");

        return opcio;
    }

    public static void generarClaus() throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, Exception
    {
        //Generar pareja claves RSA Emisor
        KeyPairGenerator kpgEm = KeyPairGenerator.getInstance("RSA");
        kpgEm.initialize(2048);
        KeyPair keyPairEm = kpgEm.generateKeyPair();

        PublicKey puKeyEm = keyPairEm.getPublic();
        try (FileOutputStream stream = new FileOutputStream("PublicKeyEMISOR.txt"))
        {
            stream.write(puKeyEm.getEncoded());
        }
        PrivateKey prKeyEm = keyPairEm.getPrivate();
        try (FileOutputStream stream = new FileOutputStream("PrivateKeyEMISOR.txt"))
        {
            stream.write(prKeyEm.getEncoded());
        }

        //Generar pareja claves RSA Receptor
        KeyPairGenerator kpgRe = KeyPairGenerator.getInstance("RSA");
        kpgRe.initialize(2048);
        KeyPair keyPairRe = kpgRe.generateKeyPair();

        PublicKey puKeyRe = keyPairRe.getPublic();
        try (FileOutputStream stream = new FileOutputStream("PublicKeyRECEPTOR.txt"))
        {
            stream.write(puKeyRe.getEncoded());
        }
        PrivateKey prKeyRe = keyPairRe.getPrivate();
        try (FileOutputStream stream = new FileOutputStream("PrivateKeyRECEPTOR.txt"))
        {
            stream.write(prKeyRe.getEncoded());
        }
        System.out.println("Generades correctament.\n");
    }

    public static void encriptar() throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, Exception
    {
        String plainText = Util.demanarString("Missatge a encriptar:");
        System.out.println("Missatge: " + plainText);
        try (FileOutputStream stream = new FileOutputStream("mensaje.txt"))
        {
            stream.write(plainText.getBytes());
        }

        Cipher mensajeCipher = Cipher.getInstance("AES");
        mensajeCipher.init(Cipher.PUBLIC_KEY, secKey);
        byte[] mensajeEncrypted = mensajeCipher.doFinal(getFileInBytes(new File("mensaje.txt")));

        try (FileOutputStream stream = new FileOutputStream("mensajeEncriptado.txt"))
        {
            stream.write(mensajeEncrypted);
        }

        //Calcular hash del mensaje
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));

        String encoded = Base64.getEncoder().encodeToString(hash);
        System.out.println("Hash calculat inicialment: " + encoded);

        //Cifrar hash con clave privada del EMISOR       
        Cipher aesCipher = Cipher.getInstance("RSA");
        PrivateKey prKeyEm = getPrivate("PrivateKeyEMISOR.txt");
        aesCipher.init(Cipher.ENCRYPT_MODE, prKeyEm);
        byte[] byteCipherText = aesCipher.doFinal(hash);

        //Encriptar hash encriptado-RSA con AES
        Cipher hashRSACipher = Cipher.getInstance("AES");
        hashRSACipher.init(Cipher.PUBLIC_KEY, secKey);
        byte[] encryptedHashRSA = hashRSACipher.doFinal(byteCipherText);

        try (FileOutputStream stream = new FileOutputStream("hashCifrado.txt"))
        {
            stream.write(encryptedHashRSA);
        }

        //Encriptar AES con pública del RECEPTOR
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey puKeyRe = getPublic("PublicKeyRECEPTOR.txt");
        cipher.init(Cipher.PUBLIC_KEY, puKeyRe);
        byte[] encryptedAESKey = cipher.doFinal(secKey.getEncoded());

        try (FileOutputStream stream = new FileOutputStream("AESEncriptada.txt"))
        {
            stream.write(encryptedAESKey);
        }
        System.out.println("");
    }

    public static void desencriptar() throws FileNotFoundException, IOException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, Exception
    {
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
        System.out.println("Hash després de desencriptar: " + decoded);

        aesCipherDecrypt = Cipher.getInstance("AES");
        aesCipherDecrypt.init(Cipher.DECRYPT_MODE, AESKey);
        byte[] mensajeDesencriptado = aesCipherDecrypt.doFinal(getFileInBytes(new File("mensajeEncriptado.txt")));

        String mensaje = new String(mensajeDesencriptado);
        System.out.println("Missatge original: " + mensaje);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(mensaje.getBytes(StandardCharsets.UTF_8));

        String hashDelOriginal = Base64.getEncoder().encodeToString(hash);
        System.out.println("Hash calculat inicialment: " + hashDelOriginal);

        if (decoded.equals(hashDelOriginal))
        {
            System.out.println("Els hash coincideixen.");
        } else
        {
            System.out.println("ERROR: Els hash NO coincideixen.");
        }
        System.out.println("");
    }
    
    public static String readFile(String path, Charset encoding) throws Exception
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static byte[] getFileInBytes(File f) throws IOException
    {
        FileInputStream fis = new FileInputStream(f);
        byte[] fbytes = new byte[(int) f.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }

    public static PrivateKey getPrivate(String filename) throws Exception
    {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static PublicKey getPublic(String filename) throws Exception
    {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
