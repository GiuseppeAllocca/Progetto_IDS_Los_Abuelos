package boundary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class UseCasesOperatorePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private MainFrame parentFrame;

    private JLabel lblLogo;
    private JPanel panelCentro;
    private JLabel lblBenvenuto;
    private JButton btnRicercaProdotti;
    private JButton btnGestioneMovimenti;
    private JButton btnLogout;

    /**
     * Questo JPanel serve principalmente a gestire la divisione dei vari casi d'uso
     * che sono accessibili agli Utenti di tipo Operatore, garantendo dunque
     * una sorta di dashboard dalla quale siano facilmente navigabili tutte le opzioni,
     */

    public UseCasesOperatorePanel(MainFrame parentFrame) {
        this.parentFrame = parentFrame;

        setBorder(new EmptyBorder(20, 20, 20, 20));
        setLayout(new BorderLayout(0, 20));

        add(getLblLogo(), BorderLayout.NORTH);
        add(getPanelCentro(), BorderLayout.CENTER);
    }


    private JLabel getLblLogo() {
        if (lblLogo == null) {
            lblLogo = new JLabel("");
            lblLogo.setHorizontalAlignment(JLabel.CENTER);

            URL imgUrl = getClass().getResource("/images/logo.png");
            if (imgUrl != null) {
                Image imgScalata = new ImageIcon(imgUrl).getImage().getScaledInstance(120, -1, Image.SCALE_SMOOTH);
                lblLogo.setIcon(new ImageIcon(imgScalata));
            } else {
                lblLogo.setText("LOGO APPLICAZIONE");
            }
        }
        return lblLogo;
    }

    private JPanel getPanelCentro() {
        if (panelCentro == null) {
            panelCentro = new JPanel();

            GridBagLayout gbl_panelCentro = new GridBagLayout();
            gbl_panelCentro.columnWidths = new int[]{0, 0};
            gbl_panelCentro.rowHeights = new int[]{0, 0, 0, 0, 0};
            gbl_panelCentro.columnWeights = new double[]{1.0, Double.MIN_VALUE};
            gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
            panelCentro.setLayout(gbl_panelCentro);

            GridBagConstraints gbc_lblBenvenuto = new GridBagConstraints();
            gbc_lblBenvenuto.insets = new Insets(0, 0, 30, 0);
            gbc_lblBenvenuto.gridx = 0;
            gbc_lblBenvenuto.gridy = 0;
            panelCentro.add(getLblBenvenuto(), gbc_lblBenvenuto);

            GridBagConstraints gbc_btnGestioneMovimenti = new GridBagConstraints();
            gbc_btnGestioneMovimenti.insets = new Insets(0, 0, 15, 0);
            gbc_btnGestioneMovimenti.gridx = 0;
            gbc_btnGestioneMovimenti.gridy = 1;
            panelCentro.add(getBtnGestioneMovimenti(), gbc_btnGestioneMovimenti);

            GridBagConstraints gbc_btnRicerca = new GridBagConstraints();
            gbc_btnRicerca.insets = new Insets(0, 0, 30, 0);
            gbc_btnRicerca.gridx = 0;
            gbc_btnRicerca.gridy = 2;
            panelCentro.add(getBtnRicercaProdotti(), gbc_btnRicerca);

            GridBagConstraints gbc_btnLogout = new GridBagConstraints();
            gbc_btnLogout.gridx = 0;
            gbc_btnLogout.gridy = 3;
            panelCentro.add(getBtnLogout(), gbc_btnLogout);
        }
        return panelCentro;
    }

    private JLabel getLblBenvenuto() {
        if (lblBenvenuto == null) {
            lblBenvenuto = new JLabel("Benvenuto nella homepage, Operatore!");
            lblBenvenuto.setFont(new Font("Tahoma", Font.BOLD, 16));
            lblBenvenuto.setHorizontalAlignment(JLabel.CENTER);
        }
        return lblBenvenuto;
    }


    public JButton getBtnGestioneMovimenti() {
        if (btnGestioneMovimenti == null) {
            btnGestioneMovimenti = new JButton("Gestione Movimenti");
            btnGestioneMovimenti.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    parentFrame.switchPanel("MOVE_PROD");
                }
            });
            btnGestioneMovimenti.setPreferredSize(new Dimension(200, 40));
        }
        return btnGestioneMovimenti;
    }

    public JButton getBtnRicercaProdotti() {
        if (btnRicercaProdotti == null) {
            btnRicercaProdotti = new JButton("Ricerca Prodotti");
            btnRicercaProdotti.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		parentFrame.ricercaProdotti("DASH_OP");
            	}
            });
            btnRicercaProdotti.setPreferredSize(new Dimension(200, 40));
        }
        return btnRicercaProdotti;
    }

    public JButton getBtnLogout() {
        if (btnLogout == null) {
            btnLogout = new JButton("Logout");
            btnLogout.setForeground(new Color(220, 53, 69));
            btnLogout.setPreferredSize(new Dimension(200, 40));
            btnLogout.addActionListener(e -> {
                if(parentFrame != null) {
                    if(checkLogout() == JOptionPane.YES_OPTION){
                        parentFrame.switchPanel("LOGIN_VIEW");
                    }
                }
            });
        }
        return btnLogout;
    }

    public int checkLogout(){
        return JOptionPane.showConfirmDialog(this, "Sei sicuro di voler effettuare il logout?",
                "Conferma Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
}