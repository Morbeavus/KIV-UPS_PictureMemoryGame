/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pexeso_client;

/**
 *
 * @author Morbeavus
 */
public class Pexeso_client 
{    
    public static Pexeso_GUI mygui = new Pexeso_GUI();
    public static Game CurrentGame = null;
    public static Player CurrentPlayer = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater
        (   new Runnable() 
            {
                @Override
                public void run() 
                {
                    mygui.setVisible(true);
                }
            }
        );
    }
}
    

