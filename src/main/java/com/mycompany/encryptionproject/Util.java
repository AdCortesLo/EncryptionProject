/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.encryptionproject;
 
import java.util.Scanner;
 
/**
 *
 * @author adrian
 */
public class Util {
 
    public static int demanarInt(String frase) {
 
        Scanner teclat = new Scanner(System.in);
        int numero;
 
        System.out.println(frase);
        while (!teclat.hasNextInt()) {
            System.out.println("Has d'introduir un número");
            teclat.next();
        }
        numero = teclat.nextInt();
        return numero;
    }
 
    /**
     * Demana un double
     * @param frase
     * @return double
     */
    public static double demanarDouble(String frase) {
 
        Scanner teclat = new Scanner(System.in);
        double numero;
 
        System.out.println(frase);
        while (!teclat.hasNextDouble()) {
            System.out.println("Has d'introduir un número");
            teclat.next();
        }
        numero = teclat.nextDouble();
        return numero;
    }
 
    /**
     * Demana un char entre dues opcions.
     * @param frase
     * @param a
     * @param b
     * @return char
     */
    public static char demanarCharDual(String frase, char a, char b) {
 
        Scanner teclat = new Scanner(System.in);
        System.out.println(frase);
 
        char caracter = teclat.next().charAt(0);
 
        while (caracter != a && caracter != b) {
            System.out.println("Introdueix " + a + "/" + b);
            caracter = teclat.next().charAt(0);
 
        }
        return caracter;
    }
 
    /**
     * Demanar un char qualsevol.
     * @param frase
     * @return char
     */
    public static char demanarChar(String frase) {
 
        Scanner teclat = new Scanner(System.in);
        System.out.println(frase);
 
        char caracter = teclat.next().charAt(0);
        return caracter;
    }
     
    /**
     * Demana un string qualsevol.
     * @param frase
     * @return string
     */
    public static String demanarString(String frase) {
 
        Scanner teclat = new Scanner(System.in);
        String paraula;
 
        System.out.println(frase);
        paraula = teclat.nextLine();
 
        while (paraula.length() == 0) {
            System.out.println("Introdueix mínim un caràcter.");
            paraula = teclat.nextLine();
        }
 
        return paraula;
    }
 
}