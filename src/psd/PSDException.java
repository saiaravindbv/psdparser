/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package psd;

/**
 *
 * @author aravind
 */
public class PSDException extends Exception {
    
    public PSDException(String message) {
        super("(PSDException)" + message);
    }
    
}
