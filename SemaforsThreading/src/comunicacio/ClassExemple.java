/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacio;

/**
 *
 * @author Jordi
 */
public class ClassExemple {
    private String text;

    public ClassExemple(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    // Bloquegem el mètode per evitar que es sobreescriguin
    synchronized public void setText(String text) {
        this.text = text;
    }
    
}
