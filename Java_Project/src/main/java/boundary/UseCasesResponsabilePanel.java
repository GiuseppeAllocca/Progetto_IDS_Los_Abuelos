package boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class UseCasesResponsabilePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private MainFrame parentFrame;

    private JLabel lblLogo;
    private JPanel panelCentro;
    private JLabel lblBenvenuto;
    private JButton btnRicercaProdotti;
    private JButton btnCreazioneProdotti;
    private JButton btnLogout;
    private JButton btnVisualizzaSottoScorta;
    private JButton btnVisualizzaMessaggi;
    private JButton btnModificaProdotti;

	/**
	 * Questo JPanel serve principalmente a gestire la divisione dei vari casi d'uso
	 * che sono accessibili agli Utenti di tipo Responsabile, garantendo dunque
	 * una sorta di dashboard dalla quale siano facilmente navigabili tutte le opzioni,
	 * a ogni bottone è associato un evento che invoca il metodo switchPanel() (presente
	 * nel MainFrame) che consente di passare a un Panel successivo mediante l'uso
	 * della Stringa identificativa settata sempre nel MainFrame) , motivo per il quale
	 * ogno JPanel prevdere che gli venga passato nel construttore un il MainFrame individuato
	 * come parentFrame che consente l'invocazione di tale metodo.
	 */

    public UseCasesResponsabilePanel(MainFrame parentFrame) {
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
		Image imgScalata = new ImageIcon(imgUrl).getImage()
		    .getScaledInstance(120, -1, Image.SCALE_SMOOTH);
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
	    gbl_panelCentro.columnWidths = new int[] { 0, 0 };
	    gbl_panelCentro.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0,
		0 };
	    gbl_panelCentro.columnWeights = new double[] { 1.0,
		Double.MIN_VALUE };
	    gbl_panelCentro.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
		0.0, 0.0, 0.0, Double.MIN_VALUE };
	    panelCentro.setLayout(gbl_panelCentro);

	    GridBagConstraints gbc_lblBenvenuto = new GridBagConstraints();
	    gbc_lblBenvenuto.insets = new Insets(0, 0, 30, 0);
	    gbc_lblBenvenuto.gridx = 0;
	    gbc_lblBenvenuto.gridy = 0;
	    panelCentro.add(getLblBenvenuto(), gbc_lblBenvenuto);

	    GridBagConstraints gbc_btnCreazioneProdotti = new GridBagConstraints();
	    gbc_btnCreazioneProdotti.insets = new Insets(0, 0, 15, 0);
	    gbc_btnCreazioneProdotti.gridx = 0;
	    gbc_btnCreazioneProdotti.gridy = 1;
	    panelCentro.add(getBtnCreazioneProdotti(), gbc_btnCreazioneProdotti);
	    
	    GridBagConstraints gbc_btnModificaProdotti = new GridBagConstraints();
	    gbc_btnModificaProdotti.insets = new Insets(0, 0, 15, 0);
	    gbc_btnModificaProdotti.gridx = 0;
	    gbc_btnModificaProdotti.gridy = 2;
	    panelCentro.add(getBtnModificaProdotti(), gbc_btnModificaProdotti);

	    GridBagConstraints gbc_btnRicerca = new GridBagConstraints();
	    gbc_btnRicerca.insets = new Insets(0, 0, 30, 0);
	    gbc_btnRicerca.gridx = 0;
	    gbc_btnRicerca.gridy = 3;
	    panelCentro.add(getBtnRicercaProdotti(), gbc_btnRicerca);

	    GridBagConstraints gbc_btnVisualizzaMessaggi = new GridBagConstraints();
	    gbc_btnVisualizzaMessaggi.insets = new Insets(0, 0, 15, 0);
	    gbc_btnVisualizzaMessaggi.gridx = 0;
	    gbc_btnVisualizzaMessaggi.gridy = 4;
	    
	    	    GridBagConstraints gbc_btnVisualizzaSottoScorta = new GridBagConstraints();
	    	    gbc_btnVisualizzaSottoScorta.insets = new Insets(0, 0, 15, 0);
	    	    gbc_btnVisualizzaSottoScorta.gridx = 0;
	    	    gbc_btnVisualizzaSottoScorta.gridy = 5;
	    	    panelCentro.add(getBtnVisualizzaSottoScorta(),
		gbc_btnVisualizzaSottoScorta);

	    GridBagConstraints gbc_btnLogout = new GridBagConstraints();
	    gbc_btnLogout.insets = new Insets(0, 0, 5, 0);
	    gbc_btnLogout.gridx = 0;
	    gbc_btnLogout.gridy = 6;
	    panelCentro.add(getBtnLogout(), gbc_btnLogout);
	}
	return panelCentro;
    }

    private JLabel getLblBenvenuto() {
	if (lblBenvenuto == null) {
	    lblBenvenuto = new JLabel(
		"Benvenuto nella homepage, Responsabile!");
	    lblBenvenuto.setFont(new Font("Tahoma", Font.BOLD, 16));
	    lblBenvenuto.setHorizontalAlignment(JLabel.CENTER);
	}
	return lblBenvenuto;
    }

    public JButton getBtnCreazioneProdotti() {
	if (btnCreazioneProdotti == null) {
	    btnCreazioneProdotti = new JButton("Creazione Prodotti");
	    btnCreazioneProdotti.setPreferredSize(new Dimension(200, 40));
		btnCreazioneProdotti.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parentFrame.switchPanel("CREATE_PROD");
			}
		});
	}
	return btnCreazioneProdotti;
    }

    public JButton getBtnRicercaProdotti() {
	if (btnRicercaProdotti == null) {
	    btnRicercaProdotti = new JButton("Ricerca Prodotti");
	    btnRicercaProdotti.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			parentFrame.ricercaProdotti("DASH_RESP");
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
		if (parentFrame != null) {
		    if (checkLogout() == JOptionPane.YES_OPTION) {
			parentFrame.switchPanel("LOGIN_VIEW");
		    }

		}
	    });
	}
	return btnLogout;
    }

    public int checkLogout() {
	return JOptionPane.showConfirmDialog(this,
	    "Sei sicuro di voler effettuare il logout?", "Conferma Logout",
	    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    private JButton getBtnVisualizzaSottoScorta() {
	if (btnVisualizzaSottoScorta == null) {
	    btnVisualizzaSottoScorta = new JButton(
		"Visualizza Prodotti Sotto Scorta");
	    btnVisualizzaSottoScorta.setPreferredSize(new Dimension(200, 40));
	    btnVisualizzaSottoScorta.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			parentFrame.visualizzaSottoScorta("VISUAL_UNDER");
		}
	    });
	}
	return btnVisualizzaSottoScorta;
    }

	private JButton getBtnModificaProdotti() {
		if (btnModificaProdotti == null) {
			btnModificaProdotti = new JButton("Modifica Prodotti");
			btnModificaProdotti.setPreferredSize(new Dimension(200, 40));
			btnModificaProdotti.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					parentFrame.switchPanel("EDIT_PROD");
				}
			});
		}
		return btnModificaProdotti;
	}
}