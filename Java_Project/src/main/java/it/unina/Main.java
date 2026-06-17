package it.unina;

import java.awt.EventQueue;
import com.formdev.flatlaf.FlatLightLaf;
import boundary.MainFrame;
import database.JpaUtil;
import entity.GestoreProdotti;
import entity.GestoreUtenti;
import setup.DatiTestProdotti;
import setup.DatiTestUtenti;


public class Main {

    public static void main(String[] args) {

        try {
            FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Impossibile caricare FlatLaf.");
        }

        JpaUtil.getInstance();


        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setLocationRelativeTo(null); 
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}